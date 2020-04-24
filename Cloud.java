import java.util.ArrayList;

public class Cloud
{
    ArrayList<Vertex> vertices;

    public Cloud() {
        // constructor
      vertices = new ArrayList<Vertex>();
    }

    public void addToCloud(Vertex v) {
        // add a vertex to the cloud
        this.vertices.add(v);
    }

    public static void merge(Cloud c1, Cloud c2) {
        // merge two clouds together
    		for (int i = 0; i < c2.vertices.size(); i++) {
    			Vertex v = c2.vertices.get(i);
    			c1.vertices.add(v);
    			v.inCloud = c1;
        }
    }
}
