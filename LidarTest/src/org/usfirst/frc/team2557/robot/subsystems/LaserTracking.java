
package org.usfirst.frc.team2557.robot.subsystems;

import org.usfirst.frc.team2557.robot.Robot;

import org.usfirst.frc.team2557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2557.robot.subsystems.LidarRangeFinder.LidarData;

/*import org.usfirst.frc.team2557.robot.subsystems.Shapes; */

import java.lang.Math;
import java.util.ArrayList;
//import java.util.Arrays;

/**
 *
 */
public class LaserTracking extends Subsystem {
    private double noHitAngle;
    private boolean notDone;
    private double maxSweepAngle;
    private boolean inRange;
    private double sweepAngle;
    private double minSweepAngle;
    private double inchesFront;
    private double hitInches;
    private double hitAngle;
    public double laserStartInches;
    private double laserStartAngle;
    private double laserEndInches;
    private double firstLength;
    private double secondLength;
    private double firstWidth;
    private double secondWidth;
    private double lowPointInches;
    private double lowPointAngle;
    private double length;
    private double width;
    private double laserEndAngle;
    private double laserInches;
    private double laserStartInchesPrev;
    private double laserStartAnglePrev;
    private boolean firstTime;
    private boolean corner;
    private double side1;
    private double side2;
    private int x;
    private boolean objectEnd;
    
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void adjustForDefense(){              
    //x = 
    //RobotMap.laserInches = RobotMap.LidarSensor.getData(x).getDistance();
    int dataCount = 0;
   
    int myDistances[] = new int[90];
    //Arrays myTestArray = Arrays.
    LidarData distanceData[] = new LidarData[90];
    LidarData currentShortest = null;
    Shapes currentShape = null;
    ArrayList<Shapes> shapeList = new ArrayList<Shapes>();
    int myAngle = 0;
    int emptyCount = 0;
    for (myAngle = 0; myAngle < 90; myAngle++) {
    myDistances[myAngle] = RobotMap.rangeFinder.getData(myAngle + 45).getDistance();
   
    System.out.print(myDistances[myAngle] + " ");
   
    if (myAngle > 0 && myAngle % 5 == 0) {
    System.out.println();
    }
   
    if (myDistances[myAngle] < 2000 && myDistances[myAngle] != 0) {
    distanceData[dataCount] = RobotMap.rangeFinder.getData(myAngle + 45);

    if (currentShape == null) {
    currentShape = new Shapes();
currentShortest = distanceData[dataCount];
lowPointAngle = dataCount; //storing the closest angle
currentShape.startAngle = dataCount; //needs to be done before dataCount is changed. dataCount is the angle.
dataCount++;
currentShape.startDistance = currentShortest.getDistance();
//currentShape.startAngle = currentShortest.getAngle(); //Is not a proper function //
currentShape.closestDistance = currentShape.startDistance;
currentShape.closestAngle = currentShape.startAngle;
currentShape.lastDistance = currentShape.startDistance;
currentShape.lastAngle = currentShape.startAngle;
emptyCount = 0;
                    continue;
    }
    if (currentShortest.getDistance() > distanceData[dataCount].getDistance()) {
    currentShortest = distanceData[dataCount];
    lowPointAngle = dataCount; //storing the closest angle
    currentShape.closestDistance = currentShortest.getDistance();
    currentShape.closestAngle = dataCount;//myAngle;//currentShortest.getAngle(); //Is not a proper function
    }
    
    currentShape.lastDistance = distanceData[dataCount].getDistance();
    currentShape.lastAngle = dataCount;//myAngle; //distanceData[dataCount].getAngle();
   
    dataCount++;
    }
   
    // end of object detection
    if (myDistances[myAngle] > 2000 || emptyCount > 2) {
    emptyCount = 0;
    // store shape
    if (currentShape != null) {
    shapeList.add(currentShape);
    }
   
    currentShape = null;
    }
   
    if (myDistances[myAngle] == 0) {
    emptyCount++;
    }
    }

    //minSweepAngle = -22;
    //maxSweepAngle = 22;
    //notDone = false;
    firstTime = true;
   
    //RobotMap.servoCenterAngle = minSweepAngle;
   
    //RobotMap.servoCenterAngle = RobotMap.servoCenter.get() + 1;

    // return if not enough data was found
    if (dataCount < 3) {
    notDone = false;
//     return;
    } else {
    notDone = true;
    objectEnd = false;
    }
   
//     notDone = true;
    if (notDone) {
     laserStartInches = (double) distanceData[0].getDistance();
       laserStartAngle = 0;//distanceData[0].getAngle(); //the index is the angle.

       lowPointInches = (double) currentShortest.getDistance();
       //lowPointAngle = currentShortest.getAngle(); //should not be necessary.
        
       laserEndInches = (double) distanceData[dataCount - 1].getDistance();
    }
    /*if(distanceData[dataCount - 1] != null && laserEndInches == (double) distanceData[dataCount - 1].getDistance()){
    objectEnd = true;
    }*/
   
   
    /*x = (int) RobotMap.servoCenterAngle; 
    if(notDone == true && distanceData[x].getDistance() < distanceData[x-1].getDistance()){
    lowPointInches = RobotMap.laserInches;
    lowPointAngle = RobotMap.servoCenterAngle;
   
    }
*/
firstLength = Math.pow(Math.cos((laserStartAngle-90) / hitInches) - Math.cos((lowPointAngle-90) / lowPointInches), 2);
secondLength = Math.pow(Math.sin((hitAngle-90) / hitInches) - Math.sin((lowPointAngle-90) / lowPointInches), 2);
side1 = Math.sqrt(firstLength + secondLength);
firstWidth = Math.pow(Math.cos((lowPointAngle-90) / lowPointInches) - Math.cos((noHitAngle-90) / laserEndInches), 2);
secondWidth = Math.pow(Math.sin((lowPointAngle-90) / lowPointInches) - Math.sin((noHitAngle-90) / laserEndInches), 2);
side2 = Math.sqrt(firstWidth + secondWidth);
if(width > 19 && width < 22){
RobotMap.rectangle = true;
}
if(RobotMap.rectangle == true && notDone == true){
RobotMap.driveAdjust = true;
}
     
/*if(notDone == true && RobotMap.laserInches > 40){
laserEndInches = RobotMap.laserInches;
laserEndAngle = RobotMap.laserAngle;
}*/
//RobotMap.width = width;
//double myPower = 2;
//firstLength = Math.cos(laserStartAngle) * laserStartInches;
//Math.pow(secondLength, myPower);
/*= firstLength ** 2 + laserStartInches ** 2; 
side1 ** 2 = firstLength ** 2 + secondLength ** 2;
firstLength = firstLength ** 2 + laserStartInches ** 2; 
secondLength = Math.cos(laserStartAngle) * laserStartInches;
side2 ** 2 = firstLength ** 2 + secondLength ** 2;
*/
if(side1 > side2){
width = side2;
length = side1;
}
else if(side1 < side2){
width = side1;
length = side2;
}
else{
width = side1;
length = side2;
}
SmartDashboard.putNumber("width", width);
SmartDashboard.putNumber("length", length);
SmartDashboard.putNumber("laserStartInches", laserStartInches);
SmartDashboard.putNumber("laserEndInches", laserEndInches);
//SmartDashboard.putNumber("RFValue", RobotMap.RFArray[5]);
SmartDashboard.putNumber("lowPointInches", lowPointInches);
SmartDashboard.putNumber("startAngle", laserStartAngle);
SmartDashboard.putNumber("lowPointAngle", lowPointAngle);
    }
}
