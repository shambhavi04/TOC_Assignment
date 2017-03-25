import java.util.ArrayList;
import java.util.Scanner;
public class Reader {
	private static Scanner a;
	private static String r;
	private static ArrayList<String> StringExpressions = new ArrayList<String>();  	// Reads all the expressions in arrayList
	private static NFA nfa;
	private static DFA dfa;
	public static void main(String[] args) {
		a = new Scanner (System.in);
		r = a.next();
		while(a.hasNext()) {	
StringExpressions.add (a.next());
				}
		setNfa (RegularExpression.constructNFA (r));		
		setDfa (RegularExpression.constructDFA (getNfa()));
		// If the string is valid then print yes, otherwise no
		for (String s : StringExpressions) {
			if (ValidExpression.ifValid(getDfa(), s)) {
				System.out.println ("yes");	
}
			Else{
System.out.println ("no");	
}
		}
	}
	public static NFA getNfa() {
		return nfa;
	}
	public static void setNfa(NFA nfa) {
		Reader.nfa = nfa;
	}
	public static DFA getDfa() {
		return dfa;
	}
	public static void setDfa(DFA dfa) {
		Reader.dfa = dfa;
	}
}
