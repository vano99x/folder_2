package ta.Tabs.PointsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ta.Database.Point;

import ta.timeattendance.R;

public class ListPointsAdapter extends BaseAdapter
{
	private Point[] items;
	private View.OnClickListener listener;
	private LayoutInflater mInflater;

	public ListPointsAdapter(
		Context paramContext, 
		Point[] paramArrayOfPoint, 
		View.OnClickListener paramOnClickListener)
	{
		this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
		this.items = paramArrayOfPoint;
		this.listener = paramOnClickListener;
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
				Point point = this.items[paramInt];
				textView.setText(point.Name);
				//baseView.setTag(point);
				baseView.setTag(new Object[]{ R.id.PointsListItem_Id, point});
				return paramView;
			}
			catch (Exception e)
			{
			}
		}
		return paramView;
	}
}

/* Location:           C:\Users\vano99\Desktop\jd-gui-0.3.5.windows\TandAOffline_dex2jar.jar
 * Qualified Name:     com.ifree.timeattendance.ListPointsAdapter
 * JD-Core Version:    0.6.2
 */