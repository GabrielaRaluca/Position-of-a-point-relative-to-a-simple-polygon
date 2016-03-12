package geometrie;

import java.util.ArrayList;

public class Order 
{
	//folosim shoelace formula
	//daca determinantii dintre coordonatele a 2 pct consecutive in ordinea data
	//sunt negativi, punctele nu sunt date in ordine trigonometrica
	public static boolean isCounterClockwise(ArrayList<Punct> p)
	{
		int i;
		Punct p1, p2;
		double determinant;
		double sum = 0;
		for (i = 0; i < p.size() - 1; i++)
		{
			p1 = p.get(i);
			p2 = p.get(i + 1);
			determinant = p1.xCord * p2.yCord - p1.yCord * p2.xCord;
			//if (determinant < 0)
				//return false;
			sum += determinant;
		}
		
		p1 = p.get(p.size() - 1);
		p2 = p.get(0);
		determinant = p1.xCord * p2.yCord - p1.yCord * p2.xCord;
		//if (determinant < 0)
			//return false;
		sum += determinant;
		if(sum < 0)
			return false;
		return true;
	}
}
