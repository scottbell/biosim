package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "Shelf".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class ShelfPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.food.ShelfOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "setStartTick", Integer.valueOf(0));
		m_opsHash.put ( "getPlant", Integer.valueOf(1));
		m_opsHash.put ( "replant", Integer.valueOf(2));
		m_opsHash.put ( "harvest", Integer.valueOf(3));
		m_opsHash.put ( "isDead", Integer.valueOf(4));
		m_opsHash.put ( "kill", Integer.valueOf(5));
		m_opsHash.put ( "getBiomassPS", Integer.valueOf(6));
		m_opsHash.put ( "getHarvestInterval", Integer.valueOf(7));
		m_opsHash.put ( "getCropTypeString", Integer.valueOf(8));
		m_opsHash.put ( "getCropType", Integer.valueOf(9));
		m_opsHash.put ( "isReadyForHavest", Integer.valueOf(10));
		m_opsHash.put ( "getCropAreaUsed", Integer.valueOf(11));
		m_opsHash.put ( "getTimeTillCanopyClosure", Integer.valueOf(12));
		m_opsHash.put ( "getCropAreaTotal", Integer.valueOf(13));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/food/Shelf:1.0"};
	public com.traclabs.biosim.idl.simulation.food.Shelf _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.food.Shelf __r = com.traclabs.biosim.idl.simulation.food.ShelfHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.food.Shelf _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.food.Shelf __r = com.traclabs.biosim.idl.simulation.food.ShelfHelper.narrow(__o);
		return __r;
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // setStartTick
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setStartTick(_arg0);
				break;
			}
			case 1: // getPlant
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.PlantHelper.write(_out,getPlant());
				break;
			}
			case 2: // replant
			{
				com.traclabs.biosim.idl.simulation.food.PlantType _arg0=com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.read(_input);
				float _arg1=_input.read_float();
				_out = handler.createReply();
				replant(_arg0,_arg1);
				break;
			}
			case 3: // harvest
			{
				_out = handler.createReply();
				harvest();
				break;
			}
			case 4: // isDead
			{
				_out = handler.createReply();
				_out.write_boolean(isDead());
				break;
			}
			case 5: // kill
			{
				_out = handler.createReply();
				kill();
				break;
			}
			case 6: // getBiomassPS
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.BiomassPSHelper.write(_out,getBiomassPS());
				break;
			}
			case 7: // getHarvestInterval
			{
				_out = handler.createReply();
				_out.write_float(getHarvestInterval());
				break;
			}
			case 8: // getCropTypeString
			{
				_out = handler.createReply();
				java.lang.String tmpResult73 = getCropTypeString();
_out.write_string( tmpResult73 );
				break;
			}
			case 9: // getCropType
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.PlantTypeHelper.write(_out,getCropType());
				break;
			}
			case 10: // isReadyForHavest
			{
				_out = handler.createReply();
				_out.write_boolean(isReadyForHavest());
				break;
			}
			case 11: // getCropAreaUsed
			{
				_out = handler.createReply();
				_out.write_float(getCropAreaUsed());
				break;
			}
			case 12: // getTimeTillCanopyClosure
			{
				_out = handler.createReply();
				_out.write_float(getTimeTillCanopyClosure());
				break;
			}
			case 13: // getCropAreaTotal
			{
				_out = handler.createReply();
				_out.write_float(getCropAreaTotal());
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
