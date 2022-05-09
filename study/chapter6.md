# 다양한 연관관계 매핑

## 연관관계 매핑시 고려사항 3가지

### 다중성
연관관계가 있는 두 엔티티가 일대일 관계인지 일대다 관계인지 다중성을 고려해야 한다.
- 다대일(@ManyToOne)
- 일대다(@OneToMany)
- 일대일(@OneToOne)
- 다대다(@ManyToMany)

### 단방향, 양방향

- 테이블
  - 외래 키 하나로 양쪽 조인 가능
  - 사실 방향이라는 개념이 없음
- 객체
  - 참조용 필드가 있는 쪽으로만 참조 가능
  - 한쪽만 참조하면 단방향
  - 양쪽이 서로 참조하면 양방향

### 연관관계의 주인
- 데이터베이스는 외래 키 하나로 두 테이블이 연관관계를 맺는다.
- 객체 양방향 관계를 A->B, B->A 2곳에서 서로를 참조한다.
- 두 객체 연관관계 중 하나를 정해서 데이터베이스 외래 키를 관리하는데 이것을 연관관계의 주인이라 한다.
- 연관관계의 주인
  - mappedBy 속성을 사용 x
  - 외래 키를 관리
- 주인의 반대편
  - 외래 키를 변경할 수 없고 읽기만 가능하다

## 다대일(N:1)
- 객체 양방향 관계에서 연관관계의 주인은 항상 다쪽이다.(테이블 외래키도 다쪽에 있기에)

```java
@Entity
public class Member_Chapter_3 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team_Chapter_3 team;
}
```

## 다대일 양방향(N:1, 1:N)
- 양방향은 외래 키가 있는 쪽이 연관관계의 주인이다.
- 양방향 연관관계를 항상 서로를 참조해야 한다.
```java
@Entity
public class Team_Chapter_3 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEAM_ID")
	private Long id;

	@OneToMany(mappedBy = "team")
	private List<Member_Chapter_3> members = new ArrayList<>();

	public void addMember(Member_Chapter_3 member) {
		this.members.add(member);
		if (member.getTeam() != this) {
			member.setTeam(this);
		}
	}

	public List<Member_Chapter_3> getMembers() {
		return members;
	}
}
```

## 일대다
### 일대다 단방향(1:N)
- 일대다 단반향은 일대다에서 일(1)이 연관관계의 주인이다.
- 테이블 일대다 관계는 항상 다(N)쪽에 외래 키가 있다.
- 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조이다
- @JoinColumn을 꼭 사용해야 한다. 그렇지 않으면 조인 테이블 방식을 사용한다(중간 테이블을 하나 추가함)

### 일대다 단방향 단점
- 엔티티가 관리하는 외래 키가 다른 테이블에 있다.
- 연관관계 관리를 위해 추가로 UPDATE SQL을 실행한다.(성능문제)
- 테이블이 늘어날수록 관리도 부담스러워진다.
- 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자.
```
insert 
into
    Team_Chapter_3
    (teamName) 
values
    (?);

update
  Member_Chapter_3 
set
  TEAM_ID=? 
where
  id=?
```

### 일대다 양방향(1:N, N:1)
- 일대다 양방향 매핑은 존재하지 않는다.
- 관계형 데이터베이스의 특성상 일대다, 다대일 관계 모두 다(N) 쪽에 외래 키가 있다.
- 불가능은 아님., 읽기 전용 필드를 사용해서 양방향처럼 사용하면 된다.
- @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
- 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자.
```java
@Entity
public class Member_Chapter_3 {

	public Member_Chapter_3() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
	private Team_Chapter_3 team;

	public Member_Chapter_3(String username) {
		this.username = username;
	}
}
```
## 일대일(1:1)
- 일대일 관계는 그 반대도 일대일 관계다.
- 일대일 관계는 주 테이블이나 대상 테이블 둘 중 어느 곳이나 외래키를 가질 수 있다.
- 외래 키에 데이터베이스 유니크(UNI) 제약조건 추가한다.

### 주 테이블에 외래 키
- 장점
  - 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능하다.
- 단점
  - 값이 없으면 외래 키에 null 이 허용되어야 한다.
- 일대일 단방향
  - 다대일(@ManyToOne) 단방향 매핑과 유사 
- 일대일 양방향
  - 다대일 양방향 매핑처럼 외래 키가 있는 곳이 연관관계의 주인
  - 반대편은 mappedBy 적용
  
### 대상 테이블에 외래 키
- 장점
  - 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조를 유지할 수 있다.
- 단점
  - 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩된다.(데이터가 있는지 확인해야 하기 때문에)
- 단방향 관계 JPA에서 지원하지 않는다.
- 양방향 관계는 지원
- 일대일 주테이블에 외래 키 양방향과 매핑 방법은 같다.

## 다대다(N:N)
- 관계형 데이터베이스는 정규환된 테이블 2개로 다대다 관계를 표현할 수 없다.
- 중간에 연결테이블을 추가하여 일대다, 다대일 관계로 풀어낼 수 있다.
- 객체는 컬렉션을 사용해서 객체 2개로 다대다 관계가 가능하다.
- @ManyToMany 사용, @JoinTable로 연결 테이블 지정
```java
@ManyToMany
@JoinTable(name = "MEMBER_PRODUCT", joinColumns = @JoinColumn(name = "MEMBER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
private List<Product_Chapter_3> products = new ArrayList<>();
```
### 다대다:한계
- 연결 테이블을 자동으로 처리해주므로 도메인 모델이 단순해지고 여러 가지로 편리하다.
- 연결 테이블이 단순히 연결만 하고 끝나지 않고 요구사항에 따라 추가 데이터를 넣을 수 있다.

### 다대다:극복, 연결 엔티티 사용
- 연결 테이블용 엔티티를 추가한다.
- @ManyToMany -> @OneToMany, @ManyToOne 으로 사용.
- 복합 기본키를 사용하는 방법은 복잡하기 때문에 새로운 키본 키를 사용하자.