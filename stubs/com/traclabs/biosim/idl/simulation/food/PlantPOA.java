package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "Plant".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class PlantPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.food.PlantOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getMolesOfCO2Inhaled", Integer.valueOf(0));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/food/Plant:1.0"};
	public com.traclabs.biosim.idl.simulation.food.Plant _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.food.Plant __r = com.traclabs.biosim.idl.simulation.food.PlantHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.food.Plant _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.food.Plant __r = com.traclabs.biosim.idl.simulation.food.PlantHelper.narrow(__o);
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
			case 0: // getMolesOfCO2Inhaled
			{
				_out = handler.createReply();
				_out.write_float(getMolesOfCO2Inhaled());
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
