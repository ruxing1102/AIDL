// BookController.aidl
package com.example.service;

import com.example.service.Book;

// Declare any non-default types here with import statements

interface BookController {

    int getInt();//int类型
    String getString();//String类型
    List<Book> getBookList();//aidl对象
    void addBook(inout Book book);//aidl对象

}
