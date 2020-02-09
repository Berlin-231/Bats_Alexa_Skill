package mypack.bus.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

public class HelpIntentHandler implements RequestHandler {

    
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "You can ask about Binghamton University busses to me";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Bing Alexa Transit System", speechText)
                .withReprompt(speechText)
                .build();
    }
}