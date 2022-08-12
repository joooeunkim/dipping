package com.common.dipping.api.dipping.domain.entity;

import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dipping extends Common {

    private String dippingTitle;
    private String dippingContent;
    private Boolean openDipping;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentDipping",columnDefinition = "bigint default 0")
    private Dipping parentDipping;

    @OneToMany(mappedBy = "parentDipping")
    private List<Dipping> childDipping;

    @OneToMany(mappedBy = "dipping", cascade = {CascadeType.REMOVE})
    private List<DippingSong> dippingSongs = new ArrayList<>();

    @OneToMany(mappedBy = "dipping", cascade = {CascadeType.REMOVE})
    private List<DippingHeart> dippingHearts = new ArrayList<>();
}
