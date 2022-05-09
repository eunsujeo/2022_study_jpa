package com.silverspoon.jpa.chapter6.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.silverspoon.jpa.chapter3.entity.Member;

@Entity
public class Team_Chapter_3 {
	public Team_Chapter_3() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEAM_ID")
	private Long id;

	private String teamName;

	@OneToMany
	@JoinColumn(name = "TEAM_ID")
	private List<Member_Chapter_3> members = new ArrayList<>();

	// public void addMember(Member_Chapter_3 member) {
	// 	this.members.add(member);
	// 	if (member.getTeam() != this) {
	// 		member.setTeam(this);
	// 	}
	// }

	public List<Member_Chapter_3> getMembers() {
		return members;
	}

	public Team_Chapter_3(String teamName) {
		this.teamName = teamName;
	}
}
