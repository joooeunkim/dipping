package com.common.dipping.api.dipping.domain.dto;

import com.common.dipping.api.dipping.domain.entity.DippingSong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DippingSongDto {

    private Long songId;
    private Long dippingId;
    private String songTitle;
    private String songSinger;
    private String songUrl;
    private String songImgUrl;

    public DippingSongDto(DippingSong dippingSong){
        this.songId = dippingSong.getId();
        this.dippingId = dippingSong.getDipping().getId();
        this.songTitle = dippingSong.getSongTitle();
        this.songSinger = dippingSong.getSongSinger();
        this.songUrl = dippingSong.getSongUrl();
        this.songImgUrl = dippingSong.getSongImgUrl();
    }
}
