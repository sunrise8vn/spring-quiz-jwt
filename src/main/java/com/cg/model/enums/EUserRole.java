package com.cg.model.enums;

import com.cg.exception.DataInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EUserRole {
    ROLE_ADMIN("ADMIN"),
    ROLE_STAFF("STAFF"),
    ROLE_STUDENT("STUDENT");

    private final String value;

    EUserRole(String value) {
        this.value = value;
    }

    private static final Map<String, EUserRole> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(EUserRole::toString, Function.identity()));

    public static EUserRole fromString(final String name) {
        EUserRole eUserRole = NAME_MAP.get(name);
        if (null == eUserRole) {
            throw new DataInputException(String.format("'%s' has no corresponding value. Accepted values: %s", name, Arrays.asList(values())));
        }
        return eUserRole;
    }

    public String getValue() {
        return this.value;
    }
}
