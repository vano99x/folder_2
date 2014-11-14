package ta.timeattendance.Models;

import ta.Database.*;
import ta.lib.*;

public interface ICategoryModel extends IBaseModel
{
	Category get_CurrentCategory();
	void set_CurrentCategory(Category c);

	void set_CurrentCategoryChanged(RunnableWithArgs runnable);
}
