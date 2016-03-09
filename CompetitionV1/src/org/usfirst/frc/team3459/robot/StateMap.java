package org.usfirst.frc.team3459.robot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.wpi.first.wpilibj.Joystick;
/**
 * @author Kyle Brown
 *
 * @param <S> The State Type
 */
public class StateMap<S> {
	/**
	 * The button number to State Mapping
	 */
	private Map<Integer,S> buttonMap = new HashMap<>();
	/**
	 * The lock that dictates whether the mapping can be effected
	 */
	private boolean lock = false;
	/**
	 * The keySet generated from the mapping when the StateMap is locked
	 */
	private Set<Integer> keySet;
	/**
	 * The StateMachine<T> being controlled by the StateMaps
	 */
	private StateMachine<S> stateMachine;
	/**
	 * The Joystick on which the buttons are located
	 */
	private Joystick joystick;
	
	/**
	 * Constructs a StateMap with the corresponding StateMachine and Joystick
	 * @param stateMachine
	 * @param joystick
	 */
	public StateMap(StateMachine<S> stateMachine, Joystick joystick) {
		this.stateMachine = stateMachine;
		this.joystick = joystick;
	}
	
	/**
	 * generates a keySet from the mapping, sets the lock to true
	 */
	public void lock() {
		keySet = buttonMap.keySet();
		lock = true;
	}
	
	/**
	 * Adds a Button -> State mapping to the StateMap
	 * @param key The button number
	 * @param value The state
	 */
	protected void put(Integer key, S value) {
		if(lock)
			return;
		
		buttonMap.put(key, value);
	}
	
	/**
	 * Causes the StateMap to update the StateMachine based on the current value
	 * of the Joystick
	 */
	public void update() {
		if(!lock)
			return;
		
		int buttonsPressed = 0;							//The number of buttons currently pressed
		int activeButton = 0;							//The ID of an actively pressed button
		
		for(Integer buttonNum : keySet) {				//For each button that is mapped
			if(joystick.getRawButton(buttonNum)) {		//If it is pressed
				activeButton = buttonNum;				//Give its value to activeButton
				buttonsPressed++;						//Add 1 to buttonsPressed
			}
		}
		
		if(buttonsPressed > 1) 							//If more than one button is pressed
			return;										//Stop the update
		
		stateMachine.setState(buttonMap.get(activeButton));	//Sets the StateMachine to have the value
															//corresponding to the only pressed button
	}
}