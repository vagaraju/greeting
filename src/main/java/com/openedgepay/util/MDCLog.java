package com.openedgepay.util;

import org.apache.log4j.MDC;
import org.json.JSONObject;
import org.springframework.web.socket.WebSocketSession;
/**
 * to track the log statements.
 * @author M1034465
 *
 */
public class MDCLog {
    /**
     * remove values from MDC.
     */
    public final void removeMDC() {

        MDC.remove("session_id");
        MDC.remove("reqId");
        MDC.remove("type");
    }
    /**
     * get Execution Time in Millisecs .
     * @param strTime .
     * @return String .
     */
    public static String getExecTimeinMilliSecs(final String strTime) {
        if (null != strTime && !"".equals(strTime) && !"null".equals(strTime)) {
            long timeinMillisec = System.currentTimeMillis()
                    - Long.parseLong(strTime);
            return timeinMillisec + "";
        } else {
            return "";
        }
    }

    /**
     * set the values from MDC.
     * @param session .
     */
    public final void setMDC(final WebSocketSession session) {
        if (null != session) {
            MDC.put("session_id", session.getId());
            MDC.put("reqId", session.getAttributes().get("reqId"));
        }
        MDC.put("type", "WebSocket");
    }
    /**
     * convert the given message into ELK understandable format .
     * @param type .
     * @param msg .
     * @param isJsonFormat .
     * @return String .
     */
    public static String getJsonLogMsg(final String type, final String msg,
            final boolean isJsonFormat) {
        if (isJsonFormat) {
            return "{\"" + type + "\"" + ":" + msg + "}";
        } else {
            JSONObject jsonLogMsg = new JSONObject();
            jsonLogMsg.put(type, msg);
            return jsonLogMsg.toString().replaceAll("\r\n" + "\\n", " ");
        }
    }
}
