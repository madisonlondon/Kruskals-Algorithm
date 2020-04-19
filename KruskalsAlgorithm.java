// Maddie London and Berke Nuri
import java.awt.Point;

public class KruskalsAlgorithm {

	public KruskalsAlgorithm() { // constructor
		System.out.println("In the KruskalsAlgorithm constructor");
		Vertex a = new Vertex(new Point());
		Vertex b = new Vertex(new Point());
		Edge c = new Edge(a, b);
	}

	public static void main(String args[]) {
        KruskalsAlgorithm foo = new KruskalsAlgorithm();
 		System.out.println("in main");
    }
}
