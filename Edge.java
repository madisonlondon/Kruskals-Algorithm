// Maddie London and Berke Nuri
import java.awt.Point;
import java.util.ArrayList;

public class Edge implements Comparable
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
        System.out.println("in the edge constructor");
    }

    @Override
    public int compareTo(Object o) {
       return  (this.weight < ((Edge) o).weight ? -1 : (this.weight == ((Edge) o).weight ? 0 : 1));
   }

    public Point midPoint() {
        return new Point((int)(0.5 * (this.v1.p.x + this.v2.p.x)),
        		(int)(0.5 * (this.v1.p.y + this.v2.p.y)));
    }
} 
