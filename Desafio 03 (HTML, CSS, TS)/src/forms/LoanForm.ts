import Component from "../base/Component";
import UserService from "../services/UserService";

export default
class LoanForm extends Component{
    constructor(){
        super('loan-form-template');

        this.renderContent();
    }

    async renderContent(){
        const userSelectEl = this.element.querySelector('select')! as HTMLSelectElement;
        const users = await UserService.getAll();
        
        users.forEach(user => {
            const optionEl = document.createElement('option');
            optionEl.value = user.id.toString();
            optionEl.textContent = user.name;
            userSelectEl.appendChild(optionEl);
        });
    }
}