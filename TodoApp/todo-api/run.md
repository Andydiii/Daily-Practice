通常需要在 todo-api 目录运行，因为这里包含：
```
pom.xml
mvnw
.mvn/
```
Maven需要从这里读取项目配置。
但一个 Terminal session 中只需要 cd 一次：
```
cd "/Users/andyliu/Desktop/Daily-Practice/Java practice/todo-api"
./mvnw spring-boot:run
```
backend:
http://localhost:8080/tasks

frontend: