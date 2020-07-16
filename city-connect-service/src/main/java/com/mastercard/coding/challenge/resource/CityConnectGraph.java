package com.mastercard.coding.challenge.resource;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

public class CityConnectGraph {

	private Map<String, LinkedHashSet<String>> cityMap = new HashMap<String, LinkedHashSet<String>>();

	private void addRoute(String fromCity, String toCity) {
		LinkedHashSet<String> connectedCities = cityMap.get(fromCity);

		if (connectedCities == null) {
			connectedCities = new LinkedHashSet<String>();
			cityMap.put(fromCity, connectedCities);

		}
		connectedCities.add(toCity);
	}

	public void addTwoWayRoute(String fromCity, String toCity) {
		addRoute(fromCity, toCity);
		addRoute(toCity, fromCity);

	}

	private LinkedList<String> getConnectedCities(String city) {

		LinkedHashSet<String> routes = cityMap.get(city);
		if (routes == null) {
			return new LinkedList<String>();
		}
		return new LinkedList<String>(routes);
	}

	public boolean areTwoCitiesConnected(CityConnectGraph cityGraph, String originCity, String destinationCity,
			LinkedList<String> visitedCities) {

		if (originCity.equals(destinationCity)) {
			return true;
		}
		boolean isConnected = false;

		for (String currentCity : cityGraph.getConnectedCities(originCity)) {
			if (!visitedCities.contains(currentCity)) {
				visitedCities.addLast(currentCity);
				isConnected = isConnected || areTwoCitiesConnected(cityGraph, currentCity, destinationCity, visitedCities);
				if(isConnected) break;
			}
		}

		return isConnected;
	}

}
