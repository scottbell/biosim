package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "EffluentValve"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public abstract class EffluentValvePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.framework.EffluentValveOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "getIndexOfEffluentStore", new java.lang.Integer(0));
		m_opsHash.put ( "clearMalfunction", new java.lang.Integer(1));
		m_opsHash.put ( "getNitrogenProducerDefinition", new java.lang.Integer(2));
		m_opsHash.put ( "getTickLength", new java.lang.Integer(3));
		m_opsHash.put ( "fixAllMalfunctions", new java.lang.Integer(4));
		m_opsHash.put ( "setLogLevel", new java.lang.Integer(5));
		m_opsHash.put ( "getGreyWaterProducerDefinition", new java.lang.Integer(6));
		m_opsHash.put ( "startMalfunction", new java.lang.Integer(7));
		m_opsHash.put ( "getDirtyWaterConsumerDefinition", new java.lang.Integer(8));
		m_opsHash.put ( "notifyCommandSent", new java.lang.Integer(9));
		m_opsHash.put ( "getBiomassProducerDefinition", new java.lang.Integer(10));
		m_opsHash.put ( "getID", new java.lang.Integer(11));
		m_opsHash.put ( "getFoodProducerDefinition", new java.lang.Integer(12));
		m_opsHash.put ( "getDryWasteProducerDefinition", new java.lang.Integer(13));
		m_opsHash.put ( "getNitrogenConsumerDefinition", new java.lang.Integer(14));
		m_opsHash.put ( "fixMalfunction", new java.lang.Integer(15));
		m_opsHash.put ( "getGreyWaterConsumerDefinition", new java.lang.Integer(16));
		m_opsHash.put ( "getPotableWaterProducerDefinition", new java.lang.Integer(17));
		m_opsHash.put ( "getMyTicks", new java.lang.Integer(18));
		m_opsHash.put ( "getBiomassConsumerDefinition", new java.lang.Integer(19));
		m_opsHash.put ( "getMalfunctions", new java.lang.Integer(20));
		m_opsHash.put ( "getFoodConsumerDefinition", new java.lang.Integer(21));
		m_opsHash.put ( "getDryWasteConsumerDefinition", new java.lang.Integer(22));
		m_opsHash.put ( "reset", new java.lang.Integer(23));
		m_opsHash.put ( "registerBioCommandListener", new java.lang.Integer(24));
		m_opsHash.put ( "getModuleName", new java.lang.Integer(25));
		m_opsHash.put ( "randomFilter", new java.lang.Integer(26));
		m_opsHash.put ( "isMalfunctioning", new java.lang.Integer(27));
		m_opsHash.put ( "getPotableWaterConsumerDefinition", new java.lang.Integer(28));
		m_opsHash.put ( "log", new java.lang.Integer(29));
		m_opsHash.put ( "getAirProducerDefinition", new java.lang.Integer(30));
		m_opsHash.put ( "maintain", new java.lang.Integer(31));
		m_opsHash.put ( "getPowerProducerDefinition", new java.lang.Integer(32));
		m_opsHash.put ( "getO2ProducerDefinition", new java.lang.Integer(33));
		m_opsHash.put ( "getCO2ProducerDefinition", new java.lang.Integer(34));
		m_opsHash.put ( "doSomeRepairWork", new java.lang.Integer(35));
		m_opsHash.put ( "setIndexOfEffluentStore", new java.lang.Integer(36));
		m_opsHash.put ( "setEnableFailure", new java.lang.Integer(37));
		m_opsHash.put ( "getAirConsumerDefinition", new java.lang.Integer(38));
		m_opsHash.put ( "clearAllMalfunctions", new java.lang.Integer(39));
		m_opsHash.put ( "getPowerConsumerDefinition", new java.lang.Integer(40));
		m_opsHash.put ( "getO2ConsumerDefinition", new java.lang.Integer(41));
		m_opsHash.put ( "tick", new java.lang.Integer(42));
		m_opsHash.put ( "setTickLength", new java.lang.Integer(43));
		m_opsHash.put ( "getDirtyWaterProducerDefinition", new java.lang.Integer(44));
		m_opsHash.put ( "getCO2ConsumerDefinition", new java.lang.Integer(45));
		m_opsHash.put ( "isFailureEnabled", new java.lang.Integer(46));
		m_opsHash.put ( "getMalfunctionNames", new java.lang.Integer(47));
		m_opsHash.put ( "scheduleMalfunction", new java.lang.Integer(48));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/framework/EffluentValve:1.0","IDL:com/traclabs/biosim/idl/simulation/food/BiomassConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/WaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/DirtyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/NitrogenConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/O2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/BiomassProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/FoodProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/O2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/NitrogenProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/DirtyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/FoodConsumer:1.0"};
	public com.traclabs.biosim.idl.simulation.framework.EffluentValve _this()
	{
		return com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.framework.EffluentValve _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper.narrow(_this_object(orb));
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
			case 0: // getIndexOfEffluentStore
			{
				_out = handler.createReply();
				_out.write_long(getIndexOfEffluentStore());
				break;
			}
			case 1: // clearMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				clearMalfunction(_arg0);
				break;
			}
			case 2: // getNitrogenProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinitionHelper.write(_out,getNitrogenProducerDefinition());
				break;
			}
			case 3: // getTickLength
			{
				_out = handler.createReply();
				_out.write_float(getTickLength());
				break;
			}
			case 4: // fixAllMalfunctions
			{
				_out = handler.createReply();
				fixAllMalfunctions();
				break;
			}
			case 5: // setLogLevel
			{
				com.traclabs.biosim.idl.framework.LogLevel _arg0=com.traclabs.biosim.idl.framework.LogLevelHelper.read(_input);
				_out = handler.createReply();
				setLogLevel(_arg0);
				break;
			}
			case 6: // getGreyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinitionHelper.write(_out,getGreyWaterProducerDefinition());
				break;
			}
			case 7: // startMalfunction
			{
				com.traclabs.biosim.idl.framework.MalfunctionIntensity _arg0=com.traclabs.biosim.idl.framework.MalfunctionIntensityHelper.read(_input);
				com.traclabs.biosim.idl.framework.MalfunctionLength _arg1=com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.read(_input);
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionHelper.write(_out,startMalfunction(_arg0,_arg1));
				break;
			}
			case 8: // getDirtyWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinitionHelper.write(_out,getDirtyWaterConsumerDefinition());
				break;
			}
			case 9: // notifyCommandSent
			{
				java.lang.String _arg0=_input.read_string();
				_out = handler.createReply();
				notifyCommandSent(_arg0);
				break;
			}
			case 10: // getBiomassProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.BiomassProducerDefinitionHelper.write(_out,getBiomassProducerDefinition());
				break;
			}
			case 11: // getID
			{
				_out = handler.createReply();
				_out.write_long(getID());
				break;
			}
			case 12: // getFoodProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.FoodProducerDefinitionHelper.write(_out,getFoodProducerDefinition());
				break;
			}
			case 13: // getDryWasteProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinitionHelper.write(_out,getDryWasteProducerDefinition());
				break;
			}
			case 14: // getNitrogenConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinitionHelper.write(_out,getNitrogenConsumerDefinition());
				break;
			}
			case 15: // fixMalfunction
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				fixMalfunction(_arg0);
				break;
			}
			case 16: // getGreyWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinitionHelper.write(_out,getGreyWaterConsumerDefinition());
				break;
			}
			case 17: // getPotableWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinitionHelper.write(_out,getPotableWaterProducerDefinition());
				break;
			}
			case 18: // getMyTicks
			{
				_out = handler.createReply();
				_out.write_long(getMyTicks());
				break;
			}
			case 19: // getBiomassConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinitionHelper.write(_out,getBiomassConsumerDefinition());
				break;
			}
			case 20: // getMalfunctions
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.framework.MalfunctionListHelper.write(_out,getMalfunctions());
				break;
			}
			case 21: // getFoodConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinitionHelper.write(_out,getFoodConsumerDefinition());
				break;
			}
			case 22: // getDryWasteConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinitionHelper.write(_out,getDryWasteConsumerDefinition());
				break;
			}
			case 23: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 24: // registerBioCommandListener
			{
				com.traclabs.biosim.idl.simulation.framework.BioCommandListener _arg0=com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.read(_input);
				_out = handler.createReply();
				registerBioCommandListener(_arg0);
				break;
			}
			case 25: // getModuleName
			{
				_out = handler.createReply();
				_out.write_string(getModuleName());
				break;
			}
			case 26: // randomFilter
			{
				float _arg0=_input.read_float();
				_out = handler.createReply();
				_out.write_float(randomFilter(_arg0));
				break;
			}
			case 27: // isMalfunctioning
			{
				_out = handler.createReply();
				_out.write_boolean(isMalfunctioning());
				break;
			}
			case 28: // getPotableWaterConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinitionHelper.write(_out,getPotableWaterConsumerDefinition());
				break;
			}
			case 29: // log
			{
				_out = handler.createReply();
				log();
				break;
			}
			case 30: // getAirProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper.write(_out,getAirProducerDefinition());
				break;
			}
			case 31: // maintain
			{
				_out = handler.createReply();
				maintain();
				break;
			}
			case 32: // getPowerProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerProducerDefinitionHelper.write(_out,getPowerProducerDefinition());
				break;
			}
			case 33: // getO2ProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.O2ProducerDefinitionHelper.write(_out,getO2ProducerDefinition());
				break;
			}
			case 34: // getCO2ProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinitionHelper.write(_out,getCO2ProducerDefinition());
				break;
			}
			case 35: // doSomeRepairWork
			{
				long _arg0=_input.read_longlong();
				_out = handler.createReply();
				doSomeRepairWork(_arg0);
				break;
			}
			case 36: // setIndexOfEffluentStore
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setIndexOfEffluentStore(_arg0);
				break;
			}
			case 37: // setEnableFailure
			{
				boolean _arg0=_input.read_boolean();
				_out = handler.createReply();
				setEnableFailure(_arg0);
				break;
			}
			case 38: // getAirConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinitionHelper.write(_out,getAirConsumerDefinition());
				break;
			}
			case 39: // clearAllMalfunctions
			{
				_out = handler.createReply();
				clearAllMalfunctions();
				break;
			}
			case 40: // getPowerConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinitionHelper.write(_out,getPowerConsumerDefinition());
				break;
			}
			case 41: // getO2ConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinitionHelper.write(_out,getO2ConsumerDefinition());
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
			case 44: // getDirtyWaterProducerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinitionHelper.write(_out,getDirtyWaterProducerDefinition());
				break;
			}
			case 45: // getCO2ConsumerDefinition
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinitionHelper.write(_out,getCO2ConsumerDefinition());
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
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
