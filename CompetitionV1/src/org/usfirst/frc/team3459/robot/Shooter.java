package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Shooter implements StateMachine<Shooter.State>{
	public static final double INSPEED = -0.6;
	public static final double OUTSPEED = 1;
	public static final double STOP = 0;
	private double lastSpeed = 0;
	
	private double multiplier = 1.0;
	
	public static final long FIREDURATION = 1000;
	
	public enum State {
		SHOOTUP, SHOOTDOWN, INTAKE, DOWN, UP, MOTOR_IN, MOTOR_OUT, DISABLE
	}

	public enum WheelDirection {
		OUT, IN, STOP 
	}
	
	private State state = State.DISABLE;
	private boolean changingState = false;
	
	private boolean startFire = false;
	private boolean firing = false;
	private long startFireDown = System.currentTimeMillis();
	private long startDown = System.currentTimeMillis();
	
	private long startShootUp = 0;
	private long startTimeShootUp = 2000;

	PiClient myPi = new PiClient();

	//***************************************************************************************
	
	private CANTalon motor1, motor2;
	private Trigger trigger;
	public Tilter tilter;
	
	public Shooter(int m1, int m2, int s1, int t1, int t2) {
		motor1 = new CANTalon(m1);
		motor2 = new CANTalon(m2);
		trigger = new Trigger(s1);
		tilter = new Tilter(t1,t2,false);		//invert tilter direction (change "false" to "true")
	}
	
	public void update() {
		trigger.update();
		
		switch(state) {
		case MOTOR_OUT:
			setWheelDirection(WheelDirection.OUT);
			break;
		
		case MOTOR_IN:
			setWheelDirection(WheelDirection.IN);
			break;
			
		case DISABLE:
			setWheelDirection(WheelDirection.STOP);
			break;
		
		case INTAKE:
			tilter.setDown();
			setWheelDirection(WheelDirection.IN);
			break;
		
		case SHOOTUP:
			tilter.setUp();
			
			if(changingState) {
				startShootUp = System.currentTimeMillis();
				changingState = false;
				return;
			}
			
			if(System.currentTimeMillis()-startShootUp > startTimeShootUp) {
				setWheelDirection(WheelDirection.OUT);
			} else {
				setWheelDirection(WheelDirection.STOP);
			}
			
			if(startFire) {
					trigger.fire();
					startFire = false;
				}
			break;
		
		case SHOOTDOWN:
			if(changingState) {
				setWheelDirection(WheelDirection.STOP);
				changingState = false;
				startDown = System.currentTimeMillis(); 
				return;
			}
			
			if(startFire) {
				startFireDown = System.currentTimeMillis();
				setWheelDirection(WheelDirection.OUT);
				trigger.fire();
				startFire = false;
				firing = true;
			}
			
			if(firing == true && System.currentTimeMillis()-startFireDown > FIREDURATION) {
				setWheelDirection(WheelDirection.STOP);
				firing = false;
			}

			if(System.currentTimeMillis()-startDown > startTimeShootUp) {
				tilter.setDown();
			} 
			
			break;
		
		case DOWN:
			
			if(changingState) {
				setWheelDirection(WheelDirection.STOP);
				changingState = false;
				startDown = System.currentTimeMillis(); 
				return;
			}
			
			if(System.currentTimeMillis()-startDown > startTimeShootUp) {
				tilter.setDown();
			} else {
				setWheelDirection(WheelDirection.STOP);
			}

			break;
			
		case UP:
			tilter.setUp();
			setWheelDirection(WheelDirection.STOP);
			break;
		}
	}
	
	public void setWheelDirection(WheelDirection dir) {
		switch (dir) {
		case OUT:
			setWheels(OUTSPEED * multiplier);
			break;
		
		case IN:
			setWheels(INSPEED);
			break;
		
		case STOP:
			setWheels(STOP);
			break;
		}
	}
	
	public void setMultiplier(double newMultiplier){
		multiplier = newMultiplier;
	}
	
	/**
	 * Sets the shooter wheels to have the same speed and oposite velocity
	 * @param val the speed value in a -1 < x < 1 scale, absolute value indicates
	 * speed negation flips direction
	 */
	public void setWheels(double val) {
		if(lastSpeed == val) 
			return;
		
		motor1.set(val);
		motor2.set(-val);
		
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
	
	public State getState() {
		return state;
	}
}
