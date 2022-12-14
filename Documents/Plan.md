# План автоматизации

Цель: Автоматизировать сценарии (как позитивные, так и негативные) покупки тура через Приложение.
_____________________________________________________________________________________________________________

## Тестовые данные

### Валидными данными при заполнении полей ввода формы считаются:

* Номер карты: цифры, в формате **** **** **** ****

* Месяц: 01-12, но не ранее текущего месяца в случае, если указан текущий год

* Год: последние две цифры порядкового номера года, не ранее текущего года

* Владелец: латиница, спецсимволы (пробел, дефис)

* CVC: диапазон значений от 001 до 999.

#### Набор карт, представленный для тестирования (файл data.json):

- 4444 4444 4444 4441, status APPROVED

- 4444 4444 4444 4442, status DECLINED

#### Несуществующая карта

- 4444 4444 4444 4444

### Проверка поддержки заявленных СУБД:

- MySql

- Postgres
_______________________________________________________________________________________________________________

## Тестовые сценарии

### **Позитивные сценарии**

#### 1. Оплата по карте со статусом APPROVED

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, остальные поля заполнены валидными данными.
  Ожидаемый результат: появилось всплывающее окно "Успешно! Операция одобрена Банком".
  В БД есть запись APPROVED.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, остальные поля заполнены валидными данными.
  Ожидаемый результат: появилось всплывающее окно "Успешно! Операция одобрена Банком".
  В БД есть запись APPROVED.

#### 2. Оплата по карте со статусом DECLINED

* Оплата по заблокированной дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4442, остальные поля заполнены валидными данными.
  Ожидаемый результат: появилось всплывающее окно "Ошибка! Банк отказал в проведении операции."
  В БД есть запись DECLINED.

* Оплата по заблокированной кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4442, остальные поля заполнены валидными данными.
  Ожидаемый результат: появилось всплывающее окно "Ошибка! Банк отказал в проведении операции."
  В БД есть запись DECLINED.

### **Негативные сценарии**

#### 3. Проверка покупки тура по несуществующей карте

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4444, остальные поля заполнены валидными данными.
  Ожидаемый результат: появилось всплывающее окно "Ошибка! Банк отказал в проведении операции."
  В БД нет новой записи.
* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4444, остальные поля заполнены валидными данными.
  Ожидаемый результат: появилось всплывающее окно "Ошибка! Банк отказал в проведении операции."
  В БД нет новой записи.

#### 4. Проверка покупки тура с неполным номером карты

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 444, остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Номер карты" появилось сообщение "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 444, остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Номер карты" появилось сообщение "Неверный формат".
  В БД нет новой записи.

#### 5. Проверка покупки тура картой с пустым полем "Номер карты"

* Оплата по дебетовой карте:
  Поле "Номер карты" оставить пустым, остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Номер карты" появилось сообщение "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  Поле "Номер карты" оставить пустым, остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Номер карты" появилось сообщение "Неверный формат".
  В БД нет новой записи.

#### 6. Проверка покупки тура картой с истекшим сроком действия (Месяц)

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Месяц" указать предыдущий месяц, остальные поля
  заполнены валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверно указан срок действия карты".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Месяц" указать предыдущий месяц, остальные поля
  заполнены валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверно указан срок действия карты".
  В БД нет новой записи.

#### 7. Проверка покупки тура картой с невалидным сроком действия (Месяц)

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Месяц" указать 13, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверно указан срок действия карты".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Месяц" указать 13, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверно указан срок действия карты".
  В БД нет новой записи.

#### 8. Проверка покупки тура картой с некорректным форматом в поле "Месяц"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Месяц" указать 3 (вместо 03), остальные поля
  заполнены валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле Месяц указать 3 (вместо 03), остальные поля
  заполнены валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверный формат".
  В БД нет новой записи.

#### 9. Проверка покупки тура картой с пустым полем "Месяц"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "Месяц" оставить пустым, остальные поля
  заполнены валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "Месяц" оставить пустым, остальные поля
  заполнены валидными данными.
  Ожидаемый результат: под полем "Месяц" появилось сообщение "Неверный формат".
  В БД нет новой записи.

#### 10. Проверка покупки тура картой с истекшим сроком действия (Год)

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Год" указать предыдущий год,
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Год" появилось сообщение "Истёк срок действия карты".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Год" указать предыдущий год,
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Год" появилось сообщение "Истёк срок действия карты".
  В БД нет новой записи.

#### 11. Проверка покупки тура картой с невалидным сроком действия (Год)

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Год" указать 00, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем "Год" появилось сообщение "Неверно указан срок действия карты".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле Год указать 00, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем "Год" появилось сообщение "Неверно указан срок действия карты".
  В БД нет новой записи.

#### 12. Проверка покупки тура картой с пустым полем "Год"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "Год" оставить пустым,
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Год" появилось сообщение "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "Год" оставить пустым,
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Год" появилось сообщение "Неверный формат".
  В БД нет новой записи.

#### 13. Проверка покупки тура с некорректными данными в поле "Владелец"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Владелец" указать одно значение (имя),
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение об ошибке.
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Владелец" указать одно значение (имя),
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение об ошибке.
  В БД нет новой записи.

#### 14. Проверка покупки тура с данными на кириллице в поле "Владелец"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Владелец" указать данные на кириллице,
  остальные поля заполнены корректными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение об ошибке.
  В БД нет новой записи

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Владелец" указать данные на кириллице,
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение об ошибке.
  В БД нет новой записи.

#### 15. Проверка покупки тура со спецсимволами в поле "Владелец"

* Оплата по дебетовой карте:
  в поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Владелец" ввести спецсимволы ("$#@*&!"),
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение об ошибке.
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле "Владелец" ввести спецсимволы ("$#@*&!"),
  остальные поля заполнены валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение об ошибке.
  В БД нет новой записи.

#### 16. Проверка покупки тура с незаполненным полем "Владелец"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "Владелец" оставить пустыми, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение "Поле обязательно для заполнения".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "Владелец" оставить пустыми, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем "Владелец" появилось сообщение "Поле обязательно для заполнения".
  В БД нет новой записи.

#### 17. Проверка покупки тура с некорректными значениями в поле CVC/CVV

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле CVC/CVV ввести 000, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем CVC/CVV появилось сообщение об ошибке "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, в поле CVC/CVV ввести 000, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем CVC/CVV появилось сообщение об ошибке "Неверный формат".
  В БД нет новой записи.

#### 18. Проверка покупки тура с незаполненным полем "CVC/CVV"

* Оплата по дебетовой карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "CVC/CVV" оставить пустым, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем CVC/CVV появилось сообщение "Неверный формат".
  В БД нет новой записи.

* Оплата по кредитной карте:
  В поле "Номер карты" ввести 4444 4444 4444 4441, поле "CVC/CVV" оставить пустым, остальные поля заполнены
  валидными данными.
  Ожидаемый результат: под полем CVC/CVV появилось сообщение "Неверный формат".
  В БД нет новой записи.
________________________________________________________________________________________________________________
## Перечень используемых инструментов с обоснованием выбора

1. IntelliJ IDEA - Многофункциональная среда разработки.
2. Java 11- Универсальный язык, позволяющий работать на большинстве ОС и различном оборудовании.
3. Gradle - Система сборки проекта. Проще подключать внешние зависимости.
4. JUnit5 - Инструмент тестирования.
5. Selenide - Фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver.
6. Lombok - Плагин для создания аннотаций, заменяющих значительное количество однообразных конструкторов JAVA.
7. Faker - Для генерации тестовых данных.
8. Docker - Платформа для разработки и запуска контейнеров. Докер позволяет создавать контейнеры, автоматизирует их
   запуск и управляет жизненным циклом.
9. Appveyor - Система CI. Непрерывный контроль интеграции кода.
10. Allure - Система подготовки отчётов.
11. Git и GitHub - для полноценной работы с фейморками и хранения автотестов.
________________________________________________________________________________________________________________

## Перечень и описание возможных рисков при автоматизации

* Возможны сложности при настройке двух СУБД (MySQL и PostgreSQL), и корректном подключении к каждой из них.
* Из-за отсутствия ТЗ и какой-либо документации по работе сервиса трудно понять, как он должен работать
  и какое поведение сервиса можно считать ошибкой.
* Трудности с нахождением необходимых для тестирования CSS - селекторов.
* Изменение на странице может привести к падению тестов и необходимости их дальнейшего исправления.
________________________________________________________________________________________________________________

## Интервальная оценка с учетом рисков (в часах)

Настройка ПО - 12 часов;

Написание и отладка автотестов - 48 часов;

Заведение issue по итогам прогонов автотестов - 8 часов;

Подготовка отчетных документов по итогам автоматизированного тестирования - 8 часов;

Подготовка отчетных документов по итогам автоматизации - 8 часов;
_______________________________________________________________________________________________________________

## План сдачи работ

Ожидаемая дата завершения проекта 13 октября 2022г.