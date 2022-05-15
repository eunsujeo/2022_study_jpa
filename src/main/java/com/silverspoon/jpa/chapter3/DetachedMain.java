package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.chapter3.entity.Member_chapter_3;

public class DetachedMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//영속
			Member_chapter_3 findMemberChapter3 = em.find(Member_chapter_3.class, 304L);
			findMemberChapter3.setName("이름바꿔");

			System.out.println("em contains member = " + em.contains(findMemberChapter3));

			// 준영속
			em.detach(findMemberChapter3);
			System.out.println("em contains member = " + em.contains(findMemberChapter3));

			// 테스트할 때 도움이된다.
			em.clear();

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
