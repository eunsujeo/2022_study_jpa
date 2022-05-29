# 값 타입
JPA의 데이터 타입을 가장 크게 분류하면 엔티티 타입과 값 타입으로 나눌 수 있다.
- 엔티티 타입 
  - @Entity로 정의하는 객체
  - 식별자를 통해 지속해서 추적할 수 있다.
- 값 타입은 int, Integer, String처럼 단순한 값으로 사용하는 자바 기본 타입이나 객체를 말한다.
  - 식별자가 없고 숫자나 문자같은 속성만 있으므로 추절할 수 없다.


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
> 엔티티는 고유될수 있으므로 참조한다고 표현하고, 값 타입은 특정 주인에 소속되고 논리적인 개념상 공유되지 않으므로 포함한다고 표현했다. 

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

## 값 타입과 불변 객체
값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념이다. 따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다.

### 값 타입 공유 참조
- 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험하다.
- 부작용(side effect) 가 발생함.
- member만 newCity로 변경되길 원했지만 member2도 변경된다.(member, member2 의 각각 update SQL이 실행된다.)
 ```java
Address address = new Address("city", "street", "10000");
Member member = nwe Member();
member.setUsername("member1");
member.setHomeAddress(address);
em.persist(member);

Member member2 = nwe Member();
member2.setUsername("member2");
member2.setHomeAddress(address);
em.persist(member2);

member.getHomeAddress().setCity("newCity");
```

### 값 타입 복사
값 타입의 실제 인스턴스인 값을 공유하는 것은 위험하다. 대신에 값(인스턴스)을 복사해서 사용해야 한다.

### 객체 타입의 한계
- 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
- 문제는 임베디드 타입처럼 **직접 정의한 값 타입은 자바의 기본타입이 아니라 객체 타입**이다.
- 자바 기본 타입에 값을 대입하면 값을 복사한다.
- **객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.**
  - 누군가 실수 할 수 있음.
- **객체의 공유 참조는 피할 수 없다.**

### 불변 객체
객체를 불변하게 만들면 값을 수정할 수 없으므로 부작용을 원천 차단할 수 있다. 따라서 **값 타입은 될 수 있으면 불변 객체(immutable Object)로 설계해야 한다.**

- 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 된다.
- 참고: Intger, String은 자바가 제공하는 대표적인 불변 객체다.

## 값 타입의 비교
인스턴스가 달라도 그 안에 값이 값으면 같은 것으로 봐야 한다.
하지만 address1, address2 객체 타입은 서로 다른 인스턴스이므로 false로 나온다.
```java
int a = 10;
int b = 10;
Sytem.out.println("a == b"); //true

Address address1 = new Address("city", "street", "10000");
Address address2 = new Address("city", "street", "10000");

Sytem.out.println(address1.equals(address2)); //false
```

- 동일성(Identity) 비교 : 인스턴스의 참고 값을 비교, == 사용
- 동등성(Equivalence) 비교 : 인스턴스의 값을 비교, equals 사용
- 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야 한다.
- equals() 메소드를 값을 비교하도록 재정의 해야 한다.
> 자바에서 equals()를 재정의하면 hashCode()도 재정의하는 것이 안전하다. 그렇지 않으면 해시를 사용하는 컬렉션(HashSet, HashMap)이 정상 동작하지 않는다.
 
## 값 타입 컬렉션
값 타입을 하나 이상 저장하려면 컬렉션에 보관하고 @ElementCollection, @CollectionTable 어노테이션을 사용하면 된다.
```java
@ElementCollection
@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "member_id"))
@Column(name = "FOOD_NAME")
private Set<String> favoriteFoods = new HashSet<>();

@ElementCollection
@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "member_id"))
private List<Address> addressHistory = new ArrayList<>();
```

- 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
- 컬렉션을 저장하기 위한 별도의 테이블이 필요하다.

### 값 타입 컬렉션 사용
책 336 ~ 338 Page 참고
> 값 타입 컬렉션은 영속성 전이(Cascade) + 고아 객체 제거(ORPHAN REMOVE) 기능을 필수로 가진다고 볼 수 있다.

### 값 타입 컬렉션의 제약사항
- 값 타입은 엔티티와 다르게 식별자 개념이 없다.
- 값을 변경하면 추적이 어렵다.
- 주의) 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
- 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 한다. null 입력 X, 중복 저장 X

### 값 타입 컬렉션의 대안
- 실무에서는 상황에 따라 값 타입 컬렉션 보다는 일대다 관계를 고려하자.
- 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용하자.
- 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션처럼 사용해라.


