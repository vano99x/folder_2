package ta.lib.Controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import ta.Database.*;

import ta.timeattendance.R;

public class LoadingProgressBarAdapter extends BaseAdapter
{
	private Context _context;
	private LayoutInflater mInflater;
	public ArrayList<String> items;

	public LoadingProgressBarAdapter(Context context, String [] arr)
	{
		this._context = context;
		this.mInflater = ((LayoutInflater)this._context.getSystemService("layout_inflater"));

		if(arr == null){
			this.items = new ArrayList<String>(9);
		}else{
			this.items = new ArrayList<String>(Arrays.asList(arr));
		}
	}
	
	//*********************************************************************************************
	//*      property
	public ArrayList<String> get_Items()
	{
		return this.items;
	}
	
	//*********************************************************************************************
	//*      override
	public int getCount()
	{
		if (this.items == null) {
			return 0;
		}
		return this.items.size();
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
			paramView = this.mInflater.inflate(R.layout.ctrl_loading_progress_bar_item, null);
		}

		TextView     textView = (TextView)    paramView.findViewById(R.id.LoadingProgressBar_ListView_Item_Text);
		//LinearLayout baseView = (LinearLayout)paramView.findViewById(R.id.LoadingProgressBar_ListView_Item_RootId);

		if( this.items != null && this.items.size() != 0 )
		{
			String str = this.items.get(paramInt);
			textView.setText(str);
		}

		return paramView;
	}
}