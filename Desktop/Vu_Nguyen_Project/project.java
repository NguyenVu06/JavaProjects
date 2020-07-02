package project_19;

import java.io.File;
import java.io.FileNotFoundException;
//import the needed classes
import java.util.*;

import net.datastructures.LinkedStack;
import net.datastructures.Stack;

public class project{
	
	//initialize variables needed:
	private static int [][] wdistance = new int[21][21];
	
    static final int V = 21; // number of Vertices, set at 21
    
    static final int Z = 20;//destination = Z lock at index 20
    
    // practice algorithm by the Dijkstra for fun
    // create a method to return the smallest distance for the Dijkstra 
    /*
    int minDistance(int dist[], Boolean visit[]) 
    { 
        // Initialize min value as a large integer
        int min = Integer.MAX_VALUE;
        
        //set the default index at Zero
        int min_index = 0; 
  
        for (int v = 0; v < V; v++) 
        	// if not visited and distance is less than the 'min' value
        	// update min to the distance at node and index the node
            if (visit[v] == false && dist[v] <= min) { 
                min = dist[v]; 
                min_index = v; 
            } 
        // return the position where the last shortest distance was found.
        return min_index; 
    } 
    


    // Dijkstra implementation using adjacency matrix for fun

    int[] DijkstraT (int graph[][]) {
    	
    	// initialize an array to store all visited vertices as FALSse, size = V = 21
    	Boolean visited [] = new Boolean[V];
    	//initialize an array to store distance from source to vertexes, size = V = 21
    	int dist [] = new int[V];
    	
    	for (int i = 0; i<V; i++) {
    		//fill visited array with FALSE
    		visited[i] = false;
    		//fill all distance with the max integer value
    		dist[i] = Integer.MAX_VALUE;
    	}
    	
    	//set default distance from destination to it self
    	dist[Z] = 0;
    	
    	//Loop thru all the vertices:
    	for(int i = 0; i<V-1; i++) {
    		//set the stored position of the lowest distance from destination
    		// the initial stored position will be the destination itself
    		int position = minDistance(dist, visited);
    		// add the destination to the  visited list
    		visited[position] = true;
    		
    		//get the shortest distance from the destination vertex by looping thru all the other vertices
    		//loop thru the input graph matrix by column using set row position
    		for(int j = 0; j<V ; j++) {
    			if(graph[position][j]+dist[position] < dist[j] // distance from j to current position plus distance from destination to current position
    															// is less than stored distance from j to destination
    					&& dist[position] != Integer.MAX_VALUE // distance from position to destination is less than infinite
    					&& visited[j] == false // position j has not been "visited"
    					&& graph[position][j] != 0) { // there is an edge from current position to j)
    						//update shortest distance from j to destination is position to destination + position to j
    						dist[j] = dist[position] + graph[position][j];
    				}
    		}
    	}
    	//return the array that stored the shortest distance from vertices to destination
		return dist;    	
    }
    */
    
    //Create a method to sort nodes into stacks by their direct distance to Z
    public static Stack<ddNode> sortStack(Stack<ddNode> input){
    	Stack<ddNode>tempStack = new LinkedStack<ddNode>();//temporary stack to store as stack is being sorted
    	while(!input.isEmpty()) { //while the input stack is not empty
    		ddNode tmp = input.pop(); //temp = the top of the input stack, pop out the top of the input stack
    		
    		while(!tempStack.isEmpty() && tempStack.top().value() < tmp.value()) {//while the temp stack is not empty and the top of the temp stack is less than temp (top of input stack)
    			input.push(tempStack.pop()); // input pop out the element from temp stack and put on top of the input stack
    		}
    		tempStack.push(tmp);//push temp value on top of the temp stack
    	}
    	
    	return tempStack;
    }
	
	//main method
	public static void main(String[] args) throws FileNotFoundException {
		// initiate scanner class and scan in text files:
		
		// text file 1: the matrix graph
		File txt = new File("/Users/nguyendvu/Downloads/metcs526_project/graph_input.txt");
		Scanner textIn = null; // initiate a scanner object to store the scan in value
		try {
			textIn = new Scanner(txt);
		} catch (FileNotFoundException e) { // Exception for if the file is not found.
			System.out.println("In valid Path, file not found!");
		}
		
		
		String header = textIn.nextLine();
		String a = header.replaceAll("\\s+", "");//replace all spaces
		// read the header from the text
		String[] headerArr = a.split("");
		textIn.close();
		
		Scanner textIn2 = new Scanner(txt);
		textIn2.nextLine();
		// read the rest of the file for all numbers in the adjacency matrix and store in the wdistance matrix
		int tempstore[] = new int [V*V];
		int tempIdx = 0;
		while (textIn2.hasNext()) {
			//store the read out from text file into a temp storage array
			if(textIn2.hasNextInt()) {
				tempstore[tempIdx] = textIn2.nextInt();
				tempIdx += 1;
			}else {
				textIn2.next();
			}
		}
		// put all the data from the temp storage array and put into matrix form.
		for(int i=0; i<V; i++) {
			for(int j=0; j<V; j++) {
				wdistance[i][j] = tempstore[(i)*(V)+j];
			}
		}
		textIn2.close();
		
		// text file 2: the direct distance file
		
		File txt2 = new File("/Users/nguyendvu/Downloads/metcs526_project/direct_distance.txt");
		Scanner text2In2 = null; // initiate a scanner object to store the scan in value
		try {
			text2In2 = new Scanner(txt2);
		} catch (FileNotFoundException e) { // Exception for if the file is not found.
			System.out.println("In valid Path, file not found!");
		}
		
		
		//read direct distance into an array of number distance with length = V
		int dirDist [] = new int[V];
		int temp2 = 0;
		while (text2In2.hasNext()) {
			//store the read out from text file into a temp storage array
			if(text2In2.hasNextInt()) {
				dirDist[temp2] = text2In2.nextInt();
				temp2 += 1;
			}else {
				text2In2.next();
			}
		}
		
		text2In2.close();
		
		//create a hash map to store Alphabet and direct distance.
		//since the text is already in alphabetical order, Key will use integers
		HashMap<Integer, Integer> dirDisMap = new HashMap<Integer, Integer>();
		for(int i=0; i <V; i++) {
			dirDisMap.put(i, dirDist[i]);
		}
		
		
		// Prompt User input:
		String startNode = "";
		
		Scanner userIn = new Scanner(System.in);
		System.out.print("Enter the start node: ");
		
		startNode = userIn.next();
		userIn.close();
    	System.out.println("User enters node " +  startNode + " as the start node\n");
    	
        // now create vertices (in alphabetical order) and store into a hash map numerical order. 
		// this map will be used to look up the start node and will be used for a reverse look up later
        HashMap<String, Integer> verts = new HashMap<String, Integer>();
        HashMap<Integer, String> revVerts = new HashMap<Integer, String>();
        int mapindex = 0;
        for(String i : headerArr) {
        	verts.put(i, mapindex);
        	revVerts.put(mapindex, i);
        	mapindex +=1;
        }

        int startInx = verts.get(startNode); //convert the alphabet input to the integer representation of the start node

        // test the input using the Dijkstra algorithm
        //project test = new project();
       	//int[] DijkstraDistance = test.DijkstraT(wdistance);
       	
       	
       	// create data structure to store nodes and edges:
       	// 2 stacks will be used to document the nodes in the paths
       	Stack<Integer> path = new LinkedStack<>(); //stack to store visited Node. This the shortest path Algorithm1
    	Stack<Integer> seqPath = new LinkedStack<>(); //stack to store visited Node. This the sequence of path Algorithm1
    	
       	Stack<Integer> path2 = new LinkedStack<>(); //stack to store visited Node. This the shortest path Algorithm2
    	Stack<Integer> seqPath2 = new LinkedStack<>(); //stack to store visited Node. This the sequence of path Algorithm2
    	
    	// create 2 maps of stack: 
    	//Each node will come with a stack adjacent nodes order by of direct distance - Algorithm1
    	HashMap<Integer, Stack<ddNode>> stackOnStack = new HashMap<Integer, Stack<ddNode>>();
    	//Each node will come with a stack adjacent nodes order by of direct distance plus the edge length - Algorithm2
    	HashMap<Integer, Stack<ddNode>> stackOnStack2 = new HashMap<Integer, Stack<ddNode>>();
    	
    	//create a loop to create a stack of direct distance node pair that connected to each node
    	// stack on stack will have a map where key is the node and values is a stack of its adjacent nodes sorted by direct distance
    	for(int i = 0; i<V; i++) {
        	Stack<ddNode> nodeStack = new LinkedStack<>();//stack to store nodes connected to each node Algorithm1
        	Stack<ddNode> nodeStack2 = new LinkedStack<>();//stack to store nodes connected to each node Algorithm2
    		for(int j = 0; j<V; j++) {
        		if(wdistance[i][j]!=0) {
        			ddNode x = new ddNode(j, dirDisMap.get(j)); //node for the Algorithm1
        			ddNode x2 = new ddNode(j, dirDisMap.get(j) + wdistance[i][j]); //node for the Algorithm2
        			nodeStack.push(x);
        			nodeStack2.push(x2);
        		}
        	}
    		Stack<ddNode> sortedNodeStack = sortStack(nodeStack); //Algorithm1
    		Stack<ddNode> sortedNodeStack2 = sortStack(nodeStack2); //Algorithm2
    		
    		stackOnStack.put(i, sortedNodeStack);// add the stack of adjacent nodes to its corresponding node Algorithm1
    		stackOnStack2.put(i, sortedNodeStack2);// add the stack of adjacent nodes to its corresponding node Algorithm2
    	}
    	

    	
    	// Algorithm 1: 
    	
    	//initiate the initial position at the start node
    	int pos, pos2; // 1 for each algorithm
    	pos = pos2 = startInx;
    	
    	path.push(pos);
    	seqPath.push(pos);
    	while(pos != Z) { //while position pointer is not Z
    		ddNode tmpN = stackOnStack.get(pos).pop(); //remove the node with the lowest distance at Pos (Pos is initiated at the start node
    		path.push(tmpN.key());//push the node with the lowest distance to path.
    		seqPath.push(tmpN.key());
    		pos = path.top();//update position to the position 

    		
    		if(stackOnStack.get(pos).size() <= 1) {// if the new position only has 1 adjacent node + dead end
    			seqPath.push(stackOnStack.get(pos).top().key());
    			path.pop();// remove node from path
    			pos = path.top(); // back track position to the top of the path stack
    		}
    	}
    	
    	
    	int shortestPathLen = 0;
    	String nodes [] = new String[path.size()];
    	int m = 0;
    	while(path.size()>1) {
    		int tempdist = path.pop();
    		nodes[m] = revVerts.get(tempdist);
    		shortestPathLen = shortestPathLen + wdistance[path.top()][tempdist];
    		m++;
    	}
    	nodes[nodes.length - 1] = revVerts.get(path.pop());
    	
    	System.out.println("\n Algorithm 1: ");
    	System.out.print("\t Sequence of all nodes: ");
    	String allnodes [] = new String[seqPath.size()];
    	int n = 0;
    	while (!seqPath.isEmpty()) {
    		allnodes[n] = revVerts.get(seqPath.pop());
    		n++;
    	}
    	System.out.print(allnodes[allnodes.length-1]);
    	for(int i = allnodes.length-2; i >= 0; i--) { 
    		System.out.print("->");
    		System.out.print(allnodes[i]);
    	}
    	
    	System.out.print("\n\t Shortest path: ");
    	System.out.print(nodes[nodes.length-1]);
    	for(int i = nodes.length-2; i>=0; i--) {
    		System.out.print("->");
    		System.out.print(nodes[i]);
    		
    	}
    	System.out.print("\n\t Shortest path length: ");
    	System.out.print(shortestPathLen);
    	System.out.println(" ");
    	//------------------------------------------------------------------------------------------------------------------------------------
    	// Algorithm 2: 
    	//initiate the initial position at the start node
    	
    	path2.push(pos2);
    	seqPath2.push(pos2);

    	while(pos2 != Z) { //while position pointer is not Z
    		ddNode tmpN = stackOnStack2.get(pos2).pop(); //remove the node with the lowest distance at Pos (Pos is initiated at the start node
    		path2.push(tmpN.key());//push the node with the lowest distance to path.
    		seqPath2.push(tmpN.key());
    		pos2 = path2.top();//update position to the position 

    		
    		if(stackOnStack2.get(pos2).size() <= 1) {// if the new position only has 1 adjacent node + dead end
    			seqPath2.push(stackOnStack2.get(pos2).top().key());
    			path2.pop();// remove node from path
    			pos2 = path2.top(); // back track position to the top of the path stack
    		}

    	}
    	
    	int shortestPathLen2 = 0;
    	String nodes2 [] = new String[path2.size()];
    	int p = 0;
    	while(path2.size()>1) {
    		int tempdist = path2.pop();
    		nodes2[p] = revVerts.get(tempdist);
    		shortestPathLen2 = shortestPathLen2 + wdistance[path2.top()][tempdist];
    		p++;
    		
    	}
    	nodes2[nodes2.length - 1] = revVerts.get(path2.pop());
    	
    	System.out.println("\n Algorithm 2: ");
    	System.out.print("\t Sequence of all nodes: ");

    	String allnodes2 [] = new String[seqPath2.size()];
    	int n2 = 0;
    	while (!seqPath2.isEmpty()) {
    		allnodes2[n2] = revVerts.get(seqPath2.pop());
    		n2++;
    	}
    	System.out.print(allnodes2[allnodes2.length-1]);
    	for(int i = allnodes2.length-2; i>=0; i--) {
    		System.out.print("->");
    		System.out.print(allnodes2[i]);
    		
    	}
    	
    	System.out.print("\n\t Shortest path: ");
    	System.out.print(nodes2[nodes2.length-1]);
    	for(int i = nodes2.length-2; i>=0; i--) {
    		System.out.print("->");
    		System.out.print(nodes2[i]);
    		
    	}
    	
    	System.out.print("\n\t Shortest path length: ");
    	System.out.print(shortestPathLen2);
    	
   
	}
}