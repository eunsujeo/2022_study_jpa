package com.silverspoon.jpa.chapter7.domain.compositekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ParentId.class)
public class Parent {
	@Id
	@Column(name = "PARENT_ID1")
	private String id1;

	@Id
	@Column(name = "PARENT_ID2")
	private String id2;
}
