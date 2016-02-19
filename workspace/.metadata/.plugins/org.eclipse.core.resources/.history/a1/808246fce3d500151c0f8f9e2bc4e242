package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Servo;

public class Trigger {
	public static final double ANGLE_IDLE = 0;
	public static final double ANGLE_FIRE = 1;
	public static final long TIME_FIRE = 1;
	
	private Servo a;
	private long fireTime = 0;
	private double idleAngle = 0;
	private double fireAngle = 0;
	private boolean firing = false;
	private long lastFire = 0;
	
	public Trigger(int n) {
		a = new Servo(n);
		this.fireTime = TIME_FIRE;
		this.idleAngle = ANGLE_IDLE;
		this.fireAngle = ANGLE_FIRE;
	}
	
	public void fire() {
		if(!firing) {
			lastFire = System.currentTimeMillis();
			firing = true;
			a.set(fireAngle);
		}
	}
	
	public void update() {
		if(firing && lastFire-System.currentTimeMillis() > fireTime) {
			firing = false;
			a.set(idleAngle);
		}
	}
}
