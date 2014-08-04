package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXValveState"
 *	@author JacORB IDL compiler 
 */

public final class IFHXValveStateHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.thermal.IFHXValveStateHelper.id(),"IFHXValveState",new String[]{"open","closed"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.thermal.IFHXValveState s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.thermal.IFHXValveState extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/thermal/IFHXValveState:1.0";
	}
	public static IFHXValveState read (final org.omg.CORBA.portable.InputStream in)
	{
		return IFHXValveState.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final IFHXValveState s)
	{
		out.write_long(s.value());
	}
}
