package com.silverspoon.jpa.chapter3;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.entity.Member;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//영속
			Member member = em.find(Member.class, 150L);
			em.remove(member);

			tx.commit();


			//비영속
			// Member member = new Member();
			// member.setId(4L);
			// member.setName("HelloJpa");
			//
			// //영속
			// System.out.println("=== Before ===");
			// em.persist(member);
			// System.out.println("=== After ===");

			// Member findMember = em.find(Member.class, 4L);
			// Member findMember2 = em.find(Member.class, 4L);
			// System.out.println("result = " + (findMember == findMember2));

			// System.out.println("findMember.id = " + findMember.getId());
			// System.out.println("findMember.name = " + findMember.getName());


		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
