package org.usfirst.frc.team6121.robot.subsystems;

import org.usfirst.frc.team6121.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {

	public void setSpeed(double a) {
		RobotMap.agitator.set(-a);
	}

    public void initDefaultCommand() {
    }
}

