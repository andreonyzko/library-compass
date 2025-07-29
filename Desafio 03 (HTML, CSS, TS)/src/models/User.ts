import Component from "../base/Component";
import type { UserType } from "../services/UserService";

export default
class User extends Component{
    constructor(
        public root: HTMLElement,
        public userData: UserType
    ){
        super('user-template');
        this.renderContent();
    }

    renderContent(){
        this.element.querySelector('.username')!.textContent = this.userData.name;

        this.attach();
    }

    attach(){
        this.root.appendChild(this.element);
    }
}