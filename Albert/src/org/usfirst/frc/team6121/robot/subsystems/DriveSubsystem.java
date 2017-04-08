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

	/** Constant for driving straight **/
	private static final double Kp = 0.05;
	/** Drive straight constant **/
	private static final double Kdriveturn = 0.35;
	
	/**
	 * The primary driving command. Uses arcade drive and a Turbo Trigger for full speed.
	 * @param stick The Joystick used for the primary driving
	 */
    public void arcadeDrive(Joystick stick) {
    	double a = RobotMap.gyro.getAngle();
    	SmartDashboard.putNumber("Gyro Heading: ", a);
    	double x = stick.getRawAxis(RobotMap.X_AXIS);
    	double y = stick.getRawAxis(RobotMap.Y_AXIS) * (stick.getRawAxis(RobotMap.R_TRIGGER) + 0.5);
    	RobotMap.drive.arcadeDrive(y, x, false);
    }
    
    /**
     * Drive the motors at "outputMagnitude" and "curve". Both outputMagnitude and 
     * curve are -1.0 to +1.0 values, where 0.0 represents stopped and not turning. 
     * curve < 0 will turn left and curve > 0 will turn right.
     * The algorithm for steering provides a constant turn radius for any normal 
     * speed range, both forward and backward. Increasing sensitivity causes sharper 
     * turns for fixed values of curve.
     * This function will most likely be used in an autonomous routine.
     * @param m The speed setting for the outside wheel in a turn, forward or backwards, +1 to -1.
     * @param c The rate of turn, constant for different forward speeds. Set curve < 0 for left 
     * turn or curve > 0 for right turn. Set curve = e^(-r/w) to get a turn radius r for wheelbase 
     * w of your robot. Conversely, turn radius r = -ln(curve)*w for a given value of curve and wheelbase w.
     */
    public void drive(double m, double c) {
    	double x = RobotMap.gyro.getAngle();
    	SmartDashboard.putNumber("Gyro Heading: ", x);
    	RobotMap.drive.drive(m, c);
    }
    
    /**
     * Using tank drive controls to precisely control the speed of the wheels. 
     * Best used for turn; thus, the name is turn
     * @param l left side motors
     * @param r right side motors
     */
    public void turn(double l, double r) {
    	double x = RobotMap.gyro.getAngle();
    	SmartDashboard.putNumber("Gyro Heading: ", x);
    	RobotMap.drive.tankDrive(l, r, false);
    }
    
    /**
     * Uses the turn method to turn the robot to a given heading. Make sure to reset the gyro before using
     * @param h The heading to go to
     * @param right If true, begins by turning to the right first
     * @param t The Target it is looking for
     */
    public void toHeading(double h, boolean right) {
    	System.out.println("Going to Heading...");
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

    /**
     * @param h A Specified heading
     * @return True if the robot is aimed at a Target
     */
    public boolean isAimed(double h) {
    	double x = RobotMap.gyro.getAngle();
    	if (Robot.VISION.getTarget() != Target.None) {
    		if (x - h > 0.5 || x - h < 0.5) {
    			return false;
    		} else {
    			return true;
    		}
    	} else {
    		return false;
    	}
    }
    
    /**
     * Drives the robot straight by using a Gyro
     * @param speed The speed it drives straight
     */
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

