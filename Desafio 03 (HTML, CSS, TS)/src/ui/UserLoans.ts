import Component from "../base/Component";
import BookLoaned from "../models/BookLoaned";
import type { BookType } from "../services/Types";
import UserService from "../services/UserService";

export default
class UserLoans extends Component{
    constructor(private userId: number){
        super('books-template');
        this.getLoans();
    }

    async getLoans(){
        const loans = await UserService.getLoans(this.userId);
        if(loans.length > 0) this.renderLoans(loans);
    }

    renderLoans(loans: BookType[]){
        this.element.innerHTML = '';

        loans.forEach(book => {
            new BookLoaned(this.element, book);
        })
    }
}