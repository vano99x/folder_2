/*package com.Tabs.FacilityInfo;

import android.widget.Filter;
import java.util.Date;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import com.ifree.Database.*;
import com.ifree.Database.FacilityInfoEntity.*;
import com.ifree.lib.*;

public class FacilityInfoFilter extends Filter
{
	private TabFacilityInfo _tabFacilityInfo;

	FacilityInfoFilter(TabFacilityInfo tabFacilityInfo)
	{
		this._tabFacilityInfo = tabFacilityInfo;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint)
	{
		android.widget.Filter.FilterResults result = new FilterResults();

		List<FacilityEntity> founded = new ArrayList<FacilityEntity>(Arrays.asList(this._tabFacilityInfo._facilityInfoEntity.FacilityArray));
		//List<FacilityEntity> founded = new ArrayList<FacilityEntity>();
		//for (FacilityEntity f : this._tabFacilityInfo._facilityInfoEntity.FacilityArray)
		//{
		//    FacilityEntity o = new FacilityEntity();

		//    o.FacilityName = f.FacilityName;
		//    o.RequestCount = f.RequestCount;
		//    o.GrantedCount = f.GrantedCount;
		//    o.HoursWorked  = f.HoursWorked;
		//    o.FinesSum     = f.FinesSum;
		//    o.BonusesSum   = f.BonusesSum;

		//    founded.add( o );
		//}

		result.values = founded;
		result.count = founded.size();

		return result;
	}

	@Override
	protected void publishResults(CharSequence charSequence, FilterResults filterResults)
	{
	}
}*/