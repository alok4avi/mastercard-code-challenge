package com.mastercard.coding.challenge.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.mastercard.coding.challenge.controller.CityConnectController;
import com.mastercard.coding.challenge.exception.FileFormatNotSupportedException;
import com.mastercard.coding.challenge.resource.CityConnectGraph;

/**
 * Service class to perform business rules upon the requests received from
 * Controller and send response back to the Controller
 *
 */
@Service
public class CityConnectService {

	@Autowired
	private ResourceLoader resourceLoader;

	private CityConnectGraph cityConnectGraph;

	private static final String YES = "Yes";
	private static final String NO = "No";
	private static final String DELIMITER = ",";
	
	private Logger LOGGER = LoggerFactory.getLogger(CityConnectController.class);

	private Resource loadData() {
		return resourceLoader.getResource("classpath:city.txt");
	}

	/**
	 * This method is used to load the data from city.txt file which has route
	 * details among different cities. Based on this data the connection between two
	 * cities(directly or indirectly) will be determined. This method gets invoked
	 * only once at the time of bean creation of this class so that data is
	 * available before the first request comes in and not required to load the data
	 * again & again.
	 */
	@PostConstruct
	private void prepareCityConnectGraph() {
		cityConnectGraph = new CityConnectGraph();
		Resource dataSource = loadData();
		LOGGER.debug("Invoking prepareCityConnectGraph to load the city routes");
		File file = null;
		try {
			file = dataSource.getFile();
			Stream<String> lines = Files.lines(file.toPath());
			List<String> cityRoutes = lines.collect(Collectors.toList());
			for (String route : cityRoutes) {
				String[] data = route.split(DELIMITER);
				if(data.length > 2) {
					lines.close();
					throw new FileFormatNotSupportedException("Input file format is not supported");
				}
				cityConnectGraph.addTwoWayRoute(data[0].toLowerCase().trim(), data[1].toLowerCase().trim());
			}
			lines.close();
		} catch (IOException e) {
			LOGGER.error("Failed to load the city routes by reading the file at path: " + file.toPath());
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Service method to determine if a route exist between origin & destination
	 * city after being invoked by the Controller. Based on if a route exist it
	 * sends either YES or NO as response back to the Controller
	 * 
	 * @param originCity      name of origin city
	 * @param destinationCity name of destination city
	 * @return
	 */
	public String areTwoCitiesConnected(String originCity, String destinationCity) {
		LinkedList<String> visited = new LinkedList<String>();
		visited.add(originCity.toLowerCase().trim());
		if (originCity.isBlank() || destinationCity.isBlank()) {
			return NO;
		} else if (cityConnectGraph.areTwoCitiesConnected(cityConnectGraph, originCity, destinationCity, visited)) {
			return YES;
		}
		return NO;
	}
}
