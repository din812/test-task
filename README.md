WIP

# test-task
Реализация тестового [задания](https://github.com/revkov/JAVA.SB2.TEST) (нашел на просторах и делаю для себя).

tldr: 
```diff
+ Написать конвертер валют;
+ Входные данные брать с сайта ЦБ из XML файла и сохранять их в PostgreSQL;
+ Загружать свежие данные при запуске программы;
+ Сделать авторизацию;
+ Сделать историю запросов отдельной страницой и на старнице конвертации;
+ Каждую конвертацию проверять самые ли последние данные в базе данных и загружать новые если нет;
```

 Основные технологии: Spring MVC, Spring Security, Spring JPA, JUnit 5, Mockito, PostgreSQL, Lombok, Thymeleaf,
Hibernate, JAXB, Docker.

 В данный момент программа работает по условию, сами страницы совершенно не оформлены и написан только один тест.

Добавлены:
```diff
1. Регистрация, с автовходом и валидацией логина и пароля.
2. Меню администратора со списком пользователей и поиском пользователя по айди и выводом их
истории (pageable) и данных.
3. Разделение доступа к страницам по ролям.
```
