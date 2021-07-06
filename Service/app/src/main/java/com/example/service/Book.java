package com.example.service;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String name;

    public Book(String name) {
        this.name=name;
    }

    protected Book(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    /**
     * 这个方法是自己写上去的，并不是根据错误提示自动生成的代码
     * 默认生成的模板类的对象只支持为in的定向tag。因为默认生成的类里面只有 writeToParcel() 方法。
     * 而如果要支持为out或者inout的定向tag的话，还需要实现readFromParcel()方法。
     * 而这个方法其实并没有在 Parcelable 接口里面，所以需要我们从头写。
     */
    public void readFromParcel(Parcel dest){
        name=dest.readString();
    }
}
