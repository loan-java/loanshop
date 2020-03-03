package io.dkgj.form;

import lombok.Data;

@Data
public class LogVisitSourceForm {

    private String mobile;
    /**
     *
     */
    private String channel;
    /**
     *
     */
    private String appName;
    /**
     *
     */
    private String appVersion;
    /**
     *
     */
    private String appCodeName;
    /**
     *
     */
    private String userAgent;
    /**
     *
     */
    private String referrer;
}
