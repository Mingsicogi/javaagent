package mins.study;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * controller intercept handler
 *
 * @author minbbangdi
 */
public class ControllerInterceptor {

    private static String LOG_FORMAT = "### Execute [uri] %s, [milliseconds] %s, [method] %s, [argument] %s ###";

    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] arguments, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            HttpServletRequest currentRequest = getCurrentRequest();
            if (currentRequest != null) {
                System.out.println(String.format(LOG_FORMAT, currentRequest.getRequestURI(), System.currentTimeMillis() - start, method.toString(), arguments));
            }
        }
    }

    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
