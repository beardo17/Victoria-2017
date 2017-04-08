package org.usfirst.frc.team6121.robot.commands;

import org.usfirst.frc.team6121.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shooting extends Command {

    public Shooting() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Shooting Initilization");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
//    		Robot.shooterSubsystem.setRPM(Robot.shooterSubsystem.rpm());
//    		Robot.shooterSubsystem.setRPM(500);
    		Robot.shooterSubsystem.setSpeed(0.4);
    		System.out.println("Set shooter to an RPM");
    	} catch (Exception e) {
//    		e.printStackTrace();
    		System.out.println(e.getMessage());
    		Robot.shooterSubsystem.setSpeed(0.4);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
