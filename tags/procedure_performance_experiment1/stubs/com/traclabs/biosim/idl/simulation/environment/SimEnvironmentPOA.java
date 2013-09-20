package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "SimEnvironment"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class SimEnvironmentPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.environment.SimEnvironmentOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "clearMalfunction", new java.lang.Integer(0));
		m_opsHash.put ( "getNitrogenStore", new java.lang.Integer(1));
		m_opsHash.put ( "getCO2Store", new java.lang.Integer(2));
		m_opsHash.put ( "getOtherStore", new java.lang.Integer(3));
		m_opsHash.put ( "setAirlockVolume", new java.lang.Integer(4));
		m_opsHash.put ( "getTickLength", new java.lang.Integer(5));
		m_opsHash.put ( "fixAllMalfunctions", new java.lang.Integer(6));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(7));
		m_opsHash.put ( "getTotalMoles", new java.lang.Integer(8));
		m_opsHash.put ( "startMalfunction", new java.lang.Integer(9));
		m_opsHash.put ( "setInitialVolumeAtSeaLevel", new java.lang.Integer(10));
		m_opsHash.put ( "getLeakRate", new java.lang.Integer(11));
		m_opsHash.put ( "getTemperature", new java.lang.Integer(12));
		m_opsHash.put ( "notifyCommandSent", new java.lang.Integer(13));
		m_opsHash.put ( "getID", new java.lang.Integer(14));
		m_opsHash.put ( "setCurrentVolumeAtSeaLevel", new java.lang.Integer(15));
		m_opsHash.put ( "fixMalfunction", new java.lang.Integer(16));
		m_opsHash.put ( "getInitialTotalPressure", new java.lang.Integer(17));
		m_opsHash.put ( "getMyTicks", new java.lang.Integer(18));
		m_opsHash.put ( "getMalfunctions", new java.lang.Integer(19));
		m_opsHash.put ( "getVaporStore", new java.lang.Integer(20));
		m_opsHash.put ( "getInitialVolume", new java.lang.Integer(21));
		m_opsHash.put ( "reset", new java.lang.Integer(22));
		m_opsHash.put ( "getCurrentVolume", new java.lang.Integer(23));
		m_opsHash.put ( "registerBioCommandListener", new java.lang.Integer(24));
		m_opsHash.put ( "getRelativeHumidity", new java.lang.Integer(25));
		m_opsHash.put ( "getModuleName", new java.lang.Integer(26));
		m_opsHash.put ( "getMaxLumens", new java.lang.Integer(27));
		m_opsHash.put ( "randomFilter", new java.lang.Integer(28));
		m_opsHash.put ( "getAirlockVolume", new java.lang.Integer(29));
		m_opsHash.put ( "isMalfunctioning", new java.lang.Integer(30));
		m_opsHash.put ( "setHourOfDayStart", new java.lang.Integer(31));
		m_opsHash.put ( "setInitialVolume", new java.lang.Integer(32));
		m_opsHash.put ( "getO2Store", new java.lang.Integer(33));
		m_opsHash.put ( "log", new java.lang.Integer(34));
		m_opsHash.put ( "getTotalPressure", new java.lang.Integer(35));
		m_opsHash.put ( "removeAirlockPercentage", new java.lang.Integer(36));
		m_opsHash.put ( "maintain", new java.lang.Integer(37));
		m_opsHash.put ( "getDayLength", new java.lang.Integer(38));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(39));
		m_opsHash.put ( "getDangerousOxygenThreshold", new java.lang.Integer(40));
		m_opsHash.put ( "getWaterDensity", new java.lang.Integer(41));
		m_opsHash.put ( "setEnableFailure", new java.lang.Integer(42));
		m_opsHash.put ( "clearAllMalfunctions", new java.lang.Integer(43));
		m_opsHash.put ( "setMaxLumens", new java.lang.Integer(44));
		m_opsHash.put ( "setDayLength", new java.lang.Integer(45));
		m_opsHash.put ( "setDangerousOxygenThreshold", new java.lang.Integer(46));
		m_opsHash.put ( "tick", new java.lang.Integer(47));
		m_opsHash.put ( "getHourOfDayStart", new java.lang.Integer(48));
		m_opsHash.put ( "setTickLength", new java.lang.Integer(49));
		m_opsHash.put ( "getLightIntensity", new java.lang.Integer(50));
		m_opsHash.put ( "isFailureEnabled", new java.lang.Integer(51));
		m_opsHash.put ( "getMalfunctionNames", new java.lang.Integer(52));
		m_opsHash.put ( "scheduleMalfunction", new java.lang.Integer(53));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/environment/SimEnvironment:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/PassiveModule:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0"};
	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment _this()
	{
		return com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.narrow(_this_object(orb));
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
			case 0: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 1: // getNitrogenStore
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.EnvironmentNitrogenStoreHelper.write(_out,getNitrogenStore());
				break;
			}
			case 2: // getCO2Store
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2StoreHelper.write(_out,getCO2Store());
				break;
			}
			case 3: // getOtherStore
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.EnvironmentOtherStoreHelper.write(_out,getOtherStore());
				break;
			}
			case 4: // setAirlockVolume
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setAirlockVolume(_arg0);
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
			case 8: // getTotalMoles
			{
				_out = handler.createReply();
				_out.write_float(getTotalMoles());
				break;
			}
			case 9: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 10: // setInitialVolumeAtSeaLevel
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setInitialVolumeAtSeaLevel(_arg0);
				break;
			}
			case 11: // getLeakRate
			{
				_out = handler.createReply();
				_out.write_float(getLeakRate());
				break;
			}
			case 12: // getTemperature
			{
				_out = handler.createReply();
				_out.write_float(getTemperature());
				break;
			}
			case 13: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 14: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 15: // setCurrentVolumeAtSeaLevel
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setCurrentVolumeAtSeaLevel(_arg0);
				break;
			}
			case 16: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 17: // getInitialTotalPressure
			{
				_out = handler.createReply();
				_out.write_float(getInitialTotalPressure());
				break;
			}
			case 18: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 19: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 20: // getVaporStore
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.EnvironmentVaporStoreHelper.write(_out,getVaporStore());
				break;
			}
			case 21: // getInitialVolume
			{
				_out = handler.createReply();
				_out.write_float(getInitialVolume());
				break;
			}
			case 22: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 23: // getCurrentVolume
			{
				_out = handler.createReply();
				_out.write_float(getCurrentVolume());
				break;
			}
			case 24: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 25: // getRelativeHumidity
			{
				_out = handler.createReply();
				_out.write_float(getRelativeHumidity());
				break;
			}
			case 26: // getModuleName
			{
				_out = handler.createReply();
				_out.write_string(getModuleName());
				break;
			}
			case 27: // getMaxLumens
			{
				_out = handler.createReply();
				_out.write_float(getMaxLumens());
				break;
			}
			case 28: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 29: // getAirlockVolume
			{
				_out = handler.createReply();
				_out.write_float(getAirlockVolume());
				break;
			}
			case 30: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 31: // setHourOfDayStart
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setHourOfDayStart(_arg0);
				break;
			}
			case 32: // setInitialVolume
			{
				float _arg0=_input.read_float();
				float _arg1=_input.read_float();
				float _arg2=_input.read_float();
				float _arg3=_input.read_float();
				float _arg4=_input.read_float();
				float _arg5=_input.read_float();
				_out = handler.createReply();
				setInitialVolume(_arg0,_arg1,_arg2,_arg3,_arg4,_arg5);
				break;
			}
			case 33: // getO2Store
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.EnvironmentO2StoreHelper.write(_out,getO2Store());
				break;
			}
			case 34: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 35: // getTotalPressure
			{
				_out = handler.createReply();
				_out.write_float(getTotalPressure());
				break;
			}
			case 36: // removeAirlockPercentage
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				removeAirlockPercentage(_arg0);
				break;
			}
			case 37: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 38: // getDayLength
			{
				_out = handler.createReply();
				_out.write_float(getDayLength());
				break;
			}
			case 39: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 40: // getDangerousOxygenThreshold
			{
				_out = handler.createReply();
				_out.write_float(getDangerousOxygenThreshold());
				break;
			}
			case 41: // getWaterDensity
			{
				_out = handler.createReply();
				_out.write_float(getWaterDensity());
				break;
			}
			case 42: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 43: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 44: // setMaxLumens
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setMaxLumens(_arg0);
				break;
			}
			case 45: // setDayLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setDayLength(_arg0);
				break;
			}
			case 46: // setDangerousOxygenThreshold
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setDangerousOxygenThreshold(_arg0);
				break;
			}
			case 47: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 48: // getHourOfDayStart
			{
				_out = handler.createReply();
				_out.write_float(getHourOfDayStart());
				break;
			}
			case 49: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 50: // getLightIntensity
			{
				_out = handler.createReply();
				_out.write_float(getLightIntensity());
				break;
			}
			case 51: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 52: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 53: // scheduleMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				int _arg2=_input.read_long();
				_out = handler.createReply();
				scheduleMalfunction(_arg0,_arg1,_arg2);
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
