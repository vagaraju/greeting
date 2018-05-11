/**
 * 
 */
package com.openedgepay.util;

import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Test;

/**
 * @author M1034465
 *
 */
public class FieldsMappingTest extends XMLTestCase {
    /**
     * mocked edge express response .
     */
    public static final String XMLEDGEEXPRSP = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
            + "<RESPONSE>\r\n" + "    <RESULT>0</RESULT>\r\n"
            + "    <RESULTMSG>Success</RESULTMSG>\r\n"
            + "    <APPROVEDAMOUNT>236.65</APPROVEDAMOUNT>\r\n"
            + "    <APPROVALCODE>008716</APPROVALCODE>\r\n"
            + "    <BATCHNO>000007</BATCHNO>\r\n"
            + "    <CARDBRAND>MasterCard</CARDBRAND>\r\n"
            + "    <CARDTYPE>Credit</CARDTYPE>\r\n"
            + "    <EXPMONTH>12</EXPMONTH>\r\n"
            + "    <EXPYEAR>18</EXPYEAR>\r\n"
            + "    <DATE_TIME>10-11-17 12:16:55</DATE_TIME>\r\n"
            + "    <MASKEDCARDNUMBER>545454XXXXXX5454</MASKEDCARDNUMBER>\r\n"
            + "    <HOSTRESPONSECODE>000</HOSTRESPONSECODE>\r\n"
            + "    <HOSTRESPONSEDESCRIPTION>Approval</HOSTRESPONSEDESCRIPTION>\r\n"
            + "    <TRANSACTIONID>000000000993</TRANSACTIONID>\r\n"
            + "    <ORDERID>TestID12345</ORDERID>\r\n"
            + "    <TerminalID>90002329</TerminalID>\r\n"
            + "    <AVSRESPONSECODE>Y</AVSRESPONSECODE>\r\n"
            + "    <CARDCODERESPONSE>M</CARDCODERESPONSE>\r\n"
            + "</RESPONSE>";
    
    /**
     * verify the particular value not presented from the given xml .
     */
    @Test
    public void testGetResultMsg()  {
        final String actualValue = new FieldsMapping().getResultMsg("0", "Approved");
        assertEquals("result message for success scenario:","Success", actualValue);
    }
    /**
     * verify the particular value not presented from the given xml .
     */
    @Test
    public void testGetResultMsgFailure()  {
        final String actualValue = new FieldsMapping().getResultMsg("12", "Declined");
        assertEquals("result message for declined scenario:","Declined by Host: 12, Declined", actualValue);
    }
    /**
     * verify the particular value not presented from the given xml .
     */
    @Test
    public void testGetResult()  {
        final String actualValue = new FieldsMapping().getResult("000");
        assertEquals("edgeexpress result code for given gateway response code:","0", actualValue);
    }
    /**
     * verify the handshake value for the given request URI.
     */
}
