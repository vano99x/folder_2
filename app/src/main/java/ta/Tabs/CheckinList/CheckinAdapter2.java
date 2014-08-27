package ta.Tabs.CheckinList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.lang.CharSequence;

import ta.Database.Personel;
import ta.Database.Checkin;
import ta.timeattendance.R;

class CheckinAdapter2 extends ArrayAdapter<Checkin>
{
	private List<Checkin> items;
	public List<Checkin> original;

	private LayoutInflater inflater;
	private Context _context;

	public CheckinAdapter2(Context context, int textViewResourceId, List<Checkin> objects)
	{
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.original = new ArrayList(this.items);
		
		this.inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this._context = context;
	}

	private String GetTextForItem(Checkin checkin)
	{
		Personel personel = checkin.get_Personel();
		String nameStr = personel.FirstName;

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
		long dt = Long.parseLong(checkin.DateTime);
		Date date = new Date(dt);
		String dateStr = sdf.format(date);

		return nameStr + " "+ dateStr;
	}


	//@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if( view == null )
		{
			//view = this.inflater.inflate(R.layout.track_item, null);
			view = this.inflater.inflate(R.layout.list_item, null);
		}

		//final 
		int pos = position;
		//final 
		Checkin checkin = items.get(position);

		if (checkin != null)
		{
			//TextView trackInfo = (TextView)view.findViewById(R.id.track);
			TextView     textView = (TextView)view.findViewById(R.id.txtName);
			LinearLayout baseView = (LinearLayout)view.findViewById(R.id.item);

			//trackInfo.setText(
			//    track.getTime().substring(11) + " " + 
			//    track.getArtistName() + " - " + 
			//    track.getTrackName() + 
			//    " (" + track.getDuration() + ")");
			String str = GetTextForItem(checkin);
			textView.setText(str);
		}


		//view.setOnClickListener(new View.OnClickListener()
		//{
		//    //@Override
		//    public void onClick(View view)
		//    {
		//        //TrackInfo currentTrack = (TrackInfo) getListAdapter().getItem(pos);

		//        //String url = 
		//        //    ChannelUtil.getTrackUrl(
		//        //        currentTrack.getTime(), 
		//        //        Radio.getHost(radioId), 
		//        //        currentChannel.getChannelId()
		//        //    );

		//        //String offset = ChannelUtil.getTrackOffset(currentTrack.getTime());
		//        //play(url, Integer.parseInt(offset));
		//    }
		//});
		return view;
	}

	private Filter _filter;
	//@Override
	public Filter getFilter()
	{
		if( _filter == null )
		{
			_filter = new TracksFilter();
		}
		return _filter;
	}


	private class TracksFilter extends Filter
	{
		//@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			// NOTE: this function is *always* called from a background thread, and
			// not the UI thread.
			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0)
			{
				List<Checkin> founded = new ArrayList<Checkin>();
				for (Checkin ch : CheckinAdapter2.this.original)
				{
					//if(
					//    t.getArtistName().toLowerCase().contains(constraint) || 
					//    t.getTrackName().toLowerCase().contains(constraint)
					//)
					//if(ch.CompareDate(constraint))
					{
						founded.add(ch);
					}
				}

				result.values = founded;
				result.count = founded.size();
			}
			else
			{
				result.values = original;
				result.count = original.size();
			}

			return result;
		}


		//@Override
		protected void publishResults(CharSequence charSequence, FilterResults filterResults)
		{
			clear();
			for (Checkin ch : (List<Checkin>) filterResults.values)
			{
				add(ch);
			}
			notifyDataSetChanged();
		}
	}
}

class TrackInfo
{
	public String str;
}