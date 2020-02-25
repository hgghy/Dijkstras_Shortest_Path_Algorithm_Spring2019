
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class WeightedGraph
{
	private HashSet<String> vertices = new HashSet<String>(); // list of vertex IDs
	private ArrayList<WeightedEdge> edges = new ArrayList<WeightedEdge>();
	
	public WeightedGraph()
	{
		
	}
	
	public WeightedGraph(ArrayList<WeightedEdge> edges)
	{
		for(int i = 0; i < edges.size(); i++)
		{
			add(edges.get(i).clone());
		}
	}
	
	public void add(WeightedEdge edge)
	{
		edges.add(edge);
		vertices.add(edge.vertex1);
		vertices.add(edge.vertex2);
	}
	
	public Path getPath(String start, String end) throws Exception
	{
		return getPathToAll(start).get(end);
	}
	
	public HashMap<String, Path> getPathToAll(String start) throws Exception // Dijkstra's shortest path algorithm
	{
		if(!vertices.contains(start))
		{
			throw new Exception("The starting vertex is not in the graph!");
		}
		
		HashSet<String> verticesToProcess = new HashSet<String>();
		HashMap<String, Path> paths = new HashMap<String, Path>();
		
		Iterator<String> vertexes = vertices.iterator();
		String nextVertex;
		while(vertexes.hasNext())
		{
			nextVertex = vertexes.next();
			
			paths.put(nextVertex, null);
		}
		
		// We don't have to test all vertices, just those that can be reached from the starting vertex,
		// so we'll add vertexes to verticesToProcess when we reach them, instead of adding them at the beginning
		paths.put(start, new Path(0, start));
		verticesToProcess.add(start);
		
		while(!verticesToProcess.isEmpty())
		{
			String closestVertex = getClosestVertex(verticesToProcess, paths);
			
			verticesToProcess.remove(closestVertex);
			
			WeightedEdge edge;
			for(int i = 0; i < edges.size(); i++)
			{
				edge = edges.get(i);
				
				if(edge.vertex1.equals(closestVertex))
				{
					// Is there a better way to do this?
					if(paths.get(edge.vertex2) == null)
						verticesToProcess.add(edge.vertex2);
					if(paths.get(edge.vertex2) == null || edge.weight + paths.get(edge.vertex1).distance < paths.get(edge.vertex2).distance)
					{
						paths.put(edge.vertex2, new Path(edge.weight + paths.get(edge.vertex1).distance, paths.get(edge.vertex1).path + " -> " + edge.vertex2));
					}
				}
				else if(edge.vertex2.equals(closestVertex)) // This is not a directed graph. If it was, this if block would not be here.
				{
					// Is there a better way to do this?
					if(paths.get(edge.vertex1) == null)
						verticesToProcess.add(edge.vertex1);
					if(paths.get(edge.vertex1) == null || edge.weight + paths.get(edge.vertex2).distance < paths.get(edge.vertex1).distance)
					{
						paths.put(edge.vertex1, new Path(edge.weight + paths.get(edge.vertex2).distance, paths.get(edge.vertex2).path + " -> " + edge.vertex1));
					}
				}
			}
		}
		
		return paths;
	}
	
	private String getClosestVertex(HashSet<String> verticesToProcess, HashMap<String, Path> paths)
	{
		Iterator<String> vertexes = verticesToProcess.iterator();
		String closestVertex = vertexes.next();
		String nextVertex;
		while(vertexes.hasNext())
		{
			nextVertex = vertexes.next();
			if(paths.get(nextVertex).distance < paths.get(closestVertex).distance)
				closestVertex = nextVertex;
		}
		
		return closestVertex;
	}
	
	public String getRandomVertex()
	{
		Random rand = new Random();
		Iterator<String> it = vertices.iterator(); // alphabetical order, apparently.
		String s = it.next();
		
		for(int i = rand.nextInt(vertices.size()); i > 0; i--)
		{
			s = it.next();
		}
		
		return s;
	}
	
	public ArrayList<WeightedEdge> getEdges()
	{
		return edges;
	}
}
