package ta.Tabs.CheckinList;

import ta.Database.Checkin;
import ta.lib.RunnableWithArgs;
import ta.timeattendance.Models.*;

public interface IChekinService extends IBaseModel
{
	void SendCheckin();
	void SaveCheckin(Checkin ch);

	void add_SaveCheckinComplete(RunnableWithArgs runnable);
}