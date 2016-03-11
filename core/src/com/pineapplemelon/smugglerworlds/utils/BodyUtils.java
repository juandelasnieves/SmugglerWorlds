package com.pineapplemelon.smugglerworlds.utils;

/**
 * Created by juandelasnieves on 9/1/16.
 */
import com.badlogic.gdx.physics.box2d.Body;
import com.pineapplemelon.smugglerworlds.box2d.UserData;
import com.pineapplemelon.smugglerworlds.enums.UserDataType;

public class BodyUtils {

    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }

    public static boolean bodyInBounds(Body body) {
        UserData userData = (UserData) body.getUserData();

        switch (userData.getUserDataType()) {
            case RUNNER:
            case ENEMY:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
            case DISPARO:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
        }

        return true;
    }

    public static boolean bodyIsEnemy(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }

    public static boolean bodyIsDisparo(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.DISPARO;
    }

}