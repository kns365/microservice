package vn.com.kns.portalapi.application.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vn.com.kns.portalapi.application.helper.util.VNCharacterUtils;
import vn.com.kns.portalapi.core.model.FilterInput;
import vn.com.kns.portalapi.core.model.dataTables.Column;
import vn.com.kns.portalapi.core.model.dataTables.DataTablesInput;
import vn.com.kns.portalapi.core.model.dataTables.Order;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

@Component
@Slf4j
public class CommonHelper {

    private static String cardMaskConst = "######******####";

    private static Environment env;

    public CommonHelper(Environment env) {
        this.env = env;
    }

    public static String getProfileActive() {
        return String.join(",", env.getActiveProfiles());
    }

    public static String getLoggingLevel() {
        String output = env.getProperty("logging.level.root") != null ? env.getProperty("logging.level.root") : "";
        return output;
    }

    public static int getTimeout() {
        int output = env.getProperty("ESB.COMMON.TIMEOUT") != null ? Integer.parseInt(env.getProperty("ESB.COMMON.TIMEOUT")) : 10;
        return output;
    }

    public static String removeAccentUnicode(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String removeAccent(String s) {
        return VNCharacterUtils.removeAccent(s);
    }

    public static String fixedLengthString(String string, int length) {
        return String.format("%1$" + length + "s", string).replace(" ", "0");
    }

    public static String getRandomRequestId() {
//        String uuid = UUID.randomUUID().toString();//return 36 chars phía VCB sẽ bị lỗi
        String uuid = DateHelper.getSystemDateMilis();
        return uuid;
    }

    public static int randomNumLog() {
        int randomWithThreadLocalRandomInARange = ThreadLocalRandom.current().nextInt(1, 999999);
        return randomWithThreadLocalRandomInARange;
    }

    public static String vendorUsername() throws SocketException, UnknownHostException {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            return ip;
        }
    }

}
