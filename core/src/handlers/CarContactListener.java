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
                tireAndGround(a, b, began);
                    
            } else if (fudA.type == FixtureUserDataType.FUD_GROUND_AREA && fudB.type == FixtureUserDataType.FUD_CAR_TIRE) {
                //What happens when the ground touches the car   
                tireAndGround(b, a, began);
            }
            
            
            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_FUEL_LANE){
            carAndFuel(a, b, began);
            }
            else if(fudA.type == FixtureUserDataType.FUD_FUEL_LANE && fudB.type == FixtureUserDataType.FUD_CAR){
             carAndFuel(b, a, began);
            }
            
            
            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_FINISH_LINE){
               carAndFinishLine(a,b);
            }
            else if(fudA.type == FixtureUserDataType.FUD_FINISH_LINE && fudB.type == FixtureUserDataType.FUD_CAR){
             carAndFinishLine(b,a);
            }
//            
//            
//            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndCar(a);
//            }
//            
//            
//            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_BRIDGE){
//             carAndBridge(a,b, began);
//            }
//            else if(fudA.type == FixtureUserDataType.FUD_BRIDGE && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndBridge(b,a,began);
//            }
//            
//            
//            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_METAL){
//             carAndMetal(a,b, began);
//            }
//            else if(fudA.type == FixtureUserDataType.FUD_METAL && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndMetal(b,a, began);
//            }
//            
//            
//            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_ICE){
//             carAndIce(a,b, began);
//            }
//            else if(fudA.type == FixtureUserDataType.FUD_ICE && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndIce(b,a, began);
//            }
//            
//            
//            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_OIL){
//             carAndOil(a,b, began);
//            }
//            else if(fudA.type == FixtureUserDataType.FUD_OIL && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndOil(b,a, began);
//            }
//            
//            else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_WALL){
//             carAndWall(a);
//            }
//            else if(fudA.type == FixtureUserDataType.FUD_WALL && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndWall(b);
//            }
//            
//             else if(fudA.type == FixtureUserDataType.FUD_CAR && fudB.type == FixtureUserDataType.FUD_TIREOBJ){
//             carAndTire(a);
//            }
//            else if(fudA.type == FixtureUserDataType.FUD_TIREOBJ && fudB.type == FixtureUserDataType.FUD_CAR){
//             carAndTire(b);
//            }
         }
    }

    void tireAndGround(Fixture tireFixture, Fixture groundFixture, boolean began) { //What happens when the car touches the ground
        
            Tire tire = (Tire) tireFixture.getBody().getUserData();
            GroundAreaType ground = (GroundAreaType) groundFixture.getUserData();
            if (began) {
                tire.addGroundArea(ground);
            } else {
                tire.removeGroundArea(ground);
            }
    }
    
    void carAndFuel(Fixture carFixture, Fixture FuelLaneFixture, boolean began){ //What happens when the car touches the fuel pad
        
        CarType type = (CarType) carFixture.getBody().getUserData();
        Car car = type.car;
        FuelAreaType fuel = (FuelAreaType) FuelLaneFixture.getUserData();
        if(began) {
            car.addFuelArea(fuel);
        } 
        else {
            car.removeFuelArea(fuel);
        }
    }
    
    void carAndFinishLine(Fixture carFixture, Fixture FinishLineFixture){ //What happens when the car touches the finish line
        CarType type = (CarType) carFixture.getBody().getUserData();
        Car car = (Car) type.car;
        FinishLineType finish = (FinishLineType) FinishLineFixture.getUserData();
        car.checkpointCheck(finish.ID_Finish);
        
    }
    
//    private void carAndCar(Fixture carFixture) {
//        CarType type = (CarType) carFixture.getBody().getUserData();
//        Car car =  (Car) type.car;
//        car.playCarOnCarSound();
//        
//    }
//
//    private void carAndWall(Fixture carFixture) {
//       CarType type = (CarType) carFixture.getBody().getUserData();
//        Car car =  (Car) type.car;
//        car.playCarOnWallSound();
//    }
//
//    private void carAndTire(Fixture carFixture) {
//        CarType type = (CarType) carFixture.getBody().getUserData();
//        Car car =  (Car) type.car;
//        car.playCarOnTireSound();
//    }
//
//    private void carAndMetal(Fixture carFixture, Fixture b, boolean began) {
//        CarType type = (CarType) carFixture.getBody().getUserData();
//        Car car =  (Car) type.car;
//        car.playCarOnTireSound();
//        
//        
//    }
//
//    private void carAndBridge(Fixture carFixture, Fixture bridgeFixture, boolean began) {
//         CarType type = (CarType) carFixture.getBody().getUserData();
//        Car car = type.car;
//        BridgeAreaType bridge = (BridgeAreaType) bridgeFixture.getUserData();
//        if(began) {
//            car.addFuelArea(fuel);
//        } 
//        else {
//            car.removeFuelArea(fuel);
//        }
//   }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
            // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
            // TODO Auto-generated method stub

    }

    
}
