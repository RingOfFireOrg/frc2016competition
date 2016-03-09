
package org.usfirst.frc.team9999.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	  
	CANTalon _talon = new CANTalon(0);	
	Joystick _joy = new Joystick(0);	
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	
	public void robotInit() {
        /* first choose the sensor */
        _talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        _talon.reverseSensor(false);
        _talon.configEncoderCodesPerRev(20); // if using FeedbackDevice.QuadEncoder
        //_talon.configPotentiometerTurns(XXX), // if using FeedbackDevice.AnalogEncoder or AnalogPot

        /* set the peak and nominal outputs, 12V means full */
        _talon.configNominalOutputVoltage(+0.0f, -0.0f);
        _talon.configPeakOutputVoltage(+12.0f, 0.0f);
        /* set closed loop gains in slot0 */
        _talon.setProfile(0);
        _talon.setF(0.1097);
        _talon.setP(0.22);
        _talon.setI(0); 
        _talon.setD(0);
	}
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	/* get gamepad axis */
    	double leftYstick = _joy.getAxis(AxisType.kY);
    	double motorOutput = _talon.getOutputVoltage() / _talon.getBusVoltage();
    	/* prepare line to print */
		_sb.append("\tout:");
		_sb.append(motorOutput);
        _sb.append("\tspd:");
        _sb.append(_talon.getSpeed() );
        
        if(_joy.getRawButton(1)){
        	/* Speed mode */
        	double targetSpeed = leftYstick * 1500.0; /* 1500 RPM in either direction */
        	_talon.changeControlMode(TalonControlMode.Speed);
        	_talon.set(targetSpeed); /* 1500 RPM in either direction */

        	/* append more signals to print when in speed mode. */
            _sb.append("\terr:");
            _sb.append(_talon.getClosedLoopError());
            _sb.append("\ttrg:");
            _sb.append(targetSpeed);
        } else {
        	/* Percent voltage mode */
        	_talon.changeControlMode(TalonControlMode.PercentVbus);
        	_talon.set(leftYstick);
        }

        if(++_loops >= 10) {
        	_loops = 0;
        	System.out.println(_sb.toString());
        }
        _sb.setLength(0);
    }
}