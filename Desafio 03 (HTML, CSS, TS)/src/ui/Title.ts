import Component from "../base/Component";

export default
class Title extends Component{
    constructor(text: string){
        super('title-template');
        this.element.textContent = text;
    }
}