package mypack;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

import mypack.bus.handler.BusIntentHandler;
import mypack.bus.handler.CancelandStopIntentHandler;
import mypack.bus.handler.HelpIntentHandler;
import mypack.bus.handler.LaunchRequestHandler;
import mypack.bus.handler.SessionEndedRequestHandler;


public class BusStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new BusIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler())
                .withSkillId("amzn1.ask.skill.b1cc2a48-ac60-4543-a718-f1c9b71158b0")
                .build();
    }

    public BusStreamHandler() {
        super(getSkill());
    }

}