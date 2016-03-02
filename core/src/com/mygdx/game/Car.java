package com.mygdx.game;

public class Car {
private String carName;
private double weight;
private double velocity;
private double acceleration;
private double maxVelocity;
private double maxReverseVelocity;
private double tankCapacity;
private double consumption;
private double breakingRate;
private final double rotationVelocity;
private double maxSteeringAngle;
private double steeringAngle;

private final double WEIGHT_STAT;
private final double ACCELERATION_STAT;
private final double TOPSPEED_STAT;
private final double HANDLING_STAT;
private final double TANK_CAPACITY_STAT;
private final double FUEL_CONSUMPTION_STAT;

private final String Address1 = "";
private final String Address2 = "";
private final String Address3 = "";
private final String Address4 = "";
private final String Address5 = "";
private final String Address6 = "";
private final String Address7 = "";
private final String Address8 = "";


	

public Car(){
                     this.carName = "Default Car";                      //Name of Car
	this.acceleration = 0.5;                                   //Rate at which the car will accelerate
	this.rotationVelocity = Math.PI/50;    //Rate at which the sprite will turn so that the turning is smooth
	this.breakingRate = 1.0;                                  //Rate of deceleration
	this.weight = 250;                                                    //Mass of vehicle
	this.maxVelocity = 50;                                         //Maximum speed of vehicle
	this.tankCapacity = 30;                                     //Fuel Capacity of vehicle
                     this.consumption = 5;                              //Fuel Consumption per 100 unit speed
                     
                     this.WEIGHT_STAT = 1;
                     this.ACCELERATION_STAT = 2;
                     this.TOPSPEED_STAT =  3;
                     this.HANDLING_STAT = 4;
                     this.TANK_CAPACITY_STAT = 5;
                     this.FUEL_CONSUMPTION_STAT = 6;
                     
                     
                     
}
/*
public Car(double acceleration, double breakingRate, double weight, double maxVelocity, double tankCapacity, double speedOfTurning){
	this.acceleration = acceleration;
	this.rotationVelocity = Math.PI/50;
	this.breakingRate = breakingRate;
	this.weight = weight;
	this.maxVelocity = maxVelocity;
	this.tankCapacity = tankCapacity;
}
*/	
public double getAccelerationRate(){
	return acceleration;
}

public double getMaxVelocity(){
	return maxVelocity;
}

public double getTankCapacity(){
	return tankCapacity;
}

public double getRotationVelocity(){
	return rotationVelocity;
}

public double getBreakingRate(){
	return breakingRate;
}

public double getWeight(){
	return weight;
}


public void setAccelerationRate(Double acceleration){
	this.acceleration = acceleration;
}


public void setTankCapacity(Double tankCapacity){
	this.tankCapacity = tankCapacity;
}

public void setMaxVelocity(Double maxVelocity){
	this.maxVelocity = maxVelocity;
}

public void setBreakingRate(Double breakingRate){
	this.breakingRate = breakingRate;
}

public void setWeight(Double weight){
	this.weight = weight;
}

@Override
public String toString(){
    
    return ("The vehicle created using this class has these statistics:"
            + "\n Max Speed : " + getMaxVelocity()
            + "\n Acceleration : " + getAccelerationRate()
            + "\n Breaking Rate : " + getBreakingRate()
            + "\n Mass : " + getWeight()
            + "\n Fuel Capacity : " + getTankCapacity()
            + "\n rotationVelocity : " + getRotationVelocity());
    
}
}