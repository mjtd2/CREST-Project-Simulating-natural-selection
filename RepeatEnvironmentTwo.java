import java.util.Scanner;
/*
This program runs EnvironmentTwo.class a number of times and takes a mean of the number of years the simulation lasts.
*/

public class RepeatEnvironmentTwo
{
	public static void main(String[] args)
	{
		int genomeLength = getIntegerInput("length of the Organisms' genomes", 1, 64);
		int genomeBase = getIntegerInput("base of the Organisms' genomes", 2, 32);
		int targetFitness = getIntegerInput("mean fitness you want the Organisms to reach", 0, genomeLength);
		int maxNumberOfOrganisms = getIntegerInput("maximum number of Organisms to survive each year", 1, 32);
		int repeats = getIntegerInput("number of times you want the simulation to run", 1, 5000);
		
		int totalYears = 0;
		//For each repeat
		for(int i = 1; i <= repeats; i++)
		{
			//Create a new EnvironmentTwo
			EnvironmentTwo environment = new EnvironmentTwo(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase);
			totalYears += environment.run();
			double meanNumberOfYears = (double)totalYears / i;
			System.out.println("Mean number of years after " + i + " simulations: " + String.format("%.6f", meanNumberOfYears));
		}
	}
	
	//Gets an integer within a given range as input.
	public static int getIntegerInput(String variableDescription, int minimum, int maximum)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type the " + variableDescription + " (" + minimum + " to " + maximum + ")...");
		
		while(true)
		{
			//If the input is not an integer
			if(!sc.hasNextInt())
			{
				//Make them type it again
				System.out.println("Please type the " + variableDescription + " (" + minimum + " to " + maximum + ")...");
				//Clear the buffer
				sc.nextLine();
				continue;
			}
			//Set the output to their input
			int output = sc.nextInt();
			//If their input is outside the domain
			if(output < minimum || output > maximum)
				//Make them type it again
				System.out.println("Please type the " + variableDescription + " (" + minimum + " to " + maximum + ")...");
			//If their input is reasonable
			else
				//Return their input
				return output;
		}
	}
}