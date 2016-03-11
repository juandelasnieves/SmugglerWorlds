package com.pineapplemelon.smugglerworlds.box2d;

import com.pineapplemelon.smugglerworlds.enums.UserDataType;

/**
 * Created by juandelasnieves on 9/1/16.
 */
public class GroundUserData extends UserData {

    public GroundUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.GROUND;
    }

}
