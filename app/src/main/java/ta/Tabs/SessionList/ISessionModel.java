package ta.Tabs.SessionList;

import ta.Database.*;
import ta.lib.*;
import ta.timeattendance.Models.IBaseModel;

public interface ISessionModel extends IBaseModel
{
	SessionMode get_SessionMode();
	void set_SessionMode(SessionMode c);
}
