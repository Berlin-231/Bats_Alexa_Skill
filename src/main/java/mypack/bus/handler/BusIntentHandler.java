package mypack.bus.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.request.RequestHelper;

import mypack.bus.updater.BATSProviderImpl;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;

public class BusIntentHandler implements IntentRequestHandler {

	
	    
	    public boolean canHandle(HandlerInput input) {
	        return input.matches(Predicates.intentName("BusAssistantIntent"));
	    }

	    
	    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
	    	
	    	RequestHelper requestHelper = RequestHelper.forHandlerInput(input);
	        
	        Optional<String> bus = requestHelper.getSlotValue("BusType");
	        String busType="de";
	        
	        if(bus.isPresent()) {
	        	busType = bus.get();
	        }
	        		//(String) bus.map(bus)
	                //.orElse("WS");
	        
	    	BATSProviderImpl bats = new BATSProviderImpl();
	    	String retval = "Wow, such empty.";
	    	try {
				retval = bats.refreshVehicles(busType);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	      // String speechText = retval;
	        return input.getResponseBuilder()
	                .withSpeech(retval)
	                .withSimpleCard("BATS", retval)
	                .build();
	    }


		public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
			// TODO Auto-generated method stub
			return false;
		}
	
	
}
