package com.youngxpepp.kakaotest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Dto {

	@AllArgsConstructor
	@Builder
	@Getter
	public static class StartRequestBody {

		private Integer problem;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class StartResponse {

		@JsonProperty("auth_key")
		private String authKey;

		private Integer problem;

		private Integer time;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class LocationsResponse {

		private List<LocationDto> locations;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class LocationDto {

		private Integer id;

		@JsonProperty("located_bikes_count")
		private Integer bikeCount;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class TrucksResponse {

		private List<TruckDto> trucks;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class TruckDto {

		private Integer id;

		@JsonProperty("location_id")
		private Integer locationId;

		@JsonProperty("loaded_bikes_count")
		private Integer bikeCount;
	}

	@AllArgsConstructor
	@Builder
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class SimulateRequest {

		private List<CommandDto> commands;
	}

	@AllArgsConstructor
	@Builder
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class CommandDto {

		@JsonProperty("truck_id")
		private Integer truckId;

		private List<TruckCommand> command;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class SimulateResponse {

		private String status;

		private Integer time;

		@JsonProperty("failed_requests_count")
		private Integer failedRequestCount;

		private String distance;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class ScoreResponse {

		private String score;
	}
}
