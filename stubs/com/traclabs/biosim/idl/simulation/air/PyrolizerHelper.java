package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "Pyrolizer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class PyrolizerHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(PyrolizerHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/air/Pyrolizer:1.0", "Pyrolizer");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.air.Pyrolizer s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/air/Pyrolizer:1.0";
	}
	public static Pyrolizer read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.simulation.air._PyrolizerStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.air.Pyrolizer s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.air.Pyrolizer)
		{
			return (com.traclabs.biosim.idl.simulation.air.Pyrolizer)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/air/Pyrolizer:1.0"))
		{
			com.traclabs.biosim.idl.simulation.air._PyrolizerStub stub;
			stub = new com.traclabs.biosim.idl.simulation.air._PyrolizerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.simulation.air.Pyrolizer unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.air.Pyrolizer)
		{
			return (com.traclabs.biosim.idl.simulation.air.Pyrolizer)obj;
		}
		else
		{
			com.traclabs.biosim.idl.simulation.air._PyrolizerStub stub;
			stub = new com.traclabs.biosim.idl.simulation.air._PyrolizerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
