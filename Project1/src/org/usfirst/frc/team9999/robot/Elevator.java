package org.usfirst.frc.team9999.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class Elevator {

	Victor motor;

	Joystick speedStick;
	JoystickButton upButton;
	JoystickButton downButton;

	DigitalInput bottomSwitch;
	DigitalInput topSwitch;

	boolean adjustable;

	double defaultSpeed = 0.5;

	public Elevator(Victor m, JoystickButton b1, JoystickButton b2,
			DigitalInput bS, DigitalInput tS) {
		motor = m;
		upButton = b1;
		downButton = b2;
		bottomSwitch = bS;
		topSwitch = tS;
		speedStick = null;

		adjustable = false;
	}

	public Elevator(Victor m, Joystick sS, JoystickButton b1,
			JoystickButton b2, DigitalInput bS, DigitalInput tS) {
		motor = m;
		upButton = b1;
		downButton = b2;
		bottomSwitch = bS;
		topSwitch = tS;
		speedStick = sS;

		adjustable = true;
	}

	public void update() {
		double speed;
		speed = getSpeedInput();
		motor.set(speed);

		SmartDashboard.putString("Elevator: ", Double.toString(speed));
	}

	public void runAutonomous() {
		double speed;
		speed = -getDefaultSpeed();
		
		// Limit checking
		boolean stop_top = speed > 0 && bottomSwitch.get();
		boolean stop_bottom = speed < 0 && topSwitch.get();
		if (stop_top || stop_bottom) {
			speed = 0;
		}
		motor.set(speed);

		SmartDashboard.putString("Elevator: ", Double.toString(speed));
	}

	public double getSpeedInput() {
		boolean stop_top, stop_bottom;
		double speed;

		speed = getDefaultSpeed();

		// Button checking
		if (downButton.get()) {
			if (!upButton.get())
				speed *= -1;
			else
				speed = 0.0;
		} else {
			if (!upButton.get())
				speed = 0.0;
		}

		// Limit checking
		stop_top = speed > 0 && bottomSwitch.get();
		stop_bottom = speed < 0 && topSwitch.get();

		if (stop_top || stop_bottom)
			speed = 0;

		return speed;
	}

	private double getDefaultSpeed() {
		double speed;
		// Speed selection
		if (adjustable) {
			speed = (speedStick.getX() + 1) / 2;
		} else {
			speed = defaultSpeed;
		}
		return speed;
	}

	public void stopAutonomous() {
		motor.set(0);
		SmartDashboard.putString("Grabber: ", Double.toString(0));
	}
}
