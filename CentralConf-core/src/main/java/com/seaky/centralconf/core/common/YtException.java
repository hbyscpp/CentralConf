package com.seaky.centralconf.core.common;

public class YtException extends RuntimeException {

  private Errorcode errorcode;

  private String extraInfo;

  private static String format =
      "errorcode:%d,typeid:%d,appid:%d,code:%d,errormsg:%s \n extrainfo:%s";

  private static String traceFormat =
      "errorcode:%d,typeid:%d,appid:%d,code:%d,errormsg:%s \n extrainfo:%s \n%s";

  public YtException(Errorcode errorcode) {
    this.errorcode = errorcode;
  }

  public YtException(Errorcode errorcode, String extraInfo) {
    super(formatMsg(errorcode, extraInfo));
    this.errorcode = errorcode;
    this.extraInfo = extraInfo;
  }

  public YtException(Errorcode errorcode, String extraInfo, String traceformat) {
    super(formatMsg(errorcode, extraInfo, traceformat));
    this.errorcode = errorcode;
    this.extraInfo = extraInfo;
  }

  public String getExtraInfo() {
    return extraInfo;
  }

  public Errorcode getErrorcode() {
    return errorcode;
  }

  private static String formatMsg(Errorcode errorcode, String extraInfo) {


    return String.format(format, errorcode.getErrorcode(), errorcode.getTypeId(),
        errorcode.getAppId(), errorcode.getCode(), errorcode.getDesc(), extraInfo);
  }

  private static String formatMsg(Errorcode errorcode, String extraInfo, String trace) {


    return String.format(traceFormat, errorcode.getErrorcode(), errorcode.getTypeId(),
        errorcode.getAppId(), errorcode.getCode(), errorcode.getDesc(), extraInfo, trace);
  }



}
