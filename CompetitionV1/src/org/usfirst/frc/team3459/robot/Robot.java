package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends SampleRobot {
	DriveTrain driveTrain;
	Joystick leftStick;
	Joystick rightStick;

	Joystick controlStick;
	JoystickButton fireB;
	JoystickButton shootUpB;
	JoystickButton shootDownB;
	JoystickButton intakeB;
	JoystickButton disableB;

	JoystickButton downB;
	JoystickButton upB;

	Shooter shooter;

	public Robot() {
		driveTrain = new DriveTrain(
				new RobotDrive(0, 1), 
				new Encoder(0, 1),
				new Encoder(2, 3)
			);
		leftStick = new Joystick(0);
		rightStick = new Joystick(1);

		controlStick = new Joystick(2);
		fireB = new JoystickButton(controlStick, 1);
		shootUpB = new JoystickButton(controlStick, 5);
		shootDownB = new JoystickButton(controlStick, 3);
		intakeB = new JoystickButton(controlStick, 4);
		disableB = new JoystickButton(controlStick, 2);

		downB = new JoystickButton(controlStick, 11);
		upB = new JoystickButton(controlStick, 12);

		shooter = new Shooter(10, 11, 3, 0, 1);
	}

	public void autonomous() {
		driveTrain.displace(1000, 1000);
		while(isAutonomous() && isEnabled()) {
			driveTrain.update();
		}
	}

	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			driveTrain.tankDrive(-leftStick.getY(), -rightStick.getY());
			driveTrain.update();
			updateShooter();

			Timer.delay(0.005);
		}
		shooter.setMode(Shooter.Mode.DISABLE);
	}

	public void updateShooter() {
		shooter.update();

		if (fireB.get()) {
			shooter.fire();
		}

		int buttonsPressed = (shootUpB.get() ? 1 : 0)
				+ (shootDownB.get() ? 1 : 0) + (intakeB.get() ? 1 : 0)
				+ (disableB.get() ? 1 : 0) + (downB.get() ? 1 : 0)
				+ (upB.get() ? 1 : 0);

		if (buttonsPressed > 1) {
			return;
		}

		if (shootUpB.get()) {
			shooter.setMode(Shooter.Mode.SHOOTUP);
		}
		if (shootDownB.get()) {
			shooter.setMode(Shooter.Mode.SHOOTDOWN);
		}
		if (intakeB.get()) {
			shooter.setMode(Shooter.Mode.INTAKE);
		}
		if (disableB.get()) {
			shooter.setMode(Shooter.Mode.DISABLE);
		}
		if (downB.get()) {
			shooter.setMode(Shooter.Mode.UP);
		}
		if (upB.get()) {
			shooter.setMode(Shooter.Mode.DOWN);
		}
	}
}
