package pro.yqy.component.web.common;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import pro.yqy.component.web.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buf;

    public boolean isUpload() {
        return getContentType() != null && getContentType().startsWith("multipart");
    }

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 上传文件情况，不读取流
        if (isUpload()) {
            return;
        }

        try (ServletInputStream is = request.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            this.buf = os.toByteArray();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isUpload()) {
            return super.getInputStream();
        }
        return new ExtendInputStream(this.buf);
    }

    @Override
    public String toString() {
        if (isUpload()) {
            return "(File Content)";
        }
        String contentType = StringUtils.toString(super.getContentType()).toLowerCase();

        if (contentType.contains("json")) {
            // TODO 过滤敏感数据显示
            return new String(this.buf, StandardCharsets.UTF_8);
        }

        Enumeration<String> keys = getParameterNames();
        StringBuilder builder = new StringBuilder();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            builder.append(key);
            builder.append("=");
            String value = super.getParameter(key);
            builder.append(value);
            builder.append("&");
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
