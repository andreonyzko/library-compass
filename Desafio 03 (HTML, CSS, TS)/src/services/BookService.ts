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

        return resp;
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

        return resp;
    }

    static async delete(bookId: number){
        const resp = await fetch(`${API_URL}/${bookId}`,
            {
                method: 'DELETE'
            }
        )

        return resp;
    }

    static async loan(bookId: number, userId: number){
        const resp = await fetch(`${API_URL}/${bookId}/emprestar/${userId}`,
            {
                method: 'POST',
            }
        );
        
        return resp;
    }

    static async giveback(bookId: number){
        const resp = await fetch(`${API_URL}/${bookId}/devolver`,
            {
                method: 'POST'
            }
        );

        return resp;
    }
}