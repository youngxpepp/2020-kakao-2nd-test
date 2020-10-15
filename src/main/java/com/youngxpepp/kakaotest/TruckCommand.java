package com.youngxpepp.kakaotest;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TruckCommand {

	NONE(0),
	UP(1),
	RIGHT(2),
	DOWN(3),
	LEFT(4),
	LOAD(5),
	UNLOAD(6);

	@JsonValue
	private Integer command;

	TruckCommand(int command) {
		this.command = command;
	}
}
