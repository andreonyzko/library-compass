## Coleção de Requisições no Postman

Acesse a coleção pronta para importar no Postman e testar rapidamente:

[Postman Collection - Biblioteca API](https://andreonyszko-3925945.postman.co/workspace/Andre-Onyszko's-Workspace~e68cf487-d6ef-47e2-8209-5cae3fb6aeaf/collection/46133040-7042aad2-df4f-4bc4-b9c2-79557aacaf41?action=share&creator=46133040)

## REQUISIÇÕES:
Deixei a baixo uma "trilha" de requisões para realizar os testes, bodys das requisições destacadas com alguns comentários explicativos e instruitivos.
Para rodar a aplicação, o único requisito além de baixar o projeto, é criar um banco com o nome library, as demais tabelas a própria aplicação cuida de criar.

---

### 1. GET http://localhost:8080/api/livros
```
// Buscar todos os livros
```

---

### 2. GET http://localhost:8080/api/livros/1
```
// Buscar pelo livro 10 -> não existe
// Buscar pelo livro 1
```

---

### 3. POST http://localhost:8080/api/livros
```
{
    // Body vazio para ver as validações do Bean Validator
}

// {
//     "title" : "Diário de um Banana",
//     "author" : "Jeff Kinney",
//     "yearPublication" : 2007, // Se informar uma data futura o Validator Bean ira barrar

//     // Não considerado pela implementação do DTO
//     "id" : 10000000,
//     "status" : "TESTE"
// }
```

---

### 4. PUT http://localhost:8080/api/livros/1
```
{
    // Body vazio para ver as validações do Bean Validator (os mesmo do POST)
}

// // Atualizar livro 10 -> livro não existe
// // Atualizar livro 1:
// {
//     "title" : "Diário de um Banana: Dias de Cão",
//     "author" : "Jeff Kinney",
//     "yearPublication" : 2007 // Se informar uma data futura o Validator Bean ira barrar
// }
```

---

### 5. PATCH http://localhost:8080/api/livros/1
```
// Atualizar livro 10 -> não existe
// Atualizar livro 1:
{
    "yearPublication" : 2010 // // Se informar uma data futura o Validator Bean ira barrar
}
```

---

### 6. GET http://localhost:8080/api/usuarios
```
// Buscar todos os usuários
```

---

### 7. GET http://localhost:8080/api/usuarios/1
```
// Buscar pelo usuário 10 -> não existe
// Buscar pelo usuário 1
```

---

### 8. POST http://localhost:8080/api/usuarios
```
{
    // Body vazio para ver as validações do Bean Validator
}

// {
//     "name" : "Andre",

//     // Ignorados pela implementação do DTO
//     "id" : 1000000
// }
```

---

### 9. PUT http://localhost:8080/api/usuarios/1
```
{
  // Body vazio para ver as validações do Bean Validator (os mesmo do POST)
}

// // Atualizar usuário 10 -> usuário não existe
// // Atualizar usuário 1:
// {
//     "name" : "Andre Onyszko"
// }
```

---

### 10. POST http://localhost:8080/api/livros/1/emprestar/1
```
// Emprestar livro 10 -> inexistente
// Emprestar pro usuário 10 -> inexistente
// Emprestar livro 1 pro usuário 1
// Emprestar livro 1 pra qualquer usuário -> já emprestado
```

---

### 11. GET http://localhost:8080/api/usuarios/1/livros-emprestados
```
// Ver empréstimos do usuário 10 -> usuário não existe
// Ver empréstimos do usuário 5
```

---

### 12. DELETE http://localhost:8080/api/livros/1
```
// Tentar deletar livro 10 -> não existe
// Tentar deletar livro 1 que está emprestado
```

---

### 13. DELETE http://localhost:8080/api/usuarios/1
```
// Tentar deletar usuário 10 -> não existe
// Tentar deletar usuário 1 que tem devoluções pendentes
```

---

### 14. POST http://localhost:8080/api/livros/1/devolver
```
// Devolver livro 10 -> inexistente
// Devolver livro 1
// Devolver novamente o livro 1 -> já disponível
```

### 15. DELETE http://localhost:8080/api/livros/1 e DELETE http://localhost:8080/api/usuarios/1
```
// Deletar livro 1 sem estar emprestado
// Deletar usuário 1 sem nenhuma devolução pendente
```
