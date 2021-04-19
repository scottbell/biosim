package com.traclabs.biosim.idl.simulation.framework;


/**
 * Generated from IDL interface "EffluentValve".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class EffluentValvePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.framework.EffluentValveOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getIndexOfEffluentStore", Integer.valueOf(0));
		m_opsHash.put ( "clearMalfunction", Integer.valueOf(1));
		m_opsHash.put ( "getNitrogenProducerDefinition", Integer.valueOf(2));
		m_opsHash.put ( "getTickLength", Integer.valueOf(3));
		m_opsHash.put ( "fixAllMalfunctions", Integer.valueOf(4));
		m_opsHash.put ( "setLogLevel", Integer.valueOf(5));
		m_opsHash.put ( "getGreyWaterProducerDefinition", Integer.valueOf(6));
		m_opsHash.put ( "startMalfunction", Integer.valueOf(7));
		m_opsHash.put ( "getDirtyWaterConsumerDefinition", Integer.valueOf(8));
		m_opsHash.put ( "notifyCommandSent", Integer.valueOf(9));
		m_opsHash.put ( "getBiomassProducerDefinition", Integer.valueOf(10));
		m_opsHash.put ( "getID", Integer.valueOf(11));
		m_opsHash.put ( "getFoodProducerDefinition", Integer.valueOf(12));
		m_opsHash.put ( "getDryWasteProducerDefinition", Integer.valueOf(13));
		m_opsHash.put ( "getNitrogenConsumerDefinition", Integer.valueOf(14));
		m_opsHash.put ( "fixMalfunction", Integer.valueOf(15));
		m_opsHash.put ( "getGreyWaterConsumerDefinition", Integer.valueOf(16));
		m_opsHash.put ( "getPotableWaterProducerDefinition", Integer.valueOf(17));
		m_opsHash.put ( "getMyTicks", Integer.valueOf(18));
		m_opsHash.put ( "getBiomassConsumerDefinition", Integer.valueOf(19));
		m_opsHash.put ( "getMalfunctions", Integer.valueOf(20));
		m_opsHash.put ( "getFoodConsumerDefinition", Integer.valueOf(21));
		m_opsHash.put ( "getDryWasteConsumerDefinition", Integer.valueOf(22));
		m_opsHash.put ( "reset", Integer.valueOf(23));
		m_opsHash.put ( "registerBioCommandListener", Integer.valueOf(24));
		m_opsHash.put ( "getModuleName", Integer.valueOf(25));
		m_opsHash.put ( "randomFilter", Integer.valueOf(26));
		m_opsHash.put ( "isMalfunctioning", Integer.valueOf(27));
		m_opsHash.put ( "getPotableWaterConsumerDefinition", Integer.valueOf(28));
		m_opsHash.put ( "log", Integer.valueOf(29));
		m_opsHash.put ( "getAirProducerDefinition", Integer.valueOf(30));
		m_opsHash.put ( "maintain", Integer.valueOf(31));
		m_opsHash.put ( "getPowerProducerDefinition", Integer.valueOf(32));
		m_opsHash.put ( "getO2ProducerDefinition", Integer.valueOf(33));
		m_opsHash.put ( "getCO2ProducerDefinition", Integer.valueOf(34));
		m_opsHash.put ( "doSomeRepairWork", Integer.valueOf(35));
		m_opsHash.put ( "setIndexOfEffluentStore", Integer.valueOf(36));
		m_opsHash.put ( "setEnableFailure", Integer.valueOf(37));
		m_opsHash.put ( "getAirConsumerDefinition", Integer.valueOf(38));
		m_opsHash.put ( "clearAllMalfunctions", Integer.valueOf(39));
		m_opsHash.put ( "getPowerConsumerDefinition", Integer.valueOf(40));
		m_opsHash.put ( "getO2ConsumerDefinition", Integer.valueOf(41));
		m_opsHash.put ( "tick", Integer.valueOf(42));
		m_opsHash.put ( "setTickLength", Integer.valueOf(43));
		m_opsHash.put ( "getDirtyWaterProducerDefinition", Integer.valueOf(44));
		m_opsHash.put ( "getCO2ConsumerDefinition", Integer.valueOf(45));
		m_opsHash.put ( "isFailureEnabled", Integer.valueOf(46));
		m_opsHash.put ( "getMalfunctionNames", Integer.valueOf(47));
		m_opsHash.put ( "scheduleMalfunction", Integer.valueOf(48));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/framework/EffluentValve:1.0","IDL:com/traclabs/biosim/idl/simulation/food/BiomassConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SimBioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/water/WaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/DirtyWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/NitrogenConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/AirProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/O2Consumer:1.0","IDL:com/traclabs/biosim/idl/simulation/waste/DryWasteConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/BiomassProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/FoodProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/O2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/air/NitrogenProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterConsumer:1.0","IDL:com/traclabs/biosim/idl/simulation/power/PowerProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/DirtyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/GreyWaterProducer:1.0","IDL:com/traclabs/biosim/idl/framework/BioModule:1.0","IDL:com/traclabs/biosim/idl/simulation/air/CO2Producer:1.0","IDL:com/traclabs/biosim/idl/simulation/water/PotableWaterProducer:1.0","IDL:com/traclabs/biosim/idl/simulation/food/FoodConsumer:1.0"};
	public com.traclabs.biosim.idl.simulation.framework.EffluentValve _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.framework.EffluentValve __r = com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.framework.EffluentValve _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.framework.EffluentValve __r = com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper.narrow(__o);
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
				java.lang.String tmpResult22 = getModuleName();
_out.write_string( tmpResult22 );
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
