package com.cimb.codetest.common;


public class WebServiceUtils {

	private static final WebServiceUtils INSTANCE = new WebServiceUtils();

    private WebServiceUtils() {}

    public static WebServiceUtils getInstance() {
        return INSTANCE;
    }

    public WSResponseBean commonSuccess(Object payload) {
        WSResponseBean resBean = new WSResponseBean();
        resBean.setResCode(Constants.RES_CODE_SUCCESS);
        resBean.setResMsg(Constants.RES_MSG_SUCCESS);
        resBean.setPayload(payload);
        return resBean;
    }

    public WSResponseBean commonError(Object payload, String errMsg) {
        WSResponseBean resBean = new WSResponseBean();
        resBean.setResCode(Constants.RES_CODE_ERROR);
        resBean.setResMsg(errMsg);
        resBean.setPayload(payload);
        return resBean;
    }
}
