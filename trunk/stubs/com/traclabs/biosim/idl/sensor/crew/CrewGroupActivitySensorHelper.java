package com.traclabs.biosim.idl.sensor.crew;


/**
 *	Generated from IDL interface "CrewGroupActivitySensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrewGroupActivitySensorHelper
{
	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/sensor/crew/CrewGroupActivitySensor:1.0", "CrewGroupActivitySensor");
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/sensor/crew/CrewGroupActivitySensor:1.0";
	}
	public static CrewGroupActivitySensor read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object());
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor narrow(final java.lang.Object obj)
	{
		if (obj instanceof com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor)
		{
			return (com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor)obj;
		}
		else if (obj instanceof org.omg.CORBA.Object)
		{
			return narrow((org.omg.CORBA.Object)obj);
		}
		throw new org.omg.CORBA.BAD_PARAM("Failed to narrow in helper");
	}
	public static com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor)obj;
		}
		catch (ClassCastException c)
		{
			if (obj._is_a("IDL:com/traclabs/biosim/idl/sensor/crew/CrewGroupActivitySensor:1.0"))
			{
				com.traclabs.biosim.idl.sensor.crew._CrewGroupActivitySensorStub stub;
				stub = new com.traclabs.biosim.idl.sensor.crew._CrewGroupActivitySensorStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
			}
		}
		throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
	}
	public static com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
			return null;
		try
		{
			return (com.traclabs.biosim.idl.sensor.crew.CrewGroupActivitySensor)obj;
		}
		catch (ClassCastException c)
		{
				com.traclabs.biosim.idl.sensor.crew._CrewGroupActivitySensorStub stub;
				stub = new com.traclabs.biosim.idl.sensor.crew._CrewGroupActivitySensorStub();
				stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
				return stub;
		}
	}
}