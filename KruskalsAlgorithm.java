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

    // GUI stuff

    MST canvas;

    JPanel buttonPanel1;
    JPanel buttonPanel2;
    JButton addVertexButton, removeVertexButton, addEdgeButton,
    removeEdgeButton, setEdgeWeightButton, computeMstButton, clearButton, enterButton;
    JTextField weight;

    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    ArrayList<Vertex> cloud;
    ArrayList<Edge> mst;

    int clickedVertexIndex;
    int clickedEdgeIndex;
    Edge temporaryEdge;
    int changeEdgeWeights;
    Edge potentialEdge;

    Dimension panelDim = null;

    public KruskalsAlgorithm() {
        // constructor
        super("Generic Swing App");
        setSize(new Dimension(900,575));

        canvas = null;

        buttonPanel1 = null;
        buttonPanel2 = null;
        weight = null;

        vertices = null;
        edges = null;
        cloud = null;
        mst = null;

        clickedVertexIndex = -1;
        clickedEdgeIndex = -1;
        temporaryEdge = null;
        changeEdgeWeights = -1;
        potentialEdge = null;

        state = States.DEFAULT;

        // Initialize main data structures
        initializeLists();

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
        setEdgeWeightButton.setActionCommand("changeEdgeWeight");
        setEdgeWeightButton.addActionListener(this);
        setEdgeWeightButton.
            setBorder(BorderFactory.
                createCompoundBorder(BorderFactory.
                        createLineBorder(Color.green),
                        setEdgeWeightButton.getBorder()));

        weight = new JTextField("");
        weight.setMinimumSize(new Dimension(70, 30));
        weight.setPreferredSize(new Dimension(70, 30));
        weight.setMaximumSize(new Dimension(70, 30));
        weight.setAlignmentX(1.0f);
        weight.setEditable(false);
        weight.setActionCommand("changeEdgeWeight");
        weight.addActionListener(this);
        weight.
            setBorder(BorderFactory.
                createCompoundBorder(BorderFactory.
                        createLineBorder(Color.green),
                        weight.getBorder()));


        computeMstButton = new JButton("Compute MST");
        computeMstButton.setMinimumSize(buttonSize);
        computeMstButton.setPreferredSize(buttonSize);
        computeMstButton.setMaximumSize(buttonSize);
        computeMstButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        computeMstButton.setActionCommand("createMST");
        computeMstButton.addActionListener(this);
        computeMstButton.
            setBorder(BorderFactory.
                createCompoundBorder(BorderFactory.
                        createLineBorder(Color.blue),
                        computeMstButton.getBorder()));

        enterButton = new JButton("Enter");
        enterButton.setMinimumSize(buttonSize);
        enterButton.setPreferredSize(buttonSize);
        enterButton.setMaximumSize(buttonSize);
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setActionCommand("enter");
        enterButton.addActionListener(this);
        enterButton.
            setBorder(BorderFactory.
                    createCompoundBorder(BorderFactory.
                            createLineBorder(Color.green),
                            enterButton.getBorder()));

        clearButton = new JButton("Clear");
        clearButton.setMinimumSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);
        clearButton.setMaximumSize(buttonSize);
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearButton.setActionCommand("clear");
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
        buttonPanel2.add(weight);
        buttonPanel2.add(Box.createHorizontalGlue());
        buttonPanel2.add(enterButton);
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
    // main method where we make and call an instance of KruskalsAlgorithm
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
    // Overriding 
    temporaryEdge = null;
    for (int i = 0; i < this.vertices.size(); ++i) {
        vertices.get(i).hovered = false; // make sure no vertices are previously highlighted
    }
    for (int i = 0; i < this.edges.size(); ++i) {
        edges.get(i).hovered = false; // make sure no edges are previously highlighted
    }
    mst.clear(); // when a new action is performed, the old MST may change, so we want to clear it
    canvas.repaint();

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
        state = States.DEFAULT;
        canvas.repaint();
    }
    else if (buttonIdentifier.equals("enter") && state == States.SET_EDGE_WEIGHT) {
        // parse the user input for edge weight
        String input = weight.getText();
        if (input.length() <= 7) {
            double double1;
            try {
                double1 = Double.parseDouble(input);
            }
            catch (NumberFormatException ex) {
                return;
            }
            Edge ed = edges.get(changeEdgeWeights);
            state = States.DEFAULT;
            ed.weight = double1;
            ed.hovered = false;
            changeEdgeWeights = -1;
            weight.setText("");
            weight.setEditable(false);
            canvas.repaint();
        }
    }

    if(state != States.SET_EDGE_WEIGHT) {
        // when the user has not clicked on the set/change edge weight button, do not allow the user to enter input
        weight.setText("");
        weight.setEditable(false);
        if (changeEdgeWeights > -1) {
            edges.get(changeEdgeWeights).hovered = false;
            changeEdgeWeights = -1;
        }
        canvas.repaint();
    }
    }

    public int onVertex(Point point) {
        // if the point of the event occured on a vertex, return the index if that vertex in vertices
        // if not, return -1
        int x = -1;

        for (int i = 0; i < vertices.size(); ++i) {

        		Vertex vertex = vertices.get(i);

            if (point.distance(vertex.p) <= 8.0) {
                x = i;
                vertex.hovered = true;
                break;
            }
            vertex.hovered = false;

        }
        return x;
    }

    public int onEdge(Point point) {
        // if the point of the event occured on an edge, return the index of that edge in edges
        // if not, return -1
        int n = -1;

        if (clickedEdgeIndex > -1) {

        		Edge edge = edges.get(clickedEdgeIndex);
        		double distanceFromEdge =
        				Line2D.ptSegDist(edge.v1.p.x, edge.v1.p.y, edge.v2.p.x, edge.v2.p.y, point.x, point.y);

            if (point.distance(edge.midPoint()) <= edge.v1.p.distance(edge.v2.p) / 2.0 &&
            		distanceFromEdge <= 8.0) {
                return clickedEdgeIndex;
            }
            edge.hovered = false;
        }

        for (int i = 0; i < edges.size(); ++i) {

        	Edge edge2 = edges.get(i);
            double distanceFromEdge2 =
            		Line2D.ptSegDist(edge2.v1.p.x, edge2.v1.p.y, edge2.v2.p.x, edge2.v2.p.y, point.x, point.y);

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
        // remove a vertex from vertices
        Vertex vertex = vertices.get(n);

        for (int i = 0; i < vertex.inEdges.size(); ++i) {
            edges.remove(vertex.inEdges.get(i));
        }

        vertices.remove(n);
    }

    public void mouseClicked(MouseEvent e) {
        // overriding
    	switch (state) {

    	case ADD_VERTEX: {
    		Point point = e.getPoint();
    		vertices.add(new Vertex(point)); // add a new vertex
    		canvas.repaint();
    		break;
    	}
    	case REMOVE_VERTEX: {
    		clickedVertexIndex = onVertex(e.getPoint());
            if (clickedVertexIndex >= 0) 
                // if the point where the user clicked is on a vertex, remove that vertex
    			removeVertex(clickedVertexIndex);

    		canvas.repaint();
    		break;
    	}
    	case ADD_EDGE_1: {
    		clickedVertexIndex = onVertex(e.getPoint());
    		if (clickedVertexIndex >= 0) { 
                // if the point where the user clicked is on a vertex, change to the state ADD_EDGE_2
    			state = States.ADD_EDGE_2;
    			canvas.repaint();
    		}
    		break;

    	}
    	case ADD_EDGE_2: {
    		int vertexIndex = onVertex(e.getPoint());
    		if (vertexIndex != clickedVertexIndex && vertexIndex > -1) { 
                // if the point where the user clicked is on a vertex and a vertex that is different than the first, then create an edge
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
    			temporaryEdge = null;
    			state = States.ADD_EDGE_1;
    			canvas.repaint();
    		}
    		break;

    	}
    	case REMOVE_EDGE: {
    		e.getPoint();
    		if (clickedEdgeIndex > -1) { 
                // if the point the user clicked is on an edge, remove that edge
    			Edge edge = edges.get(clickedEdgeIndex);
    			Vertex vt1 = edge.v1;
    			Vertex vt2 = edge.v2;
    			vt1.inEdges.remove(edge);
    			vt2.inEdges.remove(edge);
    			edges.remove(clickedEdgeIndex);
    			clickedEdgeIndex = -1;
    			canvas.repaint();
    		}
    		break;
    	}
    	case SET_EDGE_WEIGHT: {
    		e.getPoint();
    		if (clickedEdgeIndex > -1 && clickedEdgeIndex != changeEdgeWeights) {
                // if the point the user clicked is on an edge, change that edge weight 
                edges.get(clickedEdgeIndex).hovered = true;
    			weight.setEditable(true);
    			weight.setText("");
    			if (changeEdgeWeights > -1) {
    				edges.get(changeEdgeWeights).hovered = false;
    			}
    			changeEdgeWeights = clickedEdgeIndex;
    			canvas.repaint();
    		}
    		break;
    	}
    	}
    }

    public void initializeLists() {
        // initialize the arraylists
    	vertices = new ArrayList<Vertex>();
	    edges = new ArrayList<Edge>();
	    cloud = new ArrayList<Vertex>();
	    mst = new ArrayList<Edge>();
    }

    public boolean edgeExists(Point p1,  Point p2) {
        // Check to see if an edge already exists
        boolean exists = false;
        for (int i = 0; i < this.edges.size(); ++i) {
            Edge edge = this.edges.get(i);
            if ((edge.v1.p.x == p1.x && edge.v1.p.y == p1.y && edge.v2.p.x == p2.x && edge.v2.p.y == p2.y) ||
            		(edge.v1.p.x == p2.x && edge.v1.p.y == p2.y && edge.v2.p.x == p1.x && edge.v2.p.y == p1.y)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void mouseMoved(MouseEvent e) {
        // Overriding
        switch (state) {
            case DEFAULT: // do nothing
            case ADD_VERTEX: // do nothing
            case REMOVE_VERTEX: // do nothing
            case ADD_EDGE_1: {
                onVertex(e.getPoint());
                canvas.repaint();
                break;
            }
            case ADD_EDGE_2: {
                Vertex vertex = vertices.get(clickedVertexIndex);
                Point mouse = MouseInfo.getPointerInfo().getLocation();
                potentialEdge = new Edge(vertex, new Vertex(mouse));
                Point point = e.getPoint();
                temporaryEdge = new Edge(new Vertex(vertex.p), new Vertex(point));
                int location = onVertex(point);

                if (location > -1 && location != clickedVertexIndex) { 
                    // if the location is on a vertex and it is not the same vertex as the one already selected for the new edge
                    Vertex vertex2 = vertices.get(location);
                    if (edgeExists(vertex.p, vertex2.p)) {
                        vertex2.hovered = false;
                    }
                }
                vertex.hovered = true;
                canvas.repaint();
                break;
            }
            case REMOVE_EDGE: // do nothing
            case SET_EDGE_WEIGHT: {
                clickedEdgeIndex = onEdge(e.getPoint());

                if (changeEdgeWeights > -1) {
                    edges.get(changeEdgeWeights).hovered = true;
                }
                this.canvas.repaint();
                break;
            }
            case COMPUTE_MST: // do nothing
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
