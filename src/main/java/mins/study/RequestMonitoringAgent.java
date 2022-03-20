package mins.study;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;

/**
 * Http Request Monitoring Agent Definition
 *
 * @author mingbbangdi
 */
public class RequestMonitoringAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(isAnnotatedWith(Controller.class)).or(isAnnotatedWith(RestController.class))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.method(ElementMatchers.any()).intercept(MethodDelegation.to(ControllerInterceptor.class)))
                .installOn(instrumentation);

    }

}
