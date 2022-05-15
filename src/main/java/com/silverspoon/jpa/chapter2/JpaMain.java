package com.silverspoon.jpa.chapter2;

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

			List<Member_chapter_3> result = em.createQuery("select m from Member_chapter_3 as m", Member_chapter_3.class)
				.setFirstResult(5)
				.setMaxResults(8)
				.getResultList();

			for (Member_chapter_3 memberChapter3 : result) {
				System.out.println("member.name = " + memberChapter3.getName());
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
