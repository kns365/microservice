package vn.com.kns.portalapi.application.helper;

import com.kns.apps.msa.commonpack.application.helper.LogHelper;
import com.kns.apps.msa.commonpack.core.model.kafka.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import vn.com.kns.portalapi.application.service.administration.auditLog.AuditLogService;
import vn.com.kns.portalapi.application.service.administration.auditLog.dto.AuditLogInputDto;
import vn.com.kns.portalapi.application.service.auth.dto.LoginRequest;
import vn.com.kns.portalapi.core.model.ResponseDto;
import vn.com.kns.portalapi.web.security.handler.HttpServletResponseCopier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Component
public class AuditLogHelper {

    private static AuditLogService auditLogService;
    private static LogHelper logHelper;

    public AuditLogHelper(AuditLogService auditLogService, LogHelper logHelper/*, LogProducer logProducer,Environment env*/) {
        this.auditLogService = auditLogService;
        this.logHelper = logHelper;
    }

    public static void create(HttpServletRequest request, HttpServletResponseCopier response, Date execDurStart, Exception exception) {
        try {
            AuditLogInputDto logClientInfo = getClientInfo(request, response, exception);
            AuditLogInputDto auditLogInput = new AuditLogInputDto(logClientInfo, execDurStart);
            log.debug("{}", auditLogInput);
            if (!Arrays.stream(new String[]{"auditLogs", "swagger", "api-doc", "ws", "testchat", "actuator"}).anyMatch(auditLogInput.getPath()::contains)) {
                if (logHelper.isConnected()) {
                    logHelper.push(LogEvent.builder()
                            .clientName(auditLogInput.getClientName())
                            .execDuration(auditLogInput.getExecDuration())
                            .clientIpAddress(auditLogInput.getClientIpAddress())
                            .path(auditLogInput.getPath())
                            .httpStatus(auditLogInput.getHttpStatus())
                            .exception(auditLogInput.getException())
                            .browserInfo(auditLogInput.getBrowserInfo())
                            .input(auditLogInput.getInput())
                            .output(auditLogInput.getOutput())
                            .build());
                } else {
                    auditLogService.createOrEdit(auditLogInput);
                }
            }
        } catch (Exception e) {
            log.error("Error create auditLog");
        }
    }

    public static AuditLogInputDto getClientInfo(HttpServletRequest request, HttpServletResponseCopier response, Exception exception) throws IOException {
        AuditLogInputDto output = null;
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

        String req = IOUtils.toString(request.getReader());
        String res = new String(response.getCopy(), response.getCharacterEncoding());

        try {
            ResponseDto resDto = JsonHelper.jsonToObjectT(res, new ResponseDto());
            if (resDto != null && resDto.getStatus() != null && resDto.getStatus() != HttpStatus.OK.value()) {
                httpStatus = resDto.getStatus().toString();
            }
        } catch (Exception ignore) {
        }

        if (path.contains("/auth/login")) {
            try {
                LoginRequest reqDto = JsonHelper.jsonToObjectT(req, new LoginRequest());
                reqDto.setPassword("MASKED");
                req = JsonHelper.objectToJson(reqDto);
            } catch (Exception ignore) {
            }
        }

        output = new AuditLogInputDto(clientIp, clientName, browserInfo, path, httpStatus, exception, req, res);
        return output;
    }


    private void getUserAgent(String userAgent) {
        //        String userAgent = "Unknown";
        String osType = "Unknown";
        String osVersion = "Unknown";
        String browserType = "Unknown";
        String browserVersion = "Unknown";
        String deviceType = "Unknown";

        try {
            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36 OPR/60.0.3255.165";
            //userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0";
            //userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36";
            //userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134";
            //userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Mobile/15E148 Safari/604.1";
            boolean exceptionTest = false;
            if (exceptionTest) throw new Exception("EXCEPTION TEST");

            if (userAgent.indexOf("Windows NT") >= 0) {
                osType = "Windows";
                osVersion = userAgent.substring(userAgent.indexOf("Windows NT ") + 11, userAgent.indexOf(";"));

            } else if (userAgent.indexOf("Mac OS") >= 0) {
                osType = "Mac";
                osVersion = userAgent.substring(userAgent.indexOf("Mac OS ") + 7, userAgent.indexOf(")"));

                if (userAgent.indexOf("iPhone") >= 0) {
                    deviceType = "iPhone";
                } else if (userAgent.indexOf("iPad") >= 0) {
                    deviceType = "iPad";
                }

            } else if (userAgent.indexOf("X11") >= 0) {
                osType = "Unix";
                osVersion = "Unknown";

            } else if (userAgent.indexOf("android") >= 0) {
                osType = "Android";
                osVersion = "Unknown";
            }
            log.info("end of os section");

            if (userAgent.contains("Edge/")) {
                browserType = "Edge";
                browserVersion = userAgent.substring(userAgent.indexOf("Edge")).split("/")[1];

            } else if (userAgent.contains("Safari/") && userAgent.contains("Version/")) {
                browserType = "Safari";
                browserVersion = userAgent.substring(userAgent.indexOf("Version/") + 8).split(" ")[0];

            } else if (userAgent.contains("OPR/") || userAgent.contains("Opera/")) {
                browserType = "Opera";
                browserVersion = userAgent.substring(userAgent.indexOf("OPR")).split("/")[1];

            } else if (userAgent.contains("Chrome/")) {
                browserType = "Chrome";
                browserVersion = userAgent.substring(userAgent.indexOf("Chrome")).split("/")[1];
                browserVersion = browserVersion.split(" ")[0];

            } else if (userAgent.contains("Firefox/")) {
                browserType = "Firefox";
                browserVersion = userAgent.substring(userAgent.indexOf("Firefox")).split("/")[1];
            }
            log.info("end of browser section");

        } catch (Exception ex) {
            log.error("ERROR: " + ex);
        }

        log.info(
                "\n userAgent: " + userAgent
                        + "\n osType: " + osType
                        + "\n osVersion: " + osVersion
                        + "\n browserType: " + browserType
                        + "\n browserVersion: " + browserVersion
                        + "\n deviceType: " + deviceType
        );
    }

}
