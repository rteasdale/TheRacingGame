package handlers;

import car.Car;
import car.CarType;
import car.FinishLineType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import car.FixtureUserData;
import car.FixtureUserDataType;
import car.FuelAreaType;
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
                System.out.println("tire and Ground Accessed");    
                tireAndGround(a, b, began);
                    
            } else if (fudA.type == FixtureUserDataType.FUD_GROUND_AREA && fudB.type == FixtureUserDataType.FUD_CAR_TIRE) {
                //What happens when the ground touches the car    
                System.out.println("tire and Ground Accessed");    
                tireAndGround(b, a, began);
            }
            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_FUEL_LANE){
                System.out.println("CarAndFuel accessed");
            carAndFuel(a, b, began);
            }
            else if(fudA.type == FixtureUserDataType.FUD_FUEL_LANE && fudB.type == FixtureUserDataType.FUD_CAR){
                System.out.println("CarAndFuel accessed");
             carAndFuel(b, a, began);
            }
            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_FINISH_LINE){
                System.out.println("CarAndFinishLine accessed");
               carAndFinishLine(a,b);
            }
            else if(fudA.type == FixtureUserDataType.FUD_FINISH_LINE && fudB.type == FixtureUserDataType.FUD_CAR){
                System.out.println("CarAndFinishLine accessed");
             carAndFinishLine(b,a);
            }
            
        }
    }

    void tireAndGround(Fixture tireFixture, Fixture groundFixture, boolean began) { //What happens when the car touches the ground
        
            Tire tire = (Tire) tireFixture.getBody().getUserData();
            GroundAreaType ground = (GroundAreaType) groundFixture.getUserData();
            if (began) {
                tire.addGroundArea(ground);
                System.out.println("In Contact with ground");
            } else {
                tire.removeGroundArea(ground);
                System.out.println("No longer in contact with ground");
            }
    }
    
    void carAndFuel(Fixture carFixture, Fixture FuelLaneFixture, boolean began){ //What happens when the car touches the fuel pad
        
        CarType type = (CarType) carFixture.getBody().getUserData();
        Car car = type.car;
        FuelAreaType fuel = (FuelAreaType) FuelLaneFixture.getUserData();
        if(began) {
            car.addFuelArea(fuel);
            System.out.println("In Contact with a fuel Gauge");
        } 
        else {
            car.removeFuelArea(fuel);
            System.out.println("No longer in contact with fuel Gauge");
        }
    }
    
    void carAndFinishLine(Fixture carFixture, Fixture FinishLineFixture){ //What happens when the car touches the finish line
        System.out.println("CarAndFinishLine accessed");
        CarType type = (CarType) carFixture.getBody().getUserData();
        Car car = (Car) type.car;
        FinishLineType finish = (FinishLineType) FinishLineFixture.getUserData();
        car.checkpointCheck(finish.ID_Finish);
        
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
