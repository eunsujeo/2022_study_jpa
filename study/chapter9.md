# 값 타입
- 기본값 타입
  - 자바 기본 타입(예: int, double)
  - 래퍼 클래스(예:Integer)
  - String
- 임베디드 타입(복합 값 타입)
- 컬렉션 값 타입

## 기본값 타입
- 생명주기를 엔티티에 의존
  - Member를 삭제하면 name, age도 함께 삭제
- 값 타입을 공유하면 안 된다. 
  - 나의 이름 변경시 다른 회원의 이름도 변경 되면 안 된다.

```java
@Entity
public class Member {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private int age;
}

```

## 임베디드 타입(복합 값 타입)
- 새로운 값 타입을 직접 정의해서 사용할 수 있다.
- 직접 정의한 임베디드 타입도 int, String처럼 값이다.

### 임베디트 사용
- @Embeddable : 값 타입을 정의하는 곳에 표시
- @Embedded : 값 타입을 사용하는 곳에 표시

```java
@Entity
public class Member_Embedded_Type {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String name;

	@Embedded
	private Period workPeriod;

	@Embedded
	private Address address;
}
```

```java
@Embeddable
public class Period {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
```

### 임베디드 장점
- 재사용
- 높은 응집도
- Period.isWork()처럼 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있음.
- 임베디드 타입을 포함한 모든 값 타입은 엔티티의 생명주기에 의존하므로 엔티티와 임베디드 타입의 관계를 UML로 표현하면 **컴포지션(Composition) 관계**가 된다.

### 임베디드 타입과 테이블 맵핑
- 임베디드 타입은 엔티티의 값일 뿐읻다.
- 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
- 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능하다.
- 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많다.

### 임베티드 타입과 연관관계
- 임베디드 타입은 값 타입을 포함하거나 엔티티를 참조할 수 있다.

### @AttributeOverride: 속성 재정의
- 임베디드 타입에 정의한 매핑정보를 재정의할 때 사용한다.

```java
@Embedded
@AttributeOverrides({
    @AttributeOverride(name="city", column = @Column(name = "COMPANY_CITY")),
    @AttributeOverride(name="street", column = @Column(name = "COMPANY_STREET")),
    @AttributeOverride(name="zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
})
private Address companyAddress;
```

### 임베디드 타입과 null
- 임베디드 타입이 null이면 매핑한 컬럼 값은 모두 null인 된다.