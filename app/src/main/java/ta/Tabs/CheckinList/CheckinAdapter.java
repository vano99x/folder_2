package ta.Tabs.CheckinList;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import ta.Database.Personel;
import ta.Database.Checkin;
import ta.timeattendance.R;

public class CheckinAdapter extends BaseAdapter //implements Filterable
{
	private Checkin[] items;
	private View.OnClickListener listener;
	private LayoutInflater mInflater;
	private Context _context;

	public CheckinAdapter(
		Context paramContext, 
		Checkin[] checkinArr, 
		View.OnClickListener paramOnClickListener)//Object paramOnClickListener)
	{
		this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
		this.items = checkinArr;
		this.listener = paramOnClickListener;

		this._context = paramContext;
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
				Checkin checkin = this.items[paramInt];

				Personel personel = checkin.get_Personel();
				String nameStr = personel.FirstName;

				SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
				long dt = Long.parseLong(checkin.DateTime);
				Date date = new Date(dt);
				String dateStr = sdf.format(date);

				textView.setText(nameStr + " "+ dateStr);
				baseView.setTag(checkin);
				return paramView;
			}
			catch (Exception e)
			{
				Exception ex = e;
			}
		}
		return paramView;
	}

	/*
//@Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            //@SuppressWarnings("unchecked")
            //@Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                arrayListNames = (List<String>) results.values;
                notifyDataSetChanged();
            }

            //@Override
            protected FilterResults performFiltering(CharSequence constraint)
			{

                FilterResults results = new FilterResults();
                ArrayList<String> FilteredArrayNames = new ArrayList<String>();

// perform your search here using the searchConstraint String.

                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mDatabaseOfNames.size(); i++)
					{
                        String dataNames = mDatabaseOfNames.get(i);
                        if (dataNames.toLowerCase().startsWith(constraint.toString()))
						{
                            FilteredArrayNames.add(dataNames);
                        }
                    }

                    results.count = FilteredArrayNames.size();
                    results.values = FilteredArrayNames;
                    Log.e("VALUES", results.values.toString());
                

                return results;
            }
        };

        return filter;
    }*/

}