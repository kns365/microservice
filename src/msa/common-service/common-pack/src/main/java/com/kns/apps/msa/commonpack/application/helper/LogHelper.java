package com.kns.apps.msa.commonpack.application.helper;

import com.kns.apps.msa.commonpack.application.service.auditLog.LogProducer;
import com.kns.apps.msa.commonpack.core.model.kafka.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
public class LogHelper {

    private static LogProducer logProducer;
    private static String serviceName = "";
    private static Environment env;
    private static String[] ignorePaths = new String[]{"auditLogs", "swagger", "api-doc", "ws", "testchat", "actuator"};

    public boolean isConnected() {
        return logProducer.isConnected();
    }

    public LogHelper(LogProducer logProducer, Environment env) {
        this.logProducer = logProducer;
        this.env = env;
        this.serviceName = env.getProperty("spring.application.name");
    }

    public static void push(LogEvent logEvent) {
        try {
            log.debug("{}", logEvent);
            if (!validPaths(logEvent.getPath(), ignorePaths)) {
                logEvent.setServiceName(serviceName);
                logProducer.sendMessage(logEvent);
            }
        } catch (Exception e) {
            log.error("Error push log");
        }
    }

    public static void push(HttpServletRequest request, HttpServletResponse response, Object input, Object output, Date execDurStart, String exception) {
        try {
            LogEvent logEvent = getClientInfo(request, response, JsonHelper.objectToJson(input), JsonHelper.objectToJson(output), exception, execDurStart);
            log.debug("{}", logEvent);
            if (!validPaths(logEvent.getPath(), ignorePaths)) {
                logProducer.sendMessage(logEvent);
            }
        } catch (Exception e) {
            log.error("Error push log");
        }
    }

    private static Boolean validPaths(String path, String[] ignorePaths) {
        return Arrays.stream(ignorePaths).anyMatch(path::contains);
    }

    public static LogEvent getClientInfo(HttpServletRequest request, HttpServletResponse response, String input, String output, String exception, Date execDurStart) throws IOException {
        String clientIp = "";
        String clientName = "";
        String browserInfo = "";
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            clientIp = socket.getLocalAddress().getHostAddress();
            clientName = socket.getLocalAddress().getHostName();
        }
        String userAgent = request.getHeader("User-Agent");
        browserInfo = userAgent;
        String path = request.getMethod() + ":" + request.getServletPath();
        String httpStatus = String.valueOf(((HttpServletResponse) response).getStatus());
        return new LogEvent(serviceName, clientIp, clientName, browserInfo, path, httpStatus, exception, input, output, execDurStart);
    }

}
