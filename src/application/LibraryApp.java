package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Book;
import entities.User;
import entities.enums.BookStatus;

public class LibraryApp {
    static Scanner read = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    public static void main(String[] args) {
        while(true){
            int menu = menu(); // Chamada da função que mostra o menu e coleta o número da ação desejada
            if(menu == 8) break; // 8- Sair do programa
            
            if((menu == 3 || menu == 5 || menu == 6) && books.size() == 0){
                System.out.println("Nenhum livro cadastrado no sistema!");
                continue;
            }

            if((menu == 4 || menu == 5 || menu == 6 || menu == 7) && users.size() == 0){
                System.out.println("Nenhum usuário cadastrado no sistema!");
                continue;
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
        String title = read.nextLine();

        // Tenta encontrar um livro já cadastrado com este título, o ideal é retornar nulo, ou seja, não ter
        if(books.stream().filter(x -> x.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null) != null){
            System.out.println("Esse título já está cadastrado!");
            return;
        }

        System.out.print("Autor: ");
        String author = read.nextLine();

        System.out.print("Ano de publicação: ");
        int yearPublication = read.nextInt();

        books.add(new Book(title, author, yearPublication)); // Adiciona um novo livro com instância direta do novo objeto.
        System.out.println("\nLivro cadastrado com sucesso!");
    }

    public static void listBook(){
        for(Book book : books){
            System.out.println(book.toString());
        }
    }

    public static void newUser(){
        System.out.println("CADASTRO DE NOVO USUÁRIO ");

        System.out.print("Nome: ");
        String name = read.nextLine();

        users.add(new User(name, users.size()+1)); // Adiciona um novo usuário a lista de usuários com uma instânciação direta do objeto, passando o tamanho da lista para definir os id's de forma incremental.
        System.out.println("\nUsuário cadastrado com sucesso!");
    }

    public static void listUsers(){
        for(User user : users){ // Para cada usuário na lista usuários, imprima:
            System.out.println(user.toString());
        }
    }

    public static void toLoan(){
        System.out.println("NOVO EMPRÉSTIMO");

        System.out.print("Título do livro: ");
        Book book = findBook(read.nextLine()); // Função para buscar livro, retorna referencia do objeto ou nulo.

        if(book == null){
            System.err.println("Livro não encontrado!");
            return;
        }

        User user = findUser(); // Função para buscar usuário, retorna referencia do objeto ou nulo.
        
        if(user == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        user.adicionarLivroEmprestado(book); // Realiza o empréstimo
    }

    public static void giveBackBook(){
        System.out.println("REALIZAR DEVOLUÇÃO");
        
        System.out.print("Título do livro: ");
        Book book = findBook(read.nextLine());

        if(book == null){
            System.out.println("Livro não encontrado!");
            return;
        }

        if(book.getDisponivel() == BookStatus.DISPONIVEL){
            System.out.println("Esse livro não está emprestado!");
            return;
        }

        // Varre cada lista de livros emprestados de cada usuário até encontrar para quem está emprestado o livro
        User user = null;
        for(User obj_user : users){
            for(Book obj_book : obj_user.getBorrowedBooks()){
                if(obj_book.equals(book)){
                    user = obj_user;
                    break;
                }
            }
            if(user != null) break;
        }
            
        user.removerLivroEmprestado(book);

        System.out.printf("%s devolveu o livro '%s'\n", user.getName(), book.getTitle());
    }

    public static void booksUser(){
        User user = findUser(); // Função para buscar usuario, retorna referencia do objeto ou nulo.

        if(user == null){
            System.err.println("Usuário não encontrado!");
            return;
        }

        System.out.println();
        if(user.getBorrowedBooks().size() == 0){ // Se a quantidade de empréstimo do usuário for zero, imprime:
            System.out.println("Esse usuário não possuí livro emprestado.");
            return;
        }

        System.out.printf("%s (%d EMPRÉSTIMO%s):\n", 
        user.toString().replace(" - ", " ").toUpperCase(), 
        user.getBorrowedBooks().size(),
        user.getBorrowedBooks().size() > 1 ? "S" : ""
        );

        for(Book book : user.getBorrowedBooks()) System.out.println(book.toStringOneLine()); // Para cada livro na lista de empréstimos do usuário, imprimi-lo;
    }

    public static Book findBook(String title){
        return books.stream().filter(x -> x.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null); // Retorna uma referência para a primeira ocorrência de livro que tenha o título igual a da busca, se não encontrar retorna nulo. Para isso, ignora-se as diferenças de minúscula e maísculas.
    }

    public static User findUser(){
        System.out.print("Usuário (ID): ");
        int id = read.nextInt();
        read.nextLine();
        return users.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        // Retorna uma referência para a primeira ocorrência de usuário que tenha o id igual da busca, se não encontrar retorna nulo.
    }
}
