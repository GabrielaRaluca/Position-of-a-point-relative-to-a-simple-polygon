package geometrie;


import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Line 
{
	Line2D.Double line;
	
	public Line(double leftXCord, double leftYCord, double rightXCord, double rightYCord)
	{
		
		line = new Line2D.Double(leftXCord,  leftYCord, rightXCord, rightYCord);
	}
	
	public void paint(Graphics2D g2)
	{
		g2.draw(line);
	}
	
	public String toString()
	{
		return String.format("%s %s %s %s", line.x1, line.y1, line.x2, line.y2);
	}
	
	public boolean equals(Line l)
	{
		return (((l.line.x1 == this.line.x1 )&&( l.line.x2 == this.line.x2) && (l.line.y1 == this.line.y1) && (l.line.y2 == this.line.y2)) || ((l.line.x1 == this.line.x2) && (l.line.y1 == this.line.y2) && (l.line.x2 == this.line.x1) && (l.line.y2 == this.line.y1)));
	}
		
	
}
