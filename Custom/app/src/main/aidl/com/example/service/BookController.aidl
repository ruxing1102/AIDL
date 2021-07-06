// BookController.aidl
package com.example.service;

import com.example.service.Book;

// Declare any non-default types here with import statements

interface BookController {

    int getInt();
    String getString();
    List<Book> getBookList();
    void addBook(inout Book book);

}
