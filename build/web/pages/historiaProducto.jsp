<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<SCRIPT LANGUAGE="JavaScript">
history.forward();
</SCRIPT>

<html xmlns="http://www.w3.org/1999/xhtml">

    <link href="css/menu.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">

        function guarda(accion) {

            document.forma.action = accion;
            document.forma.submit();
        }
        //PARA REGRESAR EN DONDE SE QUEDO...........
        window.onload = function () {
            var pos = window.name || 0;
            window.scrollTo(0, pos);
        }
        window.onunload = function () {
            window.name = self.pageYOffset
                    || (document.documentElement.scrollTop + document.body.scrollTop);
        }
        window.onload = function () {/*hace que se cargue la función lo que predetermina que div estará oculto hasta llamar a la función nuevamente*/
            var pos = window.name || 0;
            window.scrollTo(0, pos);

            window.location.hash = "no";
            window.location.hash = "Again-No-back-button" //chrome
            window.onhashchange = function () {
                window.location.hash = "no";
            }

        }

        window.onunload = function () {
            window.name = self.pageYOffset
                    || (document.documentElement.scrollTop + document.body.scrollTop);

        }
    </script>  
    <head>
        <title>Refacciones Fabela</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    </head>
    <body>
        <header id="main-header">
            <h1  class="logo">Refacciones Fabela</h1>
        </header>
        <jsp:include page="/pages/menu.jsp"></jsp:include>
        <s:form name="forma" >
            <section id="main-content">
                <div class="div-contenido_gral">  
                    <h1 class="user-sesion" align="right">USUARIO:  <s:property value="usuariocons.NAMEUSUARIO" ></s:property></h1>
                    <s:hidden name="usuariocons.NAMEUSUARIO" id="%{usuariocons.NAMEUSUARIO}"></s:hidden> 
                        <article class="articulo1" >

                            <div class="div_sec1" id="flotante-busqueda">
                                <div class="div-busqueda" >
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Busqueda</h2></div>
                                <s:textfield name="camp.CONSULTA_PARTE" id="CONSULTA_PARTE" placeholder="Número de Parte" required="true"  cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>
                                <a href="Javascript:guarda('buscarProductoHistoria')"><div class="boton">  Buscar  </div> </a>
                            </div>
                        </div>
                    </article> 
                    <article class="articulo2">

                        <div style="margin: auto; text-align: center; width: 100%;">   <h2>Historia de movimientos de los productos</h2></div>

                        <div class="div_sec1">
                            <div style="width: 100%; text-align: center; color: #333; margin-top: 20px; margin-bottom: 20px;"> 
                                La historia de venta-stock del producto es la siguiente:
                            </div>
                            <div id="scroltabla-cliente">
                                <table id="customers" style="width: 95%; margin: auto; border-radius: 20px;">
                                    <tr>
                                        <th id="centrar-dato" style="max-width: 120px;">NO VENTA </th>
                                        <th id="centrar-dato">NO PARTE</th>
                                        <th id="centrar-dato" style="max-width: 40px; min-width: 40px;">STOCK INICIAL</th>
                                        <th id="centrar-dato" style="max-width: 40px; min-width: 40px;">PRODUCTO VENTA</th>
                                        <th id="centrar-dato"> STOCK FINAL </th>
                                        <th id="centrar-dato"> POR PEDIR </th>
                                        <th id="centrar-dato"> VENDEDOR </th>
                                        <th id="centrar-dato"> FECHA VENTA </th>
                                    </tr>
                                    <s:if test="listaHistoriaProducto.size()>0 && camp.CONSULTA_PARTE.length()>0">
                                    <s:iterator value="listaHistoriaProducto" status="start">
                                        <tr>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="NO_VENTA"></s:property></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="NO_PARTE"></s:property></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="STOCK_ANTERIOR"></s:property></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="PRODUCTO_VENTA"></s:property></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="STOCK_FINAL"></s:property></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:if test="CANTIDAD_TRAER>0"><div style="width: 100%; background: red; color:white;"><s:property value="CANTIDAD_TRAER"></s:property></div></s:if><s:else><s:property value="CANTIDAD_TRAER"></s:property></s:else></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="VENDEDOR"></s:property></td>
                                            <td id="centrar-dato" style="max-width: 120px;"><s:property value="FECHA_REGISTRO"></s:property></td>
                                            </tr>
                                    </s:iterator>
                                    </s:if>
                                    <s:else>
                                         <tr>
                                             <td colspan="8" align="center" id="centrar-dato" style="max-width: 120px;">No se encontro información del producto</td>
                                            
                                            </tr>
                                        
                                    </s:else>
                                </table>
                            </div>
                        </div>
                    </article>
                </div>

            </section> 

            <footer id="main-footer">
                <p>&copy; 2017 <a href="refaccionesfabela.com">Refacciones Fabela</a></p>
            </footer> <!-- / #main-footer -->
            <s:iterator value="modulosAUX" id="modulosAUX" status="stat">
                <s:hidden  name = "modulosAUX[%{#stat.index}].CVE_MODULO" id="CVE_MODULO"></s:hidden>
                <s:hidden  name = "modulosAUX[%{#stat.index}].CVE_MODPADRE" id="CVE_MODPADRE"></s:hidden>
                <s:hidden  name = "modulosAUX[%{#stat.index}].DESC_MOD" id="DESC_MOD"></s:hidden>
                <s:hidden  name = "modulosAUX[%{#stat.index}].IMAGEN" id="IMAGEN"></s:hidden>
            </s:iterator>
            <s:iterator value="modulosAUXP" id="modulosAUXP" status="stat">                        
                <s:hidden  name = "modulosAUXP[%{#stat.index}].MODULO" id="MODULO"></s:hidden>
                <s:hidden  name = "modulosAUXP[%{#stat.index}].MODPADRE" id="MODPADRE"></s:hidden>
                <s:hidden  name = "modulosAUXP[%{#stat.index}].MOD" id="MOD"></s:hidden>
                <s:hidden  name = "modulosAUXP[%{#stat.index}].ACCION" id="ACCION"></s:hidden>
            </s:iterator>
        </s:form>
    </body    
</html>

