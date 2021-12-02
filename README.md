# Spring-boot 초기셋팅
## 개발환경
Java 백앤드 개발자가 신규 프로젝트를 생성할 때마다 
프로젝트 구조를 어떻게 할지에 대한 고민을 덜어 주기 위한
 샘플 프로젝트 입니다.

기본 기능은 Health Check, 회원가입(구글, 페이스북, 카카오, 네이버)

#### 개발 PC 
- JDK : 11.x
- IDE : Intellij
- VCS/SCM : git

#### Database (MySQL 8.x) 
- Schema : base-dev
- Charset : utf8mb4 & utf8mb4_0900_ai_ci
- Account : dev/dev999!@
- Create Query
```
CREATE SCHEMA `base-dev` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

CREATE TABLE `base-dev`.`member_master` (
  `mem_pid` int NOT NULL AUTO_INCREMENT COMMENT '회원 고유 ID',
  `login_id` varchar(100) NOT NULL COMMENT '회원 로그인 ID(Email 형식)',
  `password` varchar(100) DEFAULT NULL COMMENT '회원 로그인 비밀번호',
  `signup_sns` varchar(45) DEFAULT NULL COMMENT '회원가입 SNS (GOOGLE, KAKAO, NAVER, FACEBOOK, APPLE)',
  `nickname` varchar(100) DEFAULT NULL COMMENT '닉네임',
  `mobile_number` varchar(20) DEFAULT NULL COMMENT '모바일 번호',
  `email_auth_status` varchar(1) DEFAULT 'F' COMMENT '이메일 인증 상태 (T:인증, F:미인증)',
  `email_auth_dt` timestamp NULL DEFAULT NULL COMMENT '이메일 인증 시간',
  `mem_status` varchar(1) DEFAULT NULL COMMENT '회원 상태( A:활동, C: 휴면, D: 탈퇴 )',
  `created_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 등록일시',
  PRIMARY KEY (`mem_pid`)
) ENGINE=InnoDB COMMENT='회원 마스터 테이블';
``` 
  - member_master 