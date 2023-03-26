package com.schneewittchen.rosandroid.model.repositories.rosRepo.node;

import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.entities.widgets.ServiceWidgetEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.ServiceData;

import org.ros.exception.ServiceException;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.service.ServiceServer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import std_srvs.EmptyRequest;
import std_srvs.EmptyResponse;

public class ServiceNode extends AbstractNode {

    private String commandNamespace;
    private ArrayList<ServiceData> serviceCollection = new ArrayList<ServiceData>();


    @Override
    public void onStart(ConnectedNode parentNode) {
        Log.i(TAG, "On Start:  " + commandNamespace);

        for (ServiceData sd : serviceCollection) {
            ServiceServer<EmptyRequest, EmptyResponse> existing = parentNode.getServiceServer(commandNamespace + "/" + sd.getCommand());
            if (existing == null) {
                parentNode.newServiceServer(
                        commandNamespace + "/" + sd.getCommand(),
                        std_srvs.Empty._TYPE,
                        new ServiceResponseBuilder<EmptyRequest, EmptyResponse>() {
                            @Override
                            public void build(std_srvs.EmptyRequest request, std_srvs.EmptyResponse response)
                                    throws ServiceException {
                                try {
                                    sd.getCommandMethodRef().invoke((null));
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                } catch (Exception e) {
                                    Log.e(e.getClass().getSimpleName(), e.getMessage());
                                }
                            }
                        });
                Log.i(TAG, "Service created:  " + commandNamespace + "/" + sd.getCommand());
            } else {
                Log.i(TAG, "Service already exists:  " + commandNamespace + "/" + sd.getCommand());
            }
        }
    }

    @Override
    public void setWidget(BaseEntity widget) {
        super.setWidget(widget);
        ServiceWidgetEntity svWidget = (ServiceWidgetEntity) widget;
        commandNamespace = svWidget.commandNamespace;
        serviceCollection = new ArrayList<ServiceData>(svWidget.serviceCollection);
    }
}
