import java.awt.Point;


public class Main {

	public static void main(String[] args) {
		int n0 = 0;
		int n1 = 2;
		int n2 = 8;
		int n3 = 3;
		
		int input = 2;
		switch (input) {
		case 0:
			System.out.println(n0);
		}
	
		int[] n = {0,2,8,3};
		System.out.println(n[input]);
		
		Point[] points = getPoints();
		Point[] target = getPoints();
		
		points[0].distance(target[0]);
	}
	
	public static Point[] getPoints() {
		return new Point[4];
	}
}