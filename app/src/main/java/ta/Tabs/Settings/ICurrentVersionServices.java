package ta.Tabs.Settings;

import ta.timeattendance.Models.*;

public interface ICurrentVersionServices extends IBaseModel
{
	void LoadCurrentVersionNumber();
	
	ta.Tabs.Settings.CurrentVersionServices.CurrentVersionLoadedEventClass get_CurrentVersionLoaded();
}