package ta.Tabs.Settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ta.Database.Point;
import ta.timeattendance.R;

public class CheckpointAdapter extends BaseAdapter
{
	private LayoutInflater  _inflater;
	private Point[]         _pointArray;
	private OnClickListener _onClickListener;

	public CheckpointAdapter(
		Context context, 
		Point[] pointArray, 
		View.OnClickListener onClickListener)
	{
		this._inflater = ((LayoutInflater)context.getSystemService("layout_inflater"));
		this._pointArray     = pointArray;
		this._onClickListener  = onClickListener;
	}

	public int getCount()
	{
		if (this._pointArray == null)
		{
			return 0;
		}
		return this._pointArray.length;
	}
	public long getItemId(int paramInt)
	{
		return paramInt;
	}
	public Object getItem(int paramInt)
	{
		return Integer.valueOf(paramInt);
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
	{
		if( paramView == null )
		{
			paramView = this._inflater.inflate(R.layout.list_item, null);
			paramView.setTag(null);
		}
		TextView     textView = (TextView)    paramView.findViewById(R.id.txtName);
		LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.item);
		baseView.setOnClickListener(this._onClickListener);
		if( this._pointArray != null )
		{
			try
			{
				Point point = this._pointArray[paramInt];
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