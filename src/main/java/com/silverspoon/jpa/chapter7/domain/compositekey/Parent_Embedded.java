package com.silverspoon.jpa.chapter7.domain.compositekey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Parent_Embedded {
	@EmbeddedId
	private Parent_EmbeddedId id;
	private String name;
}
