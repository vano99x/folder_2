package ta.Tabs.CheckinList;

import android.widget.Filter;
import java.util.List;

import ta.Database.*;

	class CheckinFilter extends Filter
	{
		private CheckinAdapter3 _checkinAdapter3;

		CheckinFilter(CheckinAdapter3 checkinAdapter3)
		{
			this._checkinAdapter3 = checkinAdapter3;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults result = new FilterResults();

			List<Checkin> founded = 
				CheckinAdapter3.GetCheckinListByDatesInterval(
					this._checkinAdapter3.tabCheckinList, 
					this._checkinAdapter3.original
				);

			result.values = founded;
			result.count = founded.size();
			return result;
		}

		@Override
		protected void publishResults(CharSequence charSequence, FilterResults filterResults)
		{
			List<Checkin> list = (List<Checkin>) filterResults.values;

			this._checkinAdapter3.items.clear();
			this._checkinAdapter3.items.addAll(list);
			this._checkinAdapter3.notifyDataSetChanged();
		}



		////@Override
		//protected FilterResults performFiltering(CharSequence constraint)
		//{
		//    FilterResults result = new FilterResults();
		//    // NOTE: this function is *always* called from a background thread, and
		//    // not the UI thread.
		//    String filterTypeStr = constraint.toString().toLowerCase();
		//    int    filterTypeInt = Integer.parseInt(filterTypeStr);
		//    //if (constraint != null && constraint.toString().length() > 0)
		//    //{

		//    switch(filterTypeInt){
		//    case 1:{

		//        long currentMS     = System.currentTimeMillis();
		//        long dayBeforeMS   = DateHelper.AddDays(currentMS, -1);
		//        Date currentDate   = new Date(currentMS);
		//        Date dayBeforeDate = new Date(dayBeforeMS);

		//        List<Checkin> founded = new ArrayList<Checkin>();
		//        for (Checkin ch : this._checkinAdapter3.original)
		//        {
		//            Date checkinDate = ch.get_DateObj();

		//            //if(
		//            //    t.getArtistName().toLowerCase().contains(constraint) || 
		//            //    t.getTrackName().toLowerCase().contains(constraint)
		//            //)
		//            //if(ch.CompareDate(constraint))
		//            if(checkinDate.after(dayBeforeDate) && checkinDate.before(currentDate))
		//            {
		//                founded.add(ch);
		//            }
		//        }

		//        result.values = founded;
		//        result.count = founded.size();

		//    break;}
		//    case 2:{

		//        result.values = this._checkinAdapter3.original;
		//        result.count =  this._checkinAdapter3.original.size();

		//    break;}
		//    }

		//    //}
		//    //else
		//    //{
		//    //    result.values = original;
		//    //    result.count = original.size();
		//    //}

		//    return result;
		//}
	}