# 엔티티 매핑

- 객체와 테이블 매핑 : @Entity, @Table
- 기본 키 맵핑 : @Id
- 필드와 컬럼 매핑 : @Column
- 연관관계 매핑 : @ManyToOne, @JoinColumn

## @Entity

- @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
- 테이블과 매핑할 클래스틑 @Entity 어노테이션을 반드시 붙여야 한다.

> 주의
> - 기본 생성자는 필수다.(파라미터가 없는 public 또는 protected 생성자).
> - final 클래스, enum, interface, inner 클래스에는 사용할 수 없다.
> - 저장할 필드에 final을 사용하면 안 된다.

## @Table

- @Table은 엔티티와 매핑할 테이블 지정

## 다양한 매핑 사용

```java

@Entity
@Table(name = "MEMBER")
public class Member_Chapter_4 {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String username;

	@Column(name = "AGE")
	private Integer age;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	private LocalDateTime createDate;

	@Lob
	private String description;
}
```

## 데이터베이스 스키마 자동 생성 & DDL 생성 기능

- 책 125page ~ 129page 참고(실제론 위험하기 때문에 사용하지 않는다. 참고만 하자.)
- DDL 생성 기능은 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.

## 기본 키 매핑

```java

@Entity
@Table(name = "MEMBER")
public class Member_Chapter_4 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
}
```

1. 직접 할당 : 기본 키를 애플리케이션에서 직접 할당한다. @Id만 사용
2. 자동 생성(@GeneratedValue)
    1. IDENTITY: 데이터베이스에 위임, MYSQL
    2. SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE, @SequenceGenerator 필요
    3. TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용, @TableGenerator 필요, 성능이 안좋다(다음 값으로 증가시키기 위해 UPDATE 쿼리를 사용한다)
    4. AUTO: 방언에 따라 자동 지정, 기본값 

## 권장하는 식별자 전략
- 기본 키 제약 조건: null 아님, 유일, 변하면 안된다.
- 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
- 예를 들어 주민등록번호도 기본 키로 적절하지 않다.
- **권장: Long형 + 대체키 + 키 생성전략 사용**
