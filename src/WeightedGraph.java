
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class WeightedGraph
{
	private HashSet<String> vertices = new HashSet<String>(); // list of vertex IDs
	private ArrayList<WeightedEdge> edges = new ArrayList<WeightedEdge>();
	
	public WeightedGraph()
	{
		// this is deliberately left empty
	}
	
	public WeightedGraph(ArrayList<WeightedEdge> edges)
	{
		// we add clones to avoid having the edges modified by outside code
		for(WeightedEdge edge : edges)
			add(edge.clone());
	}
	
	public void add(WeightedEdge edge)
	{
		// when we add an edge, we also add the vertices on either end of it
		// note that adding a vertex twice does nothing
		edges.add(edge);
		vertices.add(edge.vertex1);
		vertices.add(edge.vertex2);
	}
	
	public Path getPath(String start, String end) throws Exception
	{
		// while it may seem wasteful to calculate the paths to all vertices when were looking for one,
		// Dijkstra's shortest path algorithm is actually the fastest way to find it
		return getPathToAll(start).get(end);
	}
	
	public HashMap<String, Path> getPathToAll(String start) throws Exception // This method is the part that actually contains Dijkstra's shortest path algorithm
	{
		if(!vertices.contains(start))
		{
			throw new Exception("The starting vertex is not in the graph!");
		}
		
		// as vertices are discovered they are added to the following collections...
		HashSet<String> verticesToProcess = new HashSet<String>(); // ...to store a list of unprocessed vertices...
		HashMap<String, Path> paths = new HashMap<String, Path>(); // ... and to store the paths to each vertex.
		
		// add all the vertices to paths
		// we can't add them as they are discovered in case they are never discovered. 
		// e.g. the graph {a->b, c->d} if we start in a, c and d need to show up in the results, with null as their path 
		for(String vertex : vertices)
		{
			paths.put(vertex, null);
		}
		
		// We don't have to test all vertices, just those that can be reached from the starting vertex,
		// so we'll add vertexes to verticesToProcess when we reach them, instead of adding them at the beginning
		paths.put(start, new Path(0, start)); // we need the start to not be null so that the later paths can be created
		verticesToProcess.add(start);
		
		while(!verticesToProcess.isEmpty())
		{
			String closestVertex = getClosestVertex(verticesToProcess, paths);
			
			verticesToProcess.remove(closestVertex);
			
			searchClosestVertex(closestVertex, paths, verticesToProcess);
		}
		
		return paths;
	}
	
	// searches all edges on closestVertex and updates paths and verticesToProcess of all the vertices to which they lead
	private void searchClosestVertex(String closestVertex, HashMap<String, Path> paths, HashSet<String> verticesToProcess)
	{
		for(WeightedEdge edge: edges) // for each edge...
		{
			// ... if one end is the closest vertex, we follow the edge and update the information of the vertex we reach
			
			// since followEdge takes a monodirectional edge, the else statement has to reverse the edge with edge.clone(-1)
			if(edge.vertex1.equals(closestVertex))
			{
				followEdge(edge, paths, verticesToProcess);
			}
			else if(edge.vertex2.equals(closestVertex)) // This is not a directed graph. If it was, this if block would not be here.
			{
				followEdge(edge.clone(-1), paths, verticesToProcess);
			}
		}
	}
	
	// given a *monodirectional* edge, this adds the vertex at its end to verticesToProcess (if necessary), and updates its path (if necessary)
	// Note that this method assumes the edge has a direction of vertex1 -> vertex2. since the rest of this program is built to 
	// work on bidirectional edges, we sometimes have to reverse the edges with edge.clone(-1)
	private void followEdge(WeightedEdge edge, HashMap<String, Path> paths, HashSet<String> verticesToProcess) 
	{
		if(paths.get(edge.vertex2) == null) // if this vertex has yet to be reached i.e. hasn't been processed yet
			verticesToProcess.add(edge.vertex2);
		
		// if this is the fastest way to reach the vertex, update it's path
		if(paths.get(edge.vertex2) == null || edge.weight + paths.get(edge.vertex1).distance < paths.get(edge.vertex2).distance)
		{
			paths.put(edge.vertex2, new Path(edge.weight + paths.get(edge.vertex1).distance, paths.get(edge.vertex1).path + " -> " + edge.vertex2));
		}
		
	}
	
	// this method is used to get the unprocessed vertex with the shortest path
	private String getClosestVertex(HashSet<String> verticesToProcess, HashMap<String, Path> paths)
	{
		String closestVertex = null;
		for(String vertex : verticesToProcess)
		{
			if(closestVertex == null || paths.get(vertex).distance < paths.get(closestVertex).distance)
				closestVertex = vertex;
		}
		
		return closestVertex;
	}
	
	public String getRandomVertex()
	{
		// the cast is necessary because toArray() returns an Object[], not a String[]
		return (String) vertices.toArray()[new Random().nextInt(vertices.size())];
	}
	
	public ArrayList<WeightedEdge> getEdges()
	{
		return edges;
	}
}
