
package org.usfirst.frc.team9998.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
    Joystick controlStick = new Joystick(0);
    Solenoid s0 = new Solenoid(0);
    Solenoid s1 = new Solenoid(1);

    public void operatorControl() {
        boolean button1 = false;
        boolean button2 = false;
    	
    	while (isOperatorControl() && isEnabled()) {
        	button1 = controlStick.getRawButton(1);
        	button2 = controlStick.getRawButton(2);
            
        	if(button1 && button2) {
            	DriverStation.reportError("Both buttons pressed", false);
            } else if (button1) {
            	s0.set(true);
            	s1.set(false);
            } else if (button2) {
            	s0.set(false);
            	s1.set(true);
            }
            
            Timer.delay(0.005);		// wait for a motor update time
        }
    }
}
