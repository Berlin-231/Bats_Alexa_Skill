package mypack.bus.handler;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;



public class CancelandStopIntentHandler implements RequestHandler {

	
	    public boolean canHandle(HandlerInput input) {
	        return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
	    }

	    
	    public Optional<Response> handle(HandlerInput input) {
	        return input.getResponseBuilder()
	                .withSpeech("Goodbye")
	                .withSimpleCard("HelloWorld", "Goodbye")
	                .withShouldEndSession(true)
	                .build();
	    }

}
