package com.common.dipping.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER");

    private final String role;
}
