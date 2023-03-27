package com.schneewittchen.rosandroid.model.repositories.rosRepo.node;

import android.util.Log;

import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.entities.widgets.IServiceModel;
import com.schneewittchen.rosandroid.model.entities.widgets.ServiceWidgetEntity;
import com.schneewittchen.rosandroid.utility.Utils;

import org.ros.exception.ServiceException;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.service.ServiceServer;

import java.util.ArrayList;

import std_srvs.EmptyRequest;
import std_srvs.EmptyResponse;

public class ServiceNode extends AbstractNode {

    private String commandNamespace;
    private String serviceModelPath = "";
    private ArrayList<String> commands = new ArrayList<String>();


    @Override
    public void onStart(ConnectedNode parentNode) {
        Log.i(TAG, "On Start:  " + commandNamespace);

        for (String command : commands) {
            ServiceServer<EmptyRequest, EmptyResponse> existing = parentNode.getServiceServer(commandNamespace + "/" + command);
            if (existing == null) {
                parentNode.newServiceServer(
                        commandNamespace + "/" + command,
                        std_srvs.Empty._TYPE,
                        new ServiceResponseBuilder<EmptyRequest, EmptyResponse>() {
                            @Override
                            public void build(std_srvs.EmptyRequest request, std_srvs.EmptyResponse response)
                                    throws ServiceException {
                                Object object = Utils.getObjectFromClassName(serviceModelPath);
                                IServiceModel serviceModel = (IServiceModel) object;
                                serviceModel.runMethodByCommand(command);
                            }
                        });
                Log.i(TAG, "Service created:  " + commandNamespace + "/" + command);
            } else {
                Log.i(TAG, "Service already exists:  " + commandNamespace + "/" + command);
            }
        }
    }

    @Override
    public void setWidget(BaseEntity widget) {
        super.setWidget(widget);
        ServiceWidgetEntity svWidget = (ServiceWidgetEntity) widget;
        commandNamespace = svWidget.commandNamespace;
        serviceModelPath = svWidget.serviceModelPath;
        commands = new ArrayList<String>(svWidget.commands);
    }
}
