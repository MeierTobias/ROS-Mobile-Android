package com.schneewittchen.rosandroid.model.repositories.rosRepo.node;

import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.entities.widgets.ServiceWidgetEntity;

import org.ros.exception.ServiceException;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import std_srvs.EmptyRequest;
import std_srvs.EmptyResponse;

public class ServiceNode extends AbstractNode {

    private String command;
    private Method commandMethodRef;


    @Override
    public void onStart(ConnectedNode parentNode) {

        parentNode.newServiceServer(
                command,
                std_srvs.Empty._TYPE,
                new ServiceResponseBuilder<EmptyRequest, EmptyResponse>() {
                    @Override
                    public void build(std_srvs.EmptyRequest request, std_srvs.EmptyResponse response)
                            throws ServiceException {
                        try {
                            commandMethodRef.invoke((null));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

    }

    @Override
    public void setWidget(BaseEntity widget) {
        super.setWidget(widget);
        ServiceWidgetEntity svWidget = (ServiceWidgetEntity) widget;
        command = svWidget.command;
        commandMethodRef = svWidget.commandMethodRef;
    }
}
