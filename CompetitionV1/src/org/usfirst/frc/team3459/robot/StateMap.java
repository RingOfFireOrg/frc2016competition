package org.usfirst.frc.team3459.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;

public class StateMap { 
	public static final int shootUpB = 5;
	public static final int shootDownB = 3;
	public static final int intakeB = 4;
	public static final int disableB = 2;

	public static final int upB = 11;
	public static final int downB = 12;

	public static final int motorInB = 13;
	public static final int motorOutB = 14;
	
	private Map<Integer,Shooter.Mode> buttonMap;
	private Shooter shooter;
	private Joystick joystick;
	
	public StateMap(Shooter shooter, Joystick joystick) {
		this.shooter = shooter;
		this.joystick = joystick;
		
		buttonMap = new HashMap<>();
		buttonMap.put(shootUpB, Shooter.Mode.SHOOTUP);
		buttonMap.put(shootDownB, Shooter.Mode.SHOOTDOWN);
		buttonMap.put(intakeB, Shooter.Mode.INTAKE);
		buttonMap.put(disableB, Shooter.Mode.DISABLE);
		buttonMap.put(upB, Shooter.Mode.UP);
		buttonMap.put(downB, Shooter.Mode.DOWN);
		buttonMap.put(motorInB, Shooter.Mode.MOTOR_IN);
		buttonMap.put(motorOutB, Shooter.Mode.MOTOR_OUT);
	}
	
	public void update() {
		int buttonsPressed = 0;
		int activeButton = 0;
		for(Integer buttonNum : buttonMap.keySet()) {
			if(joystick.getRawButton(buttonNum)) {
				activeButton = buttonNum;
				buttonsPressed++;	
			}
		}
		
		if(buttonsPressed > 1) 
			return;
		
		shooter.setMode(buttonMap.get(activeButton));
	}
}
