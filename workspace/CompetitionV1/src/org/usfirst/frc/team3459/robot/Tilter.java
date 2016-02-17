package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Tilter {
	
	DoubleSolenoid solenoid;
	
	public Tilter(int p1, int p2, boolean inversion) {
		if(!inversion) {
			solenoid = new DoubleSolenoid(p1,p2);
		} else { 
			solenoid = new DoubleSolenoid(p2,p1);
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
