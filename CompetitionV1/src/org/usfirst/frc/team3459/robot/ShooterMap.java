package org.usfirst.frc.team3459.robot;

import org.usfirst.frc.team3459.ptlibj.StateMap;

import edu.wpi.first.wpilibj.Joystick;

public class ShooterMap extends StateMap<Shooter.State>{ 
	public static final int shootUpB = 5;
	public static final int shootDownB = 3;
	public static final int intakeB = 4;
	public static final int disableB = 2;

	public static final int upB = 11;
	public static final int downB = 12;

	public static final int motorInB = 9;
	public static final int motorOutB = 10;
	
	/**
	 * Place put(int,Shooter.State) commands in the ShooterMap constructor to map the 
	 * integer button id number (corresponding to Joystick.getRawButton(int) value)
	 * to the intended Shooter.State
	 * 
	 * @param shooter the shooter object that you are managing the state of
	 * @param joystick the joystick that is controlling the state
	 */
	public ShooterMap(Shooter shooter, Joystick joystick) {
		super(shooter,joystick);
		
		put(shootUpB, Shooter.State.SHOOTUP);
		put(shootDownB, Shooter.State.SHOOTDOWN);
		put(intakeB, Shooter.State.INTAKE);
		put(disableB, Shooter.State.DISABLE);
		put(upB, Shooter.State.UP);
		put(downB, Shooter.State.DOWN);
		put(motorInB, Shooter.State.MOTOR_IN);
		put(motorOutB, Shooter.State.MOTOR_OUT);
	}
}