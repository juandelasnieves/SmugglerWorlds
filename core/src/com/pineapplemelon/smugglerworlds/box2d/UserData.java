package com.pineapplemelon.smugglerworlds.box2d;

import com.pineapplemelon.smugglerworlds.enums.UserDataType;

/**
 * Created by juandelasnieves on 9/1/16.
 */
public abstract class UserData {

    protected UserDataType userDataType;
    protected float width;
    protected float height;
    protected boolean isFlaggedForDelete;

    public UserData() {

    }
    public UserData(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void delete(){isFlaggedForDelete=true;}

    public Boolean isFlaggedForDelete(){return isFlaggedForDelete;}

}