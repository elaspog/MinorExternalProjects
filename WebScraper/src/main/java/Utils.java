
public class Utils {

	public static String cleanString(String rawString) {
		
		return rawString.replaceAll("[^\\-\\+ _A-Za-z0-9]+","");
	}
}
