package geometrie;

import java.util.ArrayList;

public class SimplePolygon 
{
	public Punct punctIntersectie(Punct a, Punct b, Punct c, Punct d) //calculeaza coordonatele punctului de intersectie a dreptelor
	{
		Punct M = new Punct();
		double a1, a2, b1, b2, c1, c2;

		a1 = a.yCord - b.yCord;
		b1 = b.xCord - a.xCord;
		c1 = a.xCord * b.yCord - a.yCord * b.xCord;

		a2 = c.yCord - d.yCord;
		b2 = d.xCord - c.xCord;
		c2 = c.xCord * d.yCord - c.yCord * d.xCord;

		M.xCord = ((-c1) * b2 - b1 * (-c2)) / (a1 * b2 - a2 * b1);
		M.yCord = (a1 * (-c2) - a2 * (-c1)) / (a1 * b2 - a2 * b1);

		return M;
	}
	
	public boolean isSimple(ArrayList<Line> poligon)
	{ // luam fiecare dreapta cu celelalte, mai putin urmatoarea si precedenta si verificam punctele de intersectie
		Line l1, l2;
		Punct  p1, p2, p3, p4;
		int i, j;
		
		l1 = poligon.get(0);
		for (j = 2; j < poligon.size() - 1; j++)
		{
			l2 = poligon.get(j);
			p1 = new Punct(l1.line.x1, l1.line.y1);
			p2 = new Punct(l1.line.x2, l1.line.y2);
			p3 = new Punct(l2.line.x1, l2.line.y1);
			p4 = new Punct(l2.line.x2, l2.line.y2);
			//if (rang(p1, p2, p3, p4) != 2) // daca 2 drepte nu sunt paralele
			//{
					if(apartineSegmentelor(p1, p2, p3, p4))
						return false;
			//}
		}
		
		for (i = 1; i < poligon.size() - 1; i++)
		{
			l1 = poligon.get(i);
			for (j = i + 2; j < poligon.size(); j++)
			{
				l2 = poligon.get(j);
				p1 = new Punct(l1.line.x1, l1.line.y1);
				p2 = new Punct(l1.line.x2, l1.line.y2);
				p3 = new Punct(l2.line.x1, l2.line.y1);
				p4 = new Punct(l2.line.x2, l2.line.y2);
				//if (rang(p1, p2, p3, p4) != 2) // daca 2 drepte nu sunt paralele
				//{
						if(apartineSegmentelor(p1, p2, p3, p4))
							return false;
				//}
			}	
		}
		return true;
	}
	
	int rang(Punct a, Punct b, Punct c, Punct d)
	{
		double a1, a2, b1, b2, c1, c2;

		a1 = a.yCord - b.yCord;
		b1 = b.xCord - a.xCord;
		c1 = a.xCord * b.yCord - a.yCord * b.xCord;

		a2 = c.yCord - d.yCord;
		b2 = d.xCord - c.xCord;
		c2 = c.xCord * d.yCord - c.yCord * d.xCord;

		if (a1 * b2 - a2 * b1 != 0) //ecuatia are solutie unica -> un punct in intersectie
			return 0; //sol unica
		
		else
		{
			//calc rangul matricei extinse
			if (a1 * c2 - a2 * c1 != 0 || b1 *c2 - b2 * c1 != 0)
				return 2; //rangul matricei extinse e 2 != rangul matricei mici = > sunt paralele
			else
				return 1; //rangul matricei extinse e 1 = rangulmatricei mici -> dreptele coincid

		}
	}
	
	boolean apartineSegmentelor(Punct A, Punct B, Punct C, Punct D)
	{
		if (rang(A, B, C, D) == 0)
		{	
			Punct M;
			boolean ok1 = false, ok2 = false;
			
			M = punctIntersectie(A, B, C, D);

			//verificam daca M apartine ambelor segmente
			if (A.xCord != B.xCord) //daca x-ii sunt diferiti
			{
				if ((M.xCord >= A.xCord && M.xCord <= B.xCord) || (M.xCord <= A.xCord && M.xCord >= B.xCord))
					ok1 = true;
				if ((M.xCord >= C.xCord && M.xCord <= D.xCord) || (M.xCord <= C.xCord && M.xCord >= D.xCord))
					ok2 = true;

				if (ok1 && ok2)
					return true;
			}
			else
			{ //daca x-ii sunt aceiasi, verificam dupa y
				if ((M.yCord >= A.yCord && M.yCord <= B.yCord) || (M.yCord <= A.yCord && M.yCord >= B.yCord))
					ok1 = true;
				if ((M.yCord >= C.yCord && M.yCord <= D.yCord) || (M.yCord <= C.yCord && M.yCord >= D.yCord))
					ok2 = true;

				if (ok1 && ok2)
					return true;
			}
			
			return false;
		}
		
		return false;
	}
}
