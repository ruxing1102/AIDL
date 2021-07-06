package com.example.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.service.Book;
import com.example.service.BookController;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BookController bookController;
    private boolean connected;
    private List<Book> bookList;

    private Button mBtnGetBookList;
    private Button mBtnAddBook;
    private Button mBtnGetBookSize;
    private Button mBtnGetFirstBookName;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookController = BookController.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bookController = null;
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGetBookList = findViewById(R.id.btn_get_book_list);
        mBtnAddBook = findViewById(R.id.btn_add);
        mBtnGetBookSize = findViewById(R.id.btn_get_book_size);
        mBtnGetFirstBookName = findViewById(R.id.btn_first_book_name);

        mBtnGetBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    try {
                        bookList = bookController.getBookList();
                        for (int i = 0; i < bookList.size(); i++) {
                            Log.i("ruxing", "name=" + bookList.get(i).getName());
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mBtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connected) {
                    Book book = new Book("新书");
                    try {
                        bookController.addBook(book);
                        Log.i("ruxing", "向服务器添加了一本新书==="+book.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mBtnGetBookSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected) {
                    try {
                        int size = 0;
                        size = bookController.getInt();
                        Log.i("ruxing", "共有" + size + "本书");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mBtnGetFirstBookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connected) {
                    try {
                        String name = bookController.getString();
                        Log.i("ruxing", "第一本书的书名是：" + name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Intent intent = new Intent();
        intent.setPackage("com.example.service");
        intent.setAction("com.example.service.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(serviceConnection);
        }
    }
}