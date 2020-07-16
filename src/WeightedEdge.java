
public class WeightedEdge
{
	String vertex1;
	String vertex2;
	int weight;
	
	public WeightedEdge(String vertex1, String vertex2, int weight) throws Exception
	{
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
		
		if(weight < 0)
			throw new Exception("Negative weights are forbidden!");
	}
	
	public WeightedEdge clone()
	{
		try
		{
			return new WeightedEdge(vertex1, vertex2, weight);
		} catch (Exception e)
		{
			// unreachable, since the new edge is a clone of a legal edge and thus cannot throw an exception, but java made me do it.
			e.printStackTrace();
			return null;
		}
	}
	
	// reverses the order of the vertices if a negative number is passed in
	// used by some methods that need a directional edge for simplicity
	public WeightedEdge clone(int direction)
	{
		try
		{
			if(direction < 0)
				return new WeightedEdge(vertex2, vertex1, weight);
			
			return clone();
			
		} catch (Exception e)
		{
			// unreachable, since the new edge is a clone of a legal edge and thus cannot throw an exception, but java made me do it.
			e.printStackTrace();
			return null;
		}
	}
}
