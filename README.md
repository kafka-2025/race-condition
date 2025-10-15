# 🚀 Sample API

## 📡 REST API 개요

이 프로젝트는 다중화(다중 인스턴스, 분산 서버) 환경에서 발생할 수 있는 Race Condition 문제를 해결하는 방법을 다루는 구현 예제입니다.
Springboot 기반으로 구현되었으며, 은행 계좌의 잔액 조회, 입금, 출금 기능을 REST API 형태로 제공합니다.

### ✅ 동시성 제어 방식

다음 세 가지 접근 방식으로 데이터 정합성과 동시성 제어를 보장합니다.
- JPA 비관적 락(Pessimistic Lock)
- 낙관적 락(Optimistic Lock)
- Redis 분산 락(Distributed Lock)


### ✅ 주요 기술 스택

- Java 17+
- Spring Boot 3.x
- JPA
- Mysql
- H2 Database (파일 기반, MySQL 호환 모드)


### ✅ API 목록

| HTTP 메서드 | 경로                             | 설명       | 요청 바디            | 응답 데이터                 | Validation |
|-------------|--------------------------------|----------|------------------|----------------------------|------------|
| GET         | /accounts/{accountId}          | 계좌 잔액 조회 | 없음               | `List<AccountDTO>`             | 없음       |
| POST        | /accounts/{accountId}/deposit  | 입금       | `TransactionRequest`  | 갱신된 `AccountDTO`          | TransactionGroup |
| POST        | /accounts/{accountId}/withdraw | 출금       | `TransactionRequest`   | 갱신된 `AccountDTO`          | TransactionGroup |
| GET         | /accounts/{accountId}/transactions         | 입출금 내역 조회 | 없음               | `List<TransactionDTO>`             | 없음       |

---

### ✅ 요청 예시

#### 샘플 등록 (POST /samples)

```json
{
  "name": "샘플"
}
