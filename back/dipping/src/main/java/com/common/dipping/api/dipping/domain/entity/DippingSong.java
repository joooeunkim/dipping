package com.common.dipping.api.dipping.domain.entity;

import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DippingSong extends Common {

    private String songTitle;
    private String songSinger;
    private String songUrl;
    private String songImgUrl;

    // 게시판번호 연결
    @ManyToOne
    @JoinColumn(name = "dippingId")
    private Dipping dipping;
}
