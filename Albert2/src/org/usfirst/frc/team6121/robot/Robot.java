
package org.usfirst.frc.team6121.robot;

import org.usfirst.frc.team6121.robot.commands.Autonomous;
import org.usfirst.frc.team6121.robot.subsystems.Agitator;
import org.usfirst.frc.team6121.robot.subsystems.AutoSubsystem;
import org.usfirst.frc.team6121.robot.subsystems.AutoSubsystem.alliance;
import org.usfirst.frc.team6121.robot.subsystems.AutoSubsystem.position;
import org.usfirst.frc.team6121.robot.subsystems.AutoSubsystem.strat;
import org.usfirst.frc.team6121.robot.subsystems.BallIntakeSubsystem;
import org.usfirst.frc.team6121.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team6121.robot.subsystems.Grip;
import org.usfirst.frc.team6121.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team6121.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static DriveSubsystem driveSubsystem;
	public static ShooterSubsystem shooterSubsystem;
	public static BallIntakeSubsystem ballIntakeSubsystem;
	public static Agitator agitator;
	public static Grip vision;
	public static VisionSubsystem VISION;
	
//	public static final NetworkTable table = NetworkTable.getTable("GRIP/targets");
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<position> pos = new SendableChooser<position>();
	SendableChooser<alliance> team = new SendableChooser<alliance>();
	SendableChooser<strat> strat = new SendableChooser<strat>();
	
//	UsbCamera camera0;
//	UsbCamera camera1;
//	Mat vid = new Mat();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.init();

		VISION = new VisionSubsystem();
		driveSubsystem = new DriveSubsystem();
		vision = new Grip();
		ballIntakeSubsystem = new BallIntakeSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		agitator = new Agitator();
		
		oi = new OI();

//		camera0 = new UsbCamera("USB Camera 0", 0);
//		camera0.setFPS(15);
//		camera0.setResolution(320, 240);
//		camera0.setBrightness(0);
//		camera0.setExposureManual(0);
//		camera0.setWhiteBalanceManual(20);
//		CameraServer.getInstance().startAutomaticCapture(camera0);
//		
//		camera1 = new UsbCamera("USB Camera 1", 1);
//		camera1.setBrightness(15);
//		camera1.setFPS(30);
//		camera1.setWhiteBalanceHoldCurrent();
//		camera1.setResolution(320, 240);
//		CameraServer.getInstance().startAutomaticCapture(camera1);
		
		pos.addDefault("Right", AutoSubsystem.position.RIGHT);
		pos.addObject("Middle", AutoSubsystem.position.MID);
		pos.addObject("Left", AutoSubsystem.position.LEFT);
		SmartDashboard.putData("Position", pos);
		
		team.addDefault("Blue", AutoSubsystem.alliance.BLUE);
		team.addObject("Red", AutoSubsystem.alliance.RED);
		SmartDashboard.putData("Alliance", team);
		
		strat.addDefault("Do all the things!!!", AutoSubsystem.strat.WIN);
		strat.addObject("Shoot", AutoSubsystem.strat.SHOOT);
		strat.addObject("Gear", AutoSubsystem.strat.GEAR);
		SmartDashboard.putData("Stratgey", strat);
		
		VISION.visionThread.start();
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = new Autonomous(strat.getSelected(), pos.getSelected(), team.getSelected());

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

//	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
//	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
