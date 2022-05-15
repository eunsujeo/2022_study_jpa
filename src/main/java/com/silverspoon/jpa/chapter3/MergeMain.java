package com.silverspoon.jpa.chapter3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.silverspoon.jpa.chapter3.entity.Member_chapter_3;

public class MergeMain {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

	public static void main(String[] args) {

		Member_chapter_3 memberChapter3 = createMember(333L, "회원1");
		memberChapter3.setName("회원명변경"); // 준영속 상태에서 변경

		mergeMember(memberChapter3);

		emf.close();
	}

	static Member_chapter_3 createMember(long id, String userName) {
		//== 영속성 컨테스트1 시작 ==//
		EntityManager em1 = emf.createEntityManager();
		EntityTransaction tx1 = em1.getTransaction();
		tx1.begin();

		Member_chapter_3 memberChapter3 = new Member_chapter_3();
		memberChapter3.setId(id);
		memberChapter3.setName(userName);

		em1.persist(memberChapter3);
		tx1.commit();

		em1.close(); // 영속성 컨텍스트1 종료,

		return memberChapter3;
	}

	static void mergeMember(Member_chapter_3 memberChapter3) {
		// 영속성 컨텍스트 시작
		EntityManager em2 = emf.createEntityManager();
		EntityTransaction tx2 = em2.getTransaction();

		tx2.begin();
		Member_chapter_3 mergeMemberChapter3 = em2.merge(memberChapter3);
		tx2.commit();

		System.out.println("member = " + memberChapter3.getName());
		System.out.println("mergeMember = " + mergeMemberChapter3.getName());

		System.out.println("em2 contains member = " + em2.contains(memberChapter3));
		System.out.println("em2 mergeMember member = " + em2.contains(mergeMemberChapter3));

		em2.close();
	}
}
