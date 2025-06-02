package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Book;
import entities.User;

public class LibraryApp {
    static Scanner read = new Scanner(System.in);
    public static void main(String[] args) {
        List<Book> livros = new ArrayList<>();
        List<User> usuarios = new ArrayList<>();

        while(true){
            int menu = menu();
            if(menu == 8) break;
            switch(menu) {
                case 1:
                System.out.println("Cadastrando livro...");
                    break;
            
                case 2:
                System.out.println("Cadastrando usuário...");
                    break;
            
                case 3:
                System.out.println("Listando livros...");
                    break;
            
                case 4:
                System.out.println("Listando usuários...");
                    break;
            
                case 5:
                System.out.println("Emprestando livro...");
                    break;
            
                case 6:
                System.out.println("Devolvendo livro...");
                    break;
            
                case 7:
                System.out.println("Listando livro por usuário...");
                    break;
            
                default:
                    System.out.println("Opção inválida! Tente novamente: ");
                    break;
            }
        }
    }

    public static int menu(){
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
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        return menu;
    }
}
