package code.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Board {
	private ArrayList<ArrayList<String>> _board;
	private ArrayList<String> _colorFileNames;
	private Random _rand;
	private int _sum;
	private static int MAX_COLORS = 6; // max possible is 6

	public Board(int rows, int cols) {
			
		_sum=0;
		
		
		_board = randomBoard(rows,cols);
		
		while(!valid())
		{ 
			_board=randomBoard(rows,cols);
		}	
		
		System.out.println("The score "+_sum);

	}
	private boolean valid() {
		
		int num=match().size();
		return !(num>0)&&(hasMove().size()>0);
	}
	
	
	public HashSet<Point> hasMove()
	{ 
		HashSet<Point> hints = new HashSet<Point>();
		
		for(int row=0;row<rows()-1;row++ )
		{ 
			for(int col=0;col<cols();col++)
			{ 
				Point p=new Point(row,col);
				Point q=new Point(row+1,col);
			
				String temp = get(p);
				set(p, get(q));
				set(q, temp);
				
				 if(match().size()>0)
				 { 
					 hints.add(p);
					
				 }
				
				String temp2 = get(p);
				set(p, get(q));
				set(q, temp2);
				
				
			}
		}
		for(int row=0;row<rows();row++)
		{ 
			for(int col=0;col<cols()-1;col++)
			{ 
				Point p=new Point(row,col);
				Point q=new Point(row,col+1);
			
				String temp = get(p);
				set(p, get(q));
				set(q, temp);
				
				if(match().size()>0)
				 { 
					 hints.add(p);
				 }
				
				String temp2 = get(p);
				set(p, get(q));
				set(q, temp2);
			
			}
		}	
		
		
		return hints;
	}
	
	private ArrayList<ArrayList<String>> randomBoard(int rows, int cols)
	{ 
		
		ArrayList<ArrayList<String>> ran=new ArrayList<ArrayList<String>>();
		_rand = new Random();
		_colorFileNames = new ArrayList<String>();
		for (int i=0; i<MAX_COLORS; i=i+1) {
			_colorFileNames.add("Images/Tile-"+i+".png");
		}
		for (int r=0; r<rows; r=r+1) {
			ArrayList<String> row = new ArrayList<String>();
			for (int c=0; c<cols; c=c+1) {
				row.add(_colorFileNames.get(_rand.nextInt(_colorFileNames.size())));
			}
			ran.add(row);
		}
		
		return ran;
	}
	public int rows() { return _board.size(); }
	public int cols() { return _board.get(0).size(); }

	public String get(Point p) {
		return _board.get(p.x).get(p.y);
	}

	private String set(Point p, String s) {
	
		return _board.get(p.x).set(p.y, s);
	}

	public void exchange(Point p, Point q) {
		String temp = get(p);
		set(p, get(q));
		set(q, temp);
		if (match().size() > 0) {
			//System.out.println("The board has a match.");
			
		}
		else {
			
		}
		
	}
	
	private HashSet<Point> match() {
		return match(3);
	}

	private HashSet<Point> match(int runLength) {
		HashSet<Point> matches = verticalMatch(runLength);
		matches.addAll(horizontalMatch(runLength));
		return matches;
	}

	private HashSet<Point> horizontalMatch(int runLength) {
		HashSet<Point> matches = new HashSet<Point>();
		int minCol = 0;
		int maxCol = cols() - runLength;
		for (int r = 0; r < rows(); r = r + 1) {
			for (int c = minCol; c <= maxCol; c = c + 1) {  // The cols we can START checking in
				HashSet<String> values = new HashSet<String>();
				HashSet<Point> points = new HashSet<Point>();
				for (int offset = 0; offset < runLength; offset = offset + 1) {
					Point p = new Point(r,c+offset);
					points.add(p);
					String s = get(p);
					values.add(s);
				}
				if (values.size() == 1) { matches.addAll(points); }
			}
		}
		return matches;
	}

	private HashSet<Point> verticalMatch(int runLength) {
		HashSet<Point> matches = new HashSet<Point>();
		int minRow = 0;
		int maxRow = rows() - runLength;
		for (int c = 0; c < cols(); c = c + 1) {
			for (int r = minRow; r <= maxRow; r = r + 1) {  // The rows we can START checking in
				HashSet<String> values = new HashSet<String>();
				HashSet<Point> points = new HashSet<Point>();
				for (int offset = 0; offset < runLength; offset = offset + 1) {
					Point p = new Point(r+offset,c);
					points.add(p);
					String s = get(p);
					values.add(s);
				}
				if (values.size() == 1) { matches.addAll(points); }
			}
		}
		return matches;
	}
	public boolean checkMove(Point p, Point q) {
	
	int num=match().size();
		String temp = get(p);
		set(p, get(q));
		set(q, temp);
	
	int num2=match().size();
	String temp2 = get(p);
	set(p, get(q));
	set(q, temp2);
	
	if(num2==num)
	{ 
		return false;
	}
	else if(num>num2)
	{
		return false;
	}
	else
	{
	return true;	
	}

	
	
	}
	public void collapseTile() {
		
do{		
		for(Point p:match())
		{ 
			_board.get(p.x).set(p.y,"" );
			
		}
	
		for(int k=0;k<match().size();k++)
		{			
			for(int i=0;i<_board.size();i++)
			{ 
				for(int z=0;z<_board.get(i).size();z++)

				{
					String s=_board.get(i).get(z);

					if(s.equals(""))
					{
						Point p=new Point (i,z);
						Point q=new Point (Math.abs(i-1),z);

						String temp = get(p);

						set(p, get(q));
						set(q, temp);
					}				

				}}
		}
			
		for(int i=0;i<_board.size();i++)
		{ 
			for(int z=0;z<_board.get(i).size();z++)

			{
				String s=_board.get(i).get(z);

				if(s.equals(""))
				{ 
					_sum=_sum+1;
					String test=_colorFileNames.get(_rand.nextInt(_colorFileNames.size()));
					_board.get(i).set(z,test);	

				}
			}}

}while(match().size()>0);

System.out.println("The score "+_sum);


	}


	public int score()
	{ 
		 return _sum;
			
	}

}
