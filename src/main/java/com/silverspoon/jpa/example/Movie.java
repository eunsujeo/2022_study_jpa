package com.silverspoon.jpa.example;

import javax.persistence.Entity;

@Entity
public class Movie extends Item_Example {
	private String director;
	private String actor;
}
