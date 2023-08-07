package com.cg.model.enums;

import com.cg.exception.DataInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EQuestionType {

    SINGLE("SINGLE"),
    MULTIPLE("MULTIPLE"),
    TRUE_FALSE("TRUE_FALSE");

    private final String value;

    EQuestionType(String value) {
        this.value = value;
    }

    private static final Map<String, EQuestionType> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(EQuestionType::toString, Function.identity()));

    public static EQuestionType fromString(final String name) {
        EQuestionType questionType = NAME_MAP.get(name);
        if (null == questionType) {
            throw new DataInputException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values())));
        }
        return questionType;
    }

    public String getValue() {
        return this.value;
    }
}
