import java.util.Scanner;

/**
 * A simple class to run the Magpie class.
 * @author Laurie White
 * @version April 2012
 */
public class tvChatBotRunner
{

	/**
	 * Create a Magpie, give it user input, and print its replies.
	 */
	public static void main(String[] args)
	{
		tvchatbot chat = new tvchatbot();
		
		//String name = chat.Greeting();
		Scanner in = new Scanner (System.in);
		String statement = in.nextLine();
		
		
		while (!statement.equals("Bye"))
		{
			int num = chat.getState(statement);
			System.out.println(chat.setState(num, statement));
			statement = in.nextLine();
		}
	}

}
