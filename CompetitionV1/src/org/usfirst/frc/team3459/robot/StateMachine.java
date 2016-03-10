package org.usfirst.frc.team3459.robot;

public interface StateMachine<S> {
	/**
	 * interface method that tells the StateMachine implementation to enter a state
	 * 
	 * @param s the state being set
	 */
	void setState(S s);
}
