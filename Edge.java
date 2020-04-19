public class Edge
{
    Vertex v1; // represents one of the vertices at the end of the edge
    Vertex v2; // represents one of the vertices at the end of the edge
    boolean hovered; // represents whether or not the user's cursor is hovering of the edge
    double weight; // the weight associated with the edge
    
    public Edge(Vertex x, Vertex y) { // constructor when given two vertices
        v1 = x;
        v2 = y;
        hovered = false;
        weight = 1.0; // default edge weight is 1
    }
} 
