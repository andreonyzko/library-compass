import type { BookStatus } from "../utils/BookStatus";

export interface BookType{
    id: number;
    title: string;
    author: string;
    yearPublication: number;
    status: BookStatus
}

export interface UserType{
    id: number;
    name: string;
}