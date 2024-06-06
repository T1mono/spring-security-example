# spring-security-example

## Описание

Веб-приложение с использованием Spring Security и JWT для аутентификации и авторизации пользователей.


## Сборка backend части

```cmd
cd spring-security && mvn clean package
```
Linux (bash):
```bash
(cd spring-security && mvn clean package)
```

## Запуск

### Простой запуск:
```bash
java -jar target/spring-security-0.0.1-SNAPSHOT.jar
```

## Использование

URL с swagger документацией: http://localhost:8080/swagger-ui/index.html

## Запуск браузера с отключённым CORS
Создать ярлык на рабочем столе и в поле объект прописать
- "C:\Program Files\Google\Chrome\Application\chrome.exe" --user-data-dir="C://chrome-dev-disabled-security" --disable-web-security --disable-site-isolation-trials


## Скрпиты для создания таблиц в БД (PostgreSQL)

```
CREATE TABLE IF NOT EXISTS corporation.users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    role     VARCHAR(255)        NOT NULL
);
```

## Проверка, что хранится внутри токена

https://jwt.io/
