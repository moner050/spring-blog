package com.fastcampus.biz.domain;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DisplayTypeConverter implements AttributeConverter<DisplayType, String> {

	// Enum -> DB
	@Override
	public String convertToDatabaseColumn(DisplayType attribute) {
		if(Objects.isNull(attribute)) return null;
		
		return attribute.getName();
	}

	// DB -> Enum
	@Override
	public DisplayType convertToEntityAttribute(String dbData) {
		try {
			return DisplayType.ofName(dbData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
