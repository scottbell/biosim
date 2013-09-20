package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of alias "FoodMatterList"
 *	@author JacORB IDL compiler 
 */

public final class FoodMatterListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;

	public static void insert (org.omg.CORBA.Any any, com.traclabs.biosim.idl.simulation.food.FoodMatter[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.traclabs.biosim.idl.simulation.food.FoodMatter[] extract (final org.omg.CORBA.Any any)
	{
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.simulation.food.FoodMatterListHelper.id(), "FoodMatterList",org.omg.CORBA.ORB.init().create_sequence_tc(0, com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.type()));
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/food/FoodMatterList:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.food.FoodMatter[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		com.traclabs.biosim.idl.simulation.food.FoodMatter[] _result;
		int _l_result15 = _in.read_long();
		_result = new com.traclabs.biosim.idl.simulation.food.FoodMatter[_l_result15];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, com.traclabs.biosim.idl.simulation.food.FoodMatter[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.write(_out,_s[i]);
		}

	}
}
