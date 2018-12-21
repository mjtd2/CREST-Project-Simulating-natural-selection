/*
This program is similar to RunEnvironmentTwoVariableParameters.class, but with multithreading supported.
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread;
import java.math.*;
import java.util.Scanner;

public class MultithreadedRunEnvironmentThreeVariableParameters
{
	public static BufferedWriter bw;
	
	public static void main(String[] args)
	{
		System.out.println("0 - Target fitness    1 - Maximum no. of survivors    2 - Genome length    3 - Genome base    4 - Mutation chance denominator");
		final int independentVariableInt = getIntegerInput("number which corresponds to the desired independent variable", 0, 4);
		
		//If the independent variable is targetFitness
		if(independentVariableInt == 0)
		{
			//Get input for the constants
			final int genomeLength = getIntegerInput("length of the Organisms' genomes", 1, 64);
			final int genomeBase = getIntegerInput("base of the Organisms' genomes", 2, 32);
			final int maxNumberOfOrganisms = getIntegerInput("maximum number of Organisms to survive each year", 1, 32);
			final int mutationChanceDenominator = getIntegerInput("denominator 'd' for the 1/d chance each base has of mutating during reproduction", 8, 64);
			
			prepareBufferedWriter();
			
			//For ascending values of targetFitness
			for(int targetFitness = 0; targetFitness <= genomeLength; targetFitness ++)
			{
				System.out.println("Target fitness: " + targetFitness);
				runRepeatedly(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator);
			}
			closeBufferedWriter();
		}
		//If the independent variable is maxNumberOfOrganisms
		else if(independentVariableInt == 1)
		{
			//Get input for the constants
			final int genomeLength = getIntegerInput("length of the Organisms' genomes", 1, 64);
			final int genomeBase = getIntegerInput("base of the Organisms' genomes", 2, 32);
			final int targetFitness = getIntegerInput("mean fitness you want the Organisms to reach", 0, genomeLength);
			final int mutationChanceDenominator = getIntegerInput("denominator 'd' for the 1/d chance each base has of mutating during reproduction", 8, 64);
			
			prepareBufferedWriter();
			
			//For ascending values of maxNumberOfOrganisms
			for(int maxNumberOfOrganisms = 1; maxNumberOfOrganisms < 65; maxNumberOfOrganisms ++)
			{
				System.out.println("Maximum no. of survivors: " + maxNumberOfOrganisms);
				runRepeatedly(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator);
			}
			closeBufferedWriter();
		}
		//If the independent variable is genomeLength
		else if(independentVariableInt == 2)
		{
			//Get input for the constants
			final int genomeBase = getIntegerInput("base of the Organisms' genomes", 2, 32);
			final int targetFitnessPercentage = getIntegerInput("mean fitness you want the Organisms to reach, as a percentage of the genome's length", 0, 100);
			final int maxNumberOfOrganisms = getIntegerInput("maximum number of Organisms to survive each year", 1, 32);
			final int mutationChanceDenominator = getIntegerInput("denominator 'd' for the 1/d chance each base has of mutating during reproduction", 8, 64);
			
			prepareBufferedWriter();
			
			//For ascending values of genomeLength
			for(int genomeLength = 1; genomeLength < 129; genomeLength ++)
			{
				System.out.println("Genome length: " + genomeLength);
				runRepeatedly( (int)Math.round( (double)targetFitnessPercentage * genomeLength / 100), maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator);
			}
			closeBufferedWriter();
		}
		//If the independent variable is genomeBase
		else if(independentVariableInt == 3)
		{
			final int genomeLength = getIntegerInput("length of the Organisms' genomes", 1, 64);
			final int targetFitness = getIntegerInput("mean fitness you want the Organisms to reach", 0, genomeLength);
			final int maxNumberOfOrganisms = getIntegerInput("maximum number of Organisms to survive each year", 1, 32);
			final int mutationChanceDenominator = getIntegerInput("denominator 'd' for the 1/d chance each base has of mutating during reproduction", 8, 64);
			
			prepareBufferedWriter();
			
			//For ascending values of genomeBase
			for(int genomeBase = 1; genomeBase < 65; genomeBase ++)
			{
				System.out.println("Genome base: " + genomeBase);
				runRepeatedly(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator);
			}
			closeBufferedWriter();
		}
		//If the independent variable is mutationChanceDenominator
		else if(independentVariableInt == 4)
		{
			//Get input for the constants
			final int genomeLength = getIntegerInput("length of the Organisms' genomes", 1, 64);
			final int genomeBase = getIntegerInput("base of the Organisms' genomes", 2, 32);
			final int maxNumberOfOrganisms = getIntegerInput("maximum number of Organisms to survive each year", 1, 32);
			final int targetFitness = getIntegerInput("mean fitness you want the Organisms to reach", 0, genomeLength);
			
			prepareBufferedWriter();
			
			//For ascending values of targetFitness
			for(int mutationChanceDenominator = 8; mutationChanceDenominator < 65; mutationChanceDenominator ++)
			{
				System.out.println("Mutation chance per base: 1/" + mutationChanceDenominator);
				runRepeatedly(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator);
			}
			closeBufferedWriter();
		}
	}
	public static void closeBufferedWriter()
	{
		try
		{
			bw.close();
		}
		catch(IOException e)
		{
			System.out.println("Exception occurred closing BufferedWriter.");
		}
	}
	
	//Prepares bw using an input filename
	public static void prepareBufferedWriter()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please type the name of the file you wish to write the results to...");
		String filename = sc.next();
		//If the target file exists
		while(new File(filename + ".txt").exists())
		{
			System.out.println("File already exists!" + System.getProperty("line.separator") + "Please type the name of the file you wish to write the results to...");
			filename = sc.next();
		}
		//Create a new BufferedWriter
		try
		{
		FileWriter fw = new FileWriter(filename + ".txt");
		bw = new BufferedWriter(fw);
		}
		catch(IOException e)
		{
			System.out.println("Exception occurred preparing BufferedWriter.");
		}
		
	}
	
	//Runs EnvironmentThree 5000 times with the parameters input.
	public static void runRepeatedly(int targetFitness, int maxNumberOfOrganisms, int genomeLength, int genomeBase, int mutationChanceDenominator)
	{
		int totalYears = 0;
		//Create an array of 4 threads
		EnvironmentThree[] environments = new EnvironmentThree[4];
		Thread[] threads = new Thread[4];
		//Set up and start threads 0 to 3
		for(int i = 0; i < 4; i++)
		{
			environments[i] = new EnvironmentThree(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator); 
			threads[i] = new Thread(environments[i]);
			threads[i].start();
		}
		//For each repeat
		for(int i = 4; i < 5000; i++)
		{
			int index = i%4;
			//Wait for the thread to finish
			try
			{
				threads[index].join();
			}
			catch(InterruptedException e)
			{
				System.err.println("InterruptedException");
			}
			//Add the years the simulation took to the total
			totalYears += environments[index].year;
			//Create a new EnvironmentThree thread
			environments[index] = new EnvironmentThree(targetFitness, maxNumberOfOrganisms, genomeLength, genomeBase, mutationChanceDenominator); 
			threads[index] = new Thread(environments[index]);
			//Run it
			threads[index].start();
		}
		//Get the year from the final four threads
		for(int i = 0; i < 4; i++)
		{
			//Wait for the thread to finish
			try
			{
				threads[i].join();
			}
			catch(InterruptedException e)
			{
				System.err.println("InterruptedException");
			}
			//Add the years the simulation took to the total
			totalYears += environments[i].year;
		}
		//Find the mean number of years taken with these parameters
		double meanNumberOfYears = (double)totalYears / 5000;
		
		try
		{
			//Write the mean number of years, to 6 d.p., to the file.
			bw.write(String.format("%.6f", meanNumberOfYears));
			bw.newLine();
		}
		catch(IOException e)
		{
			System.out.println("A write error has occurred.");
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