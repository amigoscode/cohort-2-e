package com.amigoscode.external.storage.version;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StateConverter implements AttributeConverter<State, String> {

    @Override
    public String convertToDatabaseColumn(State state) {
        return state.name();
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        return State.valueOf(dbData);
    }
}
