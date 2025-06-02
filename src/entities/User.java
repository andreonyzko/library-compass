package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String nome;
    private int id;
    private List<Book> livrosEmprestados;

    public User(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.livrosEmprestados = new ArrayList<>(); // Instancia uma nova lista de livros vazia, assim o cadastro do usuário inicia com nenhum empréstimo na biblioteca
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public List<Book> getLivrosEmprestados() {
        return livrosEmprestados;
    }

    // Removido o setter de Livros Emprestados, uma vez que a manipulação da lista será feito pelos métodos de emprestar e devolver do usuário, além do setId.

    public void adicionarLivroEmprestado(Book livro){
        if(livro.emprestar()){ // A função livro.emprestar verifica a disponibilidade, se disponível inverte para emprestado, imprime uma mensagem e retorna true, então o livro é adicionado a lista de empréstimo do usuário
            livrosEmprestados.add(livro);
        }
    }

    public void removerLivroEmprestado(Book livro){
        if(livro.devolver()){ // A função livro.devolver verifica disponibilidade, se emprestado inverte para disponível, imprime uma mensagem e retorna true, então o livro é retirado da lista de empréstimos do usuário
            livrosEmprestados.remove(livro);
        }
    }

    @Override
    public String toString(){
        return "#" + id + " - " + nome;
    }
}
