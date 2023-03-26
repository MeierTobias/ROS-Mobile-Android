package com.schneewittchen.rosandroid.model.entities.widgets;


import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.ServiceData;
import com.schneewittchen.rosandroid.ui.general.Position;

import java.util.ArrayList;

public abstract class ServiceWidgetEntity
        extends BaseEntity
        implements IPositionEntity, IServiceEntity {

    public String commandNamespace;
    public ArrayList<ServiceData> serviceCollection = new ArrayList<ServiceData>();
    public int posX;
    public int posY;
    public int width;
    public int height;

    public ServiceWidgetEntity() {
        super();
    }

    public ServiceWidgetEntity(ServiceWidgetEntity entity){
        super();
        this.commandNamespace = entity.commandNamespace;
        this.serviceCollection = new ArrayList<ServiceData>(entity.serviceCollection);
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.width = entity.width;
        this.height = entity.height;
    }

    @Override
    public boolean equalRosState(BaseEntity other) {
        if (!super.equalRosState(other)) {
            return false;
        }

        if (!(other instanceof ServiceWidgetEntity)) {
            return false;
        }

        ServiceWidgetEntity otherPub = (ServiceWidgetEntity) other;

        return this.commandNamespace.equals(otherPub.commandNamespace);
    }

    @Override
    public Position getPosition() {
        return new Position(posX, posY, width, height);
    }

    @Override
    public void setPosition(Position position) {
        this.posX = position.x;
        this.posY = position.y;
        this.width = position.width;
        this.height = position.height;
    }
}
