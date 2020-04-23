import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;


public class KruskalsAlgorithm extends JFrame
    implements ActionListener, MouseListener, MouseMotionListener {

    // The radius in pixels of the circles drawn in graph_panel

    final int NODE_RADIUS = 8;

    int STATE = -1;
    int ADD_VERTEX = 0;
    int REMOVE_VERTEX = 1;
    int ADD_EDGE_1 = 2;
    int ADD_EDGE_2 = 3;
    int REMOVE_EDGE = 4;
    int SET_EDGE_WEIGHT = 5;
    int COMPUTE_MST = 6;

    // GUI stuff

    MST canvas = null;

    JPanel buttonPanel = null;
    JButton addVertexButton, removeVertexButton, addEdgeButton,
    removeEdgeButton, setEdgeWeightButton, computeMstButton, clearButton;

    // Data Structures for the Points

    /*This holds the set of vertices, all
     * represented as type Point.
     */
    ArrayList<Vertex> vertices = null;
    ArrayList<Edge> edges = null;
    ArrayList<Vertex> cloud = null;
    ArrayList<Edge> mst = null;

    // Event handling stuff
    Dimension panelDim = null;

    public KruskalsAlgorithm() {
	super("Generic Swing App");
	setSize(new Dimension(900,575));

	// Initialize main data structures
	initializeDataStructures();

	//The content pane
	Container contentPane = getContentPane();
	contentPane.setLayout(new BoxLayout(contentPane,
					    BoxLayout.Y_AXIS));

	//Create the drawing area

	canvas = new MST(this);
	canvas.addMouseListener(null);
	addMouseListener(this);
	canvas.addMouseMotionListener(null);
	addMouseMotionListener(this);

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
	addVertexButton = new JButton("Add Vertex");
	addVertexButton.setMinimumSize(buttonSize);
	addVertexButton.setPreferredSize(buttonSize);
	addVertexButton.setMaximumSize(buttonSize);
	addVertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	addVertexButton.setActionCommand("getMST");
	addVertexButton.addActionListener(this);
	addVertexButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
                       addVertexButton.getBorder()));

    //Dimension buttonSize = new Dimension(100,50);
	removeVertexButton = new JButton("Remove Vertex");
	removeVertexButton.setMinimumSize(buttonSize);
	removeVertexButton.setPreferredSize(buttonSize);
	removeVertexButton.setMaximumSize(buttonSize);
	removeVertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	removeVertexButton.setActionCommand("getMST");
	removeVertexButton.addActionListener(this);
	removeVertexButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.blue),
					   removeVertexButton.getBorder()));

	//Dimension buttonSize = new Dimension(100,50);
	addEdgeButton = new JButton("Add Edge");
	addEdgeButton.setMinimumSize(buttonSize);
	addEdgeButton.setPreferredSize(buttonSize);
	addEdgeButton.setMaximumSize(buttonSize);
	addEdgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	addEdgeButton.setActionCommand("getMST");
	addEdgeButton.addActionListener(this);
	addEdgeButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
                       addEdgeButton.getBorder()));

    //Dimension buttonSize = new Dimension(100,50);
	removeEdgeButton = new JButton("Remove Edge");
	removeEdgeButton.setMinimumSize(buttonSize);
	removeEdgeButton.setPreferredSize(buttonSize);
	removeEdgeButton.setMaximumSize(buttonSize);
	removeEdgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	removeEdgeButton.setActionCommand("getMST");
	removeEdgeButton.addActionListener(this);
	removeEdgeButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
                       removeEdgeButton.getBorder()));

    //Dimension buttonSize = new Dimension(100,50);
	setEdgeWeightButton = new JButton("Set/Change Edge Weight");
	setEdgeWeightButton.setMinimumSize(buttonSize);
	setEdgeWeightButton.setPreferredSize(buttonSize);
	setEdgeWeightButton.setMaximumSize(buttonSize);
	setEdgeWeightButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	setEdgeWeightButton.setActionCommand("getMST");
	setEdgeWeightButton.addActionListener(this);
	setEdgeWeightButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
					   setEdgeWeightButton.getBorder()));

	//Dimension buttonSize = new Dimension(100,50);
	computeMstButton = new JButton("Compute MST");
	computeMstButton.setMinimumSize(buttonSize);
	computeMstButton.setPreferredSize(buttonSize);
	computeMstButton.setMaximumSize(buttonSize);
	computeMstButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	computeMstButton.setActionCommand("getMST");
	computeMstButton.addActionListener(this);
	computeMstButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.blue),
					   computeMstButton.getBorder()));


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
	buttonPanel.add(addVertexButton);
	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(removeVertexButton);
	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(addEdgeButton);
	buttonPanel.add(Box.createHorizontalGlue());
	buttonPanel.add(removeEdgeButton);
    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(setEdgeWeightButton);
    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(computeMstButton);
    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(clearButton);
	buttonPanel.add(Box.createHorizontalGlue());

	contentPane.add((Component)this.canvas);
	contentPane.add(this.buttonPanel);
    }

    public static void main(String[] args) {

    KruskalsAlgorithm project = new KruskalsAlgorithm();
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

	if (buttonIdentifier.equals("getMST")) {
	    // compute convex hull
	    canvas.getMST();
	    canvas.repaint();
	} else if (buttonIdentifier.equals("clearDiagram")) {
	    vertices.clear();
	    edges.clear();
	    canvas.repaint();
	}
	if (buttonIdentifier.equals("addVertex")) {
        STATE = ADD_VERTEX;
    }
    else if (buttonIdentifier.equals("removeVertex")) {
        STATE = REMOVE_VERTEX;
    }
    else if (buttonIdentifier.equals("addEdge")) {
        STATE = ADD_EDGE_1;
    }
    else if (buttonIdentifier.equals("removeEdge")) {
        STATE = REMOVE_EDGE;
    }
    else if (buttonIdentifier.equals("changeEdgeWeight")) {
        STATE = SET_EDGE_WEIGHT;
    }
    else if (buttonIdentifier.equals("createMST")) {
        STATE = COMPUTE_MST;
        mst = new MST(edges).getMST();
        canvas.repaint();
    }
    else if (buttonIdentifier.equals("clear")) {
        vertices.clear();
        edges.clear();
        cloud.clear();
        //this.changeWeightNdx = -1;
        STATE = -1;
        canvas.repaint();
    }

    }



    public void mouseClicked(MouseEvent e) {
	Point click_point = e.getPoint();
	vertices.add(new Vertex(click_point));
	canvas.repaint();
    }

    public void initializeDataStructures() {

    	vertices = new ArrayList<Vertex>();
	edges = new ArrayList<Edge>();
	cloud = new ArrayList<Vertex>();
	mst = new ArrayList<Edge>();
    }

    public void mouseExited(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
