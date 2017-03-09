import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

import java.util.Set;
public class State {

	private int stateStats;

	private Map<Character, ArrayList<State>> nextState;

	private Set <State> states;

	private boolean acceptState;

public State (int Stats) {

		this.setStateStats(Stats);

		this.setNextState(new HashMap <Character, ArrayList<State>> ());

		this.setAcceptState(false);

	}
	public void addTransition (State next, char key) {

		ArrayList <State> list = this.nextState.get(key);

		

		if (list == null) {

			list = new ArrayList<State> ();

			this.nextState.put(key, list);

		}

		

		list.add(next);

	}


	public ArrayList<State> getAllTransitions(char c) {	

		if (this.nextState.get(c) == null)	{	
return new ArrayList<State> ();	}

		else 								{	
return this.nextState.get(c);	}

	}

	



	public Map<Character, ArrayList<State>> getNextState() {

		return nextState;

	}




	public void setNextState(HashMap<Character, ArrayList<State>> hashMap) {

		this.nextState = hashMap;

	}

	

	public int getStateStats() {

		return stateStats;

	}




	public void setStateStats(int stateStats) {

		this.stateStats = stateStats;

	}




	public boolean isAcceptState() {

		return acceptState;

	}




	public void setAcceptState(boolean acceptState) {

		this.acceptState = acceptState;

	}




	public Set <State> getStates() {

		return states;

	}




	public void setStates(Set <State> states) {

		this.states = states;

	}

}


