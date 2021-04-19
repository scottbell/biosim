package com.traclabs.biosim.idl.simulation.crew;


/**
 * Generated from IDL interface "Activity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class ActivityPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.crew.ActivityOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getActivityIntensity", Integer.valueOf(0));
		m_opsHash.put ( "getTimeLength", Integer.valueOf(1));
		m_opsHash.put ( "getName", Integer.valueOf(2));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/crew/Activity:1.0"};
	public com.traclabs.biosim.idl.simulation.crew.Activity _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.Activity __r = com.traclabs.biosim.idl.simulation.crew.ActivityHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.Activity _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.Activity __r = com.traclabs.biosim.idl.simulation.crew.ActivityHelper.narrow(__o);
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
			case 0: // getActivityIntensity
			{
				_out = handler.createReply();
				_out.write_long(getActivityIntensity());
				break;
			}
			case 1: // getTimeLength
			{
				_out = handler.createReply();
				_out.write_long(getTimeLength());
				break;
			}
			case 2: // getName
			{
				_out = handler.createReply();
				java.lang.String tmpResult23 = getName();
_out.write_string( tmpResult23 );
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
