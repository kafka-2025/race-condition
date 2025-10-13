# 🚀 Sample API

## 📡 REST API 개요

이 프로젝트는 **Spring Boot**와 **JDBC**를 사용하여 구현한 RESTful API 예제입니다.  
`Sample` 엔티티에 대해 CRUD(생성, 조회, 수정, 삭제) 기능을 제공합니다.

### ✅ 주요 기술 스택

- Java 17+
- Spring Boot 3.x
- JDBC (Java Database Connectivity)
- H2 Database (파일 기반, MySQL 호환 모드)


### ✅ API 목록

| HTTP 메서드 | 경로            | 설명           | 요청 바디                  | 응답 데이터                 | Validation |
|-------------|-----------------|----------------|---------------------------|----------------------------|------------|
| GET         | /samples        | 전체 샘플 조회 | 없음                      | `List<Sample>`             | 없음       |
| GET         | /samples/{id}   | 샘플 단건 조회 | 없음                      | `Sample`                   | 없음       |
| POST        | /samples        | 샘플 등록      | `SampleDTO` (name 필수)   | 등록된 `SampleDTO`          | Insert 그룹 |
| PUT         | /samples        | 샘플 수정      | `SampleDTO` (id, name 필수) | 수정된 `SampleDTO`          | Update 그룹 |
| DELETE      | /samples/{id}   | 샘플 삭제      | 없음                      | 없음                       | 없음       |

---

### ✅ 요청 예시

#### 샘플 등록 (POST /samples)

```json
{
  "name": "샘플"
}
