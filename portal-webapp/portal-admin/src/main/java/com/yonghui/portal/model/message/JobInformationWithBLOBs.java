package com.yonghui.portal.model.message;

public class JobInformationWithBLOBs extends JobInformation {
    private String text;

    private String sqlScript;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript == null ? null : sqlScript.trim();
    }
}