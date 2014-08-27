package ta.lib;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.Arrays;

public class operator
{
	public static <T> T as(Class<T> clazz, Object o)
	{
		if(clazz.isInstance(o))
		{
			return clazz.cast(o);
		}
		return null;
	}

	public static <PT,RT> ArrayList<RT> Where( //Class<String[]> classRes, Class<String[]> classParam, 
		ArrayList<PT> paramList//Iterable
		,WhereRunnable<PT,RT> filter
		)
	{
		ArrayList<RT> returnList = new ArrayList<RT>();
		for(PT itemPT : paramList)
		{
			RT itemRT = filter.run(itemPT);
			if(itemRT != null)
			{
				returnList.add(itemRT);
			}
		}
		return returnList;
	}

	//public static <PT,RT> ArrayList<RT> W(){

		public static <PT,RT> ArrayList<RT> Where(
			PT[] paramList,
			WhereRunnable<PT,RT> filter
			)
		{
			ArrayList<RT> returnList = new ArrayList<RT>();
			int count = paramList.length;
			for( int i = 0; i < count; i++)
			{
				RT itemRT = filter.run(paramList[i]);
				if(itemRT != null)
				{
					returnList.add(itemRT);
				}
			}
			return returnList;
		}

		public interface WhereRunnable<PT,RT>
		{
			RT run(PT param);
		}
	//}

	//public static <T> void Delegate()
	//{
	//T obj = new T();
	//}
}




