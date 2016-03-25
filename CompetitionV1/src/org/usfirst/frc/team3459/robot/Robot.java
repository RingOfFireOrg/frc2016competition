package org.usfirst.frc.team3459.robot;	

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends SampleRobot {
	DriveTrain driveTrain;
	Joystick leftStick;
	Joystick rightStick;

	Joystick controlStick;
	JoystickButton fireB;

	
	JoystickButton overRide;
	
	Shooter shooter;
	
	ShooterMap stateMap;
	
	PiClient myPi = new PiClient();
	
	public Robot() {
//		driveTrain = new DriveTrain(
//				new RobotDrive(0, 1), 
//				new Encoder(0, 1),
//				new Encoder(2, 3)
//			);
		leftStick = new Joystick(0);
		rightStick = new Joystick(1);

		controlStick = new Joystick(2);
		fireB = new JoystickButton(controlStick, 1);
		
		overRide = new JoystickButton(controlStick, 7);

		shooter = new Shooter(14, 11, 3, 0, 1);
//		stateMap = new ShooterMap(shooter,controlStick);
	}

	public void autonomous() {
//		double base = 0.60;
//		double rightFactor = 1.00;
//		long start = System.currentTimeMillis();
//		long elapsed;
//		long t1 = 14000;
//		
//		while(isAutonomous() && isEnabled()) {
//			elapsed = System.currentTimeMillis() - start;
//			
//			if(elapsed < t1) {
//				driveTrain.tankDrive(base,base*rightFactor);
//			} else {
//				driveTrain.tankDrive(0, 0);	
//			}
//			driveTrain.update();
//		}
//		driveTrain.tankDrive(0, 0);
//		driveTrain.update(); 
	}

	public void operatorControl() {
		shooter.setState(Shooter.State.SHOOTUP);
		while (isOperatorControl() && isEnabled()) {
//			driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());
//			driveTrain.update();
//			driveTrain.printEncoders();
			
//			stateMap.update();
			shooter.update();
			Shooter.OUTSPEED = controlStick.getThrottle()*5000;
			
			System.out.println(shooter.toString());
			
			Timer.delay(0.005);
		}
		shooter.setState(Shooter.State.DISABLE);
	}
}
