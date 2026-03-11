# 📚 Library Compass

Sistema de gerenciamento de biblioteca desenvolvido de forma **incremental** em três etapas:

1. **Java Console (CLI)** - Implementação inicial com funcionalidades básicas
2. **Spring Boot (REST API + MVC)** - Evolução da aplicação Java para arquitetura web
3. **Frontend TypeScript** - Interface moderna consumindo a API REST do Spring Boot


> 💡 **Evolução do Projeto:** Este é um projeto de aprendizado incremental onde cada etapa constrói sobre a anterior, demonstrando a evolução de uma aplicação console para uma solução full-stack completa.


> 🚀 **Próxima Fase:** Veja a continuação deste projeto no repositório [**Library Fullstack Team Compass**](https://github.com/andreonyzko/library-fullstack-team-compass) - Versão colaborativa desenvolvida em equipe com arquitetura moderna, incluindo React, PostgreSQL e recursos avançados.

---

## 📁 Desafio 01 - Java Console

Aplicação de linha de comando com funcionalidades básicas de gerenciamento.

**Stack:** Java 24, Collections, Enums

**Funcionalidades:**

- Cadastro de livros e usuários
- Listagem de dados
- Controle de empréstimos e devoluções
- Histórico de empréstimos

---

## 📁 Desafio 02 - Spring Boot

### 🔹 REST API

API RESTful para gerenciamento completo da biblioteca.

**Stack:** Spring Boot 3.5.3, Spring Data JPA, MySQL, Bean Validation

**Endpoints:**

- `GET/POST /api/livros` - Livros
- `GET/POST /api/usuarios` - Usuários
- `POST /api/emprestimos` - Empréstimos
- `PUT /api/emprestimos/{id}/devolver` - Devoluções

**Testar:** [Coleção Postman](https://andreonyszko-3925945.postman.co/workspace/Andre-Onyszko's-Workspace~e68cf487-d6ef-47e2-8209-5cae3fb6aeaf/collection/46133040-7042aad2-df4f-4bc4-b9c2-79557aacaf41?action=share&creator=46133040)

---

### 🔹 MVC + Security

Aplicação web com interface Thymeleaf e autenticação.

**Stack:** Spring Boot, Spring Security, Thymeleaf, MySQL

**Recursos:**

- 🔐 Autenticação e autorização
- 🎨 Interface web completa
- 🛡️ Proteção CSRF
- 🔒 Senhas criptografadas (BCrypt)

---

## 📁 Desafio 03 - Frontend TypeScript

SPA moderna consumindo a REST API.

**Stack:** TypeScript 5.8.3, Vite 7.0.4, Vanilla TS, CSS3

**Arquitetura:**

- Component-Based
- Router Pattern (SPA)
- Service Layer
- CSS Modular

---

## 🗄️ Modelo de Dados

**Book:** id, title, author, yearPublication, status (AVAILABLE/BORROWED)  
**User:** id, name, email, phone  
**Loan:** id, book, user, loanDate, returnDate, returned

---