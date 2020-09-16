
# Viewer4Doc

## 프로젝트 소개
  * 사용자 별 공유 문서를 관리하는 웹 사이트 
  * PDF.js를 사용한 웹 문서뷰어 

## 소프트웨어 구성

<img src="img/Architecture0916.png">

## 개발 환경

  * Springboot 2.3.2
  * jdk 1.8
  * Gradle 6.4.1
  
## 설치 및 사용법

```
git clone https://github.com/yangjae33/viewer4Doc.git
cd viewer4Doc
./gradlew bootJar
docker-compose up --build
```

## API 목록 확인

  * Login API
    
    http://localhost:8001/swagger-ui.html
    
  * Admin API
  
    http://localhost:8002/swagger-ui.html

  * User API
  
    http://localhost:8003/swagger-ui.html


## DB - MySQL

  * Docker 컨테이너 bash로 접속
    
    ```
    docker exec viewer4Doc_mysql_1 bash
    mysql -u viewer4doc -p (password: viewer4doc)
    ```

  * Workbench 에서 접속
    ```
    HOST - localhost:13306
    USER - viewer4doc
    PASSWORD - viewer4doc
    DB - viewer4doc
    ```
