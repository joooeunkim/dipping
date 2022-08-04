package com.common.dipping.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tagSeq;
	private String content;
	
	@Builder
	public Tag(String content) {
		this.content = content;
	}
	
	@OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
	private List<PostTag> postTags = new ArrayList<>();

}
