# 🛍️ NotAloneMarket Backend

**Spring Boot 기반 공동구매 플랫폼 백엔드 프로젝트입니다.**

---

## 🚀 시작하는 법 (Eclipse 기준)

### 1️⃣ 프로젝트 클론

Git Bash 또는 터미널에서 아래 명령어를 실행해 프로젝트를 클론합니다.

```bash
git clone https://github.com/NotAloneMarket/notalone-market-be.git
```

---

## 2️⃣ Eclipse에서 프로젝트 열기

1. Eclipse 실행  
2. `File → Import` 클릭  
3. `Maven → Existing Maven Projects` 선택 후 `Next`  
4. `Root Directory`에 클론한 폴더 경로 입력 (예: `C:\Users\사용자\notalone-market-be`)  
5. `Finish` 클릭

---

## 3️⃣ JDK 17 설치 및 설정

- 시스템에 **JDK 17**이 설치되어 있어야 합니다.

**Eclipse 설정 방법:**

1. `Window → Preferences → Java → Installed JREs` 이동  
2. `Add...` 클릭 → `Standard VM` 선택 후 `Next`  
3. JDK 17 설치 경로 입력 (예: `C:\Program Files\Java\jdk-17`)  
4. 등록된 JDK 17에 체크 → **기본 JRE로 설정**

---

## 4️⃣ application.properties 설정

`src/main/resources/application.properties` 파일을 열고 본인의 **Oracle DB 정보**로 수정합니다:

---

## 5️⃣ 의존성 다운로드

- 프로젝트에서 **마우스 오른쪽 클릭 → Maven → Update Project** 실행

---

## 6️⃣ 서버 실행

- `NotalonemargetApplication.java` 클래스  
  (위치: `src/main/java/com/ddwu/notalonemarget/`)  
  → **우클릭 → Run As → Java Application**

---

## ✅ 참고사항

- **Java 17 이상 필요**
- **Oracle DB 12c 이상 사용**
- 기본 포트는 `8080` (필요시 `application.properties`에서 변경 가능)
- 실행 후 [`http://localhost:8080`](http://localhost:8080)에서 API 접근 가능

