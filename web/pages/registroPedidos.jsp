<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<SCRIPT LANGUAGE="JavaScript">
history.forward();
</SCRIPT>


<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Fecha', 'Precio al Púbico', 'Precio de provedor'],
    <s:iterator value="ListaProductoHist" id="  ListaProductoHist" status="stat" >
            ['<s:property value="FECHAINGRESO"/>', <s:property value="PRECIO" />, <s:property value="PRECIO_PESO"/>],
    </s:iterator>

        ]);

        var options = {
            title: 'Historial de ingreso de los productos',
            hAxis: {title: 'Fecha de ingreso del Producto', titleTextStyle: {color: '#333'}},
            vAxis: {minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
</script>


<script>
  function validarSiNumero(numero){
    if (!/^([0-9])*$/.test(numero))
     
     document.forma.NO_PRODUCTOS_PEDIDOS.value = "";
     
     
     
  }
</script>






<html xmlns="http://www.w3.org/1999/xhtml">


    <link href="css/menu.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    
    
    <script type="text/javascript">
    
    
    </script>
    <script type="text/javascript">

        function guarda(accion) {

            document.forma.action = accion;
            document.forma.submit();
        }
        
        
        function guarda4(accion) {

var pedidos=document.forma.NO_PRODUCTOS_PEDIDOS.value;
var proveedor2=document.forma.PROVEEDOR2.value;


        if(pedidos.length>0 && proveedor2.length>0){
            document.forma.action = accion;
            document.forma.submit();
        }

        else{
           alert("¡Los dos campos son requeridos!")
       }
        }
        
         function guarda3(accion) {

            document.forma.action = accion;
            document.forma.submit();
            alert("El producto se actualizo adecuadamente");
        }
        
         function guarda2(accion) {
             
           
            document.forma.action = accion;
            var parte = document.getElementById("CONSULTA_PARTE").value;
             var moneda = document.getElementById("MONEDA").value;
              var ganancia = document.getElementById("GANANCIA2").value;
             var precio = document.getElementById("PRECIO2").value;
              var iva = document.getElementById("IVA").value;
      var dolar = document.getElementById("DOLAR").value;

             
             if(moneda=='PESO'){
                 
                 var preciofinal=(( parseFloat (precio)*parseFloat(ganancia))+ parseFloat(precio)) + ((( parseFloat (precio)*parseFloat(ganancia))+ parseFloat(precio)))* parseFloat(iva)  ;
                preciofinal=Math.trunc(preciofinal);
          
               document.forma.SIMULADOR_PRECIO.value = preciofinal;
             }
             
             if(moneda=='USD'){
                 precio=parseFloat(precio)* parseFloat(dolar);
                var preciofinal=(( parseFloat (precio)*parseFloat(ganancia))+ parseFloat(precio)) + ((( parseFloat (precio)*parseFloat(ganancia))+ parseFloat(precio)))* parseFloat(iva)  ;
                preciofinal=Math.trunc(preciofinal);
        
             document.forma.SIMULADOR_PRECIO.value = preciofinal;
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
        
        function borrarPedidosP(accion, valor) {

            document.forma.ID_PRODUCT.value = valor;
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
            <section id="main-content">



                <div class="div-contenido_gral">  
                    <h1 class="user-sesion" align="right">USUARIO:  <s:property value="usuariocons.NAMEUSUARIO" ></s:property></h1>
                    <s:hidden name="usuariocons.NAMEUSUARIO" id="%{usuariocons.NAMEUSUARIO}"></s:hidden> 

                        <article class="articulo1" >

                            <div class="div_sec1" id="flotante-busqueda">
                                <div class="div-busqueda" >
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Busqueda</h2></div>
                                <s:textfield name="camp.CONSULTA_PARTE" id="CONSULTA_PARTE" placeholder="Número de Parte" required="true"  cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>

                                <a href="Javascript:guarda('registroPedidos')"><div class="boton">  Buscar  </div> </a>


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
                                                    <td><a href="Javascript:consulta('registroPedidos','<s:property value="NO_PARTE_ALTERNATIVO" />')"><s:property value="NO_PARTE_ALTERNATIVO  " /></a></td>
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
                        
                       
                        
                       
 <s:if test="ListaBuscarProducto.size()>0">
                        <div class="div_sec1">


                            <div class="div_titulos_sec"> <h2 class="text-tit-sec">Agregar Producto</h2></div>

                           
                                <table id="customers" style="width: 95%; margin: auto; border-radius: 20px;">


                                    <s:iterator value="ListaBuscarProductoFinal" id="ListaBuscarProductoFinal" status="stat" >
                                        <tr  style="background: #333; color: white; text-shadow: 2px 6px 15px white;">
                                            <td id="centrar-dato">Parte: <a href="#popup4" style="color:white;"> <s:property value="NO_PARTE" /></a></td>
                                            <td id="centrar-dato">Producto: <s:property value="PRODUCTO" /></td>
                                            <td id="centrar-dato">Stock: <s:property value="TOTAL_BODEGAS" /></td>
                                            <td id="centrar-dato"> Precio: <s:property value="PRECIO" /> <s:property value="MONEDA"/></td

                                        </tr>
                                        <tr >
                                            <td>Fecha: <s:property value="FECHA_ACTUALIZA"/> </td>
                                            <td><s:textfield  name="camp.NO_PRODUCTOS_PEDIDOS" id="NO_PRODUCTOS_PEDIDOS" placeholder="Número de Productos" required="true" cssClass="campoFormProducto" onKeyUp="validarSiNumero(this.value); " onChange="" />
                                            <s:fielderror fieldName="Requierecantidad" cssClass="error" /> 
                                            </td>
                                            <td id="centrar-dato"><s:select style="width:70%;" cssClass="campoFormSelect"  name="camp.PROVEEDOR" id="PROVEEDOR2" list="ListaSelectProvee" listKey="RASON_PROVEE" listValue="RASON_PROVEE" headerKey="" headerValue="Proveedor"/></td>
                                            <td id="centrar-dato"><a href="Javascript:guarda4('guardaPedidos')"><img src="img/emitido.png"/></a></td>
                                        </tr>


                                    </s:iterator>

                                </table>
                           



                        </div>
                         </s:if>
                        
                        
                        <s:if test="ListaCarritoPedidos.size()>0">
                         <div class="div_sec1">


                            <div class="div_titulos_sec"> <h2 class="text-tit-sec">Lista de compras</h2></div>
                            <s:if test="foliopedido">
                            <div class="div_titulos_sec" style="background: #333; box-shadow: none; float: right; margin-right: 30px; width: 40%;"> <h2 class="text-tit-sec" style="color:white; text-shadow:none; font-size: 12px; margin-top: 0px;">El pedido fue guardao con folio: <s:property value="camp.ID_PEDIDO"/></h2></div>
                            </s:if>

                            <div id="scroltabla-cliente">
                           
                                <table id="customers" style="width: 95%; margin: auto; border-radius: 20px;">
                                        <tr  >
                                            <th id="centrar-dato">Número de Parte</th>
                                            <th id="centrar-dato">Cantidad </th>
                                            <th id="centrar-dato">Proveedor </th>
                                             <th id="centrar-dato">Fecha de Pedido </th>
                                             <th id="centrar-dato">Eliminar </th>
                                        </tr>

                                    <s:iterator value="ListaCarritoPedidos" id="ListaCarritoPedidos" status="stat" >
                                       
                                        
                                        <tr  >
                                            <td id="centrar-dato"><s:property value="NO_PARTE" /></td>
                                            <td id="centrar-dato"><s:property value="CANTIDAD" /></td>
                                            <td id="centrar-dato"><s:property value="PROVEEDOR" /></td>
                                            <td id="centrar-dato"><s:property value="FECHA_PEDIDO" /></td>
                                            <td id="centrar-dato"><a href="Javascript:borrarPedidosP('pedidosProductosBorrar','<s:property value="ID_PRODUCT" />')"><img src="img/iconEliminar.png"/></a></td>
                                        </tr>
                                       


                                    </s:iterator>

                                </table>
                                </div>
                         
                            
                            <s:if test="banguarda">
                            <a href="Javascript:guarda('pedidoGuardar')"><div class="boton" style="margin-bottom: 10px;"> Guardar Pedido  </div> </a>
                           </s:if>
                           <s:else>
                            <a href="Javascript:guarda('registroPedidos2')"><div class="boton" style="margin-bottom: 10px;"> Nuevo Pedido  </div> </a>
                            </s:else>
                                
                                <s:url action="GeneraPdf" id="myUrl" escapeAmp="false">

                                <s:param name="TipoReporte" value="'lista.jasper'"/>
                                <s:param name="ID_PEDIDO" value="camp.ID_PEDIDO"/>
                              
                                <s:param name="esExcel" value="'false'"/>
                                <s:param name="esPDF" value="'true'"/>            

                            </s:url> 
                            <s:if test="camp.ID_PEDIDO>0">
                                <a href= "<s:property value="#myUrl" />" target="_blank"  ><div class="boton" style="float: right; margin-right: 25px; margin-top: -30px; background: #333; color: white; margin-bottom: 20px;"> Imprimir Pedido  </div></a>
                            </s:if>


                        </div>
                        
                        
                        </s:if>
                        
                         <s:if test="ListaPedidosFaltantes.size()>0">
                        <div class="div_sec1">
                            <div id="scroltabla-cliente">
                             <div class="div_titulos_sec"> <h2 class="text-tit-sec">Faltantes de Pedidos</h2></div>
                                <table id="customers" style="width: 95%; margin: auto; border-radius: 20px;">
                                        <tr  >
                                           <th id="centrar-dato">Número de Pedido</th>
                                            <th id="centrar-dato">Número de Parte</th>
                                            <th id="centrar-dato">Cantidad Pedida </th>
                                            <th id="centrar-dato">Cantidad Llegada </th>
                                            <th id="centrar-dato">Proveedor </th>
                                             <th id="centrar-dato">Fecha de Pedido </th>
                                             <th id="centrar-dato">Fecha de llegada </th>
                                        </tr>

                                    <s:iterator value="ListaPedidosFaltantes" id="ListaPedidosFaltantes" status="stat" >
                                       
                                        
                                        <tr  >
                                             <td id="centrar-dato"><s:property value="ID_PEDIDO" /></td>
                                            <td id="centrar-dato"><s:property value="NO_PARTE" /></td>
                                            <td id="centrar-dato"><s:property value="CANTIDAD" /></td>
                                            <td id="centrar-dato" style="background: #e1173e; color:white;"><s:property value="CANTIDAD_LLEGADA" /></td>
                                            <td id="centrar-dato"><s:property value="PROVEEDOR" /></td>
                                            <td id="centrar-dato"><s:property value="FECHA_PEDIDO" /></td>
                                            <td id="centrar-dato"><s:property value="FECHA_LLEGADA" /></td>
                                        </tr>
                                       


                                    </s:iterator>

                                </table>
                                </div>
                            
                            
                        </div>
                        </s:if>
                        

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
                         <s:hidden  name = "camp.ID_PEDIDO"  id="camp.ID_PEDIDO"></s:hidden>  

                        </article>
                        <!-- /article -->
                    </div>
                </section> <!-- / #main-content -->


                   <div class="modal-wrapper" id="popup4">
                    <div class="popup4-contenedor">


                        <div style="width: 100; text-align: center;"> <h2 style="font-size: 18px; margin-top: 5px; margin-bottom: 5px; w" >¿Actualización de precio de producto?</h2></div>
                        
                        
                        <div class="div_sec1">
                               
                               
                           
                           
                                <div style="margin-bottom: 40px;">
                                    <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Número de Parte</h5></div>  <div class="div-cont-campos"><div > <s:textfield  name="camp.NO_PARTE" id="camp.NO_PARTE" placeholder="Número de Parte" required="true"  cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" readonly="true"/></div></div></div>
                                    <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea1">id="errorCampo"</s:if> >Producto</h5></div>  <div class="div-cont-campos"><div ><s:textfield  name="camp.PRODUCTO" id="camp.PRODUCTO" placeholder="PRODUCTO" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" />    </div></div></div>
                                    <div class="div-dos-campos" style="display: none;"><div class="div-cont-campos" id="color-etiqueta"> <h5  <s:if test="pea2">id="errorCampo"</s:if> >Categoria General</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%; display:none;" cssClass="campoFormSelect" name="camp.CATEGORIA_GENERAL" list="ListaCategoriaGral" listKey="CATEGORIA_GENERAL" listValue="CATEGORIA_GENERAL" headerKey="" headerValue="--SELECCIONE--"  /></div></div></div>
                               <div class="div-dos-campos" style="display: none;"><div class="div-cont-campos" id="color-etiqueta"> <h5 <s:if test="pea3">id="errorCampo"</s:if> >Categoria</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%; display:none;" cssClass="campoFormSelect" name="camp.CATEGORIA" list="ListaCategoria" listKey="CATEGORIA" listValue="CATEGORIA" headerKey="" headerValue="--SELECCIONE--"  /></div></div></div>

                                <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Descripción</h5></div>  <div class="div-cont-campos"><div >  <s:textfield name="camp.DESCRIPCION" id="camp.DESCRIPCION" placeholder="DESCRIPCION" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/></div></div></div>
                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Marca</h5></div>  <div class="div-cont-campos"><div ><s:textfield name="camp.MARCA" id="camp.MARCA" placeholder="MARCA" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/> </div></div></div>
                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Unidad de Medida</h5></div>  <div class="div-cont-campos"><div ><s:textfield name="camp.UNIDADMEDIDA" id="camp.UNIDADMEDIDA" placeholder="UNIDAD DE MEDIDA" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();"/> </div></div></div>

                                 <div class="div-dos-campos"><div class="div-cont-campos" id="color-etiqueta"> <h5  >Proveedor</h5></div>  <div class="div-cont-campos"><div >  <s:select style="width:70%;" cssClass="campoFormSelect" name="camp.PROVEEDOR" list="ListaSelectProvee" listKey="RASON_PROVEE" listValue="RASON_PROVEE" headerKey="" headerValue="--SELECCIONE--"/></div></div></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 <s:if test="pea6">id="errorCampo"</s:if> style="color:white;"  >Precio</h5></div>  <div class="div-cont-campos"><div >  <s:textfield name="camp.PRECIO" id="PRECIO2" placeholder="PRECIO" required="true" cssClass="campoFormProducto" onKeyUp="this.value=this.value.toUpperCase();" onchange="Javascript:guarda2('simuladorPrecio2')" /></div></div></div>

                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 style="color:white;"  >Moneda</h5></div>  <div class="div-cont-campos"><div > <s:select style="width:70%;"  cssClass="campoFormSelect" name="camp.MONEDA" id="MONEDA" list="{'PESO','USD'}" headerValue="Seleccione una Moneda" onchange="Javascript:guarda2('simuladorPrecio2')" /> </div></div></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde"> <h5 style="color:white;"  >Ganancia</h5></div>  <div class="div-cont-campos"><div ><s:select style="width:70%;  text-align: center;" cssClass="campoFormSelect" id="GANANCIA2" name="camp.GANANCIA" list="ListaGanancia" listKey="GANANCIA" listValue="ID_GANANCIA+'%'" headerKey="" headerValue="--SELECCIONE--" onchange="Javascript:guarda2('simuladorPrecio2')"/>  </div></div></div>
                               <div class="div-dos-campos"><div class="div-cont-campos" id="color-verde" style="background: white; box-shadow: none;"><a href="Javascript:guarda3('actualizarProducto2')"> <div class="div-marea" style="margin: auto; margin-top: 20px;"><div class="div-marea-texto">         <s:textfield type="text" name="camp.SIMULADOR_PRECIO" id="SIMULADOR_PRECIO" cssStyle="width:50px;" readonly="true" ></s:textfield></div> <div class="preloader"></div></a>  </div>
                            </div>  <div class="div-cont-campos"><div > </div></div></div>

                                
                                </div>
                              
                               
                               
                              

                           </div>  
                        
                        

                      
                       

                      
                        <a class="popup4-cerrar" href="#">X</a>
                    </div>
                </div>                
                                
                             
                
                
                
                

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
                
                 <s:iterator value="ListaCarritoPedidos" id="ListaCarritoPedidos" status="stat">
                <s:hidden  name = "ListaCarritoPedidos[%{#stat.index}].NO_PARTE"  id="NO_PARTE"></s:hidden>   
                <s:hidden  name = "ListaCarritoPedidos[%{#stat.index}].CANTIDAD"  id="CANTIDAD"></s:hidden>   
                <s:hidden  name = "ListaCarritoPedidos[%{#stat.index}].FECHA_PEDIDO"  id="FECHA_PEDIDO"></s:hidden>   
                <s:hidden  name = "ListaCarritoPedidos[%{#stat.index}].PROVEEDOR"  id="PROVEEDOR"></s:hidden>
               
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

            <s:textfield type="text" name="camp.PARTEAUX" id="PARTEAUX" style='visibility:hidden'  ></s:textfield>
            <s:textfield type="text" name="camp.PRECIO_CAL" id="PRECIO_CAL" value="NO" style='visibility:hidden' ></s:textfield>
             <s:textfield type="text" name="camp.MAX_AUXPEDIDO" id="MAX_AUXPEDIDO" style='visibility:hidden'  ></s:textfield>
             <s:textfield type="text" name="camp.ID_PRODUCT" id="ID_PRODUCT" style='visibility:hidden' ></s:textfield>
       <s:textfield type="text" name="camp.IVA" id="IVA" style='visibility:hidden' ></s:textfield>
         <s:textfield type="text" name="camp.DOLAR" id="DOLAR" style='visibility:hidden' ></s:textfield>

        </s:form>


    </body    














    <!-- menu principal -->

    <!--fin mp  -->	


    <!-- inicio de pagina -->




    <!-- INICIO------------------------------------------------------>








    <!--------------------------------------------->



</html>

