# 📊 Telegram Finance Bot

[![Test-CI](https://github.com/BroCodeX/BroX-FinTechBot/actions/workflows/tests.yml/badge.svg)](https://github.com/BroCodeX/BroX-FinTechBot/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/5a27dcb9c19abab7f0bc/maintainability)](https://codeclimate.com/github/BroCodeX/BroX-FinTechBot/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/5a27dcb9c19abab7f0bc/test_coverage)](https://codeclimate.com/github/BroCodeX/BroX-FinTechBot/test_coverage)

Telegram-бот для учета, анализа финансовых данных и контроля бюджета. Позволяет пользователям регистрировать доходы и расходы, получать отчеты по категориям, а также получать уведомления о превышении лимитов бюджета.

## 🚀 Основные возможности

- **Регистрация доходов и расходов** с указанием категорий
- **Отчеты по транзакциям** с разбивкой по категориям и гибкой фильтрацией
- **Интеграция с RabbitMQ** для асинхронной обработки уведомлений
- **Покрытие кода тестами** с использованием TDD-подхода
- **Контейнеризация Docker Compose и деплой в Kubernetes**

---

## 🏗 Стек технологий

- **Backend**: Java, Spring Boot (Web, Data JPA, Security, AMQP)
- **Database**: PostgreSQL
- **Messaging**: RabbitMQ
- **CI/CD**: Docker Compose, Kubernetes
- **Testing**: JUnit, Mockito
- **DocGenerator**: iText Core/Community

---

<details>
<summary>## 📚 Запуск проекта локально</summary>

### 1. Подготовьте окружение
Убедитесь, что у вас установлены:
- Docker и Docker Compose
- Java 21
- Gradle 8,7

### 2. Клонируйте репозиторий

```bash
git clone git@github.com:BroCodeX/BroX-FinTechBot.git
cd BroX-FinTechBot
```

### 3. Подготовьте переменные для запуска приложения через .env файл или через хардкод файла compose.yaml

### 4. Запустите инфраструктуру (PostgreSQL и RabbitMQ)

```bash
docker compose up -d
```

</details>

---

<details>
<summary>## 🧪 Запуск тестов</summary>

```bash
make test
```

</details>

---

<details>
<summary>## 🛠 Контейнеризация и деплой в Kubernetes</summary>

### Сборка Docker-образа

```bash
docker build -t telegram-finance-bot .
```

### Запуск в Kubernetes

1. **Создайте манифесты Kubernetes** для деплоя (см. папку `/k8s`).
2. **Примените конфигурации**:

   ```bash
   kubectl apply -f k8s/
   ```

</details>

---

## 📈 Примеры команд для бота

| Команда            | Описание                               |
|--------------------|----------------------------------------|
| `/start`           | Начало работы, регистрация пользователя и установка бюджета |
| `/help`            | Вывод доступных команд для бота        |
| `/add_transaction` | Добавление новой транзакции            |
| `/view_budget`     | Просмотр текущего бюджета              |
| `/edit_budget`     | Корректировка бюджета                  |
| `/get_report`      | Получение отчета по категориям с использованием фильтра по дате. Формат PDF  |


---


## 📬 Контакты и поддержка

Если у вас есть вопросы или предложения, создайте новый Issue или отправьте Pull Request.
