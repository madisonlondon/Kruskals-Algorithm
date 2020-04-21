
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class SwingShell extends JFrame
    implements ActionListener, MouseListener {

    // The radius in pixels of the circles drawn in graph_panel

    final int NODE_RADIUS = 8;

    // GUI stuff
    KruskalsAlgorithm canvas = null;

    JPanel buttonPanel = null;
    JButton vertexButton, edgeButton, computeButton, clearButton;

    // Data Structures for the Points

    /*This holds the set of vertices, all
     * represented as type Point.
     */
    LinkedList<Point> vertices = null;
    LinkedList<Point> hull = null;
    //LinkedList<edge> edges = null;

    // Event handling stuff
    Dimension panelDim = null;

    public SwingShell() {
	super("Generic Swing App");
	setSize(new Dimension(900,575));

	// Initialize main data structures
	initializeDataStructures();

	//The content pane
	Container contentPane = getContentPane();
	contentPane.setLayout(new BoxLayout(contentPane,
					    BoxLayout.Y_AXIS));

	//Create the drawing area
	canvas = new KruskalsAlgorithm();
	canvas.addMouseListener(this);

	Dimension canvasSize = new Dimension(900,500);
	canvas.setMinimumSize(canvasSize);
	canvas.setPreferredSize(canvasSize);
	canvas.setMaximumSize(canvasSize);
	canvas.setBackground(Color.black);

	// Create buttonPanel and fill it
	buttonPanel = new JPanel();
	Dimension panelSize = new Dimension(900,75);
	buttonPanel.setMinimumSize(panelSize);
	buttonPanel.setPreferredSize(panelSize);
	buttonPanel.setMaximumSize(panelSize);
	buttonPanel.setLayout(new BoxLayout(buttonPanel,
					    BoxLayout.X_AXIS));
	buttonPanel.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.black),
					   buttonPanel.getBorder()));

	Dimension buttonSize = new Dimension(150,50);
	vertexButton = new JButton("Add Vertex");
	vertexButton.setMinimumSize(buttonSize);
	vertexButton.setPreferredSize(buttonSize);
	vertexButton.setMaximumSize(buttonSize);
	vertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	vertexButton.setActionCommand("convexHull");
	vertexButton.addActionListener(this);
	vertexButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
					   vertexButton.getBorder()));

	//Dimension buttonSize = new Dimension(100,50);
	edgeButton = new JButton("Add Edge");
	edgeButton.setMinimumSize(buttonSize);
	edgeButton.setPreferredSize(buttonSize);
	edgeButton.setMaximumSize(buttonSize);
	edgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	edgeButton.setActionCommand("convexHull");
	edgeButton.addActionListener(this);
	edgeButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
					   edgeButton.getBorder()));

	//Dimension buttonSize = new Dimension(100,50);
	computeButton = new JButton("Compute MST");
	computeButton.setMinimumSize(buttonSize);
	computeButton.setPreferredSize(buttonSize);
	computeButton.setMaximumSize(buttonSize);
	computeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	computeButton.setActionCommand("convexHull");
	computeButton.addActionListener(this);
	computeButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.blue),
					   computeButton.getBorder()));


	clearButton = new JButton("Clear");
	clearButton.setMinimumSize(buttonSize);
	clearButton.setPreferredSize(buttonSize);
	clearButton.setMaximumSize(buttonSize);
	clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	clearButton.setActionCommand("clearDiagram");
	clearButton.addActionListener(this);
	clearButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.red),
					   clearButton.getBorder()));

	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(vertexButton);
	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(edgeButton);
	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(computeButton);
	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(clearButton);
	buttonPanel.add(Box.createHorizontalGlue());

	contentPane.add(canvas);
	contentPane.add(buttonPanel);
    }

    public static void main(String[] args) {

	SwingShell project = new SwingShell();
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

    public void actionPerformed(ActionEvent e) {

	String buttonIdentifier = e.getActionCommand();

	if (buttonIdentifier.equals("convexHull")) {
	    // compute convex hull
	    canvas.KruskalsAlgorithm();
	    canvas.repaint();
	} else if (buttonIdentifier.equals("clearDiagram")) {
	    vertices.clear();
	    hull.clear();
	    canvas.repaint();
	}
    }

    public void mouseClicked(MouseEvent e) {
	Point click_point = e.getPoint();
	vertices.add(click_point);
	canvas.repaint();
    }

    public void initializeDataStructures() {
	vertices = new LinkedList<Point>();
	hull = new LinkedList<Point>();
    }

    public void mouseExited(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}
}
