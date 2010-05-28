package com.traclabs.biosim.idl.simulation.environment;


/**
 *	Generated from IDL definition of struct "Air"
 *	@author JacORB IDL compiler 
 */

public final class AirHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(com.traclabs.biosim.idl.simulation.environment.AirHelper.id(),"Air",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("o2Moles", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("co2Moles", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("otherMoles", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("vaporMoles", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null),new org.omg.CORBA.StructMember("nitrogenMoles", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.environment.Air s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.environment.Air extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/environment/Air:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.environment.Air read (final org.omg.CORBA.portable.InputStream in)
	{
		com.traclabs.biosim.idl.simulation.environment.Air result = new com.traclabs.biosim.idl.simulation.environment.Air();
		result.o2Moles=in.read_float();
		result.co2Moles=in.read_float();
		result.otherMoles=in.read_float();
		result.vaporMoles=in.read_float();
		result.nitrogenMoles=in.read_float();
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final com.traclabs.biosim.idl.simulation.environment.Air s)
	{
		out.write_float(s.o2Moles);
		out.write_float(s.co2Moles);
		out.write_float(s.otherMoles);
		out.write_float(s.vaporMoles);
		out.write_float(s.nitrogenMoles);
	}
}
