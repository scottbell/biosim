package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "AirProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class AirProducerDefinitionPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getPercentageFull", Integer.valueOf(0));
		m_opsHash.put ( "getActualFlowRates", Integer.valueOf(1));
		m_opsHash.put ( "getMaxFlowRates", Integer.valueOf(2));
		m_opsHash.put ( "reset", Integer.valueOf(3));
		m_opsHash.put ( "setInitialDesiredFlowRates", Integer.valueOf(4));
		m_opsHash.put ( "setAirOutputs", Integer.valueOf(5));
		m_opsHash.put ( "setDesiredFlowRate", Integer.valueOf(6));
		m_opsHash.put ( "getTotalActualFlowRate", Integer.valueOf(7));
		m_opsHash.put ( "setInitialActualFlowRates", Integer.valueOf(8));
		m_opsHash.put ( "getActualFlowRate", Integer.valueOf(9));
		m_opsHash.put ( "setInitialMaxFlowRates", Integer.valueOf(10));
		m_opsHash.put ( "getMaxFlowRate", Integer.valueOf(11));
		m_opsHash.put ( "getAveragePercentageFull", Integer.valueOf(12));
		m_opsHash.put ( "connectsTo", Integer.valueOf(13));
		m_opsHash.put ( "getTotalMaxFlowRate", Integer.valueOf(14));
		m_opsHash.put ( "getDesiredFlowRates", Integer.valueOf(15));
		m_opsHash.put ( "setMaxFlowRate", Integer.valueOf(16));
		m_opsHash.put ( "getEnvironments", Integer.valueOf(17));
		m_opsHash.put ( "getDesiredFlowRate", Integer.valueOf(18));
		m_opsHash.put ( "getTotalDesiredFlowRate", Integer.valueOf(19));
	}
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/environment/AirProducerDefinition:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentFlowRateControllable:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SingleFlowRateControllable:1.0"};
	public com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition __r = com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition __r = com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionHelper.narrow(__o);
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
			case 0: // getPercentageFull
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				_out.write_float(getPercentageFull(_arg0));
				break;
			}
			case 1: // getActualFlowRates
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.FloatListHelper.write(_out,getActualFlowRates());
				break;
			}
			case 2: // getMaxFlowRates
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.FloatListHelper.write(_out,getMaxFlowRates());
				break;
			}
			case 3: // reset
			{
				_out = handler.createReply();
				reset();
				break;
			}
			case 4: // setInitialDesiredFlowRates
			{
				float[] _arg0=com.traclabs.biosim.idl.FloatListHelper.read(_input);
				_out = handler.createReply();
				setInitialDesiredFlowRates(_arg0);
				break;
			}
			case 5: // setAirOutputs
			{
				com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] _arg0=com.traclabs.biosim.idl.simulation.environment.SimEnvironmentListHelper.read(_input);
				float[] _arg1=com.traclabs.biosim.idl.FloatListHelper.read(_input);
				float[] _arg2=com.traclabs.biosim.idl.FloatListHelper.read(_input);
				_out = handler.createReply();
				setAirOutputs(_arg0,_arg1,_arg2);
				break;
			}
			case 6: // setDesiredFlowRate
			{
				float _arg0=_input.read_float();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				setDesiredFlowRate(_arg0,_arg1);
				break;
			}
			case 7: // getTotalActualFlowRate
			{
				_out = handler.createReply();
				_out.write_float(getTotalActualFlowRate());
				break;
			}
			case 8: // setInitialActualFlowRates
			{
				float[] _arg0=com.traclabs.biosim.idl.FloatListHelper.read(_input);
				_out = handler.createReply();
				setInitialActualFlowRates(_arg0);
				break;
			}
			case 9: // getActualFlowRate
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				_out.write_float(getActualFlowRate(_arg0));
				break;
			}
			case 10: // setInitialMaxFlowRates
			{
				float[] _arg0=com.traclabs.biosim.idl.FloatListHelper.read(_input);
				_out = handler.createReply();
				setInitialMaxFlowRates(_arg0);
				break;
			}
			case 11: // getMaxFlowRate
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				_out.write_float(getMaxFlowRate(_arg0));
				break;
			}
			case 12: // getAveragePercentageFull
			{
				_out = handler.createReply();
				_out.write_float(getAveragePercentageFull());
				break;
			}
			case 13: // connectsTo
			{
				com.traclabs.biosim.idl.simulation.environment.SimEnvironment _arg0=com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.read(_input);
				_out = handler.createReply();
				_out.write_boolean(connectsTo(_arg0));
				break;
			}
			case 14: // getTotalMaxFlowRate
			{
				_out = handler.createReply();
				_out.write_float(getTotalMaxFlowRate());
				break;
			}
			case 15: // getDesiredFlowRates
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.FloatListHelper.write(_out,getDesiredFlowRates());
				break;
			}
			case 16: // setMaxFlowRate
			{
				float _arg0=_input.read_float();
				int _arg1=_input.read_long();
				_out = handler.createReply();
				setMaxFlowRate(_arg0,_arg1);
				break;
			}
			case 17: // getEnvironments
			{
				_out = handler.createReply();
				com.traclabs.biosim.idl.simulation.environment.SimEnvironmentListHelper.write(_out,getEnvironments());
				break;
			}
			case 18: // getDesiredFlowRate
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				_out.write_float(getDesiredFlowRate(_arg0));
				break;
			}
			case 19: // getTotalDesiredFlowRate
			{
				_out = handler.createReply();
				_out.write_float(getTotalDesiredFlowRate());
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
