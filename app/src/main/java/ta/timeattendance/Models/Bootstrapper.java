package ta.timeattendance.Models;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import ta.lib.*;
import ta.lib.operator.*;

public class Bootstrapper
{
	private ArrayList<TypeItem> _typeItems;
/*
ISupervisorModel - ...

IPointModel - ISupervisorModel
IPointModel - IAppService
IPointModel - ISettingSvModel

ICurrentVersionServices - ...

ISendChekinService - ...

IAppService - ...

ISettingSvModel - ISupervisorModel
*/
	public static void Init()
	{
		Bootstrapper bs = Bootstrapper.Instance();
		bs.RunControllers();
	}
	private Bootstrapper()
	{
		_typeItems = new ArrayList<TypeItem>(9);
		_typeItems.add(new TypeItem("ISupervisorModel",        ta.timeattendance.Models.SupervisorModel.class, true, true));
		_typeItems.add(new TypeItem("IPointModel",             ta.timeattendance.Models.PointModel.class,      true, false));
		_typeItems.add(new TypeItem("ISettingSvModel",         ta.timeattendance.Models.SettingSvModel.class,  true, false));
		_typeItems.add(new TypeItem("IAppService",             ta.timeattendance.Services.AppService.class,    true, false));
		_typeItems.add(new TypeItem("ICurrentVersionServices", ta.Tabs.Settings.CurrentVersionServices.class,  true, false));
		_typeItems.add(new TypeItem("ISendChekinService",      ta.Tabs.CheckinList.SendChekinService.class,    true, false));
		
		Bootstrapper._instance = this;
	}
	private void RunControllers()
	{
		Resolve( ta.timeattendance.Models.ISettingSvModel.class, this );
	}



	private static Bootstrapper _instance;
	private static Bootstrapper Instance()
	{
		if(Bootstrapper._instance == null)
		{
			Bootstrapper._instance = new Bootstrapper();
		}
		return Bootstrapper._instance;
	}
	public static void Clear()
	{
		Bootstrapper.Instance()._Clear();
		//Bootstrapper._instance = null;
	}
	private void _Clear()
	{
		int count = this._typeItems.size();
		for( int i = 0; i < count; i++)
		{
			this._typeItems.get(i).Clear();
		}
	}



	public static <T> T Resolve(Class<T> _interface)
	{
		return Resolve(_interface, Bootstrapper.Instance());
	}
	public static <T> T Resolve(Class<T> _interface, Bootstrapper bs)
	{
		String temp = _interface.getName();
		int index = temp.lastIndexOf(".");
		if(index != -1 )
		{
			temp = temp.substring(index).replace(".", "");
		}
		final String className = temp;

		T instance = null;

		ArrayList<TypeItem> items = bs._typeItems;

		ArrayList<TypeItem> typeItems = 
			operator.Where(
				items,
				new WhereRunnable<TypeItem,TypeItem>() { public TypeItem run(TypeItem item)
				{
					int aaa = 9;
					String name = className;
					String str =  item.marker;
					TypeItem result = null;
					
					boolean res = str.equals(name);
					if(res == true) { 
						result = item;
					}
					return result;
				}}
			);

		if(typeItems.size() != 0)
		{
			TypeItem typeItem = typeItems.get(0);
			instance = (T)typeItem.GetOrCreateInstance();
		}
		return instance;
	}

	static
	{
		Bootstrapper._instance = null;
	}
}
