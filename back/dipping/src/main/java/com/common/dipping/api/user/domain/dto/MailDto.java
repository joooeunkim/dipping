package com.common.dipping.api.user.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {

    private String email;
    private String title;
    private String message;

}