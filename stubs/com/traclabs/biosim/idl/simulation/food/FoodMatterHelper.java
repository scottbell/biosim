package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL struct "FoodMatter".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class FoodMatterHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(FoodMatterHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.id(),"FoodMatter",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("mass", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("waterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("type", org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.id(),"PlantType",new String[]{"WHEAT","DRY_BEAN","LETTUCE","PEANUT","RICE","SOYBEAN","SWEET_POTATO","TOMATO","WHITE_POTATO","UNKNOWN_PLANT"}), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.food.FoodMatter s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.food.FoodMatter extract (final org.omg.CORBA.Any any)
	{
		org.omg.CORBA.portable.InputStream in = any.create_input_stream();
		try
		{
			return read (in);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (java.io.IOException e)
			{
			throw new RuntimeException("Unexpected exception " + e.toString() );
			}
		}
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/food/FoodMatter:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.food.FoodMatter read (final org.omg.CORBA.portable.InputStream in)
	{
		com.traclabs.biosim.idl.simulation.food.FoodMatter result = new com.traclabs.biosim.idl.simulation.food.FoodMatter();
		result.mass=in.read_float();
		result.waterContent=in.read_float();
		result.type=com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final com.traclabs.biosim.idl.simulation.food.FoodMatter s)
	{
		out.write_float(s.mass);
		out.write_float(s.waterContent);
		com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.write(out,s.type);
	}
}
