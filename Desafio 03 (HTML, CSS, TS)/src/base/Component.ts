export default abstract class Component {
    private template: HTMLTemplateElement;
    public element: HTMLElement;

    constructor(templateId: string) {
        // Get template by Id
        this.template = document.getElementById(templateId)! as HTMLTemplateElement;

        // Clone template content and get first element child
        this.element = document.importNode(this.template.content, true).firstElementChild! as HTMLElement;
    }
}