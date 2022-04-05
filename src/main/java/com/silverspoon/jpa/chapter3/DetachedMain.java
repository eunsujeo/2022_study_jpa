package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.entity.Member;

public class DetachedMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//영속
			Member findMember = em.find(Member.class, 304L);
			findMember.setName("이름바꿔");

			System.out.println("em contains member = " + em.contains(findMember));

			// 준영속
			em.detach(findMember);
			System.out.println("em contains member = " + em.contains(findMember));

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
