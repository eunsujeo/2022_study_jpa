# 프록시와 연관관계 관리

## 프록시

엔티티가 실제 사용될 때까지 데이터베이스 조회를 지연하는 방법을 **지연 로딩**이라 한다.

지연 로딩 기능을 사용하려면 실제 엔티티 객체 대신에 데이터베이스 조회를 지연할 수 있는 가짜 객체가 필요한데 이것을
**프록시 객체**라 한다.
> JPA 표준 명세는 지연로딩의 구현 방법을 JPA 구현체에 위임했다. 따라서 지금부터 설명할 내용은 하이버네이트 구현체에 대한 내용이다.

### 프록시 기초

em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회

```java
Member member=em.find(Member.class,"member1");
```

em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회

```java
Member referenceMember=em.getReference(Member.class,"member1");
```

#### 프록시의 특징

1. 프록시 클래스는 실제 클래스를 상속 받아서 만들어진다.
2. 실제 클래스와 겉모양이 같다
3. 사용하는 입장에서는 이것이 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 된다.
4. 프록시 객체는 실제 객체의 참고(target)를 보관한다.
5. 프록시 객체의 메소드를 호출하면 프록시 객체는 실제 객체의 메소드를 호출한다.

#### 프록시 객체의 초기화

아래 코드를 호출하면

1. getName();
2. 초기화요청
3. DB조회
4. 실제 엔티티 생성 및 참조 보관
5. target.getName()

```java
Member referenceMember=em.getReference(Member.class,"member1");
referenceMember.getName();
```

#### 프록시의 특징

1. 프록시 객체는 처음 사용할 때 한 번만 초기화된다.
2. 초기화 된다고 해서 프록시 객체가 실제 엔티티로 바뀌진 않고 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능하다
3. 프록시 객체는 원본 엔티티를 상속받은 객체이므로 타입 체크시에 주의해서 사용해야 한다.
4. 영속성 컨텍스트에 찾는 엔티티가 있으면 데이터베이스를 조회할 필요가 없으므로 em.getReference()를 호출해도 실제 엔티티를 반환한다.
5. 비영속/준영속 상태에서 프록시를 초기화하면 문제가 발생한다. (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)

### 프록시 확인

책 295page 참고

## 즉시 로딩과 지연 로딩

### 즉시 로딩

- 엔티티를 조회할 때 연관된 엔티티도 함께 조회한다.
- 대부분의 JPA 구현체는 즉시 로딩을 최적화하기 위해 가능하면 조인 쿼리를 사용한다.

```java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "member_id")
private Member member;
```

```java
Member member=em.find(Member.class,"member1");
Team team=member.getTeam();
```

### 지연 로딩

- 연관된 엔티티를 프록시로 조회한다. 프록시를 실제 사용할 때 초기화하면서 데이터베이스를 조회한다.

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id")
private Member member;
```

```java
Member member=em.find(Member.class,"member1");
Team team=member.getTeam(); // 프록시 객체
team.getName(); // 팀 객체 실제 사용
```

#### 즉시로딩 주의

- 모든 연관관계에 지연 로딩을 사용해라.
- 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생한다.
- 즉시 로딩은 JPQL에서 N+1문제를 일으킨다.
- @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> Lazy로 설정
- 컬렉션 즉시 로딩은 항상 외부 조인(outer join)을 사용한다.

## 영속성 전이 : CASCADE

- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶으면 사용한다.ex) 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장.
- 단일 엔티티에 종속적일 때 사용, 즉 단일 소유자일 때만 써라. 그리고 엔티티들의 lifecycle이 유사할 때 써라.
- 연관관계나 즉시/지연 로딩과는 관계가 없다.

```java

@Entity
public class Parent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parent_id")
	private Long id;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<Child> children = new ArrayList<>();
}
```

## 고아 객체
- 부모 엔티티의 컬렉션에서 자식 엔티티의 참조만 제거하면 자식 엔티티가 자동으로 삭제.
- 특정엔티티가 개인 소유할 때만 써야 한다.
- @OneToOne, @OneToMany에서만 동작
- 부모 엔티티를 통해서 자식엔티티의 관리가 필요할 때 사용.
- 도메인 주도 설계(DDD)의 Aggregate Root 개념을 구현할 때 유용.
  - ex) Parent가 Aggregate Root 이고 Chlid 는 Aggregate Root 가 관리한다. Child Repository를 만들지 않는다.

```java
@Entity
public class Parent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parent_id")
	private Long id;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Child> children = new ArrayList<>();
}

```

```java
Parent parent = em.find(Parent.class, id);
parent.getChildren().remove(0); 자식 엔티티를 컬렉션에서 제거
```