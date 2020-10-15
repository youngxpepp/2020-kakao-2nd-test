package com.youngxpepp.kakaotest;

public class LocationUtils {

	static int getX(int locationId, int n) {
		return locationId / n;
	}

	static int getY(int locationId, int n) {
		return locationId - getX(locationId, n) * n;
	}
}
