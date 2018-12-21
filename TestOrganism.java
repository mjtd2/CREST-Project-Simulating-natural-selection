import java.util.Random;

public class TestOrganism
{
	public static void main(String[] args)
	{
		int[] genome = {1,2,3,1};
		Organism theOrganism = new Organism(genome,0);
		System.out.println(theOrganism.genome[0]);
		theOrganism.genome[0] = 4;
		System.out.println(theOrganism.genome[0]);
	}
}