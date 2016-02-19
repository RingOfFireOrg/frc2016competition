package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Servo;

public class Trigger {	
	private Servo a;
	private long fireTime = 1000;
	private double idleAngle = 0;
	private double fireAngle = 1;
	private boolean firing = false;
	private long lastFire = 0;
	
	public Trigger(int n) {
		a = new Servo(n);
	}
	
	public void fire() {
		if(!firing) {
			lastFire = System.currentTimeMillis();
			firing = true;
			a.set(fireAngle);
		}
	}
	
	public void update() {
		if(firing && System.currentTimeMillis()-lastFire > fireTime) {
			firing = false;
			a.set(idleAngle);
		}
	}
}