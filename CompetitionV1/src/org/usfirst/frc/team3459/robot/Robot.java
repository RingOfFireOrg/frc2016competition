package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends SampleRobot {
	DriveTrain driveTrain;
	Joystick leftStick;
	Joystick rightStick;
	
	Preferences pref;

	Joystick controlStick;
	JoystickButton fireB;

	JoystickButton overRide;

	Shooter shooter;

	ShooterMap stateMap;

	PiClient myPi = new PiClient();

	I2C I2CBus;

	public Robot() {
		driveTrain = new DriveTrain(new RobotDrive(0, 1), new Encoder(0, 1), new Encoder(2, 3));

		leftStick = new Joystick(0);
		rightStick = new Joystick(1);
		controlStick = new Joystick(2);

		fireB = new JoystickButton(controlStick, 1);
		overRide = new JoystickButton(controlStick, 7);

		shooter = new Shooter(14, 11, 3, 0, 1);
		stateMap = new ShooterMap(shooter, controlStick);

		I2CBus = new I2C(I2C.Port.kOnboard, 0x42);
		
		pref = Preferences.getInstance();
	}

	public void autonomous() {
		double base = 0.80;
		double rightFactor = 1.00;
		long start = System.currentTimeMillis();
		long elapsed;
		long t1 = 11000;

		while (isAutonomous() && isEnabled()) {
			elapsed = System.currentTimeMillis() - start;

			if (elapsed < t1) {
				driveTrain.tankDrive(base, base * rightFactor);
			} else {
				driveTrain.tankDrive(0, 0);
			}
			driveTrain.update();
		}
		driveTrain.tankDrive(0, 0);
		driveTrain.update();

	}



	
	
	public void lightControl(int num, int red, int green, int blue) {
		byte[] toSend = new byte[1];
		toSend[0] = (byte) num;
		I2CBus.transaction(toSend, 1, null, 0);
		toSend[0] = (byte) (red/2);
		I2CBus.transaction(toSend, 1, null, 0);
		toSend[0] = (byte) (green/2);
		I2CBus.transaction(toSend, 1, null, 0);
		toSend[0] = (byte) (blue/2);
		I2CBus.transaction(toSend, 1, null, 0);
		Timer.delay(0.01); // Wait to give time for the lights to update

	}
	public int addColor(int oldVal){
		int newVal = oldVal + 32;
		if(newVal > 256){
			newVal = 0;
		}
		return newVal;
	}

	public void operatorControl() {
/*
		JoystickButton redButton;
		JoystickButton blueButton;
		JoystickButton greenButton;
		
		int redVal = 0;
		int greenVal = 0;
		int blueVal = 0;
		
		
		redButton = new JoystickButton(controlStick, 8);
		greenButton = new JoystickButton(controlStick, 10);		
		blueButton = new JoystickButton(controlStick, 12);
*/
				
		
	//	boolean hasSent = false;
		
//		Timer.start();
		
		double percentSpeed = 1.00;
		percentSpeed = pref.getDouble("percentSpeed", 1.00); 	//Slow down via SmartDashboard for Outreaches!
		System.out.println("Percent speed: " + percentSpeed);
		
		while (isOperatorControl() && isEnabled()) {
			
			driveTrain.tankDrive(-leftStick.getY() * percentSpeed, -rightStick.getY() * percentSpeed);
			driveTrain.update();
			//driveTrain.printEncoders();

			stateMap.update();
			shooter.update();

			Timer.delay(0.005);

			if (fireB.get()) {
				if (shooter.getState() == Shooter.State.SHOOTUP) {
					shooter.fire();
				} else {
					shooter.fire();
				}
			}
/*
			if (redButton.get()) { 
				redVal = addColor(redVal);
			}
			if (greenButton.get()) {
				greenVal = addColor(greenVal);
			}
						
			if (blueButton.get()) {
				blueVal = addColor(blueVal);
			}
*/
			
/*		
		if (hasSent == false) {
				for (int i = 0; i < 8; i++) {
					lightControl(i, 0, 0, 0);
				}
				// lightControl(0, 200, 0, 0);
				// lightControl(3, 0, 0, 0);
				for (int i = 0; i < 8; i++) {
					lightControl(i, redVal, greenVal, blueVal);
					Timer.delay(0.05);
				}
				//hasSent = true;

		}
		*/
		}
		shooter.setState(Shooter.State.DISABLE);
	}
}
