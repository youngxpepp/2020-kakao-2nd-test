package com.youngxpepp.kakaotest;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class KaKaoClient {

	private final RestTemplate restTemplate;
	private static KaKaoClient instance;
	private static final String X_AUTH_TOKEN = "3641614ac6c4b71439a8a68391917b06";
	private static final String ROOT_URI = "https://pegkq2svv6.execute-api.ap-northeast-2.amazonaws.com/prod/users";
	private static final String START_PATH = "/start";
	private static final String LOCATIONS_PATH = "/locations";
	private static final String TRUCKS_PATH = "/trucks";
	private static final String SIMULATE_PATH = "/simulate";
	private static final String SCORE_PATH = "/score";

	private KaKaoClient() {
		this.restTemplate = new RestTemplateBuilder()
			.rootUri(ROOT_URI)
			.build();
	}

	public Dto.StartResponse start(int problemId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Auth-Token", X_AUTH_TOKEN);

		Dto.StartRequestBody body = Dto.StartRequestBody.builder()
			.problem(problemId)
			.build();

		HttpEntity httpEntity = new HttpEntity(body, headers);

		ResponseEntity<Dto.StartResponse> responseEntity = restTemplate.exchange(START_PATH, HttpMethod.POST,
			httpEntity, Dto.StartResponse.class);

		return responseEntity.getBody();
	}

	public Dto.LocationsResponse getLocations(String authKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authKey);
		HttpEntity httpEntity = new HttpEntity(headers);

		ResponseEntity<Dto.LocationsResponse> responseEntity = restTemplate.exchange(LOCATIONS_PATH, HttpMethod.GET,
			httpEntity, Dto.LocationsResponse.class);

		return responseEntity.getBody();
	}

	public Dto.TrucksResponse getTrucks(String authKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authKey);
		HttpEntity httpEntity = new HttpEntity(headers);

		ResponseEntity<Dto.TrucksResponse> responseEntity = restTemplate.exchange(TRUCKS_PATH, HttpMethod.GET,
			httpEntity,
			Dto.TrucksResponse.class);

		return responseEntity.getBody();
	}

	public Dto.SimulateResponse simulate(String authKey, List<Dto.CommandDto> commands) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authKey);

		Dto.SimulateRequest body = Dto.SimulateRequest.builder()
			.commands(commands)
			.build();

		HttpEntity httpEntity = new HttpEntity(body, headers);

		ResponseEntity<Dto.SimulateResponse> responseEntity = restTemplate.exchange(SIMULATE_PATH, HttpMethod.PUT,
			httpEntity, Dto.SimulateResponse.class);

		return responseEntity.getBody();
	}

	public Dto.ScoreResponse getScore(String authKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authKey);
		HttpEntity httpEntity = new HttpEntity(headers);

		ResponseEntity<Dto.ScoreResponse> responseEntity = restTemplate.exchange(SCORE_PATH, HttpMethod.GET, httpEntity,
			Dto.ScoreResponse.class);

		return responseEntity.getBody();
	}

	public static KaKaoClient getInstance() {
		if (instance == null) {
			instance = new KaKaoClient();
		}

		return instance;
	}
}
