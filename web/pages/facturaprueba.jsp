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
        
         function guardado() {

         alert("Cliente gurdado");
        }
         function actualiza(accion, valor) {
                
                    document.forma.RFCAUX.value = valor;
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
                            <div class="div-busqueda">
                                 <div class="div_titulos_sec"> <h2 class="text-tit-sec">Busqueda</h2></div>
                            <s:textfield name="camp.BUSCARCLIENTE" id="camp.BUSCARCLIENTE" placeholder="RFC ó Nombre ó Empresa" required="true"  cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>
                            
                              <a href="Javascript:guarda('facturaVenta')"><div class="boton">  Factura  </div> </a>
                              
                              <img src="img/maquina-busqueda.jpg" alt="Maquina" style="width: 90%; margin-top: 20px; margin-bottom: 20px;" ></img>
                            </div>



                        </div>

                    </article> 

                    <article class="articulo2">





                        <div class="div_sec1">
                            
                                <a href="Javascript:guarda('clientesGuardar')" onclick="guardado()"><div class="boton">  Ingresar   </div> </a>
                           
                        </div>




                    </div>


                            <div class="div_sec1">


                                <div class="div_form_cliente">
                                    <div id="scroltabla-cliente">
                                        <table id="customers">
                                            <thead>
                                                <th>NP</th>
                                                <th>RFC</th>
                                                <th>Nombre del Cliente</th>
                                                <th>Empresa</th>

                                                <th>Modificar</th>
                                                <th>Eliminar</th>
                                            </thead>

                                            <s:iterator value="ListaClientes" id="ListaClientes" status="stat" >
                                                <tr>
                                                     <td id="centrar-dato"><s:property value="#stat.count" /></td>
                                                    <td><s:property value="RFC_CLIENT" /></td>
                                                    <td><s:property value="NOMBRE_CLIENT" /></td>
                                                    <td><s:property value="EMPRESA" /></td>

                                                    <td style="min-width: 50px; max-width: 50px;"><div class="img-pos-eliact"><a href="Javascript:actualiza('llanacliente','<s:property value="RFC_CLIENT" />')"><img src="img/iconModificar.png" alt="Modificar"></img></a></div> </td>
                                                    <td style="min-width: 50px; max-width: 50px;">  <div class="img-pos-eliact" ><a href="Javascript:actualiza('clientesBorrar','<s:property value="RFC_CLIENT" />')"><img src="img/iconEliminar.png" alt="Eliminar"></img></a></div></td>


                                                </tr>

                                            </s:iterator>
                                            <s:textfield type="text" name="camp.RFCAUX" id="RFCAUX" style='visibility:hidden'  ></s:textfield> 



                                        </table>

                                    </div>

                                </div>




                            </div>

                           
                        </div>

                           


                    </article><!-- /article -->
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
                 <s:iterator value="ListaClientes" id="ListaClientes" status="stat">                        
                <s:hidden  name = "ListaClientes[%{#stat.index}].RFC_CLIENT" id="RFC_CLIENT"></s:hidden>
                <s:hidden  name = "ListaClientes[%{#stat.index}].NOMBRE_CLIENT" id="NOMBRE_CLIENT"></s:hidden>
                <s:hidden  name = "ListaClientes[%{#stat.index}].EMPRESA" id="EMPRESA"></s:hidden>
               
            </s:iterator>
 
          
           
                
                
                
                
                
                
                
            </s:form>


    </body    














    <!-- menu principal -->

    <!--fin mp  -->	


    <!-- inicio de pagina -->




    <!-- INICIO------------------------------------------------------>








    <!--------------------------------------------->



</html>

