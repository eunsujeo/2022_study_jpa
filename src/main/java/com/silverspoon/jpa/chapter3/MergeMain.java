package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.entity.Member;

public class MergeMain {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

	public static void main(String[] args) {

		Member member = createMember(333L, "회원1");
		member.setName("회원명변경"); // 준영속 상태에서 변경

		mergeMember(member);

		emf.close();
	}

	static Member createMember(long id, String userName) {
		//== 영속성 컨테스트1 시작 ==//
		EntityManager em1 = emf.createEntityManager();
		EntityTransaction tx1 = em1.getTransaction();
		tx1.begin();

		Member member = new Member();
		member.setId(id);
		member.setName(userName);

		em1.persist(member);
		tx1.commit();

		em1.close(); // 영속성 컨텍스트1 종료,

		return member;
	}

	static void mergeMember(Member member) {
		// 영속성 컨텍스트 시작
		EntityManager em2 = emf.createEntityManager();
		EntityTransaction tx2 = em2.getTransaction();

		tx2.begin();
		Member mergeMember = em2.merge(member);
		tx2.commit();

		System.out.println("member = " + member.getName());
		System.out.println("mergeMember = " + mergeMember.getName());

		System.out.println("em2 contains member = " + em2.contains(member));
		System.out.println("em2 mergeMember member = " + em2.contains(mergeMember));

		em2.close();
	}
}
