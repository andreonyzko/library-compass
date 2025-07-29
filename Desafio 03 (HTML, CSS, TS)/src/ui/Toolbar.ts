import Component from "../base/Component";

export default
class ToolBar extends Component{
    private addBtn: HTMLButtonElement;

    constructor(private addHandler: () => void){
        super('toolbar-template');
        this.addBtn = this.element.querySelector('#add-btn')! as HTMLButtonElement;
        this.configure();
    }

    configure(){
        this.addBtn.addEventListener('click', this.addHandler);
    }
}