package com.traclabs.biosim.idl.simulation.air;


/**
 *	Generated from IDL interface "Pyrolizer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PyrolizerHelper
{
	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.air.Pyrolizer s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/air/Pyrolizer:1.0", "Pyrolizer");
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/air/Pyrolizer:1.0";
	}
	public static Pyrolizer read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.air.Pyrolizer s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer narrow(final java.lang.Object obj)
	{
		if (obj instanceof com.traclabs.biosim.idl.simulation.air.Pyrolizer)
		{
			return (com.traclabs.biosim.idl.simulation.air.Pyrolizer)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.simulation.air.Pyrolizer)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/air/Pyrolizer:1.0"))
			{
				com.traclabs.biosim.idl.simulation.air._PyrolizerStub stub;
				stub = new com.traclabs.biosim.idl.simulation.air._PyrolizerStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.simulation.air.Pyrolizer)obj;
		}
		catch (ClassCastException c)
		{
				com.traclabs.biosim.idl.simulation.air._PyrolizerStub stub;
				stub = new com.traclabs.biosim.idl.simulation.air._PyrolizerStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
