
package regularExpression;
//importing all the packages that are needed
import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
public class RegularExpression {
	private static int stateStats = 0;
	private static Stack<NFA> NFA_stack= new Stack<NFA> ();
	private static Stack<Character> opt = new Stack<Character> ();	
	private static Set<State> set1 = new HashSet <State> ();
	private static Set<State> set2 = new HashSet <State> ();
	private static Set <Character> inp = new HashSet <Character> ();
	
	// Function to generate NFA
	public static NFA constructNFA(String str) {
		str = AddConcat (str);
		inp.add('a');
		inp.add('b');
		NFA_stack.clear();
		opt.clear();

		for (int i = 0 ; i < str.length(); i++) {	
			if (checkChar (str.charAt(i))) {
				push(str.charAt(i));
				
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
				while (!opt.isEmpty() && importance (str.charAt(i), opt.get(opt.size() - 1)) ){
					process ();
				}
				opt.push(str.charAt(i));
			}		
		}		
		while (!opt.isEmpty()) {	process(); }
		NFA resultant_NFA = NFA_stack.pop();
		resultant_NFA.getNfa().get(resultant_NFA.getNfa().size() - 1).setAcceptState(true);
		return resultant_NFA;
	}
	private static boolean importance (char first, Character second) {
		if(first == second) {	return true;	}
		if(first == '*') 	{	return false;	}
		if(second == '*')  	{	return true;	}
		if(first == '.') 	{	return false;	}
		if(second == '.') 	{	return true;	}
		if(first == '|') 	{	return false;	} 
		else 				{	return true;	}
	}
	private static void process () {
		if (strExpression.opt.size() > 0) {
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
					System.out.println("Unkown Symbol !");
					System.exit(1);
					break;			
			}
		}
	}
	private static void star() {
		NFA nfa = NFA_stack.pop();
		State start = new State (stateStats++);
		State end	= new State (stateStats++);
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
		State start = new State (stateStats++);
		State end	= new State (stateStats++);
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
	private static void push(char sym) {
		State s0 = new State (stateStats++);
		State s1 = new State (stateStats++);
		s0.addTransition(s1, sym);
		NFA nfa = new NFA ();
		nfa.getNfa().addLast(s0);
		nfa.getNfa().addLast(s1);		
		NFA_stack.push(nfa);
	}
	private static String AddConcat(String str) {
		String newstr = new String ("");
		for (int i = 0; i < str.length() - 1; i++) {
			if ( checkChar(str.charAt(i))  && checkChar(str.charAt(i+1)) ) {
				newstr += str.charAt(i) + ".";
				
			} else if ( checkChar(str.charAt(i)) && str.charAt(i+1) == '(' ) {
				newstr += str.charAt(i) + ".";
				
			} else if ( str.charAt(i) == ')' && checkChar(str.charAt(i+1)) ) {
				newstr += str.charAt(i) + ".";
				
			} else if (str.charAt(i) == '*'  && checkChar(str.charAt(i+1)) ) {
				newstr += str.charAt(i) + ".";
				
			} else if ( str.charAt(i) == '*' && str.charAt(i+1) == '(' ) {
				newstr += str.charAt(i) + ".";
				
			} else if ( str.charAt(i) == ')' && str.charAt(i+1) == '(') {
				newstr += str.charAt(i) + ".";			
				
			} else {
				newstr += str.charAt(i);
			}
		}
		newstr += str.charAt(str.length() - 1);
		return newstr;
	}

	// function to check if inp characters are part of automata language or not
	private static boolean checkChar(char charAt) {
		if 		(charAt == 'a')	return true;
		else if (charAt == 'b')	return true;
		else if (charAt == 'e')	return true;
		else					return false;
	}

	}

