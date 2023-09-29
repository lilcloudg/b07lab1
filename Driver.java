import java.io.File;
import java.io.FileNotFoundException;;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {1,2,3};
        int [] e1 = {0,1,2};
		Polynomial p1 = new Polynomial(c1,e1);
		double [] c2 = {1,-3,2};
        int [] e2 = {0,2,3};
		Polynomial p2 = new Polynomial(c2,e2);
		Polynomial s1 = p1.add(p2);
		System.out.println("s1(0.1) = " + s1.evaluate(0.1));
        Polynomial s2 = p1.multiply(p2);
        System.out.println("s2(0.1) = " + s2.evaluate(0.1));
		if(s2.hasRoot(1))
			System.out.println("1 is a root of s2");
		else
			System.out.println("1 is not a root of s2");
		s1.saveToFile("s1.txt");
		s2.saveToFile("s2.txt");
		try {
			File file = new File("s1.txt");
			Polynomial p3 = new Polynomial(file);
			System.out.println("p3(0.1) = " + p3.evaluate(0.1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}