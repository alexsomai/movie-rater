package org.ubb.cluj.movierater.web.support;

public final class AjaxUtils {

    private AjaxUtils() {/**/}

    public static boolean isAjaxRequest(String requestedWith) {
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }
}
