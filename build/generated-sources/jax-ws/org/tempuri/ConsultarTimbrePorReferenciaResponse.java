
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConsultarTimbrePorReferenciaResult" type="{http://schemas.datacontract.org/2004/07/TES.TFD.V33.Negocios}RespuestaTFD33" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "consultarTimbrePorReferenciaResult"
})
@XmlRootElement(name = "ConsultarTimbrePorReferenciaResponse")
public class ConsultarTimbrePorReferenciaResponse {

    @XmlElementRef(name = "ConsultarTimbrePorReferenciaResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTFD33> consultarTimbrePorReferenciaResult;

    /**
     * Obtiene el valor de la propiedad consultarTimbrePorReferenciaResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     *     
     */
    public JAXBElement<RespuestaTFD33> getConsultarTimbrePorReferenciaResult() {
        return consultarTimbrePorReferenciaResult;
    }

    /**
     * Define el valor de la propiedad consultarTimbrePorReferenciaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTFD33 }{@code >}
     *     
     */
    public void setConsultarTimbrePorReferenciaResult(JAXBElement<RespuestaTFD33> value) {
        this.consultarTimbrePorReferenciaResult = value;
    }

}
