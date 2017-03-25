import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
public class RegularExpression {
	private static int ID = 0;
	private static Stack<NFA> NFA_stack = new Stack<NFA> ();
	private static Stack<Character> opt = new Stack<Character> ();	
	private static Set<State> set1 = new HashSet <State> ();
	private static Set<State> set2 = new HashSet <State> ();
	private static Set <Character> inp = new HashSet <Character> ();
	// Generating NFA
	public static NFA constructNFA(String str) {
		str = AddConcat (str);
		// Only inputs available
		inp.add('a');
		inp.add('b');
		NFA_stack.clear();
		opt.clear();
		for (int i = 0 ; i < str.length(); i++) {	
			if (checkChar (str.charAt(i))) {
				pushStack(str.charAt(i));
			} else if (opt.isEmpty()) {
				opt.push(str.charAt(i));
			} else if (str.charAt(i) == '(') {
				opt.push(str.charAt(i));
			} else if (str.charAt(i) == ')') {
				while (opt.get(opt.size()-1) != '(') {
					process();
				}				
				opt.pop();
			} else {
				while (!opt.isEmpty() && Priority (str.charAt(i), opt.get(opt.size() - 1)) ){
					process ();
				}
				opt.push(str.charAt(i));
			}		
		}		
		while (!opt.isEmpty()) {
			process();
 }
		NFA resultant_NFA = NFA_stack.pop();
		resultant_NFA.getNfa().get(resultant_NFA.getNfa().size() - 1).setAcceptState(true);
		return resultant_NFA;
	}
	// Operands’ Priority
	private static boolean Priority (char first, Character second) {
		if(first == second) {return true;}
		if(first == '*') 	{return false;}
		if(second == '*')  	{return true;}
		if(first == '.') 	{return false;}
		if(second == '.') 	{return true;}
		if(first == '|') 	{return false;} 
		else 		{return true;}
	}
	private static void process () {
		if (RegularExpression.opt.size() > 0) {
			char charAt = opt.pop();
			switch (charAt) {
				case ('|'):
					union ();
					break;
				case ('.'):
					concat ();
					break;
				case ('*'):
					star ();
					break;
				default :
					System.out.println("Symbol Not Known");
					System.exit(1);
					break;			
			}
		}
	}
	private static void star() {
		NFA nfa = NFA_stack.pop();
		State start = new State (ID++);
		State end	= new State (ID++);
		start.addTransition(end, 'e');
		start.addTransition(nfa.getNfa().getFirst(), 'e');
		nfa.getNfa().getLast().addTransition(end, 'e');
		nfa.getNfa().getLast().addTransition(nfa.getNfa().getFirst(), 'e');
		nfa.getNfa().addFirst(start);
		nfa.getNfa().addLast(end);
		NFA_stack.push(nfa);
	}
	private static void concat() {
		NFA nfa2 = NFA_stack.pop();
		NFA nfa1 = NFA_stack.pop();
		nfa1.getNfa().getLast().addTransition(nfa2.getNfa().getFirst(), 'e');
		for (State s : nfa2.getNfa()) {	
nfa1.getNfa().addLast(s); 
}
		NFA_stack.push (nfa1);
	}
	private static void union() {
		NFA nfa2 = NFA_stack.pop();
		NFA nfa1 = NFA_stack.pop();
		State start = new State (ID++);
		State end	= new State (ID++);
		start.addTransition(nfa1.getNfa().getFirst(), 'e');
		start.addTransition(nfa2.getNfa().getFirst(), 'e');
		nfa1.getNfa().getLast().addTransition(end, 'e');
		nfa2.getNfa().getLast().addTransition(end, 'e');
		nfa1.getNfa().addFirst(start);
		nfa2.getNfa().addLast(end);
		for (State s : nfa2.getNfa()) {
			nfa1.getNfa().addLast(s);
		}
		NFA_stack.push(nfa1);		
	}
	private static void pushStack(char sym) {
		State s0 = new State (ID++);
		State s1 = new State (ID++);
		s0.addTransition(s1, sym);
		NFA nfa = new NFA ();
		nfa.getNfa().addLast(s0);
		nfa.getNfa().addLast(s1);
		NFA_stack.push(nfa);
	}
	private static String AddConcat(String str) {
		String newExp = new String ("");
		for (int i = 0; i < str.length() - 1; i++) {
			if ( checkChar(str.charAt(i))  && checkChar(str.charAt(i+1)) ) {
				newExp += str.charAt(i) + ".";
			} else if ( checkChar(str.charAt(i)) && str.charAt(i+1) == '(' ) {
				newExp += str.charAt(i) + ".";
			} else if ( str.charAt(i) == ')' && checkChar(str.charAt(i+1)) ) {
				newExp += str.charAt(i) + ".";
			} else if (str.charAt(i) == '*'  && checkChar(str.charAt(i+1)) ) {
				newExp += str.charAt(i) + ".";
			} else if ( str.charAt(i) == '*' && str.charAt(i+1) == '(' ) {
				newExp += str.charAt(i) + ".";
			} else if ( str.charAt(i) == ')' && str.charAt(i+1) == '(') {
				newExp += str.charAt(i) + ".";			
			} else {
				newExp += str.charAt(i);
			}
		}
		newExp += str.charAt(str.length() - 1);
		return newExp;
	}
	// Check if input character belongs to automata language
	private static boolean checkChar(char charAt) {
		if (charAt == 'a')	return true;
		else if (charAt == 'b')	return true;
		else if (charAt == 'e')	return true;
		else return false;
	}
	// Generating DFA by using NFA
	public static DFA constructDFA(NFA nfa) {
		DFA dfa = new DFA ();
		ID = 0;
		LinkedList <State> unprocessedStates = new LinkedList<State> ();
		set1 = new HashSet <State> ();
		set2 = new HashSet <State> ();
		set1.add(nfa.getNfa().getFirst());
		removeEpsilon ();
		// add new start state of DFA to stack
		State dfaStart = new State (set2, ID++);
		dfa.getDfa().addLast(dfaStart);
		unprocessedStates.addLast(dfaStart);
		while (!unprocessedStates.isEmpty()) {
			State state = unprocessedStates.removeLast();
			for (Character sym : inp) {
				set1 = new HashSet<State> ();
				set2 = new HashSet<State> ();
				moveStates (sym, state.getStates(), set1);
				removeEpsilon ();
				boolean ifThere = false;
				State temp = null;
				for (int i = 0 ; i < dfa.getDfa().size(); i++) {
					temp = dfa.getDfa().get(i);
					if (temp.getStates().containsAll(set2)) {
						ifThere = true;
						break;
					}
				}
				if (!ifThere) {
					State t = new State (set2, ID++);
					unprocessedStates.addLast(t);
					dfa.getDfa().addLast(t);
					state.addTransition(t, sym);
				} else {
					state.addTransition(temp, sym);
				}
			}			
		}
		return dfa;
	}
	private static void removeEpsilon() {
		Stack <State> stack = new Stack <State> ();
		set2 = set1;
		for (State temp : set1) { 
stack.push(temp);	
}
		while (!stack.isEmpty()) {
			State temp = stack.pop();
			ArrayList <State> epsilonStates = temp.getAllTransitions ('e');
			for (State t : epsilonStates) {
				if (!set2.contains(t)) {
					set2.add(t);
					stack.push(t);
				}				
			}
		}		
	}
	private static void moveStates(Character c, Set<State> states,	Set<State> set) {
		ArrayList <State> temp = new ArrayList<State> ();
		for (State st : states) {	temp.add(st);	}
		for (State st : temp) {			
			ArrayList<State> allStates = st.getAllTransitions(c);
			for (State t : allStates) {	
set.add(t);	}
				}
	}
}
