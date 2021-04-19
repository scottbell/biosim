package com.traclabs.biosim.idl.simulation.waste;

/**
 * Generated from IDL alias "DryWasteStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class DryWasteStoreListHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] extract (final org.omg.CORBA.Any any)
	{
		if ( any.type().kind() == org.omg.CORBA.TCKind.tk_null)
		{
			throw new org.omg.CORBA.BAD_OPERATION ("Can't extract from Any with null type.");
		}
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(DryWasteStoreListHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(com.traclabs.biosim.idl.simulation.waste.DryWasteStoreListHelper.id(), "DryWasteStoreList",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_interface_tc("IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteStore:1.0", "DryWasteStore")));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteStoreList:1.0";
	}
	public static com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] _result;
		int _l_result22 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result22 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result22);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new com.traclabs.biosim.idl.simulation.waste.DryWasteStore[_l_result22];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=com.traclabs.biosim.idl.simulation.waste.DryWasteStoreHelper.read(_in);
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			com.traclabs.biosim.idl.simulation.waste.DryWasteStoreHelper.write(_out,_s[i]);
		}

	}
}
