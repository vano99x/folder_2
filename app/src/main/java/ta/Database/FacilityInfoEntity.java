package ta.Database;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class FacilityInfoEntity
{
	//public int Id;
	public String            ClientName;
	public FacilityEntity [] FacilityArray;
	//public String FacilityName;
	//public String RequestCount;
	//public String GrantedCount;
	//public String HoursWorked;
	//public String FinesSum;
	//public String BonusesSum;

	public static FacilityInfoEntity FromJson(JSONObject jo, String responseStr)
	{
		FacilityInfoEntity fie = null;

		try
		{
			if(jo.has("client"))
			{
				fie = new FacilityInfoEntity();
				fie.ClientName = jo.getString("client");

				if(jo.has("objects"))
				{
					//fie.FacilityName = jo.getString("object");
					JSONArray jsArr = jo.getJSONArray("objects");
					int count = jsArr.length();
					fie.FacilityArray = new FacilityEntity[count];

					for( int i = 0; i < count; i++)
					{
						fie.FacilityArray[i] = FacilityInfoEntity.FEFromJson(jsArr.getJSONObject(i));
					}
				}
			}

			int aaa = 9;
			int aaa2 = 9 - 2;
		}
		catch (Exception e)
		{
			Exception ex = e;

			int aaa = 9;
		}
		return fie;
	}

		public static FacilityEntity FEFromJson(JSONObject jo) throws JSONException
		{
			FacilityEntity fe = new FacilityEntity();//FacilityInfoEntity.

			if(jo.has("object"))
			{
				fe.FacilityName = jo.getString("object");
			}

			if(jo.has("shift"))
			fe.RequestCount = jo.getString("shift");

			if(jo.has("work_out"))
			fe.GrantedCount = jo.getString("work_out");

			if(jo.has("work_time"))
			fe.HoursWorked = jo.getString("work_time");

			if(jo.has("fine"))
			fe.FinesSum = jo.getString("fine");

			if(jo.has("bonus"))
			fe.BonusesSum = jo.getString("bonus");

			return fe;
		}

	public static class FacilityEntity
	{
		public String FacilityName;
		public String RequestCount;
		public String GrantedCount;
		public String HoursWorked;
		public String FinesSum;
		public String BonusesSum;
	}
}