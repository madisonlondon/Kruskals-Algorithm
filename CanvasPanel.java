
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class CanvasPanel extends JPanel {

    SwingShell parent = null;
    LinkedList vertices = null;
    Color currentColor = Color.red;

    public CanvasPanel(SwingShell _parent) {
	super();
	parent = _parent;
	vertices = parent.vertices;
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);

	g.setColor(currentColor);

	ListIterator iterator = vertices.listIterator(0);

	Point currentVertex = null;

	for (int i=0; i < vertices.size(); ++i) {
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
}

	





