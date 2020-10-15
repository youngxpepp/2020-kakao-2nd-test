package com.youngxpepp.kakaotest;

import java.util.ArrayList;
import java.util.List;

public class FirstSolution extends AbsctractSolution {

	@Override
	public void solve(int problemId) {
		Dto.StartResponse startResponse = this.client.start(problemId);
		String authKey = startResponse.getAuthKey();

		Dto.LocationsResponse locationsResponse = this.client.getLocations(authKey);
		Dto.TrucksResponse trucksResponse = this.client.getTrucks(authKey);

		int n = (int)Math.sqrt(locationsResponse.getLocations().size());
		int locationSize = locationsResponse.getLocations().size();
		int truckSize = trucksResponse.getTrucks().size();
		int avgBikeCount = locationsResponse.getLocations().get(0).getBikeCount();
		boolean finished = false;

		List<Integer> truckDestinations = new ArrayList<Integer>() {
			{
				for (int i = 0; i < truckSize; i++) {
					add(-1);
				}
			}
		};

		do {
			locationsResponse = this.client.getLocations(authKey);
			trucksResponse = this.client.getTrucks(authKey);

			List<Dto.LocationDto> locations = locationsResponse.getLocations();
			List<Dto.TruckDto> trucks = trucksResponse.getTrucks();

			List<Dto.CommandDto> commands = new ArrayList<>();

			for (int i = 0; i < truckSize; i++) {
				Dto.TruckDto truck = trucks.get(i);
				Dto.CommandDto command = new Dto.CommandDto(truck.getId(), new ArrayList<>());

				int beginLocation = truck.getId() * (locationSize / truckSize);
				int endLocation = beginLocation + (locationSize / truckSize) - 1;
				int truckPosition =
					LocationUtils.getX(truck.getLocationId(), n) + LocationUtils.getY(truck.getLocationId(), n);

				if (truck.getLocationId().equals(truckDestinations.get(truck.getId()))
					&& locations.get(truck.getLocationId()).getBikeCount() < avgBikeCount && truck.getBikeCount() > 0) {
					int diff = avgBikeCount - locations.get(truck.getLocationId()).getBikeCount();
					int unloadedBikeCount = Integer.min(diff, truck.getBikeCount());

					for (int j = 0; j < unloadedBikeCount; j++) {
						command.getCommand().add(TruckCommand.UNLOAD);
					}
				}

				if (locations.get(truck.getLocationId()).getBikeCount() > avgBikeCount
					&& truck.getBikeCount() < TRUCK_MAX_CAPACITY) {
					int diff = locations.get(truck.getLocationId()).getBikeCount() - avgBikeCount;
					int loadedBikeCount = Integer.min(diff, TRUCK_MAX_CAPACITY - truck.getBikeCount());

					for (int j = 0; j < loadedBikeCount; j++) {
						command.getCommand().add(TruckCommand.LOAD);
					}
				}

				if (truck.getBikeCount() < TRUCK_MAX_CAPACITY && command.getCommand().size() < TRUCK_MAX_COMMAND_SIZE) {

					int minDistance = Integer.MAX_VALUE;
					int destinationId = -1;

					for (int j = beginLocation; j <= endLocation; j++) {
						Dto.LocationDto location = locations.get(j);
						if (location.getBikeCount() > avgBikeCount) {
							int x = LocationUtils.getX(location.getId(), n);
							int y = LocationUtils.getY(location.getId(), n);
							int position = x + y;
							if (Math.abs(truckPosition - position) < minDistance) {
								destinationId = location.getId();
							}
						}
					}

					if (destinationId != -1) {
						int moveX = LocationUtils.getX(locations.get(destinationId).getId(), n) - LocationUtils.getX(
							truck.getLocationId(), n);
						int moveY = LocationUtils.getY(locations.get(destinationId).getId(), n) - LocationUtils.getY(
							truck.getLocationId(), n);

						for (int j = 0; j < Math.abs(moveX); j++) {
							if (moveX > 0) {
								command.getCommand().add(TruckCommand.RIGHT);
							} else if (moveX < 0) {
								command.getCommand().add(TruckCommand.LEFT);
							}
						}

						for (int j = 0; j < Math.abs(moveY); j++) {
							if (moveY > 0) {
								command.getCommand().add(TruckCommand.UP);
							} else if (moveY < 0) {
								command.getCommand().add(TruckCommand.DOWN);
							}
						}

						if (command.getCommand().size()
							< TRUCK_MAX_COMMAND_SIZE) { // 명령이 10개 이하면 도착했다는 뜻이므로 자전거를 트럭에 태운다.
							int diff = locations.get(destinationId).getBikeCount() - avgBikeCount;
							int loadedBikeCount = Integer.min(diff, TRUCK_MAX_CAPACITY - truck.getBikeCount());

							for (int j = 0; j < loadedBikeCount; j++) {
								if (command.getCommand().size() >= TRUCK_MAX_COMMAND_SIZE) {
									break;
								}
								command.getCommand().add(TruckCommand.LOAD);
							}
						}
					}
				}

				if (command.getCommand().size() < TRUCK_MAX_COMMAND_SIZE) { // 트럭의 명령이 10개 이하일 때, 자전거를 나눠주러 감
					int minDistance = Integer.MAX_VALUE;
					int minBikeCount = Integer.MAX_VALUE;
					int destinationId = -1;

					for (int j = beginLocation; j <= endLocation; j++) {
						Dto.LocationDto location = locations.get(j);
						if (location.getBikeCount() == 0) {
							if (location.getBikeCount() < minBikeCount) {
								destinationId = location.getId();
							}
						}
					}

					if (destinationId != -1) {
						truckDestinations.set(truck.getId(), destinationId);

						int moveX = LocationUtils.getX(locations.get(destinationId).getId(), n) - LocationUtils.getX(
							truck.getLocationId(), n);
						int moveY = LocationUtils.getY(locations.get(destinationId).getId(), n) - LocationUtils.getY(
							truck.getLocationId(), n);

						for (int j = 0; j < Math.abs(moveX); j++) {
							if (moveX > 0) {
								command.getCommand().add(TruckCommand.RIGHT);
							} else if (moveX < 0) {
								command.getCommand().add(TruckCommand.LEFT);
							}
						}

						for (int j = 0; j < Math.abs(moveY); j++) {
							if (moveY > 0) {
								command.getCommand().add(TruckCommand.UP);
							} else if (moveY < 0) {
								command.getCommand().add(TruckCommand.DOWN);
							}
						}

						if (command.getCommand().size() < TRUCK_MAX_COMMAND_SIZE) {
							int diff = avgBikeCount - locations.get(destinationId).getBikeCount();
							int unloadedBikeCount = Integer.min(diff, truck.getBikeCount());

							for (int j = 0; j < unloadedBikeCount; j++) {
								if (command.getCommand().size() >= TRUCK_MAX_COMMAND_SIZE) {
									break;
								}
								command.getCommand().add(TruckCommand.UNLOAD);
							}
						}
					}
				}

				commands.add(command);
			}

			Dto.SimulateResponse simulateResponse = client.simulate(authKey, commands);
			if (simulateResponse.getStatus().equals("finished")) {
				System.out.println(client.getScore(authKey).getScore());
				finished = true;
			}
		} while (!finished);
	}
}
