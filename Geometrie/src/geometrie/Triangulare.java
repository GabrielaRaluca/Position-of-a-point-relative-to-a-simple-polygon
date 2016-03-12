package geometrie;

import java.util.ArrayList;


public class Triangulare
{
	ReprezentareGrafica rc;
	
	int originXCord;
	int originYCord;
	
	public Triangulare()
	{}
	
	public Triangulare(ReprezentareGrafica o)
	{
		
		this.rc = o;
		
		originXCord = rc.rc.originXCord;
		originYCord = rc.rc.originYCord;
	}
	
	ArrayList<Line> getDiagonals (ArrayList<Punct> p)
	{
		
		ArrayList<Punct> points = new ArrayList<Punct>();
		points.addAll(p);
		ArrayList<Line> diagonale = new ArrayList<Line>();
		boolean principal = true; //pp ca e principal
		double delta;
		Punct P1, P2, P3;
		int i;
		int n;
		n = points.size();
		while(n > 3)
		{
			principal = true;
			P3 = points.get(n - 1);
			P2 = points.get(n - 2);
			P1 = points.get(n - 3);
			double arie1, arie2, arie3, arie;
			delta = determinant(P1, P2, P3);
			if(delta > 0) //daca varful e convex
			{
				for(i = 0; i < n - 3; i ++)
				{
					
						arie1 = Math.abs(determinant(P1, P2, points.get(i)));
						arie2 = Math.abs(determinant(P1, P3, points.get(i)));
						arie3 = Math.abs(determinant(P2, P3, points.get(i)));
						arie = Math.abs(determinant(P1, P2, P3));
						
						if(arie == arie1 + arie2 + arie3 ) //daca vreun punct al poligonului se afla in triunghiul
							//format de cele 3 puncte
							{
								principal = false;
								break;
							}
				}
				
				if(principal)
				{ //p2 principal convex deci e ureche, deci trebuie eliminata, si adaugata diagonala
					diagonale.add(new Line( P1.xCord,  P1.yCord, P3.xCord, P3.yCord));
					points.remove(n - 2);
				}
				
				else //nu e principal dar e convex
				{
					points.remove(n - 1);
					points.add(0, P3); //scoatem varful precedent si ne vom ocupa de el la sfarsit, cand ajungem la poz 0
					
				}
			}
			
			else // nu e convex
			{
				points.remove(n - 1);
				points.add(0, P3);
			}
			
			n = points.size();
		}
		
		return diagonale;
	}
	
	ArrayList<ArrayList<Punct>> getTriangles (ArrayList<Punct> p)
	{
		
		ArrayList<Punct> points = new ArrayList<Punct>();
		points.addAll(p);
		ArrayList<ArrayList<Punct>> triangles = new ArrayList<ArrayList<Punct>>();
		boolean principal = true; //pp ca e principal
		double delta;
		Punct P1, P2, P3;
		int i;
		int n;
		n = points.size();
		while(n > 2)
		{
			principal = true;
			P3 = points.get(n - 1);
			P2 = points.get(n - 2);
			P1 = points.get(n - 3);
			double arie1, arie2, arie3, arie;
			delta = determinant(P1, P2, P3);
			if(delta > 0) //daca varful e convex
			{
				for(i = 0; i < n - 3; i ++)
				{
					
						arie1 = Math.abs(determinant(P1, P2, points.get(i)));
						arie2 = Math.abs(determinant(P1, P3, points.get(i)));
						arie3 = Math.abs(determinant(P2, P3, points.get(i)));
						arie = Math.abs(determinant(P1, P2, P3));
						
						if(arie == arie1 + arie2 + arie3 ) //daca vreun punct al poligonului se afla in triunghiul
							//format de cele 3 puncte
							{
								principal = false;
								break;
							}
					
				}
				
				if(principal)
				{ //p2 principal convex deci e ureche, deci trebuie eliminata, si adaugata diagonala
					ArrayList<Punct> triangle = new ArrayList<Punct>();
					triangle.add(new Punct(P1.xCord , P1.yCord));
					triangle.add(new Punct(P2.xCord ,  P2.yCord));
					triangle.add(new Punct(P3.xCord ,  P3.yCord));
					triangles.add(triangle);
					
					points.remove(n - 2);
				}
				
				else //nu e principal dar e convex
				{
					points.remove(n - 1);
					points.add(0, P3); //scoatem varful precedent si ne vom ocupa de el la sfarsit, cand ajungem la poz 0
					
				}
			}
			
			else // nu e convex
			{
				points.remove(n - 1);
				points.add(0, P3);
			}
			
			n = points.size();
		}
		
		return triangles;
	}
	
	public double determinant(Punct a, Punct b, Punct c)
	{
		return (a.xCord * b.yCord + c.xCord * a.yCord + b.xCord * c.yCord - c.xCord * b.yCord - a.xCord * c.yCord - b.xCord * a.yCord);
	}
}
