export default
abstract class Component{
    template: HTMLTemplateElement;
    element: HTMLElement;

    constructor(templateId: string){
        this.template = document.getElementById(templateId)! as HTMLTemplateElement;
        this.element = document.importNode(this.template.content, true).firstElementChild! as HTMLElement;
    }
}