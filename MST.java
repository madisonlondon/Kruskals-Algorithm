import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

//
// Decompiled by Procyon v0.5.36
//

public class MST extends JPanel
{
    KruskalsAlgorithm parent = null;
    ArrayList<Edge> graphEdges;
    ArrayList<Vertex> graphVertices;
    ArrayList<Edge> mst;
    Color currentColor = Color.red;

    public MST(KruskalsAlgorithm _parent) {
        super();
        parent = _parent;
        graphVertices = parent.vertices;
        graphEdges = parent.edges;
    }

    public MST() {
    		graphEdges = new ArrayList<Edge>();
    		mst = new ArrayList<Edge>();
    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);

        g.setColor(currentColor);

        ListIterator iterator = graphVertices.listIterator(0);

        Point currentVertex = null;

        for (int i=0; i < graphVertices.size(); ++i) {
            currentVertex = (Point) iterator.next();
            g.fillOval(currentVertex.x - parent.NODE_RADIUS,
                   currentVertex.y - parent.NODE_RADIUS,
                   2*parent.NODE_RADIUS, 2*parent.NODE_RADIUS);
        }
    }

    public void changeColor() {
        if (currentColor.equals(Color.red)) {
            currentColor = Color.yellow;
        } else {
            currentColor = Color.red;
        }
    }

    /*
    public MST(final ArrayList c) {
        (this.graphEdges = new ArrayList()).addAll(c);
        this.mst = new ArrayList();
    }
    */

    public ArrayList<Edge> getMST() {

    		startCloud();

        while (graphEdges.size() > 0) {

        		Edge e = graphEdges.remove(0);
            Vertex vertex1 = e.v1;
            Vertex vertex2 = e.v2;
            Cloud c1 = vertex1.inCloud;
            Cloud c2 = vertex2.inCloud;

            if (c1 != c2) {
                mst.add(e);
                Cloud.merge(c1, c2);
            }
        }
        return mst;
    }

    public void startCloud() {

    		for (int i = 0; i < graphEdges.size(); ++i) {

        		graphEdges.sort(null);

        		Edge edge = graphEdges.get(i);
            Vertex vertex1 = edge.v1;
            Vertex vertex2 = edge.v2;

            if (vertex1.inCloud == null) {
                vertex1.inCloud.addToCloud(vertex1);
            }

            if (vertex2.inCloud == null) {
                vertex2.inCloud.addToCloud(vertex2);
            }
        }
    }

    public static void main(String args[]) {

    		Vertex v1 = new Vertex(new Point());
    		Vertex v2 = new Vertex(new Point());
        Edge e1 = new Edge(v1, v2);
        e1.weight = 0;
        Edge e2 = new Edge(v1, v1);
        e2.weight = 2;
        Edge e3 = new Edge(v2, v2);
        e3.weight = -3;

        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.sort(null);

        for(Edge e : edges) {
        		System.out.println(e.weight);
        }
    }
}
