package com.fastcampus.biz.domain;

import java.util.Arrays;

import com.fastcampus.biz.exception.DisplayTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DisplayType {

	TITLE("TITLE", "제목"),
	ALL("ALL", "제목 + 내용");
	
	private String type;
	private String name;

	public static DisplayType ofName(String name) throws Exception {
		return Arrays.stream(DisplayType.values())
				.filter(v -> v.getName().equals(name))
				.findAny()
				.orElseThrow(() -> new DisplayTypeException(String.format("해당하는 %s이 존재하지 않습니다.", name)));
	}
	
}
