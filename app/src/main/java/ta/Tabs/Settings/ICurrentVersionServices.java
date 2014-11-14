package ta.Tabs.Settings;

import ta.timeattendance.Models.*;
import ta.lib.*;

public interface ICurrentVersionServices extends IBaseModel
{
	void LoadCurrentVersionNumber();
	
	//ta.Tabs.Settings.CurrentVersionServices.CurrentVersionLoadedEventClass get_CurrentVersionLoaded();
	void set_CurrentVersionLoaded(RunnableWithArgs runnable);
}