package ta.lib.Controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ta.Database.*;
import ta.timeattendance.R;

public abstract class AdapterBase<T> extends BaseAdapter
{
	protected   T[]                _items;
	private   View.OnClickListener _listener;
	protected LayoutInflater       _inflater;
	//protected TextView             _currentTextView;

	public AdapterBase(
		Context context, 
		T[] array,
		View.OnClickListener onClickListener)
	{
		//this._inflater = ((LayoutInflater)context.getSystemService("layout_inflater"));
		this._inflater = android.view.LayoutInflater.from(context);
		this._items = array;
		this._listener = onClickListener;
		//this._currentTextView = null;
	}

	public abstract String GetTextFromItem(T item);
	public abstract Integer GetListItemIdentifier();

	@Override
	public int getCount()
	{
		if (this._items == null)
		{
			return 0;
		}
		return this._items.length;
	}

	@Override
	public Object getItem(int i)
	{
		return Integer.valueOf(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	protected View GetOrCreateView(View view)
	{
		if( view == null )
		{
			Integer identifier = GetListItemIdentifier();
			view = this._inflater.inflate(identifier, null);
		}
		return view;
	}

	@Override
	public View getView(int paramInt, View view, ViewGroup paramViewGroup)
	{
		view = GetOrCreateView(view);

		TextView     text = (TextView)view.findViewById(R.id.txtName);
		LinearLayout root = (LinearLayout)view.findViewById(R.id.item);
		root.setOnClickListener(this._listener);

		//if( this._items != null )
		//{
			//try
			//{
				T item = this._items[paramInt];

				text.setText(GetTextFromItem(item));

				Integer identifier = GetListItemIdentifier();
				root.setTag(new Object[]{ identifier, item});
			//}
			//catch (Exception e)
			//{
			//	Exception ex = e;
			//}
		//}
		return view;
	}
}
