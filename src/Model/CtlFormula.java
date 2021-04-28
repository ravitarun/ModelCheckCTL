package Model;

import java.util.*;
import java.util.Map.Entry;

public class CtlFormula {
    
    ///  <summary>
    ///  Enum determining Type of SAT
    ///  </summary>
	String rightExpression = "";
    String leftExpression = "";
    
    public enum TypeSAT {
        
        Unknown,
        
        AllTrue,
        
        AllFalse,
        
        Atomic,
        
        Not,
        
        And,
        
        Or,
        
        Implies,
        
        AX,
        
        EX,
        
        AU,
        
        EU,
        
        EF,
        
        EG,
        
        AF,
        
        AG,
    }
    
    public KripkeStructure _kripke;
    
    public State _state;
    
    public String _expression;
    
    public HashMap<String, String> _convertionString;
    
    ///  <summary>
    ///  Constructor
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <param name="state"></param>
    ///  <param name="kripke"></param>
    public CtlFormula(String expression, State state, KripkeStructure kripke) {
        this._convertionString = new HashMap<String, String>();
        this._convertionString.put("and", "&");
        this._convertionString.put("or", "|");
        this._convertionString.put("->", ">");
        this._convertionString.put("not", "!");
        this._kripke = kripke;
        this._state = state;
        this._expression = this.ConvertToSystemFormula(expression);
    }
    
    ///  <summary>
    ///  Replace input symbols with known system symbols
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <returns></returns>
    public final String ConvertToSystemFormula(String expression) {
        for (Entry<String, String> entry : this._convertionString.entrySet()) {
            expression = expression.replace(entry.getKey().toString(), entry.getValue().toString());
        }
        
        return expression;
    }
    
    ///  <summary>
    ///  Determine whether given state satisfy given CTL formula
    ///  </summary>
    ///  <returns></returns>
    public final boolean IsSatisfy() {
        List<State> states = this.SAT(this._expression);
        for(State state : states)
    	{
    		if (state.StateName.equals(this._state.StateName)) {
    			return true;
    	}                    
        
    }
        return false;
    }
    
    ///  <summary>
    ///  Determine Type of SAT for a given expression
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <param name="leftExpression"></param>
    ///  <param name="rightExpression"></param>
    ///  <returns></returns>
    private final TypeSAT GetTypeSAT(String expression, String leftExpression, String rightExpression) {
        // remove extra brackets
        expression = this.RemoveExtraBrackets(expression);
        // look for binary implies
        if (expression.contains(">")) {
            if (this.IsBinaryOp(expression, ">", leftExpression, rightExpression)) {
                return TypeSAT.Implies;
            }
            
        }
        
        // look for binary and
        if (expression.contains("&")) {
            if (this.IsBinaryOp(expression, "&", leftExpression, rightExpression)) {
                return TypeSAT.And;
            }
            
        }
        
        // look for binary or
        if (expression.contains("|")) {
            if (this.IsBinaryOp(expression, "|", leftExpression, rightExpression)) {
                return TypeSAT.Or;
            }
            
        }
        
        // look for binary AU
        if (expression.startsWith("A(")) {
            String strippedExpression = expression.substring(2, (expression.length() - 3));
            if (this.IsBinaryOp(strippedExpression, "U", leftExpression, rightExpression)) {
                return TypeSAT.AU;
            }
            
        }
        
        // look for binary EU
        if (expression.startsWith("E(")) {
            String strippedExpression = expression.substring(2, (expression.length() - 3));
            if (this.IsBinaryOp(strippedExpression, "U", leftExpression, rightExpression)) {
                return TypeSAT.EU;
            }
            
        }
        
        // look for unary T, F, !, AX, EX, AG, EG, AF, EF, atomic
        if (expression.equals("T")) {
            this.leftExpression = expression;
            return TypeSAT.AllTrue;
        }
        
        if (expression.equals("F")) {
            this.leftExpression = expression;
            return TypeSAT.AllFalse;
        }
        
        if (this.IsAtomic(expression)) {
            this.leftExpression = expression;
            return TypeSAT.Atomic;
        }
        
        if (expression.startsWith("!")) {
            this.leftExpression = expression.substring(1,expression.length());
            return TypeSAT.Not;
        }
        
        if (expression.startsWith("AX")) {
            this.leftExpression = expression.substring(2,expression.length());
            return TypeSAT.AX;
        }
        
        if (expression.startsWith("EX")) {
            this.leftExpression = expression.substring(2,expression.length());
            System.out.println("exp"+leftExpression);
            return TypeSAT.EX;
        }
        
        if (expression.startsWith("EF")) {
            this.leftExpression = expression.substring(2,expression.length());
            return TypeSAT.EF;
        }
        
        if (expression.startsWith("EG")) {
            this.leftExpression = expression.substring(2,expression.length());
            return TypeSAT.EG;
        }
        
        if (expression.startsWith("AF")) {
            this.leftExpression = expression.substring(2,expression.length());
            return TypeSAT.AF;
        }
        
        if (expression.startsWith("AG")) {
            this.leftExpression = expression.substring(2,expression.length());
            return TypeSAT.AG;
        }
        
        return TypeSAT.Unknown;
    }
    
    ///  <summary>
    ///  Determine states that satisfy given expression
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <returns></returns>
    private final List<State> SAT(String expression) {
    	System.out.println("Original"+expression);
        //System.Diagnostics.Debug.WriteLine(String.Format("Original Expression: {0}", expression));
        List<State> states = new ArrayList<State>();
        // from Logic in Computer Science, page 227
        
        // TypeSAT typeSAT = DetermineTypeSAT(expression, ref leftExpression, ref rightExpression);
        TypeSAT typeSAT = this.GetTypeSAT(expression, leftExpression, rightExpression);
        System.out.println("SAT"+typeSAT.toString()+"-"+leftExpression+"-"+rightExpression);
        System.out.println("SAT"+typeSAT.toString()+"-"+this.leftExpression+"-"+rightExpression);
        //System.Diagnostics.Debug.WriteLine(String.Format("Type SAT: {0}", typeSAT.ToString()));
        //System.Diagnostics.Debug.WriteLine(String.Format("Left Expression: {0}", leftExpression));
        //System.Diagnostics.Debug.WriteLine(String.Format("Right Expression: {0}", rightExpression));
        //System.Diagnostics.Debug.WriteLine("------------------------------------");
        switch (typeSAT) {
            case AllTrue:
                // all states
            	for(State state:this._kripke.States)
            	{
            		states.add(state);
            	}
                break;
            case AllFalse:
                // empty 
                break;
            case Atomic:
                for (State state : this._kripke.States) {
                    if (state.Atoms.contains(leftExpression)) {
                        states.add(state);
                    }
                    
                }
                
                break;
            case Not:
                // S  SAT (�1)
            	for(State state:this._kripke.States)
            	{
            		states.add(state);
            	}
                List<State> f1States = this.SAT(leftExpression);
                for (State state : f1States) {
                    if (states.contains(state)) {
                        states.remove(state);
                    }
                    
                }
                
                break;
            case And:
                // SAT (�1) ) SAT (�2)
                List<State> andF1States = this.SAT(leftExpression);
                List<State> andF2States = this.SAT(rightExpression);
                for (State state : andF1States) {
                    if (andF2States.contains(state)) {
                        states.add(state);
                    }
                    
                }
                
                break;
            case Or:
                // SAT (�1) * SAT (�2)
                List<State> orF1States = this.SAT(leftExpression);
                List<State> orF2States = this.SAT(rightExpression);
                states = orF1States;
                for (State state : orF2States) {
                    if (!states.contains(state)) {
                        states.add(state);
                    }
                    
                }
                
                break;
            case Implies:
                // SAT (��1 ( �2)
                // TODO: reevaluate impliesFormula
                String impliesFormula = "!"+leftExpression+"|"+rightExpression;
                states = this.SAT(impliesFormula);
                break;
            case AX:
                // SAT (�EX��1)
                // TODO: reevaluate axFormula
                String axFormula = "!"+"EX"+"!"+leftExpression;
                states = this.SAT(axFormula);
                // check if states actually has link to next state
                List<State> tempStates = new ArrayList<State>();
                for (State sourceState : states) {
                    for (Transition transition : this._kripke.Transitions) {
                        if (sourceState.Equals(transition.FromState)) {
                            tempStates.add(sourceState);
                            break;
                        }
                        
                    }
                    
                }
                
                states = tempStates;
                break;
            case EX:
                // SATEX(�1)
                // TODO: reevaluate exFormula
                String exFormula = leftExpression;
                states = this.SAT_EX(exFormula);
                break;
            case AU:
                // A[�1 U �2]
                // SAT(�(E[��2 U (��1 '��2)] ( EG��2))
                // TODO: reevaluate auFormulaBuilder
                StringBuilder auFormulaBuilder = new StringBuilder();
                auFormulaBuilder.append("!(E(!");
                auFormulaBuilder.append(rightExpression);
                auFormulaBuilder.append("U(!");
                auFormulaBuilder.append(leftExpression);
                auFormulaBuilder.append("&!");
                auFormulaBuilder.append(rightExpression);
                auFormulaBuilder.append("))|(EG!");
                auFormulaBuilder.append(rightExpression);
                auFormulaBuilder.append("))");
                states = this.SAT(auFormulaBuilder.toString());
                break;
            case EU:
                // SATEU(�1, �2)
                // TODO: reevaluate euFormula
                states = this.SAT_EU(leftExpression, rightExpression);
                break;
            case EF:
                // SAT (E( U �1))
                // TODO: reevaluate efFormula
                String efFormula = "E(TU"+leftExpression+")";
                states = this.SAT(efFormula);
                break;
            case EG:
                // SAT(�AF��1)
                // TODO: reevaulate egFormula
                String egFormula = "!AF!"+leftExpression;
                states = this.SAT(egFormula);
                break;
            case AF:
                // SATAF (�1)
                // TODO: reevaluate afFormula
                String afFormula = leftExpression;
                states = this.SAT_AF(afFormula);
                break;
            case AG:
                // SAT (�EF��1)
                // TODO: reevaluate agFormula
                String agFormula = "!EF!"+leftExpression;
                states = this.SAT(agFormula);
                break;
            case Unknown:
                System.err.println("Invalid CTL expression");
                break;
        }
        return states;
    }
    
    ///  <summary>
    ///  Handling EX
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <returns></returns>
    private final List<State> SAT_EX(String expression) {
        // X := SAT (�);
        // Y := pre(X);
        // return Y
        List<State> x = new ArrayList<State>();
        List<State> y = new ArrayList<State>();
        x = this.SAT(expression);
        y = this.PreE(x);
        return y;
    }
    
    ///  <summary>
    ///  Handling EU
    ///  </summary>
    ///  <param name="leftExpression"></param>
    ///  <param name="rightExpression"></param>
    ///  <returns></returns>
    private final List<State> SAT_EU(String leftExpression, String rightExpression) {
        List<State> w = new ArrayList<State>();
        List<State> x = new ArrayList<State>();
        List<State> y = new ArrayList<State>();
        w = this.SAT(leftExpression);
        for(State state:this._kripke.States)
        {
        	x.add(state);
        }
        y = this.SAT(rightExpression);
        while (!this.AreListStatesEqual(x, y)) {
            x = y;
            List<State> newY = new ArrayList<State>();
            List<State> preEStates = this.PreE(y);
            for(State state:y)
            {
            	newY.add(state);
            }
            List<State> wAndPreE = new ArrayList<State>();
            for (State state : w) {
                if (preEStates.contains(state)) {
                    wAndPreE.add(state);
                }
                
            }
            
            for (State state : wAndPreE) {
                if (!newY.contains(state)) {
                    newY.add(state);
                }
                
            }
            
            y = newY;
        }
        
        return y;
    }
    
    ///  <summary>
    ///  Handling AF
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <returns></returns>
    private final List<State> SAT_AF(String expression) {
        List<State> x = new ArrayList<State>();
        for(State state:this._kripke.States)
        {
        	x.add(state);
        }
        List<State> y = new ArrayList<State>();
        y = this.SAT(expression);
        while (!this.AreListStatesEqual(x, y)) {
            x = y;
            List<State> newY = new ArrayList<State>();
            List<State> preAStates = this.PreA(y);
            for(State state:y)
            {
            	newY.add(state);
            }
            for (State state : preAStates) {
                if (!newY.contains(state)) {
                    newY.add(state);
                }
                
            }
            
            y = newY;
        }
        
        return y;
    }
    
    ///  <summary>
    ///  PreE
    ///  </summary>
    ///  <param name="y"></param>
    ///  <returns></returns>
    private final List<State> PreE(List<State> y) {
        // {s  S | exists s, (s � s and s  Y )}
        List<State> states = new ArrayList<State>();
        //List<Transition> transitions = new ArrayList<Transition>();
        for (State sourceState : this._kripke.States) {
            for (State destState : y) {
                Transition myTransition = new Transition(sourceState, destState);
                	for(Transition transition : this._kripke.Transitions)
                	{
                		if (transition.FromState.equals(myTransition.FromState) &&
                				transition.ToState.equals(myTransition.ToState)) {
                			if (!states.contains(sourceState)) {
                                states.add(sourceState);
                            }
                	}                    
                    
                }
                
            }
            
        }
        
        return states;
    }
    
    ///  <summary>
    ///  PreA
    ///  </summary>
    ///  <param name="y"></param>
    ///  <returns></returns>
    private final List<State> PreA(List<State> y) {
        // pre(Y ) = prey  pre(S  Y)
        List<State> PreEY = this.PreE(y);
        List<State> S_Minus_Y = new ArrayList<State>();
        for(State state:this._kripke.States)
        {
        	S_Minus_Y.add(state);
        }
        for (State state : y) {
            if (S_Minus_Y.contains(state)) {
                S_Minus_Y.remove(state);
            }
            
        }
        
        List<State> PreE_S_Minus_Y = this.PreE(S_Minus_Y);
        // PreEY - PreE(S-Y)
        for (State state : PreE_S_Minus_Y) {
            if (PreEY.contains(state)) {
                PreEY.remove(state);
            }
            
        }
        
        return PreEY;
    }
    
    ///  <summary>
    ///  Determine whether the list contain same set of states
    ///  </summary>
    ///  <param name="list1"></param>
    ///  <param name="list2"></param>
    ///  <returns></returns>
    private final boolean AreListStatesEqual(List<State> list1, List<State> list2) {
        if ((list1.size() != list2.size())) {
            return false;
        }
        
        for (State state : list1) {
            if (!list2.contains(state)) {
                return false;
            }
            
        }
        
        return true;
    }
    
    ///  <summary>
    ///  Determine whether this is an atom
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <returns></returns>
    private final boolean IsAtomic(String expression) {
        if (this._kripke.Atoms.contains(expression)) {
            return true;
        }
        
        return false;
    }
    
    ///  <summary>
    ///  Determine whether given expression contains binary operation for the next checking
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <param name="symbol"></param>
    ///  <param name="leftExpression"></param>
    ///  <param name="rightExpression"></param>
    ///  <returns></returns>
    private final boolean IsBinaryOp(String expression, String symbol, String leftExpression, String rightExpression) {
        boolean isBinaryOp = false;
        if (expression.contains(symbol)) {
            int openParanthesisCount = 0;
            int closeParanthesisCount = 0;
            for (int i = 0; (i < expression.length()); i++) {
                String currentChar = expression.substring(i, 1);
                if ((currentChar.equals(symbol) 
                            && (openParanthesisCount == closeParanthesisCount))) {
                    leftExpression = expression.substring(0, i);
                    rightExpression = expression.substring((i + 1), (expression.length() - (i - 1)));
                    isBinaryOp = true;
                    break;
                }
                else if (currentChar.equals("(")) {
                    openParanthesisCount++;
                }
                else if (currentChar.equals(")")) {
                    closeParanthesisCount++;
                }
                
            }
            
        }
        
        return isBinaryOp;
    }
    
    ///  <summary>
    ///  Removing extra brackets
    ///  </summary>
    ///  <param name="expression"></param>
    ///  <returns></returns>
    private final String RemoveExtraBrackets(String expression) {
        String newExpression = expression;
        int openParanthesis = 0;
        int closeParanthesis = 0;
        if ((expression.startsWith("(") && expression.endsWith(")"))) {
            for (int i = 0; (i 
                        < (expression.length() - 1)); i++) {
                String charExpression = expression.substring(i, 1);
                if (charExpression.equals("(")) {
                    openParanthesis++;
                }
                else if (charExpression.equals(")")) {
                    closeParanthesis++;
                }
                
            }
            
            if (((openParanthesis - 1) 
                        == closeParanthesis)) {
                newExpression = expression.substring(1, (expression.length() - 2));
            }
            
        }
        
        return newExpression;
    }
}
