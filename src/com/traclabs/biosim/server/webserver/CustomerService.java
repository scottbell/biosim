package com.traclabs.biosim.server.webserver;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.function.Function;
import java.util.function.Predicate;
//import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerService {
	public CustomerService(int var){bar = var;}
	public CustomerService(){bar = 16;}
	public int bar;
	
  private final CopyOnWriteArrayList<Customer> cList = CustomerList.getInstance();

  @GET
  @Path("/ticks")
  @Produces(MediaType.TEXT_PLAIN)
  public static String getBiosimInfo() {
	  int ticks = BiosimWebInterface.getHolder().theBioDriver.getTicks();
	  return ticks + "";
  }

  @GET
  @Path("/all")
  @Produces(MediaType.TEXT_PLAIN)
  public String getAllCustomers() {
  String foo = "---Customer List---\n";
  return foo;
	  
    /*    return "---Customer List---\n"
    + cList.stream()
    .map(new Function<Customer, Object>() {
		@Override
		public Object apply(Customer c) {
			return c.toString();
		}
	})
    .collect(Collectors.joining("\n"));
*/
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.TEXT_PLAIN)
  public String getCustomer(@PathParam("id") final long id) {
    Optional<Customer> match
        = cList.stream()
        .filter(new Predicate<Customer>() {
			@Override
			public boolean test(Customer c) {
				return c.getId() == id;
			}
		})
        .findFirst();
    
    if (match.isPresent()) {
      return "---Customer---\n" + match.get().toString();
    } else {
      return "Customer not found";
    }
  }
}
