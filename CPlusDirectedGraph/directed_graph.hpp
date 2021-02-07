#ifndef DIRECTED_GRAPH_H
#define DIRECTED_GRAPH_H

//A large selection of data structures from the standard
//library. You need not feel compelled to use them all,
//but as you can't add any, they're all here just in case.
#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <iostream>
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
using namespace std;
//Forward declarations for classes below so they can be used below without worrying too much about the ordering.
template <typename vertex> class vertex_iterator;
template <typename vertex> class neighbour_iterator;
template <typename vertex> class directed_graph;


template <typename vertex>
class directed_graph {

private:

  //You will need to add some data members here
  //to actually represent the graph internally,
  //and keep track of whatever you need to.
  
  //The graph will be represented using Adjacency List 
  map <vertex , vector<vertex> > graph;
  int numVertices; //Number of vertices 
  int numEdges;

public:


  directed_graph(); //A constructor for directed_graph. The graph should start empty.
  ~directed_graph(); //A destructor. Depending on how you do things, this may
  //not be necessary.
  
  bool contains(const vertex&) const; //Returns true if the given vertex is in the graph, false otherwise.

  bool adjacent(const vertex&, const vertex&) const; //Returns true if the first vertex is adjacent to the second, false otherwise.

  void add_vertex(const vertex&); //Adds the passed in vertex to the graph (with no edges).
  void add_edge(const vertex&, const vertex&); //Adds an edge from the first vertex to the second.

  void remove_vertex(const vertex&); //Removes the given vertex. Should also clear any incident edges.
  void remove_edge(const vertex&, const vertex&); //Removes the edge between the two vertices, if it exists.

  std::size_t in_degree(const vertex&) const; //Returns number of edges coming in to a vertex.
  std::size_t out_degree(const vertex&) const; //Returns the number of edges leaving a vertex.
  std::size_t degree(const vertex&) const; //Returns the degree of the vertex (both in and out edges).
  
  std::size_t num_vertices() const; //Returns the total number of vertices in the graph.
  std::size_t num_edges() const; //Returns the total number of edges in the graph.

  std::vector<vertex> get_vertices() const; //Returns a vector containing all the vertices.
  std::vector<vertex> get_neighbours(const vertex&) const; //Returns a vector containing the neighbours of the given vertex.

  vertex_iterator<vertex> begin(); //Returns a graph_iterator pointing to the start of the vertex set.
  vertex_iterator<vertex> end(); //Returns a graph_iterator pointing to one-past-the-end of the vertex set.

  neighbour_iterator<vertex> nbegin(const vertex&); //Returns a neighbour_iterator pointing to the start of the neighbour set for the given vertex.
  neighbour_iterator<vertex> nend(const vertex&); //Returns a neighbour_iterator pointing to one-past-the-end of the neighbour set for the given vertex.

  std::vector<vertex> depth_first(const vertex&); //Returns the vertices of the graph in the order they are visited in by a depth-first traversal starting at the given vertex.
  std::vector<vertex> breadth_first(const vertex&); //Returns the vertices of the graph in the order they are visisted in by a breadth-first traversal starting at the given vertex.

  directed_graph<vertex> out_tree(const vertex&); //Returns a spanning tree of the graph starting at the given vertex using the out-edges.
  directed_graph<vertex> in_tree(const vertex&); //Returns a spanning tree of the graph starting at the given vertex using the in-edges.

  bool reachable(const vertex&, const vertex&) ; //Returns true if the second vertex is reachable from the first (can you follow a path of out-edges to get from the first to the second?). Returns false otherwise.

};

//The vertex_iterator class provides an iterator
//over the vertices of the graph.
//This is one of the harder parts, so if you're
//not too comfortable with C++ leave this for last.
//If you are, there are many ways of doing this,
//as long as it passes the tests, it's okay.
//You may want to watch the videos on iterators before starting.
template <typename vertex>
class vertex_iterator {

private:

	vector<vertex> verticesVector;
	int       totSize;
	vertex    currentPosition;
	
 

public:
  vertex_iterator(const vertex_iterator<vertex>&);
  vertex_iterator(const directed_graph<vertex>&, std::size_t);
  ~vertex_iterator();
  vertex_iterator<vertex> operator=(const vertex_iterator<vertex>&);
  bool operator==(const vertex_iterator<vertex>&) const;
  bool operator!=(const vertex_iterator<vertex>&) const;
  vertex_iterator<vertex> operator++();
  vertex_iterator<vertex> operator++(int);
  vertex operator*();
  vertex* operator->();
};

//The neighbour_iterator class provides an iterator
//over the neighbours of a given vertex. This is
//probably harder (conceptually) than the graph_iterator.
//Unless you know how iterators work.
template <typename vertex>
class neighbour_iterator {

private:

	vector<vertex> vertexNeighborsVector;
	int       totSize;
	vertex    currentPosition;

public:
  neighbour_iterator(const neighbour_iterator<vertex>&);
  neighbour_iterator(const directed_graph<vertex>&, const vertex&, std::size_t);
  ~neighbour_iterator();
  neighbour_iterator<vertex> operator=(const neighbour_iterator<vertex>&);
  bool operator==(const neighbour_iterator<vertex>&) const;
  bool operator!=(const neighbour_iterator<vertex>&) const;
  neighbour_iterator<vertex> operator++();
  neighbour_iterator<vertex> operator++(int);			
  vertex operator*();
  vertex* operator->();
};


//Define all your methods down here (or move them up into the header, but be careful you don't double up). If you want to move this into another file, you can, but you should #include the file here.
//Although these are just the same names copied from above, you may find a few more clues in the full
//method headers. Note also that C++ is sensitive to the order you declare and define things in - you
//have to have it available before you use it.

template <typename vertex> directed_graph<vertex>::directed_graph() 
{
	graph.clear();
	numVertices = 0;
	numEdges    = 0;
	
}

template <typename vertex> directed_graph<vertex>::~directed_graph() 
{
	graph.clear();
	numVertices = 0;
	numEdges    = 0;	
}

template <typename vertex> bool directed_graph<vertex>::contains(const vertex& u) const 
{ 
	typename map<vertex, vector<vertex>>::const_iterator itr; 
             
 	for(itr = graph.begin(); itr != graph.end(); itr++) 
		if(itr->first == u)
			return true;
		
	return false; 
}

template <typename vertex> bool directed_graph<vertex>::adjacent(const vertex& u, const vertex& v) const 
{ 

	typename  vector<vertex> ::const_iterator it2;
	
	if(this->contains(u))
	{
		for (it2 = (graph.find(u)->second).begin() ; it2 != (graph.find(u)->second).end(); it2++)
		{
				 
			if(*it2==v)
				return true;
		}
		
	}
	
	return false; 
}

template <typename vertex> void directed_graph<vertex>::add_vertex(const vertex& u) 
{
	//don't add duplicates 
	if(!this->contains(u))
	{
		//Insert a new vertex 
		vector <vertex> vertList ;//Corresponding vector of conencted vertices "edges" is empty 
		vertList.clear();	
		graph.insert(pair<vertex, vector<vertex>>(u,vertList));
		numVertices++;
	}
}

template <typename vertex> void directed_graph<vertex>::add_edge(const vertex& u, const vertex& v) 
{
	//Confirm first that both u and v are contained in teh graph 
	if(this->contains(u) && this->contains(v))
	{
		graph[u].push_back(v);
		//dont allow duplicate edges 
		numEdges++;
	}
}

template <typename vertex> void directed_graph<vertex>::remove_vertex(const vertex& u) 
{
	//Confirm that vertex u exists 
	if(this->contains(u))
	{
		//Declare iterator over the graph  
		typename  std::map<vertex,vector<vertex>>::const_iterator it;
	 
		
		//First remove the vertex from the list of vertices (i.e. keys) from the graph map 	
		it=graph.find(u);   
		graph.erase (it);                
		
		//Then remove every existence of edge between the removed vertex and any other vertex 
		for (it=graph.begin(); it!=graph.end();it++)//Iterate over the remaining vertices 
			for ( unsigned int i =0; i<graph[it->first].size();i++)//Iterate for the corresponding list of connected vertices 
				if(graph[it->first][i]==u)
					graph[it->first].erase(graph[it->first].begin()+i);
				
		numVertices--;
	}
}
template <typename vertex> void directed_graph<vertex>::remove_edge(const vertex& u, const vertex& v) 
{
	//Confirm first that both u and v are contained in teh graph 
	if(this->contains(u) && this->contains(v))
	{
		for ( unsigned int i =0; i<graph[u].size();i++)
			if(graph[u][i]==v)
			{
				graph[u].erase(graph[u].begin()+i);
				numEdges--;
			}
	}
}
template <typename vertex> std::size_t directed_graph<vertex>::in_degree(const vertex& u) const 
{ 
	//Find all number of occurrences of u inside all vertices lists 
	int inDegree = 0;
	typename std::map<vertex,vector<vertex>>::const_iterator it;
	typename  vector<vertex> ::const_iterator it2;

	for (it=graph.begin(); it!=graph.end();it++)//Iterate over the all vertices except u
	{
		 
		if(it->first!=u)
		{
					 	
			//Iterate for the corresponding list of connected vertices 
				for (it2 = (graph.find(it->first)->second).begin() ; it2 != (graph.find(it->first)->second).end(); it2++)
				{
					 
					if(*it2==u)
						inDegree++;
				}
				 
		}
	}
	 
	return inDegree; 
}

template <typename vertex> std::size_t directed_graph<vertex>::out_degree(const vertex& u) const 
{ 
	int outDegree; 
	typename std::map<vertex,vector<vertex>>::const_iterator it;
	outDegree = (graph.find(u)->second).size();
	return outDegree; 
}

template <typename vertex> std::size_t directed_graph<vertex>::degree(const vertex& u) const 
{ 
	return (this->in_degree(u)+this->out_degree(u)); 
}

template <typename vertex> std::size_t directed_graph<vertex>::num_vertices() const 
{ 
	return numVertices; 
}
template <typename vertex> std::size_t directed_graph<vertex>::num_edges() const 
{ 
	return numEdges; 
}

template <typename vertex> std::vector<vertex> directed_graph<vertex>::get_vertices()const
{ 
	typename std::map<vertex,vector<vertex>>::const_iterator it;
	vector<vertex> verticesVect;

	for (it=graph.begin(); it!=graph.end();it++)//Iterate over the all vertices 
	{
		verticesVect.push_back(it->first);		
	}
	
	return verticesVect; 
}


template <typename vertex> std::vector<vertex> directed_graph<vertex>::get_neighbours(const vertex& u) const
{ 
	//They are the vertices pointe by the current vertex
	typename  vector<vertex> ::const_iterator it2;
	vector<vertex> verticesVect ;
	if(this->contains(u))
	{
		for (it2 = (graph.find(u)->second).begin() ; it2 != (graph.find(u)->second).end(); it2++)
		{
					 
					verticesVect.push_back(*it2);	
		}
	}
		
	return verticesVect; 
}


template <typename vertex> vertex_iterator<vertex> directed_graph<vertex>::begin() 
{ 
	vertex_iterator<vertex>   vertIterator (*this,numVertices);
	
	return vertIterator; 
}

template <typename vertex> vertex_iterator<vertex> directed_graph<vertex>::end() 
{ 
	vertex_iterator<vertex>   vertIterator (*this,numVertices);
	for(int i=0;i<numVertices;i++)
		vertIterator++;

	return vertIterator; 
}

template <typename vertex> neighbour_iterator<vertex> directed_graph<vertex>::nbegin(const vertex& u) 
{ 
	neighbour_iterator<vertex>   vertNeighborsIterator (*this,u,this->get_neighbours(u).size());
	
	return vertNeighborsIterator; 
		
}
template <typename vertex> neighbour_iterator<vertex> directed_graph<vertex>::nend(const vertex& u) 
{
	neighbour_iterator<vertex>   vertNeighborsIterator (*this,u,this->get_neighbours(u).size());
	
	for(unsigned int i=0;i<this->get_neighbours(u).size();i++)
		vertNeighborsIterator++;
	
	return vertNeighborsIterator; 
}

template <typename vertex> std::vector<vertex> directed_graph<vertex>::depth_first(const vertex& u) 
{ 

	vector<vertex>  dfsVector;
	bool *visited = new bool[this->num_vertices()]; 
	//Initialize all visited vertices to 0 
    for (unsigned int i =0; i < this->num_vertices(); i++) 
        visited[i] = false; 

	//Push the start node into the stack 
    stack<int> dfsStack; 
    dfsStack.push(u); 
	vertex elem;
    while (!dfsStack.empty()) 
    { 
         
        elem = dfsStack.top(); 
        dfsStack.pop(); 
		//Check if poped element is not vistied vefore 
        if (!visited[elem]) 
        { 
            dfsVector.push_back(elem);
            visited[elem] = true; 
        } 
  
        
		//In all cases get the adjacents of the current vertex 
		vector<vertex> neighbours = this->get_neighbours(elem);
		
        for (signed int i =neighbours.size()-1; i >=0; i--) 
        {
			 
			if (!visited[neighbours[i]]) 
                dfsStack.push(neighbours[i]); 
			
		}
    } 
	
	return	dfsVector; 
}

template <typename vertex> std::vector<vertex> directed_graph<vertex>::breadth_first(const vertex& u) 
{ 
 
	vector<vertex>  bfsVector;
	bool *visited = new bool[this->num_vertices()]; 
	//Initialize all visited vertices to 0 
    for (unsigned int i =0; i < this->num_vertices(); i++) 
        visited[i] = false; 

	//Push the start node into the stack 
    list<int>  bfsQueue;
	 
    bfsQueue.push_back(u);
	visited[u]=true;
	
	vertex elem;
    while (!bfsQueue.empty()) 
    { 
         
        elem = bfsQueue.front(); 
        bfsQueue.pop_front(); 
		bfsVector.push_back(elem);
		 
		//In all cases get the adjacents of the current vertex 
		vector<vertex> neighbours = this->get_neighbours(elem);
		
        for (unsigned int i =0; i <neighbours.size(); i++) 
            if (!visited[neighbours[i]]) 
			{
				visited[neighbours[i]]=true;
				bfsQueue.push_back(neighbours[i]); 
				
			}
    } 
	
	return	bfsVector; 
 }

template <typename vertex> directed_graph<vertex> directed_graph<vertex>::out_tree(const vertex& u) 
{
	directed_graph<vertex> outTree;
	//////////////////////
	//outTree will be constructed through applying BFS 
	 
	bool *visited = new bool[this->num_vertices()]; 
	//Initialize all visited vertices to 0 
    for (unsigned int i =0; i < this->num_vertices(); i++) 
        visited[i] = false; 

	//Push the start node into the stack 
    list<int>  bfsQueue;
	 
    bfsQueue.push_back(u);
	visited[u]=true;
	outTree.add_vertex(u);
	
	vertex elem;
    while (!bfsQueue.empty()) 
    { 
         
        elem = bfsQueue.front(); 
        bfsQueue.pop_front(); 
		 
		 
		//In all cases get the adjacents of the current vertex 
		vector<vertex> neighbours = this->get_neighbours(elem);
		
        for (unsigned int i =0; i <neighbours.size(); i++) 
            if (!visited[neighbours[i]]) 
			{
				visited[neighbours[i]]=true;
				outTree.add_vertex(neighbours[i]);
				outTree.add_edge(elem,neighbours[i]);
				bfsQueue.push_back(neighbours[i]); 
				
			}
    } 


		
	return outTree; 
}

template <typename vertex> directed_graph<vertex> directed_graph<vertex>::in_tree(const vertex& u) 
{ 
	directed_graph<vertex> inTree;
	//////////////////////
	//inTree will be constructed through applying BFS 
	 
	bool *visited = new bool[this->num_vertices()]; 
	//Initialize all visited vertices to 0 
    for (unsigned int i =0; i < this->num_vertices(); i++) 
        visited[i] = false; 

	//Push the start node into the stack 
    list<int>  bfsQueue;
	 
    bfsQueue.push_back(u);
	visited[u]=true;
	inTree.add_vertex(u);
	
	vertex elem;
    while (!bfsQueue.empty()) 
    { 
         
        elem = bfsQueue.front(); 
        bfsQueue.pop_front(); 
		 
		 
		//In all cases get the adjacents of the current vertex
		//Get the backneighbors		
		vector<vertex> backneighbours;
		backneighbours.clear();	 
		typename std::map<vertex,vector<vertex>>::const_iterator it;
		typename  vector<vertex> ::const_iterator it2;

		for (it=graph.begin(); it!=graph.end();it++)//Iterate over the all vertices except elem
		{
		 
			if(it->first!=elem)
			{
					 	
				//Iterate for the corresponding list of connected vertices 
				for (it2 = (graph.find(it->first)->second).begin() ; it2 != (graph.find(it->first)->second).end(); it2++)
				{
					 
					if(*it2==elem)
						backneighbours.push_back(it->first);
				}
				 
			}
		}
		
		
		
		
		
		//////////////////////////////////////
        for (unsigned int i =0; i <backneighbours.size(); i++) 
            if (!visited[backneighbours[i]]) 
			{
				visited[backneighbours[i]]=true;
				inTree.add_vertex(backneighbours[i]);
				inTree.add_edge(backneighbours[i],elem);
				bfsQueue.push_back(backneighbours[i]); 
				
			}
    } 


		
	return inTree; 
}

template <typename vertex> bool directed_graph<vertex>::reachable(const vertex& u, const vertex& v)  
{ 
	vector<vertex> bfsVector = breadth_first(u);
	int reachableFlag = false;
	for(unsigned int i=0;i<bfsVector.size();i++)
		if(v == bfsVector[i])
			reachableFlag= true;
	
	return reachableFlag; 
}

template <typename vertex> vertex_iterator<vertex>::vertex_iterator(const vertex_iterator<vertex>& other) 
{
	//cout<<"Copy constructor"<<endl;
	  this->verticesVector = other.verticesVector;
	  this->totSize = other.totSize;
	  this->currentPosition = other.currentPosition;
	//cout<<"Current position "<< this->currentPosition;
	//cout<<"Current size  "<< this->totSize <<endl;
	
}

template <typename vertex> vertex_iterator<vertex>::vertex_iterator(const directed_graph<vertex>& graph, std::size_t position)
{

	//cout<<"Constructor - iterator"<<endl; 
	verticesVector  =  graph.get_vertices();
	totSize         =  graph.num_vertices();
	currentPosition =  0;
	//cout<<"tot size "<<totSize<<endl; 
	
}

template <typename vertex> vertex_iterator<vertex>::~vertex_iterator() 
{
	verticesVector.clear();
	totSize         =  0;
	currentPosition =  0;
	
}

template <typename vertex> vertex_iterator<vertex> vertex_iterator<vertex>::operator=(const vertex_iterator<vertex>& other) 
{
	this->verticesVector  = other.verticesVector;
	this->totSize         = other.totSize;
	this->currentPosition = other.currentPosition;
	
	
	return *this; 
}

template <typename vertex> bool vertex_iterator<vertex>::operator==(const vertex_iterator<vertex>& other) const 
{ 
	
	if(this->totSize != other .totSize)
		return false;
	
	if(this->currentPosition    !=  other.currentPosition)
		return false;
	
	for(unsigned int i =0; i < verticesVector.size() ; i++)
		if(verticesVector[i]  != other.verticesVector[i] )
			return false;
		
	return true;
}

template <typename vertex> bool vertex_iterator<vertex>::operator!=(const vertex_iterator<vertex>& other) const 
{ 
	if(this->totSize != other .totSize)
		return true;
	
	if(this->currentPosition    !=  other.currentPosition)
		return true;
	
	for(unsigned int i =0; i < verticesVector.size() ; i++)
		if(verticesVector[i]  != other.verticesVector[i] )
			return true;
		
	return false;
}

template <typename vertex> vertex_iterator<vertex> vertex_iterator<vertex>::operator++() // prefix ++
{
	
 	 
	if(this->currentPosition < this->totSize)
		(this->currentPosition)++;
	//cout<<"Now the position is "<<(this->currentPosition)<<endl;
	
	return *this; 
}

template <typename vertex> vertex_iterator<vertex> vertex_iterator<vertex>::operator++(int) // postfix ++
{
	
 	// cout<<"Postfix"<<endl;
	 vertex_iterator<vertex> result(*this);   // make a copy for prerfix
     ++(*this);              
 	//cout<<"Now the position is "<<(this->currentPosition)<<endl;

     return result;           
}
template <typename vertex> vertex vertex_iterator<vertex>::operator*() 
{ 
	int i;
	for( i = 0; i < this->currentPosition ; i++);		 
	return verticesVector[i]; 
}

template <typename vertex> vertex* vertex_iterator<vertex>::operator->() 
{ 
	int i;
	for(i = 0; i < this->currentPosition ; i++);		 
	return &verticesVector[i]; 
}

template <typename vertex> neighbour_iterator<vertex>::neighbour_iterator(const neighbour_iterator<vertex>& other)
{
	  this->vertexNeighborsVector = other.vertexNeighborsVector;
	  this->totSize = other.totSize;
	  this->currentPosition = other.currentPosition;
}

template <typename vertex> neighbour_iterator<vertex>::neighbour_iterator(const directed_graph<vertex>& graph, const vertex& u, std::size_t position) 
{
	vertexNeighborsVector  =  graph.get_neighbours(u);
	totSize                =  graph.get_neighbours(u).size();
	currentPosition =  0;
}

template <typename vertex> neighbour_iterator<vertex>::~neighbour_iterator() 
{
	vertexNeighborsVector .clear();
	totSize                = 0;
	currentPosition =  0;
}

template <typename vertex> neighbour_iterator<vertex> neighbour_iterator<vertex>::operator=(const neighbour_iterator<vertex>& other) 
{ 
	this->vertexNeighborsVector  = other.vertexNeighborsVector;
	this->totSize         = other.totSize;
	this->currentPosition = other.currentPosition;
	
	
	return *this; 
}

template <typename vertex> bool neighbour_iterator<vertex>::operator==(const neighbour_iterator<vertex>& other) const 
{ 
	if(this->totSize != other .totSize)
		return false;
	
	if(this->currentPosition    !=  other.currentPosition)
		return false;
	
	for(unsigned int i =0; i < vertexNeighborsVector.size() ; i++)
		if(vertexNeighborsVector[i]  != other.vertexNeighborsVector[i] )
			return false;
		
	return true;
}

template <typename vertex> bool neighbour_iterator<vertex>::operator!=(const neighbour_iterator<vertex>& other) const 
{ 
	if(this->totSize != other .totSize)
		return true;
	
	if(this->currentPosition    !=  other.currentPosition)
		return true;
	
	for(unsigned int i =0; i < vertexNeighborsVector.size() ; i++)
		if(vertexNeighborsVector[i]  != other.vertexNeighborsVector[i] )
			return true;
		
	return false;
}

template <typename vertex> neighbour_iterator<vertex> neighbour_iterator<vertex>::operator++() 
{ 
	if(this->currentPosition < this->totSize)
	(this->currentPosition)++;
	
	return *this; 
}

template <typename vertex> neighbour_iterator<vertex> neighbour_iterator<vertex>::operator++(int) 
{ 
	 neighbour_iterator<vertex> result(*this);   // make a copy for prerfix
     ++(*this);              
     return result;   
}		

template <typename vertex> vertex neighbour_iterator<vertex>::operator*() 
{ 
	int i;
	for( i = 0; i < this->currentPosition ; i++);		 
	return vertexNeighborsVector[i];
}

template <typename vertex> vertex* neighbour_iterator<vertex>::operator->() 
{ 
	int i;
	for(i = 0; i < this->currentPosition ; i++);		 
	return &vertexNeighborsVector[i]; 
}


#endif