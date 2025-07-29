import Component from "../base/Component";
import type { UserType } from "../services/UserService";

export default
class UserForm extends Component{
    constructor(private userData?: UserType){
        super('user-form-template');
        if(userData) this.renderContent();
    }

    renderContent(){
        const nameInput = this.element.querySelector('#username')! as HTMLInputElement;
        nameInput.value = this.userData!.name;
    }
}