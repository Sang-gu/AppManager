package com.test.appmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class Item {  // adapter에 넣어줄 형식 지정 클래스

    String name;
    Drawable image;
    Intent intent;
    Context context;

    public Item(String name, Drawable image, Intent intent, Context context) {
        super();
        this.name = name;
        this.image = image;
        this.intent = intent;
        this.context = context;
    }

}