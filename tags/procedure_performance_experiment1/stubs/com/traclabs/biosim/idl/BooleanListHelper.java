package com.traclabs.biosim.idl;

/**
 *	Generated from IDL definition of alias "BooleanList"
 *	@author JacORB IDL compiler 
 */

public final class BooleanListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, boolean[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static boolean[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.BooleanListHelper.id(), "BooleanList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8))));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/BooleanList:1.0";
	}
	public static boolean[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		boolean[] _result;
		int _l_result2 = _in.read_long();
		_result = new boolean[_l_result2];
	_in.read_boolean_array(_result,0,_l_result2);
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, boolean[] _s)
	{
		
		_out.write_long(_s.length);
		_out.write_boolean_array(_s,0,_s.length);
	}
}
