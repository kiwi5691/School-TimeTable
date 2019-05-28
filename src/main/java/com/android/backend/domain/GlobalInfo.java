package com.android.backend.domain;

public class GlobalInfo {
    private Integer id;

    private Integer version;

    private String versionstr;

    private String termbegin;

    private Integer yearfrom;

    private Integer yearto;

    private Integer term;

    private Integer isfirstuse;

    private Integer activeuseruid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getVersionstr() {
        return versionstr;
    }

    public void setVersionstr(String versionstr) {
        this.versionstr = versionstr == null ? null : versionstr.trim();
    }

    public String getTermbegin() {
        return termbegin;
    }

    public void setTermbegin(String termbegin) {
        this.termbegin = termbegin == null ? null : termbegin.trim();
    }

    public Integer getYearfrom() {
        return yearfrom;
    }

    public void setYearfrom(Integer yearfrom) {
        this.yearfrom = yearfrom;
    }

    public Integer getYearto() {
        return yearto;
    }

    public void setYearto(Integer yearto) {
        this.yearto = yearto;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getIsfirstuse() {
        return isfirstuse;
    }

    public void setIsfirstuse(Integer isfirstuse) {
        this.isfirstuse = isfirstuse;
    }

    public Integer getActiveuseruid() {
        return activeuseruid;
    }

    public void setActiveuseruid(Integer activeuseruid) {
        this.activeuseruid = activeuseruid;
    }
}