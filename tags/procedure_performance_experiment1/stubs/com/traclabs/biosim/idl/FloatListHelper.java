package com.traclabs.biosim.idl;

/**
 *	Generated from IDL definition of alias "FloatList"
 *	@author JacORB IDL compiler 
 */

public final class FloatListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, float[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static float[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.FloatListHelper.id(), "FloatList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6))));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/FloatList:1.0";
	}
	public static float[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		float[] _result;
		int _l_result1 = _in.read_long();
		_result = new float[_l_result1];
	_in.read_float_array(_result,0,_l_result1);
		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, float[] _s)
	{
		
		_out.write_long(_s.length);
		_out.write_float_array(_s,0,_s.length);
	}
}
