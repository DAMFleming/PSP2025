package ejemplos;

import java.util.Scanner;

public class ScannerDemo {

	public static void main(String[] args) {
//		String s = "5,57 + \n\n\n\t   \n   10,21";
		String s = "5,57*10,21";
		Scanner scanner = new Scanner(s);
//		System.out.println(scanner.nextDouble());
//		System.out.println(scanner.next("\\+"));
//		System.out.println(scanner.nextFloat());
		scanner.skip("\\d*,\\d+");
		System.out.println(scanner.match().group());
		scanner.skip("[\\+\\-\\*/]");
		System.out.println(scanner.match().group());
		scanner.skip("\\d*,\\d+");
		System.out.println(scanner.match().group());
	}

}
