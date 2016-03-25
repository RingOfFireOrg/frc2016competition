package org.usfirst.frc.team3459.ptlibj;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * @author Kyle Brown
 */
public class Tilter {
	/**
	 * The solenoid
	 */
	DoubleSolenoid solenoid;
	
	/**
	 * 
	 * @param p0 the port of the first solenoid
	 * @param p1 the port of the second solenoid
	 * @param inversion whether or not to invert up/down
	 */
	public Tilter(int p0, int p1, boolean inversion) {
		if(!inversion) {
			solenoid = new DoubleSolenoid(p0,p1);
		} else { 
			solenoid = new DoubleSolenoid(p1,p0);
		}
	}

	/**
	 * set the Tilter position to up
	 */
	public void setUp() {
		if(solenoid.get() != DoubleSolenoid.Value.kForward)
			solenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * set the Tilter position to down
	 */
	public void setDown() {
		if(solenoid.get() != DoubleSolenoid.Value.kReverse)
			solenoid.set(DoubleSolenoid.Value.kReverse);
	}
}
