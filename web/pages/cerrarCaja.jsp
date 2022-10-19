<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<SCRIPT LANGUAGE="JavaScript">
history.forward();
</SCRIPT>


<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Fecha', 'Precio al Púbico', 'Precio de provedor'],
    <s:iterator value="ListaProductoHist" id="  ListaProductoHist" status="stat" >
                ['<s:property value="FECHAINGRESO"/>',  <s:property value="PRECIO" />,    <s:property value="PRECIO_PESO"/>],
    </s:iterator>
         
        ]);

        var options = {
          title: 'Historial de ingreso de los productos',
          hAxis: {title: 'Fecha de ingreso del Producto',  titleTextStyle: {color: '#333'}},
          vAxis: {minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script>
 $.datepicker.regional['es'] = {
 closeText: 'Cerrar',
 prevText: '<Ant',
 nextText: 'Sig>',
 currentText: 'Hoy',
 monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
 monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
 dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
 dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
 dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
 weekHeader: 'Sm',
 dateFormat: 'dd-mm-yy',
 changeMonth:true,
 changeYear:true,
 firstDay: 1,
 isRTL: false,
 showMonthAfterYear: false,
 yearSuffix: ''
 };
 $.datepicker.setDefaults($.datepicker.regional['es']);
$(function () {
$("#Fecha").datepicker();
});

$(function () {
$("#Fecha1").datepicker();
});
</script>



<html xmlns="http://www.w3.org/1999/xhtml">


    <link href="css/menu.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">

        function guarda(accion) {

            document.forma.action = accion;
            document.forma.submit();
        }
        function actualiza(accion, valor) {

            document.forma.RFCAUX.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
         function borrarProducto(accion, valor) {

            document.forma.BORRARPRODUCTO.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
        function consulta(accion, valor) {

            document.forma.NO_VENTA.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
        function consultaCotiza(accion, valor) {

            document.forma.AUXCOTIZA.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
     function consultaCliente(accion, valor, valor2) {

            document.forma.AUX_RFC_CLIENTE.value = valor;
             document.forma.RFCCLIENTE.value = valor2;
            document.forma.action = accion;
            document.forma.target = "_self";
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
                                <div class="div-busqueda">
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Busqueda</h2></div>
                                <s:textfield name="camp.FECHA_INICIO" id="Fecha"  placeholder="Fecha de Inicial" required="true" readonly="true" cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>
                                <s:textfield name="camp.FECHA_FINAL" id="Fecha1"  placeholder="Fecha de Final" required="true" readonly="true" cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>
                                <s:url action="GeneraPdf" id="myUrl" escapeAmp="false">
                                    <s:param name="TipoReporte" value="'CORTEDECAJA.jasper'"/>
                                    <s:param name="FECHA_INICIO" value="camp.FECHA_INICIO"/>
                                    <s:param name="FECHA_FINAL" value="camp.FECHA_FINAL"/>
                                    <s:param name="esExcel" value="'false'"/>
                                    <s:param name="esPDF" value="'true'"/>            
                                </s:url> 
                                <a href="Javascript:guarda('consultaVentaCaja')"><div class="boton">  Buscar  </div> </a>

                                <img src="img/maquina-busqueda.jpg" alt="Maquina" style="width: 90%; margin-top: 20px; margin-bottom: 20px;" ></img>
                            </div>
                        </div>
                    </article> 
                                                              
                    <article class="articulo2">
                        <s:if test="ListaVentaDia.size()>0">
                            <div class="div_sec1">
                                <div class="div_titulos_sec"> <h2 class="text-tit-sec">Lista de Ventas del Día </h2></div>
                                <div class="div_form_cliente">
                                    <div id="scroltabla-cliente">
                                        <div class="contenedor-tab-hist-prog">
                                          
                                            <table id="customers">
                                                <thead>
                                                    <th>Número de Venta</th>
                                                    <th>No de Productos</th>
                                                    <th>Total de la partida</th>
                                                </thead>
                                                <s:iterator value="ListaVentaDia" id="  ListaVentaDia" status="stat" >
                                                    <tr>
                                                        <td id="centrar-dato"><a href="Javascript:consulta('cerrarCajaDetalle','<s:property value="NO_VENTA" />')"> <s:property value="NO_VENTA" /></a> </td>
                                                        <td id="centrar-dato"><s:property value="NO_PRODUCTOVENTA" /></td>
                                                        <td id="centrar-dato" style="background: green; color:white;"><s:property value="PRECIO_FINAL" /></td>
                                                    </tr>
                                                </s:iterator>
                                                <tr>
                                                    <td id="centrar-dato"> </td>
                                                    <td id="centrar-dato"></td>
                                                    <td id="centrar-dato" style="background: purple; color:white; "><s:property value="camp.TOTAL_COTIZACION"  /></td>
                                                    <td  id="centrar-dato"> </td>
                                                </tr>
                                            </table>
                                        </div>
                                        <div style="width: 200px; height: 25px; background: purple; color: white; float: right; margin-right: 120px; text-align: center; margin-top: 10px; font-size: 14px;">Total: <s:property value="camp.TOTAL_COTIZACION" /> MXN</div>
                                        <td id="centrar-dato">  <a href="<s:property value="#myUrl" />" target="_blank"><img src="img/descarga.png" style="width: 50px; margin-top: 5px;"></img> </a></td>
                                    </div>
                                </div>
                            </div>
                        </s:if>                         
                    </article>
                    <!-- /article -->
                </div>
            </section> <!-- / #main-content -->
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
            <s:textfield type="text" name="camp.PARTEAUX" id="PARTEAUX" style='visibility:hidden'  ></s:textfield>

         
          
            </s:form>
    </body    
</html>

