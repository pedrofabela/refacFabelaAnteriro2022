
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WSCFDI33", targetNamespace = "http://tempuri.org/", wsdlLocation = "https://www.foliosdigitalespac.com/WSTimbrado33/WSCFDI33.svc?WSDL")
public class WSCFDI33
    extends Service
{

    private final static URL WSCFDI33_WSDL_LOCATION;
    private final static WebServiceException WSCFDI33_EXCEPTION;
    private final static QName WSCFDI33_QNAME = new QName("http://tempuri.org/", "WSCFDI33");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://www.foliosdigitalespac.com/WSTimbrado33/WSCFDI33.svc?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSCFDI33_WSDL_LOCATION = url;
        WSCFDI33_EXCEPTION = e;
    }

    public WSCFDI33() {
        super(__getWsdlLocation(), WSCFDI33_QNAME);
    }

    public WSCFDI33(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSCFDI33_QNAME, features);
    }

    public WSCFDI33(URL wsdlLocation) {
        super(wsdlLocation, WSCFDI33_QNAME);
    }

    public WSCFDI33(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSCFDI33_QNAME, features);
    }

    public WSCFDI33(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSCFDI33(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IWSCFDI33
     */
    @WebEndpoint(name = "soapHttpEndpoint")
    public IWSCFDI33 getSoapHttpEndpoint() {
        return super.getPort(new QName("http://tempuri.org/", "soapHttpEndpoint"), IWSCFDI33.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IWSCFDI33
     */
    @WebEndpoint(name = "soapHttpEndpoint")
    public IWSCFDI33 getSoapHttpEndpoint(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "soapHttpEndpoint"), IWSCFDI33.class, features);
    }

    /**
     * 
     * @return
     *     returns IWSCFDI33
     */
    @WebEndpoint(name = "soapHttpEndpointHttps")
    public IWSCFDI33 getSoapHttpEndpointHttps() {
        return super.getPort(new QName("http://tempuri.org/", "soapHttpEndpointHttps"), IWSCFDI33.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IWSCFDI33
     */
    @WebEndpoint(name = "soapHttpEndpointHttps")
    public IWSCFDI33 getSoapHttpEndpointHttps(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "soapHttpEndpointHttps"), IWSCFDI33.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSCFDI33_EXCEPTION!= null) {
            throw WSCFDI33_EXCEPTION;
        }
        return WSCFDI33_WSDL_LOCATION;
    }

}
