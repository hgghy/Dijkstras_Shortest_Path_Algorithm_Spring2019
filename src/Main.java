
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		// this program can be run in one of several modes
		// to change modes just change the method called in this method
		
		// runOnUserInput queries the user to get the information used in creating the graph
		// just run the program and answer the questions
		
		// runsOnHardcodedData creates the graph based on hardcoded data
		// just edit the data in the method
		
		// runsOnRandomData randomly generates the graph
		// feel free to change the numberOfEdges (in the method)
		
		runOnUserInput();
	}
	
	public static WeightedGraph runOnUserInput() throws Exception
	{		
		WeightedGraph graph = new WeightedGraph();
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
		
		scan.close();
		return graph;
	}
	
	public static WeightedGraph runsOnHardcodedData() throws Exception
	{
		WeightedGraph graph = new WeightedGraph();
		
 		graph.add(new WeightedEdge("vertex1", "vertex2", 1));
		graph.add(new WeightedEdge("vertex1", "vertex3", 2));
		graph.add(new WeightedEdge("vertex1", "vertex5", 3));
		graph.add(new WeightedEdge("vertex2", "vertex3", 4));
		graph.add(new WeightedEdge("vertex5", "vertex4", 5));
		graph.add(new WeightedEdge("vertex3", "vertex4", 6));
		printPathToAll(graph.getPathToAll("vertex3"));
		return graph;
	}
	
	public static WeightedGraph runsOnRandomData() throws Exception
	{
		WeightedGraph graph = new WeightedGraph();
		
		int numberOfEdges = 30000; // number of edges to create
		Random rand = new Random();
		WeightedEdge edge;
		for(int i = 0; i < numberOfEdges; i++)
		{
			edge = new WeightedEdge(
					"vertex"+(String.format("%0" + String.valueOf(numberOfEdges).length() + "d", rand.nextInt(numberOfEdges) + 1)), 
					"vertex"+(String.format("%0" + String.valueOf(numberOfEdges).length() + "d", rand.nextInt(numberOfEdges) + 1)), 
					(rand.nextInt(5)+1));
			System.out.println(edge.vertex1 + ", " + edge.vertex2 + ", " + edge.weight);
			graph.add(edge);
		}
		
		System.out.println();
		
		printPathToAll(graph.getPathToAll(graph.getRandomVertex()));
		return graph;
	}	
	
	// this method is used by the others to print the path to each vertex
	public static void printPathToAll(HashMap<String, Path> paths)
	{
		PriorityQueue<String> q = new PriorityQueue<String>();
		
		paths.entrySet().forEach(i -> q.add("Vertex: " + i.getKey() + 
				(i.getValue() == null ? " This vertex cannot be reached" : (" Distance: " + i.getValue().distance + " Path: " + i.getValue().path))));
		
		while(!q.isEmpty())
		{
			System.out.println(q.poll());
		}
	}
}
