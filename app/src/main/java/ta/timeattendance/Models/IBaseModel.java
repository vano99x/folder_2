package ta.timeattendance.Models;

public interface IBaseModel
{
	void ClearDependencies();
	boolean get_IsClearDependencies();
	void UpdateDependencies();
	boolean get_IsKeepAlive();
}