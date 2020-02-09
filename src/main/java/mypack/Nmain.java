package mypack;

import java.io.IOException;

import org.json.JSONException;

import mypack.bus.updater.BATSProviderImpl;

public class Nmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


    	BATSProviderImpl bus = new BATSProviderImpl();
		String retval = "nothing";
    	try {
			retval = bus.refreshVehicles("Campus");// This is to test
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
