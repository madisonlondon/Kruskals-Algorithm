// Maddie London and Berke Nuri

import java.awt.Point;

public class Vertex
{
    Point p;
    boolean hovered;
    Cloud inCloud;
    
    public Vertex(Point x) {
        p = x;
        hovered = false;
        System.out.println("in the vertex constructor");
    }
}
