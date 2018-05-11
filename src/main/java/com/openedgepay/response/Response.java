package com.openedgepay.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class is used to set edge express response fields.
 *
 * @author M1034465
 *
 */
@XmlRootElement(name = "RESPONSE", namespace = "")
@XmlType(propOrder = { "RESULT", "RESULTMSG", "ALIAS", "APPROVALCODE", "APPROVEDAMOUNT", "AVSRESPONSECODE",
    "BATCHAMOUNT", "BATCHNO", "CARDBRAND", "CARDBRANDSHORT", "CARDCODERESPONSE", "CARDHOLDERNAME", "CARDTYPE",
    "DATETIME", "DEDICATEDFILENAME", "ENTRYTYPE", "EXPMONTH", "EXPYEAR", "HOSTRESPONSECODE", "HOSTRESPONSEDESCRIPTION",
    "MASKEDCARDNUMBER", "ORDERID", "RETURNID", "TRANSACTIONID", "RECEIPTTEXT" })
public class Response {
  /** expire month. */
  private String expmonth;
  /** data and time. */
  private String datatime;
  /** expiration year. */
  private String expyear;
  /** transaction id. */
  private String transactionid;
  /** approved amount. */
  private String approvedamount;
  /** result message. */
  private String resultmsg;
  /** card brand. */
  private String cardbrand;
  /** approval code. */
  private String approvalcode;
  /** batch no. */
  private String batchno;
  /** host response code. */
  private String hostresponsecode;
  /** result. */
  private String result;
  /** order id. */
  private String orderid;
  /** masked account card number. */
  private String maskedcardnumber;
  /** card code response. */
  private String cardcoderesponse;
  /** Host response description. */
  private String hostresponsedescription;
  /** card type. */
  private String cardtype;
  /** receipt text. */
  private String receipttext;
  /** avs response code. */
  private String avsresponsecode;
  /** card brand short. */
  private String cardbrandshort;
  /** card holder name. */
  private String cardholdername;
  /** card alias. */
  private String alias;
  /** batch amount. */
  private String batchamount;
  /** return id. */
  private String returnid;
  /** entry type. */
  private String entrytype;
  /** dedicated file name. */
  private String dedicatedfilename;
  /** constructor. */
  public Response() {
  }
/**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getDEDICATEDFILENAME() {
    return dedicatedfilename;
  }

  /**
   * it's self explanatory set method for respective field.
   *
   * @param dEDICATEDFILENAME
   *          .
   **/
  public final void setDEDICATEDFILENAME(final String dEDICATEDFILENAME) {
    dedicatedfilename = dEDICATEDFILENAME;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getENTRYTYPE() {
    return entrytype;
  }

  /**
   * it's self explanatory set method for respective field.
   *
   * @param eNTRYTYPE
   *          .
   **/
  public final void setENTRYTYPE(final String eNTRYTYPE) {
    entrytype = eNTRYTYPE;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getCARDBRANDSHORT() {
    return cardbrandshort;
  }

  /**
   * it's self explanatory set method for respective field.
   *
   * @param cARDBRANDSHORT
   *          .
   **/
  public final void setCARDBRANDSHORT(final String cARDBRANDSHORT) {
    cardbrandshort = cARDBRANDSHORT;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getCARDHOLDERNAME() {
    return cardholdername;
  }

  /**
   * it's self explanatory set method for respective field.
   *
   * @param cARDHOLDERNAME
   *          .
   **/
  public final void setCARDHOLDERNAME(final String cARDHOLDERNAME) {
    cardholdername = cARDHOLDERNAME;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return alias
   */
  @XmlElement
  public final String getALIAS() {
    return alias;
  }

  /**
   * it's self explanatory set method for respective field.
   *
   * @param aLIAS
   *          .
   **/
  public final void setALIAS(final String aLIAS) {
    alias = aLIAS;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getBATCHAMOUNT() {
    return batchamount;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param batcamt
   *          .
   */
  public final void setBATCHAMOUNT(final String batcamt) {
    this.batchamount = batcamt;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getRETURNID() {
    return returnid;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param rETURNID
   *          .
   */
  public final void setRETURNID(final String rETURNID) {
    returnid = rETURNID;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getEXPMONTH() {
    return expmonth;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param expm
   *          .
   */
  public final void setEXPMONTH(final String expm) {
    this.expmonth = expm;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement(name = "DATE_TIME")
  public final String getDATETIME() {
    return datatime;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param dtTime
   *          .
   */
  public final void setDATETIME(final String dtTime) {
    this.datatime = dtTime;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getEXPYEAR() {
    return expyear;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param expyr
   *          .
   */
  public final void setEXPYEAR(final String expyr) {
    this.expyear = expyr;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getTRANSACTIONID() {
    return transactionid;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param transid
   *          .
   */
  public final void setTRANSACTIONID(final String transid) {
    this.transactionid = transid;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getAPPROVEDAMOUNT() {
    return approvedamount;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param apprvAmt
   *          .
   */
  public final void setAPPROVEDAMOUNT(final String apprvAmt) {
    this.approvedamount = apprvAmt;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getRESULTMSG() {
    return resultmsg;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param rsltMsg
   *          .
   */
  public final void setRESULTMSG(final String rsltMsg) {
    this.resultmsg = rsltMsg;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getCARDBRAND() {
    return cardbrand;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param cardBrand
   *          .
   */
  public final void setCARDBRAND(final String cardBrand) {
    this.cardbrand = cardBrand;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getAPPROVALCODE() {
    return approvalcode;
  }

  /**
   * it's batch amount get method for respective field.
   *
   * @param apprvCode        .
   */
  public final void setAPPROVALCODE(final String apprvCode) {
    this.approvalcode = apprvCode;
  }
  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getBATCHNO() {
    return batchno;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param batcno
   *          .
   */
  public final void setBATCHNO(final String batcno) {
    this.batchno = batcno;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getHOSTRESPONSECODE() {
    return hostresponsecode;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param hostrspcode
   *          .
   */
  public final void setHOSTRESPONSECODE(final String hostrspcode) {
    this.hostresponsecode = hostrspcode;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getRESULT() {
    return result;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param rslt
   *          .
   */
  public final void setRESULT(final String rslt) {
    this.result = rslt;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getORDERID() {
    return orderid;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param ordrId
   *          .
   */
  public final void setORDERID(final String ordrId) {
    this.orderid = ordrId;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getMASKEDCARDNUMBER() {
    return maskedcardnumber;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param maskNum
   *          .
   */
  public final void setMASKEDCARDNUMBER(final String maskNum) {
    this.maskedcardnumber = maskNum;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getCARDCODERESPONSE() {
    return cardcoderesponse;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param cardrsp
   *          .
   */
  public final void setCARDCODERESPONSE(final String cardrsp) {
    this.cardcoderesponse = cardrsp;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getHOSTRESPONSEDESCRIPTION() {
    return hostresponsedescription;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param hostresDesc
   *       .
   */
  public final void setHOSTRESPONSEDESCRIPTION(final String hostresDesc) {
    this.hostresponsedescription = hostresDesc;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getCARDTYPE() {
    return cardtype;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param cARDTYPE
   *     .
   */
  public final void setCARDTYPE(final String cARDTYPE) {
    this.cardtype = cARDTYPE;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getRECEIPTTEXT() {
    return receipttext;
  }

  /**
   * it's self explanatory set method for respective field.
   *
   * @param recptxt .
   */
  public final void setRECEIPTTEXT(final String recptxt) {
    this.receipttext = recptxt;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @return String
   */
  @XmlElement
  public final String getAVSRESPONSECODE() {
    return avsresponsecode;
  }

  /**
   * it's self explanatory get method for respective field.
   *
   * @param avg .
   */
  public final void setAVSRESPONSECODE(final String avg) {
    this.avsresponsecode = avg;
  }
}
