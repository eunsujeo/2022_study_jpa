package com.silverspoon.jpa.chapter7.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem_Chapter_7 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order_Chapter_7 order;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item_Chapter_7 item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order_Chapter_7 getOrder() {
		return order;
	}

	public void setOrder(Order_Chapter_7 order) {
		this.order = order;
	}

	public Item_Chapter_7 getItem() {
		return item;
	}

	public void setItem(Item_Chapter_7 item) {
		this.item = item;
	}
}
