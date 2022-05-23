package com.silverspoon.jpa.chapter9;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
