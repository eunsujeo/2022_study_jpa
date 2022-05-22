package com.silverspoon.jpa.chapter7.domain.compositekey;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class Parent_EmbeddedId implements Serializable {
	@Column(name = "PARENT_ID1")
	private String id1;

	@Column(name = "PARENT_ID2")
	private String id2;

	public Parent_EmbeddedId() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Parent_EmbeddedId that = (Parent_EmbeddedId)o;
		return Objects.equals(id1, that.id1) && Objects.equals(id2, that.id2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id1, id2);
	}
}
