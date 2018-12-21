import java.util.Random;
import java.util.Arrays;

/*
This class is for creating objects which behave like EnvironmentOne.class, but which have no console output and have custom settings
*/
public class EnvironmentTwo
{
	public int[] optimalGenome;
	public Organism[] parents;
	public Organism[][] offspring;
	public int targetFitness;
	public int maxNumberOfOrganisms;
	public int genomeLength;
	public int genomeBase;
	
	//Create a new random number generator
	public Random randomNumberGenerator = new Random();
	
	public EnvironmentTwo(int targetFitness, int maxNumberOfOrganisms, int genomeLength, int genomeBase)
	{
		this.targetFitness = targetFitness;
		this.maxNumberOfOrganisms = maxNumberOfOrganisms;
		this.genomeLength = genomeLength;
		this.genomeBase = genomeBase;
	}
	
	//Runs the program & returns the number of years taken.
	public int run()
	{
		//Create the array for the parent Organisms
		parents = new Organism[maxNumberOfOrganisms];
		
		//Create the optimal genome
		optimalGenome = createRandomGenome();
		
		//Create the first parent
		parents[0] = new Organism(createRandomGenome(), 0);
		
		int year = 0;
		//On the 'zero-th' year, find the fitness of the first Organism
		int originalFitness = decideFitness(parents[0]);
		//If the 'mean fitness' is above 25, end the simulation
		if(originalFitness >= targetFitness)
		{
			//System.out.println(System.getProperty("line.separator") + "  Year: " + year + "    Number of organisms: 1    Sum of fitness: " + originalFitness + "    Mean Fitness: " + originalFitness);
			return year;
		}
		else
		{
			//Every year, until the simulation is complete
			while(true)
			{
				year ++;
				//Create a 2D array of organisms, where the x value represents the parent and the y value represents its offspring for the year
				offspring = new Organism[parents.length][];
				//For each parent
				for(int i = 0; i < parents.length; i++)
				{
					//If the parent exists
					if(parents[i] != null)
						//Add its offspring to the array of new organisms
						offspring[i] = parents[i].newYear(year, decideFitness(parents[i]));
					else
						break;
				}
				//Collect all organisms into one array
				Organism[] organismsInOrder = collectOrganisms();
				
				//Reset the sum of the organisms' fitness
				int sumOfFitness = 0;
				//For each organism
				for(Organism organism : organismsInOrder)
				{
					//Set its fitness based on its genome
					organism.fitness = decideFitness(organism);
					//Add the organism's fitness to the total fitness
					sumOfFitness += organism.fitness;
				}
				
				//Sort the organisms by fitness
				Arrays.sort(organismsInOrder);
				
				//Set numberOfOrganisms for the output
				int numberOfOrganisms = getNumberOfOrganisms();
				
				//Reset the total fitness and the number of organisms counted
				int totalFitness = 0;
				int numberOfLiveOrganisms = 0;
				//Replace the current parents with the fittest Organisms
				int maximum = 0;
				if(parents.length > organismsInOrder.length)
					maximum = organismsInOrder.length;
				else
					maximum = parents.length;
				
				for(int i = 0; i < maximum; i++)
				{
					if(organismsInOrder[i] != null)
					{
						parents[i] = organismsInOrder[i];
						//Add the organism's fitness to totalFitness
						totalFitness += decideFitness(parents[i]);
						//Increment the number of organisms
						numberOfLiveOrganisms ++;
					}
					else
						break;
				}
				//Find the mean fitness
				double meanFitness = (double)totalFitness / numberOfLiveOrganisms;

				//If the mean fitness is at least the target
				if(meanFitness >= targetFitness)
					//Stop the simulation
					return year;
			}
		}
	}
	
	public Organism[] collectOrganisms()
	{
		//Create a new array to be filled with all organisms in order of fitness
		Organism[] allOrganisms = new Organism[getNumberOfOrganisms()];
		//index is the index in allOrganisms to be added to
		int index = 0;
		
		//For each parent
		for(int x = 0; x < parents.length; x++)
		{
			//If they exist
			if(parents[x] != null)
			{
				//Add the parent
				allOrganisms[index] = parents[x];
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
	
	public int getNumberOfOrganisms()
	{
		int count = 0;
		
		//For each parent
		for(int i = 0; i < parents.length; i++)
		{
			//If they exist
			if(parents[i] != null)
				//Add 1 for the parent, plus one for each offspring
				count += 1 + offspring[i].length;
			else
				break;
		}
		return(count);
	}
	
	public int[] createRandomGenome()
	{
		//Declare output genome
		int[] genome = new int[genomeLength];
		//For each base
		for(int i = 0; i < genomeLength; i++)
			//Set to a random number
			genome[i] = randomNumberGenerator.nextInt(genomeBase);
		return(genome);
	}
	
	public int decideFitness(Organism organism)//ONLY WORKS IF THE OPTIMAL GENOME IS AT LEAST AS LONG!!!
	{
		int fitness = 0;
		//For each base
		for(int i = 0; i < organism.genome.length; i++)
		{
			//If the organism's genome's base is the same as the optimal genome's base
			if(organism.genome[i] == optimalGenome[i])
				//Add 1 to fitness
				fitness += 1;
		}
		return(fitness);
	}
}