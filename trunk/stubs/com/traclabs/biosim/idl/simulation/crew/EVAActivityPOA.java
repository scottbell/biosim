package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "EVAActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class EVAActivityPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.EVAActivityOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getEVACrewGroupName", new java.lang.Integer(0));
		m_opsHash.put ( "getActivityIntensity", new java.lang.Integer(1));
		m_opsHash.put ( "getBaseCrewGroupName", new java.lang.Integer(2));
		m_opsHash.put ( "getTimeLength", new java.lang.Integer(3));
		m_opsHash.put ( "getName", new java.lang.Integer(4));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/EVAActivity:1.0","IDL:com/traclabs/biosim/idl/simulation/crew/Activity:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.EVAActivity _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.EVAActivity _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper.narrow(_this_object(orb));
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
			case 0: // getEVACrewGroupName
			{
				_out = handler.createReply();
				_out.write_string(getEVACrewGroupName());
				break;
			}
			case 1: // getActivityIntensity
			{
				_out = handler.createReply();
				_out.write_long(getActivityIntensity());
				break;
			}
			case 2: // getBaseCrewGroupName
			{
				_out = handler.createReply();
				_out.write_string(getBaseCrewGroupName());
				break;
			}
			case 3: // getTimeLength
			{
				_out = handler.createReply();
				_out.write_long(getTimeLength());
				break;
			}
			case 4: // getName
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
