package com.traclabs.biosim.idl.simulation.thermal;


/**
 * Generated from IDL interface "IATCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class IATCSPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.thermal.IATCSOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getActivateState", Integer.valueOf(0));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(1));
		m_opsHash.put ( "getTickLength", Integer.valueOf(2));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(3));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(4));
		m_opsHash.put ( "getGreyWaterProducerDefinition", Integer.valueOf(5));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(6));
		m_opsHash.put ( "setPumpSpeed", Integer.valueOf(7));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(8));
		m_opsHash.put ( "getPpaPumpSpeedCommandStatus", Integer.valueOf(9));
		m_opsHash.put ( "getID", Integer.valueOf(10));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(11));
		m_opsHash.put ( "getIsolationValveCommandStatus", Integer.valueOf(12));
		m_opsHash.put ( "getGreyWaterConsumerDefinition", Integer.valueOf(13));
		m_opsHash.put ( "setPpaPumpSpeedCommandStatus", Integer.valueOf(14));
		m_opsHash.put ( "setIsloationValveState", Integer.valueOf(15));
		m_opsHash.put ( "setIatcsState", Integer.valueOf(16));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(17));
		m_opsHash.put ( "setBypassValveCommandStatus", Integer.valueOf(18));
		m_opsHash.put ( "getBypassValveState", Integer.valueOf(19));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(20));
		m_opsHash.put ( "getSfcaSoftwareState", Integer.valueOf(21));
		m_opsHash.put ( "reset", Integer.valueOf(22));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(23));
		m_opsHash.put ( "getModuleName", Integer.valueOf(24));
		m_opsHash.put ( "setBypassValveState", Integer.valueOf(25));
		m_opsHash.put ( "setHeaterSoftwareState", Integer.valueOf(26));
		m_opsHash.put ( "randomFilter", Integer.valueOf(27));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(28));
		m_opsHash.put ( "log", Integer.valueOf(29));
		m_opsHash.put ( "setActivateState", Integer.valueOf(30));
		m_opsHash.put ( "setTwvmSoftwareState", Integer.valueOf(31));
		m_opsHash.put ( "getPumpSpeed", Integer.valueOf(32));
		m_opsHash.put ( "maintain", Integer.valueOf(33));
		m_opsHash.put ( "getBypassValveCommandStatus", Integer.valueOf(34));
		m_opsHash.put ( "setIsolationValveCommandStatus", Integer.valueOf(35));
		m_opsHash.put ( "setSfcaSoftwareState", Integer.valueOf(36));
		m_opsHash.put ( "getHeaterSoftwareState", Integer.valueOf(37));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(38));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(39));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(40));
		m_opsHash.put ( "getPowerConsumerDefinition", Integer.valueOf(41));
		m_opsHash.put ( "tick", Integer.valueOf(42));
		m_opsHash.put ( "setTickLength", Integer.valueOf(43));
		m_opsHash.put ( "getIatcsState", Integer.valueOf(44));
		m_opsHash.put ( "getIsloationValveState", Integer.valueOf(45));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(46));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(47));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(48));
		m_opsHash.put ( "getTwvmSoftwareState", Integer.valueOf(49));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/thermal/IATCS:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/WaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0"};
	public com.traclabs.biosim.idl.simulation.thermal.IATCS _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.thermal.IATCS __r = com.traclabs.biosim.idl.simulation.thermal.IATCSHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.thermal.IATCS _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.thermal.IATCS __r = com.traclabs.biosim.idl.simulation.thermal.IATCSHelper.narrow(__o);
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
			case 0: // getActivateState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IATCSActivationHelper.write(_out,getActivateState());
				break;
			}
			case 1: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 2: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 3: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 4: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 5: // getGreyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionHelper.write(_out,getGreyWaterProducerDefinition());
				break;
			}
			case 6: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 7: // setPumpSpeed
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setPumpSpeed(_arg0);
				break;
			}
			case 8: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 9: // getPpaPumpSpeedCommandStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatusHelper.write(_out,getPpaPumpSpeedCommandStatus());
				break;
			}
			case 10: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 11: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 12: // getIsolationValveCommandStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.write(_out,getIsolationValveCommandStatus());
				break;
			}
			case 13: // getGreyWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionHelper.write(_out,getGreyWaterConsumerDefinition());
				break;
			}
			case 14: // setPpaPumpSpeedCommandStatus
			{
				com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus _arg0=com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatusHelper.read(_input);
				_out = handler.createReply();
				setPpaPumpSpeedCommandStatus(_arg0);
				break;
			}
			case 15: // setIsloationValveState
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveState _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXValveStateHelper.read(_input);
				_out = handler.createReply();
				setIsloationValveState(_arg0);
				break;
			}
			case 16: // setIatcsState
			{
				com.traclabs.biosim.idl.simulation.thermal.IATCSState _arg0=com.traclabs.biosim.idl.simulation.thermal.IATCSStateHelper.read(_input);
				_out = handler.createReply();
				setIatcsState(_arg0);
				break;
			}
			case 17: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 18: // setBypassValveCommandStatus
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setBypassValveCommandStatus(_arg0);
				break;
			}
			case 19: // getBypassValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXBypassStateHelper.write(_out,getBypassValveState());
				break;
			}
			case 20: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 21: // getSfcaSoftwareState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.write(_out,getSfcaSoftwareState());
				break;
			}
			case 22: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 23: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 24: // getModuleName
			{
				_out = handler.createReply();
				java.lang.String tmpResult145 = getModuleName();
_out.write_string( tmpResult145 );
				break;
			}
			case 25: // setBypassValveState
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXBypassStateHelper.read(_input);
				_out = handler.createReply();
				setBypassValveState(_arg0);
				break;
			}
			case 26: // setHeaterSoftwareState
			{
				com.traclabs.biosim.idl.simulation.thermal.SoftwareState _arg0=com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.read(_input);
				_out = handler.createReply();
				setHeaterSoftwareState(_arg0);
				break;
			}
			case 27: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 28: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 29: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 30: // setActivateState
			{
				com.traclabs.biosim.idl.simulation.thermal.IATCSActivation _arg0=com.traclabs.biosim.idl.simulation.thermal.IATCSActivationHelper.read(_input);
				_out = handler.createReply();
				setActivateState(_arg0);
				break;
			}
			case 31: // setTwvmSoftwareState
			{
				com.traclabs.biosim.idl.simulation.thermal.SoftwareState _arg0=com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.read(_input);
				_out = handler.createReply();
				setTwvmSoftwareState(_arg0);
				break;
			}
			case 32: // getPumpSpeed
			{
				_out = handler.createReply();
				_out.write_float(getPumpSpeed());
				break;
			}
			case 33: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 34: // getBypassValveCommandStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.write(_out,getBypassValveCommandStatus());
				break;
			}
			case 35: // setIsolationValveCommandStatus
			{
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus _arg0=com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setIsolationValveCommandStatus(_arg0);
				break;
			}
			case 36: // setSfcaSoftwareState
			{
				com.traclabs.biosim.idl.simulation.thermal.SoftwareState _arg0=com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.read(_input);
				_out = handler.createReply();
				setSfcaSoftwareState(_arg0);
				break;
			}
			case 37: // getHeaterSoftwareState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.write(_out,getHeaterSoftwareState());
				break;
			}
			case 38: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 39: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 40: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 41: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
				break;
			}
			case 42: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 43: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 44: // getIatcsState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IATCSStateHelper.write(_out,getIatcsState());
				break;
			}
			case 45: // getIsloationValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.IFHXValveStateHelper.write(_out,getIsloationValveState());
				break;
			}
			case 46: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 47: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 48: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 49: // getTwvmSoftwareState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.write(_out,getTwvmSoftwareState());
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
