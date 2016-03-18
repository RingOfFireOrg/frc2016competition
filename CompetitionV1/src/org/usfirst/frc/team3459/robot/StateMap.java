package org.usfirst.frc.team3459.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import edu.wpi.first.wpilibj.Joystick;
/**
 * @author Kyle Brown
 *
 * @param <S> The State Type
 */
public class StateMap<S> {
	private List<Entry<Integer,S>> entryList = new ArrayList<>();
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
	 * Adds a Button -> State mapping to the StateMap
	 * @param key The button number
	 * @param value The state
	 */
	protected synchronized void put(Integer key, S value) {
		entryList.add(new Pair<Integer,S>(key, value));
	}
	
	/**
	 * Causes the StateMap to update the StateMachine based on the current value
	 * of the Joystick
	 */
	public synchronized void update() {
		int buttonsPressed = 0;							//The number of buttons currently pressed
		Entry<Integer,S> activeButton = null;							//The ID of an actively pressed button
		
		for(Entry<Integer,S> entry : entryList) {				//For each button that is mapped
			if(joystick.getRawButton(entry.getKey())) {		//If it is pressed
				activeButton = entry;				//Give its value to activeButton
				buttonsPressed++;						//Add 1 to buttonsPressed
			}
		}
		
		if(buttonsPressed != 1) 							//If more than one button is pressed
			return;										//Stop the update
		
		stateMachine.setState(activeButton.getValue());	//Sets the StateMachine to have the value
															//corresponding to the only pressed button
	}
	
	class Pair<K,V> implements Entry<K,V> {
		K key;
		V value;
		
		public Pair(K key,V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}
	}
}