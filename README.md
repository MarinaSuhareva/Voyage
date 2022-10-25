# Запуск автотестов

### Шаги воспроизведения

1. Скачать проект с удаленного репозитория на свой локальный, с помощью команды  _git clone_
2. Открыть проект на установленной заранее IntelliJ IDEA
3. Запустить Docker контейнеры СУБД MySQL и PostgreSQL
4. Запустить контейнеры в терминале 

        docker-compose up --build

5. Проверить, что контейнеры запустились:
        
        docker ps

6. Запустить SUT
   * Для MySQL:
   
         java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

   * Для PostgreSQL:
   
         java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar


##### Приложение должно запуститься на:
  
         http://localhost:8080. 


7. Запустить автотесты
    * Для MySQL
  
          ./gradlew clean test -D db.url=jdbc:mysql://localhost:3306/app 
  
    * Для PostgreSQL
    
          ./gradlew clean test -D db.url=jdbc:postgresql://localhost:5433/app 
   
8. Генерация отчетов

        ./gradlew clean test allureReport
  
        ./gradlew allureServe
          
9. Завершение работы AllureServe
  
        Ctrl+C 
        
10. Остановить и удалить все контейнеры
  
        docker-compose down
        
