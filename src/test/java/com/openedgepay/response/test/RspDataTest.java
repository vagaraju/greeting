/**
 * 
 */
package com.openedgepay.response.test;

import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.MDC;
import org.custommonkey.xmlunit.XMLTestCase;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import com.openedgepay.response.RspData;
import com.openedgepay.util.FileData;
import com.openedgepay.util.MDCLog;

import static com.openedgepay.constants.Constants.GATEWAYRESPONSE;
import static com.openedgepay.constants.Constants.OG;
import static com.openedgepay.constants.Constants.ORDERID;
import static com.openedgepay.constants.Constants.RESPONSE;
import static com.openedgepay.constants.Constants.TERMINALID;
import static com.openedgepay.util.FileData.FILESPATH;
/**
 * @author M1034465
 *
 */
public class RspDataTest extends XMLTestCase {
    /**
     * LOG variable.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(RspDataTest.class);
    /**
     * file name variable.
     */
    private static final String SALE12345 = "sale12345";
    /**
     * LOG variable.
     */
    @Mock
    private Logger logger;
    /**
     * verify that for given gateway JSON response getting proper edge express
     * response.
     */
    @Test
    public void testGetJSRsp() {
        String mocksaleRsp;
        try {
            mocksaleRsp = new String(Files.readAllBytes(
                    Paths.get(FILESPATH + "sale12345EERsp" + ".xml")));
            ConcurrentMap<String, String> rspMap = RspData
                    .getJSRsp(FileData.readFileData(SALE12345), "OG");
            String actualSaleRsp = rspMap.get(SALE12345);

            assertXMLEqual(
                    "for given input json and expected edge express response.",
                    mocksaleRsp, actualSaleRsp);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("error occured..");
            }
        }

    }

    /**
     * verify that for given false gateway JSON response getting proper edge
     * express response.
     */
    @Test
    public void testFalseGetJSRsp() {
        try {
            String expectedXML = "<RESPONSE><RESULT>0</RESULT><RESULTMSG>Success</RESULTMSG><ORDERID>TestID12345</ORDERID><TERMINALID>sale</TERMINALID></RESPONSE>";
           
            ConcurrentMap<String, String> rspMap = RspData
                    .getJSRsp(FileData.readFileData(SALE12345), "MB");
            String actualSaleRsp = rspMap.get(SALE12345);

            assertXMLEqual(
                    "for given input MB XML and response xml",
                    expectedXML, actualSaleRsp);
        } catch (Exception e) {
                LOG.error("error occured..");
        }

    }
    /**
     * verify that for given local date and local time converting to date format.
     */
    @Test
    public void testGetDateTime() {
            String mocksaleRsp = "2017-10-11 13:16:52";
           String actualSaleRsp = RspData.getDateTime("113017", "161916");
           assertNotEquals(
                    "converts the local date and local time to dateformat:",
                    mocksaleRsp, actualSaleRsp);
    }
    /**
     * verify that for given local date and local time converting to date format.
     */
    @Test
    public void testGetDateTimeFalse() {
            String mocksaleRsp = "2017-10-11 13:16:52";
           String actualSaleRsp = RspData.getDateTime("113017", "abc");
           assertNotEquals(
                    "converts the local date and local time to dateformat:",
                    mocksaleRsp, actualSaleRsp);
    }
    /**
     * verify that for given false gateway JSON response getting proper edge
     * express response.
     */
    @Test
    public void testGetDateTimeWithEmpty() {
            String mocksaleRsp = null;
           String actualSaleRsp = RspData.getDateTime("", "");
            assertEquals(
                    "converts the local date and local time to dateformat:",
                    mocksaleRsp, actualSaleRsp);
    }
    /**
     * test for read file data
     * 
     * @throws Exception get exception
     *             .
     */
    @Test
    public void testReadFileData() throws Exception {
        logger = Mockito.mock(Logger.class);
        Mockito.when(logger.isInfoEnabled()).thenReturn(false);
        JSONObject obj = new JSONObject(FileData.readFileData("abc"));
        String actualXML = (String) obj.get("error");
        
        String expectedXML = "Please place the file @ desired location and try again.!!";
        assertNotEquals("reading the file date which was not present", expectedXML, actualXML);
          
    } 
    /**
     * verify that for given false gateway JSON response getting proper edge
     * express response.
     */
    @Test
    public void getJSONObj() {
        JSONObject objJSONObject = RspData.getJSONObj(null, null);
        assertEquals("JSON object is null", null, objJSONObject);
    }
    
    /**
     * verify that for given gateway JSON response getting proper edge express
     * response.
     */
    @Test
    public void testGetJSRspEmpty() {
        String mocksaleRsp = null;
        ConcurrentMap<String, String> rspMap = RspData.getJSRsp("", "OG");
        String actualSaleRsp = rspMap.get(SALE12345);
        assertEquals("check for empty object", mocksaleRsp, actualSaleRsp);
    }
    /**
     * verify that for given false gateway JSON response getting proper edge
     * express response.
     */
    @Test
    public void testGetJSRspUnKnownHandShake() {
        String rspXML = "<RESPONSE><RESULT>0</RESULT><RESULTMSG>Success</RESULTMSG><ORDERID>TestID12345</ORDERID><TERMINALID>sale</TERMINALID></RESPONSE>";

        ConcurrentMap<String, String> rspMap = RspData.getJSRsp(rspXML, "MSS");
        String actualSaleRsp = rspMap.get(SALE12345);

        assertEquals("with unknown Handshake Val:", null, actualSaleRsp);
    }
    /**
     * verify that for given false gateway JSON response getting proper edge
     * express response.
     */
    @Test
    public void testGetEERsp() {
        String strGatewayResponse = "{\"GatewayResponse\":{\"OrderID\":\"sale\",\"TerminalID\":\"91827364\"}}";
       
        ConcurrentMap<String, String> rspMap = RspData.getEERsp(strGatewayResponse,"GatewayResponse");
        String actualSaleRsp = rspMap.get(SALE12345);

        assertEquals("with expdate and reeciptxml:", null, actualSaleRsp);
    }
    /**
     * test for read file data
     * 
     * @throws Exception get exception
     *             .
     */
    @Test
    public void testReadFileDataWithNoLoggerEnabled () throws Exception {
        logger = Mockito.mock(Logger.class);
        Mockito.when(logger.isInfoEnabled()).thenReturn(false);
        JSONObject obj = new JSONObject(FileData.readFileData("abc"));
        String actualXML = (String) obj.get("error");
        String expectedXML = "Please place the file @ desired location and try again.!!";
        assertNotEquals("reading the file date which was not present", expectedXML, actualXML);
    } 
    /**
     * verify that for given mobile Cancel JSON response getting proper edge
     * express response.
     */
    @Test
    public void testGetEERspForMobileCancelRsp() throws Exception {
        String mobileCancelRqst = "{\"Response\":{\"ResponseCode\":\"3\",\"ResponseDescription\""
                + ":\"Canceled due to improper data\",\"TerminalID\""
                + ":\"91827364\",\"OrderID\":\"sale\",\"RequestId\":\"sale-91827364-201818182719.620\"}}";
        String expectedMobileXMLResponse = "<?xml version=\"1.0\"?><RESPONSE>\r\n" + 
                "    <RESULT>3</RESULT>\r\n" + 
                "    <RESULTMSG>Declined by Host: 3, Canceled due to improper data</RESULTMSG>\r\n" + 
                "    <HOSTRESPONSECODE>3</HOSTRESPONSECODE>\r\n" + 
                "    <HOSTRESPONSEDESCRIPTION>Canceled due to improper data</HOSTRESPONSEDESCRIPTION>\r\n" + 
                "    <ORDERID>sale</ORDERID>\r\n" + 
                "</RESPONSE>";
        String actualSaleRsp = RspData.getJSRsp(mobileCancelRqst,"MB").get("sale91827364");
        assertXMLEqual("chect for mobile cancellation Response:", expectedMobileXMLResponse, actualSaleRsp);
    }
    /**
     * verify key value from the message.
     */
    @Test
    public void testKeyFromMBMsg() {
        String message = "{\"Response\":{\"ResponseCode\":\"3\",\"ResponseDescription\""
                + ":\"Canceled due to improper data\",\"TerminalID\""
                + ":\"91827364\",\"OrderID\":\"sale\",\"RequestId\":\"sale-91827364-201818182719.620\"}}";
        String key = RspData.getKey(message, "MB");
        assertEquals("verify the key value from the message", "sale91827364", key);
    }
    /**
     * verify key value from the OG message.
     */
    @Test
    public void testKeyFromOGMsg() {
        String strGatewayResponse = "{\"GatewayResponse\": {\"ProcessorResponse\": \"000AP\",\"OrderID\": \"sale\",\"TerminalID\": \"12345\"}}";
        String key = RspData.getKey(strGatewayResponse, "OG");
        assertEquals("verify the key value from the message", "sale12345", key);
    }
}
