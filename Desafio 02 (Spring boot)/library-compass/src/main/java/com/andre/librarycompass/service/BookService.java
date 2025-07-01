package com.andre.librarycompass.service;

import com.andre.librarycompass.entity.Book;
import com.andre.librarycompass.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractService<Book> {

    @Autowired
    public BookService(BookRepository bookRepository) {
        super(bookRepository);
    }
}
