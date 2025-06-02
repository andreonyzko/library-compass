package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Book;
import entities.User;

public class LibraryApp {
    static Scanner read = new Scanner(System.in);
    static List<Book> livros = new ArrayList<>();
    static List<User> usuarios = new ArrayList<>();
    public static void main(String[] args) {
        while(true){
            int menu = menu(); // Chamada da função que mostra o menu e coleta o número da ação desejada
            if(menu == 8) break; // 8- Sair do programa
            switch(menu) {
                case 1: // 1- Cadastrar novo livro
                    newBook();
                    break;
            
                case 2: // 2- Cadastrar novo usuário
                    newUser();
                    break;
            
                case 3: // 3- Listar todos os livros
                    listBook();
                    break;
            
                case 4: // 4- Listar todos os usuários
                    listUsers();
                    break;
            
                case 5: // 5- Realizar empréstimo
                    toLoan();
                    break;
            
                case 6: // 6- Devolver livro
                    giveBackBook();
                    break;
            
                case 7: // 7- Listar livros emprestados por usuário
                    booksUser();
                    break;
            
                default: // Opção inválida
                    System.out.println("Opção inválida! Tente novamente: ");
                    break;
            }
        }
    }

    public static int menu(){
        System.err.println();
        System.out.println("=-=-=-=-=-=- Menu -=-=-=-=-=-=-=");
        System.out.println("1- Cadastrar novo livro");
        System.out.println("2- Cadastrar novo usuário");
        System.out.println("3- Listar todos os livros");
        System.out.println("4- Listar todos os usuários");
        System.out.println("5- Realizar empréstimo");
        System.out.println("6- Devolver livro");
        System.out.println("7- Listar livros emprestados por usuário");
        System.out.println("8- Sair do programa");
        System.out.print("Ação: ");
        int menu = read.nextInt();
        read.nextLine();
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.err.println();
        return menu;
    }

    public static void newBook(){
        System.out.println("CADASTRO DE NOVO LIVRO ");
        System.out.print("Título: ");
        String titulo = read.nextLine();

        System.out.print("Autor: ");
        String autor = read.nextLine();

        System.out.print("Ano de publicação: ");
        int anoPublicacao = read.nextInt();

        livros.add(new Book(titulo, autor, anoPublicacao)); // Adiciona um novo livro com instância direta do novo objeto passando os parametros necessários
        System.out.println("\nLivro cadastrado com sucesso!");
    }

    public static void listBook(){
        for(Book livro : livros){
            System.out.println(livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor());
            System.err.println("Ano de Publicação: " + livro.getAnoPublicacao());
            System.out.println("Status: " + (livro.isDisponivel() ? "disponível" : "emprestado")); // Imprime o estado atual do livro através de uma condição ternária, se o método isDisponivel retornar true imprime "disponível", se false "emprestado".
            System.err.println();
        }
    }

    public static void newUser(){
        System.out.println("CADASTRO DE NOVO USUÁRIO ");

        System.out.print("Nome: ");
        String nome = read.nextLine();

        usuarios.add(new User(nome, usuarios)); // Adiciona um novo usuário a lista de usuários com uma instânciação direta do objeto, passando a própria lista de usuários para que o construtor pegue seu tamanho da lista e consiga definir o id do novo usuário se forma incremental.
        System.out.println("\nUsuário cadastrado com sucesso!");
    }

    public static void listUsers(){
        for(User usuario : usuarios){ // Para cada usuário na lista usuários, imprima:
            System.out.printf("#%d - %s\n", usuario.getId(), usuario.getNome());
        }
    }

    public static void toLoan(){
        System.out.println("NOVO EMPRÉSTIMO");

        System.out.print("Título do livro: ");
        Book livro = findBook(read.nextLine());

        if(livro == null){
            System.err.println("Livro não encontrado!");
            return;
        }

        System.out.print("Nome do usuário: ");
        User usuario = findUser(read.nextLine());
        
        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        usuario.adicionarLivroEmprestado(livro); // Realiza o empréstimo
    }

    public static void giveBackBook(){
        System.out.println("REALIZAR DEVOLUÇÃO");

        System.out.print("Nome do usuário: ");
        User usuario = findUser(read.nextLine());
        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }
        
        System.out.print("Título do livro: ");
        Book livro = findBook(read.nextLine());

        if(livro == null){
            System.err.println("Livro não encontrado nos empréstimos do usuário!");
            return;
        }

        usuario.removerLivroEmprestado(livro);
    }

    public static void booksUser(){
        System.out.print("Nome do usuário: ");
        User usuario = findUser(read.nextLine());

        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        System.out.println();
        if(usuario.getLivrosEmprestados().size() == 0){
            System.out.println("Esse usuário não possuí livro emprestado.");
            return;
        }

        System.out.println("Livros emprestados: ");
        for(Book livro : usuario.getLivrosEmprestados()) System.out.printf("%s - %s (%d)\n", livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao()); // Para cada livro na lista de empréstimos do usuário, imprimi-lo;
    }

    public static Book findBook(String titulo){
        return livros.stream().filter(x -> x.getTitulo().toLowerCase().equals(titulo.toLowerCase())).findFirst().orElse(null); // Retorna uma referência para a primeira ocorrência de livro que tenha o título igual a da busca, se não encontrar retorna nulo. Para isso, transforma-se tanto a string do objeto quanto da procura para lowercased.
    }

    public static User findUser(String nome){
        return usuarios.stream().filter(x -> x.getNome().toLowerCase().equals(nome.toLowerCase())).findFirst().orElse(null); 
        // Retorna uma referência para a primeira ocorrência de usuário que tenha o nome igual da busca, se não encontrar retorna nulo. Para isso, transforma-se tanto a string do objeto quanto da procura para lowercased.
    }
}
