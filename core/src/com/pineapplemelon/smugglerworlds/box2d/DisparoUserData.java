package com.pineapplemelon.smugglerworlds.box2d;

/**
 * Created by juandelasnieves on 9/1/16.
 */
import com.badlogic.gdx.math.Vector2;
import com.pineapplemelon.smugglerworlds.enums.UserDataType;
import com.pineapplemelon.smugglerworlds.utils.Constants;


public class DisparoUserData extends UserData {

    private Vector2 linearVelocity;
    private String[] textureRegions;

    public DisparoUserData(float width, float height, String[] textureRegions) {
        super(width, height);
        userDataType = UserDataType.DISPARO;
        linearVelocity = Constants.DISPARO_LINEAR_VELOCITY;
        this.textureRegions = textureRegions;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public String[] getTextureRegions() {
        return textureRegions;
    }
}