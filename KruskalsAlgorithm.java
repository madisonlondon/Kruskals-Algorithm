// Maddie London and Berke Nuri 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;


public class KruskalsAlgorithm extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
    final int VERTEX_RADIUS = 8; // The radius in pixels of the vertex drawn 

    KruskalsAlgorithm canvas = null;  // GUI stuff

    JPanel buttonPanel = null;
    JButton addVertexButton, removeVertexButton, addEdgeButton, removeEdgeButton, edgeWeightButton, clearButton, createMstButton, okButton;

    // Data Structures for the Points

    /*This holds the set of vertices, all
     * represented as type Point.
     */
    LinkedList<Vertex> vertices = null;
    LinkedList<Edge> edges = null;
    LinkedList<Point> hull = null;
    //LinkedList<edge> edges = null;

    public KruskalsAlgorithm() {
    }

    public static void main(final String[] array) {
        final KruskalsAlgorithm project = new KruskalsAlgorithm();
project.addWindowListener(new WindowAdapter() {
		public void
		    windowClosing(WindowEvent e) {
		    System.exit(0);
		}
	    }
				  );
	project.pack();
	project.setVisible(true);
    }

	// Override
    public void actionPerformed(final ActionEvent actionEvent) {
}

	// Override
    public void mouseClicked(final MouseEvent mouseEvent) {
    }

	// Override
    public void mouseMoved(final MouseEvent mouseEvent) {
    }

   	// Override
    public void mouseExited(final MouseEvent mouseEvent) {
    }

    // Override
    public void mouseEntered(final MouseEvent mouseEvent) {
    }

    // Override
    public void mouseReleased(final MouseEvent mouseEvent) {
    }

    // Override
    public void mousePressed(final MouseEvent mouseEvent) {
    }

    // Override
    public void mouseDragged(final MouseEvent mouseEvent) {
    }
}