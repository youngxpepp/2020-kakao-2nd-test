package com.youngxpepp.kakaotest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KaKaoClientTest {

	private static KaKaoClient client = KaKaoClient.getInstance();

	@Test
	void start() {
		Dto.StartResponse response = client.start(1);
	}

	@Test
	void getLocations() {
		Dto.StartResponse startResponse = client.start(1);
		String authKey = startResponse.getAuthKey();
		Dto.LocationsResponse locationsResponse = client.getLocations(authKey);
	}

	@Test
	void getTrucks() {
		Dto.StartResponse startResponse = client.start(1);
		String authKey = startResponse.getAuthKey();
		Dto.TrucksResponse trucksResponse = client.getTrucks(authKey);
	}

	@Test
	void simulate() {


	}

	@Test
	void getInstance() {
	}
}
