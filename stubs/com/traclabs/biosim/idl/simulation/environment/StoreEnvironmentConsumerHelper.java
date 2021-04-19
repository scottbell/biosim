package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "StoreEnvironmentConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class StoreEnvironmentConsumerHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(StoreEnvironmentConsumerHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/environment/StoreEnvironmentConsumer:1.0", "StoreEnvironmentConsumer");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/environment/StoreEnvironmentConsumer:1.0";
	}
	public static StoreEnvironmentConsumer read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.simulation.environment._StoreEnvironmentConsumerStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer)
		{
			return (com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/environment/StoreEnvironmentConsumer:1.0"))
		{
			com.traclabs.biosim.idl.simulation.environment._StoreEnvironmentConsumerStub stub;
			stub = new com.traclabs.biosim.idl.simulation.environment._StoreEnvironmentConsumerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer)
		{
			return (com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentConsumer)obj;
		}
		else
		{
			com.traclabs.biosim.idl.simulation.environment._StoreEnvironmentConsumerStub stub;
			stub = new com.traclabs.biosim.idl.simulation.environment._StoreEnvironmentConsumerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
