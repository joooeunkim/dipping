package com.common.dipping.api.dipping.controller;

import com.common.dipping.api.board.domain.dto.HeartDto;
import com.common.dipping.api.dipping.domain.dto.DippingHeartDto;
import com.common.dipping.api.dipping.service.DippingHeartService;
import com.common.dipping.api.dipping.service.DippingService;
import com.common.dipping.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dipping")
@RequiredArgsConstructor
public class DippingController {

    private final DippingService dippingService;
    private final DippingHeartService dippingHeartService;

    @GetMapping
    public ResponseEntity<?> getDippingListOrDippingOne(@RequestParam(name = "dippingId", required = false) Long dippingId,
                                                        @RequestParam(name = "sort", required = false) String sort){

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerDipping(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteDipping(@Param("dippingId") Long dippingId){
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editDipping(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<?> dippingLikeUnLike(@AuthenticationPrincipal UserDetailsImpl userInfo,@RequestBody ObjectNode likeObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        DippingHeartDto dippingheartDto = mapper.treeToValue(likeObj.get("dippingLike"), DippingHeartDto.class);
        int result = -1;
        // 초기값인 경우
        result = dippingHeartService.setHeartByUserIdAndDippingId(userInfo.getId(),dippingheartDto.getDippingId());


        if (result == 1 || result == 0){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/like")
    public ResponseEntity<?> getDippingLikeList(@Param("dippingId") Long dippingId){
        List<DippingHeartDto> dippingHeartDtos = dippingHeartService.getListByDippingId(dippingId);

        Map<String, Object> result = new HashMap<String, Object>();
        if(!dippingHeartDtos.isEmpty()){
            result.put("code", 200);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("likes", dippingHeartDtos);
            result.put("data",data);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            result.put("code",201);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
}
