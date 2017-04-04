package org.usfirst.frc.team6121.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int XBOX_CONTROLLER_1 = 0;
	public static final int XBOX_CONTROLLER_2 = 1;
	
	public static final int X_AXIS = 0;
	public static final int Y_AXIS = 1;
	public static final int R_TRIGGER = 3;

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static final int X_BUTTON = 3;
	public static final int Y_BUTTON = 4;
	public static final int L_BUTTON = 5;
	public static final int R_BUTTON = 6;
	
	public static int FRONT_LEFT_MOTOR = 0;
	public static int BACK_LEFT_MOTOR = 1;
	public static int FRONT_RIGHT_MOTOR = 2;
	public static int BACK_RIGHT_MOTOR = 3;

	public static final int SHOOTER_MOTOR = 1;
	public static final int BALL_INTAKE_MOTOR = 5;
	public static final int AGITATOR_MOTOR = 6;
	
	/******* PID CONSTANTS *********/
	 
	public static final double pShooterBadder = 0.00023;
	public static final double iShooterBadder = 0.0000;
	public static final double dShooterBadder = 0.0000;
	public static final double kForward	= 0.000137500;
	public static final double bForward	= 0.080000000;
	
	public static SpeedController flMotor;
	public static SpeedController rlMotor;
	public static SpeedController frMotor;
	public static SpeedController rrMotor;
	
	public static CANTalon shooterMotor;
	public static SpeedController ballIntakeMotor;
	public static SpeedController agitator;
	
	public static ADXRS450_Gyro gyro;
	
	
	public static RobotDrive drive;
	
	public static void init() {
		
		shooterMotor = new CANTalon(SHOOTER_MOTOR);
		ballIntakeMotor = new Victor(BALL_INTAKE_MOTOR);
		agitator = new Spark(AGITATOR_MOTOR);
		
		shooterMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor.reverseSensor(false);
		shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterMotor.configPeakOutputVoltage(+12.0f, -12.0f);
		shooterMotor.setProfile(0);
		shooterMotor.setF(0);
		shooterMotor.setP(0);
		shooterMotor.setI(0);
		shooterMotor.setD(0);
		
		flMotor = new Victor(FRONT_LEFT_MOTOR);
		rlMotor = new Victor(BACK_LEFT_MOTOR);
		frMotor = new Victor(FRONT_RIGHT_MOTOR);
		rrMotor = new Victor(BACK_RIGHT_MOTOR);
		
		drive = new RobotDrive(flMotor, rlMotor, frMotor, rrMotor);
		
		gyro = new ADXRS450_Gyro();
		gyro.reset();
	
	}
}
