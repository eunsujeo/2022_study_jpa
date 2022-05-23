package com.silverspoon.jpa.chapter8;

import java.lang.reflect.Member;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.chapter3.entity.Member_chapter_3;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			// Member member = em.find(Member.class, "member1");
			Member referenceMember = em.getReference(Member.class, "member1");
			referenceMember.getName();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
