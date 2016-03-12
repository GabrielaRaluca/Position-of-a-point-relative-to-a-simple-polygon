package geometrie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ReprezentareGrafica extends JPanel
{
	ReperCartezian rc;
	ArrayList<Line> linii; 
	
	ArrayList<Line> poligon; 
	ArrayList<Punct> points; 
	ArrayList<Punct> triangle;
	
	MouseEvents mouse;
	public ReprezentareGrafica(int frameHeight, int frameWidth)
	{
		super();
		setBackground(Color.WHITE);
		rc = new ReperCartezian(frameHeight, frameWidth);
		linii = new ArrayList<Line>();
		triangle = new ArrayList<Punct>();
		poligon = new ArrayList<Line>();
		points = new ArrayList<Punct>();
		mouse = new MouseEvents(this);
		super.addMouseListener(mouse);
		super.addMouseMotionListener(mouse);
	}

	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		rc.paint(g2);
		
		int i;
		
		
		g2.setColor(Color.RED);
		for(i = 0; i < linii.size(); i++)
			linii.get(i).paint(g2);
		
		
		g2.setColor(Color.BLACK);
		for(i = 0; i < poligon.size(); i++)
			poligon.get(i).paint(g2);
		
		
		if (triangle.size() > 0)
		{	
			Line line;
			for(i = 0; i < triangle.size() - 1; i++)
			{
				line = new Line(triangle.get(i).xCord, triangle.get(i).yCord, triangle.get(i+1).xCord,triangle.get(i+1).yCord);
					
				g2.setColor(Color.WHITE);
				line.paint(g2);
				g2.setColor(Color.GREEN);
				line.paint(g2);
			}
				
			line = new Line(triangle.get(0).xCord, triangle.get(0).yCord, triangle.get(triangle.size() - 1).xCord, triangle.get(triangle.size() - 1).yCord);
			g2.setColor(Color.WHITE);
			line.paint(g2);
			g2.setColor(Color.GREEN);
			line.paint(g2);
		}
		
		
		for( i = 0; i < points.size(); i++)
		{
			g2.setColor(Color.BLACK);
		
			Ellipse2D.Double punct = new Ellipse2D.Double(points.get(i).xCord - 5,  points.get(i).yCord - 5, 10,10);
			g2.fill(punct);
			g2.draw(punct);
		}
	}
	
	public void adauga(Line l)
	{	
		linii.add(l);
	}
	
	public void creeazaPoligon(double xl, double yl, double xr, double yr)
	{
		Line l = new Line(xl, yl, xr, yr);
		poligon.add(l);
	}
}
