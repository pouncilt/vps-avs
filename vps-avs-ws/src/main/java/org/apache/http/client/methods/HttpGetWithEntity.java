package org.apache.http.client.methods;

/**
 * Created by tpouncil on 6/12/2015.
 */
public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
    public final static String METHOD_NAME = "GET";

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}