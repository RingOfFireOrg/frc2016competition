package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain {
	private RobotDrive drive;
	private Encoder lEncoder;
	private Encoder rEncoder;
	
	public enum State {SPEED,DISPLACEMENT};
	State state = State.SPEED;
	int lDisplacement = 0;
	int rDisplacement = 0;
	double leftSpeed, rightSpeed;
	public DriveTrain(RobotDrive drive, Encoder e1, Encoder e2) {
		this.drive = drive;
		this.lEncoder = e2;
		this.rEncoder = e1;
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		state = State.SPEED;
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}
	
	public void displace(int lDisplacement, int rDisplacement) {
		state = State.DISPLACEMENT;
		lEncoder.reset();
		rEncoder.reset();
		
		this.lDisplacement = lDisplacement;
		this.rDisplacement = rDisplacement;
	}
	
	public void update() {
		if(state == State.SPEED) {
			drive.tankDrive(leftSpeed, rightSpeed);	
			return;
		}
		
		if(lEncoder.get() < lDisplacement) {
			leftSpeed = 1;
		} else {
			leftSpeed = 0;
		}
		
		if(rEncoder.get() < rDisplacement) {
			rightSpeed = 1;
		} else {
			rightSpeed = 0;
		}
		
		drive.tankDrive(leftSpeed, rightSpeed);
	}
	
	public void printEncoders() {
//		String output = "Left: " + lEncoder.get() + " Right: " + rEncoder.get();
//		DriverStation.reportError(output,false);
	}
}
