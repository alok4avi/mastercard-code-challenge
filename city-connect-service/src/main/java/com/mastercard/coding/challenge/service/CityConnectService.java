package com.mastercard.coding.challenge.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.mastercard.coding.challenge.resource.CityConnectGraph;

@Service
public class CityConnectService {

	@Autowired
	private ResourceLoader resourceLoader;

	private CityConnectGraph cityConnectGraph;

	private static final String YES = "Yes";
	private static final String NO = "No";
	private static final String DELIMITER = ",";

	private Resource loadData() {
		return resourceLoader.getResource("classpath:city.txt");
	}

	@PostConstruct
	private void prepareCityConnectGraph() {
		cityConnectGraph = new CityConnectGraph();
		Resource repo = loadData();
		try {
			File file = repo.getFile();
			Stream<String> lines = Files.lines(file.toPath());
			List<String> cityRoutes = lines.collect(Collectors.toList());
			for (String route : cityRoutes) {
				String[] data = route.split(DELIMITER);
				cityConnectGraph.addTwoWayRoute(data[0].toLowerCase().trim(), data[1].toLowerCase().trim());
			}
			lines.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String areTwoCitiesConnected(String originCity, String destinationCity) {
		LinkedList<String> visited = new LinkedList<String>();
		visited.add(originCity.toLowerCase().trim());
		if(originCity.isBlank() || destinationCity.isBlank()) {
			return NO;
		}
		else if (cityConnectGraph.areTwoCitiesConnected(cityConnectGraph, originCity, destinationCity, visited)) {
			return YES;
		}
		return NO;
	}
}
