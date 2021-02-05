#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <unordered_set>
#include <unordered_map>
#include <set>
#include <array>
#include <list>
#include <forward_list>
#include <deque>
#include <map>
#include <cstddef>
#include <string>
#include <utility>
#include <algorithm>
#include <limits>
#include <optional>
#include <exception>
#include <stdexcept>

#include "directed_graph.hpp"
using namespace std; 
/*
 * Computes whether the input is a Directed Acyclic Graph (DAG).
 * A digraph is a DAG if there is no vertex that has a cycle.
 * A cycle is a non-empty set of [out-]edges that starts at one 
 * vertex, and returns to it.
 */
template <typename vertex>
bool is_dag(const directed_graph<vertex> & d) 
{
	int numVertices = d.num_vertices();
	vector<vertex> verticesVect(d.begin(),d.end());
	//Call Member functions to get the in degree for all vertices 
    map <vertex , int > inDegreeMap;
	//inDegreeMap stores the in_degree corresponds to each vertex 
	for (int i=0;i<verticesVect.size();i++) 
	{
		inDegreeMap.insert(pair<vertex,int>(verticesVect[i],d.in_degree(verticesVect[i])));
    } 
   //Push the zero in_degree vertices into a queue 
    queue < vertex >  zeroInDegreeQueue; 
	typename std::map<vertex,int>::const_iterator it;
	for (it=inDegreeMap.begin(); it!=inDegreeMap.end();it++) 
	{
		if(inDegreeMap[it->first]==0)
			zeroInDegreeQueue.push(it->first); 
	}
		
	
	int visitiedNum = 0; 
	vector<vertex> top_order; 
  
    
	while (!zeroInDegreeQueue.empty()) 
	{ 
  
        
		vertex u = zeroInDegreeQueue.front(); 
        zeroInDegreeQueue.pop(); 
        top_order.push_back(u); 
  
		//Get the adjacents 
		vector<vertex> neighbors(d.nbegin(u),d.nend(u));
		for (it=inDegreeMap.begin(); it!=inDegreeMap.end();it++) 
		{
			for(int i=0;i<neighbors.size();i++)
				if( it->first ==neighbors[i])
				{
					inDegreeMap[it->first]--;
					if(inDegreeMap[it->first]==0)
						zeroInDegreeQueue.push(it->first); 
					
				}
		}
  
        visitiedNum++; //Increase Visitied Vertices Count 
    } 
  
    
	if (visitiedNum != numVertices)  
        return false; 
    else
        return true;
	
	
	
}

/*
 * Computes a topological ordering of the vertices.
 * For every vertex u in the order, and any of its
 * neighbours v, v appears later in the order than u.
 */
template <typename vertex>
std::list<vertex> topological_sort(const directed_graph<vertex> & d) 
{
	std::list<vertex> topologicalSortList;
	//The graph must be a dag 
	if(!is_dag(d))
		return topologicalSortList;
	  
	int numVertices = d.num_vertices();
	vector<vertex> verticesVect(d.begin(),d.end());
	//Call Member functions to get the in degree for all vertices 
    map <vertex , int > inDegreeMap;
	//inDegreeMap stores the in_degree corresponds to each vertex 
	for (int i=0;i<verticesVect.size();i++) 
	{
		inDegreeMap.insert(pair<vertex,int>(verticesVect[i],d.in_degree(verticesVect[i])));
    } 
   //Push the zero in_degree vertices into a queue 
    queue < vertex >  zeroInDegreeQueue; 
	typename std::map<vertex,int>::const_iterator it;
	for (it=inDegreeMap.begin(); it!=inDegreeMap.end();it++) 
	{
		if(inDegreeMap[it->first]==0)
			zeroInDegreeQueue.push(it->first); 
	}
		
 
	while (!zeroInDegreeQueue.empty()) 
	{ 
  
        
		vertex u = zeroInDegreeQueue.front(); 
        zeroInDegreeQueue.pop(); 
        topologicalSortList.push_back(u); 
  
		//Get the adjacents 
		vector<vertex> neighbors(d.nbegin(u),d.nend(u));
		for (it=inDegreeMap.begin(); it!=inDegreeMap.end();it++) 
		{
			for(int i=0;i<neighbors.size();i++)
				if( it->first ==neighbors[i])
				{
					inDegreeMap[it->first]--;
					if(inDegreeMap[it->first]==0)
						zeroInDegreeQueue.push(it->first); 
					
				}
		}

	}  
    
	return topologicalSortList;
	
}

/*
 * Given a DAG, computes whether there is a Hamiltonian path.
 * a Hamiltonian path is a path that visits every vertex
 * exactly once.
 */
template <typename vertex>
bool is_hamiltonian_dag(const directed_graph<vertex> & d) 
{
	//Get the topological Sort List of the graph 
	int numVertices = d.num_vertices();
	if(numVertices==0)
		return true;
	list<vertex> topSorList = topological_sort<vertex>(d);
	typename list<vertex> :: iterator it; 
	vector<vertex> vertices ; 
	if(	topSorList.size()==0)
		return false ; //Not a DAG 
	
	for(it = topSorList.begin(); it != topSorList.end(); ++it) 
		vertices.push_back(*it);
	for(int i=0;i<vertices.size()-1;i++)
		if(!d.adjacent(vertices[i],vertices[i+1]))
			return false ;		
  return true;
}

/*
 * Do Depth first search while updating visitied vector and return vector of DFS as paramter - recrsive version 
 *
 */
template <typename vertex>
void do_dfs(const directed_graph<vertex> & d , vertex vert, map <vertex , bool > & visited,vector<vertex> & weakComponent) 
{ 
	/////////////////////////////////
	visited[vert] = true; 
	//cout<<"\t"<<vert<<" ";
	weakComponent.push_back(vert);
    vector<vertex> adjacents(d.nbegin(vert),d.nend(vert)); 
    for(int i = 0; i <adjacents.size(); ++i) 
        if(!visited[adjacents[i]]) 
            do_dfs(d,adjacents[i], visited,weakComponent); 
	/////////////////////////////////		
}


/*
 * Fill the stack according to the finishing time of vertices DFS 
 *
 */
template <typename vertex>
void fill_finish_Time(const directed_graph<vertex> & d , vertex vert, map <vertex , bool > & visited, stack<vertex> &Stack) 
{ 
    /////////////////////////////////
    visited[vert] = true;
	vector<vertex> adjacents(d.nbegin(vert),d.nend(vert)); 
    for(int i = 0; i <adjacents.size(); i++) 
        if(!visited[adjacents[i]]) 
            fill_finish_Time(d,adjacents[i], visited, Stack); 
	//Fill the vertex in the stack now 
    Stack.push(vert); 
} 

/*
 * reverse the directed graph 
 *
 */
template <typename vertex>
directed_graph<vertex> reverseTheGraph(const directed_graph<vertex> & d )
{ 
	directed_graph <vertex>g ; 
	//Get all vertices in the graph d 
	vector<vertex> verticesVect(d.begin(),d.end());
	//Add same vertices to the graph g 
	for (int i=0; i<verticesVect.size() ; i++)
		g.add_vertex(verticesVect[i]);
	//Add adjacents of each vertex 
    for (int i=0; i<verticesVect.size() ; i++)
    { 
		vector<vertex> adjVect(d.nbegin(verticesVect[i]),d.nend(verticesVect[i]));
 
        for(int j = 0; j!=adjVect.size();j++) 
        { 
            g.add_edge(adjVect[j],verticesVect[i]); 
        } 
    } 
    return g; 
} 
	
/*
 * Computes the weakly connected components of the graph.
 * A [weak] component is the smallest subset of the vertices
 * such that the in and out neighbourhood of each vertex in
 * the set is also contained in the set.
 */
template <typename vertex>
std::vector<std::vector<vertex>> components(const directed_graph<vertex> & d) 
{
	
	//Prepare vector of vector of vertices to hold the weak components 
	vector<std::vector<vertex>>  weakComponents;
	//Get all vertices in the graph 
	vector<vertex> verticesVect(d.begin(),d.end());
	

	//Make map of visitied maps vertex to boolean = True if vistied otherwise equals false 
	map <vertex , bool > visited;
	
	//Iniliaze the map with keys = vertices and values = false -not any vertex is visited yet 
    for(int i = 0;i<verticesVect.size();i++) 
		visited.insert(pair<vertex,bool>(verticesVect[i],false));      
  
	//Convert the directed to un-directed graph 
		directed_graph <vertex>g ; 
	//Add same vertices to the graph g 
	for (int i=0; i<verticesVect.size() ; i++)
		g.add_vertex(verticesVect[i]);
	
	for (int i=0; i<verticesVect.size() ; i++)
    { 
		vector<vertex> adjVect(d.nbegin(verticesVect[i]),d.nend(verticesVect[i]));
 
        for(int j = 0; j!=adjVect.size();j++) 
        { 
            g.add_edge(adjVect[j],verticesVect[i]); 
            g.add_edge(verticesVect[i],adjVect[j]); 

        } 
    } 
	
    for(int i = 0;i<verticesVect.size();i++) 
    { 
        if (visited[verticesVect[i]] == false) 
        { 
			//cout<<"DFS for "<<verticesVect[i] <<endl;
			vector<vertex> weakComponent;
			//cout<<"Current Vertex "<<verticesVect[i]<<endl;
			do_dfs(g,verticesVect[i], visited,weakComponent);   
			weakComponents.push_back(weakComponent);
            
        } 
    } 
	
	
	

	
	
	
  return weakComponents;
}

/*
 * Computes the strongly connected components of the graph.
 * A strongly connected component is a subset of the vertices
 * such that for every pair u, v of vertices in the subset,
 * v is reachable from u and u is reachable from v.
 */

template <typename vertex>
std::vector<std::vector<vertex>> strongly_connected_components(const directed_graph<vertex> & d) 
{
	
	stack<vertex> Stack; 
  
	//Prepare vector of vector of vertices to hold the strong components 
	vector<std::vector<vertex>>  strongComponents;
	//Get all vertices in the graph 
	vector<vertex> verticesVect(d.begin(),d.end());
	

	//Make map of visitied maps vertex to boolean = True if vistied otherwise equals false 
	map <vertex , bool > visited;
	
	//Iniliaze the map with keys = vertices and values = false -not any vertex is visited yet 
    for(int i = 0;i<verticesVect.size();i++) 
		visited.insert(pair<vertex,bool>(verticesVect[i],false));      
  
	//Apply Kosaraju's  algorithms which needs to fill stack according to the finishing time 
    for(int i = 0;i<verticesVect.size();i++) 
        if(visited[verticesVect[i]] == false) 
            fill_finish_Time<vertex>(d ,verticesVect[i], visited, Stack); 
  
    //Obtain the reversed directed graph 
    directed_graph<vertex>  g = reverseTheGraph(d); 
  
	//Re-init the visitied 
	//Make map of visitied maps vertex to boolean = True if vistied otherwise equals false 
	map <vertex , bool > visited2;
	
	//Iniliaze the map with keys = vertices and values = false -not any vertex is visited yet 
    for(int i = 0;i<verticesVect.size();i++) 
		visited2.insert(pair<vertex,bool>(verticesVect[i],false));      
	//Now process vertices according to their finishing time 
	while (!Stack.empty()) 
    { 
         
        vertex vert = Stack.top(); 
        Stack.pop(); 
  
        //If not visitied then get the strongly connected components  
        if (visited2[vert] == false) 
        { 
			vector<vertex> strongComponent;
			do_dfs(g,vert, visited2,strongComponent);   
			strongComponents.push_back(strongComponent);
 
        } 
    } 
	
	
	
  return strongComponents;
}



/*
 * Computes the shortest distance from u to every other vertex
 * in the graph d. The shortest distance is the smallest number
 * of edges in any path from u to the other vertex.
 * If there is no path from u to a vertex, set the distance to
 * be the number of vertices in d plus 1.
 */
template <typename vertex>
std::unordered_map<vertex, std::size_t> shortest_distances(const directed_graph<vertex> & d, const vertex & u) 
{
	 
	
	//Map of the distance - map each vertex to an integer distance from the source vertex u 
	 typename std::unordered_map <vertex , std::size_t > dist;
	//Get all vertices in the graph 
	vector<vertex> verticesVect(d.begin(),d.end());
	int numVertices = verticesVect.size();
	
	//Shortest path tree map 
	map <vertex , bool > shortestPathTreeVec;
    for(int i = 0;i<verticesVect.size();i++) 
		shortestPathTreeVec.insert(pair<vertex,bool>(verticesVect[i],false)); 	
	

     //All distances = infinity at first  
     for (int i = 0; i < verticesVect.size(); i++) 
     {
		 dist[verticesVect[i]] = numVertices+1;
	 }
	 
     //Distance from source vertex u to itself = 0 
	 dist[u] = 0; 
   
     // Find shortest path for all vertices 
     for (int c = 0; c < verticesVect.size()-1; c++) 
     { 
       
	   int min = numVertices+1;
	   vertex   min_vert; 
   
	   for (int i = 0; i < verticesVect.size(); i++) 
			if (shortestPathTreeVec[verticesVect[i]] == false && dist[verticesVect[i]] <= min) 
			{
				min = dist[verticesVect[i]]; 
				min_vert = verticesVect[i]; 
			}
	 
	   vertex u = min_vert; 
   
       
	   shortestPathTreeVec[u] = true; 
   
       
	   for (int i = 0; i < verticesVect.size(); i++)  
		   if (!shortestPathTreeVec[verticesVect[i]] && d.adjacent(u,verticesVect[i]) && dist[u] != numVertices+1 )
			   if(dist[u]+1 < dist[verticesVect[i]])
				dist[verticesVect[i]] = dist[u] + 1; 
			
     } 
	 
	
	
	
	return dist;
}

