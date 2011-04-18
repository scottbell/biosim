package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL definition of alias "CrewGroupList"
 *	@author JacORB IDL compiler 
 */

public final class CrewGroupListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, com.traclabs.biosim.idl.simulation.crew.CrewGroup[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.traclabs.biosim.idl.simulation.crew.CrewGroup[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.simulation.crew.CrewGroupListHelper.id(), "CrewGroupList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/crew/CrewGroup:1.0", "CrewGroup")));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/crew/CrewGroupList:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.crew.CrewGroup[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		com.traclabs.biosim.idl.simulation.crew.CrewGroup[] _result;
		int _l_result4 = _in.read_long();
		_result = new com.traclabs.biosim.idl.simulation.crew.CrewGroup[_l_result4];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, com.traclabs.biosim.idl.simulation.crew.CrewGroup[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.write(_out,_s[i]);
		}

	}
}
