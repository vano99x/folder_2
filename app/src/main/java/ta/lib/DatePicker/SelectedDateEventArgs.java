package ta.lib.DatePicker;

import ta.lib.Common.*;

public class SelectedDateEventArgs
{
	public SelectedDateEventArgs(DateTime d,String n)
	{
		this.dt = d;
		this.name = n;
	}

	public DateTime dt;
	public String name;
}