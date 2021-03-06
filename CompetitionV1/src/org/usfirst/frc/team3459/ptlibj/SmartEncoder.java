package org.usfirst.frc.team3459.ptlibj;

import edu.wpi.first.wpilibj.Encoder;

/**
 * @author Kyle Brown
 */
public class SmartEncoder extends Encoder {
	private long lastCall = 0;
	private long now;
	private long elapsed;
	
	private double lastPos = 0;
	private double pos;
	private double speed;
	
	/**
	 * @param p0 the first Digital I/O port
	 * @param p1 the second Digital I/O port
	 */
	public SmartEncoder(int p0, int p1) {
		super(p0,p1);
	}
	
	/**
	 * @param name the name it will be referred to by in the resulting string
	 * @return the string representation of the Encoders state
	 */
	public String getState(String name) {
		now = System.currentTimeMillis();
		elapsed = now - lastCall;
		
		pos = get();
		speed = (pos-lastPos)/elapsed;
		
		return name + ":: pos:" + pos + "\t|| spd: " + speed;
	}
}