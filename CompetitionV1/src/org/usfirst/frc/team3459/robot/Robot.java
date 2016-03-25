package org.usfirst.frc.team3459.robot;	

import org.usfirst.frc.team3459.ptlibj.DriveTrain;
import org.usfirst.frc.team3459.ptlibj.VelocityTalon;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
	private static double F = 1.5;
	private static double P = 0.8;
	private static double I = 0;
	private static double D = 0;
	
	
	DriveTrain driveTrain;
	Joystick leftStick;
	Joystick rightStick;

	Joystick controlStick;
	JoystickButton fireB;

	
	JoystickButton overRide;
	
	VelocityTalon talon1, talon2;
	Shooter shooter;
	
	ShooterMap stateMap;
	
	PiClient myPi = new PiClient();
	
	public Robot() {
//		driveTrain = new DriveTrain(
//				new RobotDrive(0, 1), 
//				new SmartEncoder(0, 1),
//				new SmartEncoder(2, 3)
//			);
		leftStick = new Joystick(0);
		rightStick = new Joystick(1);

		controlStick = new Joystick(2);
		fireB = new JoystickButton(controlStick, 1);
		
		overRide = new JoystickButton(controlStick, 7);

		talon1 = VelocityTalon.createTalon(14,CANTalon.FeedbackDevice.QuadEncoder,20);
		talon1 = VelocityTalon.createTalon(11,CANTalon.FeedbackDevice.QuadEncoder,20);
		shooter = new Shooter(talon1, talon2, 3, 0, 1);
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
			driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());
			
			stateMap.update();
			shooter.update();
			
//			System.out.println(shooter.toString());
			
			Timer.delay(0.005);
		}
		shooter.setState(Shooter.State.DISABLE);
	}
	
	//F recommendation: 1.5
	public void test() {
		talon1.setF(0);
		talon2.setF(0);
		
		talon1.setPID(0, 0, 0);
		talon2.setPID(0, 0, 0);
		
		boolean testMode = true;
		boolean lastPress = false;
		
		talon1.setInTest(testMode);
		talon1.setInTest(testMode);
		
		while(isTest() && isEnabled()) {
			if(controlStick.getRawButton(2)) {
				if(!lastPress) {
					testMode = !testMode;
					talon1.setInTest(testMode);
					talon1.setInTest(testMode);
					
					if(!testMode) {
						talon1.updateF();
						talon2.updateF();
						
						System.out.println("T1: " + talon1.getFSuggestion() + " T2: " + talon2.getFSuggestion());
					}
				}
				
				lastPress = true;
			}
			
			talon1.setSpeed(controlStick.getThrottle()*5000);
			talon2.setSpeed(controlStick.getThrottle()*5000);
			
			talon1.update();
			talon2.update();
		}
	}
	
	public void getFPID() {
		F = SmartDashboard.getNumber("F", F);
		P = SmartDashboard.getNumber("P", P);
		I = SmartDashboard.getNumber("I", I);
		D = SmartDashboard.getNumber("D", D);
	}
}