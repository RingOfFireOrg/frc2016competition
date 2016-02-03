package org.usfirst.frc.team9999.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

// this is a comment
public class MentorRobot extends SampleRobot {
	// Joysticks
	Joystick leftStick;
	Joystick rightStick;
	Joystick controlStick;
	Joystick potentiometers;

	// Buttons
	JoystickButton grabber1;
	JoystickButton grabber2;
	JoystickButton elevator1;
	JoystickButton elevator2;

	RobotDrive myRobot; // robot drive

	// Grabber*******************************
	// + Switches
	DigitalInput closeSwitch;
	DigitalInput openSwitch;
	DigitalInput pressureSwitch;

	// + Objects
	Victor grabberMotor;
	Grabber grabber;

	// Elevator******************************
	// + Switches
	DigitalInput topSwitch;
	DigitalInput bottomSwitch;

	// + Objects
	Victor elevatorMotor;
	Elevator elevator;

	public MentorRobot() {
		leftStick = new Joystick(0);
		rightStick = new Joystick(1);
		controlStick = new Joystick(2);
		potentiometers = new Joystick(3);

		grabber1 = new JoystickButton(controlStick, 12);
		grabber2 = new JoystickButton(controlStick, 11);
		elevator1 = new JoystickButton(controlStick, 3);
		elevator2 = new JoystickButton(controlStick, 4);

		myRobot = new RobotDrive(0, 1);
		myRobot.setExpiration(0.1);

		topSwitch = new DigitalInput(6);
		bottomSwitch = new DigitalInput(1);
		elevatorMotor = new Victor(2);
		elevator = new Elevator(elevatorMotor, elevator1, elevator2,
				bottomSwitch, topSwitch);

		openSwitch = new DigitalInput(7);
		closeSwitch = new DigitalInput(8);
		pressureSwitch = new DigitalInput(4);
		grabberMotor = new Victor(3);
		grabber = new Grabber(grabberMotor, grabber1, grabber2, openSwitch,
				closeSwitch, pressureSwitch);

	}

	public static boolean USING_NANO = true;
	public static long SCALE = USING_NANO ? 1000000 : 1;
	public static double DRIVEMULT = 1.0; // 1.35 for STEP 1.0 for no step
	public static long END_GRAB = 0 * SCALE;
	public static long END_ELEVATE = END_GRAB + (0 * SCALE);
	public static long END_FIRST_TURN = END_ELEVATE + (0 * SCALE);
	public static double END_DRIVE = END_FIRST_TURN
			+ (2500 * DRIVEMULT * SCALE);
	public static long END_SECOND_TURN = (long) (END_DRIVE + (700 * SCALE));

	// Simple Moving Autonomous
	
	public void autonomous() {
	long start = getTime();
	long elapsed;
	while (isAutonomous() && isEnabled()) { // while in auton
		elapsed = getTime() - start;

		if (elapsed < END_GRAB) { // if first 1 seconds
			grabber.runAutonomous(); // Grab Bin
		} else if (elapsed < END_ELEVATE) {
			grabber.stopAutonomous(); // Stop Grabbing Bin
			elevator.runAutonomous(); // Elevator Up
		} else if (elapsed < END_FIRST_TURN) {
			elevator.stopAutonomous(); // Stop Elevator Up
			myRobot.tankDrive(0.75, 0.05); // Turn Right
		} else if (elapsed < END_DRIVE) {
			myRobot.tankDrive(0.55, 0.55); // Go Forward
		} else if (elapsed < END_SECOND_TURN) {
			myRobot.tankDrive(0.75, 0.05); // Turn Right
		} else { // if after 2 seconds
			myRobot.tankDrive(0, 0); // Stop
		}
	}
	myRobot.tankDrive(0, 0);
}

private long getTime() {
	return USING_NANO ? System.nanoTime() : System.currentTimeMillis();
}
	
	//Backup of old auto
	
//	public void autonomous() {
//		long start = getTime();
//		long elapsed;
//		while (isAutonomous() && isEnabled()) { // while in auton
//			elapsed = getTime() - start;
//
//			if (elapsed < END_GRAB) { // if first 1 seconds
//				grabber.runAutonomous(); // Grab Bin
//			} else if (elapsed < END_ELEVATE) {
//				grabber.stopAutonomous(); // Stop Grabbing Bin
//				elevator.runAutonomous(); // Elevator Up
//			} else if (elapsed < END_FIRST_TURN) {
//				elevator.stopAutonomous(); // Stop Elevator Up
//				myRobot.tankDrive(0.75, 0.05); // Turn Right
//			} else if (elapsed < END_DRIVE) {
//				myRobot.tankDrive(0.55, 0.55); // Go Forward
//			} else if (elapsed < END_SECOND_TURN) {
//				myRobot.tankDrive(0.75, 0.05); // Turn Right
//			} else { // if after 2 seconds
//				myRobot.tankDrive(0, 0); // Stop
//			}
//		}
//		myRobot.tankDrive(0, 0);
//	}
//
//	private long getTime() {
//		return USING_NANO ? System.nanoTime() : System.currentTimeMillis();
//	}

	public void operatorControl() {
		myRobot.setSafetyEnabled(true);

		while (isOperatorControl() && isEnabled()) {
			myRobot.tankDrive(leftStick.getY() * 1, rightStick.getY() * 1); // set
																				// the
																				// tank
																				// drive

			grabber.update();
			elevator.update();

			Timer.delay(0.005); // wait for a motor update time
		}
	}
}