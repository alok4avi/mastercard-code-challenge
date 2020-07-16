package com.mastercard.coding.challenge.resource;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class is responsible to prepare the graph data structure to hold the
 * data being loaded by Service class. Also it provides different helper methods
 * so that we can determine the route between cities.
 *
 */
public class CityConnectGraph {

	private Map<String, LinkedHashSet<String>> cityMap = new HashMap<String, LinkedHashSet<String>>();

	/**
	 * This method add route between two cities
	 * 
	 * @param fromCity
	 * @param toCity
	 */
	private void addRoute(String fromCity, String toCity) {
		LinkedHashSet<String> connectedCities = cityMap.get(fromCity);

		if (connectedCities == null) {
			connectedCities = new LinkedHashSet<String>();
			cityMap.put(fromCity, connectedCities);

		}
		connectedCities.add(toCity);
	}

	/**
	 * This method add two way route between two cities
	 * 
	 * @param fromCity
	 * @param toCity
	 */
	public void addTwoWayRoute(String fromCity, String toCity) {
		addRoute(fromCity, toCity);
		addRoute(toCity, fromCity);

	}

	/**
	 * This method returns the list of directly connected cities for the given city
	 * 
	 * @param city
	 * @return
	 */
	private LinkedList<String> getConnectedCities(String city) {

		LinkedHashSet<String> routes = cityMap.get(city);
		if (routes == null) {
			return new LinkedList<String>();
		}
		return new LinkedList<String>(routes);
	}

	/**
	 * This method determines if the origin city & destination city is connected
	 * directly or indirectly and returns a boolean response accordingly.
	 * 
	 * @param cityGraph       data structure having routes between different cities
	 * @param originCity      name of origin city
	 * @param destinationCity name of destination city
	 * @param visitedCities   list of already visited cities
	 * @return
	 */
	public boolean areTwoCitiesConnected(CityConnectGraph cityGraph, String originCity, String destinationCity,
			LinkedList<String> visitedCities) {

		if (originCity.equals(destinationCity)) {
			return true;
		}
		boolean isConnected = false;

		for (String currentCity : cityGraph.getConnectedCities(originCity)) {
			if (!visitedCities.contains(currentCity)) {
				visitedCities.addLast(currentCity);
				isConnected = isConnected
						|| areTwoCitiesConnected(cityGraph, currentCity, destinationCity, visitedCities);
				if (isConnected)
					break;
			}
		}

		return isConnected;
	}

}
