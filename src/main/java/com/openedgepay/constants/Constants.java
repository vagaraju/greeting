package com.openedgepay.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class for Constants.
 *
 * @author M1034465
 *
 */
public final class Constants {
  /**
   * constructor.
   */
  private Constants() {
  }
  /**
   * indicate js component.
   */
  public static final String JS = "JS";
  /**
   * indicate openedge component.
   */
  public static final String OG = "OG";
  /**
   * indicate mobile component.
   */
  public static final String MB = "MB";
  /**
   * indicate js URL.
   */
  public static final String JS_WSS_URI = "/TxnStatus/JS";
  /**
   * indicate js mobile.
   */
  public static final String MB_WSS_URI = "/TxnStatus/MB";
  /**
   * indicate OG URL.
   */
  public static final String OG_WSS_URI = "/TxnStatus/OG";
  /**
   * store JSON fields.
   */
  public static final ConcurrentHashMap<String, String> JSONFIELDS = new ConcurrentHashMap<String, String>();
  /**
   * label field.
   */
  public static final String NOLABEL = "nolabel";
  /**
   * expiration date data field.
   */
  public static final String EXPDATE = "ExpDate";
  /**
   * label field.
   */
  public static final String RECEIPTTEXT = "<RECEIPTTEXT>";
  /**
   * expiration date data field.
   */
  public static final String RECEIPTXML = "ReceiptXml";
  /**
   * mapping gateway response fields to JSON API .
   */
  static {
    JSONFIELDS.put("MerchantHeader", NOLABEL);
    JSONFIELDS.put("MerchantAddress", NOLABEL);
    JSONFIELDS.put("MerchantPhone", NOLABEL);
    JSONFIELDS.put("MerchantID", "Terminal ID");
    JSONFIELDS.put("TransactionID", "Trans ID");
    JSONFIELDS.put("OrderID", "Order ID");
    JSONFIELDS.put("ReceiptNumber", "Receipt #");
    JSONFIELDS.put("TransactionType", "Trans Type");
    JSONFIELDS.put("TransactionDateTime", "Date/Time");
    JSONFIELDS.put("CardType", "Card Type");
    JSONFIELDS.put("CardNumber", "Card Number");
    JSONFIELDS.put("EntryMethod", "Entry Method");
    JSONFIELDS.put("ApprovalCode", "Approval Code");
    JSONFIELDS.put("TotalAmount", "Total Amount");
    JSONFIELDS.put("LineItems", NOLABEL);
    JSONFIELDS.put("Verbiage", NOLABEL);
    JSONFIELDS.put("SignatureLine", NOLABEL);
    JSONFIELDS.put("SignatureText", NOLABEL);
    JSONFIELDS.put("AuthorizationAgreement", NOLABEL);
    JSONFIELDS.put("MerchantPolicy", NOLABEL);
    JSONFIELDS.put("MerchantFooter", NOLABEL);
    JSONFIELDS.put("Clerk", "Clerk ID");
    JSONFIELDS.put("AccountType", "Account Type");
  };
  /** constant field for gateway json response field. */
  public static final String GATEWAYRESPONSE = "GatewayResponse";
  /** constant field for gateway json response field. */
  public static final String RESPONSECODE = "ResponseCode";
  /** constant field for gateway json response field. */
  public static final String RESPONSEDESCRIPTION = "ResponseDescription";
  /** constant field for gateway json response field. */
  public static final String TRANSACTIONID = "TransactionID";
  /** constant field for gateway json response field. */
  public static final String AMOUNT = "Amount";
  /** constant field for gateway json response field. */
  public static final String CARDTYPE = "CardType";
  /** constant field for gateway json response field. */
  public static final String CARDBRAND = "CardBrand";
  /** constant field for gateway json response field. */
  public static final String CARDBRANDSHORT = "CardBrandShort";
  /** constant field for gateway json response field. */
  public static final String MASKEDACCTNUM = "MaskedAcctNum";
  /** constant field for gateway json response field. */
  public static final String CARDHOLDERNAME = "CardholderName";
  /** constant field for gateway json response field. */
  public static final String ALIAS = "Alias";
  /** constant field for gateway json response field. */
  public static final String BATCHNUM = "BatchNum";
  /** constant field for gateway json response field. */
  public static final String BATCHAMOUNT = "BatchAmount";
  /** constant field for gateway json response field. */
  public static final String APPROVALCODE = "ApprovalCode";
  /** constant field for gateway json response field. */
  public static final String AVSRESPONSECODE = "AVSResponseCode";
  /** constant field for gateway json response field. */
  public static final String CARDCODERESPONSE = "CardCodeResponse";
  /** constant field for gateway json response field. */
  public static final String RECEIPT = "Receipt";
  /** constant field for gateway json response field. */
  public static final String ORDERID = "OrderID";
  /** constant field for gateway json response field. */
  public static final String RETURNID = "ReturnID";
  /** constant field for gateway json response field. */
  public static final String EMVDEDICATEDFILENAME = "EMVDedicatedFileName";
  /** constant field for gateway json response field. */
  public static final String ENTRYMETHOD = "EntryMethod";
  /** constant field for gateway json response field. */
  public static final String TRANSACTIONDATETIME = "TransactionDateTime";
  /** constant field for gateway json response field. */
  public static final String TERMINALID = "TerminalID";
  /** constant field for gateway json response field. */
  public static final String LABEL = "label";
  /** constant field for gateway json response field. */
  public static final String CONTENT = "content";

  /** constant field for gateway json response field. */
  public static final String RECEIPTXML_STRT_TAG = "<RECEIPTXML>";

  /** constant field for gateway json response field. */
  public static final String RECEIPTXML_END_TAG = "</RECEIPTXML>";
  /** empty space. */
  public static final String EMPTY_SPACE = " ";
  /** empty space. */
  public static final String TOKENEQ = "Token=";
  /** empty space. */
  public static final String LOCALDATE = "LocalDate";
  /** empty space. */
  public static final String LOCALTIME = "LocalTime";
  /** list of allowed origion values. */
    public static final List<String> ALLOWEDORIGINS = Collections
            .unmodifiableList(
                    new ArrayList<String>(Arrays.asList("JS", "MB", "OG")));
  /** indicates source of request. */
  public static final String HANDSHAKESOURCE = "HandshakeRequestSource";
  /** mapping edge express response fields. */
  public static final Map<String, Integer> GATEWAYCODEMAP = new ConcurrentHashMap<String, Integer>();
  /** CANCEL code. */
  public static final int CANCEL_3 = 3;
  /** DEVICE INIT ERROR. */
  public static final int DEVICE_INTI_ERR_7 = 7;
  /** Failure ERROR. */
  public static final int FAILURE_4 = 4;
  /** GATEWAY TIMEOUT. */
  public static final int GATEWAYTIMEOUT_2 = 2;
  /** SUCCESS. */
  public static final int SUCCESS_0 = 0;
  /** DECLIENED_CODE. */
  public static final String DECLIENDCODE = "12";
  /** SUCCESS CODE. */
  public static final String SUCCESSCODE = "0";
  /** mobile cancellation response header name. */
  public static final String RESPONSE = "Response";
   static {
    // gateway response codes....
    GATEWAYCODEMAP.put("000", SUCCESS_0);
    GATEWAYCODEMAP.put("007", SUCCESS_0);
    GATEWAYCODEMAP.put("032", SUCCESS_0);
    GATEWAYCODEMAP.put("820", GATEWAYTIMEOUT_2);
    GATEWAYCODEMAP.put("821", GATEWAYTIMEOUT_2);

    // mobile response codes....
    GATEWAYCODEMAP.put("3", CANCEL_3);
    GATEWAYCODEMAP.put("4", FAILURE_4);
    GATEWAYCODEMAP.put("7", DEVICE_INTI_ERR_7);
    GATEWAYCODEMAP.put("99", GATEWAYTIMEOUT_2);
  };
}
