package ta.Tabs.TemplateList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ta.Database.*;

import ta.lib.Common.DateTime;
import ta.timeattendance.R;

public class ListTemplateAdapter extends BaseAdapter
{
	private Template[] items;
	private View.OnClickListener listener;
	private LayoutInflater mInflater;

	public ListTemplateAdapter(
		Context context, 
		Template[] array,
		View.OnClickListener onClickListener)
	{
		this.mInflater = ((LayoutInflater)context.getSystemService("layout_inflater"));
		this.items = array;
		this.listener = onClickListener;
	}

	public int getCount()
	{
		if (this.items == null)
		{
			return 0;
		}
		return this.items.length;
	}

	public Object getItem(int paramInt)
	{
		return Integer.valueOf(paramInt);
	}

	public long getItemId(int paramInt)
	{
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	{
		if( paramView == null )
		{
			paramView = this.mInflater.inflate(R.layout.list_item, null);
			paramView.setTag(null);
		}
		TextView     textView = (TextView)    paramView.findViewById(R.id.txtName);
		LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.item);
		baseView.setOnClickListener(this.listener);
		if( this.items != null )
		{
			try
			{
				Template item = this.items[paramInt];

				DateTime tplStart = DateTime.FromDaySecond(item.StartTime);
				String str4 = tplStart.ToTimeString();
				DateTime tplEnd   = DateTime.FromDaySecond(item.EndTime);
				String str5 = tplEnd.ToTimeString();
				str4 = str4 +"-"+ str5;
				textView.setText(str4);

				baseView.setTag(new Object[]{ R.id.Template_Item_TagId, item});
				return paramView;
			}
			catch (Exception e)
			{
			}
		}
		return paramView;
	}
}
