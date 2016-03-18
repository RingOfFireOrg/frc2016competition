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
	
	long lastCall = 0;
	long now = 0;
	long elapsed = 0;
	double lastLPos = 0;
	double lastRPos = 0;
	double lPos;
	double rPos;
	double lSpeed;
	double rSpeed;
	int count = 0;
	
	public void printEncoders() {
		count++;
		if(count%10 != 0)
			return;
		
		now = System.currentTimeMillis();
		elapsed = now - lastCall;
		
		lPos = lEncoder.get();
		rPos = rEncoder.get();
		lSpeed = (lPos-lastLPos)/elapsed;
		rSpeed = (rPos-lastRPos)/elapsed;
//		
//		String outputPosition = "Pos|Left: " + lPos + " Right: " + rPos;
//		String outputRate = "Speed|Left: " + lSpeed + " Right: " + rSpeed;
//		
//		DriverStation.reportError(outputRate,false);//outputPosition + " //// " + 
		DriverStation.reportError(Double.toString(lSpeed/rSpeed), false);
		lastLPos = lPos;
		lastRPos = rPos;
		lastCall = now;
	}
}
