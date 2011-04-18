package com.traclabs.biosim.idl.simulation.crew;
/**
 *	Generated from IDL definition of enum "ScheduleType"
 *	@author JacORB IDL compiler 
 */

public final class ScheduleTypeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.crew.ScheduleTypeHelper.id(),"ScheduleType",new String[]{"ACTIVE_SCHED","PASSIVE_SCHED","VACATION_SCHED"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.crew.ScheduleType s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.crew.ScheduleType extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/crew/ScheduleType:1.0";
	}
	public static ScheduleType read (final org.omg.CORBA.portable.InputStream in)
	{
		return ScheduleType.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final ScheduleType s)
	{
		out.write_long(s.value());
	}
}
