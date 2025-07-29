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

    static async loan(bookId: number, userId: number): Promise<any>{
        const resp = await fetch(`${API_URL}/${bookId}/emprestar/${userId}`,
            {
                method: 'POST',
            }
        );
        
        return resp;
    }

    static async giveback(bookId: number): Promise<any> {
        const resp = await fetch(`${API_URL}/${bookId}/devolver`,
            {
                method: 'POST'
            }
        );

        return resp;
    }
}