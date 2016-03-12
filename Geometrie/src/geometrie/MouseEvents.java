package geometrie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseEvents implements MouseListener, MouseMotionListener{

	ReprezentareGrafica rc;
	
	public MouseEvents(ReprezentareGrafica r)
	{
		rc = r;
	}
	
	Punct getPoint(MouseEvent e)
	{ //functie care ia pozitia cursorului 
		Punct p;
		double x = e.getX();
		double y =  e.getY();
		p = new Punct(x, y);
		
		return p;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		rc.points.add(getPoint(e));
		rc.repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
