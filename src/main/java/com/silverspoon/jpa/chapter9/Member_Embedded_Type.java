package com.silverspoon.jpa.chapter9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Member_Embedded_Type {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String name;

	@Embedded
	private Period workPeriod;

	@Embedded
	private Address homeAddress;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="city", column = @Column(name = "COMPANY_CITY")),
		@AttributeOverride(name="street", column = @Column(name = "COMPANY_STREET")),
		@AttributeOverride(name="zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
	})
	private Address companyAddress;

	@ElementCollection
	@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "member_id"))
	@Column(name = "FOOD_NAME")
	private Set<String> favoriteFoods = new HashSet<>();

	// @ElementCollection
	// @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "member_id"))
	// private List<Address> addressHistory = new ArrayList<>();


	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "member_id")
	private List<AddressEntity> addressHistory = new ArrayList<>();
}
