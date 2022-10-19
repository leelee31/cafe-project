# 커피 주문 서비스

## 개발 환경
* Java 11
* Spring Boot 2.7.0
* h2 Database
* Gradle
* JPA
* JUnit5

## 서비스 설명
* 메뉴 생성 및 조회
* 주문 시 포인트로 결제
* 주문이 완료되면 주문 내역을 전송
* 주문 내역으로 인기 메뉴 조회

## API

### 메뉴 
* 생성 
  * POST /menu
* 목록 조회 
  * GET /menu
* 메뉴명으로 단건 조회 
  * GET /menu/{name}

### 포인트
* 생성 
  * POST /point 
* 충전 
  * POST /point/charge

### 주문
* POST /order

### 인기 메뉴 목록 조회
* GET /popular/menu


## 개선 필요 사항
* 커피 주문에 의존성이 크므로 분리 필요
* 예외 처리 기능 개선 -> 예외 시 사용자에게 어떤 메세지를 반환할 것인가?

- - -
## 학습 
### Optional  
https://onibmag.tistory.com/entry/%EC%9E%90%EB%B0%94-null-%EB%8C%80%EC%8B%A0-Optional-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
