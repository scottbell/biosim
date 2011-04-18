package com.traclabs.biosim.idl.simulation.waste;


/**
 *	Generated from IDL interface "Incinerator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class IncineratorHelper
{
	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.waste.Incinerator s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.waste.Incinerator extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/waste/Incinerator:1.0", "Incinerator");
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/waste/Incinerator:1.0";
	}
	public static Incinerator read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.waste.Incinerator s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.waste.Incinerator narrow(final java.lang.Object obj)
	{
		if (obj instanceof com.traclabs.biosim.idl.simulation.waste.Incinerator)
		{
			return (com.traclabs.biosim.idl.simulation.waste.Incinerator)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static com.traclabs.biosim.idl.simulation.waste.Incinerator narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.simulation.waste.Incinerator)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/waste/Incinerator:1.0"))
			{
				com.traclabs.biosim.idl.simulation.waste._IncineratorStub stub;
				stub = new com.traclabs.biosim.idl.simulation.waste._IncineratorStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static com.traclabs.biosim.idl.simulation.waste.Incinerator unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.simulation.waste.Incinerator)obj;
		}
		catch (ClassCastException c)
		{
				com.traclabs.biosim.idl.simulation.waste._IncineratorStub stub;
				stub = new com.traclabs.biosim.idl.simulation.waste._IncineratorStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}
