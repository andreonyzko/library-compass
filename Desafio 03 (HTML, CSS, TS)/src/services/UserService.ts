import type { BookType } from "./BookService";

export interface UserType{
    id: number;
    name: string;
}

const API_URL = 'http://localhost:8080/api/usuarios';

export default
class UserService{
    static async getAll(): Promise<UserType[]>{
        const resp = await fetch(API_URL);
        return resp.json();
    }

    static async getLoans(userId: number): Promise<BookType[]>{
        const resp = await fetch(`${API_URL}/${userId}/livros-emprestados`);
        return resp.json();
    }
}