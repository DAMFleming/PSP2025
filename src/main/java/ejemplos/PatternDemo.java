package ejemplos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternDemo {

	public static void main(String[] args) {
		String s = "    5,57   *    10.21 ";
		Pattern p = Pattern.compile("\\s*(\\d*([,\\.])\\d+)\\s*([\\+\\-\\*/])\\s*(\\d*\\2\\d+)\\s*");
		Matcher m = p.matcher(s);
		if (m.matches()) {
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}
	}

}
