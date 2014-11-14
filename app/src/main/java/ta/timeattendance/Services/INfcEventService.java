package ta.timeattendance.Services;

import ta.Database.Personel;
import ta.lib.RunnableWithArgs;
import ta.timeattendance.Models.*;
import ta.timeattendance.MainActivity;

public interface INfcEventService extends IBaseModel
{
	void add_NewTagReceived(RunnableWithArgs runnable);

	void Start(MainActivity ma);
}
