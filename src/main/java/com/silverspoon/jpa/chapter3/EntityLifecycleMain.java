package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.chapter3.entity.Member;

public class EntityLifecycleMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//비영속
			Member member = new Member(304L, "member300");

			System.out.println("비영속 =================================================");
			//
			// // 영속
			// em.persist(member);
			//
			// System.out.println("영속 ==================================================");

			//준영속
			// em.detach(member);
			//
			// System.out.println("준영속 ==================================================");

			// System.out.println("member.Name : " + member.getName());

			//삭제
			// Member findMember = em.find(Member.class, 304L);
			// em.remove(findMember);

			System.out.println("삭제 ==================================================");

			tx.commit();


		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
