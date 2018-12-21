import java.util.Random;
import java.math.*;

public class Organism implements Comparable
{
	//Declare variables
	//A "cycle" denotes a single chance to reproduce.
	public int maxOffspringPerCycle;
	//A "year" is the shortest amount of time between reproductive seasons.
	public int yearsBeforeReproducing;
	public int yearsAlive;
	public int cyclesPerYear;//May vary
	public static int genomeBase = 10;
	public int[] genome;
	public int birthyear;
	public Organism[] organisms;
	public Random randomNumberGenerator = new Random();
	public int offspring = 0;
	public int fitness;
	public static int mutationChanceDenominator = 16;
	
	//Set variables when an instance is created
	public Organism(int[] genomeInput, int birthyearInput)
	{
		//Set variables to default values
		this.maxOffspringPerCycle = 5;
		this.yearsBeforeReproducing = 0;
		this.yearsAlive = 1048576;
		this.cyclesPerYear = 1;
		//Set variables to custom values
		this.genome = genomeInput;
		this.birthyear = birthyearInput;
	}
	
	//Each year, the organism will take the number of the year and its fitness.
	public Organism[] newYear(int year, int fitness)
	{
		calculateOffspring(fitness);
		//Reproduce
		return(reproduce(offspring, year));
	}
	
	//A greater fitness results in a greater number of offspring, up to five.
	public void calculateOffspring(int fitness)
	{
		//Offspring is 0 by default.
		offspring = 0;
		//For each possible number of offspring in turn
		for(int i = 1; i <= maxOffspringPerCycle; i++)
		{
			//If their fitness is low enough to have only the (base) number of offspring which is being tested
			if(fitness <= (double)i * genome.length / maxOffspringPerCycle)
			{
				//Set the number of offspring to this number
				offspring = i;
				//End the subroutine
				break;
			}
		}
	}
	
	public Organism[] reproduce(int offspring, int year)
	{
		//Create an array to be filled with offspring
		Organism[] newOrganisms = new Organism[offspring];
		//For each offspring
		for(int i = 0; i < offspring; i++) {
			//Add a new organism (with an edited genome) to the array to be sent to the environment
			newOrganisms[i] = new Organism(editGenome(), year);
		if(newOrganisms[0].genome == genome)
			System.out.println("Organism is a CLONE of the PARENT!");
		}
		return(newOrganisms);
	}
	
	//Outputs a mutated version of the organism's genome
	public int[] editGenome()
	{
		//Create a deep copy of the genome
		int[] output = copyGenome();
		//For each base
		for(int i = 0; i < output.length; i++)
		{
			//With a 1/(mutationChanceDenominator) chance
			if(randomNumberGenerator.nextInt(mutationChanceDenominator) == 0)
				//Replace the base with a random number
				output[i] = randomNumberGenerator.nextInt(genomeBase);
		}
		//Output the new genome
		return(output);
	}
	
	//Outputs a deep copy of the genome
	public int[] copyGenome()
	{
		//Create a new (output) genome of the same length
		int[] output = new int[genome.length];
		//For each base
		for(int i = 0; i < genome.length; i++)
			//Copy the base
			output[i] = genome[i];
		return(output);
	}
	
	//For use with Comparable, for sorting an array of organisms by fitness
	public int compareTo(Object temp)
	{
		//Convert the other organism into an Organism object
		Organism other = (Organism)temp;
		
		//If the organism is less fit, output 1 so it will appear later in the array
		if(this.fitness < other.fitness)
			return 1;
		//If the organism is more fit, output -1 so it will appear earlier in the array
		if(this.fitness > other.fitness)
			return -1;
		//Else, output 0
		if(this.fitness == other.fitness)
			return 0;
		//If there was an error
		System.out.println("FITNESS NOT SET!");
		return(0);
	}
}