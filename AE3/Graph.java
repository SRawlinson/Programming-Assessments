import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;

/*
 * This class represents the Model section of the program, building the graph based on the Input file, and displaying the 
 * information found within based on the selected variables from the combo boxes found in the Frame class. 
 */
public class Graph extends JComponent implements MouseListener {
	private int width;
	private int height;
	private ArrayList<Bond> dataList;
	private ArrayList<Ellipse2D> ellipsesList;
	
	private double maxX = 0;
	private double minX = 0;
	private double maxY = 0;
	private double minY = 0;
	private double xAxisLength;
	private double yAxisLength;
	private double incrementX;
	private double incrementY;
	
	private Point2D.Double zeroPoint;
	private Point2D.Double maxXPoint ;
	private Point2D.Double maxYPoint ;
	private Line2D.Double xAxis;
	private Line2D.Double yAxis;
	
	public Graph(ArrayList dataInputs) {
		dataList = dataInputs;

		setMaxAndMinValues();
		ellipsesList = new ArrayList();
		this.addMouseListener(this);
		
	}
	
	public void setArrayList(ArrayList dataInputs) {
		dataList = dataInputs;
		
	}
	
	public ArrayList getDataList() {
		return dataList;
	}
	
	/*
	 * This method sets the minimum and maximum values for each selected axis. This is to provide accurate scaling (with 
	 * the max values) and to allow the graph to accommodate for values below zero (with the minimum values). 
	 */
	public void setMaxAndMinValues() {
		//This resets each min and max value at the start for the < or > formula to work. 
		maxX = 0;
		minX = 0;
		maxY = 0;
		minY = 0;
		//This then finds the new max and min values for the each axis by going through each bond item. 
		for(Bond b : dataList) {
			if(b.getX() > maxX) {
				maxX = b.getX();
			}
			if(b.getX() < minX) {
				minX = b.getX();
			}
			if(b.getY() > maxY) {
				maxY = b.getY();
			}
			if(b.getY() < minY) {
				minY = b.getY();
			}
		}
		//Finally, this rounds the max and min values so that each item fits cleanly on the graph 
		maxX = Math.ceil(maxX += (maxX/10));
		maxY = Math.ceil(maxY += (maxY/10));
		minX = Math.floor(minX -= (minX/10));
		minY = Math.floor(minY -= (minY/10));
	
	}

	/*
	 * The paintComponents method is broken into smaller methods to allow for easier testing. 
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		
		drawEachAxis(g2);
		
		drawEllipsesList(g2);
		
	}
	
	/*
	 * This methods draws the outer border of the graph, defining the area the points will be plotted to. 
	 */
	public void drawEachAxis(Graphics2D g2) {
	
		setAxisLengths();
	
		g2.draw(xAxis);
		g2.draw(yAxis);	
		g2.drawString(String.valueOf(minX), 30, height-18);
		g2.drawString(String.valueOf(minY), 0, height-35);
	//This method then calls the next, which adds in the details and scale to the graph. 
		drawAxisDetails(g2);	
	
	}
	
	public void setAxisLengths() {
		height = this.getHeight();
		width = this.getWidth();
		zeroPoint = new Point2D.Double(50, height-40);
		maxXPoint = new Point2D.Double(width-40, height-40);
		maxYPoint = new Point2D.Double(50, 40); 
		xAxis = new Line2D.Double(zeroPoint, maxXPoint);
		yAxis = new Line2D.Double(zeroPoint, maxYPoint);
		//The following variables are declared for scaling the graph
		xAxisLength = xAxis.x2 - xAxis.x1;
		yAxisLength = yAxis.y1 - yAxis.y2;
	}
	
		/*
		 * This method adds in the scale for each axis. 
		 */
	public void drawAxisDetails(Graphics2D g2) {
		
		incrementX = xAxisLength/10;
		incrementY = yAxisLength/10;
		/*
		 * The scale must divide the length of the axis by the difference between the max and min values, not simply the max 
		 * value. The difference between the min and zero must be accounted for (if any). 
		 * 
		 * These lines adjust the max value accordingly, while not affecting the program if the min value is zero. 
		 */
		maxX += (0-minX);
		maxY += (0-minY);
		
		if (minX < 0) {
			drawZeroMarkerX(g2);
		} 
			//The increment variables dictate the value of the scale, and the marker positions. I felt that ten 
			// markers would be sufficient for a clear graph. 
			for(int i = 1; i < 11; i++) {
				Line2D.Double xLine = new Line2D.Double(incrementX*i+50, height-40, incrementX*i+50, height - 30);
				g2.draw(xLine);			
				String xScaleValue = String.valueOf(String.format("%.2f", minX + (maxX/10)*i));
				g2.drawString(xScaleValue, (int) (incrementX*i+30), height-18);
			} 
		
		
		if (minY < 0) {
			drawZeroMarkerY(g2);
		} 
			for(int i = 1; i < 11; i++) {
				Line2D.Double yLine = new Line2D.Double(50, (height -(incrementY*i))-40, 45, (height - (incrementY*i))-40);
				g2.draw(yLine);
				String yScaleValue = String.valueOf(String.format("%.1f", minY + (maxY/10)*i));
				g2.drawString(yScaleValue, 0, (int) ((height - (incrementY*i)) - 35));
			}
		
		
	}
	
	/*
	 * The following methods add in a marker on the scale for zero if there are any negative numbers found. 
	 * 
	 * This was built with the idea that negative values would be the exception to the rule rather than a regular occurrence, and so 
	 * simply adds further clarity to the scale, although it may overlap the usual markers. 
	 */
	
	public void drawZeroMarkerX(Graphics2D g2) {
		Line2D.Double zeroLine = new Line2D.Double(((0 - minX)*(xAxisLength/maxX)) + 50, height-40, ((0 - minX)*(xAxisLength/maxX)) + 50, height -30);
		g2.draw(zeroLine);
		g2.drawString("0", (int)(((0 - minX)*(xAxisLength/maxX)) + 47), height - 18);

	}
	
	public void drawZeroMarkerY(Graphics2D g2) {
		Line2D.Double zeroLine = new Line2D.Double(50, (height - ((0 - minY)*(yAxisLength/maxY)) -40), 45, (height - ((0 - minY)*(yAxisLength/maxY))-40));
		g2.draw(zeroLine);
		g2.drawString("0", 5, (int)(height - (0 - minY)*(yAxisLength/ maxY))-35);
		
	}
	
	
	/*
	 * Each bond object is plotted to the graph according to the scale, accommodating for negative values by plotting 
	 * them relative to the minimum value (usually zero). 
	 */
	public void drawEllipsesList(Graphics2D g2){
		//Similar to clearing the max and min values, the array-list of ellipses (needed for the mouse listener element of the program) needs to be empty 
		//before the next process starts. 
		ellipsesList.clear();
		g2.setColor(Color.BLUE);
		//This adds the bonds in according to their values (to the scale determined above)
		for(Bond b: dataList) {
			double x = (b.getX()*(xAxisLength/maxX) + 50 + ((0 - minX)*(xAxisLength/maxX))) ;
			double y = b.getY()*(yAxisLength/maxY) + 40 + ((0 - minY)*(yAxisLength/maxY));
			//inverts the y axis
			y = (height-y);
			
			double r = 5;
			Ellipse2D.Double e = new Ellipse2D.Double(x,y,r,r);
			g2.draw(e);
			g2.fill(e);		
			ellipsesList.add(e);			

		}
	}
	
	/*
	 * This method cycles through the arraylist of bond objects for any which share a coordinate 
	 * with that of the user's mouse, and prints any found to the detailsField in the Frame class. 
	 */
	public void mouseClicked(MouseEvent e) {
		
		for(int i = 0; i < ellipsesList.size(); i++) {
			if (ellipsesList.get(i).contains(e.getPoint())) {
				Frame.updateDetailsField(dataList.get(i));
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	

}
