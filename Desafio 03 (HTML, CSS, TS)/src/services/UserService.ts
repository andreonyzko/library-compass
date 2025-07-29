import type { UserType } from "./Types";
import type { BookType } from "./Types";

const API_URL = 'http://localhost:8080/api/usuarios';

export default
class UserService{
    static async getAll(): Promise<UserType[]>{
        const resp = await fetch(API_URL);
        return resp.json();
    }

    static async register(userJSON: string){
        const resp = await fetch(API_URL,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },

                body: userJSON
            }
        )

        return resp;
    }

    static async update(userId: number, userJSON: string){
        const resp = await fetch(`${API_URL}/${userId}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },

                body: userJSON
            }
        )

        return resp;
    }

    static async delete(userId: number){
        const resp = await fetch(`${API_URL}/${userId}`, 
            {
                method: 'DELETE',
            }
        )

        return resp;
    }

    static async getLoans(userId: number): Promise<BookType[]>{
        const resp = await fetch(`${API_URL}/${userId}/livros-emprestados`);
        return resp.json();
    }
}