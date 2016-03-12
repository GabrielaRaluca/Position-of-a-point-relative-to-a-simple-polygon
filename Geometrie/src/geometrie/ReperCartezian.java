package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class ReperCartezian 
{
	Line2D.Double axaOX;
	Line2D.Double axaOY;
	Polygon arrowOX;
	Polygon arrowOY;
	int originXCord;
	int originYCord;
	Ellipse2D.Double origin;
	
	public ReperCartezian(int frameHeight, int frameWidth)
	
	{
		originXCord = frameWidth/2;
		originYCord = frameHeight / 2;
		
		axaOX = new Line2D.Double(5, originYCord, frameWidth - 10, originYCord);
		axaOY = new Line2D.Double(originXCord, 10, originXCord, frameHeight - 10);
		arrowOX = new Polygon();
		arrowOX.addPoint(frameWidth - 5, originYCord);
		arrowOX.addPoint(frameWidth - 20, originYCord - 5);
		arrowOX.addPoint(frameWidth - 20, originYCord + 5);
		
		arrowOY = new Polygon();
		arrowOY.addPoint(originXCord, 5);
		arrowOY.addPoint(originXCord - 5, 20);
		arrowOY.addPoint(originXCord + 5, 20);
		
		origin = new Ellipse2D.Double(originXCord - 5, originYCord - 5, 10,10);
	}
	
	public void paint(Graphics2D g2)
	{
		
		g2.setColor(Color.BLUE);
		g2.draw(axaOX);
		g2.draw(axaOY);
		g2.fill(arrowOX);
		g2.fill(arrowOY);
		g2.draw(arrowOX);
		g2.draw(arrowOY);
		g2.fill(origin);
		g2.draw(origin);
		
	}
}
