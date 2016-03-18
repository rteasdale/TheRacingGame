package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

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
private final double ROTATION_VELOCITY;
private double maxSteeringAngle;
private double steeringAngle;

private final int WEIGHT_STAT;
private final int ACCELERATION_STAT;
private final int TOPSPEED_STAT;
private final int HANDLING_STAT;
private final int TANK_CAPACITY_STAT;
private final int FUEL_CONSUMPTION_STAT;

private final String PATH_LIGHT_BLUE;
private final String PATH_DARK_BLUE;
private final String PATH_YELLOW;
private final String PATH_GREEN;
private final String PATH_WHITE;
private final String PATH_RED;
private final String PATH_PURPLE;
private final String PATH_ORANGE;

public String[] golfProperties;
public String[] lamboProperties;
public String[] priusProperties;
public String[] porscheProperties;
public String[] fordProperties;
public String[] zondaProperties;

public Car(){
                     this.carName = "Default Car";                      //Name of Car
	this.weight = 250;                                                    //Mass of vehicle
                      this.velocity = 0;                                                  //Current Total velocity of car
                      this.acceleration = 0.5;                                   //Rate at which the car will accelerate
                      this.maxVelocity = 50;                                         //Maximum speed of vehicle
                      this.maxReverseVelocity = 25;                     //Maximum speed of vehicle backwards
                      this.tankCapacity = 30;                                     //Fuel Capacity of vehicle
                      this.consumption = 5;                              //Fuel Consumption per 100 unit speed
	this.breakingRate = 1.0;                                  //Rate of deceleration
                    this.ROTATION_VELOCITY = Math.PI/50;    //Rate at which the sprite will turn so that the turning is smooth
                    this.maxSteeringAngle = Math.PI/6;          //The maximal angle of steering
                    this.steeringAngle = 0;                                 //Current Steering angle
	
                    this.WEIGHT_STAT = 1;                              //Stat indicated for weight
                     this.ACCELERATION_STAT = 2;                //Stat indicated for Acceleration
                     this.TOPSPEED_STAT =  3;                       //Stat indicated for Top Speed
                     this.HANDLING_STAT = 4;                        //Stat indicated for Handling
                     this.TANK_CAPACITY_STAT = 5;             //Stat indicated for Tank Capacity
                     this.FUEL_CONSUMPTION_STAT = 6;     //Stat indicated for Fuel Consumption
                     
                     this.PATH_LIGHT_BLUE = "";                 //Path for the image address for the light blue car
                     this.PATH_DARK_BLUE = "";                  //Path for the image address for the dark blue car
                     this.PATH_YELLOW = "";                         //Path for the image address for the yellow car
                     this.PATH_GREEN = "";                            //Path for the image address for the green car
                     this.PATH_WHITE = "";                            //Path for the image address for the white car
                     this.PATH_RED = "";                                //Path for the image address for the red car
                     this.PATH_PURPLE = "";                         //Path for the image address for the purple car
                     this.PATH_ORANGE = "";                         //Path for the image address for the orange car
}

public Car(String[] array){
    this.carName = array[0];
    this.weight = Double.parseDouble(array[1]);
    this.velocity = Double.parseDouble(array[2]);
    this.acceleration = Double.parseDouble(array[3]);
    this.maxVelocity = Double.parseDouble (array[4]);
    this.maxReverseVelocity = Double.parseDouble(array[5]);
    this.tankCapacity = Double.parseDouble(array[6]);
    this.consumption = Double.parseDouble(array[7]);
    this.breakingRate = Double.parseDouble(array[8]);
    this.maxSteeringAngle = Math.PI/6;
    this.steeringAngle = Double.parseDouble(array[10]);
    this.ROTATION_VELOCITY = Math.PI/50;
    
    this.WEIGHT_STAT = Integer.parseInt(array[11]);
    this.ACCELERATION_STAT = Integer.parseInt(array[12]);
    this.TOPSPEED_STAT = Integer.parseInt(array[13]);
    this.HANDLING_STAT = Integer.parseInt(array[14]);
    this.TANK_CAPACITY_STAT = Integer.parseInt(array[15]);
    this.FUEL_CONSUMPTION_STAT = Integer.parseInt(array[16]);
    
    this.PATH_LIGHT_BLUE = array[17];
    this.PATH_DARK_BLUE = array[18];
    this.PATH_YELLOW = array[19];
    this.PATH_GREEN = array[20];
    this.PATH_WHITE = array[21];
    this.PATH_RED = array[22];
    this.PATH_PURPLE = array[23];
    this.PATH_ORANGE = array [24];
}

public Car(String carName,double weight, double velocity, double acceleration, double maxVelocity, double maxReverseVelocity,
        double tankCapacity, double consumption, double breakingRate,double maxSteeringAngle, double steeringAngle, int WEIGHT_STAT,
        int  ACCELERATION_STAT, int TOPSPEED_STAT, int HANDLING_STAT, int TANK_CAPACITY_STAT, int FUEL_CONSUMPTION_STAT,
        String PATH_LIGHT_BLUE, String PATH_DARK_BLUE, String PATH_YELLOW, String PATH_GREEN, String PATH_WHITE, String PATH_RED, 
        String PATH_PURPLE, String PATH_ORANGE){
	
                     this.acceleration = acceleration;
	this.ROTATION_VELOCITY = Math.PI/50;
	this.breakingRate = breakingRate;
	this.weight = weight;
	this.maxVelocity = maxVelocity;
	this.tankCapacity = tankCapacity;
                     this.carName = carName;                                                             
                     this.velocity = velocity;                                                            
                     this.maxReverseVelocity = maxReverseVelocity;                     
                     this.consumption = consumption;                                             
                     this.maxSteeringAngle = maxSteeringAngle;                                           
                     this.steeringAngle = steeringAngle;        
        
                     this.WEIGHT_STAT = WEIGHT_STAT;
                     this.ACCELERATION_STAT = ACCELERATION_STAT;
                     this.TOPSPEED_STAT =  TOPSPEED_STAT;
                     this.HANDLING_STAT = HANDLING_STAT;
                     this.TANK_CAPACITY_STAT = TANK_CAPACITY_STAT;
                     this.FUEL_CONSUMPTION_STAT = FUEL_CONSUMPTION_STAT;
                     
                     this.PATH_LIGHT_BLUE = PATH_LIGHT_BLUE;
                     this.PATH_DARK_BLUE = PATH_LIGHT_BLUE;
                     this.PATH_YELLOW = PATH_YELLOW;
                     this.PATH_GREEN = PATH_GREEN;
                     this.PATH_WHITE = PATH_WHITE;
                     this.PATH_RED = PATH_RED;
                     this.PATH_PURPLE = PATH_PURPLE;
                     this.PATH_ORANGE = PATH_ORANGE;
}

public String getCarName(){
	return carName;
}
	

public double getVelocity(){
	return velocity;
}

public double getMaxReverseVelocity(){
	return maxReverseVelocity;
}

public double getConsumption(){
	return consumption;
}

public double getAccelerationRate(){
	return acceleration;
}

public double getMaxSteeringAngle(){
	return maxSteeringAngle;
}

public double getSteeringAngle(){
	return steeringAngle;
}

public int getWEIGHT_STAT(){
	return WEIGHT_STAT;
}

public int getACCELERATION_STAT(){
	return ACCELERATION_STAT;
}

public int getTOPSPEED_STAT(){
	return TOPSPEED_STAT;
}

public int getHANDLING_STAT(){
	return HANDLING_STAT;
}

public int getTANK_CAPACITY_STAT(){
	return TANK_CAPACITY_STAT;
}

public int getFUEL_CONSUMPTION_STAT(){
	return FUEL_CONSUMPTION_STAT;
}

public String getPATH_LIGHT_BLUE(){
                    return PATH_LIGHT_BLUE;
}

public String getPATH_DARK_BLUE(){
                    return PATH_DARK_BLUE;
}

public String getPATH_GREEN(){
                    return PATH_GREEN;
}
public String getPATH_ORANGE(){
                    return PATH_ORANGE;
}

public String getPATH_PURPLE(){
                    return PATH_PURPLE;
}

public String getPATH_RED(){
                    return PATH_RED;
}

public String getPATH_WHITE(){
                    return PATH_WHITE;
}

public String getPATH_YELLOW(){
                    return PATH_YELLOW;
}

public double getMaxVelocity(){
	return maxVelocity;
}

public double getTankCapacity(){
	return tankCapacity;
}

public double getRotationVelocity(){
	return ROTATION_VELOCITY;
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

public void setCarName(String carName){
	this.carName = carName;
}

public void setVelocityt(Double velocity){
	this.velocity = velocity;
}

public void setmaxReverseVelocity (Double maxReverseVelocity){
	this.maxReverseVelocity = maxReverseVelocity;
}

public void setConsumption(Double consumption){
	this.consumption = consumption;
}

public void setmaxSteeringAngle(Double maxSteeringAngle){
	this.maxSteeringAngle = maxSteeringAngle;
}

public void setSteeringAngle(Double steeringAngle){
	this.steeringAngle = steeringAngle;
}



public String[] getGolfData(){
    FileHandle file = Gdx.files.internal("data/carData.txt");
    String WholeText = file.readString();
    String[] str_array = WholeText.split("\n");
    String golfData = str_array[0];
    golfProperties = golfData.split(",");
    return golfProperties;
}

public String[] getLamboData(){
    FileHandle file = Gdx.files.internal("data/carData.txt");
    String WholeText = file.readString();
    String[] str_array = WholeText.split("\n");
    String lamboData = str_array[2];
    lamboProperties = lamboData.split(",");
    return lamboProperties;
}

public String[] getPriusData(){
    FileHandle file = Gdx.files.internal("data/carData.txt");
    String WholeText = file.readString();
    String[] str_array = WholeText.split("\n");
    String priusData = str_array[4];
    priusProperties = priusData.split(",");
    return priusProperties;
}

public String[] getPorscheData(){
    FileHandle file = Gdx.files.internal("data/carData.txt");
    String WholeText = file.readString();
    String[] str_array = WholeText.split("\n");
    String porscheData = str_array[6];
    porscheProperties = porscheData.split(",");
    return porscheProperties;
}

public String[] getFordData(){
    FileHandle file = Gdx.files.internal("data/carData.txt");
    String WholeText = file.readString();
    String[] str_array = WholeText.split("\n");
    String fordData = str_array[8];
        fordProperties = fordData.split(",");
        return fordProperties;
}

public String[] getZondaData(){
    FileHandle file = Gdx.files.internal("data/carData.txt");
    String WholeText = file.readString();
    String[] str_array = WholeText.split("\n");
    String zondaData = str_array[10];
         zondaProperties = zondaData.split(",");
        return zondaProperties;
}



@Override
public String toString(){
    
    return ("The vehicle created using this class has these statistics:"
            + "\n Car Name : " + getCarName()
            + "\n Weight : " + getWeight()
            + "\n Velocity : " + getVelocity()
            + "\n Acceleration : " + getAccelerationRate()
            + "\n Max Velocity : " + getMaxVelocity()
            + "\n Max Reverse Speed : " + getMaxReverseVelocity()
            + "\n Tank Capacity : " + getTankCapacity()
            + "\n Consumption : " + getConsumption()
            + "\n Breaking Rate : " + getBreakingRate()
            + "\n ROTATION_VELOCITY : " + getRotationVelocity()
            + "\n Max Steering Angle : " + getMaxSteeringAngle()
            + "\n Steering Angle : " + getSteeringAngle()
            
            +"\n\n Weight Stat : " + getWEIGHT_STAT()
            +"\n Acceleration Stat : " + getACCELERATION_STAT()
            +"\n Top Speed Stat : " + getTOPSPEED_STAT()
            +"\n Handling Stat : " + getHANDLING_STAT()
            +"\n Tank Capacity Stat : " + getTANK_CAPACITY_STAT()
            +"\n Fuel Consumption Stat : " + getFUEL_CONSUMPTION_STAT()
            
            + "\n\n PATH_LIGHT_BLUE : " + getPATH_LIGHT_BLUE()
            + "\n PATH_DARK_BLUE : " + getPATH_DARK_BLUE()
            + "\n PATH_GREEN : " + getPATH_GREEN()
            + "\n PATH_ORANGE : " + getPATH_ORANGE()
            + "\n PATH_PURPLE : " + getPATH_PURPLE()
            + "\n PATH_WHITE : " + getPATH_WHITE()
            + "\n PATH_YELLOW: " + getPATH_YELLOW()
            + "\n PATH_RED: " + getPATH_RED()
                    );
            
    
}
}