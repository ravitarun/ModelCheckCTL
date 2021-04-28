package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import Ctrl.OutputHandler;



public class Project extends OutputHandler{
	private Path path;
	private String inputState;
	private String inputExpression;

	public void runProject() {
		
		try {
			KripkeStructure _kripke;
			
			String kripkeString = Files.readString(path);
			KripkeStructure kripke = new KripkeStructure(kripkeString, inputState);
	        _kripke = kripke;
	        
	        if(kripke.validState) {
	        	printOutput(_kripke.ToString());
	        State checkedState = new State(inputState);
	        CtlFormula ctlFormula = new CtlFormula(inputExpression, checkedState, _kripke);
            Boolean isSatisfy = ctlFormula.IsSatisfy();
	      
            if(isSatisfy)
	        printOutput( inputExpression + " for " + inputState + " is true");
            else
            	printOutput(inputExpression + " for " + inputState + " is false");
	        }
	        else {
	        	printOutput("Your input state is Invalid Please enter a Valid State.");
	        }
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Project(Path path, String state, String Expression) {
		this.path = path;
		inputState = state;
		inputExpression = Expression;
	}

}
