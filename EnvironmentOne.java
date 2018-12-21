import java.util.Random;
import java.util.Arrays;

/*
In this environment:
	All genomes are 32 bases long
	All genomes are in base 10
	No more than 10 organisms can be alive at once.
*/
public class EnvironmentOne
{
	public static int[] optimalGenome;
	public static Organism[] organisms;
	public static Organism[][] offspring;
	
	//Create a new random number generator
	public static Random randomNumberGenerator = new Random();
	
	public static void main(String[] args)
	{
		//Create the array for the (up to) 10 surviving organisms
		organisms = new Organism[10];
		
		//Create the optimal genome
		//optimalGenome = createRandomGenome();  //OLD CODE
		optimalGenome = new int[32];
		for(int i = 0; i < 32; i++)
			optimalGenome[i] = 0;
		
		//Create the first organism
		organisms[0] = new Organism(createRandomGenome(), 0);
		
		//Output the optimal genome
		System.out.print("Optimal Genome:");
		for(int i = 0; i < 32; i++)
			System.out.print(" " + optimalGenome[i]);
		//Output the first organism's genome
		System.out.print(System.getProperty("line.separator") + "First Genome:  ");
		for(int i = 0; i < 32; i++)
			System.out.print(" " + organisms[0].genome[i]);
		System.out.println();
		
		//Set year counter
		int year = 0;
		//On the 'zero-th' year, find the fitness of the first Organism
		int originalFitness = decideFitness(organisms[0]);
		//If the 'mean fitness' is above 25, end the simulation
		if(originalFitness > 25)
			System.out.println(System.getProperty("line.separator") + "  Year: " + year + "    Number of organisms: 1    Sum of fitness: " + originalFitness + "    Mean Fitness: " + originalFitness);
		else
		{
			//Every year after the 'zero-th', until the simulation is complete
			while(true)
			{
				//Increment the year
				year ++;
				//Output a title for the year
				System.out.println(System.getProperty("line.separator") + System.getProperty("line.separator") + "---------------------------------- NEW YEAR: Year " + year + " ----------------------------------" + System.getProperty("line.separator"));
				//Create a 2D array of organisms, where the x value represents the parent and the y value represents its offspring for the year
				offspring = new Organism[organisms.length][];
				//For each organism
				for(int i = 0; i < 10; i++)
				{
					//If the organism exists
					if(organisms[i] != null)
						//Add its offspring to the array of new organisms
						offspring[i] = organisms[i].newYear(year, decideFitness(organisms[i]));
				}
				//Collect all organisms into one array
				Organism[] organismsInOrder = collectOrganisms();
				
				
				
				//For each organism
				for(Organism organism : organismsInOrder)
					//Set its fitness based on its genome
					organism.fitness = decideFitness(organism);
					
				//Reset the sum of the organisms' fitness
				int sumOfFitness = 0;
				//Sort the organisms by fitness
				Arrays.sort(organismsInOrder);
				
				//For each organism, from least fit to fittest
				for(int i = organismsInOrder.length - 1; i > -1; i--)
				{
					//Add the organism's fitness to the total fitness
					sumOfFitness += organismsInOrder[i].fitness;
					//Print the organism's genome
					System.out.print("Genome of organism " + (i + 1) + ":");
					for(int j = 0; j < 32; j++)
						System.out.print(" " + organismsInOrder[i].genome[j]);
					System.out.println(System.getProperty("line.separator"));
				}
				
				//Set numberOfOrganisms for the output
				int numberOfOrganisms = getNumberOfOrganisms();
				
				//Reset the total survivors' fitness and the number of organisms counted
				int totalFitness = 0;
				int numberOfLiveOrganisms = 0;
				//Replace the current parents with the fittest (up to) 10 organisms
				int maximum = 0;
				if(organisms.length > organismsInOrder.length)
					maximum = organismsInOrder.length;
				else
					maximum = organisms.length;
				
				for(int i = 0; i < maximum; i++)
				{
					if(organismsInOrder[i] != null)
					{
						//Add the Organism to the array of next year's parents
						organisms[i] = organismsInOrder[i];
						//Add the Organism's fitness to totalFitness
						totalFitness += decideFitness(organisms[i]);
						//Increment the number of organisms
						numberOfLiveOrganisms ++;
					}
					else
						break;
				}
				//Find the mean fitness
				double meanFitness = (double)totalFitness / numberOfLiveOrganisms;
				//Output the year, number of parents + offspring, the sum of their fitness & the mean fitness of next year's parents
				System.out.println(System.getProperty("line.separator") + "  Year: " + year + "    Number of organisms: " + numberOfOrganisms + "    Sum of fitness: " + sumOfFitness + "    Mean Fitness: " + meanFitness);
				//If the mean fitness is above 25
				if(meanFitness > 25)
					//Stop the simulation here
					break;
			}
		}
	}
	
	public static Organism[] collectOrganisms()
	{
		//Create a new array to be filled with all organisms in order of fitness
		Organism[] allOrganisms = new Organism[getNumberOfOrganisms()];
		//index is the index in allOrganisms to be added to
		int index = 0;
		
		//For each parent
		for(int x = 0; x < 10; x++)
		{
			//If they exist
			if(organisms[x] != null)
			{
				//Add the parent
				allOrganisms[index] = organisms[x];
				index ++;
				//For each offspring
				for(int y = 0; y < offspring[x].length; y++)
				{
					//Add the offspring
					allOrganisms[index] = offspring[x][y];
					index ++;
				}
			}
		}
		return(allOrganisms);
	}
	
	public static int getNumberOfOrganisms()
	{
		int count = 0;
		
		//For each parent
		for(int i = 0; i < 10; i++)
		{
			//If they exist
			if(organisms[i] != null)
				//Add 1 for the parent, plus one for each offspring
				count += 1 + offspring[i].length;
			else
				break;
		}
		return(count);
	}
	
	public static int[] createRandomGenome()
	{
		//Declare output genome
		int[] genome = new int[32];
		//For each base
		for(int i = 0; i < 32; i++)
			//Set to a random number
			genome[i] = randomNumberGenerator.nextInt(10);
		return(genome);
	}
	
	public static int decideFitness(Organism organism)
	{
		int fitness = 0;
		//For each base
		for(int i = 0; i < 32; i++)
		{
			//If the organism's genome's base is the same as the optimal genome's base
			if(organism.genome[i] == optimalGenome[i])
				//Add 1 to fitness
				fitness += 1;
		}
		return(fitness);
	}
}