package com.mastercard.coding.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.coding.challenge.service.CityConnectService;

import io.swagger.annotations.ApiOperation;

/**
 * Controller class to handle the incoming request to check if two cities are
 * connected
 * 
 */
@RestController
@RequestMapping(value = "/connected")
public class CityConnectController {

	@Autowired
	private CityConnectService cityConnectService;
	
	private Logger LOGGER = LoggerFactory.getLogger(CityConnectController.class);

	/**
	 * Method to receive GET requests in order to check if a route exist between
	 * origin & destination cities
	 * 
	 * @param originCity      name of the origin city
	 * @param destinationCity name of the destination city
	 * @return
	 */
	@ApiOperation(value = "Determine if two two cities are connected", notes = "Returns 'Yes' or 'No' as response")
	@GetMapping
	public String isConnected(@RequestParam(value = "origin", required = true) String originCity,
			@RequestParam(value = "destination", required = true) String destinationCity) {
		
		LOGGER.debug("Invoking isConnected for origin city: {} & destinaction city: {}", originCity, destinationCity);

		return cityConnectService.areTwoCitiesConnected(originCity.toLowerCase().trim(),
				destinationCity.toLowerCase().trim());
	}

}
