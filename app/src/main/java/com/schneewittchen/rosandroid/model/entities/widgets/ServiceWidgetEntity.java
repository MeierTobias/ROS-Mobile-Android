package com.schneewittchen.rosandroid.model.entities.widgets;


import com.schneewittchen.rosandroid.ui.general.Position;

import java.lang.reflect.Method;

public abstract class ServiceWidgetEntity
        extends BaseEntity
        implements IPositionEntity, IServiceEntity {

    public String command;
    public Method commandMethodRef;
    public int posX;
    public int posY;
    public int width;
    public int height;


    @Override
    public boolean equalRosState(BaseEntity other) {
        if (!super.equalRosState(other)) {
            return false;
        }

        if (!(other instanceof ServiceWidgetEntity)) {
            return false;
        }

        ServiceWidgetEntity otherPub = (ServiceWidgetEntity) other;

        return this.command == otherPub.command
                && this.commandMethodRef == otherPub.commandMethodRef;
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
