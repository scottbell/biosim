package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "EnvironmentCO2Store".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class EnvironmentCO2StoreHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(EnvironmentCO2StoreHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentCO2Store:1.0", "EnvironmentCO2Store");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentCO2Store:1.0";
	}
	public static EnvironmentCO2Store read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.simulation.environment._EnvironmentCO2StoreStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store)
		{
			return (com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentCO2Store:1.0"))
		{
			com.traclabs.biosim.idl.simulation.environment._EnvironmentCO2StoreStub stub;
			stub = new com.traclabs.biosim.idl.simulation.environment._EnvironmentCO2StoreStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store)
		{
			return (com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store)obj;
		}
		else
		{
			com.traclabs.biosim.idl.simulation.environment._EnvironmentCO2StoreStub stub;
			stub = new com.traclabs.biosim.idl.simulation.environment._EnvironmentCO2StoreStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
