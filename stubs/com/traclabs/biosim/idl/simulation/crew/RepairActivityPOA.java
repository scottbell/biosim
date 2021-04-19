package com.traclabs.biosim.idl.simulation.crew;


/**
 * Generated from IDL interface "RepairActivity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class RepairActivityPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.RepairActivityOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getModuleNameToRepair", Integer.valueOf(0));
		m_opsHash.put ( "getMalfunctionIDToRepair", Integer.valueOf(1));
		m_opsHash.put ( "getActivityIntensity", Integer.valueOf(2));
		m_opsHash.put ( "getTimeLength", Integer.valueOf(3));
		m_opsHash.put ( "getName", Integer.valueOf(4));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/RepairActivity:1.0","IDL:com/traclabs/biosim/idl/simulation/crew/Activity:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.RepairActivity _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.RepairActivity __r = com.traclabs.biosim.idl.simulation.crew.RepairActivityHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.RepairActivity _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.RepairActivity __r = com.traclabs.biosim.idl.simulation.crew.RepairActivityHelper.narrow(__o);
		return __r;
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
			case 0: // getModuleNameToRepair
			{
				_out = handler.createReply();
				java.lang.String tmpResult24 = getModuleNameToRepair();
_out.write_string( tmpResult24 );
				break;
			}
			case 1: // getMalfunctionIDToRepair
			{
				_out = handler.createReply();
				_out.write_longlong(getMalfunctionIDToRepair());
				break;
			}
			case 2: // getActivityIntensity
			{
				_out = handler.createReply();
				_out.write_long(getActivityIntensity());
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
				java.lang.String tmpResult25 = getName();
_out.write_string( tmpResult25 );
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
