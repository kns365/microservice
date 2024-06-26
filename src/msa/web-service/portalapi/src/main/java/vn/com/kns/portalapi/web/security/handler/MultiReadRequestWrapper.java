package vn.com.kns.portalapi.web.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BoundedInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

@Slf4j
public class MultiReadRequestWrapper extends HttpServletRequestWrapper {
    // We include a max byte size to protect against malicious requests,
    //since this all has to be read into memory
    public static final Integer MAX_BYTE_SIZE = 1_048_576; // 1 MB
    private StringBuilder body;

    public MultiReadRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = new StringBuilder("");
        try (InputStream bounded = new BoundedInputStream(request.getInputStream(), MAX_BYTE_SIZE);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bounded));) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                body.append(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.toString().getBytes());
        return new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                //do nothing
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
