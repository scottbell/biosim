package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL definition of alias "PowerStoreList"
 *	@author JacORB IDL compiler 
 */

public final class PowerStoreListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, com.traclabs.biosim.idl.simulation.power.PowerStore[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.traclabs.biosim.idl.simulation.power.PowerStore[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.simulation.power.PowerStoreListHelper.id(), "PowerStoreList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/power/PowerStore:1.0", "PowerStore")));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/power/PowerStoreList:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.power.PowerStore[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		com.traclabs.biosim.idl.simulation.power.PowerStore[] _result;
		int _l_result3 = _in.read_long();
		_result = new com.traclabs.biosim.idl.simulation.power.PowerStore[_l_result3];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=com.traclabs.biosim.idl.simulation.power.PowerStoreHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, com.traclabs.biosim.idl.simulation.power.PowerStore[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			com.traclabs.biosim.idl.simulation.power.PowerStoreHelper.write(_out,_s[i]);
		}

	}
}
