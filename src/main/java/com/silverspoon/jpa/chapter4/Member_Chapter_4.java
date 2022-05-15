package com.silverspoon.jpa.chapter4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.silverspoon.jpa.chapter6.entity.Locker_Chapter_3;
import com.silverspoon.jpa.chapter6.entity.Product_Chapter_3;
import com.silverspoon.jpa.chapter6.entity.Team_Chapter_3;

@Entity
@Table(name = "MEMBER")
public class Member_Chapter_4 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "NAME")
	private String username;

	@Column(name = "AGE")
	private Integer age;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDate;

	@Lob
	private String description;
}
