package Model;

import java.util.*;

public class KripkeStructure {
	
	public List<Transition> Transitions = new ArrayList<Transition>();
    
    public List<State> States = new ArrayList<State>();
    
    public List<String> Atoms = new ArrayList<String>();

    
    ///  <summary>
    ///  Overloaded constructor
    ///  </summary>
    ///  <param name="kripkeStructureDefinition"></param>
    public KripkeStructure(String kripkeStructureDefinition) {
        try {
            List<String> parsedStructure = Arrays.asList(kripkeStructureDefinition.replaceAll("\r\n", "").split(";"));
            if (((parsedStructure == null) 
                        || (parsedStructure.size() != 3))) {
                throw new Exception("Input file does not contain appropriate segments to construct kripke structure");
            }
            
            List<String> stateNames = Arrays.asList(parsedStructure.get(0).replace(" ", "").split(","));
            List<String> transitions = Arrays.asList(parsedStructure.get(1).replace(" ", "").split(","));
            List<String> stateAtomStructures = Arrays.asList(parsedStructure.get(2).split(","));
            // load states
            for (String stateName : stateNames) {
                State state = new State(stateName);
                States.add(state);
                
                
            }
            System.out.println(stateNames);
            
            // load transitions
            for (String transition : transitions) {
                List<String> parsedTransition = Arrays.asList(transition.split(":"));
                if (((parsedTransition == null) 
                            || (parsedTransition.size() != 2))) {
                    throw new Exception("Transition is not in the valid format");
                }
                
                String transitionName = parsedTransition.get(0);
                List<String> parsedFromToStates = Arrays.asList(parsedTransition.get(1).split("-"));
                if (((parsedFromToStates == null) 
                            || (parsedFromToStates.size() != 2))) {
                    throw new Exception("Transition {0} is not in [from state] - [to state] format"+transitionName);
                }
                
                String fromStateName = parsedFromToStates.get(0);
                String toStateName = parsedFromToStates.get(1);
                State fromState = this.FindStateByName(fromStateName);
                State toState = this.FindStateByName(toStateName);
                if (((fromState == null) 
                            || (toState == null))) {
                    throw new Exception("Invalid state is detected in transition {0}"+ transitionName);
                }
                
                Transition transitionObj = new Transition(transitionName, fromState, toState);
                if (!Transitions.contains(transitionObj)) {
                    Transitions.add(transitionObj);
                }
                else {
                    throw new Exception("Transitions from state {0} to state {1} are defined more than once"+fromStateName+toStateName);
                }
                
            }
            
            // load atoms
            for (String stateAtomStructure : stateAtomStructures) {
                List<String> parsedStateAtomStructure = Arrays.asList(stateAtomStructure.split(":"));
                if (((parsedStateAtomStructure == null) 
                            || (parsedStateAtomStructure.size() != 2))) {
                    throw new Exception("{0} is not a valid state: atoms definition"+stateAtomStructure);
                }
                
                String stateName = parsedStateAtomStructure.get(0).replace(" ", "");
                String atomNames = parsedStateAtomStructure.get(1).trim();
                List<String> parsedAtoms = Arrays.asList(atomNames.split(" "));
                List<String> stateAtoms = new ArrayList<String>();
                for (String atom : parsedAtoms) {
                    if (atom.length()==0) {
                        
                    }
                    else if (!stateAtoms.contains(atom)) {
                        stateAtoms.add(atom);
                    }
                    else {
                        throw new Exception("Atom {0} is defined more than once for state {1}"+atom+stateName);
                    }
                    
                }
                
                State stateObj = this.FindStateByName(stateName);
                if ((stateObj == null)) {
                    throw new Exception("State {0} is not defined"+stateName);
                }
                
                stateObj.Atoms = stateAtoms;
                // load to list of atoms
                for (String atom : stateAtoms) {
                    if (!Atoms.contains(atom)) {
                        Atoms.add(atom);
                    }
                    
                }
                
            }
            
        }

        catch (Exception ex) {
            System.err.println(ex);
        }
        
    }
    
    
    ///  <summary>
    ///  Find the state by its name
    ///  </summary>
    ///  <param name="stateName"></param>
    ///  <returns></returns>
    public final State FindStateByName(String stateName) {
        for (State state : this.States) {
            if (state.StateName.equals(stateName)) {
                return state;
            }
            
        }
        
        return null;
    }
    
    ///  <summary>
    ///  Override ToString method
    ///  </summary>
    ///  <returns></returns>
    public String ToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("STATES");
        sb.append("-----------");
        sb.append(this.StatesToString());
        sb.append("\n");
        sb.append("\n");
        sb.append("TRANSITIONS");
        sb.append("-------------------");
        sb.append(this.TransitionsToString());
        return sb.toString();
    }
    
    public final String StatesToString() {
        StringBuilder sb = new StringBuilder();
        List<String> stateStrings = new ArrayList<String>();
        System.out.println(stateStrings);
        for (State state : this.States) {
            String atomNames = state.Atoms.toString();
            stateStrings.add(state.StateName+"("+atomNames+")");
        }
        
        sb.append(stateStrings.toString());
        return sb.toString();
    }
    
    public final String TransitionsToString() {
        StringBuilder sb = new StringBuilder();
        List<String> transitionString = new ArrayList<String>();
        for (Transition transition : this.Transitions) {
            transitionString.add(transition.TransitionName+"("+transition.FromState.StateName+"->"+transition.ToState.StateName+")");
        }
        
        sb.append(transitionString.toString());
        return sb.toString();
    }
}

