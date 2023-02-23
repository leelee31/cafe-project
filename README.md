# 커피 주문 서비스

## 개발 목적 

객체 지향 설계, Spring Boot, JPA, 테스트 코드 학습용 프로젝트

[자세한 내용을 적은 글](https://onibmag.tistory.com/52)

## 개발 환경
* Java 11
* Spring Boot 2.7.0
* h2 Database
* Gradle
* JPA
* JUnit5
* kafka-clients

## 서비스 설명
* 커피 메뉴 생성 및 조회
* 주문 시 포인트로 결제
* 주문이 완료되면 주문 내역을 전송
* 특정 기간 내 인기 메뉴 조회

## 도메인 설계
<img width="800" alt="diagram" src="https://user-images.githubusercontent.com/45748683/196957133-7bb334fe-9a3c-49ec-a434-be15418aef2c.png">

## 프로젝트 구조 
<img width="170" alt="structure" src="https://user-images.githubusercontent.com/45748683/219075818-6571f853-98b6-4c3b-8d97-4ca13f0e52f3.png">

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

