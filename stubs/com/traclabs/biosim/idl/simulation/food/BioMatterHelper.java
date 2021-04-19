package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL struct "BioMatter".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class BioMatterHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(BioMatterHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_struct_tc(com.traclabs.biosim.idl.simulation.food.BioMatterHelper.id(),"BioMatter",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("mass", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("inedibleFraction", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("edibleWaterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("inedibleWaterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("type", org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.id(),"PlantType",new String[]{"WHEAT","DRY_BEAN","LETTUCE","PEANUT","RICE","SOYBEAN","SWEET_POTATO","TOMATO","WHITE_POTATO","UNKNOWN_PLANT"}), null)});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.food.BioMatter s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.food.BioMatter extract (final org.omg.CORBA.Any any)
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
		return "IDL:com/traclabs/biosim/idl/simulation/food/BioMatter:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.food.BioMatter read (final org.omg.CORBA.portable.InputStream in)
	{
		com.traclabs.biosim.idl.simulation.food.BioMatter result = new com.traclabs.biosim.idl.simulation.food.BioMatter();
		result.mass=in.read_float();
		result.inedibleFraction=in.read_float();
		result.edibleWaterContent=in.read_float();
		result.inedibleWaterContent=in.read_float();
		result.type=com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.read(in);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final com.traclabs.biosim.idl.simulation.food.BioMatter s)
	{
		out.write_float(s.mass);
		out.write_float(s.inedibleFraction);
		out.write_float(s.edibleWaterContent);
		out.write_float(s.inedibleWaterContent);
		com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.write(out,s.type);
	}
}
