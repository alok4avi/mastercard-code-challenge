package com.mastercard.coding.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mastercard.coding.challenge.service.CityConnectService;

@SpringBootTest
@AutoConfigureMockMvc
public class CityConnectControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CityConnectService cityConnectService;

	@Test
	public void  isConnected_whenCitiesConnectedDirectly() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesConnectedViaAnotherCity() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesAreNotConnected() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenOriginCityIsBlank() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenDestinationCityIsBlank() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesNameDoesNotExist() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenOriginCityDoesNotExist() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenDestinationCityDoesNotExist() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}

	
	@Test
	public void  isConnected_whenGivenInvalidOriginCity() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenGivenInvalidDestinationCity() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}

}
