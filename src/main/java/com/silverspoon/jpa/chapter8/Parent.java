package com.silverspoon.jpa.chapter8;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Parent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parent_id")
	private Long id;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Child> children = new ArrayList<>();
}
