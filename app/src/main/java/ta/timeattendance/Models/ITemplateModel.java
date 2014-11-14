package ta.timeattendance.Models;

import ta.Database.*;
import ta.lib.*;

public interface ITemplateModel extends IBaseModel
{
	Template get_CurrentTemplate();
	void set_CurrentTemplate(Template c);

	void add_CurrentTemplateChanged(RunnableWithArgs runnable);
}
