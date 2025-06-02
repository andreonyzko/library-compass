package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String nome;
    private int id;
    private List<Book> livrosEmprestados;

    public User(String nome, List<User> usuarios) {
        this.nome = nome;
        this.id = usuarios.size()+1;
        this.livrosEmprestados = new ArrayList<>();
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

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void adicionarLivroEmprestado(Book livro){
        if(livro.emprestar()){
            livrosEmprestados.add(livro);
        }
    }

    public void removerLivroEmprestado(Book livro){
        if(livro.devolver()){
            livrosEmprestados.remove(livro);
        }
    }
}
