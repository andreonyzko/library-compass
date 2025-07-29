import Component from "../base/Component";
import BookService from "../services/BookService";
import UserService from "../services/UserService";

export default
class LoanForm extends Component{
    private form: HTMLFormElement;
    private selectEl: HTMLSelectElement;

    constructor(private bookId: number){
        super('loan-form-template');
        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.selectEl = this.element.querySelector('select')! as HTMLSelectElement;

        this.renderContent();
    }

    async renderContent(){
        const users = await UserService.getAll();
        
        users.forEach(user => {
            const optionEl = document.createElement('option');
            optionEl.value = user.id.toString();
            optionEl.textContent = user.name;
            this.selectEl.appendChild(optionEl);
        });

        this.configure();
    }

    configure(){
        this.form.addEventListener('submit', async e => {
            e.preventDefault();
            const userId = +this.selectEl.value;

            BookService.loan(this.bookId, userId);
        })
    }
}