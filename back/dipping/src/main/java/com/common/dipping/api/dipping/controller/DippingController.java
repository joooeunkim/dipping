package com.common.dipping.api.dipping.controller;

import com.common.dipping.api.dipping.service.DippingHeartService;
import com.common.dipping.api.dipping.service.DippingService;
import com.common.dipping.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> dippingLikeUnLike(@AuthenticationPrincipal UserDetailsImpl userInfo,@RequestBody ObjectNode registerObj) throws JsonProcessingException {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/like")
    public ResponseEntity<?> getDippingLikeList(@Param("dippingId") Long dippingId){
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
