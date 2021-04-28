package Model;
    
public class Transition {
	
	public String TransitionName;
    
    public State FromState;
    
    public State ToState;
    
    ///  <summary>
    ///  Constructor
    ///  </summary>
    public Transition() {
        
    }
    
    ///  <summary>
    ///  Overloaded constructor
    ///  </summary>
    ///  <param name="fromState"></param>
    ///  <param name="toState"></param>
    public Transition(State fromState, State toState) {
        TransitionName = "";
        FromState = fromState;
        ToState = toState;
    }
    
    ///  <summary>
    ///  Overloaded constructor
    ///  </summary>
    ///  <param name="transitionName"></param>
    ///  <param name="fromState"></param>
    ///  <param name="toState"></param>
    public Transition(String transitionName, State fromState, State toState) {
        TransitionName = transitionName;
        FromState = fromState;
        ToState = toState;
    }
    
    
    ///  <summary>
    ///  Implement Equals method
    ///  </summary>
    ///  <param name="other"></param>
    ///  <returns></returns>
    public final boolean equals(Transition other) {
        if ((this.FromState.Equals(other.FromState) && this.ToState.Equals(other.ToState))) {
            return true;
        }
        
        return false;
    }
}
