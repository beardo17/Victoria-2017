package org.usfirst.frc.team6121.robot.subsystems;

import org.usfirst.frc.team6121.robot.Robot;
import org.usfirst.frc.team6121.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	
	private static final int theta = 60;
	private static final double g = 12 * 32.174;
	private static final int y = 97;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double rpm() {
    	double x = Robot.VISION.getDistance();
    	if (x != 0) {
    		return ((Math.sqrt((x * x * g)/(x * Math.sin(theta * 2) - 2 * y * Math.cos(theta) * Math.cos(theta))) / 12)
    				* 60 / (Math.PI / 3));
    	} else {
    		return 1200;
    	}
    }
    
    public void setSpeed(double a) {
    	RobotMap.shooterMotor.set(a);
    	double motorOutput = RobotMap.shooterMotor.getOutputVoltage() / RobotMap.shooterMotor.getBusVoltage();
    	printShooter(motorOutput);
    }
    
    public void setRPM(double a) {
    	double motorOutput = RobotMap.shooterMotor.getOutputVoltage() / RobotMap.shooterMotor.getBusVoltage();
        
        double targetSpeed = a;
        RobotMap.shooterMotor.changeControlMode(TalonControlMode.Speed);
        RobotMap.shooterMotor.set(targetSpeed);
        
        printShooter(targetSpeed, motorOutput);
    }
    
    public void printShooter(double targetSpeed, double motorOutput) {

    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput);
        _sb.append("\tspd:");
        _sb.append(RobotMap.shooterMotor.getSpeed());

        /* append more signals to print when in speed mode. */
        _sb.append("\terr:");
        _sb.append(RobotMap.shooterMotor.getClosedLoopError());
        _sb.append("\ttrg:");
        _sb.append(targetSpeed);
        
    	if(++_loops >= 10) {
    		_loops = 0;
    		SmartDashboard.putString("Shooter data: ", _sb.toString());
    	}
    	_sb.setLength(0);
    }
    
    public void printShooter(double motorOutput) {

    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput);
        _sb.append("\tspd:");
        _sb.append(RobotMap.shooterMotor.getSpeed());

        /* append more signals to print when in speed mode. */
        _sb.append("\terr:");
        _sb.append(RobotMap.shooterMotor.getClosedLoopError());
        
    	if(++_loops >= 10) {
    		_loops = 0;
    		SmartDashboard.putString("Shooter data: ", _sb.toString());
    	}
    	_sb.setLength(0);
    }
    
}

