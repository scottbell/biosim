package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "BiomassStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class BiomassStorePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.food.BiomassStoreOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "addBioMatter", Integer.valueOf(0));
		m_opsHash.put ( "setPipe", Integer.valueOf(1));
		m_opsHash.put ( "setCurrentLevel", Integer.valueOf(2));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(3));
		m_opsHash.put ( "calculateWaterContentInStore", Integer.valueOf(4));
		m_opsHash.put ( "getTickLength", Integer.valueOf(5));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(6));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(7));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(8));
		m_opsHash.put ( "getCurrentCapacity", Integer.valueOf(9));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(10));
		m_opsHash.put ( "getID", Integer.valueOf(11));
		m_opsHash.put ( "setInitialCapacity", Integer.valueOf(12));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(13));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(14));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(15));
		m_opsHash.put ( "getInitialCapacity", Integer.valueOf(16));
		m_opsHash.put ( "reset", Integer.valueOf(17));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(18));
		m_opsHash.put ( "getOverflow", Integer.valueOf(19));
		m_opsHash.put ( "take", Integer.valueOf(20));
		m_opsHash.put ( "getPercentageFilled", Integer.valueOf(21));
		m_opsHash.put ( "getModuleName", Integer.valueOf(22));
		m_opsHash.put ( "randomFilter", Integer.valueOf(23));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(24));
		m_opsHash.put ( "log", Integer.valueOf(25));
		m_opsHash.put ( "setInitialLevel", Integer.valueOf(26));
		m_opsHash.put ( "maintain", Integer.valueOf(27));
		m_opsHash.put ( "setInitialBioMatterLevel", Integer.valueOf(28));
		m_opsHash.put ( "getCurrentLevel", Integer.valueOf(29));
		m_opsHash.put ( "setCurrentCapacity", Integer.valueOf(30));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(31));
		m_opsHash.put ( "takeBioMatterMass", Integer.valueOf(32));
		m_opsHash.put ( "add", Integer.valueOf(33));
		m_opsHash.put ( "isPipe", Integer.valueOf(34));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(35));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(36));
		m_opsHash.put ( "getInitialLevel", Integer.valueOf(37));
		m_opsHash.put ( "tick", Integer.valueOf(38));
		m_opsHash.put ( "setTickLength", Integer.valueOf(39));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(40));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(41));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(42));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/food/BiomassStore:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/PassiveModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Store:1.0"};
	public com.traclabs.biosim.idl.simulation.food.BiomassStore _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.food.BiomassStore __r = com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.food.BiomassStore _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.food.BiomassStore __r = com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper.narrow(__o);
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
			case 0: // addBioMatter
			{
				com.traclabs.biosim.idl.simulation.food.BioMatter _arg0=com.traclabs.biosim.idl.simulation.food.BioMatterHelper.read(_input);
				_out = handler.createReply();
				_out.write_float(addBioMatter(_arg0));
				break;
			}
			case 1: // setPipe
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setPipe(_arg0);
				break;
			}
			case 2: // setCurrentLevel
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setCurrentLevel(_arg0);
				break;
			}
			case 3: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 4: // calculateWaterContentInStore
			{
				_out = handler.createReply();
				_out.write_float(calculateWaterContentInStore());
				break;
			}
			case 5: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 6: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 7: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 8: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 9: // getCurrentCapacity
			{
				_out = handler.createReply();
				_out.write_float(getCurrentCapacity());
				break;
			}
			case 10: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 11: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 12: // setInitialCapacity
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setInitialCapacity(_arg0);
				break;
			}
			case 13: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 14: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 15: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 16: // getInitialCapacity
			{
				_out = handler.createReply();
				_out.write_float(getInitialCapacity());
				break;
			}
			case 17: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 18: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 19: // getOverflow
			{
				_out = handler.createReply();
				_out.write_float(getOverflow());
				break;
			}
			case 20: // take
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(take(_arg0));
				break;
			}
			case 21: // getPercentageFilled
			{
				_out = handler.createReply();
				_out.write_float(getPercentageFilled());
				break;
			}
			case 22: // getModuleName
			{
				_out = handler.createReply();
				java.lang.String tmpResult72 = getModuleName();
_out.write_string( tmpResult72 );
				break;
			}
			case 23: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 24: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 25: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 26: // setInitialLevel
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setInitialLevel(_arg0);
				break;
			}
			case 27: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 28: // setInitialBioMatterLevel
			{
				com.traclabs.biosim.idl.simulation.food.BioMatter _arg0=com.traclabs.biosim.idl.simulation.food.BioMatterHelper.read(_input);
				_out = handler.createReply();
				setInitialBioMatterLevel(_arg0);
				break;
			}
			case 29: // getCurrentLevel
			{
				_out = handler.createReply();
				_out.write_float(getCurrentLevel());
				break;
			}
			case 30: // setCurrentCapacity
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setCurrentCapacity(_arg0);
				break;
			}
			case 31: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 32: // takeBioMatterMass
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.BioMatterListHelper.write(_out,takeBioMatterMass(_arg0));
				break;
			}
			case 33: // add
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(add(_arg0));
				break;
			}
			case 34: // isPipe
			{
				_out = handler.createReply();
				_out.write_boolean(isPipe());
				break;
			}
			case 35: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 36: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 37: // getInitialLevel
			{
				_out = handler.createReply();
				_out.write_float(getInitialLevel());
				break;
			}
			case 38: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 39: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 40: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 41: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 42: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
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
