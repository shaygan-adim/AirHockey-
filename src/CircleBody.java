import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Random;

public class CircleBody {
    private float mass,radius;
    private Body body;

    CircleBody(float mass , float radius , float initialX , float initialY , World world,short category, short mask){
        this.mass = mass;
        this.radius = radius;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(initialX, initialY);
        this.body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;
        MassData massData = new MassData();
        massData.mass = this.mass;
        body.createFixture(fixtureDef);
        body.setMassData(massData);
    }
    CircleBody(float mass , float radius , float initialX , float initialY , World world,short category){
        this.mass = mass;
        this.radius = radius;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(initialX, initialY);
        this.body = world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits = category;
        MassData massData = new MassData();
        massData.mass = this.mass;
        body.createFixture(fixtureDef);
        body.setMassData(massData);
    }

    void setX(float x) {
        this.body.setTransform(new Vec2(x,this.body.getPosition().y),0);
    }

    void setY(float y) {
        this.body.setTransform(new Vec2(this.body.getPosition().x,y),0);
    }

    float getX(){
        return this.body.getPosition().x;
    }
    float getY(){
        return this.body.getPosition().y;
    }

    float getVx() {
        return this.body.getLinearVelocity().x;
    }

    void setVx(float vx) {
        this.body.setLinearVelocity(new Vec2(vx,this.body.getLinearVelocity().y));
    }

    float getVy() {
        return this.body.getLinearVelocity().y;
    }

    void setVy(float vy) {
        this.body.setLinearVelocity(new Vec2(this.body.getLinearVelocity().x,vy));
    }

    public float getMass() {
        return mass;
    }

    public float getRadius() {
        return radius;
    }

    public Body getBody(){
        return this.body;
    }
}
