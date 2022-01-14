/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappers;

import beans.productosBean;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pedro
 */
public class carroCotizaMapper implements Mapper{
     public Object mapRow(ResultSet rs) throws SQLException {
        productosBean usr = new productosBean();
 
 

        
        
        if (rs.getString("ID_COTIZAPRODUCTO") != null) {
            usr.setID_COTIZAPRODUCTO(rs.getString("ID_COTIZAPRODUCTO").trim());
        } else {
            usr.setID_COTIZAPRODUCTO(rs.getString("ID_COTIZAPRODUCTO"));
        }
        
        
         if (rs.getString("NO_COTIZA") != null) {
            usr.setNO_COTIZA(rs.getString("NO_COTIZA").trim());
        } else {
            usr.setNO_COTIZA(rs.getString("NO_COTIZA"));
        }
        
         
           if (rs.getString("NO_PARTE") != null) {
            usr.setNO_PARTE(rs.getString("NO_PARTE").trim());
        } else {
            usr.setNO_PARTE(rs.getString("NO_PARTE"));
        }
           
           
           
             if (rs.getDouble("PRECIO_UNITARIO") ==0) {
            usr.setPRECIO_UNITARIO(rs.getDouble("PRECIO_UNITARIO"));
        } else {
            usr.setPRECIO_UNITARIO(rs.getDouble("PRECIO_UNITARIO"));
        }
             
              if (rs.getString("NO_PRODUCTOCOTIZA") != null) {
            usr.setNO_PRODUCTOCOTIZA(rs.getString("NO_PRODUCTOCOTIZA").trim());
        } else {
            usr.setNO_PRODUCTOCOTIZA(rs.getString("NO_PRODUCTOCOTIZA"));
        }
              
            if (rs.getDouble("PRECIO_FINAL") != 0) {
            usr.setPRECIO_FINAL(rs.getDouble("PRECIO_FINAL"));
        } else {
            usr.setPRECIO_FINAL(rs.getDouble("PRECIO_FINAL"));
        }   
            
            
              if (rs.getString("RFC_CLIENTE") != null) {
            usr.setRFC_CLIENTE(rs.getString("RFC_CLIENTE").trim());
        } else {
            usr.setRFC_CLIENTE(rs.getString("RFC_CLIENTE"));
        }   
              
              
               if (rs.getString("STATUS_COTIZA") != null) {
            usr.setSTATUS_COTIZA(rs.getString("STATUS_COTIZA").trim());
        } else {
            usr.setSTATUS_COTIZA(rs.getString("STATUS_COTIZA"));
        }   
               
           if (rs.getString("RESPONSABLE") != null) {
            usr.setRESPONSABLE(rs.getString("RESPONSABLE").trim());
        } else {
            usr.setRESPONSABLE(rs.getString("RESPONSABLE"));
        }         
            
           if (rs.getString("FECHA_COTIZA") != null) {
            usr.setFECHA_COTIZA(rs.getString("FECHA_COTIZA").trim());
        } else {
            usr.setFECHA_COTIZA(rs.getString("FECHA_COTIZA"));
        }          
             if (rs.getString("AUXCOTIZA") != null) {
            usr.setAUXCOTIZA(rs.getString("AUXCOTIZA").trim());
        } else {
            usr.setAUXCOTIZA(rs.getString("AUXCOTIZA"));
        }   
             
              if (rs.getString("NO_VENTA") != null) {
            usr.setNO_VENTA(rs.getString("NO_VENTA").trim());
        } else {
            usr.setNO_VENTA(rs.getString("NO_VENTA"));
        }   
                      
               if (rs.getDouble("IVA_UNITARIO") != 0) {
            usr.setIVA_UNITARIO(rs.getDouble("IVA_UNITARIO"));
        } else {
            usr.setIVA_UNITARIO(rs.getDouble("IVA_UNITARIO"));
        }   
              
           if (rs.getDouble("PRECIO_PARTIDA") != 0) {
            usr.setPRECIO_PARTIDA(rs.getDouble("PRECIO_PARTIDA"));
        } else {
            usr.setPRECIO_PARTIDA(rs.getDouble("PRECIO_PARTIDA"));
        }   
             if (rs.getDouble("IVA_PARTIDA") != 0) {
            usr.setIVA_PARTIDA(rs.getDouble("IVA_PARTIDA"));
        } else {
            usr.setIVA_PARTIDA(rs.getDouble("IVA_PARTIDA"));
        }      
             if (rs.getDouble("PRECIO_UNITARIO_TOTAL") != 0) {
            usr.setPRECIO_UNITARIO_TOTAL(rs.getDouble("PRECIO_UNITARIO_TOTAL"));
        } else {
            usr.setPRECIO_UNITARIO_TOTAL(rs.getDouble("PRECIO_UNITARIO_TOTAL"));
        }  
               if (rs.getDouble("PRECIO_PARTIDA_TOTAL") != 0) {
            usr.setPRECIO_PARTIDA_TOTAL(rs.getDouble("PRECIO_PARTIDA_TOTAL"));
        } else {
            usr.setPRECIO_PARTIDA_TOTAL(rs.getDouble("PRECIO_PARTIDA_TOTAL"));
        }         
           
        return usr;
    }
}
