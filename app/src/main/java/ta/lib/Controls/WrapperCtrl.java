package ta.lib.Controls;

import java.lang.reflect.Constructor;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import ta.lib.tabui.*;

public class WrapperCtrl<T extends Tab>
{
	protected Class<T>  _clazz;
	protected Context   _mainActivity;
	protected ViewGroup _rootView;
	protected int _i1;
	protected int _i2;

	public WrapperCtrl(Class<T> clazz, Context mainActivity, ViewGroup rootView, int i1, int i2)
	{
		this._clazz = clazz;
		this._mainActivity = mainActivity;
		this._rootView = rootView;
		this._i1 = i1;
		this._i2 = i2;

		this.__instance = null;
	}

	
	private T __instance;
	private T get_Instance()
	{
		if(this.__instance == null)
		{
			try
			{
				Constructor<T> ctor = _clazz.getConstructor(Context.class,ViewGroup.class, Integer.class, Integer.class);
				T obj = ctor.newInstance( _mainActivity, _rootView, _i1, _i2);
				this.__instance = obj;
			}
			catch(Exception ex)
			{
			}
		}
		return this.__instance;
	}

	public void Show()
	{
		this.get_Instance().Show();
	}

	public void Hide()
	{
		this.get_Instance().Hide();
	}

	public void Clear()
	{
	}

}