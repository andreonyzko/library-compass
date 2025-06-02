package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Book;
import entities.User;

public class LibraryApp {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) {
        // Criação das listas de livros e usuários cadastrados
        List<Book> livros = new ArrayList<>();
        List<User> usuarios = new ArrayList<>();

        while(true){
            int menu = menu(); // Chamada da função que mostra o menu e coleta o número da ação desejada
            if(menu == 8) break; // 8- Sair do programa
            switch(menu) {
                case 1: // 1- Cadastrar novo livro
                    newBook(livros);
                    break;
            
                case 2: // 2- Cadastrar novo usuário
                    newUser(usuarios);
                    break;
            
                case 3: // 3- Listar todos os livros
                    listBook(livros);
                    break;
            
                case 4: // 4- Listar todos os usuários
                    listUsers(usuarios);
                    break;
            
                case 5: // 5- Realizar empréstimo
                    toLoan(usuarios, livros);
                    break;
            
                case 6: // 6- Devolver livro
                System.out.println("Devolvendo livro...");
                    break;
            
                case 7: // 7- Listar livros emprestados por usuário
                    booksUser(usuarios);
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

    public static void newBook(List<Book> livros){
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

    public static void listBook(List<Book> livros){
        for(Book livro : livros){
            System.out.println(livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor());
            System.err.println("Ano de Publicação: " + livro.getAnoPublicacao());
            System.out.println("Status: " + (livro.isDisponivel() ? "disponível" : "emprestado")); // Imprime o estado atual do livro através de uma condição ternária, se o método isDisponivel retornar true imprime "disponível", se false "emprestado".
            System.err.println();
        }
    }

    public static void newUser(List<User> usuarios){
        System.out.println("CADASTRO DE NOVO USUÁRIO ");

        System.out.print("Nome: ");
        String nome = read.nextLine();

        usuarios.add(new User(nome, usuarios)); // Adiciona um novo usuário a lista de usuários com uma instânciação direta do objeto, passando a própria lista de usuários para que o construtor pegue seu tamanho da lista e consiga definir o id do novo usuário se forma incremental.
        System.out.println("\nUsuário cadastrado com sucesso!");
    }

    public static void listUsers(List<User> usuarios){
        for(User usuario : usuarios){ // Para cada usuário na lista usuários, imprima:
            System.out.printf("#%d - %s\n", usuario.getId(), usuario.getNome());
        }
    }

    public static void toLoan(List<User> usuarios, List<Book> livros){
        System.out.println("NOVO EMPRÉSTIMO");

        System.out.print("Título do livro: ");
        String busca_livro = read.nextLine();

        Book livro = livros.stream().filter(x -> x.getTitulo().equals(busca_livro)).findFirst().orElse(null); // Retorna uma referência para a primeira ocorrência de livro que tenha o título igual inserido acima, se não encontrar retorna nulo
        if(livro == null){
            System.err.println("Livro não encontrado!");
            return;
        }

        System.out.print("Nome do usuário: ");
        String busca_usuario = read.nextLine();

        User usuario = usuarios.stream().filter(x -> x.getNome().equals(busca_usuario)).findFirst().orElse(null); // Retorna uma referência para a primeira ocorrência de usuário que tenha o nome igual inserido acima, se não encontrar retorna nulo
        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        usuario.adicionarLivroEmprestado(livro); // Realiza o empréstimo
    }   

    public static void booksUser(List<User> usuarios){
        System.out.print("Nome do usuário: ");
        String busca_usuario = read.nextLine();

        User usuario = usuarios.stream().filter(x -> x.getNome().equals(busca_usuario)).findFirst().orElse(null); // Retorna uma referência para a primeira ocorrência de usuário que tenha o nome igual inserido acima, se não encontrar retorna nulo
        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        System.out.println();
        System.out.println("Livros emprestados: ");
        for(Book livro : usuario.getLivrosEmprestados()) System.out.printf("%s - %s (%d)\n", livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao());
    }
}
