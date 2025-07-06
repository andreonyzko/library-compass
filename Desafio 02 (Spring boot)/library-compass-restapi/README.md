# COLEÇÃO DAS REQUISIÇÕES PRONTAS NO POSTMAN:
https://andreonyszko-3925945.postman.co/workspace/Andre-Onyszko's-Workspace~e68cf487-d6ef-47e2-8209-5cae3fb6aeaf/collection/46133040-7042aad2-df4f-4bc4-b9c2-79557aacaf41?action=share&creator=46133040

# REQUISIÇÕES:
GET http://localhost:8080/api/livros
// Buscar todos os usuarios

GET http://localhost:8080/api/livros/1
// Buscar pelo livro 10 -> não existe
// Buscar pelo livro 1

POST http://localhost:8080/api/livros
{
// Body vazio para ver as validações do Bean Validator
}

// {
//     "title" : "Diário de um Banana",
//     "author" : "Jeff Kinney",
//     "yearPublication" : 2040, // Data futura, correto: 2007

//     // Não considerado pela implementação do DTO
//     "id" : 10000000,
//     "status" : "TESTE"
// }