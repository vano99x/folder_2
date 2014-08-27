package ta.timeattendance.Models;

import ta.Database.*;
import ta.lib.*;

public interface IPointModel extends IBaseModel
{
	Point get_CurrentPoint();
	void set_CurrentPoint(Point p);

	void set_CurrentPointChanged(RunnableWithArgs runnable);
}