package com.traclabs.biosim.idl.simulation.food;


/**
 *	Generated from IDL interface "Plant"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _PlantStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements com.traclabs.biosim.idl.simulation.food.Plant
{
	private String[] ids = {"IDL:com/traclabs/biosim/idl/simulation/food/Plant:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = com.traclabs.biosim.idl.simulation.food.PlantOperations.class;
	public float getMolesOfCO2Inhaled()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "getMolesOfCO2Inhaled", true);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "getMolesOfCO2Inhaled", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			PlantOperations _localServant = (PlantOperations)_so.servant;
			float _result;			try
			{
			_result = _localServant.getMolesOfCO2Inhaled();
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
