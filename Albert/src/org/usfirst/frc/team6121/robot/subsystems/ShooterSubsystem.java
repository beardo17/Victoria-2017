package org.usfirst.frc.team6121.robot.subsystems;

import org.usfirst.frc.team6121.robot.Robot;
import org.usfirst.frc.team6121.robot.RobotMap;
import org.usfirst.frc.team6121.robot.subsystems.VisionSubsystem.Target;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {
	
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;

//	private final double shootAngle = Math.toRadians(60);
//	private final double y = 97;
//    private final double g = 32.174 * 12;
    private final double xShooterOffset = 0;
    private final double xGearOffset = 0;
    
	VisionSubsystem v = Robot.VISION;
			
    public double aimValue() {
    	double c = 0;
    	if (v.getTarget() == Target.Boiler) {
    		c = (v.getWidth(Target.Boiler) * xShooterOffset / 15) + v.getCenterX(Target.Boiler);
    	} else if (v.getTarget() == Target.Gear) {
    		c = (v.getWidth(Target.Gear) * xGearOffset / 10.25) + v.getCenterX(Target.Gear);
    	}
		return c;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setSpeed(double a) {
    	RobotMap.shooterMotor.set(a);
    }
    
    public void setRPM(double a) {
    	double motorOutput = RobotMap.shooterMotor.getOutputVoltage() / RobotMap.shooterMotor.getBusVoltage();
        
        /* Speed mode */
        double targetSpeed = a; /* 1500 RPM in either direction */
        RobotMap.shooterMotor.changeControlMode(TalonControlMode.Speed);
        RobotMap.shooterMotor.set(targetSpeed); /* 1500 RPM in either direction */
        
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
    
}

