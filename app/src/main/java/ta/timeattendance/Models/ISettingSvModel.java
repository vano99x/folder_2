package ta.timeattendance.Models;

import ta.Database.*;
import ta.lib.*;

public interface ISettingSvModel extends IBaseModel
{
	void PointSettingChanged_Add(RunnableWithArgs runnable);
}