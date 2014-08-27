package ta.lib;

public class DateHelper
{
	private DateHelper()
	{
	}

	public static long AddDays(long dt, int days)
	{
		long newDT = dt + (days * 24 * 60 * 60 * 1000);

		return newDT;
	}
}