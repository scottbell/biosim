package com.traclabs.biosim.idl.simulation.air.cdrs;


/**
 * Generated from IDL interface "CDRSModule".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class CDRSModulePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getBlowerArmedStatus", Integer.valueOf(0));
		m_opsHash.put ( "setWaterPumpState", Integer.valueOf(1));
		m_opsHash.put ( "getAirInletValveState", Integer.valueOf(2));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(3));
		m_opsHash.put ( "setAirInletValveArmedStatus", Integer.valueOf(4));
		m_opsHash.put ( "setCO2IsolationValveArmedStatus", Integer.valueOf(5));
		m_opsHash.put ( "getAirReturnValveState", Integer.valueOf(6));
		m_opsHash.put ( "getPrimaryHeatProduction", Integer.valueOf(7));
		m_opsHash.put ( "getTickLength", Integer.valueOf(8));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(9));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(10));
		m_opsHash.put ( "getCO2VentValveArmedStatus", Integer.valueOf(11));
		m_opsHash.put ( "getGreyWaterProducerDefinition", Integer.valueOf(12));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(13));
		m_opsHash.put ( "setAirInletValveState", Integer.valueOf(14));
		m_opsHash.put ( "setCO2IsolationValveState", Integer.valueOf(15));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(16));
		m_opsHash.put ( "getID", Integer.valueOf(17));
		m_opsHash.put ( "setBlowerArmedStatus", Integer.valueOf(18));
		m_opsHash.put ( "setBlowerState", Integer.valueOf(19));
		m_opsHash.put ( "getCO2VentValveState", Integer.valueOf(20));
		m_opsHash.put ( "getAirReturnValveArmedStatus", Integer.valueOf(21));
		m_opsHash.put ( "getWaterPumpState", Integer.valueOf(22));
		m_opsHash.put ( "setArmedStatus", Integer.valueOf(23));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(24));
		m_opsHash.put ( "setAirReturnValveArmedStatus", Integer.valueOf(25));
		m_opsHash.put ( "getGreyWaterConsumerDefinition", Integer.valueOf(26));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(27));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(28));
		m_opsHash.put ( "getState", Integer.valueOf(29));
		m_opsHash.put ( "getAirInletValveArmedStatus", Integer.valueOf(30));
		m_opsHash.put ( "reset", Integer.valueOf(31));
		m_opsHash.put ( "setState", Integer.valueOf(32));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(33));
		m_opsHash.put ( "getModuleName", Integer.valueOf(34));
		m_opsHash.put ( "randomFilter", Integer.valueOf(35));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(36));
		m_opsHash.put ( "log", Integer.valueOf(37));
		m_opsHash.put ( "getCO2IsolationValveState", Integer.valueOf(38));
		m_opsHash.put ( "getAirProducerDefinition", Integer.valueOf(39));
		m_opsHash.put ( "maintain", Integer.valueOf(40));
		m_opsHash.put ( "getSecondaryHeatProduction", Integer.valueOf(41));
		m_opsHash.put ( "setCO2VentValveArmedStatus", Integer.valueOf(42));
		m_opsHash.put ( "setAirReturnValveState", Integer.valueOf(43));
		m_opsHash.put ( "getCO2ProducerDefinition", Integer.valueOf(44));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(45));
		m_opsHash.put ( "setDayNightState", Integer.valueOf(46));
		m_opsHash.put ( "getWaterPumpArmedStatus", Integer.valueOf(47));
		m_opsHash.put ( "getAirConsumerDefinition", Integer.valueOf(48));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(49));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(50));
		m_opsHash.put ( "getPowerConsumerDefinition", Integer.valueOf(51));
		m_opsHash.put ( "getArmedStatus", Integer.valueOf(52));
		m_opsHash.put ( "tick", Integer.valueOf(53));
		m_opsHash.put ( "setTickLength", Integer.valueOf(54));
		m_opsHash.put ( "getBlowerState", Integer.valueOf(55));
		m_opsHash.put ( "getCO2IsolationValveArmedStatus", Integer.valueOf(56));
		m_opsHash.put ( "setCO2VentValveState", Integer.valueOf(57));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(58));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(59));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(60));
		m_opsHash.put ( "getDayNightState", Integer.valueOf(61));
		m_opsHash.put ( "setWaterPumpArmedStatus", Integer.valueOf(62));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/air/cdrs/CDRSModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0"};
	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule __r = com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule __r = com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleHelper.narrow(__o);
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
			case 0: // getBlowerArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.write(_out,getBlowerArmedStatus());
				break;
			}
			case 1: // setWaterPumpState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerStateHelper.read(_input);
				_out = handler.createReply();
				setWaterPumpState(_arg0);
				break;
			}
			case 2: // getAirInletValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.write(_out,getAirInletValveState());
				break;
			}
			case 3: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 4: // setAirInletValveArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setAirInletValveArmedStatus(_arg0);
				break;
			}
			case 5: // setCO2IsolationValveArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setCO2IsolationValveArmedStatus(_arg0);
				break;
			}
			case 6: // getAirReturnValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.write(_out,getAirReturnValveState());
				break;
			}
			case 7: // getPrimaryHeatProduction
			{
				_out = handler.createReply();
				_out.write_float(getPrimaryHeatProduction());
				break;
			}
			case 8: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 9: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 10: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 11: // getCO2VentValveArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.write(_out,getCO2VentValveArmedStatus());
				break;
			}
			case 12: // getGreyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionHelper.write(_out,getGreyWaterProducerDefinition());
				break;
			}
			case 13: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 14: // setAirInletValveState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.read(_input);
				_out = handler.createReply();
				setAirInletValveState(_arg0);
				break;
			}
			case 15: // setCO2IsolationValveState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.read(_input);
				_out = handler.createReply();
				setCO2IsolationValveState(_arg0);
				break;
			}
			case 16: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 17: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 18: // setBlowerArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setBlowerArmedStatus(_arg0);
				break;
			}
			case 19: // setBlowerState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerStateHelper.read(_input);
				_out = handler.createReply();
				setBlowerState(_arg0);
				break;
			}
			case 20: // getCO2VentValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.write(_out,getCO2VentValveState());
				break;
			}
			case 21: // getAirReturnValveArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.write(_out,getAirReturnValveArmedStatus());
				break;
			}
			case 22: // getWaterPumpState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerStateHelper.write(_out,getWaterPumpState());
				break;
			}
			case 23: // setArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatusHelper.read(_input);
				_out = handler.createReply();
				setArmedStatus(_arg0);
				break;
			}
			case 24: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 25: // setAirReturnValveArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setAirReturnValveArmedStatus(_arg0);
				break;
			}
			case 26: // getGreyWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionHelper.write(_out,getGreyWaterConsumerDefinition());
				break;
			}
			case 27: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 28: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 29: // getState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSStateHelper.write(_out,getState());
				break;
			}
			case 30: // getAirInletValveArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.write(_out,getAirInletValveArmedStatus());
				break;
			}
			case 31: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 32: // setState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSStateHelper.read(_input);
				_out = handler.createReply();
				setState(_arg0);
				break;
			}
			case 33: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 34: // getModuleName
			{
				_out = handler.createReply();
				java.lang.String tmpResult119 = getModuleName();
_out.write_string( tmpResult119 );
				break;
			}
			case 35: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 36: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 37: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 38: // getCO2IsolationValveState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.write(_out,getCO2IsolationValveState());
				break;
			}
			case 39: // getAirProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper.write(_out,getAirProducerDefinition());
				break;
			}
			case 40: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 41: // getSecondaryHeatProduction
			{
				_out = handler.createReply();
				_out.write_float(getSecondaryHeatProduction());
				break;
			}
			case 42: // setCO2VentValveArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setCO2VentValveArmedStatus(_arg0);
				break;
			}
			case 43: // setAirReturnValveState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.read(_input);
				_out = handler.createReply();
				setAirReturnValveState(_arg0);
				break;
			}
			case 44: // getCO2ProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinitionHelper.write(_out,getCO2ProducerDefinition());
				break;
			}
			case 45: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 46: // setDayNightState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightStateHelper.read(_input);
				_out = handler.createReply();
				setDayNightState(_arg0);
				break;
			}
			case 47: // getWaterPumpArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.write(_out,getWaterPumpArmedStatus());
				break;
			}
			case 48: // getAirConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionHelper.write(_out,getAirConsumerDefinition());
				break;
			}
			case 49: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 50: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 51: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
				break;
			}
			case 52: // getArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatusHelper.write(_out,getArmedStatus());
				break;
			}
			case 53: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 54: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 55: // getBlowerState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerStateHelper.write(_out,getBlowerState());
				break;
			}
			case 56: // getCO2IsolationValveArmedStatus
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.write(_out,getCO2IsolationValveArmedStatus());
				break;
			}
			case 57: // setCO2VentValveState
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveStateHelper.read(_input);
				_out = handler.createReply();
				setCO2VentValveState(_arg0);
				break;
			}
			case 58: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 59: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 60: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
				break;
			}
			case 61: // getDayNightState
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightStateHelper.write(_out,getDayNightState());
				break;
			}
			case 62: // setWaterPumpArmedStatus
			{
				com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus _arg0=com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatusHelper.read(_input);
				_out = handler.createReply();
				setWaterPumpArmedStatus(_arg0);
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
