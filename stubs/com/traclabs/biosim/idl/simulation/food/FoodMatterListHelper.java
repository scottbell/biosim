package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL alias "FoodMatterList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class FoodMatterListHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, com.traclabs.biosim.idl.simulation.food.FoodMatter[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.traclabs.biosim.idl.simulation.food.FoodMatter[] extract (final org.omg.CORBA.Any any)
	{
		if ( any.type().kind() == org.omg.CORBA.TCKind.tk_null)
		{
			throw new org.omg.CORBA.BAD_OPERATION ("Can't extract from Any with null type.");
		}
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(FoodMatterListHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.simulation.food.FoodMatterListHelper.id(), "FoodMatterList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.id(),"FoodMatter",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("mass", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("waterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("type", org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.id(),"PlantType",new String[]{"WHEAT","DRY_BEAN","LETTUCE","PEANUT","RICE","SOYBEAN","SWEET_POTATO","TOMATO","WHITE_POTATO","UNKNOWN_PLANT"}), null)})));
				}
			}
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
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result15 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result15);
				}
		}
		catch (java.io.IOException e)
		{
		}
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
