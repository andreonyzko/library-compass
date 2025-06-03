package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int id;
    private List<Book> borrowedBooks;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new ArrayList<>(); // Instancia uma nova lista de livros vazia, assim o cadastro do usuário inicia com nenhum empréstimo na biblioteca
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Removido o setter de Livros Emprestados, uma vez que a manipulação da lista será feito pelos métodos de emprestar e devolver do usuário, além do setId.

    public void adicionarLivroEmprestado(Book book){
        if(book.lend()){ // A função livro.emprestar verifica a disponibilidade, se disponível inverte para emprestado, imprime uma mensagem e retorna true, então o livro é adicionado a lista de empréstimo do usuário
            borrowedBooks.add(book);
        }
    }

    public void removerLivroEmprestado(Book book){
        if(book.giveBack()){ // A função livro.devolver verifica disponibilidade, se emprestado inverte para disponível, imprime uma mensagem e retorna true, então o livro é retirado da lista de empréstimos do usuário
            borrowedBooks.remove(book);
        }
    }

    @Override
    public String toString(){
        return "#" + id + " - " + name;
    }
}
