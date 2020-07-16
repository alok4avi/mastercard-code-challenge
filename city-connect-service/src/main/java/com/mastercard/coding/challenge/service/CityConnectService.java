package com.mastercard.coding.challenge.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
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
	@Value("${city.connect.service.routes.file.name}")
	private String sourceFileName;
	@Value("${city.connect.service.routes.file.path}")
	private String sourceFilePath;

	private static final String YES = "Yes";
	private static final String NO = "No";
	private static final String DELIMITER = ",";

	private Logger LOGGER = LoggerFactory.getLogger(CityConnectController.class);

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
		LOGGER.debug("Invoking prepareCityConnectGraph to load the city routes");
		try {
			InputStream resource = new ClassPathResource(sourceFileName).getInputStream();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
				List<String> cityRoutes = reader.lines().collect(Collectors.toList());
				for (String route : cityRoutes) {
					String[] data = route.split(DELIMITER);
					if (data.length > 2) {
						throw new FileFormatNotSupportedException("Input file format is not supported");
					}
					cityConnectGraph.addTwoWayRoute(data[0].toLowerCase().trim(), data[1].toLowerCase().trim());
				}
			}
		} catch (IOException e) {
			LOGGER.error("Failed to load the city routes by reading the file at path: " + sourceFilePath);
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
		LOGGER.debug("Invoking areTwoCitiesConnected service method for origin city: {} & destination city: {}",
				originCity, destinationCity);
		LinkedList<String> visited = new LinkedList<String>();
		visited.add(originCity.toLowerCase().trim());
		if (originCity.isBlank() || destinationCity.isBlank()) {
			return NO;
		} else if (cityConnectGraph.areTwoCitiesConnected(cityConnectGraph, originCity, destinationCity, visited)) {
			LOGGER.debug("Route Found");
			return YES;
		}
		LOGGER.debug("Route Not Found");
		return NO;
	}
}
