package com.trux.app.deploy;

/**
 * Created by sharadsingh on 16/06/17.
 */

public class BaseResponce {
    private  String errorCode;
    private  String errorMesaage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMesaage() {
        return errorMesaage;
    }

    public void setErrorMesaage(String errorMesaage) {
        this.errorMesaage = errorMesaage;
    }


}
