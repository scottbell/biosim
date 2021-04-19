package com.traclabs.biosim.idl.simulation.framework;


/**
 * Generated from IDL interface "BioCommandListener".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class BioCommandListenerHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(BioCommandListenerHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/framework/BioCommandListener:1.0", "BioCommandListener");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.framework.BioCommandListener s)
	{
			any.insert_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.framework.BioCommandListener extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/framework/BioCommandListener:1.0";
	}
	public static BioCommandListener read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(com.traclabs.biosim.idl.simulation.framework._BioCommandListenerStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final com.traclabs.biosim.idl.simulation.framework.BioCommandListener s)
	{
		_out.write_Object(s);
	}
	public static com.traclabs.biosim.idl.simulation.framework.BioCommandListener narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.framework.BioCommandListener)
		{
			return (com.traclabs.biosim.idl.simulation.framework.BioCommandListener)obj;
		}
		else if (obj._is_a("IDL:com/traclabs/biosim/idl/simulation/framework/BioCommandListener:1.0"))
		{
			com.traclabs.biosim.idl.simulation.framework._BioCommandListenerStub stub;
			stub = new com.traclabs.biosim.idl.simulation.framework._BioCommandListenerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static com.traclabs.biosim.idl.simulation.framework.BioCommandListener unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof com.traclabs.biosim.idl.simulation.framework.BioCommandListener)
		{
			return (com.traclabs.biosim.idl.simulation.framework.BioCommandListener)obj;
		}
		else
		{
			com.traclabs.biosim.idl.simulation.framework._BioCommandListenerStub stub;
			stub = new com.traclabs.biosim.idl.simulation.framework._BioCommandListenerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
