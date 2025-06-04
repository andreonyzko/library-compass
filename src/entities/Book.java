package entities;

import entities.enums.BookStatus;

public class Book {
    private String title;
    private String author;
    private int yearPublication;
    private BookStatus available;

    public Book(String title, String author, int yearPublication) {
        this.title = title;
        this.author = author;
        this.yearPublication = yearPublication;
        this.available = BookStatus.DISPONIVEL; // Construtor atribui todo novo livro instanciado como disponível
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAutor(String author) {
        this.author = author;
    }

    public int getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(int yearPublication) {
        this.yearPublication = yearPublication;
    }

    public BookStatus getDisponivel() {
        return available;
    }

    // Removido o setDisponivel, uma vez que a manipulação será feita através dos métodos emprestar e devolver.

    @Override // Sobreescrita do método toString da biblioteca padrão do java
    public String toString(){
        StringBuilder response = new StringBuilder(); // Cria uma nova string com o StringBuilder

        // Estruturando a string
        response.append(title + "\n");
        response.append("Autor: " + author + "\n");
        response.append("Ano de Publicação: " + yearPublication + "\n");
        response.append("Status: " + available.toString().toLowerCase() + "\n");
        
        return response.toString();
    }

    public String toStringOneLine(){
        return title + " - " + author + " (" + yearPublication + ")";
    }

    public boolean lend(){
        if(available == BookStatus.DISPONIVEL){ // Se estiver disponível, inverte sua disponibilidade e imprime uma mensagem de empréstimo realizado
            available = BookStatus.EMPRESTADO;
            System.out.println("Livro emprestado!\n");
            return true;
        }
        else{ // Se não estiver disponível imprime uma mensagem de indisponiblidade
            System.out.println("Esse livro não está disponível!\n");
            return false;
        }
    }

    public boolean giveBack(){
        if(available == BookStatus.EMPRESTADO){ // Se estiver emprestado, inverte a disponibilidade e imprime uma mensagem de devolução realizada
            available = BookStatus.DISPONIVEL;
            return true;
        }
        else{ // Se já estiver disponível, imprime uma mensagem de que o livro já estava disponível
            return false;
        }
    }
}
