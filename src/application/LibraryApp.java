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
            
            if(menu == 3 || menu == 5 || menu == 6){
                if(livros.size() == 0){
                    System.out.println("Nenhum livro cadastrado no sistema!");
                    continue;
                }
            }

            if(menu == 4 || menu == 5 || menu == 6 || menu == 7){
                if(usuarios.size() == 0){
                    System.out.println("Nenhum usuário cadastrado no sistema!");
                    continue;
                }
            }

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
        System.out.println();
        return menu;
    }

    public static void newBook(){
        System.out.println("CADASTRO DE NOVO LIVRO ");
        System.out.print("Título: ");
        String titulo = read.nextLine();

        if(livros.stream().filter(x -> x.getTitulo().toLowerCase().equals(titulo.toLowerCase())).findFirst().orElse(null) != null){
            System.out.println("Esse título já está cadastrado!");
            return;
        }

        System.out.print("Autor: ");
        String autor = read.nextLine();

        System.out.print("Ano de publicação: ");
        int anoPublicacao = read.nextInt();

        livros.add(new Book(titulo, autor, anoPublicacao)); // Adiciona um novo livro com instância direta do novo objeto.
        System.out.println("\nLivro cadastrado com sucesso!");
    }

    public static void listBook(){
        for(Book livro : livros){
            System.out.println(livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor());
            System.err.println("Ano de Publicação: " + livro.getAnoPublicacao());
            System.out.println("Status: " + (livro.isDisponivel() ? "disponível" : "emprestado")); // Imprime o estado atual do livro através de uma condição ternária.
            System.err.println();
        }
    }

    public static void newUser(){
        System.out.println("CADASTRO DE NOVO USUÁRIO ");

        System.out.print("Nome: ");
        String nome = read.nextLine();

        System.out.print("ID: ");
        int id = read.nextInt();

        if(usuarios.stream().filter(x -> x.getId() == id).findFirst().orElse(null) != null){
            System.out.println("Já existe um usuário com esse ID cadastrado");
            return;
        }

        usuarios.add(new User(nome, id)); // Adiciona um novo usuário a lista de usuários com uma instânciação direta do objeto.
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
        Book livro = findBook(read.nextLine()); // Função para buscar livro, retorna referencia do objeto ou nulo.

        if(livro == null){
            System.err.println("Livro não encontrado!");
            return;
        }

        User usuario = findUser(); // Função para buscar usuário, retorna referencia do objeto ou nulo.
        
        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        usuario.adicionarLivroEmprestado(livro); // Realiza o empréstimo
    }

    public static void giveBackBook(){
        System.out.println("REALIZAR DEVOLUÇÃO");
        
        System.out.print("Título do livro: ");
        Book livro = findBook(read.nextLine());

        if(livro == null || livro.isDisponivel()){
            System.err.println("Livro não encontrado ou não está emprestado!");
            return;
        }

        // Varre cada lista de livros emprestados de cada usuário até encontrar para quem está emprestado o livro
        User usuario = null;
        for(User obj_user : usuarios){
            for(Book obj_book : obj_user.getLivrosEmprestados()){
                if(obj_book.equals(livro)){
                    usuario = obj_user;
                    break;
                }
            }
            if(usuario != null) break;
        }

        System.out.println("O livro estava emprestado para " + usuario.getNome());
            
        usuario.removerLivroEmprestado(livro);
    }

    public static void booksUser(){
        User usuario = findUser(); // Função para buscar usuario, retorna referencia do objeto ou nulo.

        if(usuario == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        System.out.println();
        if(usuario.getLivrosEmprestados().size() == 0){ // Se a quantidade de empréstimo do usuário for zero, imprime:
            System.out.println("Esse usuário não possuí livro emprestado.");
            return;
        }

        System.out.printf("Livros emprestados para #%d %s:\n", usuario.getId(), usuario.getNome());
        for(Book livro : usuario.getLivrosEmprestados()) System.out.printf("%s - %s (%d)\n", livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao()); // Para cada livro na lista de empréstimos do usuário, imprimi-lo;
    }

    public static Book findBook(String titulo){
        return livros.stream().filter(x -> x.getTitulo().toLowerCase().equals(titulo.toLowerCase())).findFirst().orElse(null); // Retorna uma referência para a primeira ocorrência de livro que tenha o título igual a da busca, se não encontrar retorna nulo. Para isso, transforma-se tanto a string do objeto quanto da procura para lowercased.
    }

    public static User findUser(){
        String choice;
        while(true){
            System.out.print("Buscar usuário por ID ou nome? ");
            choice = read.nextLine().toLowerCase();
            if(choice.equals("id") || choice.equals("nome")) break;
            else System.out.println("Opção inválida, tente novamente:");
        }

        System.out.printf("Usuário (%s): ", choice);
        if(choice.equals("id")){
            int id = read.nextInt();
            read.nextLine();
            return usuarios.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
             // Retorna uma referência para a primeira ocorrência de usuário que tenha o id igual da busca, se não encontrar retorna nulo.
        }
        else{
            String nome = read.nextLine();
            return usuarios.stream().filter(x -> x.getNome().toLowerCase().equals(nome.toLowerCase())).findFirst().orElse(null);
             // Retorna uma referência para a primeira ocorrência de usuário que tenha o nome igual da busca, se não encontrar retorna nulo. Para isso, transforma-se tanto a string do objeto quanto da procura para lowercased.
        }
    }
}
