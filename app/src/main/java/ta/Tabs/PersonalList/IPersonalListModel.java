package ta.Tabs.PersonalList;

import ta.Database.*;
import ta.lib.*;
import ta.timeattendance.MainActivity;
import ta.timeattendance.Models.IBaseModel;

public interface IPersonalListModel extends IBaseModel
{
	MainActivity.State get_CallerView();
	void set_CallerView(MainActivity.State s);
}
