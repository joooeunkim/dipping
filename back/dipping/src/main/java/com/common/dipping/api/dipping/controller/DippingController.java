package com.common.dipping.api.dipping.controller;

import com.common.dipping.api.dipping.domain.dto.DippingDto;
import com.common.dipping.api.dipping.domain.dto.DippingResponseDto;
import com.common.dipping.api.dipping.domain.dto.DippingSongDto;
import com.common.dipping.api.dipping.domain.entity.Dipping;
import com.common.dipping.api.dipping.domain.entity.DippingSong;
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

import java.util.*;

@RestController
@RequestMapping("/api/dipping")
@RequiredArgsConstructor
public class DippingController {

    private final DippingService dippingService;
    private final DippingHeartService dippingHeartService;

    @GetMapping
    public ResponseEntity<?> getDippingListOrDippingOne(@AuthenticationPrincipal UserDetailsImpl userInfo,
                                                        @RequestParam(name = "dippingId", required = false) Long dippingId,
                                                        @RequestParam(name = "sort", required = false) String sort,
                                                        @RequestParam(name = "pageNum", required = false,defaultValue = "1") int pageNum){
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> Main = new HashMap<String, Object>();
        List<Object> comment = new ArrayList<>();
        if(dippingId != null) {
            // 딥핑 단일 조회
            Dipping dipping = dippingService.getDippingOne(dippingId);

            result.put("code", 200);

            DippingResponseDto dippingResponseDto = new DippingResponseDto(dipping);
            List<Dipping> ChildDippings = dippingService.getChildByDippingId(dipping);

            dippingResponseDto.LikeAndChild(dippingHeartService.isMylikeByDippingId(userInfo.getId(),dipping)
            ,dippingHeartService.getCountByDippingId(dipping),dippingService.getCountChild(dipping));
            Main.put("item", dippingResponseDto);

            // 딥핑 음악 정보
            List<DippingSongDto> dippingSongDtos = dippingService.getDippingSongAllById(dipping);
            Main.put("music", dippingSongDtos);
            data.put("Main",Main);

            if(!ChildDippings.isEmpty()){
                for (Dipping d: ChildDippings) {
                    Map<String, Object> temp = new HashMap<String, Object>();
                    DippingResponseDto child = new DippingResponseDto(d);
                    child.LikeAndChild(dippingHeartService.isMylikeByDippingId(userInfo.getId(),d),dippingHeartService.getCountByDippingId(d),0);
                    List<DippingSongDto> childSong = dippingService.getDippingSongAllById(d);
                    temp.put("item", child);
                    temp.put("music", childSong);
                    comment.add(temp);
                }
                data.put("comment",comment);
            }
        }
        else if(sort != null){
            List<Dipping> dippings = new ArrayList<>();
            switch (sort){
                case "recent":
                    dippings = dippingService.getListByrecent(userInfo.getId(),pageNum);
                    break;
                case "trend":
                    dippings = dippingService.getTrendDippings(pageNum);
                    break;
                case "following":
                    dippings = dippingService.getFollowingDippings(userInfo.getId(),pageNum);
                    break;
            }
            if(!dippings.isEmpty()){
                result.put("code", 200);
                for (Dipping d: dippings) {
                    Map<String, Object> temp = new HashMap<String, Object>();
                    DippingResponseDto dip = new DippingResponseDto(d);
                    dip.LikeAndChild(dippingHeartService.isMylikeByDippingId(userInfo.getId(),d)
                            ,dippingHeartService.getCountByDippingId(d),dippingService.getCountChild(d));;
                    List<DippingSongDto> dipSong = dippingService.getDippingSongAllById(d);
                    temp.put("item", dip);
                    temp.put("music", dipSong);
                    comment.add(temp);
                }
                data.put("posts",comment);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청"); // 잘못된 요청
        }

        if(!data.isEmpty()){
            result.put("data",data);
        }else {
            result.put("code",201);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @PostMapping
    public ResponseEntity<?> registerDipping(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        DippingDto dippingDto = mapper.treeToValue(registerObj.get("dipping"), DippingDto.class);
        dippingDto.setUserId(userInfo.getId());
        List<DippingSongDto> dippingSongDto = Arrays.asList(mapper.treeToValue(registerObj.get("playlist"), DippingSongDto[].class));

        Dipping newDipping = dippingService.register(dippingDto);
        dippingService.registerSong(dippingSongDto, newDipping);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity deleteDipping(@RequestParam("dippingId") Long dippingId){
        boolean result = dippingService.deleteDipping(dippingId);
        if(!result){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editDipping(@AuthenticationPrincipal UserDetailsImpl userInfo, @RequestBody ObjectNode registerObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        DippingDto dippingDto = mapper.treeToValue(registerObj.get("dipping"), DippingDto.class);
        dippingDto.setUserId(userInfo.getId());
        List<DippingSongDto> dippingSongDto = Arrays.asList(mapper.treeToValue(registerObj.get("playlist"), DippingSongDto[].class));

        Dipping newDipping = dippingService.edit(dippingDto);
        dippingService.editSong(dippingSongDto, newDipping);

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
