import { ResponseHandler } from "../utils/ErrorHandler";
import type { BookType } from "./Types";

const API_URL = 'http://localhost:8080/api/livros';

export default
class BookService{
    static async getAll(): Promise<BookType[]> {
        const res = await fetch(API_URL);
        return res.json();
    }

    static async register(bookJSON: string){
        const resp = await fetch(API_URL, 
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },

                body: bookJSON
            }
        )

        return await ResponseHandler(resp);
    }

    static async update(bookId: number, bookJSON: string){
        const resp = await fetch(`${API_URL}/${bookId}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },

                body: bookJSON
            }
        )

        return await ResponseHandler(resp);
    }

    static async delete(bookId: number){
        const resp = await fetch(`${API_URL}/${bookId}`,
            {
                method: 'DELETE'
            }
        )

        return await ResponseHandler(resp);
    }

    static async loan(bookId: number, userId: number){
        const resp = await fetch(`${API_URL}/${bookId}/emprestar/${userId}`,
            {
                method: 'POST',
            }
        );
        
        return await ResponseHandler(resp);
    }

    static async giveback(bookId: number){
        const resp = await fetch(`${API_URL}/${bookId}/devolver`,
            {
                method: 'POST'
            }
        );

        return await ResponseHandler(resp);
    }
}