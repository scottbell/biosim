INCLUDES = -I. -I$(TAO_ROOT) -I$(ACE_ROOT) -I$(TAO_ROOT)/orbsvcs/orbsvcs

biosim_client: biosimC.cpp biosim_client.cpp
	       g++ -o biosim_client biosimC.cpp biosim_client.cpp	       $(INCLUDES)	       -L/net/home/korten/ACEOrb/ACE_wrappers/TAO/tao -L/net/home/korten/ACEOrb/ACE_wrappers/TAO/orbsvcs/orbsvcs	       -L/net/home/korten/ACEOrb/ACE_wrappers/TAO/tao/PortableServer	       -lTAO -lTAO_PortableServer -lTAO_CosNaming

biosim_server: biosimS.cpp biosimC.cpp biosim.cpp biosim_server.cpp
	       g++ -o biosim_server biosimS.cpp biosimC.cpp biosim.cpp biosim_server.cpp	       $(INCLUDES)	       -L/net/home/korten/ACEOrb/ACE_wrappers/TAO/tao -L/net/home/korten/ACEOrb/ACE_wrappers/TAO/orbsvrc/orbsvcs	       -L/net/home/korten/ACEOrb/ACE_wrappers/TAO/tao/PortableServer	       -lTAO -lTAO_PortableServer -lTAO_CosNaming

#------------------
# Dependencies
# -----------------

# DO NOT DELETE THIS LINE -- make depend depends on it.
