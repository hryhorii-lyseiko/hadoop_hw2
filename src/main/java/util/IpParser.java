package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpParser implements Cloneable {



    private static class ParserUtils {

        public static int safeParse(String byteSym) {
            try {
                return Integer.parseInt(byteSym);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }



    private static final int IP_GROUP = 1;
    private static final int BYTE_GROUP = 2;
    private static final int USER_AGENT_GROUP = 3;


    private static String LOG_LINE_REGEX = "^(ip[\\d]+) \\S+ \\S+ \\[[^\\]]*]* \"[^\"]*\" \\d\\d\\d ([^\\s]*) \"[^\"]*\" \"([^\"]*)\"$";

    private Pattern LOG_LINE_REGEX_PATTERN = Pattern.compile(LOG_LINE_REGEX);

    private Matcher lineMatcher = null;

    private boolean isLineMatched;

    public void  matchLine(String apacheLogLine) {
        lineMatcher = LOG_LINE_REGEX_PATTERN.matcher(apacheLogLine);
        isLineMatched = lineMatcher.find();
    }

    public String getIp() {
        return lineMatcher.group(IP_GROUP);
    }

    public String getBytesSymbols() {
        return lineMatcher.group(BYTE_GROUP);
    }

    public int getBytes() {
        return ParserUtils.safeParse(lineMatcher.group(BYTE_GROUP));
    }

    public String getUserAgent() {
        return lineMatcher.group(USER_AGENT_GROUP);
    }

    public boolean isValidLine() {
        return isLineMatched;
    }


}
