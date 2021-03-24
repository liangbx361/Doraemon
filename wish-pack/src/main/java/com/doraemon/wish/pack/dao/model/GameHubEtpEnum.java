package com.doraemon.wish.pack.dao.model;

public enum GameHubEtpEnum {

    BI("com.u17173.gamehub.etp.bi.BIEtp"),
    TDGA("com.u17173.gamehub.etp.tdga.TdgaEtp"),
    TDGAGP("com.u17173.gamehub.etp.tdgagp.TdgagpEtp");

    private String className;

    GameHubEtpEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
