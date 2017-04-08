package org.usfirst.frc.team6121.robot;

import org.usfirst.frc.team6121.robot.commands.Agitate;
import org.usfirst.frc.team6121.robot.commands.AimGear;
import org.usfirst.frc.team6121.robot.commands.AimShot;
import org.usfirst.frc.team6121.robot.commands.Drive;
import org.usfirst.frc.team6121.robot.commands.Intake;
import org.usfirst.frc.team6121.robot.commands.Shooting;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static Joystick xboxController_1;
	public static Joystick xboxController_2;
	
	OI() {
		
		xboxController_1 = new Joystick(RobotMap.XBOX_CONTROLLER_1);
		xboxController_2 = new Joystick(RobotMap.XBOX_CONTROLLER_2);
		
		Button ballIntakeButton = new JoystickButton(xboxController_1, RobotMap.R_BUTTON);
		ballIntakeButton.toggleWhenPressed(new Intake(1));
		
		Button ballIntakeButton1 = new JoystickButton(xboxController_1, RobotMap.L_BUTTON);
		ballIntakeButton1.toggleWhenPressed(new Intake(-1));
		
		Button backLeft = new JoystickButton(xboxController_1, RobotMap.X_BUTTON);
		backLeft.whenPressed(new Drive(-1, -0.3601348999, 0.5));
		
		Button backRight = new JoystickButton(xboxController_1, RobotMap.B_BUTTON);
		backRight.whenPressed(new Drive(-1, 0.3601348999, 0.5));
		
		Button backFast = new JoystickButton(xboxController_1, RobotMap.Y_BUTTON);
		backFast.whenPressed(new Drive(-1, 0, 0.75));
		
		Button gearButton = new JoystickButton(xboxController_1, RobotMap.A_BUTTON);
		gearButton.whenPressed(new AimGear(true));
		
		Button aimButton = new JoystickButton(xboxController_2, RobotMap.L_BUTTON);
		aimButton.whileHeld(new AimShot(true));
		
		Button shooterButton = new JoystickButton(xboxController_2, RobotMap.R_BUTTON);
		shooterButton.whileHeld(new Shooting());
		
		Button agitatorButton = new JoystickButton(xboxController_2, RobotMap.Y_BUTTON);
		agitatorButton.whileHeld(new Agitate());
		
		
//		Button aimButton = new JoystickButton(xboxController_2, RobotMap.L_BUTTON);
	
	
	
	}
}
