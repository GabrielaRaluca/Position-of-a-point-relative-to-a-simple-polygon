package geometrie;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class Frame implements Runnable
{

	JFrame frame;
	JButton buton;
	JButton fisier;
	JButton click;
	JButton clear;
	JButton getPosition;
	
	JOptionPane pane;
	JDialog dialog;
	
	String message;
	String title;
	
	boolean dinFisier;
	boolean clicked;
	
	int height;
	int width;
	double originXCord;
	double originYCord;
	int i;
	ReprezentareGrafica rc;
	
	ArrayList<Punct> poligon;
	ArrayList<Line> diagonale;
	ArrayList<Punct> triangle;
	
	SimplePolygon poly;
	
	Triangulare triangulare;
	PozitiePunct position;
	
	public void run() 
	{
		
		poligon = new ArrayList<Punct>();
		poly = new SimplePolygon();
		frame = new JFrame("Pozitia relativa a unui punct fata de un poligon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	
		title = "Pozitie punct";
		
		height = 1000;
		width = 1800;
		
		originXCord = width / 2;
		originYCord = height / 2;
		
		frame.setSize(width, height);
		addComponents();
		
		
		addButonDiagonale();
		addButonPozitie();

	}
	public void addButonPozitie()
	{
		getPosition.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				start2(getPosition);
			}
			
		});
	}
	
	public void start2(JButton b) // un thread nou pentru pozitia punctului, care necesita
	{ // o noua triangulare in care retinem triunghiuri, nu diagonale
		SwingWorker <Void, ArrayList<Punct> > worker = new SwingWorker<Void, ArrayList<Punct > > ()
				{  

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						if (poly.isSimple(rc.poligon)) // daca e simplu
						{
							triangulare = new Triangulare(rc); 
							ArrayList<Punct> punctePoligon = new ArrayList<Punct>();
						
							punctePoligon.addAll(rc.points);
							Punct x = punctePoligon.get(punctePoligon.size() - 1);
							punctePoligon.remove(punctePoligon.size() - 1);
							position = new PozitiePunct(triangulare); 
							triangle = position.getPosition(punctePoligon, x);
						//in triangle retinem triunghiul in care s-a gasit punctul x, care e 
							//ultimul adaugat in vectorul de puncte, adica ultimul click dat
							//sau ultimul punct citit din fisier
						publish(triangle);
						}
						return null;
					}
					
					protected void process(List< ArrayList<Punct> > list)
			        {
						if (triangle != null)
							{	//copiem triunghiul in campul triangle al reprezentarii ptr a fi desenat
							//cu verde
								for (i = 0; i < triangle.size(); i++)
									rc.triangle.add(triangle.get(i));
								
								rc.repaint();
								message = new String("Punctul se afla in triunghiul colorat cu verde");
								pane = new JOptionPane(message);
								dialog= pane.createDialog(frame, title);
								dialog.setVisible(true);
							}
						

						else if(triangle == null && PozitiePunct.onSegment)
						{ // daca nu s-a adaugat nimic in triangle iar punctul e pe o latura
							//a POLIGONULUI
							message = new String("Punctul se afla pe o latura a poligonului");
							pane = new JOptionPane(message);
							dialog= pane.createDialog(frame, title);
							dialog.setVisible(true);
						}
						
						else //if(triangle == null && !PozitiePunct.onSegment)
						{ //altfel, daca nu e nimic in poligon si punctul nu e nici pe latura
							message = new String("Punctul se afla in afara poligonului");
							pane = new JOptionPane(message);
							dialog= pane.createDialog(frame, title);
							dialog.setVisible(true);
						}
						
			        }
					
				};
				worker.execute();
	}
	
	public void addButonDiagonale()
	{
		buton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent event)
			{
				start(buton);
			}
			
		});
	}
	
	public void start(JButton b)
	{
		SwingWorker <Void, ArrayList<Line> > worker = new SwingWorker<Void, ArrayList<Line > > ()
				{  //un nou thread ptr triangularea propriuzisa
			//retinem un vector de linii[diagonalele]
			//si le desenam

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						if (poly.isSimple(rc.poligon))
						{
							triangulare = new Triangulare(rc);
							if( dinFisier ) 
							{//daca am citit din fisier, facem triangularea doar intre primele n-1 puncte
								//ptr ca ultimul e cel a carui pozitie o vom cauta
								ArrayList<Punct> temp = new ArrayList<Punct>();
								temp.addAll(rc.points);
								temp.remove(temp.size() - 1);
								diagonale = triangulare.getDiagonals(temp);
							}
							else
								
								diagonale = triangulare.getDiagonals(rc.points);
						}
						else
						{ //daca poligonul nu e simplu...
							message = new String("Poligonul nu este simplu, deci nu poate fi triangulat.");
							pane = new JOptionPane(message);
							dialog= pane.createDialog(frame, title);
							dialog.setVisible(true);
						}
							publish(diagonale);

						return null;
					}
					
					protected void process(List< ArrayList<Line> > list)
			        { //copiem diagonalele in vectorul de linii din reprezentare grafica pt a fi desenat
						if (diagonale != null){
						for(i = 0; i < diagonale.size(); i++)
			        	{
			        		rc.adauga(diagonale.get(i));
							rc.repaint();
			        	}
						diagonale.clear();}
			        }
				};
				worker.execute();
	}
	
	public void initialize () throws FileNotFoundException
	{ //citim din fisier
		Scanner input  = new Scanner(new File("fisier.in"));
		while(input.hasNextInt())
		{
			poligon.add(new Punct(input.nextDouble() + originXCord, originYCord - input.nextDouble()));
		}
		
		
		rc.points.clear();
		rc.poligon.clear();
		rc.points.addAll(poligon);
		input.close();
	}
	
	public void addComponents()
	{
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		rc = new ReprezentareGrafica(height, width);
		
		
		buton = new JButton("Triangulare");
		getPosition = new JButton("Pozitia Punctului");
	
		
		fisier = new JButton("Citeste din fisier");
		fisier.addActionListener(new ActionListener(){
			//butonul care deseneaza poligonul dat in fisier
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dinFisier = true; // am citit din fisier
				clicked = false; // nu cu click-uri
				
				poligon.clear();
				rc.linii.clear();
				try {
					initialize();
				} catch (FileNotFoundException es) {
					// TODO Auto-generated catch block
					es.printStackTrace();
				}
				
				ArrayList<Punct> temp = new ArrayList<Punct>();
				Punct p;
				temp.addAll(rc.points);
				p = temp.get(temp.size() - 1);
				temp.remove(temp.size() - 1);
				if(!Order.isCounterClockwise(temp))
				{ //daca nu sunt date in ordine trigonometrica punctele, dam reverse doar primelor n-1
					//ptr ca doar intre ele se va face triangularea daca se citeste din fisier
					Collections.reverse(temp);
					rc.points.clear();
					rc.points.addAll(temp);
					rc.points.add(p);
				}
				
				for(i = 0; i < rc.points.size() - 2; i++)  //DESENAM POLIGONUL 
				{
					Punct l = rc.points.get(i);
					Punct r = rc.points.get(i + 1);
					rc.creeazaPoligon(l.xCord,  l.yCord,r.xCord,  r.yCord);
					rc.repaint();
				}
				
				Punct l = rc.points.get(poligon.size() - 2);
				Punct r = rc.points.get(0);
				rc.creeazaPoligon( l.xCord,  l.yCord, r.xCord,  r.yCord);
				rc.repaint();
			}
			
		});
		
		click = new JButton ("Creeaza poligon");
		click.addActionListener(new ActionListener(){
			//butonul care deseneaza poligonul dat din click-uri
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dinFisier = false;
				clicked = true;
				
				rc.poligon.clear();
				if(!Order.isCounterClockwise(rc.points))
					Collections.reverse(rc.points);
				
				if(rc.points.size() > 1)
				{
					for(i = 0; i < rc.points.size() - 1; i++)  //DESENAM POLIGONUL 
					{
						Punct l = rc.points.get(i);
						Punct r = rc.points.get(i + 1);
						rc.creeazaPoligon( l.xCord,  l.yCord,  r.xCord,  r.yCord);
						rc.repaint();
					}
				
					Punct l = rc.points.get(rc.points.size() - 1);
					Punct r = rc.points.get(0);
					rc.creeazaPoligon( l.xCord,  l.yCord,  r.xCord,  r.yCord);
					rc.linii.clear();
					rc.repaint();
				}	
			}
			
		});
		
		clear = new JButton("Clear");
		clear.addActionListener(new ActionListener(){
			//butonul de sters tot de pe tabla
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rc.points.clear();
				rc.poligon.clear();
				rc.linii.clear();
				rc.triangle.clear();
				rc.repaint();
			}
			
		});
		
		constraints = getConstraints(0, 1, 1, 1, GridBagConstraints.BOTH);
		constraints.gridwidth = 5;
		frame.add(rc, constraints);
		
		buton.setBackground(Color.WHITE);
		constraints = getConstraints(0, 0, 0.5, 0, GridBagConstraints.HORIZONTAL);
		frame.add(buton, constraints);
		
		click.setBackground(Color.WHITE);
		constraints = getConstraints(1, 0, 0.5, 0, GridBagConstraints.HORIZONTAL);
		frame.add(click, constraints);
				
		fisier.setBackground(Color.WHITE);
		constraints = getConstraints(2, 0, 0.5, 0, GridBagConstraints.HORIZONTAL);
		frame.add(fisier, constraints);
		
		clear.setBackground(Color.WHITE);
		constraints = getConstraints(3, 0, 0.5, 0, GridBagConstraints.HORIZONTAL);
		frame.add(clear, constraints);
		
		getPosition.setBackground(Color.WHITE);
		constraints = getConstraints(4, 0, 0.5, 0, GridBagConstraints.HORIZONTAL);
		frame.add(getPosition, constraints);
	}
	
	public GridBagConstraints getConstraints(int x, int y, double weightx, int weighty, int fill)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx  = x;
		c.gridy = y;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = fill;
		
		return c;
	}

}
