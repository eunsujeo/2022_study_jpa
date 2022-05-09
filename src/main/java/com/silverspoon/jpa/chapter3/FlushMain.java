package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.chapter3.entity.Member;

public class FlushMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//region 직접호출
			Member member = new Member(1001L, "member1000");
			em.persist(member);

			// 1차 캐시가 지워지지 않는다. 쓰기 지연 SQL 저장소에 있던 등록/수정/삭제 쿼리가 데이터베이스에 반영되는 과정
			em.flush();

			System.out.println("=====================================");
			//endregion

			//region JPQL 쿼리 실행시 플러시 자동 호출
			// Member member1001 = new Member(10004L, "member1001");
			// Member member1002 = new Member(10005L, "member1002");
			// Member member1003 = new Member(10006L, "member1003");
			//
			// em.persist(member1001);
			// em.persist(member1002);
			// em.persist(member1003);
			//
			// TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
			// List<Member> members = query.getResultList();
			// for (Member member : members) {
			// 	if (member.getId().equals(10005L)) {
			// 		System.out.println("Member1001 id : " + member.getId());
			// 	}
			// }
			// TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
			// List<Product> products = query.getResultList();

			//endregion
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
