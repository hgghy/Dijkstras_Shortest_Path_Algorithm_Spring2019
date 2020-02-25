
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
			// unreachable, but java made me do it.
			e.printStackTrace();
			return null;
		}
	}
}
