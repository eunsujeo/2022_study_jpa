package com.silverspoon.jpa.chapter7.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Order_Chapter_7 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member_Chapter_7 member;

	@OneToMany(mappedBy = "order_item_id")
	private List<OrderItem_Chapter_7> orderItems = new ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime orderDate;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;

	@OneToOne(mappedBy = "delivery_id")
	private Delivery_Chapter_7 delivery;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member_Chapter_7 getMember() {
		return member;
	}

	public void setMember(Member_Chapter_7 member) {
		if (this.member != null) {
			this.getMember().getOrders().remove(this);
		}
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem_Chapter_7 orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public List<OrderItem_Chapter_7> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem_Chapter_7> orderItems) {
		this.orderItems = orderItems;
	}

	public Delivery_Chapter_7 getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery_Chapter_7 delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
