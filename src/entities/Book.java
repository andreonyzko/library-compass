package entities;

public class Book {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private boolean disponivel;

    public Book(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.disponivel = true; // Construtor atribui todo novo livro instanciado como disponível
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
        if(disponivel){ // Se estiver disponível, inverte sua disponibilidade e imprime uma mensagem de empréstimo realizado
            disponivel = false;
            System.out.println("Livro emprestado!");
            return true;
        }
        else{ // Se não estiver disponível imprime uma mensagem de indisponiblidade
            System.out.println("Esse livro não está disponível!");
            return false;
        }
    }

    public boolean devolver(){
        if(!disponivel){ // Se NÃO estiver disponível, inverte a disponibilidade e imprime uma mensagem de devolução realizada
            disponivel = true;
            System.out.println("Livro devolvido!");
            return true;
        }
        else{ // Se já estiver disponível, imprime uma mensagem de que o livro já estava disponível
            System.out.println("Esse livro já está disponível!");
            return false;
        }
    }
}
