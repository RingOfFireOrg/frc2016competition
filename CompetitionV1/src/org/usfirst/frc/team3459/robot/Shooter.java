package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Shooter implements StateMachine<Shooter.State>{
	public static final double INSPEED = 0.6;
	public static double OUTSPEED = 1500;//-1;
	public static final double STOP = 0;
	
	public static final double P = 0.8;
	public static final double I = 0.00;
	public static final double D = 0.0;
	
	
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
	
//	private long startShootUp = 0;
	private long startTimeShootUp = 2000;
	//***************************************************************************************
	
	private CANTalon motor1, motor2;
	private Trigger trigger;
	public Tilter tilter;
	
	public Shooter(int m1, int m2, int s1, int t1, int t2) {
		motor1 = new CANTalon(m1);
		motor2 = new CANTalon(m2);
		trigger = new Trigger(s1);
		tilter = new Tilter(t1,t2,false);		//invert tilter direction (change "false" to "true")
		motor1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		motor2.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		motor1.reverseSensor(false);
		motor2.reverseSensor(false);
		
		motor1.configEncoderCodesPerRev(20);
		motor2.configEncoderCodesPerRev(20);
		
		motor1.configPeakOutputVoltage(+12.0f, -12.0f);
		motor2.configPeakOutputVoltage(+12.0f, -12.0f);
		
		motor1.configNominalOutputVoltage(+0.0f, -0.0f);
		motor2.configNominalOutputVoltage(+0.0f, -0.0f);
		
		motor2.setInverted(true);
		
		motor1.changeControlMode(CANTalon.TalonControlMode.Speed);
		motor2.changeControlMode(CANTalon.TalonControlMode.Speed);

		motor1.setProfile(0);
		motor1.setF(1.5);
		motor1.setP(P);
		motor1.setI(I);
		motor1.setD(D);
//		
		motor2.setProfile(0);
		motor2.setF(1.5);
		motor2.setP(P);
		motor2.setI(I);
		motor2.setD(D);
		
//		motor1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//		motor2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
//		motor1.getSpeed();
	}
	
	public void update() {
		trigger.update();
		
		switch(state) {
		case MOTOR_OUT:
			setWheels(OUTSPEED);
			break;
		
		case MOTOR_IN:
			setWheels(INSPEED);
			break;
			
		case DISABLE:
			setWheels(STOP);
			break;
		
		case INTAKE:
			tilter.setDown();
			setWheels(INSPEED);
			break;
		
		case SHOOTUP:
			tilter.setUp();
			
			if(changingState) {
//				startShootUp = System.currentTimeMillis();
				changingState = false;
				return;
			}
			
//			if(System.currentTimeMillis()-startShootUp > startTimeShootUp) {
				setWheels(OUTSPEED);
//			} else {
//				setWheels(STOP);
//			}
			
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
				setWheels(OUTSPEED);
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
		
		motor1.set(val);
		motor2.set(val);
		
		lastSpeed = val;
//		System.out.println(val);
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
	
	public String average(double d1, double d2) {
		return Double.toString((Math.abs(d1)+Math.abs(d2))/2.0);
	}
	
	public String toString() {
		String output = "out: " + average(motor1.getOutputVoltage()/motor1.getBusVoltage(),
				motor2.getOutputVoltage()/motor2.getBusVoltage());
		
		String speed = "spd: " + average(motor1.getSpeed(),motor2.getSpeed());
		String error = "err: " + average(motor1.getClosedLoopError(), motor2.getClosedLoopError());
		String target = "trg: " + Shooter.OUTSPEED;
		
		return output + "\t|| " + speed + "\t|| " + error + "\t|| " + target;
	}
	
	double nativeUnitsPerRev = 20*4;
	double scale = 1023;
	double timeConst = 600;
	
	double f1=0;
	double f2=0;
	
	int count = 0;
	
	public String getFSuggestionText() {
		if(count%100 != 0)
			return "";
		
		f1 = (scale*timeConst)/(Math.abs(motor1.getSpeed())*nativeUnitsPerRev);
		f2 = (scale*timeConst)/(Math.abs(motor2.getSpeed())*nativeUnitsPerRev);
		
		return " F1: " + f1 + " F2: " + f2;
	}
}