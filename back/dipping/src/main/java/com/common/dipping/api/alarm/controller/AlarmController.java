package com.common.dipping.api.alarm.controller;

import com.common.dipping.api.alarm.domain.dto.AlarmDto;
import com.common.dipping.api.alarm.domain.entity.Alarm;
import com.common.dipping.api.alarm.repository.AlarmRepository;
import com.common.dipping.api.alarm.service.AlarmService;
import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.api.user.repository.UserRepository;
import com.common.dipping.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "알람 목록", description = "사용자의 알람 목록을 반환")
    @GetMapping()
    public ResponseEntity<?> AlarmList(@AuthenticationPrincipal UserDetailsImpl requestUser) {

        List<AlarmDto> alarmList = alarmService.getAlarmByReceiver(requestUser);
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> alarmResult = new HashMap<>();
        result.put("code", 200);
        alarmResult.put("alarm", alarmList);
        result.put("data", alarmResult);

        return ResponseEntity.ok().body(result);
    }
    /*
    {
      "code": "200",
      "data": {
        "alarm": [
          {
            "alarmId": 0,
            "senderId": 0,
            "alarmMessage": 1,
            "alarmRead": false,
            "alarmType": ""
          }
        ]
      }
    }
    */


}
