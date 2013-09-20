#include "biosim.h"

int main (int argc, char* argv[])
{
  try {
    // First initialize the ORB, that will remove some arguments...
    CORBA::ORB_var orb =
      CORBA::ORB_init (argc, argv, "TestServer");

    CORBA::Object_var poa_object =
      orb->resolve_initial_references ("RootPOA");
    PortableServer::POA_var poa =
      PortableServer::POA::_narrow (poa_object.in ());
    PortableServer::POAManager_var poa_manager =
      poa->the_POAManager ();
    poa_manager->activate ();

    // Create the servant
    Biosim_Biodriver biosim_biodriver(orb);

    // Activate it to obtain the object reference
    biosim::idl::framework::BioDriver_var biodriver =
      biosim_biodriver._this ();

    // Put the object reference as an IOR string
    CORBA::String_var ior = orb->object_to_string (biodriver.in ());
    // Print it out!
    cout << ior.in () << endl;

    orb->run ();

    // The application code goes here!

    // Destroy the POA, waiting until the destruction terminates
    poa->destroy (1, 1);
    orb->destroy ();
  }
  catch (CORBA::Exception &ex) {
    std::cerr << "CORBA exception raised!" << std::endl;
  }
  return 0;
}


