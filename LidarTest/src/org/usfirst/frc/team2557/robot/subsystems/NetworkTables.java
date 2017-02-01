package org.usfirst.frc.team2557.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class NetworkTables extends Subsystem {

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void publishContours(){
    	NetworkTable table;
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    	String[] tableElements = new String[5];
    	
    	tableElements[0] = "centerX";
    	tableElements[1] = "centerY";
    	tableElements[2] = "width";
    	tableElements[3] = "area";
    	tableElements[4] = "height";

    	while (true){

    		for(String element : tableElements){
    			System.out.print(element + " ");
  				System.out.print(table.getNumber(element, 0) + ""
  						);
  				System.out.println();
    		}
    		System.out.println();
    		Timer.delay(1);
    	}
    }
}


