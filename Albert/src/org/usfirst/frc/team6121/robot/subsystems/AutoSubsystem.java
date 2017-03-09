package org.usfirst.frc.team6121.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class AutoSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
	// here. Call these from Commands.

	public enum alliance {
		BLUE,
		RED
	}

	public enum position {
		LEFT,
		MID,
		RIGHT
	}

	public enum strat {
		SHOOT, GEAR, WIN

	}

	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

