package com.traclabs.biosim.idl.framework;


/**
 *	Generated from IDL interface "TechSpecificInfo"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _TechSpecificInfoStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements com.traclabs.biosim.idl.framework.TechSpecificInfo
{
	private String[] ids = {"IDL:com/traclabs/biosim/idl/framework/TechSpecificInfo:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = com.traclabs.biosim.idl.framework.TechSpecificInfoOperations.class;
	public java.lang.String print()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "print", true);
				_is = _invoke(_os);
				java.lang.String _result = _is.read_string();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "print", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			TechSpecificInfoOperations _localServant = (TechSpecificInfoOperations)_so.servant;
			java.lang.String _result;			try
			{
			_result = _localServant.print();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

}
