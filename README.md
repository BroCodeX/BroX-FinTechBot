# 🌐 VPN Subscription Service with Telegram Bot 🎩

Welcome to the VPN Subscription Service project! This repository hosts a full-stack application that provides secure, subscription-based VPN access through a user-friendly Telegram bot. Designed to handle everything from subscription management to asynchronous notifications and robust monitoring, this project is built for scale and reliability. 🌍🔒

## 🌟 Key Features
- **Seamless VPN Access**: Secure access to VPN services with subscription-based control.
- **Telegram Bot Integration**: Users interact with the VPN service directly through Telegram commands.
- **Asynchronous Notifications**: RabbitMQ-powered notifications to keep users up-to-date on their subscriptions.
- **Comprehensive Monitoring**: Sentry integration for real-time error tracking and system health.
- **Scalable Deployment**: Designed for cloud deployment with Docker and Kubernetes support.

---

## 📑 Table of Contents
1. [Tech Stack](#tech-stack)
2. [System Architecture](#system-architecture)
3. [Getting Started](#getting-started)
4. [Bot Usage](#bot-usage)
5. [Deployment Instructions](#deployment-instructions)
6. [API Endpoints](#api-endpoints)
7. [License](#license)
8. [Contact & Bot Link](#contact--bot-link)

---

## ⚙️ Tech Stack
This project leverages a modern technology stack to ensure optimal performance and maintainability:
- **Backend**: Java, Spring Boot (RESTful APIs), Spring Data JPA (database management), Spring AMQP (messaging)
- **Database**: PostgreSQL
- **Message Queue**: RabbitMQ
- **Bot Interface**: Telegram Bot API
- **Monitoring**: Sentry
- **Containerization & Deployment**: Docker, Kubernetes

---

## 🏗️ System Architecture
Our system architecture is structured around a microservice design that ensures modularity, scalability, and efficient error handling:
1. **VPN Server**: Controls user access based on subscription status.
2. **Telegram Bot Interface**: Users can manage subscriptions and receive status updates directly through Telegram.
3. **Backend API**: Built with Spring Boot, this API handles all core business logic, including managing subscriptions, user data, and message queues.
4. **RabbitMQ**: Powers the notification system to provide real-time updates on subscription status and service notifications.
5. **PostgreSQL Database**: Stores user information, subscription statuses, and activity logs.
6. **Sentry**: Tracks and logs errors and downtime for continuous monitoring.

---

## 🚀 Getting Started

### Prerequisites
Ensure you have the following installed on your system:
- **Java 17**
- **Docker & Docker Compose**
- **PostgreSQL and RabbitMQ** (for local testing or use Docker)
- **Sentry** account for monitoring

### Set Up Environment Variables
Create a `.env` file in the project’s root directory and define the required variables:
```plaintext
TELEGRAM_BOT_TOKEN=your_bot_token
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
RABBITMQ_URL=your_rabbitmq_url
SENTRY_DSN=your_sentry_dsn
```

### Run Docker Containers
```bash
docker-compose up
```

### Database Migration
Run database migrations to set up the schema:
```bash
./mvnw flyway:migrate
```

### Start the Application
```bash
./mvnw spring-boot:run
```

---

## 🤖 Bot Usage

Once deployed, the Telegram bot offers the following commands for user interaction:

- `/start` - Initializes the bot and sends a welcome message to the user.
- `/subscribe` - Allows the user to start or renew their VPN subscription.
- `/status` - Displays the user's current subscription status, including expiration details.
- `/info` - Provides VPN connection instructions and additional information.

### API Endpoints
For detailed API documentation, visit: [Swagger API Docs](http://localhost:8080/swagger-ui.html)

---

## 🌐 Deployment Instructions

### Docker & Kubernetes

#### Docker
1. **Build Docker Image**
   ```bash
   docker build -t vpn-subscription-service .
   ```

2. **Start Containers**
   Run all services using `docker-compose.yml`:
   ```bash
   docker-compose up -d
   ```

#### Kubernetes
For deployment to a Kubernetes cluster:
1. **Namespace Creation**
   ```bash
   kubectl create namespace vpn-service
   ```

2. **Deploy Resources**
   Apply the Kubernetes manifests:
   ```bash
   kubectl apply -f k8s/deployment.yaml -n vpn-service
   kubectl apply -f k8s/service.yaml -n vpn-service
   ```

3. **Configure Sentry for Error Tracking in Kubernetes**
   Sentry setup can be adapted to monitor all services running in the Kubernetes environment, ensuring continuous error tracking.

---

## 📜 License
This project is licensed under the MIT License. For details, see the [LICENSE](LICENSE) file.

---

## 📬 Contact & Bot Link
For any questions or feedback, reach out via [your-email@example.com](mailto:your-email@example.com).

### 🌐 Try the Bot
🔗 [Telegram Bot](https://t.me/YourBotUsername) (Link coming soon!)
