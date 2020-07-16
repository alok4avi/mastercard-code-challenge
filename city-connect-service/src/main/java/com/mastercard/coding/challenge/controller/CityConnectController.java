package com.mastercard.coding.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mastercard.coding.challenge.service.CityConnectService;

@RestController
@RequestMapping(value = "/connected")
public class CityConnectController {

	@Autowired
	private CityConnectService cityConnectService;

	@GetMapping
	public String isConnected(@RequestParam(value = "origin", required = true) String originCity,
			@RequestParam(value = "destination", required = true) String destinationCity) {

		return cityConnectService.areTwoCitiesConnected(originCity.toLowerCase().trim(),
				destinationCity.toLowerCase().trim());
	}

}
