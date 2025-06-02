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
}
