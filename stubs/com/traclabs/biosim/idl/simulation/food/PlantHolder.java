package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "Plant".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PlantHolder	implements org.omg.CORBA.portable.Streamable{
	 public Plant value;
	public PlantHolder()
	{
	}
	public PlantHolder (final Plant initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PlantHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PlantHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PlantHelper.write (_out,value);
	}
}
