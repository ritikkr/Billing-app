# Enterprise Billing Application Backend

## Modern RESTful API for Invoice and Customer Management

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg?style=for-the-badge&logo=openjdk)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green.svg?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-orange.svg?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

---

## 🚀 Overview

This **Enterprise Billing Application Backend** is a robust and scalable **RESTful API** built with **Spring Boot 3** and **Java 17**. It provides core functionalities for managing customers, items (products/services), and generating comprehensive bills/invoices. Designed with an **enterprise-level architecture**, it emphasizes clean code, separation of concerns, data integrity, and efficient **invoice management**.

This backend service is ideal for businesses requiring a reliable system to automate their billing processes, track financial transactions, and maintain detailed records of customer interactions and sales.

## ✨ Key Features

* **Customer Management:** CRUD operations for customer records, including contact details, addresses, and company information.
* **Item/Product Management:** Define and manage products or services with pricing, descriptions, and taxability.
* **Invoice/Bill Generation:** Create detailed invoices with multiple line items, automatic calculation of subtotal, tax, and total amounts.
* **Bill Status Tracking:** Manage the lifecycle of an invoice (DRAFT, ISSUED, PAID, VOID, CANCELLED).
* **Data Validation:** Robust input validation using JSR 303 (Bean Validation) for all incoming API requests.
* **Auditing:** Automatic tracking of creation and last modification timestamps for all entities.
* **Soft Deletes:** Records are marked as inactive instead of being permanently deleted, preserving historical data integrity.
* **BigDecimal for Currency:** Ensures precise financial calculations, avoiding floating-point inaccuracies.
* **Layered Architecture:** Clear separation of concerns (Controller, Service, Repository, Entity, DTO) for maintainability and scalability.
* **Global Exception Handling:** Consistent error responses for API consumers.
* **MySQL Database Integration:** Persistent storage for all billing data.

## 🛠️ Technologies Used

* **Backend:**
    * **Java 17:** The core programming language.
    * **Spring Boot 3.x:** Framework for rapid application development.
    * **Spring Data JPA:** Simplifies database interactions.
    * **Hibernate:** JPA implementation for ORM.
    * **Lombok:** Reduces boilerplate code (getters, setters, constructors).
    * **Jakarta Validation (Bean Validation):** For robust data validation.
* **Database:**
    * **MySQL 8.0+:** Relational database for data storage.
* **Build Tool:**
    * **Maven:** Project build automation tool.

## 🚀 Getting Started

Follow these steps to set up and run the **Billing Application Backend** on your local machine.

### Prerequisites

* **Java Development Kit (JDK) 17 or higher**
* **Maven 3.6.x or higher**
* **MySQL Server 8.0+**
* **An IDE** (IntelliJ IDEA, VS Code, Eclipse) with Spring Boot support.

### 1. Clone the Repository

```bash
git clone [https://github.com/your-username/enterprise-billing-app.git](https://github.com/your-username/enterprise-billing-app.git)
cd enterprise-billing-app
