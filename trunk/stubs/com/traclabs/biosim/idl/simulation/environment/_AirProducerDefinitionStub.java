package com.traclabs.biosim.idl.simulation.environment;


/**
 *	Generated from IDL interface "AirProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _AirProducerDefinitionStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition
{
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/environment/AirProducerDefinition:1.0","IDL:com/traclabs/biosim/idl/simulation/environment/EnvironmentFlowRateControllable:1.0","IDL:com/traclabs/biosim/idl/simulation/framework/SingleFlowRateControllable:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = com.traclabs.biosim.idl.simulation.environment.AirProducerDefinitionOperations.class;
	public float getPercentageFull(int index)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getPercentageFull", true);
				_os.write_long(index);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getPercentageFull", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getPercentageFull(index);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float[] getActualFlowRates()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getActualFlowRates", true);
				_is = _invoke(_os);
				float[] _result = com.traclabs.biosim.idl.FloatListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getActualFlowRates", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float[] _result;			try
			{
			_result = _localServant.getActualFlowRates();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float[] getMaxFlowRates()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getMaxFlowRates", true);
				_is = _invoke(_os);
				float[] _result = com.traclabs.biosim.idl.FloatListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getMaxFlowRates", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float[] _result;			try
			{
			_result = _localServant.getMaxFlowRates();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void reset()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "reset", true);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "reset", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.reset();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void setInitialDesiredFlowRates(float[] flowrates)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setInitialDesiredFlowRates", true);
				com.traclabs.biosim.idl.FloatListHelper.write(_os,flowrates);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setInitialDesiredFlowRates", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.setInitialDesiredFlowRates(flowrates);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void setAirOutputs(com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setAirOutputs", true);
				com.traclabs.biosim.idl.simulation.environment.SimEnvironmentListHelper.write(_os,destinations);
				com.traclabs.biosim.idl.FloatListHelper.write(_os,maxFlowRates);
				com.traclabs.biosim.idl.FloatListHelper.write(_os,desiredFlowRates);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setAirOutputs", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.setAirOutputs(destinations,maxFlowRates,desiredFlowRates);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void setDesiredFlowRate(float value, int index)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setDesiredFlowRate", true);
				_os.write_float(value);
				_os.write_long(index);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setDesiredFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.setDesiredFlowRate(value,index);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public float getTotalActualFlowRate()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getTotalActualFlowRate", true);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getTotalActualFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getTotalActualFlowRate();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void setInitialActualFlowRates(float[] flowrates)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setInitialActualFlowRates", true);
				com.traclabs.biosim.idl.FloatListHelper.write(_os,flowrates);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setInitialActualFlowRates", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.setInitialActualFlowRates(flowrates);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public float getActualFlowRate(int index)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getActualFlowRate", true);
				_os.write_long(index);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getActualFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getActualFlowRate(index);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void setInitialMaxFlowRates(float[] flowrates)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setInitialMaxFlowRates", true);
				com.traclabs.biosim.idl.FloatListHelper.write(_os,flowrates);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setInitialMaxFlowRates", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.setInitialMaxFlowRates(flowrates);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public float getMaxFlowRate(int index)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getMaxFlowRate", true);
				_os.write_long(index);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getMaxFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getMaxFlowRate(index);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float getAveragePercentageFull()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getAveragePercentageFull", true);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getAveragePercentageFull", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getAveragePercentageFull();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public boolean connectsTo(com.traclabs.biosim.idl.simulation.environment.SimEnvironment pSimEnvironment)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "connectsTo", true);
				com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.write(_os,pSimEnvironment);
				_is = _invoke(_os);
				boolean _result = _is.read_boolean();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "connectsTo", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			boolean _result;			try
			{
			_result = _localServant.connectsTo(pSimEnvironment);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float getTotalMaxFlowRate()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getTotalMaxFlowRate", true);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getTotalMaxFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getTotalMaxFlowRate();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float[] getDesiredFlowRates()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getDesiredFlowRates", true);
				_is = _invoke(_os);
				float[] _result = com.traclabs.biosim.idl.FloatListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getDesiredFlowRates", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float[] _result;			try
			{
			_result = _localServant.getDesiredFlowRates();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public void setMaxFlowRate(float value, int index)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setMaxFlowRate", true);
				_os.write_float(value);
				_os.write_long(index);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setMaxFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			try
			{
			_localServant.setMaxFlowRate(value,index);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] getEnvironments()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getEnvironments", true);
				_is = _invoke(_os);
				com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] _result = com.traclabs.biosim.idl.simulation.environment.SimEnvironmentListHelper.read(_is);
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getEnvironments", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] _result;			try
			{
			_result = _localServant.getEnvironments();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float getDesiredFlowRate(int index)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getDesiredFlowRate", true);
				_os.write_long(index);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getDesiredFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getDesiredFlowRate(index);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public float getTotalDesiredFlowRate()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getTotalDesiredFlowRate", true);
				_is = _invoke(_os);
				float _result = _is.read_float();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getTotalDesiredFlowRate", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			AirProducerDefinitionOperations _localServant = (AirProducerDefinitionOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getTotalDesiredFlowRate();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

}
