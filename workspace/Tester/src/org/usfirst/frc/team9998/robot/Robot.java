
package org.usfirst.frc.team9998.robot;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class Robot extends SampleRobot {
    Joystick controlStick;
    JoystickButton downButton;
    JoystickButton upButton;

    boolean usingDouble = false;
    JoystickButton doubleButton;
    JoystickButton singleButton;
    DoubleSolenoid doubleSolenoid;
    Solenoid singleSolenoid1;
    Solenoid singleSolenoid2;
    
    public Robot() {
        controlStick = new Joystick(0);
        doubleButton = new JoystickButton(controlStick,1);
        singleButton = new JoystickButton(controlStick,2);
        downButton = new JoystickButton(controlStick,3);
        upButton = new JoystickButton(controlStick,4);
        
        singleSolenoid1 = new Solenoid(0);
        singleSolenoid2 = new Solenoid(1);
    }
    
    public void robotInit() {}
    public void autonomous() {}

    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
            if(singleButton.get() && doubleButton.get()) {
            	DriverStation.reportError("Double Button Press", false);
            } else if (singleButton.get()) {
            	if(usingDouble) {
            		usingDouble = false;
            		doubleSolenoid.free();
            		doubleSolenoid = null;
            		singleSolenoid1 = new Solenoid(0);
                    singleSolenoid2 = new Solenoid(1);
            	}
            } else if (doubleButton.get()) {
            	if(!usingDouble) {
            		usingDouble = true;
            		singleSolenoid1.free();
            		singleSolenoid1 = null;
            		singleSolenoid2.free();
            		singleSolenoid2 = null;
                    singleSolenoid2 = new Solenoid(0,1);
            	}
            }
            
            
            if(downButton.get() && upButton.get()) {
            	DriverStation.reportError("Double Button Press", false);
            } else if (downButton.get()) {
            	if(usingDouble) {
            		if(doubleSolenoid != null) {
            			doubleSolenoid.set(DoubleSolenoid.Value.kForward);
            		} else {
            			DriverStation.reportError("The DoubleSolenoid is not available", false);
            		}
            	} else {
            		if(singleSolenoid1 != null || singleSolenoid2 != null) {
                		singleSolenoid1.set(false);
                		singleSolenoid2.set(true);	
            		} else {
            			DriverStation.reportError("The Solenoids are not available", false);
            		}
            	}
            } else if (upButton.get()) {
            	if(usingDouble) {
            		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
            	} else {
            		singleSolenoid1.set(true);
            		singleSolenoid2.set(false);
            	}
            }
            
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    public void test() {}
}
