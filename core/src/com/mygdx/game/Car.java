package com.mygdx.game;

public class Car {
private double acceleration;
private final double SpriteTurningRate;
private double speedOfTurning;
private double breakingRate;
private double mass;
private double maxSpeed;
private double fuelCapacity;
private double fuelConsumption;
	

public Car(){
	this.acceleration = 0.5;                        //Rate at which the car will accelerate
	this.SpriteTurningRate = Math.PI/50;    //Rate at which the sprite will turn so that the turning is smooth
	this.breakingRate = 1.0;                                  //Rate of deceleration
	this.mass = 250;                                                    //Mass of vehicle
	this.maxSpeed = 50;                                         //Maximum speed of vehicle
	this.fuelCapacity = 30;                                     //Fuel Capacity of vehicle
	this.speedOfTurning = 4;                                //Handling: Speed at which the car will turn (Rate of the SpriteTurningRate)
                     this.fuelConsumption = 5;                              //Fuel Consumption per 100 unit speed
}

public Car(double acceleration, double breakingRate, double mass, double maxSpeed, double fuelCapacity, double speedOfTurning){
	this.acceleration = acceleration;
	this.SpriteTurningRate = Math.PI/50;
	this.breakingRate = breakingRate;
	this.mass = mass;
	this.maxSpeed = maxSpeed;
	this.fuelCapacity = fuelCapacity;
	this.speedOfTurning = speedOfTurning;
}
	
public double getAccelerationRate(){
	return acceleration;
}

public double getSpeedOfTurning(){
	return speedOfTurning;
}

public double getMaxSpeed(){
	return maxSpeed;
}

public double getFuelCapacity(){
	return fuelCapacity;
}

public double getSpriteTurningRate(){
	return SpriteTurningRate;
}

public double getBreakingRate(){
	return breakingRate;
}

public double getMass(){
	return mass;
}


public void setAccelerationRate(Double acceleration){
	this.acceleration = acceleration;
}

public void setSpeedOfTurning(Double speedOfTurning){
	this.speedOfTurning = speedOfTurning;
}

public void setFuelCapacity(Double fuelCapacity){
	this.fuelCapacity = fuelCapacity;
}

public void setMaxSpeed(Double maxSpeed){
	this.maxSpeed = maxSpeed;
}

public void setBreakingRate(Double breakingRate){
	this.breakingRate = breakingRate;
}

public void setMass(Double mass){
	this.mass = mass;
}

@Override
public String toString(){
    
    return ("The vehicle created using this class has these statistics:"
            + "\n Max Speed : " + getMaxSpeed()
            + "\n Acceleration : " + getAccelerationRate()
            + "\n Speed of turning : " + getSpeedOfTurning()
            + "\n Breaking Rate : " + getBreakingRate()
            + "\n Mass : " + getMass()
            + "\n Fuel Capacity : " + getFuelCapacity()
            + "\n SpriteTurningRate : " + getSpriteTurningRate());
    
}
/*
@Override
public boolean equals(Object car){
    boolean answer = false;
    
    if()
    
    return answer
}
*/

}
