package com.kns.apps.microservice.zuulserver.handler;

import com.google.common.base.CharMatcher;
import com.kns.apps.microservice.configserver.application.helper.JsonHelper;
import com.kns.apps.microservice.configserver.core.model.ResponseDto;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitExceededException;
import com.netflix.client.ClientException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Slf4j
@ControllerAdvice
public class CustomZuulErrorFilter extends ZuulFilter {

    private static final String FILTER_TYPE = "error";
    private static final int FILTER_ORDER = -1;
    private static final String THROWABLE_KEY = "throwable";

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;// Needs to run before SendErrorFilter which has filterOrder == 0
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            Throwable throwable = context.getThrowable();

            if (throwable instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) throwable;
                Integer errorCode = context.getResponseStatusCode();
                String errorMsg = zuulException.getCause().getCause().getLocalizedMessage();//429 TOO_MANY_REQUESTS
//                log.error("Zuul failure detected: " + errorMsg, zuulException);
                log.error("Zuul failure detected: " + errorMsg + "-" + context.getResponseStatusCode());

                /*if (throwable.getCause().getCause().getCause() instanceof ConnectException) {
                    context.remove("throwable");
                    context.setResponseBody("RESPONSE_BODY");
                    context.getResponse()
                            .setContentType(MediaType.APPLICATION_JSON_VALUE);
                    context.setResponseStatusCode(503);
                }*/
                /*if (throwable.getCause().getCause().getCause() instanceof ConnectException) {
                    ZuulException customException = new ZuulException("", 503, "Service Unavailable");
                    context.setThrowable(customException);
                }*/
                /*if (throwable.getCause().getCause().getCause() instanceof RateLimitExceededException) {
                    ZuulException customException = new ZuulException("", TOO_MANY_REQUESTS.value(), TOO_MANY_REQUESTS.getReasonPhrase());
                    context.setThrowable(customException);
                }*/

                context.remove(THROWABLE_KEY);
                context.setResponseStatusCode(200);
                context.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
                context.setResponseBody(JsonHelper.objectToJson(new ResponseDto(errorCode, errorMsg, null, null)));
            }
        } catch (Exception ex) {
            log.error("Exception filtering in CustomZuulErrorFilter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

}
