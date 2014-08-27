/*package ta.lib;

import java.util.ArrayList;
import java.lang.Runnable;
import ta.lib.*;

public class Event<T extends RunnableWithArgs>
//public class Event<T extends RunnableWithArgs<TArg>, TArg>
{
	ArrayList<T> listeners;

	public Event()
	{
		this.listeners = new ArrayList<T>();
	}

    public void Add(T toAdd)
	{
        this.listeners.add(toAdd);
    }

    public void RunEvent2(Object[] eventParams, String name)
	{
		//if(name != null)
		//{
		//    UIHelper.Instance().Toast("aaa \n RunEvent", 3);
		//}
	    //this.RunEvent( eventParams );
    	
		//int count = this.listeners.size();
		//if(count == 0)
		//{
		//    UIHelper.Instance().Toast("aaa \n emply Event", 3);
		//}
		//else
		//{
		//    UIHelper.Instance().Toast("aaa \n Event exist", 3);
		//}
	    this.RunEvent( eventParams );
	}

	public void RunEvent(Object eventParams)
	{
		this.RunEvent( new Object[]{ eventParams });
	}

	public void RunEvent(Object[] eventParams)
	{
		int aaa = 9;
        // Notify everybody that may be interested.
        for (T item : this.listeners)
		{
			item.result = eventParams;
            item.run();
		}
    }
}*/