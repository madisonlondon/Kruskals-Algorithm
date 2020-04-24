// Maddie London and Berke Nuri
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.JPanel;

public class MST extends JPanel
{
    KruskalsAlgorithm parent = null;
    ArrayList<Edge> graphEdges;
    ArrayList<Vertex> graphVertices;
    ArrayList<Edge> mst;
    Color currentColor = Color.red;
    Edge potentialEdge;

    public MST(KruskalsAlgorithm _parent) {
        // constructor
        super();
        parent = _parent;
        graphVertices = parent.vertices;
        graphEdges = parent.edges;
        potentialEdge = parent.potentialEdge;
        mst = parent.mst;
    }

    public MST() {
        // constructor with no parameters
    		graphEdges = new ArrayList<Edge>();
    		graphVertices = new ArrayList<Vertex>();
    		mst = new ArrayList<Edge>();
    }


    public MST(ArrayList<Edge> graph) {
        // constructor when the only parameter is an Arraylist of edges
        graphEdges = graph;
        mst = new ArrayList<Edge>();
        graphVertices = new ArrayList<Vertex>();
    }

    public void paintComponent(Graphics g) {
        // draw the app
        super.paintComponent(g);

        for (int i = 0; i < graphVertices.size(); ++i) {

        		g.setColor(currentColor);
        		Vertex currentVertex = graphVertices.get(i);

        		if (currentVertex.hovered) {
                g.setColor(Color.yellow);
            }

            g.fillOval(currentVertex.p.x - parent.NODE_RADIUS,
                   currentVertex.p.y - parent.NODE_RADIUS,
                   2*parent.NODE_RADIUS, 2*parent.NODE_RADIUS);

        }

        for (int i = 0; i < graphEdges.size(); ++i) {

        		g.setColor(currentColor);
            Edge edge = graphEdges.get(i);

            if (edge.hovered) {
                g.setColor(Color.yellow);
            }

            Point p1 = edge.v1.p;
            Point p2 = edge.v2.p;
            g.drawLine(p1.x, p1.y, p2.x, p2.y);

            Point midpoint = edge.midPoint();
            g.drawString("Weight: " + edge.weight, midpoint.x, midpoint.y);

        }

        // Potential edge
        if(potentialEdge != null) {
            System.out.println("drawing line to cursor");
            g.setColor(Color.yellow);

            Point p1 = potentialEdge.v1.p;
            Point p2 = potentialEdge.v2.p;
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        if (parent.mst.size() > 0) {
        for (int i = 0; i < parent.mst.size(); ++i) {

        		g.setColor(Color.blue);

            Edge edge = parent.mst.get(i);
            Vertex vt1 = edge.v1;
            Vertex vt2 = edge.v2;

            g.fillOval(vt1.p.x - parent.NODE_RADIUS, vt1.p.y - parent.NODE_RADIUS,
                    2 * parent.NODE_RADIUS, 2 * parent.NODE_RADIUS);
            g.fillOval(vt2.p.x - parent.NODE_RADIUS, vt2.p.y - parent.NODE_RADIUS,
                    2 * parent.NODE_RADIUS, 2 * parent.NODE_RADIUS);


            g.drawLine(vt1.p.x, vt1.p.y, vt2.p.x, vt2.p.y);
            Point midpoint2 = edge.midPoint();
            g.drawString("Weight: "+ edge.weight, midpoint2.x, midpoint2.y);
        }
        }

    }

    public void changeColor() {
        // change the color
        if (currentColor.equals(Color.red)) {
            currentColor = Color.yellow;
        } else {
            currentColor = Color.red;
        }
    }

    public ArrayList<Edge> getMST() {
        // returns an Arraylist of edges in the MST
    	startCloud();
    	ArrayList<Edge> temp = new ArrayList<Edge>();

        while (graphEdges.size() > 0) {

        		Edge e = graphEdges.remove(0);
        		temp.add(e);
            Vertex vertex1 = e.v1;
            Vertex vertex2 = e.v2;
            Cloud c1 = vertex1.inCloud;
            Cloud c2 = vertex2.inCloud;

            if (c1 != c2) {
                mst.add(e);
                Cloud.merge(c1, c2);
            }
        }

        graphEdges.addAll(temp);

        return mst;
    }

    public void startCloud() {
        // create the cloud by adding the appropriate vertices
        for (int i = 0; i < graphEdges.size(); ++i) {
            graphEdges.sort(null);

            Edge edge = graphEdges.get(i);
            Vertex vertex1 = edge.v1;
            Vertex vertex2 = edge.v2;

            if (vertex1.inCloud.vertices.size() == 0) {
                vertex1.inCloud.addToCloud(vertex1);
            }
            if (vertex2.inCloud.vertices.size() == 0) {
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
