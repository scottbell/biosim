package com.traclabs.biosim.idl.simulation.food;


/**
 *	Generated from IDL definition of struct "BioMatter"
 *	@author JacORB IDL compiler 
 */

public final class BioMatterHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(com.traclabs.biosim.idl.simulation.food.BioMatterHelper.id(),"BioMatter",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("mass", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("inedibleFraction", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("edibleWaterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("inedibleWaterContent", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("type", com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.type(), null)});
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
		return read(any.create_input_stream());
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
