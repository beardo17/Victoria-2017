package org.usfirst.frc.team6121.robot.subsystems;

import org.usfirst.frc.team6121.robot.Robot;
import org.usfirst.frc.team6121.robot.RobotMap;
import org.usfirst.frc.team6121.robot.commands.Shooting;
import org.usfirst.frc.team6121.robot.subsystems.VisionSubsystem.Target;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

	private final double shootAngle = Math.toRadians(60);
	private final double y = 97;
    private final double g = 32.174 * 12;
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
    
}

