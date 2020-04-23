import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;
import java.lang.Object.*;


public class KruskalsAlgorithm extends JFrame
    implements ActionListener, MouseListener, MouseMotionListener {

    // The radius in pixels of the circles drawn in graph_panel

    final int NODE_RADIUS = 8;

    enum States {DEFAULT, ADD_VERTEX, REMOVE_VERTEX, ADD_EDGE_1, ADD_EDGE_2, REMOVE_EDGE, SET_EDGE_WEIGHT, COMPUTE_MST } 
    States state = States.DEFAULT;
    int ADD_VERTEX = 0;
    int REMOVE_VERTEX = 1;
    int ADD_EDGE_1 = 2;
    int ADD_EDGE_2 = 3;
    int REMOVE_EDGE = 4;
    int SET_EDGE_WEIGHT = 5;
    int COMPUTE_MST = 6;

    int clickedVertexIndex;
    int edgeIndex = -1;

    // GUI stuff

    MST canvas = null;

    JPanel buttonPanel1 = null;
    JPanel buttonPanel2 = null;
    JButton addVertexButton, removeVertexButton, addEdgeButton,
    removeEdgeButton, setEdgeWeightButton, computeMstButton, clearButton;

    ArrayList<Vertex> vertices = null;
    ArrayList<Edge> edges = null;
    ArrayList<Vertex> cloud = null;
    ArrayList<Edge> mst = null;

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
	canvas.addMouseListener(this);
	canvas.addMouseMotionListener(this);

	Dimension canvasSize = new Dimension(900,500);
	canvas.setMinimumSize(canvasSize);
	canvas.setPreferredSize(canvasSize);
	canvas.setMaximumSize(canvasSize);
	canvas.setBackground(Color.black);

	// Create buttonPanel1 and fill it
	buttonPanel1 = new JPanel();
	Dimension panelSize = new Dimension(900,75);
	buttonPanel1.setMinimumSize(panelSize);
	buttonPanel1.setPreferredSize(panelSize);
	buttonPanel1.setMaximumSize(panelSize);
	buttonPanel1.setLayout(new BoxLayout(buttonPanel1,
					    BoxLayout.X_AXIS));
                        buttonPanel1.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.black),
                       buttonPanel1.getBorder()));

    // Create buttonPanel2 and fill it
	buttonPanel2 = new JPanel();
	buttonPanel2.setMinimumSize(panelSize);
	buttonPanel2.setPreferredSize(panelSize);
	buttonPanel2.setMaximumSize(panelSize);
	buttonPanel2.setLayout(new BoxLayout(buttonPanel2,
					    BoxLayout.X_AXIS));
                        buttonPanel2.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.black),
					   buttonPanel2.getBorder()));

	Dimension buttonSize = new Dimension(150,50);
	addVertexButton = new JButton("Add Vertex");
	addVertexButton.setMinimumSize(buttonSize);
	addVertexButton.setPreferredSize(buttonSize);
	addVertexButton.setMaximumSize(buttonSize);
	addVertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	addVertexButton.setActionCommand("addVertex");
	addVertexButton.addActionListener(this);
	addVertexButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
                       addVertexButton.getBorder()));

	removeVertexButton = new JButton("Remove Vertex");
	removeVertexButton.setMinimumSize(buttonSize);
	removeVertexButton.setPreferredSize(buttonSize);
	removeVertexButton.setMaximumSize(buttonSize);
	removeVertexButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	removeVertexButton.setActionCommand("removeVertex");
	removeVertexButton.addActionListener(this);
	removeVertexButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
					   removeVertexButton.getBorder()));

	addEdgeButton = new JButton("Add Edge");
	addEdgeButton.setMinimumSize(buttonSize);
	addEdgeButton.setPreferredSize(buttonSize);
	addEdgeButton.setMaximumSize(buttonSize);
	addEdgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	addEdgeButton.setActionCommand("addEdge");
	addEdgeButton.addActionListener(this);
	addEdgeButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
                       addEdgeButton.getBorder()));

	removeEdgeButton = new JButton("Remove Edge");
	removeEdgeButton.setMinimumSize(buttonSize);
	removeEdgeButton.setPreferredSize(buttonSize);
	removeEdgeButton.setMaximumSize(buttonSize);
	removeEdgeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	removeEdgeButton.setActionCommand("removeEdge");
	removeEdgeButton.addActionListener(this);
	removeEdgeButton.
	    setBorder(BorderFactory.
		      createCompoundBorder(BorderFactory.
					   createLineBorder(Color.green),
                       removeEdgeButton.getBorder()));

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
					   createLineBorder(Color.green),
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

	buttonPanel1.add(Box.createHorizontalGlue());
	buttonPanel1.add(addVertexButton);
	buttonPanel1.add(Box.createHorizontalGlue());
	buttonPanel1.add(removeVertexButton);
	buttonPanel1.add(Box.createHorizontalGlue());
	buttonPanel1.add(addEdgeButton);
	buttonPanel1.add(Box.createHorizontalGlue());
	buttonPanel1.add(removeEdgeButton);
    buttonPanel1.add(Box.createHorizontalGlue());

    buttonPanel2.add(Box.createHorizontalGlue());
    buttonPanel2.add(setEdgeWeightButton);
    buttonPanel2.add(Box.createHorizontalGlue());
    buttonPanel2.add(computeMstButton);
    buttonPanel2.add(Box.createHorizontalGlue());
    buttonPanel2.add(clearButton);
	buttonPanel2.add(Box.createHorizontalGlue());

	contentPane.add((Component)this.canvas);
    contentPane.add(this.buttonPanel1);
    contentPane.add(this.buttonPanel2);
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

	if (buttonIdentifier.equals("addVertex")) {
        state = States.ADD_VERTEX;
    }
    else if (buttonIdentifier.equals("removeVertex")) {
        state = States.REMOVE_VERTEX;
    }
    else if (buttonIdentifier.equals("addEdge")) {
        state = States.ADD_EDGE_1;
    }
    else if (buttonIdentifier.equals("removeEdge")) {
        state = States.REMOVE_EDGE;
    }
    else if (buttonIdentifier.equals("changeEdgeWeight")) {
        state = States.SET_EDGE_WEIGHT;
    }
    else if (buttonIdentifier.equals("createMST")) {
        state = States.COMPUTE_MST;
        mst = new MST(edges).getMST();
        canvas.repaint();
    }
    else if (buttonIdentifier.equals("clear")) {
        vertices.clear();
        edges.clear();
        cloud.clear();
        //this.changeWeightNdx = -1;
        state = States.DEFAULT;
        canvas.repaint();
    }

    }

    public int onAVertex(Point point) {
        int n = -1;

        for (int i = 0; i < vertices.size(); ++i) {

        		Vertex vertex = vertices.get(i);

            if (point.distance(vertex.p) <= 8.0) {
                n = i;
                vertex.hovered = true;
                break;
            }
            vertex.hovered = false;

        }
        return n;
    }

    public int onAnEdge(Point point) {
        int n = -1;

        if (edgeIndex > 0) {

        		Edge edge = edges.get(edgeIndex);
        		Line2D line = new Line2D.Double (edge.v1.p, edge.v2.p);
        		double distanceFromEdge =
        				line.ptSegDist(edge.v1.p.x, edge.v1.p.y, edge.v2.p.x, edge.v2.p.y, point.x, point.y);

            if (point.distance(edge.midPoint()) <= edge.v1.p.distance(edge.v2.p) / 2.0 &&
            		distanceFromEdge <= 8.0) {
                return edgeIndex;
            }
            edge.hovered = false;
        }

        for (int i = 0; i < this.edges.size(); ++i) {

        		Edge edge2 = edges.get(i);
        		Line2D line2 = new Line2D.Double (edge2.v1.p, edge2.v2.p);
            double distanceFromEdge2 =
            		line2.ptSegDist(edge2.v1.p.x, edge2.v1.p.y, edge2.v2.p.x, edge2.v2.p.y, point.x, point.y);

            if (point.distance(edge2.midPoint()) <= edge2.v1.p.distance(edge2.v2.p) / 2.0 &&
            		distanceFromEdge2 <= 8.0) {
                n = i;
                edge2.hovered = true;
                break;
            }
        }

        return n;
    }

    public void removeVertex(int n) {

        Vertex vertex = vertices.get(n);

        for (int i = 0; i < vertex.inEdges.size(); ++i) {
            edges.remove(vertex.inEdges.get(i));
        }

        vertices.remove(n);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    	if (state == States.ADD_VERTEX) {
    		Point point = e.getPoint();
    		vertices.add(new Vertex(point));
        canvas.repaint();
    	}
    	else if (state == States.REMOVE_VERTEX) {
    		int vertexIndex = onAVertex(e.getPoint());
    		if (vertexIndex >= 0)
    			removeVertex(vertexIndex);

    		canvas.repaint();
    	}
    	else if (state == States.ADD_EDGE_1) {
    		clickedVertexIndex = onAVertex(e.getPoint());
    		state = States.ADD_EDGE_2;
    		canvas.repaint();
    	}
    	else if (state == States.ADD_EDGE_2) {
    		int vertexIndex = onAVertex(e.getPoint());

    		Vertex vertex = vertices.get(clickedVertexIndex);
        Vertex vertex2 = vertices.get(vertexIndex);

        Edge edge = new Edge(vertex, vertex2);
        edges.add(edge);
        vertex.inEdges.add(edge);
        vertex2.inEdges.add(edge);

        Vertex vertex3 = vertex;
        Vertex vertex4 = vertex2;

        vertex4.hovered = false;
        vertex3.hovered = false;
        //this.tempEdge = null;
        state = States.ADD_EDGE_1;
        canvas.repaint();
    	}
    	else if (STATE == REMOVE_EDGE) {
    		e.getPoint();

    		if (edgeIndex != -1) {
    			Edge edge = edges.get(edgeIndex);
    			Vertex vt1 = edge.v1;
    			Vertex vt2 = edge.v2;
    			vt1.inEdges.remove(edge);
    			vt2.inEdges.remove(edge);
    			edges.remove(edgeIndex);
    			edgeIndex = -1;
    			canvas.repaint();
    			//break;
    		}
    	}

    }




/*
    public void mouseClicked(MouseEvent e) {
	Point click_point = e.getPoint();
	vertices.add(new Vertex(click_point));
	canvas.repaint();
    }
*/
    public void initializeDataStructures() {

    	vertices = new ArrayList<Vertex>();
	    edges = new ArrayList<Edge>();
	    cloud = new ArrayList<Vertex>();
	    mst = new ArrayList<Edge>();
    }

    // Overriding
    public void mouseMoved(MouseEvent e) {
        switch (state) {
            case DEFAULT: 
            case ADD_VERTEX:
            case REMOVE_VERTEX:
            case ADD_EDGE_1: 
            case ADD_EDGE_2:
            case REMOVE_EDGE:
            case SET_EDGE_WEIGHT:
            case COMPUTE_MST: 
        }
    }

    // Overriding 
    public void mouseExited(MouseEvent e) {}

    // Overriding 
    public void mouseEntered(MouseEvent e) {}

    // Overriding 
    public void mouseReleased(MouseEvent e) {}

    // Overriding 
    public void mousePressed(MouseEvent e) {}

    // Overriding 
    public void mouseDragged(MouseEvent e) {}

}
