package org.usfirst.frc.team6121.robot.commands;

import org.usfirst.frc.team6121.robot.subsystems.AutoSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Autonomous extends CommandGroup {

	/**
	 * Handles all of the autonomous stuff based on inputs from the DS
	 * 
	 * @param strat Strategy for Autonomous
	 * @param pos Position for Autonomous
	 * @param team Alliance Color
	 */
    public  Autonomous(AutoSubsystem.strat strat, AutoSubsystem.position pos, AutoSubsystem.alliance team) {
    	
    	switch(team) {
    	case BLUE:
    		switch(pos) {
			case LEFT:
				switch(strat) {
    				case SHOOT:
    					addSequential(new Drive(0.5, 0.6065306597, 1.25)); // Turn for the boiler
//    					addSequential(new AimShot());
    					addSequential(new Shooting());
    					break;
    				case GEAR:
    					addSequential(new DriveStraight(1.7, 0.45));
    					addSequential(new AimGear(false));
    					addSequential(new DriveStraight(1, 0.4));
    					addSequential(new WaitCommand(5));
    					addSequential(new DriveStraight(1, -0.25));
//    					addSequential(new GearDeliver());
    					break;
    				case WIN: //TODO: Fix the going to the hopper and stuff
    					addSequential(new Drive(0.5, 0.0025569325, 1)); // To the peg
//    					addSequential(new GearDeliver());
    					addSequential(new WaitCommand(3));
    					addSequential(new Drive(-0.5, 0, 0.5));         // Back it up
    					addSequential(new Drive(-0.5, -0.25, 0.5));       // To the hopper
    					addSequential(new Drive(0, -0.25, 0.125));        // Catch the balls
    					addSequential(new WaitCommand(2));
//    					addSequential(new AimShot());
    					addSequential(new Shooting());
    					break;
    				default:
    					break;
				}
				break;
			case MID:
				switch(strat) {
				case GEAR:
					addSequential(new DriveStraight(3, 0.3)); // To the peg
					addSequential(new WaitCommand(5));
					addSequential(new DriveStraight(1, -0.25));
					break;
				case SHOOT:
					addSequential(new Drive(0.5, 0.6065306597, 1.25)); // Turn for the boiler
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				case WIN:
					addSequential(new Drive(0.5, 0, 1));
//					addSequential(new GearDeliver());
					addSequential(new WaitCommand(3));
					addSequential(new Drive(-0.5, 0, 0.25));
					addSequential(new Drive(-0.5, 0.7, 1));
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				default:
					break;
				}
				break;
			case RIGHT:
				switch(strat) {
				case GEAR:
					addSequential(new DriveStraight(1.7, 0.45));
					addSequential(new AimGear(true));
					addSequential(new DriveStraight(1.5, 0.3));
					addSequential(new WaitCommand(5));
					addSequential(new DriveStraight(1, -0.25));
					break;
				case SHOOT:
					addSequential(new Drive(0.5, 0.6065306597, 1.25)); // Turn for the boiler
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				case WIN:
					addSequential(new Drive(0.5, -0.0025569325, 1));
//					addSequential(new GearDeliver());
					addSequential(new WaitCommand(3));
					addSequential(new Drive(-0.5, 0.3601348999, 0.5));
					addSequential(new Drive(-0.5, 0.2789854233, 1));
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				default:
					break;
				}
				break;
			default:
				break;
    		}
    		break;
    	case RED:
    		switch(pos) {
			case LEFT:
				switch(strat) {
				case SHOOT:
					addSequential(new Drive(0.5, -0.6065306597, 1.25)); // Turn for the boiler
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				case GEAR:
					addSequential(new DriveStraight(1.7, 0.45));
					addSequential(new AimGear(false));
					addSequential(new DriveStraight(1, 0.3));
					addSequential(new WaitCommand(5));
					addSequential(new DriveStraight(1, -0.25));
//					addSequential(new GearDeliver());
					break;
				case WIN:
					addSequential(new Drive(0.5, 0.0025569325, 1));
//					addSequential(new GearDeliver());
					addSequential(new WaitCommand(3));
					addSequential(new Drive(-0.5, -0.3601348999, 0.5));
					addSequential(new Drive(-0.5, -0.2789854233, 1));
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				default:
				}
				break;
			case MID:
				switch(strat) {
				case GEAR:
					addSequential(new DriveStraight(3, 0.3)); // To the peg
					addSequential(new WaitCommand(5));
					addSequential(new DriveStraight(1, -0.25));
					break;
				case SHOOT:
					addSequential(new Drive(0.5, -0.6065306597, 1.25)); // Turn for the boiler
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				case WIN:
//					addSequential(new GearDeliver());
					addSequential(new WaitCommand(1));
					addSequential(new Drive(0.5, 0, 0.25));
					addSequential(new Drive(0.5, 0.6065306597, 0.25));
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				default:
					break;
				}
				break;
			case RIGHT:
				switch(strat) {
				case GEAR:
					addSequential(new DriveStraight(1.7, 0.45));
					addSequential(new AimGear(true));
					addSequential(new DriveStraight(1.5, 0.3));
					addSequential(new WaitCommand(5));
					addSequential(new DriveStraight(1, -0.25));
//					addSequential(new GearDeliver());
					break;
				case SHOOT:
					addSequential(new Drive(0.5, -0.6065306597, 1.25)); // Turn for the boiler
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				case WIN:
					addSequential(new Drive(0.5, -0.0025569325, 0.5));  // To the peg
//					addSequential(new GearDeliver());
					addSequential(new WaitCommand(1));
					addSequential(new Drive(-0.5, 0, 0.125));           // Back it up
					addSequential(new Drive(-0.5, 0.25, 0.5));          // To the hopper
					addSequential(new Drive(0, 0.25, 0.125));           // Catch the balls
					addSequential(new WaitCommand(2));
//					addSequential(new AimShot());
					addSequential(new Shooting());
					break;
				default:
					break;
				}
				break;
			default:
				break;
    		}
    		break;
    	default:
    	}
    	
    }
}
