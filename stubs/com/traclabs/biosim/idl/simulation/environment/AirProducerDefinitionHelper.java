package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "AirProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class AirProducerDefinitionHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(AirProducerDefinitionHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/environment/AirProducerDefinition:1.0", "AirProducerDefinition");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/environment/AirProducerDefinition:1.0";
	}
	public static AirProducerDefinition read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.simulation.environment._AirProducerDefinitionStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition)
		{
			return (com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/environment/AirProducerDefinition:1.0"))
		{
			com.traclabs.biosim.idl.simulation.environment._AirProducerDefinitionStub stub;
			stub = new com.traclabs.biosim.idl.simulation.environment._AirProducerDefinitionStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition)
		{
			return (com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition)obj;
		}
		else
		{
			com.traclabs.biosim.idl.simulation.environment._AirProducerDefinitionStub stub;
			stub = new com.traclabs.biosim.idl.simulation.environment._AirProducerDefinitionStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
