package bmw.lbs.demo;

import java.math.BigDecimal;

public class Util {
	
	/**
	 * Convert a string to long, also support scientific notation
	 * 
	 * @param time
	 * @return
	 */
	
	public static Long ConvertToLong(String time) {
		Long value = 0L;
		try {	
			value = new BigDecimal(time).longValue();			
		}
		catch(NumberFormatException e) {			
		}	
		
		return value;
	}
}
