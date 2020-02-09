package mypack.bus.handler;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

public class LaunchRequestHandler implements RequestHandler {

	
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Welcome to BATS. How can I help you?";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Bing Alexa Transit System", speechText)
                .withReprompt(speechText)
                .build();
    }
	
}
