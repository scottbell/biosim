package com.traclabs.biosim.idl.simulation.power;
/**
 * Generated from IDL enum "RPCMSwitchState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class RPCMSwitchStateHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(RPCMSwitchStateHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.power.RPCMSwitchStateHelper.id(),"RPCMSwitchState",new String[]{"open","closed"});
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.power.RPCMSwitchState s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.power.RPCMSwitchState extract (final org.omg.CORBA.Any any)
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
		return "IDL:com/traclabs/biosim/idl/simulation/power/RPCMSwitchState:1.0";
	}
	public static RPCMSwitchState read (final org.omg.CORBA.portable.InputStream in)
	{
		return RPCMSwitchState.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final RPCMSwitchState s)
	{
		out.write_long(s.value());
	}
}
