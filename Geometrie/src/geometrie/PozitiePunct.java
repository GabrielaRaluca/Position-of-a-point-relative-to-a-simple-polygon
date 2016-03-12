package geometrie;

import java.util.ArrayList;

public class PozitiePunct 
{
	Triangulare triangulare;
	static boolean onSegment;
	
	public PozitiePunct() {}
	public PozitiePunct(Triangulare t)
	{
		triangulare = t;
		onSegment = false;
	}
	
	ArrayList<Punct> getPosition(ArrayList<Punct> points, Punct pct)
	{
		//returneaza un triunghi in care a gasit punctul
		// null daca nu e in poligon
		// null si onSegment = true daca e pe vreo latura a poligonului NU a vreunui triungi
		int i, j;
		double arie, arie1, arie2, arie3;
		ArrayList<Punct> triangle = new ArrayList<Punct>();
		ArrayList<Punct> p = new ArrayList<Punct>();
		p.addAll(points);
		Punct searchedPoint = new Punct(pct.xCord, pct.yCord);
		
		ArrayList<ArrayList<Punct>> triangles = triangulare.getTriangles(p);
		for (j = 0; j < triangles.size(); j++)
		{
			triangle =  new ArrayList<Punct>();
			triangle.addAll(triangles.get(j));
			arie = Math.abs(triangulare.determinant(triangle.get(0), triangle.get(1), triangle.get(2)));
			
			arie1 = Math.abs(triangulare.determinant(triangle.get(0), triangle.get(1), searchedPoint));
			arie2 = Math.abs(triangulare.determinant(triangle.get(0), triangle.get(2),searchedPoint));
			arie3 = Math.abs(triangulare.determinant(triangle.get(1), triangle.get(2), searchedPoint));
			
			if(arie1 == 0) // daca 3 puncte sunt coliniare
			{
				Line segment = new Line(triangle.get(0).xCord, triangle.get(0).yCord, triangle.get(1).xCord, triangle.get(1).yCord);
				
				for(i = 0; i < triangulare.rc.poligon.size(); i++)
				{
					if(triangulare.rc.poligon.get(i).equals(segment))
					{
						onSegment = true;
						break;
					}
				}
				
			}
			
			if(arie2 == 0) // daca 3 puncte sunt coliniare
			{
				Line segment = new Line(triangle.get(0).xCord, triangle.get(0).yCord, triangle.get(2).xCord, triangle.get(2).yCord);
				
				for(i = 0; i < triangulare.rc.poligon.size(); i++)
				{
					if(triangulare.rc.poligon.get(i).equals(segment))
					{
						onSegment = true;
						break;
					}
				}
			}
			
			if(arie3 == 0) // daca 3 puncte sunt coliniare
			{
				Line segment = new Line(triangle.get(1).xCord, triangle.get(1).yCord, triangle.get(2).xCord, triangle.get(2).yCord);
				
				for(i = 0; i < triangulare.rc.poligon.size(); i++)
				{
					if(triangulare.rc.poligon.get(i).equals(segment) )
					{
						onSegment = true;
						break;
					}
				}
			}
			
			
			if(arie == (arie1 + arie2 + arie3) && !onSegment)
			{
				return triangle;
			}
		}
		return null;
	}
}
