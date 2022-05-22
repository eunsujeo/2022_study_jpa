# 고급 매핑

## 상속 관계 맵핑
- 관계형 데이터베이스에는 객체지향 언어에서 다루는 상속이라는 개념이 없다.
- 슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사
- ORM에서 이야기하는 상속 관계 맵핑은 객체의 상속 구조와 데이터베이스의 슈퍼타입 서브타입 관계를 매핑하는 것

### 조인 전략
엔티티 각각을 모두 테이블로 만들고 자식 테이블이 부모 테이블의 기본 키를 받아서 기본 키 + 외래 키로 사용하는 전략
따라서 조회할 때 조인을 자주 이용한다. 객체는 타입으로 구분하지만 테이블에는 타입의 개념이 없기 때문에 타입을 구분하는 컬럼을 추가해야 한다.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Item_Example {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;
  
  private String name;
  
  private int price;
  
  private int stockQuantity;
}
```

```java
@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "album_id")
public class Album extends Item_Example{
	private String artist;
}
```

#### 조인 전략 장점
- 테이블이 정규화된다.
- 외래 키 참조 무결성 제약조건을 활용할 수 있다.
- 저장공간을 효율적으로 사용한다.
#### 조인 전략 장점
- 조회할 때 조인이 많이 사용되느로 성능이 저하될 수 있다.
- 조회 쿼리가 복잡하다
- 데이터를 등록할 INSERT SQL 을 두 번 실행된다.

### 단일 테이블 전략
테이블을 하나만 사용한다. 구분 커럼(DTYPE)으로 어떤 자식 데이터가 저장되었는지 구분한다.
@DiscriminatorColumn을 꼭 설정해 줘야 한다.(default value는 entity명이다)

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item_Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
  
    private String name;
  
    private int price;
}
```

```java
@Entity
@DiscriminatorValue("B")
public class Album extends Item_Example{
	private String artist;
}
```

#### 단일 테이블 전략 장점
- 조인이 필요 없으므로 일반적으로 조회 성능이 빠르다.
- 조회 쿼리가 단순한다.
 
#### 단일 테이블 전략 단점
- 자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다.
- 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. 그러므로 상황에 따라서는 조회 성능이 오히려 느려질 수 있다.

### 구현 클래스마다 테이블 전략
자식 엔티티마다 테이블을 만든다. 그리고 자식 테이블 각각에 필요한 컬럼이 모두 있다.
추천하지 않는 전략이다.

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item_Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
  
    private String name;
  
    private int price;
}
```

#### TABLE_PER_CLASS 장점
- 서브 타입을 구분해서 처리할 때 효과적이다.
- not null 제약조건을 사용할 수 있다.

#### TABLE_PER_CLASS 단점
- 여러 자식 테이블을 함께 조회할 때 성능이 느리다.(SQL에 UNION을 사용해야 한다)
- 자식 테이블을 통합해서 쿼리하기 어렵다.

## @MappedSuperclass
- 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할
- 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
- 참고 : @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능

## 복합 키와 식별 관계 맵핑
식별 관계 : 부모테이블의 기본 키를 내려받아서 자식 테이블의 기본 키 + 외래 키로 사용하는 관계
비식별 관계 : 부모 테이블의 기본 키를 받아서 자식 테이블의 외래 키로만 사용하는 관계

### 복합 키: 비식별 관계 맵핑
JPA에서 식별자를 둘 이상 사용할려면 별도 식별자 클래스를 만들어야 한다.
식별자 필드가 2개이상이면 별도의 식별자 클래스를 만들고 그곳에 equals와 hashCode를 구현해야 한다.
JPA는 복합키를 위해 @IdClass, @EmbeddedId 2가지 방법을 제공한다.