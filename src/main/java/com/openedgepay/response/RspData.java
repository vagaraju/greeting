package com.openedgepay.response;

import static com.openedgepay.constants.Constants.ALIAS;
import static com.openedgepay.constants.Constants.AMOUNT;
import static com.openedgepay.constants.Constants.APPROVALCODE;
import static com.openedgepay.constants.Constants.AVSRESPONSECODE;
import static com.openedgepay.constants.Constants.BATCHAMOUNT;
import static com.openedgepay.constants.Constants.BATCHNUM;
import static com.openedgepay.constants.Constants.CARDBRAND;
import static com.openedgepay.constants.Constants.CARDBRANDSHORT;
import static com.openedgepay.constants.Constants.CARDCODERESPONSE;
import static com.openedgepay.constants.Constants.CARDHOLDERNAME;
import static com.openedgepay.constants.Constants.CARDTYPE;
import static com.openedgepay.constants.Constants.CONTENT;
import static com.openedgepay.constants.Constants.EMPTY_SPACE;
import static com.openedgepay.constants.Constants.EMVDEDICATEDFILENAME;
import static com.openedgepay.constants.Constants.ENTRYMETHOD;
import static com.openedgepay.constants.Constants.EXPDATE;
import static com.openedgepay.constants.Constants.GATEWAYRESPONSE;
import static com.openedgepay.constants.Constants.JSONFIELDS;
import static com.openedgepay.constants.Constants.LABEL;
import static com.openedgepay.constants.Constants.LOCALDATE;
import static com.openedgepay.constants.Constants.LOCALTIME;
import static com.openedgepay.constants.Constants.MASKEDACCTNUM;
import static com.openedgepay.constants.Constants.MB;
import static com.openedgepay.constants.Constants.NOLABEL;
import static com.openedgepay.constants.Constants.OG;
import static com.openedgepay.constants.Constants.ORDERID;
import static com.openedgepay.constants.Constants.RECEIPT;
import static com.openedgepay.constants.Constants.RECEIPTTEXT;
import static com.openedgepay.constants.Constants.RECEIPTXML;
import static com.openedgepay.constants.Constants.RECEIPTXML_END_TAG;
import static com.openedgepay.constants.Constants.RECEIPTXML_STRT_TAG;
import static com.openedgepay.constants.Constants.RESPONSE;
import static com.openedgepay.constants.Constants.RESPONSECODE;
import static com.openedgepay.constants.Constants.RESPONSEDESCRIPTION;
import static com.openedgepay.constants.Constants.RETURNID;
import static com.openedgepay.constants.Constants.TERMINALID;
import static com.openedgepay.constants.Constants.TRANSACTIONID;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openedgepay.util.FieldsMapping;
import com.openedgepay.util.MDCLog;

/**
 *
 * @author M1034465
 *
 */
public final class RspData {
  /**
   * LOG variable.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RspData.class);
  /**
   * no argument private constructor.
   */
  private RspData() {
      //not called
   }
   /**
   * get string xml value for the given JSONObject.
   *
   * @param receiptXMLContent
   *          .
   * @return StringBuilder .
   */
  public static StringBuilder getXMLForJSONObj(final JSONObject receiptXMLContent) {
    final StringBuilder xmlElements = new StringBuilder(RECEIPTXML_STRT_TAG);
    JSONFIELDS.forEach((key, value) -> {
      xmlElements.append(getXMLElement(key, value, receiptXMLContent));
    });
    xmlElements.append(RECEIPTXML_END_TAG);
    return xmlElements;
  }

  /**
   * prepare the xml element for the given json object.
   *
   * @param elementName
   *          .
   * @param labelValue
   *          .
   * @param jsonReceipt
   *          .
   * @return String .
   */
  private static String getXMLElement(final String elementName, final String labelValue, final JSONObject jsonReceipt) {
    String elementVal = null;
    String labelNanmeNVal = "";

    if (NOLABEL.equals(labelValue)) {
      elementVal = getValue(jsonReceipt, elementName);
    } else {
      final JSONObject oJSONObject = getJSONObj(jsonReceipt, elementName);
      final String labelAttrVal = getValue(oJSONObject, LABEL);
      if (null != labelAttrVal) {
        labelNanmeNVal = EMPTY_SPACE + LABEL + "='" + labelAttrVal + "'";
      }
      elementVal = getValue(oJSONObject, CONTENT);
    }
    String xmlElement = "";
    if (null != elementVal) {
      xmlElement = "<" + elementName + labelNanmeNVal + ">" + elementVal + "</" + elementName + ">";
    }
    return xmlElement;
  }

  /**
   * get the json object from the given key name.
   *
   * @param jsonReceipt
   *          .
   * @param keyName
   *          .
   * @return JSONObject .
   */
  public static JSONObject getJSONObj(final JSONObject jsonReceipt, final String keyName) {
    JSONObject obj = null;
    try {
      if (null != jsonReceipt) {
        obj = jsonReceipt.getJSONObject(keyName);
      }
    } catch (JSONException e) {
        LOG.error("ignore this error.");
    }
    return obj;
  }

  /**
   * get the required key value from the given JSON object.
   *
   * @param jsonReceipt
   *          .
   * @param keyName
   *          .
   * @return String.
   */
  public static String getValue(final JSONObject jsonReceipt, final String keyName) {
    String val = null;
    try {
      if (null != jsonReceipt) {
        val = (String) jsonReceipt.get(keyName);
      }
    } catch (JSONException e) {
        // ignore this error.
    }
    return val;
  }


  /**
   * convert EdgeGateWayRsp To EdgeExpRsp .
   *
   * @param rsp response from MB/OG components.
   * @param strResponseHeader response header name.
   * @return ConcurrentMap .
   */
  public static ConcurrentMap<String, String> getEERsp(final String rsp, final String strResponseHeader) {
    final ConcurrentMap<String, String> rspMap = new ConcurrentHashMap<String, String>();
    final Response jsRsp = new Response();
    final FieldsMapping mapping = new FieldsMapping();
    final JSONObject gRsp = new JSONObject(rsp).getJSONObject(strResponseHeader);
    jsRsp.setRESULT(mapping.getResult(getValue(gRsp, RESPONSECODE)));
    jsRsp.setRESULTMSG(mapping.getResultMsg(jsRsp.getRESULT(), getValue(gRsp, RESPONSEDESCRIPTION)));
    jsRsp.setHOSTRESPONSECODE(getValue(gRsp, RESPONSECODE));
    jsRsp.setHOSTRESPONSEDESCRIPTION(getValue(gRsp, RESPONSEDESCRIPTION));
    jsRsp.setTRANSACTIONID(getValue(gRsp, TRANSACTIONID));
    jsRsp.setAPPROVEDAMOUNT(getValue(gRsp, AMOUNT));
    jsRsp.setCARDTYPE(getValue(gRsp, CARDTYPE));
    jsRsp.setCARDBRAND(getValue(gRsp, CARDBRAND));
    jsRsp.setCARDBRANDSHORT(getValue(gRsp, CARDBRANDSHORT));
    jsRsp.setMASKEDCARDNUMBER(getValue(gRsp, MASKEDACCTNUM));
    jsRsp.setCARDHOLDERNAME(getValue(gRsp, CARDHOLDERNAME));
    jsRsp.setALIAS(getValue(gRsp, ALIAS));
    jsRsp.setBATCHNO(getValue(gRsp, BATCHNUM));
    jsRsp.setBATCHAMOUNT(getValue(gRsp, BATCHAMOUNT));
    jsRsp.setAPPROVALCODE(getValue(gRsp, APPROVALCODE));
    jsRsp.setAVSRESPONSECODE(getValue(gRsp, AVSRESPONSECODE));
    jsRsp.setCARDCODERESPONSE(getValue(gRsp, CARDCODERESPONSE));
    jsRsp.setRECEIPTTEXT(getValue(gRsp, RECEIPT));
    jsRsp.setORDERID(getValue(gRsp, ORDERID));
    jsRsp.setRETURNID(getValue(gRsp, RETURNID));
    jsRsp.setDEDICATEDFILENAME(getValue(gRsp, EMVDEDICATEDFILENAME));
    jsRsp.setENTRYTYPE(getValue(gRsp, ENTRYMETHOD));
    jsRsp.setDATETIME(getDateTime(getValue(gRsp, LOCALDATE), getValue(gRsp, LOCALTIME)));
    if (null != getValue(gRsp, EXPDATE)) {
      jsRsp.setEXPMONTH(getValue(gRsp, EXPDATE).substring(0, 2));
      jsRsp.setEXPYEAR(getValue(gRsp, EXPDATE).substring(2));
    }
    String xml = convertPOJO2XMLString(jsRsp);
    if (null != getJSONObj(gRsp, RECEIPTXML) && StringUtils.isNotEmpty(xml)) {
      xml = xml.replace(RECEIPTTEXT, (getXMLForJSONObj(getJSONObj(gRsp, RECEIPTXML)) + RECEIPTTEXT));
    }
    rspMap.put(getKey(gRsp), xml);
    return rspMap;
  }

  /**
   * get the response map with key as ordierid+terminalid and payload as the
   * response.
   *
   * @param rspMsg
   *          .
   * @param handShakeSource
   *          .
   * @return ConcurrentMap .
   */
  public static ConcurrentMap<String, String> getJSRsp(final String rspMsg, final String handShakeSource) {
    ConcurrentMap<String, String> rspMap = new ConcurrentHashMap<String, String>();
    if (StringUtils.isNotEmpty(rspMsg)) {
        if (OG.equals(handShakeSource)) {
            rspMap = getEERsp(rspMsg, GATEWAYRESPONSE);
        } else if (MB.equals(handShakeSource)) {
            rspMap = getEERsp(rspMsg, RESPONSE);
        }
    }
    return rspMap;
  }

  /**
   * converts given POJO class to String XML data.
   *
   * @param objJSCompRsp
   *          Response.
   * @return string :returns xmlString Data.
   */
  private static String convertPOJO2XMLString(final Response objJSCompRsp) {
    final StringWriter rspXML = new StringWriter();
    rspXML.append("<?xml version=\"1.0\"?>");
    JAXBContext context;
    Marshaller marshaller;

    try {
        context = JAXBContext.newInstance(Response.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(objJSCompRsp, rspXML);
    } catch (final Exception exception) {
        LOG.error("Exception @ convertPOJO2XMLString()=" + exception);
    }

    return rspXML.toString();
  }
  /**
   * @param localDate MMDDYY.
   * @param localTime HHMMSS.
   * @return String yyyy-MM-dd HH:mm:ss format.
   */
  public static String getDateTime(final String localDate, final String localTime) {
      String strTransDateTime = null;
      String localDateNTime = localDate + localTime;
      localDateNTime = localDateNTime.replaceAll("null", "");
      if (StringUtils.isNotEmpty(localDateNTime)) {

          SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyHHmmss");
          SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          try {
              strTransDateTime = sdf2.format(sdf1.parse(localDateNTime));

          } catch (ParseException e) {
              LOG.error("Exception @ getDateTime=" + e.getMessage());
          }

      }
      return strTransDateTime;
  }
    /**
     * @param json input JSON string.
     * @return key string value.
     */
    public static String getKey(final JSONObject json) {
        return getValue(json, ORDERID) + getValue(json, TERMINALID);
    }

    /**
     * get the key information from the message.
     * @param message .
     * @param handShakeSource .
     * @return String.
     */
    public static String getKey(final String message,
            final String handShakeSource) {
        String nodeName = null;
        if (handShakeSource.equals(OG)) {
            nodeName = GATEWAYRESPONSE;
        } else {
            nodeName = RESPONSE;
        }
        JSONObject jsonTxtMsg = new JSONObject(message).getJSONObject(nodeName);
        String key = getKey(jsonTxtMsg);
        if (MDC.get("reqId") == null) {
            MDC.put("reqId", getValue(jsonTxtMsg, ORDERID) + "-"
                    + getValue(jsonTxtMsg, TERMINALID));
        }
        LOG.info(MDCLog.getJsonLogMsg("incomingReqFromRspSys", message, true));
        return key;
    }
}
