package com.traclabs.biosim.idl.simulation.framework;


/**
 *	Generated from IDL interface "BioCommandListener"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _BioCommandListenerStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements com.traclabs.biosim.idl.simulation.framework.BioCommandListener
{
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/framework/BioCommandListener:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = com.traclabs.biosim.idl.simulation.framework.BioCommandListenerOperations.class;
	public void newCommandSent(java.lang.String commandName)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "newCommandSent", true);
				_os.write_string(commandName);
				_is = _invoke(_os);
				return;
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "newCommandSent", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			BioCommandListenerOperations _localServant = (BioCommandListenerOperations)_so.servant;
			try
			{
			_localServant.newCommandSent(commandName);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

}
