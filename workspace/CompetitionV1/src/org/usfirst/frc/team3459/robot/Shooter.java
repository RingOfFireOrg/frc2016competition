package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Shooter {
	public static final double INSPEED = -0.6;
	public static final double OUTSPEED = 1;
	public static final double STOP = 0;
	private double lastSpeed = 0;
	
	public static final long FIREDURATION = 1000;
	
	public enum Mode {SHOOTUP,SHOOTDOWN,INTAKE,DOWN,UP,DISABLE}

	private Mode mode = Mode.DISABLE;
	private Mode lastMode = mode;
	private boolean changingMode = false;
	
	private boolean startFire = false;
	private boolean firing = false;
	private long startShootDown = System.currentTimeMillis();
	//***************************************************************************************
	
	private CANTalon motor1, motor2;
	private Trigger trigger;
	private Tilter tilter;
	
	public Shooter(int m1, int m2, int s1, int t1, int t2) {
		motor1 = new CANTalon(m1);
		motor2 = new CANTalon(m2);
		trigger = new Trigger(s1);
		tilter = new Tilter(t1,t2,false);
	}
	
	public void update() {
		trigger.update();
		
		switch(mode) {
		case DISABLE:
			setWheels(STOP);
			break;
		
		case INTAKE:
			tilter.setDown();
			setWheels(INSPEED);
			break;
		
		case SHOOTUP:
			tilter.setUp();
			setWheels(OUTSPEED);
			
			if(startFire) {
				trigger.fire();
				startFire = false;
			}
			break;
		
		case SHOOTDOWN:
			tilter.setDown();
			
			if(changingMode) {
				setWheels(STOP);
				changingMode = false;
				return;
			}
			
			if(startFire) {
				startShootDown = System.currentTimeMillis();
				setWheels(OUTSPEED);
				startFire = false;
				firing = true;
			}
			
			if(firing == true && System.currentTimeMillis()-startShootDown > FIREDURATION) {
				setWheels(STOP);
				firing = false;
			}
			break;
		
		case DOWN: 
			tilter.setDown();
			setWheels(0);
			break;
			
		case UP:
			tilter.setUp();
			setWheels(0);
			break;
		}
	}
	
	public void setWheels(double val) {
		if(lastSpeed == val) 
			return;
		
		motor1.set(val);
		motor2.set(val);
		
		lastSpeed = val;
	}
	
	public void setMode(Mode m) {
		if(m != mode) {
			lastMode = mode;
			mode = m;
			changingMode = true;	
		}
	}
	
	public void revertMode() {
		mode = lastMode;
		changingMode = true;	
	}
	
	public void fire() {
		startFire = true;
	}
}