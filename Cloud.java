import java.util.ArrayList;

public class Cloud
{
    ArrayList<Vertex> vertices;

    public Cloud() {
      vertices = new ArrayList<Vertex>();
    }

    public void addToCloud(Vertex v) {
        this.vertices.add(v);
    }

    public static void merge(Cloud c1, Cloud c2) {

    		for (int i = 0; i < c2.vertices.size(); i++) {

    			Vertex v = c2.vertices.get(i);
    			c1.vertices.add(v);
    			v.inCloud = c1;
        }
    }
}
