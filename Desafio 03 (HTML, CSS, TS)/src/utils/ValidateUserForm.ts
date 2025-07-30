export default function (username: string) {
    if(!username.trim()) throw new Error('Username is required');
}