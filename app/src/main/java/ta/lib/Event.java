package ta.lib;

import java.util.ArrayList;
import java.lang.Runnable;

import ta.lib.*;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.Type;

public class Event<TArg,TRes>
{
	ArrayList<RunnableWithArgs<TArg,TRes>> listeners;

	public Event()
	{
		this.listeners = new ArrayList<RunnableWithArgs<TArg,TRes>>();
	}
	
	private String __eventMarker;
	private String get_EventMarker(Object obj)
	{
		if(this.__eventMarker == null)
		{
			ParameterizedType ptEv = (ParameterizedType)(obj.getClass().getGenericSuperclass());
			Type [] typeArr = ptEv.getActualTypeArguments();
			String str1 = Event.GetStrByType(typeArr[0]);
			String str2 = Event.GetStrByType(typeArr[1]);

			this.__eventMarker = str1 + str2;
		}
		return this.__eventMarker;
	}


	private Object __eventTypeHolder;
	public void set_EventTypeHolder(Object eventTypeHolder)
	{
		this.__eventTypeHolder = eventTypeHolder;
	}
	public Object get_EventTypeHolder()
	{
		if(this.__eventTypeHolder == null)
		{
			this.__eventTypeHolder = this;
		}
		return this.__eventTypeHolder;
	}


	private static String GetStrByType(Type t)
	{
		java.lang.Class                    cl  = null;
		java.lang.reflect.GenericArrayType gat = null;
		String res = null;

		if(        ( cl  = operator.as(java.lang.Class.class,                    t)) != null   )
		{
			res = cl.getName();
		}
		else if(   ( gat = operator.as(java.lang.reflect.GenericArrayType.class, t)) != null   )
		{
			Class gct = (Class)gat.getGenericComponentType();
			res = gct.getName() + "[]";
		}
		
		return res;
	}

	private static <TArg,TRes> String GetHandlerMarker(RunnableWithArgs<TArg,TRes> toAdd)
	{
		String res = null;
		try
		{
		Class type = ((Object)toAdd).getClass();
		//Class baseType = type.getSuperclass();
		//TypeVariable [] typeArr = baseType.getTypeParameters();
		ParameterizedType pt = (ParameterizedType)(type.getGenericSuperclass());
		Type [] typeArr = pt.getActualTypeArguments();

		String str1 = Event.GetStrByType(typeArr[0]);
		String str2 = Event.GetStrByType(typeArr[1]);

		res = str1 + str2;
		}
		catch(Exception e)
		{
			Exception ex = e;
		}
		return res;
	}


                                  //<TArg,TRes>
	public void Add(RunnableWithArgs            handler)
	{
		int aaa = 9;
		int aaa2 = 9 - 2;

		String hm = Event.GetHandlerMarker(handler);
		Object typeHolder = this.get_EventTypeHolder();
		String vm = this.get_EventMarker(typeHolder);

		if(vm.equals(hm)){
			this.listeners.add(handler);
		} else {
			throw new RuntimeException( ta.lib.Common.CommonHelper.CreateMessageForException("ta.lib.Event.Add") );
			//int [] Exception = new int[]{0};    //int throW = Exception[1];
		}
	}

    public void RunEvent()
    {
        for (RunnableWithArgs<TArg,TRes> item : this.listeners)
        {
            item.run();
        }
    }

	public void RunEvent(TArg eventParams)
	{
		int aaa = 9;
		// Notify everybody that may be interested.
		for (RunnableWithArgs<TArg,TRes> item : this.listeners)
		{
			item.arg = eventParams;
			//item.result = true;
			item.run();
		}
	}

	public void RunEvent(TArg eventParams, TRes eventParams2)
	{
		// Notify everybody that may be interested.
		for (RunnableWithArgs<TArg,TRes> item : this.listeners)
		{
			item.arg = eventParams;
			item.result = eventParams2;
			item.run();

			int aaa = 9;
			int aaa2 = 9-2;
		}
	}
}