package com.traclabs.biosim.idl.actuator.water;


/**
 * Generated from IDL interface "WaterRSActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class WaterRSActuatorHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(WaterRSActuatorHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/actuator/water/WaterRSActuator:1.0", "WaterRSActuator");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.actuator.water.WaterRSActuator s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.actuator.water.WaterRSActuator extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/actuator/water/WaterRSActuator:1.0";
	}
	public static WaterRSActuator read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.actuator.water._WaterRSActuatorStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.actuator.water.WaterRSActuator s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.actuator.water.WaterRSActuator narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.actuator.water.WaterRSActuator)
		{
			return (com.traclabs.biosim.idl.actuator.water.WaterRSActuator)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/actuator/water/WaterRSActuator:1.0"))
		{
			com.traclabs.biosim.idl.actuator.water._WaterRSActuatorStub stub;
			stub = new com.traclabs.biosim.idl.actuator.water._WaterRSActuatorStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.actuator.water.WaterRSActuator unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.actuator.water.WaterRSActuator)
		{
			return (com.traclabs.biosim.idl.actuator.water.WaterRSActuator)obj;
		}
		else
		{
			com.traclabs.biosim.idl.actuator.water._WaterRSActuatorStub stub;
			stub = new com.traclabs.biosim.idl.actuator.water._WaterRSActuatorStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
