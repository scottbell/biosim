package com.traclabs.biosim.idl.simulation.food;
/**
 *	Generated from IDL definition of enum "PlantType"
 *	@author JacORB IDL compiler 
 */

public final class PlantTypeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.id(),"PlantType",new String[]{"WHEAT","DRY_BEAN","LETTUCE","PEANUT","RICE","SOYBEAN","SWEET_POTATO","TOMATO","WHITE_POTATO","UNKNOWN_PLANT"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.food.PlantType s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.food.PlantType extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/food/PlantType:1.0";
	}
	public static PlantType read (final org.omg.CORBA.portable.InputStream in)
	{
		return PlantType.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final PlantType s)
	{
		out.write_long(s.value());
	}
}
