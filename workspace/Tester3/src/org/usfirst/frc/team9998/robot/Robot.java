
package org.usfirst.frc.team9998.robot;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
    Joystick controlStick = new Joystick(0);
    DoubleSolenoid s = new DoubleSolenoid(0,1);

    public void operatorControl() {
        boolean button1 = false;
        boolean button2 = false;
    	
    	while (isOperatorControl() && isEnabled()) {
        	button1 = controlStick.getRawButton(1);
        	button2 = controlStick.getRawButton(2);
            
        	if(button1 && button2) {
            	DriverStation.reportError("Both buttons pressed", false);
            } else if (button1) {
            	s.set(DoubleSolenoid.Value.kForward);
            } else if (button2) {
            	s.set(DoubleSolenoid.Value.kReverse);
            }
            
            Timer.delay(0.005);		// wait for a motor update time
        }
    }
}
