package handlers;

import car.Car;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import car.FixtureUserData;
import car.FixtureUserDataType;
import car.GroundAreaType;
import car.Tire;

public class CarContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
            // TODO Auto-generated method stub
            contact(contact, true);
    }

    @Override
    public void endContact(Contact contact) {
            // TODO Auto-generated method stub
            contact(contact, false);
    }

    void contact(Contact contact, boolean began) {

        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a.getUserData() instanceof FixtureUserData
                    && a.getUserData() instanceof FixtureUserData) {

            FixtureUserData fudA = (FixtureUserData) a.getUserData();
            FixtureUserData fudB = (FixtureUserData) b.getUserData();

            if (fudA == null || fudB == null) {
                    return;
            }

            if (fudA.type == FixtureUserDataType.FUD_CAR_TIRE && fudB.type == FixtureUserDataType.FUD_GROUND_AREA) {
                //What happens when the car touches the car    
                tireAndGround(a, b, began);
                    
            } else if (fudA.type == FixtureUserDataType.FUD_GROUND_AREA && fudB.type == FixtureUserDataType.FUD_CAR_TIRE) {
                //What happens when the ground touches the car    
                tireAndGround(b, a, began);
            }
            else if(fudA.type == FixtureUserDataType.FUD_CAR_TIRE && fudB.type == FixtureUserDataType.FUD_FUEL_LANE){
                //What happens when car touches fuel
            }
            else if(fudA.type == FixtureUserDataType.FUD_FUEL_LANE && fudB.type == FixtureUserDataType.FUD_CAR_TIRE){
                //What happens when fuel touches car
            }
            else if(fudA.type == FixtureUserDataType.FUD_CAR_TIRE && fudB.type == FixtureUserDataType.FUD_FINISH_LINE){
                //What happens when car touches finish line
            }
            else if(fudA.type == FixtureUserDataType.FUD_FINISH_LINE && fudB.type == FixtureUserDataType.FUD_CAR_TIRE){
                //What happens when finish line touches car
            }
            
        }
    }

    void tireAndGround(Fixture tireFixture, Fixture groundFixture, boolean began) {
            Tire tire = (Tire) tireFixture.getBody().getUserData();
            GroundAreaType ground = (GroundAreaType) groundFixture.getUserData();
            if (began) {
                tire.addGroundArea(ground);
            } else {
                tire.removeGroundArea(ground);
            }
    }
    
    void addFuel(){
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
            // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
            // TODO Auto-generated method stub

    }

}
