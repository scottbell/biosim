package com.traclabs.biosim.idl.simulation.food;


/**
 *	Generated from IDL definition of struct "FoodMatter"
 *	@author JacORB IDL compiler 
 */

public final class FoodMatterHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.id(),"FoodMatter",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("mass", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("waterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("type", com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.type(), null)});
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
		return read(any.create_input_stream());
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
