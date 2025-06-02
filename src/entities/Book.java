package entities;

public class Book {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel;

    public Book(String titulo, String autor, int anoPublicacao, boolean disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = disponivel;
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

    public boolean isDisponivel() {
        return disponivel;
    }

    public boolean emprestar(){
        if(disponivel){
            disponivel = false;
            System.out.println("Livro emprestado!");
            return true;
        }
        else{
            System.out.println("Esse livro não está disponível!");
            return false;
        }
    }

    public boolean devolver(){
        if(!disponivel){
            disponivel = true;
            System.out.println("Livro devolvido!");
            return true;
        }
        else{
            System.out.println("Esse livro já está disponível!");
            return false;
        }
    }
}
