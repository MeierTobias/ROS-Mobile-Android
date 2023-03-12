package com.schneewittchen.rosandroid.widgets.djicamera;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;

import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.ros.internal.message.Message;
import org.ros.internal.message.MessageBuffers;
import org.ros.node.topic.Publisher;

import java.io.IOException;
import java.util.Random;

import sensor_msgs.Image;

public class DjiCameraData  extends BaseData {
    public static final String TAG = "DjiCameraData";

    private Image img;

    public DjiCameraData(){

    }

    @Override
    public Message toRosMessage(Publisher<Message> publisher, BaseEntity widget) {
        int height = 100;
        int width = 100;

        ChannelBufferOutputStream stream = new ChannelBufferOutputStream(MessageBuffers.dynamicBuffer());
        byte[] imageInBytes = new byte[height * width];

        Random r = new Random();
        for (int i = 0; i < (height*width); i++){
            imageInBytes[i] = (byte)r.nextInt(10);
        }

        try {
            stream.write(imageInBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //noinspection UnusedAssignment
        imageInBytes = null;



        //ChannelBuffer buffer = ChannelBuffers.buffer(height * width);
        //Random r = new Random();
        //while (buffer.writableBytes() >= 4) {
        //    buffer.writeInt((byte)r.nextInt(10));
        //}


        sensor_msgs.Image message = (Image) publisher.newMessage();
        message.setHeight(height);
        message.setWidth(width);
        message.setEncoding("rgb8");

        message.setData(stream.buffer().copy());
        //message.setData(buffer);
        return message;
    }
}
