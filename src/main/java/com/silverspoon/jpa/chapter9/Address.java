package com.silverspoon.jpa.chapter9;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String city;
	private String street;
	private String zipcode;
}