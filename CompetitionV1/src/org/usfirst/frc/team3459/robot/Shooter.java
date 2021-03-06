package org.usfirst.frc.team3459.robot;

import org.usfirst.frc.team3459.ptlibj.StateMachine;
import org.usfirst.frc.team3459.ptlibj.Tilter;
import org.usfirst.frc.team3459.ptlibj.Trigger;
import org.usfirst.frc.team3459.ptlibj.VelocityTalon;

public class Shooter implements StateMachine<Shooter.State>{
	//negative speed = in
	public static final double INTAKESPEED = -3000;
	public static final double SHOOTDOWNSPEED = 4500;
	public static final double SHOOTUPSPEED = 5700;
	public static final double STOP = 0;
	
	private double lastSpeed = 0;
	
	public static final long FIREDURATION = 1000;
	
	public enum State {
		SHOOTUP, SHOOTDOWN, INTAKE, DOWN, UP, MOTOR_IN, MOTOR_OUT, DISABLE
	}

	private State state = State.DISABLE;
	private boolean changingState = false;
	
	private boolean startFire = false;
	private boolean firing = false;
	private long startFireDown = System.currentTimeMillis();
	private long startDown = System.currentTimeMillis();
	
	private long startShootUp = 0;
	private long startTimeShootUp = 2000;

	//***************************************************************************************
	
	private VelocityTalon talon1, talon2;
	private Trigger trigger;
	public Tilter tilter;
	
	public Shooter(VelocityTalon m1, VelocityTalon m2, int s1, int t1, int t2) {
		talon1 = m1;
		talon2 = m2;
		trigger = new Trigger(s1);
		tilter = new Tilter(t1,t2,false);		//invert tilter direction (change "false" to "true")
		
		talon1.setF(1.5);
		talon2.setF(1.5);
		
		talon1.setPID(0, 0, 0);
		talon2.setPID(0, 0, 0);
	}
	
	public void update() {
		trigger.update();
		
		switch(state) {
		case MOTOR_OUT:
			setWheels(SHOOTDOWNSPEED);
			break;
		
		case MOTOR_IN:
			setWheels(INTAKESPEED);
			break;
			
		case DISABLE:
			setWheels(STOP);
			break;
		
		case INTAKE:
			tilter.setDown();
			setWheels(INTAKESPEED);
			break;
		
		case SHOOTUP:
			tilter.setUp();
			
			if(changingState) {
				startShootUp = System.currentTimeMillis();
				changingState = false;
				return;
			}
			
			if(System.currentTimeMillis()-startShootUp > startTimeShootUp) {
				setWheels(SHOOTUPSPEED);
			} else {
				setWheels(STOP);
			}
			
			if(startFire) {
					trigger.fire();
					startFire = false;
				}
			break;
		
		case SHOOTDOWN:
			if(changingState) {
				setWheels(STOP);
				changingState = false;
				startDown = System.currentTimeMillis(); 
				return;
			}
			
			if(startFire) {
				startFireDown = System.currentTimeMillis();
				setWheels(SHOOTDOWNSPEED);
				startFire = false;
				firing = true;
			}
			
			if(firing == true && System.currentTimeMillis()-startFireDown > FIREDURATION) {
				setWheels(STOP);
				firing = false;
			}

			if(System.currentTimeMillis()-startDown > startTimeShootUp) {
				tilter.setDown();
			} 
			
			break;
		
		case DOWN:
			
			if(changingState) {
				setWheels(STOP);
				changingState = false;
				startDown = System.currentTimeMillis(); 
				return;
			}
			
			if(System.currentTimeMillis()-startDown > startTimeShootUp) {
				tilter.setDown();
			} else {
				setWheels(STOP);
			}

			break;
			
		case UP:
			tilter.setUp();
			setWheels(STOP);
			break;
		}
	}
	
	/**
	 * Sets the shooter wheels to have the same speed and oposite velocity
	 * @param val the speed value in a -1 < x < 1 scale, absolute value indicates
	 * speed negation flips direction
	 */
	public void setWheels(double val) {
		if(lastSpeed == val) 
			return;
		
		talon1.setSpeed(val);
		talon2.setSpeed(-val);
		talon1.update();
		talon2.update();
		
		lastSpeed = val;
	}
	
	/**
	 * Sets the state of the Shooter
	 * @param s
	 */
	public void setState(State s) {
		if(s != state) {
			state = s;
			changingState = true;	
			startFire = false;
		}
	}
	
	/**
	 * Starts the firing process
	 */
	public void fire() {
		startFire = true;
	}
	
	/**
	 * @return returns the current state of this Shooter
	 */
	public State getState() {
		return state;
	}
	
	public String toString() {		
		return "M1::" + talon1.toString() + "\nM2::" + talon2.toString();
	}
}
