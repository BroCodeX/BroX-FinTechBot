# 📊 Telegram Finance Bot

[![Test-CI](https://github.com/BroCodeX/BroX-FinTechBot/blob/main/.github/workflows/tests.yml/badge.svg)](https://github.com/BroCodeX/BroX-FinTechBot/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/5a27dcb9c19abab7f0bc/maintainability)](https://codeclimate.com/github/BroCodeX/BroX-FinTechBot/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/5a27dcb9c19abab7f0bc/test_coverage)](https://codeclimate.com/github/BroCodeX/BroX-FinTechBot/test_coverage)

Telegram-бот для учета, анализа финансовых данных и контроля бюджета. Позволяет пользователям регистрировать доходы и расходы, получать отчеты по категориям, а также получать уведомления о превышении лимитов бюджета.

## 🚀 Основные возможности

- **Регистрация доходов и расходов** с указанием категорий
- **Отчеты по транзакциям** с разбивкой по категориям
- **Установка и контроль бюджета** с уведомлениями о превышении лимита
- **Интеграция с RabbitMQ** для асинхронной обработки уведомлений
- **Покрытие кода тестами** с использованием TDD-подхода
- **Контейнеризация и деплой в Kubernetes**

---

## 🏗 Стек технологий

- **Backend**: Java, Spring Boot (Web, Data JPA, Security, AMQP)
- **Database**: PostgreSQL
- **Messaging**: RabbitMQ
- **CI/CD**: Docker, Kubernetes
- **Testing**: JUnit, Mockito

---

<details>
<summary>## 📚 Запуск проекта локально</summary>

### 1. Подготовьте окружение
Убедитесь, что у вас установлены:
- Docker и Docker Compose
- Java 17

### 2. Клонируйте репозиторий

\```bash
git clone https://github.com/yourusername/telegram-finance-bot.git
cd telegram-finance-bot
\```

### 3. Запустите инфраструктуру (PostgreSQL и RabbitMQ)

\```bash
docker-compose up -d
\```

### 4. Запустите приложение

\```bash
./mvnw spring-boot:run
\```

</details>

---

<details>
<summary>## 🧪 Запуск тестов</summary>

\```bash
./mvnw test
\```

</details>

---

<details>
<summary>## 🛠 Контейнеризация и деплой в Kubernetes</summary>

### Сборка Docker-образа

\```bash
docker build -t telegram-finance-bot .
\```

### Запуск в Kubernetes

1. **Создайте манифесты Kubernetes** для деплоя (см. папку `/k8s`).
2. **Примените конфигурации**:

   \```bash
   kubectl apply -f k8s/
   \```

</details>

---

## 📈 Примеры команд для бота

| Команда            | Описание                               |
|--------------------|----------------------------------------|
| `/start`           | Начало работы и регистрация пользователя |
| `/add_transaction` | Добавление новой транзакции            |
| `/view_transactions` | Отображение последних транзакций       |
| `/set_budget`      | Установка месячного бюджета            |
| `/get_report`      | Получение отчета по категориям         |

Пример добавления транзакции:

\```plaintext
/add_transaction
Тип: Расход
Категория: Еда
Сумма: 500
\```

---

## 🔗 Ссылка на бота

[Перейти к боту в Telegram](https://t.me/your_bot_placeholder)

---

## 📬 Контакты и поддержка

Если у вас есть вопросы или предложения, создайте новый Issue или отправьте Pull Request.
