package com.schneewittchen.rosandroid.widgets.djiactivation;

import com.schneewittchen.rosandroid.model.entities.widgets.PublisherWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.Topic;

public class DjiActivationEntity extends PublisherWidgetEntity {

    private String connectionStatus;
    private String productInfo;

    public DjiActivationEntity(){
        this.width = 8;
        this.height = 2;
        this.topic = new Topic("dji/connection_status", std_msgs.String._TYPE);
        this.connectionStatus = "Click here to register";
        this.productInfo = "Info: App not registered";
        this.immediatePublish = true;
    }

    public void setConnectionStatus(String txt){
        connectionStatus = txt;
    }

    public String getConnectionStatus(){
        return connectionStatus;
    }

    public void setProductInfo(String txt){
        productInfo = txt;
    }

    public String getProductInfo(){
        return productInfo;
    }
}
