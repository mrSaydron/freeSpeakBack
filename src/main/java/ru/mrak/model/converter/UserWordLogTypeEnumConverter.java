package ru.mrak.model.converter;

import ru.mrak.model.enumeration.UserWordLogTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserWordLogTypeEnumConverter implements AttributeConverter<UserWordLogTypeEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserWordLogTypeEnum attribute) {
        return attribute.getId();
    }

    @Override
    public UserWordLogTypeEnum convertToEntityAttribute(Integer dbData) {
        return UserWordLogTypeEnum.of(dbData);
    }
}
