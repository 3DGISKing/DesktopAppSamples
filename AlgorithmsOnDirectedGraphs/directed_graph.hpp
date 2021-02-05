#ifndef DIRECTED_GRAPH_H
#define DIRECTED_GRAPH_H

#include <unordered_set>
#include <unordered_map>

template <typename vertex>
class directed_graph {

private:

  std::unordered_set<vertex> vertices;
  std::unordered_map<vertex, std::unordered_set<vertex>> edges;

public:
	
  typedef typename std::unordered_set<vertex>::iterator vertex_iterator;
  typedef typename std::unordered_set<vertex>::iterator neighbour_iterator;
  typedef typename std::unordered_set<vertex>::const_iterator const_vertex_iterator;
  typedef typename std::unordered_set<vertex>::const_iterator const_neighbour_iterator;
  
  directed_graph() {} //A constructor for directed_graph. 
  
  directed_graph(const directed_graph<vertex>& other) {
    vertices = other.vertices;
    edges = other.edges;
  }
  
  ~directed_graph() {} //A destructor.

  bool contains(const vertex& u) const { return vertices.count(u) > 0; } //Returns true if the given vertex is in the graph, false otherwise.
  
  //Returns true if the first vertex is adjacent to the second, false otherwise.
  
  bool adjacent(const vertex& u, const vertex& v) const {
    if (contains(u)) return edges.at(u).count(v) > 0;
    return false;
  }

  //Adds the passed in vertex to the graph (with no edges).
  void add_vertex(const vertex& u) {
    if (!contains(u)) {
      vertices.insert(u);
      edges[u] = std::unordered_set<vertex>();
    }
  }
  
  //Adds an edge from the first vertex to the second.
  void add_edge(const vertex& u, const vertex& v) {
    if (contains(u) && contains(v) && u != v) edges[u].insert(v);
  }

  //Removes the given vertex. Should also clear any incident edges.
  void remove_vertex(const vertex& u) {
    vertices.erase(u);
    edges.erase(u);
  }
  
  //Removes the edge between the two vertices, if it exists.
  void remove_edge(const vertex& u, const vertex& v) {
    if (contains(u)) edges[u].erase(v);
  }

  //Returns number of edges coming in to a vertex.
  std::size_t in_degree(const vertex& u) const {
    auto count = 0;
    for (auto v : vertices) {
      if (u != v) count += edges.at(v).count(u);
    }
    return count;
  }
  
  //Returns the number of edges leaving a vertex.
  std::size_t out_degree(const vertex& u) const { return contains(u)? edges.at(u).size() : 0; }
  
  //Returns the degree of the vertex (both in and out edges).
  std::size_t degree(const vertex& u) const { return in_degree(u) + out_degree(u); }
  
  //Returns the total number of vertices in the graph.
  std::size_t num_vertices() const { return vertices.size(); }
  
  //Returns the total number of edges in the graph.
  std::size_t num_edges() const {
    auto count = 0;
    for (auto entry : edges) count += entry.second.size();
    return count;
  }

  //Returns a graph_iterator pointing to the start of the vertex set. 
  vertex_iterator begin() {
    return vertices.begin();
  }

  //Returns a graph_iterator pointing to one-past-the-end of the vertex set.
  vertex_iterator end() {
    return vertices.end();
  }
    
  //Returns a graph_iterator pointing to the start of the vertex set.  
  const_vertex_iterator begin() const {
    return vertices.cbegin();
  }
  
  //Returns a graph_iterator pointing to one-past-the-end of the vertex set.
  const_vertex_iterator end() const {
    return vertices.cend();
  }
  
  //Returns a neighbour_iterator pointing to the start of the neighbour set for the given vertex.
  neighbour_iterator nbegin(const vertex& u) {
    return edges.at(u).begin();
  }
  
  //Returns a neighbour_iterator pointing to one-past-the-end of the neighbour set for the given vertex.
  neighbour_iterator nend(const vertex& u) {
    return edges.at(u).end();
  }
  
  //Returns a neighbour_iterator pointing to the start of the neighbour set for the given vertex.
  const_neighbour_iterator nbegin(const vertex& u) const {
    return edges.at(u).cbegin();
  }
  
  //Returns a neighbour_iterator pointing to one-past-the-end of the neighbour set for the given vertex.
  const_neighbour_iterator nend(const vertex& u) const {
    return edges.at(u).cend();
  }

};

#endif

