package com.silverspoon.jpa.chapter6.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Member_Chapter_6 {

	public Member_Chapter_6() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
	private Team_Chapter_6 team;

	@OneToOne
	@JoinColumn(name = "LOCKER_ID")
	private Locker_Chapter_6 locker;

	@ManyToMany
	@JoinTable(name = "MEMBER_PRODUCT", joinColumns = @JoinColumn(name = "MEMBER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	private List<Product_Chapter_6> products = new ArrayList<>();

	public Member_Chapter_6(String username) {
		this.username = username;
	}
}
