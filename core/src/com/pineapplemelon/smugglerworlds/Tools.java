package com.pineapplemelon.smugglerworlds;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by juandelasnieves on 11/3/16.
 */
public class Tools {
    public static class ProjectileEquation {

        public float gravity;
        public Vector2 startVelocity = new Vector2();
        public Vector2 startPoint = new Vector2();

        public float getX(float t) {
            return startVelocity.x * t + startPoint.x;
        }

        public float getY(float t) {
            return 0.5f * gravity * t * t + startVelocity.y * t + startPoint.y;
        }

        public float getTForGivenX(float x) {
            // x = startVelocity.x * t + startPoint.x
            // x - startPoint.x = startVelocity.x * t
            // t = (x - startPoint.x) / (startVelocity.x);
            return (x - startPoint.x) / (startVelocity.x);
        }

    }

    public static class Controller {

        public float power = 50f;
        public float angle = 0f;
        public boolean charging = false;

        public boolean fixedHorizontalDistance = true;

    }

    public static class ControllerLogic {

        public boolean wasPressed;
        public Controller controller;

        final Vector2 pressedPosition = new Vector2();
        final Vector2 currentPosition = new Vector2();
        final Vector2 tmp = new Vector2();

        public ControllerLogic(Controller controller, Vector2 slingshotPosition) {
            this.controller = controller;
            wasPressed = false;
            this.pressedPosition.set(slingshotPosition);
        }
    }

    public static class TrajectoryActor extends Actor {

        public Controller controller;
        public ProjectileEquation projectileEquation;
        public Sprite trajectorySprite;

        public int trajectoryPointCount = 50;
        public float timeSeparation = 1f;

        public TrajectoryActor(Controller controller, float gravity, Sprite trajectorySprite) {
            this.controller = controller;
            this.trajectorySprite = trajectorySprite;
            this.projectileEquation = new ProjectileEquation();
            this.projectileEquation.gravity = gravity;
        }
    }
}
