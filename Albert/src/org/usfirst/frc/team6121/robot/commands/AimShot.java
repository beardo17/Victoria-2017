package org.usfirst.frc.team6121.robot.commands;

import org.usfirst.frc.team6121.robot.Robot;
import org.usfirst.frc.team6121.robot.RobotMap;
import org.usfirst.frc.team6121.robot.subsystems.VisionSubsystem.Target;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AimShot extends Command {
	private boolean m_right;
	
    public AimShot(boolean right) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	m_right = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.gyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.VISION.printVision();
		Robot.driveSubsystem.toHeading(Robot.VISION.getTurn(), m_right, Target.Boiler);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveSubsystem.isAimed(Robot.VISION.getTurn());
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
