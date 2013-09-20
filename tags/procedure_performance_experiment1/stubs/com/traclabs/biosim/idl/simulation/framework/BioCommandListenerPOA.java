package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "BioCommandListener"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class BioCommandListenerPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.framework.BioCommandListenerOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "newCommandSent", new java.lang.Integer(0));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/framework/BioCommandListener:1.0"};
	public com.traclabs.biosim.idl.simulation.framework.BioCommandListener _this()
	{
		return com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.framework.BioCommandListener _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // newCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				newCommandSent(_arg0);
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
