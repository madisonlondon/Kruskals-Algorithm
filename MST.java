import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.awt.Point;

// 
// Decompiled by Procyon v0.5.36
// 

public class MST
{
    ArrayList<Edge> graphEdges;
    ArrayList<Edge> mst;
    
    public MST() {
    		graphEdges = new ArrayList<Edge>();
    		mst = new ArrayList<Edge>();
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
