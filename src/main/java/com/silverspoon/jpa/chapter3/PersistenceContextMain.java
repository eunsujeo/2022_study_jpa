package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.chapter3.entity.Member;

public class PersistenceContextMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//region 1차캐시
			//비영속
			Member member = new Member(511L, "JPA");

			// 1차 캐시에 저장됨(영속)
			em.persist(member);

			// 1차 캐시에서 조회
			Member findMember = em.find(Member.class, 502L);

			// Database select쿼리가 나갈까?
			System.out.println("findMember.id = " + findMember.getId());
			System.out.println("findMember.name = " + findMember.getName());

			// Query는 몇 번 나갈까?
			// Member findMember1 = em.find(Member.class, 501L);
			// Member findMember2 = em.find(Member.class, 501L);
			//endregion

			//region 동일성 보장
			// Member findMember3 = em.find(Member.class, 501L);
			// Member findMember4 = em.find(Member.class, 501L);
			// System.out.println("result : " + (findMember3 == findMember4));
			//endregion

			//region 트랜잭션을 지원하는 쓰기 지연
			// Member member1 = new Member(602L, "A");
			// Member member2 = new Member(603L, "B");
			//
			// em.persist(member1);
			// em.persist(member2);
			// //여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
			//
			// System.out.println("====================================");
			// //커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
			//endregion

			//region 변경 감지(Dirty Checking)
			// Member member = em.find(Member.class, 501L);
			// member.setName("BOOK2");
			//endregion

			//region 엔티티 삭제
			// Member member = em.find(Member.class, 501L);
			// em.remove(member);
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
