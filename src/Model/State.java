package Model;

import java.util.*;
    
public class State {
	
	public String StateName;
    
    public List<String> Atoms;
    
    ///  <summary>
    ///  Constructor
    ///  </summary>

    ///  <summary>
    ///  Overloaded constructor
    ///  </summary>
    ///  <param name="stateName"></param>
    public State(String stateName) {
        StateName = stateName;
        Atoms = new ArrayList<String>();
    }
    
    
    
    ///  <summary>
    ///  Implement Equals method
    ///  </summary>
    ///  <param name="other"></param>
    ///  <returns></returns>
    public final boolean Equals(State other) {
        return this.StateName.equals(other.StateName);
    }
}
