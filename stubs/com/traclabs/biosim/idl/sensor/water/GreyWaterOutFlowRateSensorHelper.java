package com.traclabs.biosim.idl.sensor.water;


/**
 * Generated from IDL interface "GreyWaterOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class GreyWaterOutFlowRateSensorHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(GreyWaterOutFlowRateSensorHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/sensor/water/GreyWaterOutFlowRateSensor:1.0", "GreyWaterOutFlowRateSensor");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/sensor/water/GreyWaterOutFlowRateSensor:1.0";
	}
	public static GreyWaterOutFlowRateSensor read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.sensor.water._GreyWaterOutFlowRateSensorStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor)
		{
			return (com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/sensor/water/GreyWaterOutFlowRateSensor:1.0"))
		{
			com.traclabs.biosim.idl.sensor.water._GreyWaterOutFlowRateSensorStub stub;
			stub = new com.traclabs.biosim.idl.sensor.water._GreyWaterOutFlowRateSensorStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor)
		{
			return (com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor)obj;
		}
		else
		{
			com.traclabs.biosim.idl.sensor.water._GreyWaterOutFlowRateSensorStub stub;
			stub = new com.traclabs.biosim.idl.sensor.water._GreyWaterOutFlowRateSensorStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
