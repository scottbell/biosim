package com.traclabs.biosim.idl.simulation.food;
/**
 * Generated from IDL enum "PlantType".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PlantTypeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public PlantType value;

	public PlantTypeHolder ()
	{
	}
	public PlantTypeHolder (final PlantType initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PlantTypeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PlantTypeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PlantTypeHelper.write (out,value);
	}
}
