package ta.lib.Common;

import ta.lib.DatePicker.IIntervalDateTimeProvider;

public class SimpleIntervalDateTimeProvider implements IIntervalDateTimeProvider
{
	private DateTime _left;
	private DateTime _right;

	public SimpleIntervalDateTimeProvider(
		//DateTime left,DateTime right
		)
	{
		//_left = left;
		//_right = right;
	}

	public DateTime GetDateTimeLeft()
	{
		return _left;
	}
	public void set_DateTimeLeft(DateTime value)
	{
		_left = value;
	}

	public DateTime GetDateTimeRight()
	{
		return _right;
	}
	public void set_DateTimeRight(DateTime value)
	{
		_right = value;
	}
}
