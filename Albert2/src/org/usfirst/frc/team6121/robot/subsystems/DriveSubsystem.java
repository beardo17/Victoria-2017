package org.usfirst.frc.team6121.robot.subsystems;

import org.usfirst.frc.team6121.robot.Robot;
import org.usfirst.frc.team6121.robot.RobotMap;
import org.usfirst.frc.team6121.robot.commands.ArcadeDrive;
import org.usfirst.frc.team6121.robot.subsystems.VisionSubsystem.Target;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private static final double Kp = 0.06;
	private static final double Kdriveturn = 0.3;
	
    public void arcadeDrive(Joystick stick) {
    	double x = stick.getRawAxis(RobotMap.X_AXIS);
    	double y = stick.getRawAxis(RobotMap.Y_AXIS) * (stick.getRawAxis(RobotMap.R_TRIGGER) + 0.5);
    	RobotMap.drive.arcadeDrive(y, x, false);
    }
    
    /**
     * Drive the motors at "outputMagnitude" and "curve". Both outputMagnitude and curve are -1.0 to +1.0 values, where 0.0 represents stopped and not turning. curve < 0 will turn left and curve > 0 will turn right.
     * 
The algorithm for steering provides a constant turn radius for any normal speed range, both forward and backward. Increasing sensitivity causes sharper turns for fixed values of curve.

This function will most likely be used in an autonomous routine.
     * @param m The speed setting for the outside wheel in a turn, forward or backwards, +1 to -1.
     * @param c The rate of turn, constant for different forward speeds. Set curve < 0 for left turn or curve > 0 for right turn. Set curve = e^(-r/w) to get a turn radius r for wheelbase w of your robot. Conversely, turn radius r = -ln(curve)*w for a given value of curve and wheelbase w.
     */
    public void drive(double m, double c) {
    	RobotMap.drive.drive(m, c);
    }
    
    public void turn(double l, double r) {
    	RobotMap.drive.tankDrive(l, r, false);
    }
    
    public void aimGear(double h, boolean right) {
    	double x = RobotMap.gyro.getAngle();
    	SmartDashboard.putNumber("Gyro Heading: ", x);
    	if (Robot.VISION.getTarget() != Target.None) {
    		if (x - h > 0.5) {
    			turn(Kdriveturn, -Kdriveturn);
    		} else if (x - h < 0.5) {
    			turn(-Kdriveturn, Kdriveturn);
    		} else {
    			turn(0, 0);
    		}
    	} else if (right) {
    		turn(Kdriveturn, -Kdriveturn);
    	} else {
    		turn(-Kdriveturn, Kdriveturn);
    	}
    }
    
    public void aimShot(double h, boolean right) {
    	System.out.println("Aiming Shot...");
    	double x = RobotMap.gyro.getAngle();
    	SmartDashboard.putNumber("Gyro Heading: ", x);
    	if (Robot.VISION.getTarget() == Target.Boiler) {
    		if (x - h > 0.5) {
    			turn(Kdriveturn, -Kdriveturn);
    		} else if (x - h < 0.5) {
    			turn(-Kdriveturn, Kdriveturn);
    		} else {
    			turn(0, 0);
    		}
    	} else if (right) {
    		turn(Kdriveturn, -Kdriveturn);
    	} else {
    		turn(-Kdriveturn, Kdriveturn);
    	}
    }

    public boolean isGearAimed(double h) {
    	double x = RobotMap.gyro.getAngle();
    	if (Robot.VISION.getTarget() == Target.Gear) {
    		if (x - h > 0.5 || x - h < 0.5) {
    			return false;
    		} else {
    			return true;
    		}
    	} else {
    		return false;
    	}
    }
    
    public boolean isShotAimed(double h) {
    	double x = RobotMap.gyro.getAngle();
    	if (Robot.VISION.getTarget() == Target.Boiler) {
    		if (x - h > 0.5 || x - h < 0.5) {
    			return false;
    		} else {
    			return true;
    		}
    	} else {
    		return false;
    	}
    }
    
    public void driveStraight(double speed) {
    	double angle = RobotMap.gyro.getAngle();
    	SmartDashboard.putNumber("Gyro Heading: ", angle);
    	drive(speed, angle * Kp);
    	Timer.delay(0.004);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ArcadeDrive());
    }

}

