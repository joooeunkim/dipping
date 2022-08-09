package com.common.dipping.api.search.domain.entity;

import com.common.dipping.api.user.domain.entity.User;
import com.common.dipping.common.Common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Search extends Common {

    @Column(nullable = true)
    private String word;

    @JoinColumn()
    @ManyToOne
    private User userId;
}
