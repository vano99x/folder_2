package ta.timeattendance.Models;

public class TypeItem
{
	public  String  marker;
	public  Class   type;
	private boolean _isSengleton;
	private boolean _isAliveBetweenRecreations;
	private IBaseModel _obj;

	public TypeItem(String _marker, Class _type, boolean isSengleton, boolean isAliveBetweenRecreations)
	{
		marker      = _marker;
		type        = _type;
		_isSengleton = isSengleton;
		_isAliveBetweenRecreations = isAliveBetweenRecreations;
		_obj = null;
	}

	public Object GetOrCreateInstance()
	{
		Object result = null;

		if(_isSengleton)
		{
			if(_obj == null)
			{
				try{
					//IllegalAccessException InstantiationException
					_obj = (IBaseModel)type.newInstance();
				}
				catch(java.lang.IllegalAccessException e){
					//Exception ex = e;
					throw new RuntimeException( e.getMessage() );
				}catch(java.lang.InstantiationException e){
					//Exception ex = e;
					throw new RuntimeException( e.getMessage() );
				}
			}
			else if(_obj.get_IsClearDependencies())
			{
				_obj.UpdateDependencies();
			}
			result = _obj;
		}
		else
		{
			try{   result = type.newInstance();   }catch(Exception e){}
		}
		return result;
	}

	public void Clear()
	{
		if(_isAliveBetweenRecreations && this._obj.get_IsKeepAlive())
		{
			this._obj.ClearDependencies();
		}
		else
		{
			this._obj = null;
		}
	}
}