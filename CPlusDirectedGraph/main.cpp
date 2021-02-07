#include <iostream>
#include <vector>

#include "directed_graph.hpp"


int main() 
{
	directed_graph<int> graph1;
	graph1.add_vertex(0);
	graph1.add_vertex(1);
	graph1.add_vertex(2);
	graph1.add_vertex(3);
	graph1.add_vertex(4);
	graph1.add_vertex(5);
	
	graph1.add_edge(0,3);
	graph1.add_edge(5,4);
	graph1.add_edge(3,4);
	graph1.add_edge(4,0);
	graph1.add_edge(0,2);
	graph1.add_edge(2,1);
	graph1.add_edge(1,0);
		

	cout<<"In degree of 0 : "<<graph1.in_degree(0)<<endl; 
	cout<<"In degree of 1 : "<<graph1.in_degree(1)<<endl; 
	cout<<"In degree of 2 : "<<graph1.in_degree(2)<<endl; 
	cout<<"In degree of 3 : "<<graph1.in_degree(3)<<endl; 
	cout<<"In degree of 4 : "<<graph1.in_degree(4)<<endl; 
	
	cout<<"Out degree of 0 : "<<graph1.out_degree(0)<<endl; 
	cout<<"Out degree of 1 : "<<graph1.out_degree(1)<<endl; 
	cout<<"Out degree of 1 : "<<graph1.out_degree(2)<<endl; 
	cout<<"Out degree of 1 : "<<graph1.out_degree(3)<<endl; 
	cout<<"Out degree of 1 : "<<graph1.out_degree(4)<<endl; 

	//Assignment1Tests  testsObject;
	vector<int> vertices= graph1.get_vertices();
	vector<int> neightbors ;
	
	cout<<"Graph vertices "<<endl;
	for(unsigned int i =0;i<vertices.size();i++)
	{
		cout<<vertices[i]<<" "<<endl;;
		cout<<"\tneighbors: ";
		neightbors.clear();
		neightbors = graph1.get_neighbours(vertices[i]);
		for( unsigned int j =0;j<neightbors.size();j++)
			cout<<neightbors[j]<<" ";
		cout<<endl;
	}
	cout<<endl;
	
	cout<<"Depth first Search"<<endl;
	cout<<"Start from node 0 "<<endl; 
	vector<int> dfsVertices=  graph1.depth_first(0);
	for(unsigned int i =0;i<dfsVertices.size();i++)
		cout<<dfsVertices[i]<<" ";
	cout<<endl;
	
 	
	cout<<"Breadth first Search"<<endl;
	cout<<"Start from node 0 "<<endl; 
	vector<int> bfsVertices=  graph1.breadth_first(0);
	for(unsigned int i =0;i<bfsVertices.size();i++)
		cout<<bfsVertices[i]<<" ";
	cout<<endl;

	cout<<"Spanning tree constructed from node 0"<<endl; 
	cout<<"Out_Tree vertices "<<endl;
	directed_graph<int> outTree = graph1.out_tree(0);
	vertices= outTree.get_vertices();
	for(unsigned int i =0;i<vertices.size();i++)
	{
		cout<<vertices[i]<<" "<<endl;;
		cout<<"\tneighbors: ";
		neightbors.clear();
		neightbors = outTree.get_neighbours(vertices[i]);
		for(unsigned int j =0;j<neightbors.size();j++)
			cout<<neightbors[j]<<" ";
		cout<<endl;
	}
	cout<<endl;	
	
	cout<<"Spanning tree constructed to node 0"<<endl; 
	cout<<"In_Tree vertices "<<endl;
	directed_graph<int> inTree = graph1.in_tree(0);
	vertices= inTree.get_vertices();
	for(unsigned int i =0;i<vertices.size();i++)
	{
		cout<<vertices[i]<<" "<<endl;;
		cout<<"\tneighbors: ";
		neightbors.clear();
		neightbors = inTree.get_neighbours(vertices[i]);
		for(unsigned int j =0;j<neightbors.size();j++)
			cout<<neightbors[j]<<" ";
		cout<<endl;
	}
	cout<<endl;	
	
	
	cout<<"Is reachable 0 --> 5 !? "<<endl;
	if(graph1.reachable(0,5)==true)
		cout<<"\tYes"<<endl;
	else	
		cout<<"\tNo"<<endl;
	

	///////// Testing the iterators 
	cout<<"Test the vertex iterator"<<endl;
	cout<<"The following are the list of the vertices in the graph"<<endl;
	vertex_iterator <int> vertIterator(graph1,graph1.num_vertices());
	for( vertIterator = graph1.begin(); vertIterator != graph1.end(); vertIterator++)
	{		 
		cout << *vertIterator<< " ;  ";
	}
	/////
	cout<<"Test the neighbors iterator"<<endl;
	cout<<"The following are the list of the vertices in the graph"<<endl;
	neighbour_iterator <int> vertNeighborsIterator(graph1,0,graph1.get_neighbours(0).size());

	for( vertNeighborsIterator = graph1.nbegin(0); vertNeighborsIterator != graph1.nend(0); vertNeighborsIterator++)
	{	

		cout << *vertNeighborsIterator<< " ;  ";
	}
	
}