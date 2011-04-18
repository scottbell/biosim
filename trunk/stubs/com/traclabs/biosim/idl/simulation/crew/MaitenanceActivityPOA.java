package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "MaitenanceActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class MaitenanceActivityPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getActivityIntensity", new java.lang.Integer(0));
		m_opsHash.put ( "getModuleNameToMaintain", new java.lang.Integer(1));
		m_opsHash.put ( "getTimeLength", new java.lang.Integer(2));
		m_opsHash.put ( "getName", new java.lang.Integer(3));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/MaitenanceActivity:1.0","IDL:com/traclabs/biosim/idl/simulation/crew/Activity:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityHelper.narrow(_this_object(orb));
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
			case 0: // getActivityIntensity
			{
				_out = handler.createReply();
				_out.write_long(getActivityIntensity());
				break;
			}
			case 1: // getModuleNameToMaintain
			{
				_out = handler.createReply();
				_out.write_string(getModuleNameToMaintain());
				break;
			}
			case 2: // getTimeLength
			{
				_out = handler.createReply();
				_out.write_long(getTimeLength());
				break;
			}
			case 3: // getName
			{
				_out = handler.createReply();
				_out.write_string(getName());
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
