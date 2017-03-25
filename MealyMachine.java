import java.util.Scanner;



public class MealyMachine {


	
	public static void main(String args[])
	{
		String in;	// Input
		int ps = 0; 	//Present state
		int out_state=0; // state
		String status; 	//Chocolate released or not
		
		System.out.println("Mealy machine : Vending Machine");
		System.out.println("You are in state "+ ps);
		System.out.println("Rules 1): Chocolate is released on giving more than or equal to 10 cents.");
		System.out.println("Rules 2): Nickel and dime cannot be entered in the same transaction");
		System.out.println("Rules 3): State for 0 cents is 0 and 5 cents is 5");
		System.out.println("Rules 4): Enter N for Nickel, D for Dime");
		System.out.println("Rules 5): Machine will reset after releasing the chocolate");
		show_state_transition_table();
		while(true)
		{
			status = "Machine reset";
			
			System.out.println("\n\n*********************************************\n\n");
			System.out.println("Present state : "+ps);
			System.out.println("Status : "+status);
			System.out.println("\n\n*********************************************\n\n");
			System.out.println("Enter Input:");
			
			Scanner ch = new Scanner(System.in);
			
			String s = ch.next();	//Enter Nickel or Dime
			if(s.charAt(0)=='N')
				{
					ps = 1;
					out_state = 0;
					if(out_state==1)
						status = "Chocolate released";
					else
						status = "Chocolate not released";
					
					System.out.println("Present state : "+ps);
					System.out.println("Status : "+status);
					
					System.out.println("Enter Input:");
					String s2 = ch.next();
					
						if(s2.charAt(0)=='N')
						{
							ps = 0;
							out_state = 1;
							
							if(out_state==1)
								status = "Chocolate released";
							else
								status = "Chocolate not released";
						
							
							System.out.println("Present state : "+ps);
							System.out.println(" Status : "+status);
						
							
						}
						
						else if(s2.charAt(0)=='D')
						{
							ps = 0;
							out_state = 0;
							status = "Machine reset";
							System.out.println("Rule two violated");
							System.out.println("Present state : "+ps);
							System.out.println(" Status : "+status);
							
						}
				
			
				}
			else 
			{
				ps = 0;
				out_state = 1;
				
				
				if(out_state==1)
					status = "Chocolate released";
				else
					status = "Chocolate not released";
				
				System.out.println("Present state : "+ps);
				System.out.println(" Status : "+status);
				
			}
			
			
			
		
		}
		
		
		
	}
	
	public static void show_state_transition_table()
	{

		System.out.println("Transition Table");
		System.out.println("PS\tN\tD\tNextState\tOutput");
		System.out.println("\n\n");
		System.out.println("0\t0\t0\t0\t\t0");
		System.out.println("0\t0\t1\t0\t\t1");
		System.out.println("0\t1\t0\t1\t\t0");
		System.out.println("0\t1\t1\tX\t\tX");
		System.out.println("1\t0\t0\t1\t\t0");
		System.out.println("1\t0\t1\t0\t\t1");
		System.out.println("1\t1\t0\t0\t\t1");
		System.out.println("1\t1\t1\tX\t\tX");
		
	}
		
	
	
	
}
