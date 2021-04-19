package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "AirRS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class AirRSPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.air.AirRSOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(0));
		m_opsHash.put ( "getTickLength", Integer.valueOf(1));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(2));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(3));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(4));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(5));
		m_opsHash.put ( "getH2ProducerDefinition", Integer.valueOf(6));
		m_opsHash.put ( "getID", Integer.valueOf(7));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(8));
		m_opsHash.put ( "getPotableWaterProducerDefinition", Integer.valueOf(9));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(10));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(11));
		m_opsHash.put ( "getH2ConsumerDefinition", Integer.valueOf(12));
		m_opsHash.put ( "reset", Integer.valueOf(13));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(14));
		m_opsHash.put ( "getModuleName", Integer.valueOf(15));
		m_opsHash.put ( "randomFilter", Integer.valueOf(16));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(17));
		m_opsHash.put ( "getPotableWaterConsumerDefinition", Integer.valueOf(18));
		m_opsHash.put ( "log", Integer.valueOf(19));
		m_opsHash.put ( "getAirProducerDefinition", Integer.valueOf(20));
		m_opsHash.put ( "maintain", Integer.valueOf(21));
		m_opsHash.put ( "getO2ProducerDefinition", Integer.valueOf(22));
		m_opsHash.put ( "getCO2ProducerDefinition", Integer.valueOf(23));
		m_opsHash.put ( "getMethaneProducerDefinition", Integer.valueOf(24));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(25));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(26));
		m_opsHash.put ( "getAirConsumerDefinition", Integer.valueOf(27));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(28));
		m_opsHash.put ( "getPowerConsumerDefinition", Integer.valueOf(29));
		m_opsHash.put ( "tick", Integer.valueOf(30));
		m_opsHash.put ( "setTickLength", Integer.valueOf(31));
		m_opsHash.put ( "getCO2ConsumerDefinition", Integer.valueOf(32));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(33));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(34));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(35));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/air/AirRS:1.0","IDL:com/traclabs/biosim/idl/simulation/air/H2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/O2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/MethaneProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/H2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentConsumer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirProducer:1.0"};
	public com.traclabs.biosim.idl.simulation.air.AirRS _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.air.AirRS __r = com.traclabs.biosim.idl.simulation.air.AirRSHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.air.AirRS _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.air.AirRS __r = com.traclabs.biosim.idl.simulation.air.AirRSHelper.narrow(__o);
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
			case 0: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 1: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 2: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 3: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 4: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 5: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 6: // getH2ProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.H2ProducerDefinitionHelper.write(_out,getH2ProducerDefinition());
				break;
			}
			case 7: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 8: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 9: // getPotableWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinitionHelper.write(_out,getPotableWaterProducerDefinition());
				break;
			}
			case 10: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 11: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 12: // getH2ConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinitionHelper.write(_out,getH2ConsumerDefinition());
				break;
			}
			case 13: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 14: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 15: // getModuleName
			{
				_out = handler.createReply();
				java.lang.String tmpResult89 = getModuleName();
_out.write_string( tmpResult89 );
				break;
			}
			case 16: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 17: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 18: // getPotableWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinitionHelper.write(_out,getPotableWaterConsumerDefinition());
				break;
			}
			case 19: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 20: // getAirProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper.write(_out,getAirProducerDefinition());
				break;
			}
			case 21: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 22: // getO2ProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.O2ProducerDefinitionHelper.write(_out,getO2ProducerDefinition());
				break;
			}
			case 23: // getCO2ProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinitionHelper.write(_out,getCO2ProducerDefinition());
				break;
			}
			case 24: // getMethaneProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinitionHelper.write(_out,getMethaneProducerDefinition());
				break;
			}
			case 25: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 26: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 27: // getAirConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionHelper.write(_out,getAirConsumerDefinition());
				break;
			}
			case 28: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 29: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
				break;
			}
			case 30: // tick
			{
				_out = handler.createReply();
				tick();
				break;
			}
			case 31: // setTickLength
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				setTickLength(_arg0);
				break;
			}
			case 32: // getCO2ConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinitionHelper.write(_out,getCO2ConsumerDefinition());
				break;
			}
			case 33: // isFailureEnabled
			{
				_out = handler.createReply();
				_out.write_boolean(isFailureEnabled());
				break;
			}
			case 34: // getMalfunctionNames
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.StringListHelper.write(_out,getMalfunctionNames());
				break;
			}
			case 35: // scheduleMalfunction
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
