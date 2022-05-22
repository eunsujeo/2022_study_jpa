package com.silverspoon.jpa.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem_Example extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order_Example order;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item_Example item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order_Example getOrder() {
		return order;
	}

	public void setOrder(Order_Example order) {
		this.order = order;
	}

	public Item_Example getItem() {
		return item;
	}

	public void setItem(Item_Example item) {
		this.item = item;
	}
}
