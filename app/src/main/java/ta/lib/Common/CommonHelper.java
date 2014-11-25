package ta.lib.Common;

public class CommonHelper
{
	private CommonHelper() { }

	public static String GetExceptionName(Object e)
	{
		String name = e.getClass().getName();
		String result = ClassFromFullName(name);
		return result;
	}

	public static String ClassFromFullName(String name)
	{
		String result = "";
		if(name != null && name != "")
		{
			int index = name.lastIndexOf(".");
			if(index != -1 )
			{
				String target = ".";
				String replacement = "";
				result = name.substring(index).replace(target, replacement);
			}
		}
		return result;
	}

	public static String CreateMessageForException(String str)
	{
		String res = "\n*********\n" +    "\texception in"    +  "\n*********\n" +    "\t"+str;
		return res;
	}

	public static String TextForException(Throwable thr)
	{
		Throwable e = thr.getCause();
		StackTraceElement[] arr = e.getStackTrace();
		int count = 0;
		if(arr.length < 5)
			count = arr.length;
		else
			count = 5;

		String res = "*********\n\t" + GetExceptionName(e) + "\n*********\n\n";

		for(int i = 0; i < count;i++)
		{
			StackTraceElement ste = arr[i];
			String file = ste.getFileName();
			String cls = ClassFromFullName(ste.getClassName());
			String method = ste.getMethodName();

			//res = res + String.valueOf(i+1) + "-" + method + "-" + file + "\n\n";
			res = res + String.valueOf(i+1) + "-" + file + "\n";
			res = res + "    " + cls+"::"+method + "\n\n";
		}
		return res;
	}
}