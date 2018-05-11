package com.openedgepay.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.MDC;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author M1034465
 *
 */
public class MDCLogTest {
    @Mock
    private WebSocketSession session;
    
    /**
     * set the necessary data to run test methods.
     */
    @Before
    public void executedBeforeEach() {
        session = Mockito.mock(WebSocketSession.class);
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("reqId", "sale-123456789");
        Mockito.when(session.getAttributes()).thenReturn(attributes);
    }
    /**
     * verify the availability of particular value in MDC .
     */
    @Test
    public void testRemoveMDC()  {
        MDC.put("type", "websocket");
        new MDCLog().removeMDC();
        assertEquals(null, MDC.get("type"));
    }
    /**
     * verify execution time difference .
     */
    @Test
    public void testExecTimeinMilliSecs() {
        String startTime = ""+System.currentTimeMillis();
        String timeDiff = MDCLog.getExecTimeinMilliSecs(startTime);
        assertNotEquals(123, timeDiff);
    }
    /**
     * verify execution time difference for null value.
     */
    @Test
    public void testExecTimeinMilliSecswithNullVal() {
        String timeDiff = MDCLog.getExecTimeinMilliSecs(null);
        assertEquals("", timeDiff);
    }
    /**
     * verify the values in MDC .
     */
    @Test
    public void testSetMDC() {
        new MDCLog().setMDC(session);
        assertEquals("sale-123456789", MDC.get("reqId"));
        new MDCLog().removeMDC();
    }
    /**
     * convert the given message into ELK understandable format .
     */
    @Test
    public void getJsonLogMsg() {
        String incomingJSReq = "{\"OrderID\":\"sale\",\"TerminalID\":\"91827364\",\"RequestStatus\":\"open\"}";
        String incomingJSMDCReq = "{\"incomingReq\":{\"OrderID\":\"sale\",\"TerminalID\":\"91827364\",\"RequestStatus\":\"open\"}}";
        String strIncomRqst = MDCLog.getJsonLogMsg("incomingReq", incomingJSReq, true);
        assertEquals(incomingJSMDCReq, strIncomRqst);
    }
    /**
     * convert the given message into ELK understandable format .
     */
    @Test
    public void getJsonLogMsgForRspSys() {
        String messageFromRspSys = "{\"Response\" : { \"TerminalID\" : \"90039608\", \"ResponseDescription\" : \"Transaction Canceled\", \"OrderID\" : \"sale111\",\"RequestID\" : \"sale111-90039608-20180314110149.945\", \"ResponseCode\" : \"3\" }}";
        String actualMDCRsp = "{\"outgoingResp\":\"{\\\"Response\\\" : { \\\"TerminalID\\\" : \\\"90039608\\\", \\\"ResponseDescription\\\" : \\\"Transaction Canceled\\\", \\\"OrderID\\\" : \\\"sale111\\\",\\\"RequestID\\\" : \\\"sale111-90039608-20180314110149.945\\\", \\\"ResponseCode\\\" : \\\"3\\\" }}\"}";
        String expectedMDCRsp = MDCLog.getJsonLogMsg("outgoingResp", messageFromRspSys, false);
        assertEquals(actualMDCRsp, expectedMDCRsp);
    }
}
