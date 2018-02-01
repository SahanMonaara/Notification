package com.example.android.testnotification;

import com.orm.SugarRecord;

/**
 * Created by Sahan on 1/29/2018.
 */

public class Data extends SugarRecord {
    //private int notificationCount;
    public int notificationCount;

    public Data(){

    }
    public Data(int notificationCount){
        this.notificationCount=notificationCount;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}