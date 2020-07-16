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
		mockMvc.perform(get("/connected?origin=Boston&destination=Philadelphia")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesAreNotConnected() throws Exception {
		mockMvc.perform(get("/connected?origin=Philadelphia&destination=Albany")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenOriginCityIsBlank() throws Exception {
		mockMvc.perform(get("/connected?origin=&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenDestinationCityIsBlank() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesNameDoesNotExist() throws Exception {
		mockMvc.perform(get("/connected?origin=Paris&destination=Toronto")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenOriginCityDoesNotExist() throws Exception {
		mockMvc.perform(get("/connected?origin=Paris&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenDestinationCityDoesNotExist() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=Toronto")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}

	
	@Test
	public void  isConnected_whenInvalidOriginCity() throws Exception {
		mockMvc.perform(get("/connected?origin=abcd&destination=Newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenInvalidDestinationCity() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=abcd")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("No")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesAreConnectedButHavingUppercaseNames() throws Exception {
		mockMvc.perform(get("/connected?origin=BOSTON&destination=NEWARK")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesAreConnectedButHavingLowercaseNames() throws Exception {
		mockMvc.perform(get("/connected?origin=boston&destination=newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}
	
	@Test
	public void  isConnected_whenCitiesAreConnectedButHavingMixedcaseNames() throws Exception {
		mockMvc.perform(get("/connected?origin=Boston&destination=newark")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Yes")).andReturn().getResponse().getContentAsString();

	}

}
