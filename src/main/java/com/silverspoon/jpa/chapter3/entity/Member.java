package com.silverspoon.jpa.chapter3.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Member {

	@Id
	private Long id;
	private String name;
	private Integer age;

	public Member() {
	}

	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
