# Auth-Todo-App

Spring Boot를 기반으로 MySQL을 사용하는 간단한 Task 관리 애플리케이션입니다.
개발에서 배포까지의 과정을 경험하고 CI/CD를 통한 배포 과정을 이해하며 Docker와 AWS EC2를 실습하기 위한 목적으로 진행한 작은 프로젝트입니다.

---

## 목차
1. [개요](#개요)
2. [기능](#기능)
3. [시스템 요구 사항](#시스템-요구-사항)
4. [설치 및 설정](#설치-및-설정)
5. [Docker 사용법](#docker-사용법)
6. [AWS 배포](#aws-배포)
7. [문제 해결](#문제-해결)
8. [API 엔드포인트](#api-엔드포인트)
9. [라이선스](#라이선스)

---

## 개요
- **Spring Boot**: 백엔드 개발
- **MySQL**: 관계형 데이터베이스
- **Docker**: 컨테이너화
- **AWS EC2**: 배포 및 호스팅

---

## 기능
- 사용자 인증(JWT) (회원가입/로그인)
- Task 생성, 조회, 수정, 삭제 (CRUD)
- RESTful API 설계
- Docker 기반 배포 지원

---

## 시스템 요구 사항
- **Java**: 17 이상
- **Docker**: 최신 버전
- **AWS EC2**: Amazon Linux 2023 (또는 호환 환경)
- **MySQL Workbench** (선택 사항, 데이터베이스 관리용)

---

## 설치 및 설정

### 1. 프로젝트 클론
```bash
git clone https://github.com/<your-repository>/auth-todo-app.git
cd auth-todo-app
```

### 2. 설정 파일 수정
`application.properties` 또는 `application.yml` 파일에서 MySQL 정보를 수정.
```properties
spring.datasource.url=jdbc:mysql://<mysql-container-ip>:3306/task_manager
spring.datasource.username=root
spring.datasource.password=<your-password>
```

### 3. 프로젝트 빌드
```bash
./gradlew build
```

---

## Docker 사용법

### 1. Docker 이미지 빌드
Spring Boot 애플리케이션의 Docker 이미지를 빌드합니다:
```bash
docker build -t auth-todo-app .
```

### 2. MySQL 컨테이너 실행
MySQL 컨테이너를 실행합니다:
```bash
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=<your-password> -e MYSQL_DATABASE=task_manager -p 3306:3306 -d mysql:latest
```

### 3. Spring Boot 컨테이너 실행
빌드한 이미지를 사용해 애플리케이션 컨테이너를 실행합니다:
```bash
docker run --name auth-todo-app -p 8080:8080 --link mysql-container:mysql -d auth-todo-app
```

---

## AWS 배포

### 1. EC2 설정
- Amazon Linux 2023 EC2 인스턴스를 시작합니다.
- 보안 그룹 설정에서 8080, 3306 포트를 열어주세요.
- Docker 설치:

```bash
sudo yum update -y
sudo yum install docker -y
sudo systemctl start docker
sudo usermod -aG docker ec2-user
```

### 2. 컨테이너 배포
- Docker Hub에서 이미지 가져오기:

```bash
docker pull <your-docker-hub-username>/auth-todo-app
docker pull mysql:8.0
```

- MySQL 컨테이너 실행:

```bash
docker run --name mysql-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=<your-password> -e MYSQL_DATABASE=task_manager -d mysql:8.0
```

- Auth-Todo-App 컨테이너 실행:

```bash
docker run --name auth-todo-app -p 8080:8080 --link mysql-container:mysql -d <your-docker-hub-username>/auth-todo-app
```

---

## 문제 해결

### MySQL 연결 문제
1. MySQL 컨테이너 실행 여부 확인:

```bash
docker ps
```

2. MySQL 연결 테스트:

```bash
docker exec -it mysql-container mysql -u root -p
```

3. `application.properties`에 MySQL IP 및 자격 정보를 올바르게 입력했는지 확인하세요.

### 애플리케이션 접근 불가
1. 로그에서 에러 확인:

```bash
docker logs auth-todo-app
```

2. AWS EC2 보안 그룹이 8080, 3306 포트를 허용하는지 확인하세요.

### 네트워크 문제
1. Docker 네트워크 설정 확인:

```bash
docker network inspect bridge
```

2. 필요 시 Docker 재시작:

```bash
sudo systemctl restart docker
```

---

## API 엔드포인트

### 인증
- **POST** `/auth/signup` - 회원가입
- **POST** `/auth/login` - 로그인 후 JWT 반환

### Task 관리
- **POST** `/task` - 새로운 Task 생성 (JWT 필요)
- **GET** `/task` - 모든 Task 조회 (JWT 필요)
- **PUT** `/task/{id}` - Task 수정 (JWT 필요)
- **DELETE** `/task/{id}` - Task 삭제 (JWT 필요)

---
