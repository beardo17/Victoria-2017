package org.usfirst.frc.team6121.robot.commands;

import org.usfirst.frc.team6121.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AimShot extends Command {
	private boolean m_right;

    public AimShot(boolean right) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_right = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.driveSubsystem.aimGear(Robot.VISION.getTurnGear(), m_right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveSubsystem.isGearAimed(Robot.VISION.getTurnGear());
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
