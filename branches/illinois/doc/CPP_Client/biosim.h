#ifndef BIOSIM_H
#define BIOSIM_H

#include "biosimS.h"
#include <string>

class Biosim_Biodriver 
    : public virtual POA_biosim::idl::framework::BioDriver 
    , public virtual PortableServer::RefCountServantBase
{
public:
  Biosim_Biodriver (CORBA::ORB_ptr orb);

  virtual CORBA::Boolean isPaused() throw (CORBA::SystemException);
  virtual CORBA::Boolean isStarted() throw (CORBA::SystemException);
  virtual void spawnSimulation() throw (CORBA::SystemException);
  virtual void spawnSimulationTillDead() throw (CORBA::SystemException);
  virtual void spawnSimulationTillN(CORBA::Long nTicks) throw (CORBA::SystemException);
  virtual void pauseSimulation() throw (CORBA::SystemException);
  virtual void endSimulation() throw (CORBA::SystemException);
  virtual CORBA::Boolean simulationHasStarted() throw (CORBA::SystemException);
  virtual void advanceOneTick() throw (CORBA::SystemException);
  virtual void resumeSimulation() throw (CORBA::SystemException);
  virtual void setLogging(CORBA::Boolean pLogSim) throw (CORBA::SystemException);
  virtual CORBA::Boolean isLogging() throw (CORBA::SystemException);
  virtual void setDriverPauseLength(CORBA::Long pDriverPauseLength) throw (CORBA::SystemException);
  virtual CORBA::Long getDriverPauseLength() throw (CORBA::SystemException);

 private:
  // Use an ORB reference to conver strings to objects and shutdown
  // the application.
  CORBA::ORB_var orb_;
};

#endif /* BIOSIM_H */


