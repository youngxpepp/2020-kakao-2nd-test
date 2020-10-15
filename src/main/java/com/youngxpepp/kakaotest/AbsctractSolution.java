package com.youngxpepp.kakaotest;

public abstract class AbsctractSolution {

	protected final KaKaoClient client;
	public static final int TRUCK_MAX_CAPACITY = 20;
	public static final int TRUCK_MAX_COMMAND_SIZE = 10;

	public AbsctractSolution() {
		this.client = KaKaoClient.getInstance();
	}

	public abstract void solve(int problemId);
}
