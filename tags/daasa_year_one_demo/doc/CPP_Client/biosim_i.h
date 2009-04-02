#ifndef BIOSIM_I_H
#define BIOSIM_I_H

#include "biosimS.h"
#include <string>

class Biosim_Biodriver : public virtual POA_biosim::idl::framework::BioDriver {
public:
  Biosim_Biodriver (CORBA::ORB_ptr orb);

  virtual bool isPaused() throw (CORBA::SystemException);
  virtual bool isStarted() throw (CORBA::SystemException);
  virtual void spawnSimulation() throw (CORBA::SystemException);
  virtual void spawnSimulationTillDead() throw (CORBA::SystemException);
  virtual void spawnSimulationTillN(long nTicks) throw (CORBA::SystemException);
  virtual void pauseSimulation() throw (CORBA::SystemException);
  virtual void endSimulation() throw (CORBA::SystemException);
  virtual bool simulationHasStarted() throw (CORBA::SystemException);
  virtual void advanceOneTick() throw (CORBA::SystemException);
  virtual void resumeSimulation() throw (CORBA::SystemException);
  virtual void setLogging(bool pLogSim) throw (CORBA::SystemException);
  virtual bool isLogging() throw (CORBA::SystemException);
  virtual void setDriverPauseLength(long pDriverPauseLength) throw (CORBA::SystemException);
  virtual long getDriverPauseLength() throw (CORBA::SystemException);
};

#endif /* BIOSIM_I_H */


