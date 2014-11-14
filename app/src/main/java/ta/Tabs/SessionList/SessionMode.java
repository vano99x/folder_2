package ta.Tabs.SessionList;


	public enum SessionMode
	{
		ArrivalOk(      "ArrivalOk",       0),
		DepartureOk(    "DepartureOk",     1),
		DepartureMiss(  "DepartureMiss",   2);

		private String _strString;
		private int    _intString;
		SessionMode(String var1, int var2)
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
