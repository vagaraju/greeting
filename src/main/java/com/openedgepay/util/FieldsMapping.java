package com.openedgepay.util;

import static com.openedgepay.constants.Constants.MB;
import static com.openedgepay.constants.Constants.DECLIENDCODE;
import static com.openedgepay.constants.Constants.GATEWAYCODEMAP;
import static com.openedgepay.constants.Constants.SUCCESSCODE;

import java.util.Locale;

/**
 * fields error code mapping class.
 *
 * @author M1034465
 *
 */
public class FieldsMapping {
  /**
   * get the result code from the given gateway response code.
   *
   * @param gateWayRespCode
   *          .
   * @return string .
   */
  public final String getResult(final String gateWayRespCode) {
    String resultCode = DECLIENDCODE;
    if (null != gateWayRespCode && GATEWAYCODEMAP.get(gateWayRespCode) != null) {
      resultCode = String.valueOf(GATEWAYCODEMAP.get(gateWayRespCode));
    }
    return resultCode;
  }

  /**
   * get the result code and result message for response code and desc.
   *
   * @param code
   *          .
   * @param desc
   *          .
   * @return string .
   */
  public final String getResultMsg(final String code, final String desc) {
    String resultCode = "Success";
    if (!SUCCESSCODE.equals(code)) {
      resultCode = "Declined by Host: " + code + ", " + desc;
    }
    return resultCode;
  }
}
