package ta.lib.Common;

public class CommonHelper
{
	public static String GetExceptionName(Exception e)
	{
		String result = null;
		String name = e.getClass().getName();
		int index = name.lastIndexOf(".");
		if(index != -1 )
		{
			String target = ".";
			String replacement = "";
			result = name.substring(index).replace(target, replacement);
		}
		return result;
	}

	public static String CreateMessageForException(String str)
	{
		String res = "\n*********\n" +    "\texception in"    +  "\n*********\n" +    "\t"+str;
		return res;
	}
}