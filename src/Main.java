
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		WeightedGraph graph = new WeightedGraph();
		
		/* Here's a couple of easier ways to test:
 		// 1:
 		graph.add(new WeightedEdge("vertex1", "vertex2", 1));
		graph.add(new WeightedEdge("vertex1", "vertex3", 2));
		graph.add(new WeightedEdge("vertex1", "vertex5", 3));
		graph.add(new WeightedEdge("vertex2", "vertex3", 4));
		graph.add(new WeightedEdge("vertex5", "vertex4", 5));
		graph.add(new WeightedEdge("vertex3", "vertex4", 6));
		printPathToAll(graph.getPathToAll("vertex3"));
		 */
		
		//2:
		/*
		Random rand = new Random();
		for(int i = 0; i < 10; i++)
		{
			graph.add(new WeightedEdge(""+(rand.nextInt(10)+1), ""+(rand.nextInt(10)+1), (rand.nextInt(10)+1)));
		}
		
		graph.getEdges().forEach(i -> System.out.println(i.vertex1+", "+i.vertex2+", "+i.weight));
		
		printPathToAll(graph.getPathToAll(graph.getRandomVertex()));
		*/
		
		Scanner scan = new Scanner(System.in);
		
		String inputstring = "";
		do
		{
			System.out.println("Enter 'done' or a edge in the following format: <VertexID1>, <VertexID2>, <Weight>");
			inputstring = scan.nextLine();
			if(!inputstring.equalsIgnoreCase("done"))
			try
			{
				String[] tokens = inputstring.split(", ");
				if(tokens.length != 3)
					throw new Exception();
				graph.add(new WeightedEdge(tokens[0], tokens[1], Integer.parseInt(tokens[2])));
			}
			catch (Exception e)
			{
				System.out.println("That didn't work. Check your input and try again.");
			}
		} while(!inputstring.equalsIgnoreCase("done"));
		
		boolean done = false;
		do
		{
			try
			{
				System.out.println("Now enter the vertex from which you want paths calculated");
				printPathToAll(graph.getPathToAll(scan.nextLine()));
				done = true;
			}
			catch (Exception e)
			{
				System.out.println("That didn't work. Check your input and try again.");
			}
		} while(!done);
		
		scan.nextLine(); // in case someone is using cmd, it won't close
	}
	
	public static void printPathToAll(HashMap<String, Path> paths)
	{
		paths.entrySet().forEach(i -> System.out.println("Vertex: " + i.getKey() + 
				(i.getValue() == null ? " This vertex cannot be reached" : (" Distance: " + i.getValue().distance + " Path: " + i.getValue().path))));
	}
}
