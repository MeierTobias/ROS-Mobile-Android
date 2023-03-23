package com.schneewittchen.rosandroid.widgets.lockorientation;

import com.schneewittchen.rosandroid.model.entities.widgets.SilentWidgetEntity;

public class LockOrientationEntity extends SilentWidgetEntity {
    public String onText;
    public String offText;

    public LockOrientationEntity() {
        this.width = 3;
        this.height = 1;
        this.onText = "Locked";
        this.offText = "Unlocked";
    }
}
