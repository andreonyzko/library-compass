package entities;

import entities.enums.BookStatus;

public class Book {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private BookStatus disponivel;

    public Book(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = BookStatus.DISPONIVEL; // Construtor atribui todo novo livro instanciado como disponível
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public BookStatus getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(BookStatus disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isDisponivel() {
        return (disponivel == BookStatus.DISPONIVEL ? true : false);
    }

    @Override
    public String toString(){
        StringBuilder resposta = new StringBuilder(); // Cria uma nova string com o StringBuilder

        // Estruturando a string
        resposta.append(titulo + "\n");
        resposta.append("Autor: " + autor + "\n");
        resposta.append("Ano de Publicação: " + anoPublicacao + "\n");
        resposta.append("Status: " + disponivel.toString().toLowerCase() + "\n");
        
        return resposta.toString();
    }

    public String toStringOneLine(){
        return titulo + " - " + autor + " (" + anoPublicacao + ")";
    }

    public boolean emprestar(){
        if(disponivel == BookStatus.DISPONIVEL){ // Se estiver disponível, inverte sua disponibilidade e imprime uma mensagem de empréstimo realizado
            disponivel = BookStatus.EMPRESTADO;
            System.out.println("Livro emprestado!");
            return true;
        }
        else{ // Se não estiver disponível imprime uma mensagem de indisponiblidade
            System.out.println("Esse livro não está disponível!");
            return false;
        }
    }

    public boolean devolver(){
        if(disponivel == BookStatus.EMPRESTADO){ // Se estiver emprestado, inverte a disponibilidade e imprime uma mensagem de devolução realizada
            disponivel = BookStatus.DISPONIVEL;
            System.out.println("Livro devolvido!");
            return true;
        }
        else{ // Se já estiver disponível, imprime uma mensagem de que o livro já estava disponível
            System.out.println("Esse livro já está disponível!");
            return false;
        }
    }
}
