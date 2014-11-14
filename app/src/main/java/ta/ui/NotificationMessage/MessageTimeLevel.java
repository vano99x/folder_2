package ta.ui.NotificationMessage;

public enum MessageTimeLevel
{
	Before(      "Before",       0),
	InMoment(      "InMoment",       1),
	After(      "After",       2);

		private String _strString;
		private int    _intString;
		private MessageTimeLevel(String var1, int var2)
		{
			_strString = var1;
			_intString = var2;
		}
		public String toString()
		{
			return _strString;
		}
		public int toInt()
		{
			return _intString;
		}
}
