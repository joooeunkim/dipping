package com.common.dipping.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER"),
    ROLe_ADMIN("ROLE_ADMIN");

    private final String value;
}
