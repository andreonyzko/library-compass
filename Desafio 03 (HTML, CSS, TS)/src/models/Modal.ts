import Component from "../base/Component";

export default class Modal extends Component{
    title: HTMLElement;
    message: HTMLElement;
    confirmBtn: HTMLButtonElement;
    cancelBtn: HTMLButtonElement;

    constructor(){
        super('modal-template');

        this.title = this.element.querySelector('h1')! as HTMLElement;
        this.message = this.element.querySelector('p')! as HTMLElement;
        this.confirmBtn = this.element.querySelector('.confirm-btn')! as HTMLButtonElement;
        this.cancelBtn = this.element.querySelector('.cancel-btn')! as HTMLButtonElement;
    }
}