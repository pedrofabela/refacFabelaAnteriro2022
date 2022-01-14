/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappers;

import daos.*;
import beans.productosBean;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author pedro
 */
public class historiaProductoMapper implements Mapper{
    
     public Object mapRow(ResultSet rs) throws SQLException {
        productosBean usr = new productosBean();
        
                
 
        if (rs.getString("NO_VENTA") != null) {
            usr.setNO_VENTA(rs.getString("NO_VENTA").trim());
        } else {
            usr.setNO_VENTA(rs.getString("NO_VENTA"));
        }
        
        if (rs.getString("NO_PARTE") != null) {
            usr.setNO_PARTE(rs.getString("NO_PARTE").trim());
        } else {
            usr.setNO_PARTE(rs.getString("NO_PARTE"));
        }
         if (rs.getString("STOCK_ANTERIOR") != null) {
            usr.setSTOCK_ANTERIOR(rs.getString("STOCK_ANTERIOR").trim());
        } else {
            usr.setSTOCK_ANTERIOR(rs.getString("STOCK_ANTERIOR"));
        }
          if (rs.getString("PRODUCTO_VENTA") != null) {
            usr.setPRODUCTO_VENTA(rs.getString("PRODUCTO_VENTA").trim());
        } else {
            usr.setPRODUCTO_VENTA(rs.getString("PRODUCTO_VENTA"));
        }
          if (rs.getString("STOCK_FINAL") != null) {
            usr.setSTOCK_FINAL(rs.getString("STOCK_FINAL").trim());
        } else {
            usr.setSTOCK_FINAL(rs.getString("STOCK_FINAL"));
        }
           if (rs.getString("FECHA_REGISTRO") != null) {
            usr.setFECHA_REGISTRO(rs.getString("FECHA_REGISTRO").trim());
        } else {
            usr.setFECHA_REGISTRO(rs.getString("FECHA_REGISTRO"));
        }
           if (rs.getString("VENDEDOR") != null) {
            usr.setVENDEDOR(rs.getString("VENDEDOR").trim());
        } else {
            usr.setVENDEDOR(rs.getString("VENDEDOR"));
           }
           
              if (rs.getString("CANTIDAD_TRAER") != null) {
            usr.setCANTIDAD_TRAER(rs.getString("CANTIDAD_TRAER").trim());
        } else {
            usr.setCANTIDAD_TRAER(rs.getString("CANTIDAD_TRAER"));
        }
         
         
        return usr;
    }
    
}
