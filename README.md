<h1 align="center"> Процедура запуска автотестов.</h1>
Запустить Docker Desktop.

* Открыть клонированный проект в **IntelliJ IDEA**.
  * Перед запуском тестов необходимо поднять контейнеры с базами данных (MySQL, PostgreSQL) и эмулятором банковских сервисов:
  * Выполните команду: docker-compose up -d
  
 * Приложение поддерживает две СУБД. Перед запуском тестов необходимо запустить .jar файл, указав URL нужной базы.
   
* Для работы с MySQL: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
  
* Для работы с PostgreSQL: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
  
  * Запуск тестов под MySQL: ./gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app" "-Ddb.user=app" "-Ddb.password=pass"
  * Запуск тестов под PostgreSQL: ./gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app" "-Ddb.user=app" "-Ddb.password=pass"



* Генерация отчетов

    * ./gradlew allureServe Для генерация отчёта Allure Report по результатам тестирования и автоматическое открытие отчета в браузере по умолчанию.  
