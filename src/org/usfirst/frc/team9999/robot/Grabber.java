package org.usfirst.frc.team9999.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class Grabber {

    Victor motor;
	
	Joystick speedStick;
	JoystickButton closeButton; 
	JoystickButton openButton;
	
	DigitalInput closeSwitch;
	DigitalInput openSwitch;
	DigitalInput pressureSwitch;
	
	boolean adjustable;
	
	double defaultSpeed = 1.0;
	
	public Grabber(Victor m, JoystickButton b1, JoystickButton b2, DigitalInput lS, DigitalInput  rS, DigitalInput pS) {
		motor = m;

		speedStick = null;
		closeButton = b1;
		openButton = b2;
		
		closeSwitch = lS;
		openSwitch = rS;
		pressureSwitch = pS;
		
		adjustable = false;
	}
	
	public Grabber(Victor m, Joystick sS, JoystickButton b1, JoystickButton b2, DigitalInput lS, DigitalInput rS, DigitalInput pS) {
		motor = m;
		
		speedStick = sS;
		closeButton = b1;
		openButton = b2;
		
		closeSwitch = lS;
		openSwitch = rS;
		pressureSwitch = pS;
		
		adjustable = true;
	}
	
	public void update() { 
		double speed;
		speed = getSpeedInput();
		motor.set(speed);
		
		SmartDashboard.putString("Grabber: ", Double.toString(speed));
	}
	
	public void runAutonomous() {
		double speed;
		speed = -getDefaultSpeed();

		boolean stop_close = speed > 0 && closeSwitch.get();
		boolean stop_open = speed < 0 && openSwitch.get();
		if(stop_close || stop_open) {
			speed = 0;
		}
		motor.set(speed);

		SmartDashboard.putString("Grabber: ", Double.toString(speed));
	}
	
	public double getSpeedInput() {
		double speed;
		
		speed = getDefaultSpeed();
		
		//Button checking
		if(openButton.get()) {
    		if(!closeButton.get()) 
    			speed *= -1;
    		else
    			speed = 0.0;
    	} else {
    		if(!closeButton.get()) 
    			speed = 0.0;
    	}
		
		//Limit checking
		boolean stop_close = speed > 0 && closeSwitch.get();
		boolean stop_open = speed < 0 && openSwitch.get();
		
		if(stop_close || stop_open)
			speed = 0;
		
		return speed;
	}

	private double getDefaultSpeed() {
		double speed;
		//Speed selection
		if(adjustable) {
			speed = (speedStick.getX()+1)/2;
		} else {
			speed = defaultSpeed;
		}
		return speed;
	}

	public void stopAutonomous() {
		motor.set(0);
	}

}
