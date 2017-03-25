public class ValidExpression {
	public static boolean ifValid(DFA dfa, String str) {
		State s = dfa.getDfa().getFirst();
		// if string is empty, then it is valid only if first state is accept state
		if (str.compareTo("e") == 0) {
			if (s.isAcceptState()) {	
return true;
 	}
			else{
return false; 	
}
					} 
else if (dfa.getDfa().size() > 0) {	
			for (int i = 0 ; i < str.length(); i++) {
				// If no transition then break the DFA as its invalid string
				if (s == null) { 
break;
 }
				s = s.getNextState().get(str.charAt(i)).get(0);
						}
			if (s != null && s.isAcceptState()) {
				return true;	
} 
			else{
				return false;	
}
		} else {
			return false;
		}
	}
}
