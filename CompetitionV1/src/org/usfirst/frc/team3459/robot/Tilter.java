package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Tilter {
	
	DoubleSolenoid solenoid;
	
	public Tilter(int p0, int p1, boolean inversion) {
		if(!inversion) {
			solenoid = new DoubleSolenoid(p0,p1);
		} else { 
			solenoid = new DoubleSolenoid(p1,p0);
		}
	}
	
	public void setUp() {
		if(solenoid.get() != DoubleSolenoid.Value.kForward)
			solenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setDown() {
		if(solenoid.get() != DoubleSolenoid.Value.kReverse)
			solenoid.set(DoubleSolenoid.Value.kReverse);
	}
}
