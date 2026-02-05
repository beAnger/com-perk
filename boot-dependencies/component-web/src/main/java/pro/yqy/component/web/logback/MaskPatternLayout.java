package pro.yqy.component.web.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskPatternLayout extends PatternLayout {

    private final Pattern markPattern;

    public MaskPatternLayout() {
        Set<String> keywords = new HashSet<>();
        keywords.add("password");
        keywords.add("passport");

        markPattern = Pattern.compile(
                "([\"']?(" + String.join("|", keywords) + ")[\"']? *[=:]) ?([\"']?).*?([\"']?)([, ])"
        );
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String logLine = super.doLayout(event);
        Matcher matcher = markPattern.matcher(logLine);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "$1$3****$4$5");
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
