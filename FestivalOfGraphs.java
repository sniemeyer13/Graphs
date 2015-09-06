//Sarah Niemeyer
//Matt Sainz
//100027519
//Graphs Java Homework
  
  
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Deque;
import java.util.ArrayDeque;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

class FestivalOfGraphs
{
	private Graph g;
	
	public void init(String graphFile) 
	{
		System.out.println(graphFile);
		// open the file
		BufferedReader br = null;
		
		try
		{
			// open the file called "graph.txt"
			br = new BufferedReader(new FileReader(graphFile));
		
			// read the first line
			String line = br.readLine();	
			
			// convert the first line (a string) to an integer (numberOfVertices)
			Integer n = Integer.parseInt(line);
			
			g = new Graph(n);
		
			for (int fromVertex = 0; fromVertex < n; ++fromVertex)
			{
				line = br.readLine();
				if (line.length() > 0)
				{
					int pos = 0;
					while (line.indexOf("{", pos) > -1)
					{
						String sub = line.substring(line.indexOf("{", pos) + 1, line.indexOf("}", pos + 1));
						Integer toVertex = Integer.parseInt(sub.substring(0, sub.indexOf(",")));
						Integer weightOfEdge = Integer.parseInt(sub.substring(sub.indexOf(",") + 1, sub.length()));
						pos = line.indexOf("}", pos + 1);
						if (!g.hasEdge(fromVertex, toVertex))
							g.addEdge(fromVertex, toVertex, weightOfEdge);
					}	
				}	
			}
		}
		catch (FileNotFoundException ex) {
			// handle FileNotFoundException
			System.out.println("No such file as " + graphFile + " in your directory");
		}
		catch (IOException ex) {
			// handle IOException
			System.out.println("Ran into problems processing " + graphFile);
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException ex) {
					// handle IOException
					System.out.println("Ran into unrecoverable problems processing " + graphFile);
				}
			}
		}
		
	}
	
	public List<Integer> DepthFirstList(Integer v)
	{
		//first on last off
		
		//use the stack
		//push the first vertex on the stack, mark it as visited, then look
		//at the vertex at the top of the stack, then find one of its unvisited neighbors,
		//push it on to the top of the stack, then mark it as visited
		//then you check the vertex at the top of the stack (that neighbor you just visited)
		//pick one of its unvisited neighbors, push it onto the stack, mark it as visited, and 
		//then continue to pick on one of its neighbors until you no longer have anything 
		//on the stack and nothing is unvisited
	
		List<Integer> vertices = new ArrayList<Integer>();
		Deque<Integer> toExplore = new ArrayDeque<Integer>();
		List<Integer> visited = new ArrayList<Integer>();
		
		//similar to breadth first except you put to explore last, not first and
		//one other change
		
		//initialize visited to all = 0
		//add v toExplore 'stack'
		//set v's visited to 1
		
		for(Integer i = 0; i < g.getNumVertices(); i++)
		{
			visited.add(0);
		}
		
	    toExplore.addLast(v);
		visited.set(v,1);
		
		while(toExplore.size() != 0)
		{
			Integer w = toExplore.removeLast();
			
			vertices.add(w);
			
			for(Integer neighbor: g.getAdjList(w).keySet())
			{
				
				if(visited.get(neighbor) == 0)
				{
					toExplore.addLast(neighbor);
					visited.set(neighbor, 1);
				}
			}
		}
		
		return vertices;
	}
	
	
	public List<Integer> BreadthFirstList(Integer v) 
	{
		List<Integer> vertices = new ArrayList<Integer>();
		Deque<Integer> toExplore = new ArrayDeque<Integer>();
		List<Integer> visited = new ArrayList<Integer>();
		
		
		for(Integer i = 0; i < g.getNumVertices(); i++)
		{
			visited.add(0);
		}
		
		toExplore.addLast(v);
		visited.set(v,1);
		
		while(toExplore.size() != 0)
		{
			Integer w = toExplore.removeFirst();
			vertices.add(w);
			
			for(Integer neighbor: g.getAdjList(w).keySet())
			{
				if(visited.get(neighbor) == 0)
				{
					toExplore.addLast(neighbor);
					visited.set(neighbor, 1);
				}
			}
		}
		return vertices;
	}

	
	public Graph DepthFirstSpanningTree(Integer v) 
	{
		Graph t = new Graph(g.getNumVertices()); 
		Deque<Integer> toExplore = new ArrayDeque<Integer>();
		List<Integer> visited = new ArrayList<Integer>();
		
		for(Integer i = 0; i < g.getNumVertices(); i++)
		{
			visited.add(0);	
		}
		
		toExplore.addLast(v);
		visited.set(v,1);
			
		while(toExplore.size() != 0)
		{
			Integer w = toExplore.removeLast();
			
			//vertices.add(w);
			
			for(Integer neighbor: g.getAdjList(w).keySet())
			{
				
				if(visited.get(neighbor) == 0)
				{
					t.addEdge(w, neighbor,1);
					toExplore.addLast(neighbor);
					visited.set(neighbor, 1);
				}
			}
		}
		
		return t;
	}
	
	
	public Graph BreadthFirstSpanningTree(Integer v) 
	{
		//first on first off, queue
		
		//visit the first node, mark it as visited, check each of it's neighbors that hasn't
		//been visited, mark them as visited, then add them to the queue
		//once you've seen all the neighbors of the current node, go the queue
		//and the first one on the queue is the next node that you start on 
		// and visit its neighbors etc.
		
		Graph t = new Graph(g.getNumVertices()); 
		Deque<Integer> toExplore = new ArrayDeque<Integer>();
		List<Integer> visited = new ArrayList<Integer>();
		
		for(Integer i = 0; i < g.getNumVertices(); i++)
		{
			//say that nothing has been visited
			visited.add(0);
		}
		
		//add the node you set out to search to the end of to explore
		toExplore.addLast(v);
		//say that you have visited v
		visited.set(v,1);
		
		//before the while loop, there is only one thing in the queue,
		//but we are about to put a bunch of things in it
		//puts the first one you are going to explore on the queue
		while(toExplore.size() != 0)
		{
			//set interger w as whatever is next up in the queue
			Integer w = toExplore.removeFirst();
			//add it to the graph
			//vertices.add(w);
			
			//this gets you all the neighbors of the node 'w' you are looking at
			for(Integer neighbor: g.getAdjList(w).keySet())
			{
				//if you haven't already visited that neighbor node before
				if(visited.get(neighbor) == 0)
				{
					//add it to the graph
					t.addEdge(w, neighbor,1);
					//add it to the front of the queue
					toExplore.addLast(neighbor);
					//set that node as visited
					visited.set(neighbor, 1);
				}
			}
		}
		
		return t;
	}

	List<String> DijkstrasShortestPath(Integer t)
	{
		List<Integer> initialized = new ArrayList<Integer>();
		List<Integer> pathWeights = new ArrayList<Integer>();
		List<String> prettyWeights = new ArrayList<String>();
		
		Set<Integer> vertexSet = new HashSet<Integer>();
		
		return prettyWeights;
	}
	
	public Graph PrimsAlgorithm(Integer v) 
	{
		Graph t = new Graph(g.getNumVertices()); 
		return t;
	}
	
	
}

