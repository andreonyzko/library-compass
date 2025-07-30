export default function (title: string, author: string, yearPublication: number) {
    if(!title.trim()) throw new Error('Title is required');
    if(!author.trim()) throw new Error('Author is required');
    if(!yearPublication) throw new Error('Year of publication is required');

    const currentYear = new Date().getFullYear();
    if(yearPublication > currentYear) throw new Error('The year of publication cannot be in the future');
}