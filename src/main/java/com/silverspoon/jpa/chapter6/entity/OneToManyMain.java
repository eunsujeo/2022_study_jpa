package com.silverspoon.jpa.chapter6.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToManyMain {

	public static void main(String[] args) {
		testSave();
	}

	public static void testSave() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			Member_Chapter_6 member = new Member_Chapter_6("member1");
			Member_Chapter_6 member2 = new Member_Chapter_6("member2");

			Team_Chapter_6 team = new Team_Chapter_6("team1");
			team.getMembers().add(member);
			team.getMembers().add(member2);

			em.persist(member);
			em.persist(member2);
			em.persist(team);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

	}
}
