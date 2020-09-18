package utils.attachment;

import io.qameta.allure.attachment.http.HttpRequestAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UrlencHttpRequestAttachment extends HttpRequestAttachment {

    private final Map<String, String> urlencs;

    public UrlencHttpRequestAttachment(String name, String url, String method, String body, String curl, Map<String, String> headers, Map<String, String> cookies, Map<String, String> dataUrlenc) {
        super(name, url, method, body, curl, headers, cookies);
        this.urlencs = dataUrlenc;
    }

    public Map<String, String> getUrlencs() {
        return urlencs;
    }

    public static final class Builder {

        private final String name;

        private final String url;

        private String method;

        private String body;

        private final Map<String, String> headers = new HashMap<>();

        private final Map<String, String> cookies = new HashMap<>();

        private final Map<String, String> urlencs = new HashMap<>();

        private Builder(final String name, final String url) {
            Objects.requireNonNull(name, "Name must not be null value");
            Objects.requireNonNull(url, "Url must not be null value");
            this.name = name;
            this.url = url;
        }

        public static Builder create(final String attachmentName, final String url) {
            return new Builder(attachmentName, url);
        }

        public Builder setMethod(final String method) {
            Objects.requireNonNull(method, "Method must not be null value");
            this.method = method;
            return this;
        }

        public Builder setHeader(final String name, final String value) {
            Objects.requireNonNull(name, "Header name must not be null value");
            Objects.requireNonNull(value, "Header value must not be null value");
            this.headers.put(name, value);
            return this;
        }

        public Builder setHeaders(final Map<String, String> headers) {
            Objects.requireNonNull(headers, "Headers must not be null value");
            this.headers.putAll(headers);
            return this;
        }

        public Builder setCookie(final String name, final String value) {
            Objects.requireNonNull(name, "Cookie name must not be null value");
            Objects.requireNonNull(value, "Cookie value must not be null value");
            this.cookies.put(name, value);
            return this;
        }

        public Builder setCookies(final Map<String, String> cookies) {
            Objects.requireNonNull(cookies, "Cookies must not be null value");
            this.cookies.putAll(cookies);
            return this;
        }

        public Builder setBody(final String body) {
            Objects.requireNonNull(body, "Body should not be null value");
            this.body = body;
            return this;
        }

        public Builder setUrlenc(final String name, final String value) {
            Objects.requireNonNull(name, "Urlencoded data name must not be null value");
            Objects.requireNonNull(value, "Urlencoded data value must not be null value");
            this.urlencs.put(name, value);
            return this;
        }

        public Builder setUrlencs(final Map<String, String> dataUrlenc) {
            Objects.requireNonNull(dataUrlenc, "Urlencoded data must not be null value");
            this.urlencs.putAll(dataUrlenc);
            return this;
        }

        public UrlencHttpRequestAttachment build() {
            return new UrlencHttpRequestAttachment(name, url, method, body, getCurl(), headers, cookies, urlencs);
        }

        private String getCurl() {
            final StringBuilder builder = new StringBuilder("curl -v");
            if (Objects.nonNull(method)) {
                builder.append(" -X ").append(method);
            }
            builder.append(" '").append(url).append('\'');
            headers.forEach((key, value) -> appendHeader(builder, key, value));
            cookies.forEach((key, value) -> appendCookie(builder, key, value));
            urlencs.forEach((key, value) -> appendUrlenc(builder, key, value));

            if (Objects.nonNull(body)) {
                builder.append(" -d '").append(body).append('\'');
            }
            return builder.toString();
        }

        private static void appendHeader(final StringBuilder builder, final String key, final String value) {
            builder.append(" -H '")
                    .append(key)
                    .append(": ")
                    .append(value)
                    .append('\'');
        }

        private static void appendCookie(final StringBuilder builder, final String key, final String value) {
            builder.append(" -b '")
                    .append(key)
                    .append('=')
                    .append(value)
                    .append('\'');
        }

        private static void appendUrlenc(final StringBuilder builder, final String key, final String value) {
            builder.append(" --data-urlencode '")
                    .append(key)
                    .append('=')
                    .append(value)
                    .append('\'');
        }
    }
}
