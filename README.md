# WhereIsMyHome_Final_신주용_이승현

> 삼성 청년 SW 아카데미 (SSAFY) 10기 관통 PJT

## 기능

## 실행 방법

### Dependencies

|  Name   | Version  |
|:-------:|:--------:|
| Node.js | ^18.17.1 |
|  Java   |   1.8    |
|  Maven  |   3.9    |

### Run locally

1. 프로젝트 클론 후 디렉토리로 이동

    ```bash
    git clone https://github.com/cheesecat47/whereismyhome-final.git
    cd whereismyhome-final
    ```

2. API 서버 실행

   ```bash
   cd backend
   ./mvnw package
   java -jar target/whereismyhome_gumi_sjy_lsh-0.0.1-SNAPSHOT.jar
   ```

3. Frontend 개발 서버 실행

   ```bash
   cd frontend
   npm i
   npm run dev
   ```

## Authors

- [신주용 @cheesecat47](https://lab.ssafy.com/cheesecat47)
- [이승현 @sh980812](https://lab.ssafy.com/sh980812)
