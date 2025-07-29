import { BookStatus } from "../utils/BookStatus";

export interface BookType{
    id: number;
    title: string;
    author: string;
    yearPublication: number;
    status: BookStatus
}

const API_URL = 'http://localhost:8080/api/livros';

export default
class BookService{
    static async getAll(): Promise<BookType[]> {
        const res = await fetch(API_URL);
        return res.json();
    }
}