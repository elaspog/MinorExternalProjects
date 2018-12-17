import java.util.concurrent.ThreadLocalRandom;

public class TimeRandomizer {
	
	private int min;
	private int max;
	
	public TimeRandomizer (int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int RandomTime() {
		return RandomTimeBetweenRange(min, max);
	}
	
	public static int RandomTimeBetweenRange(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

}
