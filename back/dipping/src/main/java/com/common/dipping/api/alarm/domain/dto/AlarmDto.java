package com.common.dipping.api.alarm.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmDto {

    private String senderNickname;
    private String senderProfileImgUrl;
    private String createAt;
    private String alarmMessage;
    private String alarmType;
    private Boolean alarmRead;

}
