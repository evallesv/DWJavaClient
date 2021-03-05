

import java.io.IOException;
import java.net.MalformedURLException;

import solutions.vpf.docuware.VanillaDwClient;
import solutions.vpf.docuware.common.Organization;
import solutions.vpf.docuware.common.Organizations;

/**
 * @author evall
 *
 */
public class HelloWorld {
	
    public static void main(String... args) {
    	try {
    		Configurations config = new Configurations();
			VanillaDwClient client = new VanillaDwClient(config.Get("ApiBase"));
			
			if(client.Login(config.Get("User"), config.Get("Password"))) {
				
				Organizations orgs = client.GetOrganizations();
				if(orgs != null) {
					for (Organization organization : orgs.getOrganization()) {
						System.out.println("Default Org: " + organization.getName());
					}	
				}
				
			}else {
				System.out.println("Error!");
			}
			client.Logoff();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
