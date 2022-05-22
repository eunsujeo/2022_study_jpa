package com.silverspoon.jpa.example;

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
public class Order_Example extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member_Example member;

	@OneToMany(mappedBy = "order_item_id")
	private List<OrderItem_Example> orderItems = new ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime orderDate;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;

	@OneToOne(mappedBy = "delivery_id")
	private Delivery_Example delivery;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member_Example getMember() {
		return member;
	}

	public void setMember(Member_Example member) {
		if (this.member != null) {
			this.getMember().getOrders().remove(this);
		}
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem_Example orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public List<OrderItem_Example> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem_Example> orderItems) {
		this.orderItems = orderItems;
	}

	public Delivery_Example getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery_Example delivery) {
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
