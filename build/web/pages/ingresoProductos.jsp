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



<script>
  function validarSiNumero(numero){
    if (!/^([0-9])*$/.test(numero))
     
     document.forma.CANTIDAD_LLEGADA.value = "";
     
     
     
  }
</script>




<html xmlns="http://www.w3.org/1999/xhtml">


    <link href="css/menu.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">

        function guarda(accion) {

            document.forma.action = accion;
            document.forma.submit();
        }
        
        function eliminar(accion) {
            
             var parte = document.getElementById("CONSULTA_PARTE").value;
             
            
             
             if(parte.length>0){
               
                    document.forma.action = accion;
                    document.forma.submit();
              
        
             }
             else {
                 alert("No cuenta con un número de parte, favor de colocarlo");
             }

           
        }
        function actualiza(accion, valor) {

            document.forma.RFCAUX.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
        function consulta(accion, valor) {

            document.forma.CONSULTA_PARTE.value = valor;
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

        </header><!-- / #main-header -->
        <jsp:include page="/pages/menu.jsp"></jsp:include>
        <s:form name="forma" >
            <section id="main-content" style="height: 1800px;">



                <div class="div-contenido_gral" style="height: 1500px;">  
                    <h1 class="user-sesion" align="right">USUARIO:  <s:property value="usuariocons.NAMEUSUARIO" ></s:property></h1>
                    <s:hidden name="usuariocons.NAMEUSUARIO" id="%{usuariocons.NAMEUSUARIO}"></s:hidden> 

                        <article class="articulo1" >

                            <div class="div_sec1" id="flotante-busqueda">
                                <div class="div-busqueda" >
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Busqueda</h2></div>
                                <s:textfield name="camp.CONSULTA_PARTE" id="CONSULTA_PARTE" placeholder="Número de Parte" required="true"  cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>

                                <a href="Javascript:guarda('buscarProductos')"><div class="boton">  Buscar  </div> </a>


                                <!--TABLA DE UBICACIONES -->

                                <div id="scroltabla-ubicaciones">
                                    <s:if test="ListaBuscarProducto.size()>0">
                                    <table id="customers">
                                        <thead>
                                            
                                            <th>Bodega</th>
                                            <th>Cantidad</th>
                                            <th>Ubicación</th>
                                        </thead>

                                        <s:iterator value="ListaBuscarProducto" id="ListaBuscarProducto" status="stat" >
                                            <tr>
                                                
                                                <td><s:property value="NAME_BODEGA" /></td>
                                                <td id="centrar-dato"><s:property value="CATIDAD" /></td>
                                                <td id="centrar-dato"><s:property value="ANAQUEL" /><s:property value="NIVEL" /></td>
                                            </tr>
                                        </s:iterator>
                                        <s:textfield type="text" name="camp.RFCAUX" id="RFCAUX" style='visibility:hidden'  ></s:textfield> 
                                        </table>
                                    </div>
                                    </s:if>
                                    <!--TABLA DE ALTERNATIVOS -->

                              
                                    <div id="scroltabla-ubicaciones">
                               <s:if test="ListaBuscarProducto.size()>0">
                                        <table id="customers" >
                                            <thead  >
                                                <th>Alternativo</th>
                                                <th>Cantidad   </th>
                                                 <th>Precio   </th>
                                            </thead>

                                            <s:iterator value="ListaProductoAlt" id="ListaProductoAlt" status="stat" >
                                                <tr <s:if test='NO_PARTE=="NO"'>style="background: #FB6E6E; color:white;"</s:if>>
                                                    <td><a href="Javascript:consulta('buscarProductos','<s:property value="NO_PARTE_ALTERNATIVO" />')"><s:property value="NO_PARTE_ALTERNATIVO" /></a></td>
                                                <td id="centrar-dato"><s:property value="TOTAL_BODEGAS" /></td>
                                                <td id="centrar-dato"><s:property value="PRECIO" /></td>
                                            </tr>
                                        </s:iterator>
 
                                        </table>
                                </s:if>
                                    </div>
                                    


                                    <!-- <img src="img/maquina-busqueda.jpg" alt="Maquina" style="width: 90%; margin-top: 20px; margin-bottom: 20px;" ></img>-->


                                </div>
                            </div>

                        </article> 
                        <article class="articulo2">
                    <s:if test="actprod">
                           <div class="div_sec1">
                               
                           
                                <div class="div_titulos_sec"> <h2 class="text-tit-sec">Datos del Producto</h2></div>
                           
                                <div style="margin-bottom: 40px;">
                                    <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Número de Parte</h5></div>  <div class="div-cont-campos"><div > <s:textfield  name="camp.NO_PARTE" id="camp.NO_PARTE" placeholder="Número de Parte" required="true"  cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" readonly="true"/></div></div><s:fielderror fieldName="ERROR1" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                                    <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea1">id="errorCampo"</s:if> >Producto</h5></div>  <div class="div-cont-campos"><div ><s:textfield  name="camp.PRODUCTO" id="camp.PRODUCTO" placeholder="PRODUCTO" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" />    </div></div><s:fielderror fieldName="ERROR2" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  <s:if test="pea2">id="errorCampo"</s:if> >Categoria General</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.CATEGORIA_GENERAL" list="ListaCategoriaGral" listKey="CATEGORIA_GENERAL" listValue="CATEGORIA_GENERAL" headerKey="" headerValue="--SELECCIONE--"   onchange="Javascript:guarda('selectCategoria')"/></div></div> <s:fielderror fieldName="ERROR3" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />    </div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea3">id="errorCampo"</s:if> >Categoria</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.CATEGORIA" list="ListaCategoria" listKey="CATEGORIA" listValue="CATEGORIA" headerKey="" headerValue="--SELECCIONE--"/></div></div><s:fielderror fieldName="ERROR4" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto;" /> </div>
                                <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea3">id="errorCampo"</s:if> >Categoria Sat</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.CVE_SAT" list="ListaCatSat" listKey="CVE_SAT" listValue="CVE_SAT+'-'+DESCRIPCION" headerKey="" headerValue="--SELECCIONE--"/></div></div><s:fielderror fieldName="ERROR5" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                               
                                <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Descripción</h5></div>  <div class="div-cont-campos"><div >  <s:textfield name="camp.DESCRIPCION" id="camp.DESCRIPCION" placeholder="DESCRIPCION" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/></div></div><s:fielderror fieldName="ERROR6" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Marca</h5></div>  <div class="div-cont-campos"><div ><s:textfield name="camp.MARCA" id="camp.MARCA" placeholder="MARCA" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/> </div></div><s:fielderror fieldName="ERROR7" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Unidad de Medida</h5></div>  <div class="div-cont-campos"><div ><s:textfield name="camp.UNIDADMEDIDA" id="camp.UNIDADMEDIDA" placeholder="UNIDAD DE MEDIDA" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/> </div></div><s:fielderror fieldName="ERROR8" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>

                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Proveedor</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.PROVEEDOR" list="ListaSelectProvee" listKey="RASON_PROVEE" listValue="RASON_PROVEE" headerKey="" headerValue="--SELECCIONE--"/></div></div><s:fielderror fieldName="ERROR9" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 <s:if test="pea6">id="errorCampo"</s:if> style="color:white;"  >Precio</h5></div>  <div class="div-cont-campos"><div >  <s:textfield name="camp.PRECIO" id="camp.PRECIO" placeholder="PRECIO" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" onchange="Javascript:guarda('simuladorPrecio')" /></div></div><s:fielderror fieldName="ERROR10" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>

                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 style="color:white;"  >Moneda</h5></div>  <div class="div-cont-campos"><div > <s:select style="width:70%;"  cssClass="campoFormSelect" name="camp.MONEDA" id="camp.MONEDA" list="{'PESO','USD'}" headerValue="Seleccione una Moneda" onchange="Javascript:guarda('simuladorPrecio')" /> </div></div><s:fielderror fieldName="ERROR11" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" />  </div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 style="color:white;"  >Ganancia</h5></div>  <div class="div-cont-campos"><div ><s:select style="width:70%;  text-align: center;" cssClass="campoFormSelect" name="camp.GANANCIA" list="ListaGanancia" listKey="GANANCIA" listValue="ID_GANANCIA+'%'" headerKey="" headerValue="--SELECCIONE--" onchange="Javascript:guarda('simuladorPrecio')"/>  </div></div></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde" style="background: white; box-shadow: none;"><a href="Javascript:guarda('actualizarProducto')"> <div class="div-marea" style="margin: auto; margin-top: 20px;"><div class="div-marea-texto"><s:property value="camp.SIMULADOR_PRECIO"/></div> <div class="preloader"></div></a>   </div>
                            </div>  <div class="div-cont-campos"><div > </div></div></div>

                                
                            
           <div class="boton" style="margin-top: 50px; background: red; color:white;"> <a href="#popup3"> Eliminar </a>  </div>

           <div style="width: 100%; text-align: center;">           <s:fielderror fieldName="ELIMINADO" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto;" /></div>
           
           
           
                                </div>
                              
                                  <div style="width:100%; text-align: center; color: green; margin-top: 20px;"><s:fielderror fieldName="ACTUALIZADO" cssStyle="text-aling:center; font-size:14px ; color: green; margin:auto;" /></div>
                         
                               
                              

                           </div>  
           
              


                       
                        </s:if>
                           
                            
                            
                            
                             <s:if test="regprod">
                           <div class="div_sec1">
                               
                               
                           
                                <div class="div_titulos_sec"> <h2 class="text-tit-sec">Registro de Producto</h2></div>
                           
                                <div style="margin-bottom: 40px;">
                                <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Número de Parte</h5></div>  <div class="div-cont-campos"><div > <s:textfield  name="camp.NO_PARTE" id="camp.NO_PARTE" placeholder="Número de Parte" required="true"  cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" readonly="true"/></div></div><s:fielderror fieldName="ERROR1" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea1">id="errorCampo"</s:if> >Producto</h5></div>  <div class="div-cont-campos"><div ><s:textfield  name="camp.PRODUCTO" id="camp.PRODUCTO" placeholder="PRODUCTO" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" />    </div></div><s:fielderror fieldName="ERROR2" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  <s:if test="pea2">id="errorCampo"</s:if> >Categoria General</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.CATEGORIA_GENERAL" list="ListaCategoriaGral" listKey="CATEGORIA_GENERAL" listValue="CATEGORIA_GENERAL" headerKey="" headerValue="--SELECCIONE--"   onchange="Javascript:guarda('selectCategoria')"/></div></div><s:fielderror fieldName="ERROR3" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea3">id="errorCampo"</s:if> >Categoria</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.CATEGORIA" list="ListaCategoria" listKey="CATEGORIA" listValue="CATEGORIA" headerKey="" headerValue="--SELECCIONE--"/></div></div><s:fielderror fieldName="ERROR4" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea3">id="errorCampo"</s:if> >Categoria Sat</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.CVE_SAT" list="ListaCatSat" listKey="CVE_SAT" listValue="CVE_SAT+'-'+DESCRIPCION" headerKey="" headerValue="--SELECCIONE--"/></div></div><s:fielderror fieldName="ERROR5" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>

                                <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Descripción</h5></div>  <div class="div-cont-campos"><div >  <s:textfield name="camp.DESCRIPCION" id="camp.DESCRIPCION" placeholder="DESCRIPCION" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/></div></div><s:fielderror fieldName="ERROR6" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Marca</h5></div>  <div class="div-cont-campos"><div ><s:textfield name="camp.MARCA" id="camp.MARCA" placeholder="MARCA" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/> </div></div><s:fielderror fieldName="ERROR7" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Unidad de Medida</h5></div>  <div class="div-cont-campos"><div ><s:textfield name="camp.UNIDADMEDIDA" id="camp.UNIDADMEDIDA" placeholder="UNIDAD DE MEDIDA" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/> </div></div><s:fielderror fieldName="ERROR8" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>

                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Proveedor</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.PROVEEDOR" list="ListaSelectProvee" listKey="RASON_PROVEE" listValue="RASON_PROVEE" headerKey="" headerValue="--SELECCIONE--"/></div></div><s:fielderror fieldName="ERROR9" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 <s:if test="pea6">id="errorCampo"</s:if> style="color:white;"  >Precio</h5></div>  <div class="div-cont-campos"><div >  <s:textfield name="camp.PRECIO" id="camp.PRECIO" placeholder="PRECIO" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" onchange="Javascript:guarda('simuladorPrecio')" /></div></div><s:fielderror fieldName="ERROR10" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>

                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 style="color:white;"  >Moneda</h5></div>  <div class="div-cont-campos"><div > <s:select style="width:70%;"  cssClass="campoFormSelect" name="camp.MONEDA" id="camp.MONEDA" list="{'PESO','USD'}" headerValue="Seleccione una Moneda" onchange="Javascript:guarda('simuladorPrecio')" /> </div></div><s:fielderror fieldName="ERROR11" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 style="color:white;"  >Ganancia</h5></div>  <div class="div-cont-campos"><div ><s:select style="width:70%;  text-align: center;" cssClass="campoFormSelect" name="camp.GANANCIA" list="ListaGanancia" listKey="GANANCIA" listValue="ID_GANANCIA+'%'" headerKey="" headerValue="--SELECCIONE--" onchange="Javascript:guarda('simuladorPrecio')"/>  </div></div><s:fielderror fieldName="ERROR12" cssStyle="text-aling:center; font-size:14px ; color: red; margin:auto; text-aling:center;" /></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde" style="background: white; box-shadow: none;"><a href="Javascript:guarda('guardaProducto')"> <div class="div-marea" style="margin: auto; margin-top: 20px;"><div class="div-marea-texto"><s:property value="camp.SIMULADOR_PRECIO"/></div> <div class="preloader"></div></a>  </div>
                            </div>  <div class="div-cont-campos"><div > </div></div></div>

                                
                                </div>
                              
                               
                                 <div style="width:100%; text-align: center; color: green; margin-top: 20px;"><s:fielderror fieldName="ACTUALIZADO" cssStyle="text-aling:center; font-size:14px ; color: green; margin:auto;" /></div>
                         
                              

                           </div>  

                       
                        </s:if>
                           
                            
                            
                            
                            

                       
                            <s:if test="ListaProductoHist.size()>0">
                                <div class="div_sec1" style="height: 400px;">

                            <div class="div_titulos_sec"> <h2 class="text-tit-sec">Historial de precio del Producto </h2></div>
                            <div class="div_form_cliente">
                                <div id="scroltabla-cliente" style="overflow-y:  scroll; height: 100px;">
                                    
                            <div id="chart_div" style="width: 95%; height: 100px; margin-top: 20px;"></div>

                                    
                                    <div class="contenedor-tab-hist-prog">
                                    <table id="customers">
                                        <thead>
                                            <th>Parte</th>
                                            <th>Producto</th>
                                            <th>Precio</th>
                                            <th>Moneda</th>
                                            <th>Precio Público</th>
                                            <th>Proveedor</th>
                                            <th>Fecha de ingreso</th>
                                            
                                            
                                        </thead>

                                        <s:iterator value="ListaProductoHist" id="  ListaProductoHist" status="stat" >
                                              <tr>
                                                  <td><a href="Javascript:consulta('buscarProductos','<s:property value="NO_PARTE" />')"> <s:property value="NO_PARTE" /></a> </td>
                                                <td><s:property value="PRODUCTO" /></td>
                                                <td id="centrar-dato"><s:property value="PRECIO" /></td>
                                                <td id="centrar-dato"><s:property value="MONEDA" /></td>
                                                 <td id="centrar-dato"><s:property value="PRECIO_PESO" /></td>
                                                 <td id="centrar-dato"><s:property value="PROVEEDOR" /></td>
                                                    <td id="centrar-dato"><s:property value="FECHAINGRESO" /></td>
                                            

                                            </tr>
                                          

                                        </s:iterator>



                                        </table>
                                        </div>
                                    

                                    </div>

                                </div>




                            </div>

                            </s:if>
                            
                            
                           <!--historia de ingreso de productos --> 
                            
                            <s:if test="ListaHistoriaBodegas.size()>0">
                                <div class="div_sec1" style="height: 400px;">

                            <div class="div_titulos_sec"> <h2 class="text-tit-sec">Historial de ingreso a bodegas del Producto </h2></div>
                            <div class="div_form_cliente">
                                <div id="scroltabla-cliente" style="overflow-y:  scroll; height: 100px;">
                         
                                    
                                    <div class="contenedor-tab-hist-prog">
                                    <table id="customers">
                                        <thead>
                                            <th>Parte</th>
                                            <th>Cantidad</th>
                                            <th>Bodega</th>
                                            <th>Anaquel</th>
                                            <th>Nivel</th>
                                            <th>Fecha de Ingreso</th>
                                           
                                            
                                            
                                        </thead>

                                        <s:iterator value="ListaHistoriaBodegas" id="  ListaHistoriaBodegas" status="stat" >
                                              <tr>
                                                  <td id="centrar-dato" > <s:property value="NO_PARTE" /> </td>
                                                <td id="centrar-dato"><s:property value="CANTIDAD" /></td>
                                                <td id="centrar-dato"><s:property value="BODEGA" /></td>
                                                <td id="centrar-dato"><s:property value="ANAQUEL" /></td>
                                                 <td id="centrar-dato"><s:property value="NIVEL" /></td>
                                                 <td id="centrar-dato"><s:property value="FECHA_INGRESO" /></td>
                                                   
                                            </tr>
                                          

                                        </s:iterator>



                                        </table>
                                        </div>
                                    

                                    </div>

                                </div>




                            </div>

                            </s:if>
                    </div>

                              <div class="modal-wrapper" id="popup3">
                    <div class="popup3-contenedor">


                        <div style="width: 100; text-align: center;"> <h2 style="font-size: 18px; margin-top: 40px; margin-bottom: 30px;" >¿Desea eliminar definitivamente el producto?</h2></div>
                        
                        <div style="text-align: center;"> <s:password name="camp.AUTORIZACION" id ="AUTORIZACION" cssClass="campoFormProducto" placeholder="Contraseña"></s:password></div>
                        

                        <div style="width: 100%; margin-top: 20px;"> <a href="Javascript:eliminar('eliminarProducto')" onclick="this.onclick=function(){return false}"><div style="width: 120px; height: 30px; background: green; color: white; text-align: center; font-size: 16px; margin: auto; border-radius: 8px;">OK</div></a></div>
                            
                        

                      
                        <a class="popup3-cerrar" href="#">X</a>
                    </div>
                </div>      


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
           <s:iterator value="ListaBuscarProducto" id="ListaBuscarProducto" status="stat">
              <s:hidden  name = "ListaBuscarProducto[%{#stat.index}].NAME_BODEGA"  id="NAME_BODEGA"></s:hidden>   
              <s:hidden  name = "ListaBuscarProducto[%{#stat.index}].CATIDAD"  id="CATIDAD"></s:hidden>   
                   <s:hidden  name = "ListaBuscarProducto[%{#stat.index}].ANANQUEL"  id="ANAQUEL"></s:hidden>   
                    <s:hidden  name = "ListaBuscarProducto[%{#stat.index}].NIVEL"  id="NIVEL"></s:hidden>
            </s:iterator> 
                
                
              <s:iterator value="ListaProductoAlt" id="ListaProductoAlt" status="stat">
              <s:hidden  name = "ListaProductoAlt[%{#stat.index}].NO_PARTE"  id="NO_PARTE"></s:hidden>   
              <s:hidden  name = "ListaProductoAlt[%{#stat.index}].NO_PARTE_ALTERNATIVO"  id="NO_PARTE_ALTERNATIVO"></s:hidden>   
                   <s:hidden  name = "ListaProductoAlt[%{#stat.index}].TOTAL_BODEGAS"  id="TOTAL_BODEGAS"></s:hidden>   
                    <s:hidden  name = "ListaProductoAlt[%{#stat.index}].PRECIO"  id="PRECIO"></s:hidden>
            </s:iterator>    
                
         <s:iterator value="ListaProductosGral" id="ListaProductosGral" status="stat">
              <s:hidden  name = "ListaProductosGral[%{#stat.index}].NO_PARTE"  id="NO_PARTE"></s:hidden>   
              <s:hidden  name = "ListaProductosGral[%{#stat.index}].ALTERNATIVO"  id="ALTERNATIVO"></s:hidden>   
                   <s:hidden  name = "ListaProductosGral[%{#stat.index}].TOTAL_BODEGAS"  id="TOTAL_BODEGAS"></s:hidden>   
                    <s:hidden  name = "ListaProductosGral[%{#stat.index}].PRECIO"  id="PRECIO"></s:hidden>
                    <s:hidden  name = "ListaProductosGral[%{#stat.index}].PRODUCTO"  id="PRODUCTO"></s:hidden>
            </s:iterator>     
                
                <s:iterator value="ListaProductoHist" id="ListaProductoHist" status="stat">
              <s:hidden  name = "ListaProductoHist[%{#stat.index}].NO_PARTE"  id="NO_PARTE"></s:hidden>   
              <s:hidden  name = "ListaProductoHist[%{#stat.index}].PRODUCTO"  id="PRODUCTO"></s:hidden>   
                 
                    <s:hidden  name = "ListaProductoHist[%{#stat.index}].PRECIO"  id="PRECIO"></s:hidden>
                    <s:hidden  name = "ListaProductoHist[%{#stat.index}].PRECIO_PESO"  id="PRECIO_PESO"></s:hidden>
                    <s:hidden  name = "ListaProductoHist[%{#stat.index}].MONEDA"  id="MONEDA"></s:hidden>
                    <s:hidden  name = "ListaProductoHist[%{#stat.index}].PROVEEDOR"  id="PROVEEDOR"></s:hidden>
                    <s:hidden  name = "ListaProductoHist[%{#stat.index}].FECHAINGRESO"  id="FECHAINGRESO"></s:hidden>
            </s:iterator>  
                
                  <s:iterator value="ListaHistoriaBodegas" id="ListaHistoriaBodegas" status="stat">
              <s:hidden  name = "ListaHistoriaBodegas[%{#stat.index}].NO_PARTE"  id="NO_PARTE"></s:hidden>   
              <s:hidden  name = "ListaHistoriaBodegas[%{#stat.index}].CANTIDAD"  id="CANTIDAD"></s:hidden>   
                   <s:hidden  name = "ListaHistoriaBodegas[%{#stat.index}].BODEGA"  id="BODEGA"></s:hidden>   
                    <s:hidden  name = "ListaHistoriaBodegas[%{#stat.index}].ANAQUEL"  id="ANAQUEL"></s:hidden>
                    <s:hidden  name = "ListaHistoriaBodegas[%{#stat.index}].NIVEL"  id="NIVEL"></s:hidden>
                    <s:hidden  name = "ListaHistoriaBodegas[%{#stat.index}].FECHA_INGRESO"  id="FECHA_INGRESO"></s:hidden>
            </s:iterator>    
                
                 <s:iterator value="ListaCatSat" id="ListaCatSat" status="stat">
                                <s:hidden  name = "ListaCatSat[%{#stat.index}].CVE_SAT"  id="CVE_SAT"></s:hidden>   
                                 <s:hidden  name = "ListaCatSat[%{#stat.index}].DESCRIPCION"  id="DESCRIPCION"></s:hidden>   
                               
                                 </s:iterator> 
                
                
                
                 <s:iterator value="ListaGanancia" id="ListaGanancia" status="stat">
                                <s:hidden  name = "ListaGanancia[%{#stat.index}].ID_GANANCIA"  id="ID_GANANCIA"></s:hidden>                                    
                                <s:hidden  name = "ListaGanancia[%{#stat.index}].GANANCIA"  id="GANANCIA"></s:hidden> 
                                 </s:iterator> 
                               
                               
                                <s:iterator value="ListaCategoriaGral" id="ListaCategoriaGral" status="stat">
                                <s:hidden  name = "ListaCategoriaGral[%{#stat.index}].CATEGORIA_GENERAL"  id="CATEGORIA_GENERAL"></s:hidden>                                    
                               
                                 </s:iterator> 
 
                                  <s:iterator value="ListaCategoria" id="ListaCategoria" status="stat">
                                <s:hidden  name = "ListaCategoria[%{#stat.index}].CATEGORIA"  id="CATEGORIA"></s:hidden>                                    
                               
                                 </s:iterator> 
 
 
                               
                               <s:iterator value="ListaSelectProvee" id="ListaSelectProvee" status="stat">
                                <s:hidden  name = "ListaSelectProvee[%{#stat.index}].RFC_PROVEE"  id="RFC_PROVEE"></s:hidden>   
                                 <s:hidden  name = "ListaSelectProvee[%{#stat.index}].RASON_PROVEE"  id="RASON_PROVEE"></s:hidden>   
                               
                                 </s:iterator> 
                            
                            
                            
                            
                            
                                
                            <s:hidden  name = "regprod"  id="regprod"></s:hidden>  
                            <s:hidden  name = "actprod"  id="actprod"></s:hidden>  
                
                
                
                
<s:textfield type="text" name="camp.PARTEAUX" id="PARTEAUX" style='visibility:hidden'  ></s:textfield>
<s:textfield type="text" name="camp.PRECIO_CAL" id="PRECIO_CAL" value="NO" style='visibility:hidden' ></s:textfield>

        </s:form>


    </body    














    <!-- menu principal -->

    <!--fin mp  -->	


    <!-- inicio de pagina -->




    <!-- INICIO------------------------------------------------------>








    <!--------------------------------------------->



</html>

