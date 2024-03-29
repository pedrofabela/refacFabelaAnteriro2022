/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import beans.FacturaBean;
import beans.Navegacion;
import beans.camposConBean;
import beans.clientesBean;
import beans.moduloAuxBean;
import beans.moduloBean;
import beans.precioBean;
import beans.productosBean;
import beans.proveedoresBean;
import beans.usuarioBean;
import business.AccesoBusiness;
import business.ConsultaBusiness;
import business.FacturaService;
import com.opensymphony.xwork2.ActionSupport;
import daos.FacturaServiceImpl;
import daos.TimbrarXml;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import utilidades.Constantes;

/**
 *
 * @author pedro
 */
public class Consultas_Action extends ActionSupport implements SessionAware {

    private usuarioBean usuariocons;
    private String cveusuario;
    private String pasusuario;
    private String nomModulo;
    private String modulo;
    private String nombreUsuario;
    private String BUSCARCLIENTE;

    //LISTAS PERSISTENTES DEL MENU
    public List<moduloBean> modulosAUX = new ArrayList<moduloBean>();
    public List<moduloAuxBean> modulosAUXP = new ArrayList<moduloAuxBean>();

    //Clientes
    public List<clientesBean> ListaClientes = new ArrayList<clientesBean>();
    public List<proveedoresBean> Listaproveedores = new ArrayList<proveedoresBean>();
    public List<usuarioBean> Listausuarios = new ArrayList<usuarioBean>();

    // LISTAS DE COMPRAS
    public List<productosBean> ListaProductosGral = new ArrayList<productosBean>();
    public List<productosBean> ListaBuscarProducto = new ArrayList<productosBean>();
    public List<productosBean> ListaBuscarProductoLike = new ArrayList<productosBean>();
    public List<productosBean> ListaBuscarProductoFinal = new ArrayList<productosBean>();
    public List<productosBean> ListaProductoAlt = new ArrayList<productosBean>();
    public List<productosBean> ListaGanancia = new ArrayList<productosBean>();
    public List<camposConBean> ListaCategoriaGral = new ArrayList<camposConBean>();
    public List<productosBean> ListaCategoria = new ArrayList<productosBean>();
    public List<productosBean> ListaSelectProvee = new ArrayList<productosBean>();
    public List<productosBean> ListaProductoHist = new ArrayList<productosBean>();
    public List<productosBean> ListaContadoresPedidos = new ArrayList<productosBean>();
    public List<productosBean> ListaCarritoPedidos = new ArrayList<productosBean>();
    public List<productosBean> ListaPedidosPendientes = new ArrayList<productosBean>();
    public List<productosBean> ListaPedidosBuscar = new ArrayList<productosBean>();
    public List<productosBean> ListaPedidosFaltantes = new ArrayList<productosBean>();
    public List<productosBean> ListaActEntPedidos = new ArrayList<productosBean>();
    public List<productosBean> ListaAnaquel = new ArrayList<productosBean>();
    public List<productosBean> ListaNivel = new ArrayList<productosBean>();
    public List<productosBean> ListaConsultaBodega = new ArrayList<productosBean>();
    public List<productosBean> ListaInventario = new ArrayList<productosBean>();
    public List<productosBean> ListaCarroCotizacion = new ArrayList<productosBean>();
    public List<productosBean> ListaTraerProducto = new ArrayList<productosBean>();
    public List<productosBean> ListaVentaProducto = new ArrayList<productosBean>();
    public List<productosBean> ListaStok = new ArrayList<productosBean>();
    public List<productosBean> ListaCotizaHist = new ArrayList<productosBean>();
    public List<productosBean> ListaAlternativos = new ArrayList<productosBean>();
    public List<productosBean> ListaVentaDia = new ArrayList<productosBean>();
    public List<productosBean> ListaValorLlegada = new ArrayList<productosBean>();
    public List<productosBean> ListaHistoriaBodegas = new ArrayList<productosBean>();
    public List<productosBean> ListaCatSat = new ArrayList<productosBean>();
    public List<camposConBean> listausocfdi = new ArrayList<camposConBean>();
    public List<camposConBean> listaformapago = new ArrayList<camposConBean>();
    public List<camposConBean> listaCreditos = new ArrayList<camposConBean>();
    public List<camposConBean> listaCreditosNota = new ArrayList<camposConBean>();
    public List<camposConBean> listaCreditosNotaAbonos = new ArrayList<camposConBean>();
    
     public List<camposConBean> listaHistoriaProducto = new ArrayList<camposConBean>();
    private FacturaService facturaService;

    public Consultas_Action() throws Exception {
        this.facturaService = new FacturaServiceImpl();
    }

    Statement objConexion;
    PreparedStatement objPreConexion;
    Connection conecta;

    //BANDERAS EN GENERAL
    public boolean banCliente;
    public boolean banClienteBusqueda;
    public boolean banClienteActualiza;
    public String RFCAUX;

    public boolean regprod;
    public boolean actprod;
    public boolean actprod2;
    public boolean foliopedido = false;

    public boolean banguarda = true;

    //CONSTRUCTORES DE BEAN 
    camposConBean camp = new camposConBean();
    clientesBean respon = new clientesBean();
    precioBean precioUniPartida = new precioBean();

    //SESSIN USUARIO	
    //BANDERAS ERROS
    boolean pea1 = false;
    boolean pea2 = false;
    boolean pea3 = false;
    boolean pea4 = false;
    boolean pea5 = false;
    boolean pea6 = false;
    boolean pea7 = false;
    boolean pea8 = false;
    boolean pea9 = false;
    boolean pea10 = false;

    boolean nop = false;
    boolean cat = false;
    boolean catg = false;
    boolean prove = false;
    boolean prec = false;
    boolean botonAct = true;

    boolean traspaso = false;

    boolean banfacturasi = false;
    boolean banfacturano = false;
    boolean bancrednota = false;
    boolean bancrednotaAbonos = false;

    // private Map session  = ActionContext.getContext().getSession();
    private String nivelUsuario;

    //Exception
    private String TipoError;
    private String TipoException;

    /* VARIABLES DE IVA Y DOLAR+++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    float iva = 0;
    float dolar = 0;
    float dolarcalcula = 0;
    float ivacalcula = 0;

    public List<camposConBean> getListaHistoriaProducto() {
        return listaHistoriaProducto;
    }

    public void setListaHistoriaProducto(List<camposConBean> listaHistoriaProducto) {
        this.listaHistoriaProducto = listaHistoriaProducto;
    }
    
    

    public precioBean getPrecioUniPartida() {
        return precioUniPartida;
    }

    public void setPrecioUniPartida(precioBean precioUniPartida) {
        this.precioUniPartida = precioUniPartida;
    }

    public List<camposConBean> getListaCreditosNotaAbonos() {
        return listaCreditosNotaAbonos;
    }

    public void setListaCreditosNotaAbonos(List<camposConBean> listaCreditosNotaAbonos) {
        this.listaCreditosNotaAbonos = listaCreditosNotaAbonos;
    }

    public boolean isBancrednotaAbonos() {
        return bancrednotaAbonos;
    }

    public void setBancrednotaAbonos(boolean bancrednotaAbonos) {
        this.bancrednotaAbonos = bancrednotaAbonos;
    }

    public boolean isBancrednota() {
        return bancrednota;
    }

    public void setBancrednota(boolean bancrednota) {
        this.bancrednota = bancrednota;
    }

    public List<camposConBean> getListaCreditos() {
        return listaCreditos;
    }

    public void setListaCreditos(List<camposConBean> listaCreditos) {
        this.listaCreditos = listaCreditos;
    }

    public List<camposConBean> getListaCreditosNota() {
        return listaCreditosNota;
    }

    public void setListaCreditosNota(List<camposConBean> listaCreditosNota) {
        this.listaCreditosNota = listaCreditosNota;
    }

    public FacturaService getFacturaService() {
        return facturaService;
    }

    public void setFacturaService(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    public List<productosBean> getListaCatSat() {
        return ListaCatSat;
    }

    public void setListaCatSat(List<productosBean> ListaCatSat) {
        this.ListaCatSat = ListaCatSat;
    }

    public List<camposConBean> getListausocfdi() {
        return listausocfdi;
    }

    public void setListausocfdi(List<camposConBean> listausocfdi) {
        this.listausocfdi = listausocfdi;
    }

    public List<camposConBean> getListaformapago() {
        return listaformapago;
    }

    public void setListaformapago(List<camposConBean> listaformapago) {
        this.listaformapago = listaformapago;
    }

    public boolean isBanfacturasi() {
        return banfacturasi;
    }

    public void setBanfacturasi(boolean banfacturasi) {
        this.banfacturasi = banfacturasi;
    }

    public boolean isBanfacturano() {
        return banfacturano;
    }

    public void setBanfacturano(boolean banfacturano) {
        this.banfacturano = banfacturano;
    }

    public List<productosBean> getListaBuscarProductoLike() {
        return ListaBuscarProductoLike;
    }

    public void setListaBuscarProductoLike(List<productosBean> ListaBuscarProductoLike) {
        this.ListaBuscarProductoLike = ListaBuscarProductoLike;
    }

    public Statement getObjConexion() {
        return objConexion;
    }

    public void setObjConexion(Statement objConexion) {
        this.objConexion = objConexion;
    }

    public PreparedStatement getObjPreConexion() {
        return objPreConexion;
    }

    public void setObjPreConexion(PreparedStatement objPreConexion) {
        this.objPreConexion = objPreConexion;
    }

    public Connection getConecta() {
        return conecta;
    }

    public void setConecta(Connection conecta) {
        this.conecta = conecta;
    }

    public boolean isBanguarda() {
        return banguarda;
    }

    public void setBanguarda(boolean banguarda) {
        this.banguarda = banguarda;
    }

    public boolean isBotonAct() {
        return botonAct;
    }

    public void setBotonAct(boolean botonAct) {
        this.botonAct = botonAct;
    }

    public boolean isTraspaso() {
        return traspaso;
    }

    public void setTraspaso(boolean traspaso) {
        this.traspaso = traspaso;
    }

    public List<productosBean> getListaHistoriaBodegas() {
        return ListaHistoriaBodegas;
    }

    public void setListaHistoriaBodegas(List<productosBean> ListaHistoriaBodegas) {
        this.ListaHistoriaBodegas = ListaHistoriaBodegas;
    }

    public List<productosBean> getListaVentaDia() {
        return ListaVentaDia;
    }

    public void setListaVentaDia(List<productosBean> ListaVentaDia) {
        this.ListaVentaDia = ListaVentaDia;
    }

    public List<productosBean> getListaValorLlegada() {
        return ListaValorLlegada;
    }

    public void setListaValorLlegada(List<productosBean> ListaValorLlegada) {
        this.ListaValorLlegada = ListaValorLlegada;
    }

    public List<productosBean> getListaAlternativos() {
        return ListaAlternativos;
    }

    public void setListaAlternativos(List<productosBean> ListaAlternativos) {
        this.ListaAlternativos = ListaAlternativos;
    }

    public boolean isActprod2() {
        return actprod2;
    }

    public void setActprod2(boolean actprod2) {
        this.actprod2 = actprod2;
    }

    public List<productosBean> getListaCotizaHist() {
        return ListaCotizaHist;
    }

    public void setListaCotizaHist(List<productosBean> ListaCotizaHist) {
        this.ListaCotizaHist = ListaCotizaHist;
    }

    public List<productosBean> getListaVentaProducto() {
        return ListaVentaProducto;
    }

    public void setListaVentaProducto(List<productosBean> ListaVentaProducto) {
        this.ListaVentaProducto = ListaVentaProducto;
    }

    public List<productosBean> getListaStok() {
        return ListaStok;
    }

    public void setListaStok(List<productosBean> ListaStok) {
        this.ListaStok = ListaStok;
    }

    public List<productosBean> getListaTraerProducto() {
        return ListaTraerProducto;
    }

    public void setListaTraerProducto(List<productosBean> ListaTraerProducto) {
        this.ListaTraerProducto = ListaTraerProducto;
    }

    public List<productosBean> getListaCarroCotizacion() {
        return ListaCarroCotizacion;
    }

    public void setListaCarroCotizacion(List<productosBean> ListaCarroCotizacion) {
        this.ListaCarroCotizacion = ListaCarroCotizacion;
    }

    public List<productosBean> getListaInventario() {
        return ListaInventario;
    }


    /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void setListaInventario(List<productosBean> ListaInventario) {
        this.ListaInventario = ListaInventario;
    }

    public List<productosBean> getListaConsultaBodega() {
        return ListaConsultaBodega;
    }

    public void setListaConsultaBodega(List<productosBean> ListaConsultaBodega) {
        this.ListaConsultaBodega = ListaConsultaBodega;
    }

    public List<productosBean> getListaAnaquel() {
        return ListaAnaquel;
    }

    public void setListaAnaquel(List<productosBean> ListaAnaquel) {
        this.ListaAnaquel = ListaAnaquel;
    }

    public List<productosBean> getListaNivel() {
        return ListaNivel;
    }

    public void setListaNivel(List<productosBean> ListaNivel) {
        this.ListaNivel = ListaNivel;
    }

    public List<productosBean> getListaActEntPedidos() {
        return ListaActEntPedidos;
    }

    public void setListaActEntPedidos(List<productosBean> ListaActEntPedidos) {
        this.ListaActEntPedidos = ListaActEntPedidos;
    }

    public List<productosBean> getListaPedidosFaltantes() {
        return ListaPedidosFaltantes;
    }

    public void setListaPedidosFaltantes(List<productosBean> ListaPedidosFaltantes) {
        this.ListaPedidosFaltantes = ListaPedidosFaltantes;
    }

    public List<productosBean> getListaPedidosBuscar() {
        return ListaPedidosBuscar;
    }

    public void setListaPedidosBuscar(List<productosBean> ListaPedidosBuscar) {
        this.ListaPedidosBuscar = ListaPedidosBuscar;
    }

    public List<productosBean> getListaPedidosPendientes() {
        return ListaPedidosPendientes;
    }

    public void setListaPedidosPendientes(List<productosBean> ListaPedidosPendientes) {
        this.ListaPedidosPendientes = ListaPedidosPendientes;
    }

    public boolean isFoliopedido() {
        return foliopedido;
    }

    public void setFoliopedido(boolean foliopedido) {
        this.foliopedido = foliopedido;
    }

    public List<productosBean> getListaCarritoPedidos() {
        return ListaCarritoPedidos;
    }

    public void setListaCarritoPedidos(List<productosBean> ListaCarritoPedidos) {
        this.ListaCarritoPedidos = ListaCarritoPedidos;
    }

    public List<productosBean> getListaContadoresPedidos() {
        return ListaContadoresPedidos;
    }

    public void setListaContadoresPedidos(List<productosBean> ListaContadoresPedidos) {
        this.ListaContadoresPedidos = ListaContadoresPedidos;
    }

    public List<productosBean> getListaProductoHist() {
        return ListaProductoHist;
    }

    public void setListaProductoHist(List<productosBean> ListaProductoHist) {
        this.ListaProductoHist = ListaProductoHist;
    }

    public boolean isRegprod() {
        return regprod;
    }

    public void setRegprod(boolean regprod) {
        this.regprod = regprod;
    }

    public boolean isActprod() {
        return actprod;
    }

    public void setActprod(boolean actprod) {
        this.actprod = actprod;
    }

    public boolean isCat() {
        return cat;
    }

    public void setCat(boolean cat) {
        this.cat = cat;
    }

    public boolean isCatg() {
        return catg;
    }

    public void setCatg(boolean catg) {
        this.catg = catg;
    }

    public boolean isProve() {
        return prove;
    }

    public void setProve(boolean prove) {
        this.prove = prove;
    }

    public boolean isPrec() {
        return prec;
    }

    //INICIAN METODOS GET Y SET DE LOS OBJETOS+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void setPrec(boolean prec) {
        this.prec = prec;
    }

    public boolean isNop() {
        return nop;
    }

    public void setNop(boolean nop) {
        this.nop = nop;
    }

    public boolean isPea2() {
        return pea2;
    }

    public void setPea2(boolean pea2) {
        this.pea2 = pea2;
    }

    public boolean isPea3() {
        return pea3;
    }

    public void setPea3(boolean pea3) {
        this.pea3 = pea3;
    }

    public boolean isPea4() {
        return pea4;
    }

    public void setPea4(boolean pea4) {
        this.pea4 = pea4;
    }

    public boolean isPea5() {
        return pea5;
    }

    public void setPea5(boolean pea5) {
        this.pea5 = pea5;
    }

    public boolean isPea6() {
        return pea6;
    }

    public void setPea6(boolean pea6) {
        this.pea6 = pea6;
    }

    public boolean isPea7() {
        return pea7;
    }

    public void setPea7(boolean pea7) {
        this.pea7 = pea7;
    }

    public boolean isPea8() {
        return pea8;
    }

    public void setPea8(boolean pea8) {
        this.pea8 = pea8;
    }

    public boolean isPea9() {
        return pea9;
    }

    public void setPea9(boolean pea9) {
        this.pea9 = pea9;
    }

    public boolean isPea10() {
        return pea10;
    }

    public void setPea10(boolean pea10) {
        this.pea10 = pea10;
    }

    public boolean isPea1() {
        return pea1;
    }

    public void setPea1(boolean pea1) {
        this.pea1 = pea1;
    }

    public List<productosBean> getListaCategoria() {
        return ListaCategoria;
    }

    public void setListaCategoria(List<productosBean> ListaCategoria) {
        this.ListaCategoria = ListaCategoria;
    }

    public List<productosBean> getListaSelectProvee() {
        return ListaSelectProvee;
    }

    public void setListaSelectProvee(List<productosBean> ListaSelectProvee) {
        this.ListaSelectProvee = ListaSelectProvee;
    }

    public List<productosBean> getListaGanancia() {
        return ListaGanancia;
    }

    public void setListaGanancia(List<productosBean> ListaGanancia) {
        this.ListaGanancia = ListaGanancia;
    }

    public List<camposConBean> getListaCategoriaGral() {
        return ListaCategoriaGral;
    }

    public void setListaCategoriaGral(List<camposConBean> ListaCategoriaGral) {
        this.ListaCategoriaGral = ListaCategoriaGral;
    }

    public List<productosBean> getListaBuscarProductoFinal() {
        return ListaBuscarProductoFinal;
    }

    public void setListaBuscarProductoFinal(List<productosBean> ListaBuscarProductoFinal) {
        this.ListaBuscarProductoFinal = ListaBuscarProductoFinal;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getDolar() {
        return dolar;
    }

    public void setDolar(float dolar) {
        this.dolar = dolar;
    }

    public float getDolarcalcula() {
        return dolarcalcula;
    }

    public void setDolarcalcula(float dolarcalcula) {
        this.dolarcalcula = dolarcalcula;
    }

    public float getIvacalcula() {
        return ivacalcula;
    }

    public void setIvacalcula(float ivacalcula) {
        this.ivacalcula = ivacalcula;
    }

    public List<productosBean> getListaProductoAlt() {
        return ListaProductoAlt;
    }

    public void setListaProductoAlt(List<productosBean> ListaProductoAlt) {
        this.ListaProductoAlt = ListaProductoAlt;
    }

    public List<productosBean> getListaBuscarProducto() {
        return ListaBuscarProducto;
    }

    public void setListaBuscarProducto(List<productosBean> ListaBuscarProducto) {
        this.ListaBuscarProducto = ListaBuscarProducto;
    }

    public List<productosBean> getListaProductosGral() {
        return ListaProductosGral;
    }

    public void setListaProductosGral(List<productosBean> ListaProductosGral) {
        this.ListaProductosGral = ListaProductosGral;
    }

    public List<usuarioBean> getListausuarios() {
        return Listausuarios;
    }

    public void setListausuarios(List<usuarioBean> Listausuarios) {
        this.Listausuarios = Listausuarios;
    }

    public List<proveedoresBean> getListaproveedores() {
        return Listaproveedores;
    }

    public void setListaproveedores(List<proveedoresBean> Listaproveedores) {
        this.Listaproveedores = Listaproveedores;
    }

    public clientesBean getRespon() {
        return respon;
    }

    public void setRespon(clientesBean respon) {
        this.respon = respon;
    }

    public usuarioBean getUsuariocons() {
        return usuariocons;
    }

    public void setUsuariocons(usuarioBean usuariocons) {
        this.usuariocons = usuariocons;
    }

    public String getCveusuario() {
        return cveusuario;
    }

    public void setCveusuario(String cveusuario) {
        this.cveusuario = cveusuario;
    }

    public String getPasusuario() {
        return pasusuario;
    }

    public void setPasusuario(String pasusuario) {
        this.pasusuario = pasusuario;
    }

    public String getNomModulo() {
        return nomModulo;
    }

    public void setNomModulo(String nomModulo) {
        this.nomModulo = nomModulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<moduloBean> getModulosAUX() {
        return modulosAUX;
    }

    public void setModulosAUX(List<moduloBean> modulosAUX) {
        this.modulosAUX = modulosAUX;
    }

    public List<moduloAuxBean> getModulosAUXP() {
        return modulosAUXP;
    }

    public void setModulosAUXP(List<moduloAuxBean> modulosAUXP) {
        this.modulosAUXP = modulosAUXP;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(String nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public String getTipoError() {
        return TipoError;
    }

    public void setTipoError(String TipoError) {
        this.TipoError = TipoError;
    }

    public String getTipoException() {
        return TipoException;
    }

    public void setTipoException(String TipoException) {
        this.TipoException = TipoException;
    }

    public Navegacion getObjNaveg() {
        return objNaveg;
    }

    public void setObjNaveg(Navegacion objNaveg) {
        this.objNaveg = objNaveg;
    }

    public String getLigaActual() {
        return ligaActual;
    }

    public void setLigaActual(String ligaActual) {
        this.ligaActual = ligaActual;
    }

    public String getRFCAUX() {
        return RFCAUX;
    }

    public void setRFCAUX(String RFCAUX) {
        this.RFCAUX = RFCAUX;
    }

    public boolean isBanClienteActualiza() {
        return banClienteActualiza;
    }

    public void setBanClienteActualiza(boolean banClienteActualiza) {
        this.banClienteActualiza = banClienteActualiza;
    }

    public camposConBean getCamp() {
        return camp;
    }

    public void setCamp(camposConBean camp) {
        this.camp = camp;
    }

    public String getBUSCARCLIENTE() {
        return BUSCARCLIENTE;
    }

    public void setBUSCARCLIENTE(String BUSCARCLIENTE) {
        this.BUSCARCLIENTE = BUSCARCLIENTE;
    }

    public List<clientesBean> getListaClientes() {
        return ListaClientes;
    }

    public void setListaClientes(List<clientesBean> ListaClientes) {
        this.ListaClientes = ListaClientes;
    }

    public boolean isBanCliente() {
        return banCliente;
    }

    public void setBanCliente(boolean banCliente) {
        this.banCliente = banCliente;
    }

    public boolean isBanClienteBusqueda() {
        return banClienteBusqueda;
    }

    public void setBanClienteBusqueda(boolean banClienteBusqueda) {
        this.banClienteBusqueda = banClienteBusqueda;
    }

    //******************** PARA OBJETO DE NAVEGACIoN ***********************************************
    private Map session;
    Navegacion objNaveg;
    String ligaRegreso = Navegacion.InitialPage;
    String ligaActual = "";

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public String getLigaRegreso() {
        return ligaRegreso;
    }

    public void setLigaRegreso(String ligaRegreso) {
        this.ligaRegreso = ligaRegreso;
    }

    public String clientesCon() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            banCliente = true;
            ConsultaBusiness consult = new ConsultaBusiness();

            ListaClientes = consult.clientesCon();

            //Constantes.enviaMensajeConsola("El nombre de usuario es:" + usuariocons.getNAMEUSUARIO());

        } catch (Exception e) {
              System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public String clientesBusqueda() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();
            banCliente = true;

            //Constantes.enviaMensajeConsola("El valor a buscar" + camp.getBUSCARCLIENTE());
            if (camp.getBUSCARCLIENTE().length() > 0) {

                camp.setBUSCARCLIENTE(camp.getBUSCARCLIENTE().toUpperCase());

            }
            ListaClientes = consult.clientesBusqueda(camp);
            limpiar();

        } catch (Exception e) {
            System.out.println(e);
        }

        return "SUCCESS";
    }

    public String clientesGuarda() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();
            //  boolean nom = false;
            boolean rfc = false;
            boolean rs = false;
            boolean dir = false;
            boolean tel = false;
            // boolean emp = false;
            boolean cor = false;

            int validarfc = 0;

            if (camp.getRAZONSOCIAL().length() > 0) {
                camp.setRAZONSOCIAL(camp.getRAZONSOCIAL().toUpperCase());
                rs = true;

            } else {
                rs = false;

                addFieldError("CampRequierers", "Se requiere un valor ");
            }

            if (camp.getRFC_CLIENTE().length() <= 20 && camp.getRFC_CLIENTE().length() >= 10) {
                camp.setRFC_CLIENTE(camp.getRFC_CLIENTE().toUpperCase());

                if (validaRfc(camp.getRFC_CLIENTE()) == 1) {
                    //Constantes.enviaMensajeConsola("el valor " + validaRfc(camp.getRFC_CLIENTE()));
                    rfc = true;
                } else {
                    //Constantes.enviaMensajeConsola("el valor " + validaRfc(camp.getRFC_CLIENTE()));
                    rfc = false;

                    addFieldError("CampRequiererfc", "Formato de RFC novalido, favor de verificarlo");
                }

            } else {
                rfc = false;

                addFieldError("CampRequiererfc", "Se requieren de 12 a 13 dígitos");
            }

//            if (camp.getNOMBRE_CLIENTE().length() > 0) {
//                nom = true;
//
//            } else {
//                nom = false;
//
//                addFieldError("CampRequierenom", "Se requiere un valor ");
//            }
            if (camp.getDIRECCION_CLIENTE().length() > 0) {
                camp.setDIRECCION_CLIENTE(camp.getDIRECCION_CLIENTE().toUpperCase());
                dir = true;

            } else {
                dir = false;

                addFieldError("CampRequieredir", "Se requiere un valor ");
            }

            if (camp.getTELEFONO_CLIENTE().length() > 0) {

                if (validaTelefono(camp.getTELEFONO_CLIENTE()) == 1) {
                    // //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));
                    tel = true;
                } else {
                    //  //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));
                    tel = false;

                    addFieldError("CampRequieretel", "Formato de número no valido, debe contener solo 10 dígitos ");
                }

            } else {
                tel = false;

                addFieldError("CampRequieretel", "Se requiere un valor ");
            }
//            if (camp.getEMPRESA_CLIENTE().length() > 0) {
//                emp = true;
//
//            } else {
//                emp = false;
//
//                addFieldError("CampRequiereemp", "Se requiere un valor ");
//            }
            if (camp.getCORREO_CLIENTE().length() > 0) {
                camp.setCORREO_CLIENTE(camp.getCORREO_CLIENTE().toUpperCase());
                if (validaCorreo(camp.getCORREO_CLIENTE()) == 1) {
                    // //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));

                    camp.setCORREO_CLIENTE(camp.getCORREO_CLIENTE().toLowerCase());
                    cor = true;
                } else {
                    //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));
                    cor = false;

                    addFieldError("CampRequierecor", "Formato de correo no vaalido ");
                }

            } else {
                cor = false;

                addFieldError("CampRequierecor", "Se requiere un valor ");
            }

            banCliente = true;
            if (rfc && rs && dir && tel && cor) {

                camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

                consult.GuardaDatos(camp);
                clientesCon();

                limpiar();
                addFieldError("mensaje", "Se guardo al nuevo cliente");
            }

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String clientesActualiza() {
        try {
            banCliente = false;
            banClienteActualiza = true;
            ConsultaBusiness consult = new ConsultaBusiness();

            boolean rfc = false;
            boolean rs = false;
            boolean dir = false;
            boolean tel = false;
            // boolean emp = false;
            boolean cor = false;

            int validarfc = 0;

            if (camp.getRAZONSOCIAL().length() > 0) {
                camp.setRAZONSOCIAL(camp.getRAZONSOCIAL().toUpperCase());
                rs = true;

            } else {
                rs = false;

                addFieldError("CampRequierers", "Se requiere un valor ");
            }

            if (camp.getRFC_CLIENTE().length() <= 20 && camp.getRFC_CLIENTE().length() >= 10) {
                camp.setRFC_CLIENTE(camp.getRFC_CLIENTE().toUpperCase());

                if (validaRfc(camp.getRFC_CLIENTE()) == 1) {
                    //Constantes.enviaMensajeConsola("el valor " + validaRfc(camp.getRFC_CLIENTE()));
                    rfc = true;
                } else {
                    //Constantes.enviaMensajeConsola("el valor " + validaRfc(camp.getRFC_CLIENTE()));
                    rfc = false;

                    addFieldError("CampRequiererfc", "Formato de RFC novalido, favor de verificarlo");
                }

            } else {
                rfc = false;

                addFieldError("CampRequiererfc", "Se requieren de 12 a 13 dígitos");
            }

//            if (camp.getNOMBRE_CLIENTE().length() > 0) {
//                nom = true;
//
//            } else {
//                nom = false;
//
//                addFieldError("CampRequierenom", "Se requiere un valor ");
//            }
            if (camp.getDIRECCION_CLIENTE().length() > 0) {
                camp.setDIRECCION_CLIENTE(camp.getDIRECCION_CLIENTE().toUpperCase());
                dir = true;

            } else {
                dir = false;

                addFieldError("CampRequieredir", "Se requiere un valor ");
            }

            if (camp.getTELEFONO_CLIENTE().length() > 0) {

                if (validaTelefono(camp.getTELEFONO_CLIENTE()) == 1) {
                    // //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));
                    tel = true;
                } else {
                    //  //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));
                    tel = false;

                    addFieldError("CampRequieretel", "Formato de número no valido, debe contener solo 10 dígitos ");
                }

            } else {
                tel = false;

                addFieldError("CampRequieretel", "Se requiere un valor ");
            }
//            if (camp.getEMPRESA_CLIENTE().length() > 0) {
//                emp = true;
//
//            } else {
//                emp = false;
//
//                addFieldError("CampRequiereemp", "Se requiere un valor ");
//            }
            if (camp.getCORREO_CLIENTE().length() > 0) {
                camp.setCORREO_CLIENTE(camp.getCORREO_CLIENTE().toUpperCase());
                if (validaCorreo(camp.getCORREO_CLIENTE()) == 1) {
                    // //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));

                    camp.setCORREO_CLIENTE(camp.getCORREO_CLIENTE().toLowerCase());
                    cor = true;
                } else {
                    //Constantes.enviaMensajeConsola("el valor "+validaRfc(camp.getRFC_CLIENTE()));
                    cor = false;

                    addFieldError("CampRequierecor", "Formato de correo no vaalido ");
                }

            } else {
                cor = false;

                addFieldError("CampRequierecor", "Se requiere un valor ");
            }

            if (rfc && rs && dir && tel && cor) {

                camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
                consult.ActualizaDatos(camp);
                clientesBusqueda();
                limpiar();
                banCliente = true;
                banClienteActualiza = false;

                addFieldError("mensaje", "Se actualizarón los datos del  cliente");
            }

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String llenarCamposActualizar() {
        try {
            banCliente = false;
            banClienteActualiza = true;
            ConsultaBusiness consult = new ConsultaBusiness();

            //Constantes.enviaMensajeConsola("LA RFC A CONSULTAR ES:" + camp.getRFCAUX());

            ListaClientes = consult.clientesBusquedaRfc(camp);

            Iterator LD = ListaClientes.iterator();
            clientesBean obj;

            while (LD.hasNext()) {
                obj = (clientesBean) LD.next();
                camp.setRAZONSOCIAL(obj.getRASON_CLIENT());
                camp.setRFC_CLIENTE(obj.getRFC_CLIENT());
                camp.setNOMBRE_CLIENTE(obj.getNOMBRE_CLIENT());
                camp.setDIRECCION_CLIENTE(obj.getDIRECCION_CLIENT());
                camp.setTELEFONO_CLIENTE(obj.getTELEFONO_CLIET());
                camp.setEMPRESA_CLIENTE(obj.getEMPRESA());
                camp.setCORREO_CLIENTE(obj.getCORREO_CLIENT());

            }
            // consult.ActualizaDatos(camp);
            // clientesBusqueda();
            //   limpiar();

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String clientesBorrar() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();

            if (consult.noCreditos(camp) > 0) {
                addFieldError("mensaje", "No se puede eliminar al usuario, ya que cuenta con un crédito activo");

            } else {

                consult.borrarDatos(camp);
                clientesBusqueda();
                limpiar();
                addFieldError("mensaje", "Se borro al cliente de manera satisfactoria");
            }

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public void limpiar() {

        camp.setBUSCARCLIENTE("");
        camp.setCORREO_CLIENTE("");
        camp.setDIRECCION_CLIENTE("");
        camp.setEMPRESA_CLIENTE("");
        camp.setNOMBRE_CLIENTE("");
        camp.setRAZONSOCIAL("");
        camp.setRFC_CLIENTE("");
        camp.setTELEFONO_CLIENTE("");
        camp.setBUSCARPROVEEDOR("");

        camp.setCORREO_PROV("");
        camp.setDIRECCION_PROV("");
        camp.setEMPRESA_PROV("");
        camp.setNOMBRE_PROV("");
        camp.setRAZONSOCIAL_PROV("");
        camp.setRFC_PROV("");
        camp.setTELEFONO_PROV("");

        camp.setUSUARIO("");
        camp.setPASSWORD("");
        camp.setNAMEUSUARIO("");

        camp.setNO_PARTE("");
        camp.setPRODUCTO("");
        camp.setCATEGORIA("");
        camp.setCATEGORIA_GENERAL("");
        camp.setDESCRIPCION("");
        camp.setMARCA("");
        camp.setUNIDADMEDIDA("");
        camp.setPROVEEDOR("");
        camp.setPRECIO("");
        camp.setGANANCIA("");

    }

    public String proveedoresCon() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            banCliente = true;
            ConsultaBusiness consult = new ConsultaBusiness();

            Listaproveedores = consult.proveedoresCon();

            //Constantes.enviaMensajeConsola("El nombre de usuario es:" + usuariocons.getNAMEUSUARIO());

        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String proveedoresBusqueda() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            banCliente = true;

            //Constantes.enviaMensajeConsola("El valor a buscar" + camp.getBUSCARCLIENTE());
            Listaproveedores = consult.proveedoresBusqueda(camp);
            limpiar();

            //Constantes.enviaMensajeConsola("El nombre de usuario es:" + usuariocons.getNAMEUSUARIO());

        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String proveedoresGuarda() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            //Constantes.enviaMensajeConsola("entre a guardar provvedores");

            ConsultaBusiness consult = new ConsultaBusiness();
            boolean nom = false;
            boolean rfc = false;
            boolean rs = false;
            boolean dir = false;
            boolean tel = false;
            boolean emp = false;
            boolean cor = false;

            if (camp.getRAZONSOCIAL_PROV().length() > 0) {
                rs = true;

            } else {
                rs = false;

                addFieldError("CampRequierers", "Se requiere un valor ");
            }

            if (camp.getRFC_PROV().length() <= 18 && camp.getRFC_PROV().length() >= 12) {
                rfc = true;

            } else {
                rfc = false;

                addFieldError("CampRequiererfc", "Se requieren 18 elementos ");
            }

            if (camp.getNOMBRE_PROV().length() > 0) {
                nom = true;

            } else {
                nom = false;

                addFieldError("CampRequierenom", "Se requiere un valor ");
            }

            if (camp.getDIRECCION_PROV().length() > 0) {
                dir = true;

            } else {
                dir = false;

                addFieldError("CampRequieredir", "Se requiere un valor ");
            }

            if (camp.getTELEFONO_PROV().length() > 0) {
                tel = true;

            } else {
                tel = false;

                addFieldError("CampRequieretel", "Se requiere un valor ");
            }
            if (camp.getEMPRESA_PROV().length() > 0) {
                emp = true;

            } else {
                emp = false;

                addFieldError("CampRequiereemp", "Se requiere un valor ");
            }
            if (camp.getCORREO_PROV().length() > 0) {
                cor = true;

            } else {
                cor = false;

                addFieldError("CampRequierecor", "Se requiere un valor ");
            }

            banCliente = true;
            if (nom && rfc && rs && dir && tel && emp && cor) {
                //Constantes.enviaMensajeConsola("se validaron los datos");
                camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

                consult.GuardaProv(camp);

                //Constantes.enviaMensajeConsola("regrese de gurdar voy a consultar");
                proveedoresCon();
                limpiar();
            }

        } catch (Exception e) {
          System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String proveedoresBorrar() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();

            consult.borrarProveedores(camp);
            proveedoresCon();
            limpiar();

        } catch (Exception e) {
        }

        return "SUCCESS";
    }

    public String llenarCamposProveedores() {
        try {
            banCliente = false;
            banClienteActualiza = true;
            ConsultaBusiness consult = new ConsultaBusiness();

            //Constantes.enviaMensajeConsola("LA RFC A CONSULTAR ES:" + camp.getRFCAUX());

            Listaproveedores = consult.proveedoresBusquedaRfc(camp);

            Iterator LD = Listaproveedores.iterator();
            proveedoresBean obj;

            while (LD.hasNext()) {
                obj = (proveedoresBean) LD.next();
                camp.setRAZONSOCIAL_PROV(obj.getRASON_PROVEE());
                camp.setRFC_PROV(obj.getRFC_PROVEE());
                camp.setNOMBRE_PROV(obj.getNOMBRE_PROVEE());
                camp.setDIRECCION_PROV(obj.getDIRECCION_PROVEE());
                camp.setTELEFONO_PROV(obj.getTELEFONO_PROVEE());
                camp.setEMPRESA_PROV(obj.getEMPRESA_PROVEE());
                camp.setCORREO_PROV(obj.getCORREO_PROVEE());

            }
            // consult.ActualizaDatos(camp);
            // clientesBusqueda();
            //   limpiar();

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String proveedoresActualiza() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
            consult.Actualizaproveedores(camp);
            proveedoresBusqueda();
            limpiar();

        } catch (Exception e) {
        }

        return "SUCCESS";
    }

    public String usuariosCon() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            banCliente = true;
            ConsultaBusiness consult = new ConsultaBusiness();

            Listausuarios = consult.usuariosCon();

            //Constantes.enviaMensajeConsola("El nombre de usuario es:" + usuariocons.getNAMEUSUARIO());

        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String traspasos() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String usuariosGuarda() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();
            boolean nom = false;
            boolean rfc = false;
            boolean rs = false;
            boolean dir = false;
            boolean tel = false;
            boolean emp = false;
            boolean cor = false;

            if (camp.getUSUARIO().length() > 0) {
                rs = true;
                camp.setNAMEUSUARIO(camp.getUSUARIO());

            } else {
                rs = false;

                addFieldError("CampRequierers", "Se requiere un valor ");
            }

            if (camp.getPASSWORD().length() <= 18 && camp.getPASSWORD().length() >= 2) {
                rfc = true;

            } else {
                rfc = false;

                addFieldError("CampRequiererfc", "Se requieren almenos 5 letras y/o números ");
            }

            if (camp.getNAMEPERFIL().length() > 0) {
                nom = true;
                String valor = camp.getNAMEPERFIL();
                switch (valor) {

                    case "Administrador":
                        camp.setPERFIL("1");
                        //Constantes.enviaMensajeConsola("El Perfil de Adimistrador en " + camp.getPERFIL());
                        break;
                    case "Ventas":
                        camp.setPERFIL("2");
                        //Constantes.enviaMensajeConsola("El Perfil de Adimistrador en " + camp.getPERFIL());
                        break;
                    case "Almacen":
                        camp.setPERFIL("3");
                        //Constantes.enviaMensajeConsola("El Perfil de Adimistrador en " + camp.getPERFIL());
                        break;
                    case "Compras":
                        camp.setPERFIL("4");
                        //Constantes.enviaMensajeConsola("El Perfil de Adimistrador en " + camp.getPERFIL());
                        break;
                    case "Caja":
                        camp.setPERFIL("5");
                        //Constantes.enviaMensajeConsola("El Perfil de Adimistrador en " + camp.getPERFIL());
                        break;
                }

            } else {
                nom = false;

                addFieldError("CampRequierenom", "Se requiere un valor ");
            }

            //Constantes.enviaMensajeConsola("EL VALOR INTRODUCIDO ES ....." + camp.getPERFIL());

            banCliente = true;
            if (nom && rfc && rs) {

                camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

                consult.Guardausuarios(camp);
                usuariosCon();
                limpiar();
            }

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String usuariosBorrar() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();

            consult.usuariosDatos(camp);
            usuariosCon();
            limpiar();

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String ingresoProductos() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();
            regprod = true;
            actprod = false;

//            iva = iva();
//            ivacalcula = Float.parseFloat(iva);
//            dolar = consult.dolar();
//            dolarcalcula = Float.parseFloat(dolar);
            ListaProductoHist = consult.productosBuscarHist(camp);

            /*
            ListaProductosGral = consult.productosGral();

            Iterator cge = ListaProductosGral.iterator();

            productosBean pb;

            float precioCal;
            float precio;
            float ganancia;

            while (cge.hasNext()) {
                pb = (productosBean) cge.next();
                precio = Float.parseFloat(pb.getPRECIO());
                               
                if (pb.getPRECIO_CAL().equals("SI")) {
                            if (pb.getMONEDA().equals("USD")) {

                                precioCal = (float) (((((precio * (dolarcalcula)) * (ivacalcula)) + (precio * (dolarcalcula))) * .32) + (((precio * (dolarcalcula)) * (ivacalcula)) + (precio * (dolarcalcula))));

                                pb.setPRECIO(String.valueOf(Math.round(precioCal)));
                            }
                            if (pb.getMONEDA().equals("PESO")) {

                                precioCal = (float) (((((precio) * (ivacalcula)) + (precio)) * .32) + (((precio) * (ivacalcula)) + (precio)));

                                pb.setPRECIO(String.valueOf(Math.round(precioCal)));
                            }

                }
                
                if(pb.getPRECIO_CAL().equals("NO")){
                   
                                ganancia=Float.parseFloat(pb.getGANANCIA());

                               if (pb.getMONEDA().equals("USD")) {

                                   precioCal = (float) (((((precio * (dolarcalcula)) * (ivacalcula)) + (precio * (dolarcalcula))) * ganancia) + (((precio * (dolarcalcula)) * (ivacalcula)) + (precio * (dolarcalcula))));

                                   pb.setPRECIO(String.valueOf(Math.round(precioCal)));
                               }
                               if (pb.getMONEDA().equals("PESO")) {


                                   precioCal = (float) (((((precio) * (ivacalcula)) + (precio)) * ganancia) + (((precio) * (ivacalcula)) + (precio)));
                                   //Constantes.enviaMensajeConsola("sali del calculo y es:"+precioCal);
                                   pb.setPRECIO(String.valueOf(Math.round(precioCal)));
                                   //Constantes.enviaMensajeConsola("mande el valor"+pb.getPRECIO());
                               }
                    
                    
                    
                }

            } */

 /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    //ajustado para nuevo calculo
    public String buscarProductos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            ListaBuscarProducto = consult.productosBuscar(camp);
            ListaCatSat = consult.catSat(camp);

            ListaBuscarProductoFinal = consult.productosBuscarFinal(camp);
            actprod = false;
            regprod = false;

            if (ListaBuscarProductoFinal.size() > 0) {
                actprod = true;
                regprod = false;

            }

            if (ListaBuscarProductoFinal.size() <= 0) {
                actprod = false;
                regprod = true;
                limpiar();
                camp.setNO_PARTE(camp.getCONSULTA_PARTE());

            }

            Iterator cgeBP = ListaBuscarProductoFinal.iterator();
            productosBean pbBP;

            while (cgeBP.hasNext()) {
                pbBP = (productosBean) cgeBP.next();

                camp.setNO_PARTE(pbBP.getNO_PARTE());
                camp.setPRODUCTO(pbBP.getPRODUCTO());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setDESCRIPCION(pbBP.getDESCRIPCION());
                camp.setPRECIO(pbBP.getPRECIO());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setMARCA(pbBP.getMARCA());
                camp.setUNIDADMEDIDA(pbBP.getUNIDADMEDIDA());
                camp.setMONEDA(pbBP.getMONEDA());
                camp.setALTERNATIVO(pbBP.getALTERNATIVO());
                camp.setPRECIO_PESO(pbBP.getPRECIO_PESO());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setPRECIO_CAL(pbBP.getPRECIO_CAL());
                camp.setCVE_SAT(pbBP.getCVE_SAT());

                if (pbBP.getGANANCIA().equals("0")) {
                    camp.setGANANCIA("0.32");

                } else {
                    camp.setGANANCIA(pbBP.getGANANCIA());
                }

                camp.setRESPONSABLE(pbBP.getRESPONSABLE());
                camp.setPROVEEDOR(pbBP.getPROVEEDOR());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setCATEGORIA_GENERAL(pbBP.getCATEGORIA_GENERAL());

            }

            ivacalcula = iva();
            dolarcalcula = dolar();

            ListaProductoHist = consult.productosBuscarHist(camp);

            Iterator cge = ListaProductoHist.iterator();

            productosBean pb;

            float precioCal;
            float precio;
            float ganancia;

            while (cge.hasNext()) {
                pb = (productosBean) cge.next();
                precio = Float.parseFloat(pb.getPRECIO());
                ganancia = Float.parseFloat(pb.getGANANCIA());

                precioUniPartida = calculaPrecio(pb.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

            
                pb.setPRECIO_PESO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

            }

            if (camp.getPRECIO().length() > 0 && camp.getGANANCIA().length() > 0) {

                simuladorPrecio();
            }

            ListaGanancia = consult.ganancia(camp);

            ListaSelectProvee = consult.selectProvee(camp);

            ListaCategoriaGral = consult.categoria(camp);
            ListaCategoria = consult.categoriaP(camp);

            ListaProductoAlt = consult.productosBuscarAlt(camp);

            Iterator cgeAlt = ListaProductoAlt.iterator();

            productosBean pbAlt;

            precioCal = 0;
            precio = 0;
            ganancia = 0;

            while (cgeAlt.hasNext()) {
                pbAlt = (productosBean) cgeAlt.next();
                precio = Float.parseFloat(pbAlt.getPRECIO());
                ganancia = Float.parseFloat(pbAlt.getGANANCIA());

                precioUniPartida = calculaPrecio(pbAlt.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

             

                pbAlt.setPRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

            }

            ListaHistoriaBodegas = consult.historiaBodegas(camp);
            //Constantes.enviaMensajeConsola("la lsita tiene un valor de " + ListaHistoriaBodegas.size());

            /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public precioBean calculaPrecio(String moneda, float ganancia, double precioUnitario, double aumento, int cantidad, float ivacalcula, float dolarcalcula) throws Exception {

       
        double precioCal = 0;
        double precioPeso = 0;
        double precioPesofinalSinIva = 0;
        double precioPesofinalIva = 0;
        double precio_partida=0;
        double iva_partida=0;
        double precio_partida_total=0;

        precioBean precio = new precioBean();

        if (moneda.equals("USD")) {

            precioPeso = precioUnitario * dolarcalcula;

        } else {
            precioPeso = precioUnitario;
        }

        precioPesofinalSinIva = (double) ((precioPeso * ganancia) + precioPeso);
        
     
       

        if (aumento > 0) {

            precioPesofinalSinIva = precioPesofinalSinIva + aumento;
           

        }
        
     
        

        precioPesofinalIva = (double) (precioPesofinalSinIva) * iva;
        
       
          

        precioCal = (double) (precioPesofinalSinIva + precioPesofinalIva);
        
       
         
         
        precio_partida=precioPesofinalSinIva * cantidad;
       
        
        iva_partida=precioPesofinalIva * cantidad;
       
        
        precio_partida_total=precioCal * cantidad;
       
         
           
         precioPesofinalSinIva=  Math.floor(precioPesofinalSinIva * 100) / 100;
            precioPesofinalIva=  Math.floor(precioPesofinalIva * 100) / 100;
               precio_partida=  Math.floor(precio_partida * 100) / 100;
                  iva_partida=  Math.floor(iva_partida * 100) / 100;
                    precio_partida_total=  Math.floor(precio_partida_total * 100) / 100;
                      precioCal=  Math.floor(precioCal * 100) / 100;
         
         
         
        precio.setPRECIO_UNIARIO(precioPesofinalSinIva);
        precio.setIVA_UNITARIO(precioPesofinalIva);
        precio.setPRECIO_UNITARIO_TOTAL(precioCal);
        precio.setPRECIO_PARTIDA(precio_partida);
        precio.setIVA_PARTIDA(iva_partida);
        precio.setPRECIO_PARTIDA_TOTAL(precio_partida_total);
        
//        //Constantes.enviaMensajeConsola("PRECIO UNITARIO:"+ precio.getPRECIO_UNIARIO());
//         //Constantes.enviaMensajeConsola("IVA_UNITARIO" + precio.getIVA_UNITARIO());
//          //Constantes.enviaMensajeConsola("PRECIO UNITARIO TOTAL" + precio.getPRECIO_UNITARIO_TOTAL());
//           //Constantes.enviaMensajeConsola("PRECIO PARTIDA" + precio.getPRECIO_PARTIDA());
//            //Constantes.enviaMensajeConsola("IVA PARTIDA" + precio.getIVA_PARTIDA());
//             //Constantes.enviaMensajeConsola("PRECIO TOTAL PARTIDA" + precio.getPRECIO_PARTIDA_TOTAL());
             

        return precio;
    }

    public String eliminarProducto() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            //Constantes.enviaMensajeConsola("La constraseña es:" + camp.getAUTORIZACION());

            String aut = camp.getAUTORIZACION();
            String pass = consult.pass();

            //Constantes.enviaMensajeConsola(pass);

            if (pass.equals(aut)) {
                consult.eliminarProducto(camp);
                consult.eliminarProductoBodegas(camp);
                consult.eliminarProductoAlternativos(camp);
                consult.eliminarProductoHist(camp);
                consult.eliminarProductoHistIn(camp);
                ListaHistoriaBodegas.clear();
                ListaGanancia.clear();
                ListaSelectProvee.clear();
                ListaCategoriaGral.clear();
                ListaCategoria.clear();
                ListaProductoAlt.clear();
                ListaProductoHist.clear();
                ListaBuscarProducto.clear();
                ListaBuscarProductoFinal.clear();
                camp.setNO_PARTE("");
                camp.setPRODUCTO("");
                camp.setCATEGORIA("");
                camp.setDESCRIPCION("");
                camp.setPRECIO("");
                camp.setCATEGORIA("");
                camp.setMARCA("");
                camp.setUNIDADMEDIDA("");
                camp.setMONEDA("");
                camp.setALTERNATIVO("");
                camp.setPRECIO_PESO("");
                camp.setCATEGORIA("");
                camp.setPRECIO_CAL("");
                camp.setCONSULTA_PARTE("");
                camp.setCVE_SAT("");

                addFieldError("ELIMINADO", "El producto fue eliminado con éxito");

            } else {

                addFieldError("ELIMINADO", "Contraseña incorrecta favor de verificarla e intentalo nuevamente");

            }

        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String buscarProductosTraspasos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            ListaBuscarProducto = consult.productosBuscar(camp);
            ListaProductoAlt = consult.productosBuscarAlt(camp);

            if (ListaBuscarProducto.size() > 0) {

                Iterator LBP = ListaBuscarProducto.iterator();

                productosBean obj;

                int local = 0;

                int casa = 0;

                int tenango = 0;

                while (LBP.hasNext()) {
                    obj = (productosBean) LBP.next();

                    if (obj.getNAME_BODEGA().equals("CASA")) {

                        casa++;

                    }
                    if (obj.getNAME_BODEGA().equals("TENANGO")) {

                        tenango++;

                    }

                }
                camp.setNO_PARTE(camp.getCONSULTA_PARTE());
                if (casa == 0) {

                    camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
                    camp.setANAQUEL("0");
                    camp.setNIVEL("A");
                    camp.setCANTIDAD_LLEGADA("0");
                    camp.setBODEGA("CASA");
                    consult.guardaProductosBodega(camp);

                }

                if (tenango == 0) {

                    camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
                    camp.setANAQUEL("0");
                    camp.setNIVEL("A");
                    camp.setCANTIDAD_LLEGADA("0");
                    camp.setBODEGA("TENANGO");
                    consult.guardaProductosBodega(camp);

                }

                ListaBuscarProducto = consult.productosBuscar(camp);

            }

            /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String nuevoTraspasos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            camp.setBODEGA_AUXILIAR("");
            camp.setNUEVOSTOK("");

            ListaBuscarProducto = consult.productosBuscar(camp);

            traspaso = true;

            ListaAnaquel = consult.anaquel(camp);

            ListaNivel = consult.nivel(camp);

            /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String traspasar() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            camp.setCONSULTA_PARTE(camp.getNO_PARTE());

            //Constantes.enviaMensajeConsola("consulta parte" + camp.getCONSULTA_PARTE());

            int traspasar = 0;
            int stock = 0;
            int nuevoStok = 0;

            traspasar = Integer.parseInt(camp.getCANTIDAD_LLEGADA());
            stock = Integer.parseInt(camp.getCANTIDAD());
            nuevoStok = stock - traspasar;

            camp.setNUEVOSTOK(String.valueOf(nuevoStok));

            int local = 0;
            int casa = 0;
            int tenango = 0;

            ListaBuscarProducto = consult.productosBuscar(camp);

            Iterator LBP = ListaBuscarProducto.iterator();
            productosBean obj;

            while (LBP.hasNext()) {
                obj = (productosBean) LBP.next();

                if (obj.getNAME_BODEGA().equals("LOCAL")) {

                    local = Integer.parseInt(obj.getCATIDAD());
                }
                if (obj.getNAME_BODEGA().equals("CASA")) {
                    casa = Integer.parseInt(obj.getCATIDAD());
                }
                if (obj.getNAME_BODEGA().equals("TENANGO")) {
                    tenango = Integer.parseInt(obj.getCATIDAD());
                }

            }

            //Constantes.enviaMensajeConsola("LOCAL" + local);
            //Constantes.enviaMensajeConsola("CASA" + casa);
            //Constantes.enviaMensajeConsola("TENANGO" + tenango);

            consult.actualizaMovimientoMenos(camp);
            //Constantes.enviaMensajeConsola("EL NOMBRE DE LA BODEGA ES :%%%%%%%" + camp.getNAME_BODEGA());

            if (camp.getBODEGA_AUXILIAR().equals("LOCAL")) {

                nuevoStok = traspasar + local;
                camp.setNUEVOSTOK(String.valueOf(nuevoStok));
                camp.setBODEGA("LOCAL");
                consult.actualizaMovimiento(camp);

            }

            if (camp.getBODEGA_AUXILIAR().equals("CASA")) {
                //Constantes.enviaMensajeConsola("entre a insertar a la bodegax");
                nuevoStok = traspasar + casa;
                camp.setNUEVOSTOK(String.valueOf(nuevoStok));
                camp.setBODEGA("CASA");
                consult.actualizaMovimiento(camp);

            }
            if (camp.getBODEGA_AUXILIAR().equals("TENANGO")) {

                nuevoStok = traspasar + tenango;
                camp.setNUEVOSTOK(String.valueOf(nuevoStok));
                camp.setBODEGA("TENANGO");
                consult.actualizaMovimiento(camp);

            }

            traspaso = true;

            ListaAnaquel = consult.anaquel(camp);

            ListaNivel = consult.nivel(camp);

            camp.setNO_PARTE("");
            camp.setBODEGA("");
            camp.setANAQUEL("");
            camp.setNIVEL("");
            camp.setCANTIDAD_LLEGADA("");
            camp.setCANTIDAD("");
            camp.setNAME_BODEGA("");
            camp.setBODEGA_AUXILIAR("");
            camp.setNUEVOSTOK("");
            traspaso = false;

            buscarProductosTraspasos();

            local = 0;
            casa = 0;
            tenango = 0;
            nuevoStok = 0;
            traspasar = 0;
            stock = 0;

        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String selectCategoria() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            ListaCategoria = consult.categoriaP(camp);

            if (camp.getPRECIO().length() > 0 && camp.getGANANCIA().length() > 0) {

                simuladorPrecio();
            }

        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    /* CONSULTA DE IVA    +++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public float iva() throws Exception {

        ConsultaBusiness consult = new ConsultaBusiness();

        iva = consult.iva();

        return iva;
    }

    /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

 /* CONSULTA DE DOLAR+++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public float dolar() throws Exception {

        ConsultaBusiness consult = new ConsultaBusiness();

        dolar = consult.dolar();

        return dolar;
    }

    /* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    //ajustado al nuevo calculo
    public String simuladorPrecio() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();
            float PrecioFinal = 0;
            float precio = 0;
            String moneda = "";
            float ganancia = 0;
              ivacalcula = iva();
        dolarcalcula = dolar();

            if (camp.getPRECIO().length() > 0 && camp.getGANANCIA().length() > 0) {

                /* CALCULO DE IVA Y DOLAR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  */
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
                precio = Float.parseFloat(camp.getPRECIO());
                ganancia = Float.parseFloat(camp.getGANANCIA());
                moneda = camp.getMONEDA();

                precioUniPartida = calculaPrecio(moneda, ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

             

                camp.setSIMULADOR_PRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

            }
        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    //ajustado al nuevo calculo
    public String simuladorPrecio2() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();
            float PrecioFinal = 0;
            float precio = 0;
            String moneda = "";
            float ganancia = 0;

            if (camp.getPRECIO().length() > 0 && camp.getGANANCIA().length() > 0) {

                /* CALCULO DE IVA Y DOLAR++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  */
                ivacalcula = iva();
                dolarcalcula = dolar();

                /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
                precio = Float.parseFloat(camp.getPRECIO());
                ganancia = Float.parseFloat(camp.getGANANCIA());
                moneda = camp.getMONEDA();

                precioUniPartida = calculaPrecio(moneda, ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);
 

                camp.setSIMULADOR_PRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

                buscarProductos();

            }
        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String actualizarProducto() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            java.util.Date fecha = new Date();         
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YYYY");
            //Constantes.enviaMensajeConsola(dt1.format(fecha));
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
            camp.setFECHA_ACTUALIZA(dt1.format(fecha));

            boolean error1 = false;
            boolean error2 = false;
            boolean error3 = false;
            boolean error4 = false;
            boolean error5 = false;
            boolean error6 = false;
            boolean error7 = false;
            boolean error8 = false;
            boolean error9 = false;
            boolean error10 = false;
            boolean error11 = false;
            boolean error12 = false;

            if (camp.getNO_PARTE().length() > 0) {
                error1 = true;
            } else {
                addFieldError("ERROR1", "Campo requerido");
                error1 = false;
            }
            if (camp.getPRODUCTO().length() > 0) {
                error2 = true;
            } else {
                addFieldError("ERROR2", "Campo requerido");
                error2 = false;
            }
            if (camp.getCATEGORIA_GENERAL().length() > 0) {
                error3 = true;
            } else {
                addFieldError("ERROR3", "Campo requerido");
                error3 = false;
            }
            if (camp.getCATEGORIA().length() > 0) {
                error4 = true;
            } else {
                addFieldError("ERROR4", "Campo requerido");
                error4 = false;
            }
            if (camp.getCVE_SAT().length() > 0) {
                error5 = true;
            } else {
                addFieldError("ERROR5", "Campo requerido");
                error5 = false;
            }
            if (camp.getDESCRIPCION().length() > 0) {
                error6 = true;
            } else {
                addFieldError("ERROR6", "Campo requerido");
                error6 = false;
            }
            if (camp.getMARCA().length() > 0) {
                error7 = true;
            } else {
                addFieldError("ERROR7", "Campo requerido");
                error7 = false;
            }
            if (camp.getUNIDADMEDIDA().length() > 0) {
                error8 = true;
            } else {
                addFieldError("ERROR8", "Campo requerido");
                error8 = false;
            }

            if (camp.getPROVEEDOR().length() > 0) {
                error9 = true;
            } else {
                addFieldError("ERROR9", "Campo requerido");
                error9 = false;
            }

            if (camp.getPRECIO().length() > 0) {
                error10 = true;
            } else {
                addFieldError("ERROR10", "Campo requerido");
                error10 = false;
            }
            if (camp.getMONEDA().length() > 0) {
                error11 = true;
            } else {
                addFieldError("ERROR11", "Campo requerido");
                error11 = false;
            }
            if (camp.getGANANCIA().length() > 0) {
                error12 = true;
            } else {
                addFieldError("ERROR12", "Campo requerido");
                error12 = false;
            }

            if (error1 && error2 && error3 && error4 && error5 && error6 && error7 && error8 && error9 && error10 && error11 && error12) {
                consult.actualizaProductos(camp);
                consult.guardaProductosHist(camp);
                simuladorPrecio();
                addFieldError("ACTUALIZADO", "Se guardo la mofificación con éxito");
            }
            //  buscarProductos();
            simuladorPrecio();

        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String guardaProducto() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            java.util.Date fecha = new Date();
            
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YYYY");
            //Constantes.enviaMensajeConsola(dt1.format(fecha));
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
            camp.setFECHA_ACTUALIZA(dt1.format(fecha));

            boolean error1 = false;
            boolean error2 = false;
            boolean error3 = false;
            boolean error4 = false;
            boolean error5 = false;
            boolean error6 = false;
            boolean error7 = false;
            boolean error8 = false;
            boolean error9 = false;
            boolean error10 = false;
            boolean error11 = false;
            boolean error12 = false;

            if (camp.getNO_PARTE().length() > 0) {
                error1 = true;
            } else {
                addFieldError("ERROR1", "Campo requerido");
                error1 = false;
            }
            if (camp.getPRODUCTO().length() > 0) {
                error2 = true;
            } else {
                addFieldError("ERROR2", "Campo requerido");
                error2 = false;
            }
            if (camp.getCATEGORIA_GENERAL().length() > 0) {
                error3 = true;
            } else {
                addFieldError("ERROR3", "Campo requerido");
                error3 = false;
            }
            if (camp.getCATEGORIA().length() > 0) {
                error4 = true;
            } else {
                addFieldError("ERROR4", "Campo requerido");
                error4 = false;
            }
            if (camp.getCVE_SAT().length() > 0) {
                error5 = true;
            } else {
                addFieldError("ERROR5", "Campo requerido");
                error5 = false;
            }
            if (camp.getDESCRIPCION().length() > 0) {
                error6 = true;
            } else {
                addFieldError("ERROR6", "Campo requerido");
                error6 = false;
            }
            if (camp.getMARCA().length() > 0) {
                error7 = true;
            } else {
                addFieldError("ERROR7", "Campo requerido");
                error7 = false;
            }
            if (camp.getUNIDADMEDIDA().length() > 0) {
                error8 = true;
            } else {
                addFieldError("ERROR8", "Campo requerido");
                error8 = false;
            }

            if (camp.getPROVEEDOR().length() > 0) {
                error9 = true;
            } else {
                addFieldError("ERROR9", "Campo requerido");
                error9 = false;
            }

            if (camp.getPRECIO().length() > 0) {
                error10 = true;
            } else {
                addFieldError("ERROR10", "Campo requerido");
                error10 = false;
            }
            if (camp.getMONEDA().length() > 0) {
                error11 = true;
            } else {
                addFieldError("ERROR11", "Campo requerido");
                error11 = false;
            }
            if (camp.getGANANCIA().length() > 0) {
                error12 = true;
            } else {
                addFieldError("ERROR12", "Campo requerido");
                error12 = false;
            }

            if (error1 && error2 && error3 && error4 && error5 && error6 && error7 && error8 && error9 && error10 && error11 && error12) {

                //Constantes.enviaMensajeConsola("el precio calculado es " + camp.getSIMULADOR_PRECIO());
                consult.guardaProductos(camp);
                consult.guardaProductosHist(camp);

                camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
                camp.setANAQUEL("0");
                camp.setNIVEL("A");
                camp.setCANTIDAD_LLEGADA("0");
                camp.setBODEGA("LOCAL");
                consult.guardaProductosBodega(camp);

                simuladorPrecio();
                addFieldError("ACTUALIZADO", "Se guardo el producto con éxito");

            }

            simuladorPrecio();

        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String stockProductos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            ivacalcula = iva();
            dolarcalcula = dolar();

            ListaProductosGral = consult.productosGral();

            Iterator cge = ListaProductosGral.iterator();

            productosBean pb;

            float precioCal;
            float precio;
            float ganancia=0;

            while (cge.hasNext()) {
                pb = (productosBean) cge.next();
                precio = Float.parseFloat(pb.getPRECIO());
                
                ganancia = Float.parseFloat(pb.getGANANCIA());
                precioUniPartida = calculaPrecio(pb.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

              

                pb.setPRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));
               

               

            }

        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String registroPedidos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            ivacalcula = iva();
            dolarcalcula = dolar();
            camp.setIVA(String.valueOf(ivacalcula));
            camp.setDOLAR(String.valueOf(dolarcalcula));

            String fecha;
            // ListaCarritoPedidos.clear();
            // ListaBuscarProducto.clear();
            // ListaBuscarProductoFinal.clear();

            ListaBuscarProducto = consult.productosBuscar(camp);

            float precioCal;
            float precio;
            float ganancia;

            ListaBuscarProductoFinal = consult.productosBuscarFinal(camp);

            
            ListaSelectProvee = consult.selectProvee(camp);

            ListaProductoAlt = consult.productosBuscarAlt(camp);

            Iterator cgeAlt = ListaProductoAlt.iterator();

            productosBean pbAlt;

            precioCal = 0;
            precio = 0;
            ganancia = 0;

            while (cgeAlt.hasNext()) {
                pbAlt = (productosBean) cgeAlt.next();
                precio = Float.parseFloat(pbAlt.getPRECIO());
                ganancia = Float.parseFloat(pbAlt.getGANANCIA());
                precioUniPartida = calculaPrecio(pbAlt.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

              

                pbAlt.setPRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));
             
                
            }

            ListaPedidosFaltantes = consult.pedidosFaltantes(camp);

            buscarProductos();

        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String registroPedidos2() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            ListaCarritoPedidos.clear();
            ListaBuscarProducto.clear();
            ListaBuscarProductoFinal.clear();
            camp.setID_PEDIDO("");
            camp.setMAX_AUXPEDIDO("");
            camp.setMAX_PEDIDO("");
            camp.setMAX_PRODUCT("");
            camp.setCONSULTA_PARTE("");

            ListaPedidosFaltantes = consult.pedidosFaltantes(camp);

            // ListaBuscarProducto = consult.productosBuscar(camp);
        } catch (Exception e) {
           System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String fecha() {
        String fechaHoy;

        java.util.Date fecha = new Date();
      
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-YYYY");
        fechaHoy = dt1.format(fecha);

        return fechaHoy;
    }

    public String guardaPedidos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            String fecha = "";
            boolean cantidad = false;
            boolean provee = false;

            fecha = fecha();
            camp.setFECHA_ACTUALIZA(fecha);

            ListaContadoresPedidos = consult.contadoresPedidos(camp);
            //  //Constantes.enviaMensajeConsola("PROVEEDOR+++++++++++++++++++++++"+camp.getPROVEEDOR());
            if (camp.getNO_PRODUCTOS_PEDIDOS().length() >= 1) {
                cantidad = true;

            }
            if (camp.getPROVEEDOR().length() >= 1) {
                provee = true;
            }

            if (cantidad && provee) {
                Iterator cp = ListaContadoresPedidos.iterator();

                productosBean pb;

                int idprod = 0;
                int idpedido = 0;
                int auxpedido = 0;

                while (cp.hasNext()) {

                    pb = (productosBean) cp.next();

                    idprod = Integer.parseInt(pb.getMAX_PRODUCT());

                    auxpedido = Integer.parseInt(pb.getMAX_AUXPEDIDO());

                    idpedido = Integer.parseInt(pb.getMAX_PEDIDO());

                }

                //      //Constantes.enviaMensajeConsola("action.Consultas_Action.guardaPedidos()");
                camp.setMAX_PRODUCT(String.valueOf(idprod + 1));

                if (camp.getMAX_AUXPEDIDO().length() > 0) {
                    camp.setMAX_AUXPEDIDO("");
                    camp.setMAX_AUXPEDIDO(String.valueOf(auxpedido));
                    camp.setID_PEDIDO(String.valueOf(idpedido));

                } else {
                    camp.setMAX_AUXPEDIDO("");
                    camp.setMAX_AUXPEDIDO(String.valueOf(auxpedido + 1));

                    camp.setID_PEDIDO(String.valueOf(idpedido + 1));
                }

                consult.guardaPedidos(camp);

                camp.setNO_PRODUCTOS_PEDIDOS("");
                camp.setPROVEEDOR("");
                camp.setNO_PARTE("");
                camp.setPRODUCTO("");

                registroPedidos();

                ListaCarritoPedidos = consult.carritoPedidos(camp);

            }

        } catch (Exception e) {
          System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String pedidosProductoBorrar() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();
            //Constantes.enviaMensajeConsola("action.Consultas_Action.pedidosProductoBorrar()" + camp.getID_PRODUCT());

            consult.pedidosProductosBorrar(camp);

            ListaCarritoPedidos = consult.carritoPedidos(camp);
            ListaBuscarProducto.clear();

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String pedidoGuardar() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();

            banguarda = false;

            camp.setESTATUS_PEDIDO("PEDIDO");
            //Constantes.enviaMensajeConsola("el pedido es:" + camp.getID_PEDIDO());

            consult.pedidoGuardar(camp);
            registroPedidos();

            ListaCarritoPedidos = consult.carritoPedidos(camp);
            foliopedido = true;

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String compras() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaPedidosPendientes = consult.pedidosPendientes(camp);
            ListaPedidosFaltantes = consult.pedidosFaltantes(camp);

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String comprasBuscar() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaPedidosBuscar = consult.pedidosBuscar(camp);
            Iterator LPB = ListaPedidosBuscar.iterator();

            productosBean obj;
            int cantidad = 0;
            int llegada = 0;
            while (LPB.hasNext()) {
                obj = (productosBean) LPB.next();
                cantidad = 0;
                llegada = 0;
                cantidad = Integer.parseInt(obj.getCANTIDAD());
                llegada = Integer.parseInt(obj.getCANTIDAD_LLEGADA());

                if (cantidad == llegada) {

                    obj.setAGREGAR("SI");

                }

            }

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String borrarPedidoProducto() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            consult.borrarPedidoProducto(camp);
            comprasBuscar();
            ListaPedidosPendientes = consult.pedidosPendientes(camp);

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String ingresoM() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            ListaPedidosPendientes = consult.pedidosPendientes(camp);

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String pedidosBuscar() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaPedidosBuscar = consult.pedidosBuscar(camp);
            Iterator LPB = ListaPedidosBuscar.iterator();

            productosBean obj;
            int cantidad = 0;
            int llegada = 0;
            while (LPB.hasNext()) {
                obj = (productosBean) LPB.next();
                cantidad = 0;
                llegada = 0;
                cantidad = Integer.parseInt(obj.getCANTIDAD());
                llegada = Integer.parseInt(obj.getCANTIDAD_LLEGADA());

                if (cantidad == llegada) {

                    obj.setAGREGAR("SI");

                }

            }

            if (ListaPedidosBuscar.size() <= 0) {

            }

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String preingresoAlmacen() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaActEntPedidos = consult.pedidosBuscarAct(camp);

            Iterator LAEP = ListaActEntPedidos.iterator();

            productosBean pB;

            while (LAEP.hasNext()) {
                pB = (productosBean) LAEP.next();
                camp.setPARTEAUX(pB.getNO_PARTE());
                camp.setANAQUEL(pB.getANAQUEL());
                camp.setNIVEL(pB.getNIVEL());

            }

            ListaBuscarProducto = consult.productosBuscar(camp);
            //  camp.setCONSULTA_PARTE(camp.getNO_PARTE());

            ListaAnaquel = consult.anaquel(camp);

            ListaNivel = consult.nivel(camp);

            //Constantes.enviaMensajeConsola("LA UBICACIÓN DEL PRODUCTO ES +++++++++++++" + camp.getANAQUEL() + camp.getNIVEL());

        } catch (Exception e) {
             System.out.println(e);
        }

        return "SUCCESS";
    }

    public String ingresoAlmacen() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;
            fecha = fecha();
            camp.setFECHA_LLEGADA(fecha);
            camp.setESTATUS_PEDIDO("ALMACEN");
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

            ListaValorLlegada = consult.valorLlegada(camp);

            if (ListaValorLlegada.size() > 0) {

                Iterator LVL = ListaValorLlegada.iterator();
                productosBean obj4;
                int valorPedido = 0;
                int valorIngresado = 0;
                while (LVL.hasNext()) {
                    obj4 = (productosBean) LVL.next();

                    valorPedido = Integer.parseInt(obj4.getCANTIDAD_LLEGADA());

                }

                valorIngresado = Integer.parseInt(camp.getCANTIDAD_LLEGADA());

                //HISTORIA DE INGRESO DEL PRODUCTO A BODEGAS
                consult.GuardaHistoriaBodega(camp);

                camp.setCANTIDAD_LLEGADA(String.valueOf(valorPedido + valorIngresado));

                consult.guardaLlegadaP(camp);

                ListaConsultaBodega = consult.consultaBodega(camp);

                if (ListaConsultaBodega.size() > 0) {
                    Iterator LCB = ListaConsultaBodega.iterator();

                    productosBean pB;
                    int totalBodega = 0;
                    int totalBodegainicial = 0;
                    String Bodega = null;

                    while (LCB.hasNext()) {
                        pB = (productosBean) LCB.next();

                        totalBodega = valorIngresado + Integer.parseInt(pB.getCATIDAD());
                        totalBodegainicial = Integer.parseInt(pB.getCATIDAD());
                        Bodega = pB.getNAME_BODEGA();

                    }

                    camp.setCANTIDAD(String.valueOf(totalBodega));

                    if (Bodega.length() > 0) {
                        camp.setBODEGA(String.valueOf(Bodega));
                    }
                    if (Bodega.length() > 0) {

                        consult.ActualizaBodegaStock(camp);

                    }
                } else {
                    //Constantes.enviaMensajeConsola("Entre a insertar");

                    consult.GuardaPBodega(camp);
                }

                //Constantes.enviaMensajeConsola("SALI DE LA ACTUALIZACION DE LLEGADA DE PRODUCTOS");

            } else {
                consult.GuardaHistoriaBodega(camp);
                consult.guardaLlegadaP(camp);

                ListaConsultaBodega = consult.consultaBodega(camp);

                if (ListaConsultaBodega.size() > 0) {
                    Iterator LCB = ListaConsultaBodega.iterator();

                    productosBean pB;
                    int totalBodega = 0;
                    int totalBodegainicial = 0;
                    String Bodega = null;

                    while (LCB.hasNext()) {
                        pB = (productosBean) LCB.next();

                        totalBodega = Integer.parseInt(camp.getCANTIDAD_LLEGADA()) + Integer.parseInt(pB.getCATIDAD());
                        totalBodegainicial = Integer.parseInt(pB.getCATIDAD());
                        Bodega = pB.getNAME_BODEGA();

                    }

                    camp.setCANTIDAD(String.valueOf(totalBodega));

                    if (Bodega.length() > 0) {
                        camp.setBODEGA(String.valueOf(Bodega));
                    }
                    if (Bodega.length() > 0) {

                        consult.ActualizaBodegaStock(camp);

                    }
                } else {
                    //Constantes.enviaMensajeConsola("Entre a insertar");

                    consult.GuardaPBodega(camp);
                }

            }

            comprasBuscar();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String inventario() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaAnaquel = consult.anaquel(camp);

            ListaNivel = consult.nivel(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String inventarioLista() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaInventario = consult.inventarioLista(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String ventaNueva() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            
            inicializaVentaCot();

            ListaInventario = consult.inventarioLista(camp);

            camp.setACTIVA_VENTA("");

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String buscarProductosVenta() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            if (camp.getAUX_RFC_CLIENTE().length() > 0) {
                
                  ivacalcula = iva();
                  dolarcalcula = dolar();

                DecimalFormat formateador = new DecimalFormat("###,###.##");
                ListaBuscarProducto = consult.productosBuscar(camp);
                Constantes.enviaMensajeConsola("ListaBuscarProducto" + ListaBuscarProducto.size());

                Iterator LBP = ListaBuscarProducto.iterator();
                productosBean objB;

                int casa = 0;
                int tenango = 0;
                int local = 0;

                while (LBP.hasNext()) {
                    objB = (productosBean) LBP.next();

                    if (objB.getNAME_BODEGA().equals("LOCAL")) {

                        local = local + Integer.parseInt(objB.getCATIDAD());
                    }

                    if (objB.getNAME_BODEGA().equals("CASA")) {

                        casa = casa + Integer.parseInt(objB.getCATIDAD());
                    }

                    if (objB.getNAME_BODEGA().equals("TENANGO")) {

                        tenango = tenango + Integer.parseInt(objB.getCATIDAD());
                    }

                }
                
                Constantes.enviaMensajeConsola("sALI DE LA CONSULTA");

                camp.setTOTAL_PRODUCTO_BODEGAS(String.valueOf(local + casa + tenango));
                
                  
                ListaCotizaHist = consult.cotizaHist(camp);
                Constantes.enviaMensajeConsola("ListaCotizaHist" + ListaCotizaHist.size());
                
               

                ListaBuscarProductoFinal = consult.productosBuscarFinal(camp);
                actprod = false;
                regprod = false;

                if (ListaBuscarProducto.size() <= 0) {
                    actprod2 = true;

                    ListaBuscarProductoLike = consult.productosBuscarLike(camp);

                }

                if (ListaBuscarProductoFinal.size() > 0) {
                    actprod = true;
                    regprod = false;

                }

                if (ListaBuscarProductoFinal.size() <= 0) {
                    actprod = false;
                    regprod = true;
                    limpiar();
                    camp.setNO_PARTE(camp.getCONSULTA_PARTE());

                }

                float precioCal = 0;
                float precioCal2 = 0;
                double precio = 0;
                float ganancia = 0;
                double incremento=0;

                int cantidad = 0;
                if (camp.getCANTIDAD_VENTA() != null) {
                    cantidad = Integer.parseInt(camp.getCANTIDAD_VENTA());
                } else {
                    cantidad = 1;
                }

                Constantes.enviaMensajeConsola("la cantidad de productos a vender es:");
                Iterator cgeBP = ListaBuscarProductoFinal.iterator();
                productosBean pbBP;

                while (cgeBP.hasNext()) {
                    pbBP = (productosBean) cgeBP.next();

                    precioCal = 0;
                    precioCal2 = 0;

                    camp.setNO_PARTE(pbBP.getNO_PARTE());
                    camp.setPRODUCTO(pbBP.getPRODUCTO());
                    camp.setCATEGORIA(pbBP.getCATEGORIA());
                    camp.setDESCRIPCION(pbBP.getDESCRIPCION());
                    camp.setPRECIO(pbBP.getPRECIO());
                    precio = Double.parseDouble(pbBP.getPRECIO());

                    if (pbBP.getGANANCIA().equals("0")) {
                        ganancia = Float.parseFloat("0.32");

                    } else {
                        ganancia = Float.parseFloat(pbBP.getGANANCIA());
                    }
                    
                    incremento=camp.getINCREMENTO();
                    

                    precioUniPartida = calculaPrecio(pbBP.getMONEDA(), ganancia, precio, incremento, cantidad, ivacalcula, dolarcalcula );
                     //precioCal2 = precioUniPartida.getPRECIO_UNITARIO_TOTAL()* cantidad;

                   
                    camp.setTOTAL_PRODUCTO_VENTA(String.valueOf(precioUniPartida.getPRECIO_PARTIDA_TOTAL()));
                  camp.setPRECIO_PESO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));
                    camp.setPRECIO_UNITARIO(precioUniPartida.getPRECIO_UNIARIO());
                    camp.setIVA_UNITARIO(precioUniPartida.getIVA_UNITARIO());
                    camp.setPRECIO_PARTIDA(precioUniPartida.getPRECIO_PARTIDA());
                    camp.setIVA_PARTIDA(precioUniPartida.getIVA_PARTIDA());
                    camp.setPRECIO_PARTIDA_TOTAL(precioUniPartida.getPRECIO_PARTIDA_TOTAL());
                    camp.setPRECIO_UNITARIO_TOTAL(precioUniPartida.getPRECIO_UNITARIO_TOTAL());

                    Constantes.enviaMensajeConsola("+++++++++PRECIOS UNITARIOS++++++++++++++");
                    Constantes.enviaMensajeConsola("PRECIO UNITARIO: " + camp.getPRECIO_UNITARIO());
                    Constantes.enviaMensajeConsola("IVA UNITARIO: " + camp.getIVA_UNITARIO());
                    Constantes.enviaMensajeConsola("PRECIO UNITARIO TOTAL" + camp.getPRECIO_UNITARIO_TOTAL());

                    Constantes.enviaMensajeConsola("+++++++++PRECIOS PARTIDAS++++++++++++++");
                    Constantes.enviaMensajeConsola("PRECIO PARTIDA: " + camp.getPRECIO_PARTIDA());
                    Constantes.enviaMensajeConsola("IVA PARTIDA: " + camp.getIVA_PARTIDA());
                    Constantes.enviaMensajeConsola("PRECIO PARTIDA TOTAL " + camp.getPRECIO_PARTIDA_TOTAL());

                    camp.setCATEGORIA(pbBP.getCATEGORIA());
                    camp.setMARCA(pbBP.getMARCA());
                    camp.setUNIDADMEDIDA(pbBP.getUNIDADMEDIDA());
                    camp.setMONEDA(pbBP.getMONEDA());
                    camp.setALTERNATIVO(pbBP.getALTERNATIVO());
                    camp.setPRECIO_PESO(pbBP.getPRECIO_PESO());
                    camp.setCATEGORIA(pbBP.getCATEGORIA());
                    camp.setPRECIO_CAL(pbBP.getPRECIO_CAL());

                    camp.setRESPONSABLE(pbBP.getRESPONSABLE());
                    camp.setPROVEEDOR(pbBP.getPROVEEDOR());
                    camp.setCATEGORIA(pbBP.getCATEGORIA());
                    camp.setCATEGORIA_GENERAL(pbBP.getCATEGORIA_GENERAL());

                }

                ListaCarroCotizacion = consult.carroCotizacion(camp);

                ListaProductoHist = consult.productosBuscarHist(camp);

                if (ListaProductoHist.size() > 0) {

                    Constantes.enviaMensajeConsola("ENTRE A CONSULTAR LA HISTORIA DEL PRODUCTO");
                    Iterator cge = ListaProductoHist.iterator();

                    productosBean pb;

                    while (cge.hasNext()) {

                        precioCal = 0;
                        precioCal2 = 0;
                        pb = (productosBean) cge.next();
                        precio = Float.parseFloat(pb.getPRECIO());
                        ganancia = Float.parseFloat(pb.getGANANCIA());

                        precioUniPartida = calculaPrecio(pb.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), cantidad, ivacalcula, dolarcalcula);
                        pb.setPRECIO_PESO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

                    }

                }

                ListaProductoAlt = consult.productosBuscarAlt(camp);
                 Constantes.enviaMensajeConsola("SALI DE CONSULTAR LOS PRODUCTOS ALTERNATIVOS ");

                if (ListaProductoAlt.size() > 0) {
                    Iterator cgeAlt = ListaProductoAlt.iterator();

                    productosBean pbAlt;

                    precioCal = 0;
                    precio = 0;
                    ganancia = 0;

                    while (cgeAlt.hasNext()) {
                        pbAlt = (productosBean) cgeAlt.next();
                        precio = Float.parseFloat(pbAlt.getPRECIO());

                        if (pbAlt.getGANANCIA().equals("0")) {
                            ganancia = Float.parseFloat("0.32");

                        } else {
                            ganancia = Float.parseFloat(pbAlt.getGANANCIA());
                        }

                        precioUniPartida = calculaPrecio(pbAlt.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), cantidad, ivacalcula, dolarcalcula);
                        pbAlt.setPRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

                    }
                      Constantes.enviaMensajeConsola("SALI DE CONSULTAR EL PRECIO DE LOS PRODUCTOS ALTERATIVOS  ");
                }
                
             

                String fecha;
                fecha = fecha();
                camp.setFECHA_COTIZA(fecha);
                camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
                
                 Constantes.enviaMensajeConsola("TERMINE DE CONSULTAR EL PRODUCTO  ");

               return "SUCCESS";
            }
        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String asignarCliente() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaClientes.clear();
            buscarProductosVenta();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String guardaProductoVenta() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            String fecha;
            fecha = fecha();
            camp.setFECHA_COTIZA(fecha);
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());
          
            ListaProductoAlt = consult.productosBuscarAlt(camp);

            int AUXCOTIZA = 0;
            double total_compara=0.0; 
            int cantidad=0;
            double diferencia=0.0;
            if (camp.getAUXCOTIZA().length() > 0) {
                
              cantidad=Integer.parseInt(camp.getCANTIDAD_VENTA());
              total_compara=camp.getPRECIO_UNITARIO_TOTAL()*cantidad;
              diferencia=camp.getPRECIO_PARTIDA_TOTAL()-total_compara;
              
                if(diferencia<=1){              
               
               // Constantes.enviaMensajeConsola("ENTRE A GRADAR LAS VENTAS");
                consult.guardaProductoVenta(camp);
                camp.setPRECIO_PESO("");
                camp.setCANTIDAD_VENTA("");
                camp.setTOTAL_PRODUCTO_VENTA("");
                camp.setCONSULTA_PARTE("");
                camp.setNO_PARTE("");
                camp.setNO_PARTE("");
                camp.setPRODUCTO("");
                camp.setDESCRIPCION("");
                camp.setPRECIO_PESO("");
                camp.setMARCA("");
                camp.setPRECIO_UNITARIO(0);
                camp.setPRECIO_UNIARIO2(0);
                camp.setIVA_UNITARIO(0);
                camp.setPRECIO_PARTIDA(0);
                camp.setIVA_PARTIDA(0);
                camp.setPRECIO_UNITARIO_TOTAL(0);
                camp.setPRECIO_PARTIDA_TOTAL(0);
                 }
                else{
                    addFieldError("error", "No se pudo agregar el producto, favor de intentarlo de nuevo");
                }

            } else {

                AUXCOTIZA = Integer.parseInt(consult.auxCotiza());
                AUXCOTIZA = AUXCOTIZA + 1;
                camp.setAUXCOTIZA(String.valueOf(AUXCOTIZA));
                //Constantes.enviaMensajeConsola("ENTRE A GRADAR LAS VENTAS 2");
                
                 cantidad=Integer.parseInt(camp.getCANTIDAD_VENTA());
              total_compara=camp.getPRECIO_UNITARIO_TOTAL()*cantidad;
              diferencia=camp.getPRECIO_PARTIDA_TOTAL()-total_compara;
                
                  if(diferencia<=1){      
                
                consult.guardaProductoVenta(camp);

                camp.setPRECIO_PESO("");
                camp.setCANTIDAD_VENTA("");
                camp.setTOTAL_PRODUCTO_VENTA("");
                camp.setCONSULTA_PARTE("");
                camp.setNO_PARTE("");
                camp.setNO_PARTE("");
                camp.setPRODUCTO("");
                camp.setDESCRIPCION("");
                camp.setPRECIO_PESO("");
                camp.setMARCA("");
                 camp.setPRECIO_UNITARIO(0);
                camp.setPRECIO_UNIARIO2(0);
                camp.setIVA_UNITARIO(0);
                camp.setPRECIO_PARTIDA(0);
                camp.setIVA_PARTIDA(0);
                camp.setPRECIO_UNITARIO_TOTAL(0);
                camp.setPRECIO_PARTIDA_TOTAL(0);
                  }
                     else{
                  addFieldError("error", "No se pudo agregar el producto, favor de intentarlo de nuevo");

                }

            }

            ListaCarroCotizacion = consult.carroCotizacion(camp);

            if (ListaCarroCotizacion.size() > 0) {

                double total_cotiza = 0;
                double total_individual = 0;
                Iterator LCC = ListaCarroCotizacion.iterator();

                productosBean LLCB;

                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();
                   
                   total_cotiza = total_cotiza + LLCB.getPRECIO_PARTIDA_TOTAL();

                }

                camp.setTOTAL_COTIZACION(String.valueOf(total_cotiza));

                if (camp.getNO_COTIZACION().length() > 0) {
                    consult.actualizarNoCotiza(camp);
                }

            }
            camp.setCANTIDAD_VENTA("1");

            ListaBuscarProducto.clear();
        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String generaCotizacion() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            int no_cotiza = 0;
            if (camp.getNO_COTIZACION().length() <= 0) {
                no_cotiza = Integer.parseInt(consult.noCotiza()) + 1;

                camp.setNO_COTIZACION(String.valueOf(no_cotiza));
            }
            consult.actualizarNoCotiza(camp);

            ListaCarroCotizacion = consult.carroCotizacion(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String borrarProducto() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            //Constantes.enviaMensajeConsola("SE BORRARA EL ARTICULO" + camp.getBORRARPRODUCTO());
            consult.borrarProducto(camp);

            ListaCarroCotizacion = consult.carroCotizacion(camp);

            if (ListaCarroCotizacion.size() > 0) {

                double total_cotiza = 0;
                double total_individual = 0;
                Iterator LCC = ListaCarroCotizacion.iterator();

                productosBean LLCB;

                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();
                    total_individual = LLCB.getPRECIO_FINAL();
                    total_cotiza = total_cotiza + total_individual;

                }
                camp.setTOTAL_COTIZACION(String.valueOf(total_cotiza));
            }

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String borrarProductoVenta() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            camp.setNO_PARTE_VENTA(camp.getNO_PARTE());
            ListaBuscarProducto = consult.productosBuscarVenta(camp);

            int local = 0;
            int casa = 0;
            int tenango = 0;
            int nuv_stok = 0;
            int aux_stok = 0;

            for (int p = 0; p < ListaBuscarProducto.size(); p++) {

                if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("LOCAL")) {
                    local = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                    //Constantes.enviaMensajeConsola("Local:" + local);
                }

                if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("CASA")) {
                    casa = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                    //Constantes.enviaMensajeConsola("Casa:" + casa);
                }

                if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("TENANGO")) {
                    tenango = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                    //Constantes.enviaMensajeConsola("Tenango:" + tenango);
                }

            }

            nuv_stok = local + Integer.parseInt(camp.getNO_PRODUCTOVENTA());

            camp.setNUEVOSTOK(String.valueOf(nuv_stok));

            //Constantes.enviaMensajeConsola("nuevoStock" + camp.getNUEVOSTOK());

            consult.actualizaStockCancela(camp);

            consult.borrarProductoVenta(camp);

            consultaVentaCancela();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    //ajustado para nuevo calculo
    public String generarVenta() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();           

            conecta = consult.crearConexion();
            //statement
            objConexion = consult.crearStatement(conecta);

            //  DecimalFormat df = new DecimalFormat("#");
            String fecha = fecha();
            // FECHA DEL SISTEMA
            camp.setFECHA_VENTA(fecha);
            // RESPONSABLE DE LA VENTA
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

            int nV = Integer.parseInt(consult.noVenta()) + 1;
            camp.setNO_VENTA(String.valueOf(nV));

            // Consulta de Cotización
            ListaCarroCotizacion = consult.carroCotizacion(camp);
            int c;
            int local = 0;
            int casa = 0;
            int tenango = 0;
            int nuv_stok = 0;
            int aux_stok = 0;         
            int p = 0;
            int vendidos = 0;
         
            int total_bodegas=0;


            for (c = 0; c < ListaCarroCotizacion.size(); c++) {
               
               
                camp.setTOTAL_PRODUCTO_VENTA(String.valueOf(ListaCarroCotizacion.get(c).getPRECIO_PARTIDA_TOTAL()));
                camp.setNO_PARTE_VENTA(ListaCarroCotizacion.get(c).getNO_PARTE());
                camp.setPRECIO_UNITARIO(ListaCarroCotizacion.get(c).getPRECIO_UNITARIO());
                camp.setIVA_UNITARIO(ListaCarroCotizacion.get(c).getIVA_UNITARIO());
                camp.setPRECIO_PARTIDA(ListaCarroCotizacion.get(c).getPRECIO_PARTIDA());
                camp.setIVA_PARTIDA(ListaCarroCotizacion.get(c).getIVA_PARTIDA());
                camp.setPRECIO_UNITARIO_TOTAL(ListaCarroCotizacion.get(c).getPRECIO_UNITARIO_TOTAL());
                camp.setPRECIO_PARTIDA_TOTAL(ListaCarroCotizacion.get(c).getPRECIO_PARTIDA_TOTAL());
                camp.setNO_PRODUCTOVENTA(ListaCarroCotizacion.get(c).getNO_PRODUCTOCOTIZA());
                camp.setNO_COTIZA(ListaCarroCotizacion.get(c).getNO_COTIZA());
                camp.setAUX_RFC_CLIENTE(ListaCarroCotizacion.get(c).getRFC_CLIENTE());              

                 p = 0;

                vendidos = 0;

                camp.setNO_PARTE_VENTA(ListaCarroCotizacion.get(c).getNO_PARTE());
                vendidos = Integer.parseInt(ListaCarroCotizacion.get(c).getNO_PRODUCTOCOTIZA());

                // TOTAL DEL PRODUCTO EN BODEGAS
                ListaBuscarProducto = consult.productosBuscarVenta(camp);

                local = 0;
                casa = 0;
                tenango = 0;
                nuv_stok = 0;
                aux_stok = 0;
               total_bodegas=0;

                for (p = 0; p < ListaBuscarProducto.size(); p++) {

                    if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("LOCAL")) {
                        local = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                        //Constantes.enviaMensajeConsola("Local:" + local);
                    }

                    if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("CASA")) {
                        casa = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                      //  //Constantes.enviaMensajeConsola("Casa:" + casa);
                    }

                    if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("TENANGO")) {
                        tenango = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                       // //Constantes.enviaMensajeConsola("Tenango:" + tenango);
                    }

                }
                

                
                //total de productos en refac
                 total_bodegas=local+casa+tenango;               
                
                
                 
               // se determina el tipo de entrega                
                if(local >= Integer.parseInt(camp.getNO_PRODUCTOVENTA())){
                     camp.setENTREGA("INMEDIATA");
                }
                else{
                    if(total_bodegas >= Integer.parseInt(camp.getNO_PRODUCTOVENTA())){
                         camp.setENTREGA("TRASPASO");
                    }
                    
                    else{
                         camp.setENTREGA("POR DEFINIR");
                    }
                    
                }
                
                  consult.guardaVentaCiclo(conecta, objPreConexion, camp);

                ////// TERMINA TOTAL DE PRODUCTOS POR BODEGA         
                if (local >= vendidos) {
                    nuv_stok = local - vendidos;
                    camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                    camp.setBODEGA("LOCAL");
                    camp.setENTREGA("INMEDIATA");
                    camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                    consult.actualizaStok(conecta, objPreConexion, camp);                  
                    consult.historiaVenta(conecta, objPreConexion, camp);
                } else {

                    aux_stok = vendidos - local;
                    nuv_stok = 0;
                    camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                    camp.setBODEGA("LOCAL");
                    camp.setENTREGA("TRASPASO");
                      camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                    consult.actualizaStok(conecta, objPreConexion, camp);                  
                    consult.historiaVenta(conecta, objPreConexion, camp);
                   

                    if (casa >= aux_stok) {

                        nuv_stok = casa - aux_stok;
                          camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                        camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                        camp.setBODEGA("CASA");
                        consult.actualizaStok(conecta, objPreConexion, camp);
                          consult.historiaVenta(conecta, objPreConexion, camp);
                        if (aux_stok > 0) {
                            camp.setCANTIDAD_TRAER(String.valueOf(aux_stok));
                            consult.productoTraer(conecta, objPreConexion, camp);
                        }                      

                    } else {

                        aux_stok = aux_stok - casa;
                        nuv_stok = 0;
                        camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                        camp.setBODEGA("CASA");
                         camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                        consult.actualizaStok(conecta, objPreConexion, camp);
                         consult.historiaVenta(conecta, objPreConexion, camp);
                        if (casa > 0) {
                            camp.setCANTIDAD_TRAER(String.valueOf(casa));
                            consult.productoTraer(conecta, objPreConexion, camp);
                        }
                     

                        if (tenango >= aux_stok) {

                            nuv_stok = tenango - aux_stok;
                            camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                            camp.setBODEGA("TENANGO");
                            camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                            consult.actualizaStok(conecta, objPreConexion, camp);
                            consult.historiaVenta(conecta, objPreConexion, camp);
                            if (aux_stok > 0) {
                                camp.setCANTIDAD_TRAER(String.valueOf(aux_stok));
                                consult.productoTraer(conecta, objPreConexion, camp);
                            }
                           

                        } else {
                            aux_stok = aux_stok - tenango;
                            nuv_stok = 0;
                            camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                            camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                            camp.setBODEGA("TENANGO");
                            consult.actualizaStok(conecta, objPreConexion, camp);
                            consult.historiaVenta(conecta, objPreConexion, camp);
                            if (tenango > 0) {
                                camp.setCANTIDAD_TRAER(String.valueOf(tenango));
                                consult.productoTraer(conecta, objPreConexion, camp);
                            }
                           

                            if (aux_stok > 0) {
                                camp.setBODEGA("COMPRAR");
                                camp.setENTREGA("POR DEFINIR");
                                camp.setCANTIDAD_TRAER(String.valueOf(aux_stok));
                                camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                                 consult.historiaVenta(conecta, objPreConexion, camp);
                                consult.productoTraer(conecta, objPreConexion, camp);
                               
                            }

                        }

                    }

                }

            }

            cierraConexiones();
            consult.actualizaEstadoCotiza(camp);            
            traerProducto();           
           limpiarVenta();
            //Constantes.enviaMensajeConsola("termine de guardar la venta ");
        } catch (Exception e) {
            
            System.out.println(e);
        }

        return "SUCCESS";
    }
    
    public void limpiarVenta(){
        
        ListaProductoAlt=null;
        ListaProductoHist=null;
        ListaBuscarProducto=null;
        ListaPedidosBuscar=null;
        ListaBuscarProductoLike=null;
        
    }
    
     public void inicializaVentaCot(){
        
        ListaProductoAlt=null;
        ListaProductoHist=null;
        ListaBuscarProducto=null;
        ListaPedidosBuscar=null;
        ListaBuscarProductoLike=null;
        ListaCarritoPedidos=null;
        ListaCarroCotizacion=null;
       camp.setNO_VENTA("");
       camp.setNO_COTIZA("");
       camp.setNO_COTIZACION("");
       camp.setRFC_CLIENTE(null);
       camp.setAUX_RFC_CLIENTE(null);
        
    }

    public String traerProducto() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaTraerProducto = consult.traerProducto(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }
    
     public String inicioHist() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

           // ListaTraerProducto = consult.traerProducto(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }
     
      public String buscarProductoHistoria() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            
          

          listaHistoriaProducto = consult.buscarProductoHistoria(camp);
          
            //Constantes.enviaMensajeConsola("la lista de historia es de "+ listaHistoriaProducto.size() );

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }
     

    public String consultaCotizacion() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            camp.setNO_COTIZA(camp.getAUXCOTIZA());

            ListaCarroCotizacion = consult.carroConCotizacion(camp);

            if (ListaCarroCotizacion.size() > 0) {

                double total_cotiza = 0;
                double total_individual = 0;
                Iterator LCC = ListaCarroCotizacion.iterator();

                productosBean LLCB;

                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();
                    total_individual = LLCB.getPRECIO_FINAL();
                    total_cotiza = total_cotiza + total_individual;

                }

                camp.setTOTAL_COTIZACION(String.valueOf(total_cotiza));
                //Constantes.enviaMensajeConsola("precio final de la cotizacion es:" + camp.getTOTAL_COTIZACION());

                if (camp.getNO_COTIZACION().length() > 0) {
                    consult.actualizarNoCotiza(camp);
                }

            }

            ListaVentaProducto = consult.consultaVenta(camp);

            if (ListaVentaProducto.size() > 0) {

                Iterator LVP = ListaVentaProducto.iterator();
                productosBean LVPB;

                while (LVP.hasNext()) {
                    LVPB = (productosBean) LVP.next();

                    camp.setNO_VENTA(LVPB.getNO_VENTA());
                    //Constantes.enviaMensajeConsola("EL NUMERO DE VENTA ES:" + LVPB.getNO_VENTA());

                }
                //Constantes.enviaMensajeConsola("EL NUMERO A CONSULTAR DE VENTA ES:" + camp.getNO_VENTA());

            }

        } catch (Exception e) {
              System.out.println(e);
            
          
        }

        return "SUCCESS";
    }

    //agustada para nuevo 
    public String generarVentaCon() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            //Conexión 
            conecta = consult.crearConexion();
            objConexion = consult.crearStatement(conecta);
         

            String fecha = fecha();
            // FECHA DEL SISTEMA
            camp.setFECHA_VENTA(fecha);
            // RESPONSABLE DE LA VENTA
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

            int nV = Integer.parseInt(consult.noVenta()) + 1;
            camp.setNO_VENTA(String.valueOf(nV));

            // Consulta de Cotización
            ListaCarroCotizacion = consult.carroConCotizacion(camp);
            int c;
            int local = 0;
            int casa = 0;
            int tenango = 0;
            int nuv_stok = 0;
            int aux_stok = 0;
            int p = 0;
            int vendidos = 0;
            int total_bodegas=0;

            for (c = 0; c < ListaCarroCotizacion.size(); c++) {

                camp.setNO_PARTE_VENTA(ListaCarroCotizacion.get(c).getNO_PARTE());
                camp.setPRECIO_UNITARIO(ListaCarroCotizacion.get(c).getPRECIO_UNITARIO());
                camp.setIVA_UNITARIO(ListaCarroCotizacion.get(c).getIVA_UNITARIO());
                camp.setPRECIO_PARTIDA(ListaCarroCotizacion.get(c).getPRECIO_PARTIDA());
                camp.setIVA_PARTIDA(ListaCarroCotizacion.get(c).getIVA_PARTIDA());
                camp.setPRECIO_UNITARIO_TOTAL(ListaCarroCotizacion.get(c).getPRECIO_UNITARIO_TOTAL());
                camp.setPRECIO_PARTIDA_TOTAL(ListaCarroCotizacion.get(c).getPRECIO_PARTIDA_TOTAL());
                camp.setNO_PRODUCTOVENTA(ListaCarroCotizacion.get(c).getNO_PRODUCTOCOTIZA());
                camp.setNO_COTIZA(ListaCarroCotizacion.get(c).getNO_COTIZA());
                camp.setAUX_RFC_CLIENTE(ListaCarroCotizacion.get(c).getRFC_CLIENTE());

             

                p = 0;
                vendidos = 0;

                vendidos = Integer.parseInt(camp.getNO_PRODUCTOVENTA());

                // TOTAL DEL PRODUCTO EN BODEGAS
                ListaBuscarProducto = consult.productosBuscarVenta(camp);

                local = 0;
                casa = 0;
                tenango = 0;
                nuv_stok = 0;
                aux_stok = 0;
                total_bodegas=0;

                for (p = 0; p < ListaBuscarProducto.size(); p++) {

                    if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("LOCAL")) {
                        local = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                        //Constantes.enviaMensajeConsola("Local:" + local);
                    }

                    if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("CASA")) {
                        casa = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                        //Constantes.enviaMensajeConsola("Casa:" + casa);
                    }

                    if (ListaBuscarProducto.get(p).getNAME_BODEGA().equals("TENANGO")) {
                        tenango = Integer.parseInt(ListaBuscarProducto.get(p).getCATIDAD());
                        //Constantes.enviaMensajeConsola("Tenango:" + tenango);
                    }

                }
                
                 //total de productos en refac
                 total_bodegas=local+casa+tenango;               
                
                
                 
               // se determina el tipo de entrega                
                if(local>=Integer.parseInt(camp.getNO_PRODUCTOVENTA())){
                     camp.setENTREGA("INMEDIATA");
                }
                else{
                    if(total_bodegas>=Integer.parseInt(camp.getNO_PRODUCTOVENTA())){
                         camp.setENTREGA("TRASPASO");
                    }
                    
                    else{
                         camp.setENTREGA("POR DEFINIR");
                    }
                    
                }
                
                   consult.guardaVentaCiclo(conecta, objPreConexion, camp);

                ////// TERMINA TOTAL DE PRODUCTOS POR BODEGA         
               if (local >= vendidos) {
                    nuv_stok = local - vendidos;
                    camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                    camp.setBODEGA("LOCAL");
                    camp.setENTREGA("INMEDIATA");
                    camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                    consult.actualizaStok(conecta, objPreConexion, camp);                  
                    consult.historiaVenta(conecta, objPreConexion, camp);
                } else {

                    aux_stok = vendidos - local;
                    nuv_stok = 0;
                    camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                    camp.setBODEGA("LOCAL");
                    camp.setENTREGA("TRASPASO");
                      camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                    consult.actualizaStok(conecta, objPreConexion, camp);                  
                    consult.historiaVenta(conecta, objPreConexion, camp);
                   

                    if (casa >= aux_stok) {

                        nuv_stok = casa - aux_stok;
                          camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                        camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                        camp.setBODEGA("CASA");
                        consult.actualizaStok(conecta, objPreConexion, camp);
                          consult.historiaVenta(conecta, objPreConexion, camp);
                        if (aux_stok > 0) {
                            camp.setCANTIDAD_TRAER(String.valueOf(aux_stok));
                            consult.productoTraer(conecta, objPreConexion, camp);
                        }                      

                    } else {

                        aux_stok = aux_stok - casa;
                        nuv_stok = 0;
                        camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                        camp.setBODEGA("CASA");
                         camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                        consult.actualizaStok(conecta, objPreConexion, camp);
                         consult.historiaVenta(conecta, objPreConexion, camp);
                        if (casa > 0) {
                            camp.setCANTIDAD_TRAER(String.valueOf(casa));
                            consult.productoTraer(conecta, objPreConexion, camp);
                        }
                     

                        if (tenango >= aux_stok) {

                            nuv_stok = tenango - aux_stok;
                            camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                            camp.setBODEGA("TENANGO");
                            camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                            consult.actualizaStok(conecta, objPreConexion, camp);
                            consult.historiaVenta(conecta, objPreConexion, camp);
                            if (aux_stok > 0) {
                                camp.setCANTIDAD_TRAER(String.valueOf(aux_stok));
                                consult.productoTraer(conecta, objPreConexion, camp);
                            }
                           

                        } else {
                            aux_stok = aux_stok - tenango;
                            nuv_stok = 0;
                            camp.setNUEVOSTOK(String.valueOf(nuv_stok));
                            camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                            camp.setBODEGA("TENANGO");
                            consult.actualizaStok(conecta, objPreConexion, camp);
                            consult.historiaVenta(conecta, objPreConexion, camp);
                            if (tenango > 0) {
                                camp.setCANTIDAD_TRAER(String.valueOf(tenango));
                                consult.productoTraer(conecta, objPreConexion, camp);
                            }
                           

                            if (aux_stok > 0) {
                                camp.setBODEGA("COMPRAR");
                                camp.setENTREGA("POR DEFINIR");
                                camp.setCANTIDAD_TRAER(String.valueOf(aux_stok));
                                camp.setSTOCK_ANTERIOR(String.valueOf(total_bodegas));
                                 consult.historiaVenta(conecta, objPreConexion, camp);
                                consult.productoTraer(conecta, objPreConexion, camp);
                               
                            }

                        }

                    }

                }

            }

            cierraConexiones();

            consult.actualizaEstadoCotiza(camp);

            /*
         
          
          
             */
            traerProducto();

            addActionError("La venta se ha guardado");

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String consultaVenta() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            camp.setNO_COTIZA(camp.getAUXCOTIZA());

            ListaCarroCotizacion = consult.carroConCotizacion(camp);

            if (ListaCarroCotizacion.size() > 0) {

                double total_cotiza = 0;
                double total_individual = 0;
                Iterator LCC = ListaCarroCotizacion.iterator();

                productosBean LLCB;

                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();
                    total_individual = LLCB.getPRECIO_FINAL();
                    total_cotiza = total_cotiza + total_individual;

                }

                camp.setTOTAL_COTIZACION(String.valueOf(total_cotiza));
                //Constantes.enviaMensajeConsola("precio final de la cotizacion es:" + camp.getTOTAL_COTIZACION());

                if (camp.getNO_COTIZACION().length() > 0) {
                    consult.actualizarNoCotiza(camp);
                }

            }

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String consultaVentaAlmacen() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaCarroCotizacion = consult.ventaAlmacen(camp);

            if (ListaCarroCotizacion.size() <= 0) {

                addFieldError("error", "La venta no exixte o no ha sido cobrada");

            }

            traerProducto();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String actualizaVentaEstatus() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            consult.actualizaEstatusEntrega(camp);

            ListaCarroCotizacion = consult.ventaAlmacen(camp);

            traerProducto();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String consultaVentaGenera() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaCarroCotizacion = consult.ventaConsultaCobra(camp);

            Iterator LCC = ListaCarroCotizacion.iterator();
            productosBean LLCB;

            double Total_general = 0;
            while (LCC.hasNext()) {
                LLCB = (productosBean) LCC.next();

                Total_general =Total_general+LLCB.getPRECIO_FINAL();

            }

            camp.setTOTAL_COTIZACION(String.valueOf(Total_general));

            ListaTraerProducto = consult.traerProducto(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String consultaVentaCancela() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaCarroCotizacion = consult.ventaConsulta(camp);

            Iterator LCC = ListaCarroCotizacion.iterator();
            productosBean LLCB;

            double Total_general = 0;
            while (LCC.hasNext()) {
                LLCB = (productosBean) LCC.next();

                Total_general = Total_general + LLCB.getPRECIO_FINAL();

            }

            camp.setTOTAL_COTIZACION(String.valueOf(Total_general));

            ListaTraerProducto = consult.traerProducto(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String consultaVentaCobra() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            ListaCarroCotizacion = consult.ventaConsultaCobra(camp);

            //Constantes.enviaMensajeConsola("Lita" + ListaCarroCotizacion.size());

            Iterator LCC = ListaCarroCotizacion.iterator();
            productosBean LLCB;

            double Total_general = 0;
            while (LCC.hasNext()) {
                LLCB = (productosBean) LCC.next();

                Total_general = Total_general + LLCB.getPRECIO_FINAL();
                camp.setSTATUS_VENTA(LLCB.getSTATUS_VENTA());

            }

            camp.setTOTAL_COTIZACION(String.valueOf(Total_general));

            if (camp.getSTATUS_VENTA().equals("V")) {

            } else if (camp.getSTATUS_VENTA().equals("2") && camp.getSTATUS_VENTA() != "3") {

                listausocfdi = this.facturaService.Obtenerusocfdi();
                listaformapago = this.facturaService.Obtenerformapago();

                List<FacturaBean> listadatoscliente = this.facturaService.obtenerVenta(Integer.parseInt(camp.getNO_VENTA()));

                for (FacturaBean facturaBean : listadatoscliente) {

                    camp.setRFC_CLIENTE(facturaBean.getRFC_CLIENT());
                    camp.setRAZONSOCIAL(facturaBean.getRASON_CLIENT());
                    if (facturaBean.getCORREO_CLIENT() != null) {
                        camp.setCORREO_CLIENTE(facturaBean.getCORREO_CLIENT().toLowerCase());
                    } else {
                        camp.setCORREO_CLIENTE("");
                    }
                }

                //Constantes.enviaMensajeConsola("Razon social" + camp.getRAZONSOCIAL());

                banfacturasi = true;

            }

            // ListaTraerProducto=consult.traerProducto(camp);
            camp.setFOLIOSRESTANTES(String.valueOf(TimbrarXml.consultaFolio()));
            //Constantes.enviaMensajeConsola("folios restantes" + camp.getFOLIOSRESTANTES());

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String cobraNota() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String cobraNotaActualiza() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            //Constantes.enviaMensajeConsola("Llegue al modulo de cobranza cobrar Actualiza");
            camp.setESTADO_VENTA("2");

            consult.actualizaCobra(camp);

            //addActionError("La nota se ha cobrado");
        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String cobraNotaActualizaFactura() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            Thread.sleep(8000);

            ConsultaBusiness consult = new ConsultaBusiness();
            //Constantes.enviaMensajeConsola("Llegue al modulo de cobranza cobrar Actualiza");
            camp.setESTADO_VENTA("2");

            consult.actualizaCobra(camp);

            addActionError("La nota se ha cobrado");
            addActionError("La servivio de facturación no esta disponible en este momento intentaré más tarde");

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String cerrarCaja() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;

            fecha = fecha();

            camp.setFECHA_CONSULTA(fecha);

            ListaVentaDia = consult.ventaDia(camp);

            Iterator LVD = ListaVentaDia.iterator();

            productosBean obj2;

            double total_general_detalle = 0;

            while (LVD.hasNext()) {
                obj2 = (productosBean) LVD.next();

                total_general_detalle = total_general_detalle + obj2.getPRECIO_FINAL();
            }

            camp.setTOTAL_COTIZACION(String.valueOf(total_general_detalle));

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String cerrarCajaDetalle() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;

            fecha = fecha();

            camp.setFECHA_CONSULTA(fecha);

            if (camp.getFECHA_INICIO().length() > 0 || camp.getFECHA_FINAL().length() > 0) {
                //Constantes.enviaMensajeConsola("ENTRE A DETALLE DE NOTAS CON FECHA");
                ListaCarroCotizacion = consult.ventaConsultaDiaFecha(camp);

                Iterator LCC = ListaCarroCotizacion.iterator();
                productosBean LLCB;

                double Total_general = 0;
                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();

                    Total_general = Total_general + LLCB.getPRECIO_FINAL();

                }

                camp.setTOTAL_VENTA_DETALLE(String.valueOf(Total_general));

                consultaVentaCaja();

            } else {

                ListaCarroCotizacion = consult.ventaConsultaDia(camp);

                Iterator LCC = ListaCarroCotizacion.iterator();
                productosBean LLCB;

                double Total_general = 0;
                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();

                    Total_general = Total_general + LLCB.getPRECIO_FINAL();

                }

                camp.setTOTAL_VENTA_DETALLE(String.valueOf(Total_general));

                ListaVentaDia = consult.ventaDia(camp);

            }
        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String consultaVentaCaja() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;

            fecha = fecha();

            if (camp.getFECHA_INICIO().length() > 0 || camp.getFECHA_FINAL().length() > 0) {

                ListaVentaDia = consult.ventaConsultaFechasDetalle(camp);

                Iterator LCC = ListaVentaDia.iterator();
                productosBean LLCB;

                double Total_general = 0;
                while (LCC.hasNext()) {
                    LLCB = (productosBean) LCC.next();

                    Total_general = Total_general + LLCB.getPRECIO_FINAL();

                }

                camp.setTOTAL_COTIZACION(String.valueOf(Total_general));
            }

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String productosStok0() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;

            fecha = fecha();
            camp.setFECHA_CONSULTA(fecha);

            ListaStok = consult.listaStok0(camp);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String productosStok0Fechas() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;

            fecha = fecha();
            camp.setFECHA_CONSULTA(fecha);

            if (camp.getFECHA_INICIO().length() > 0 || camp.getFECHA_FINAL().length() > 0) {
                ListaStok = consult.listaStok0Fechas(camp);

            }
        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String alternativos() {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            String fecha;

            fecha = fecha();
            camp.setFECHA_CONSULTA(fecha);

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String buscarAlternativos() throws Exception {
        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            ConsultaBusiness consult = new ConsultaBusiness();

            DecimalFormat formateador = new DecimalFormat("###,###.##");
            ListaBuscarProducto = consult.productosBuscar(camp);
            ListaCotizaHist = consult.cotizaHist(camp);

            ListaBuscarProductoFinal = consult.productosBuscarFinal(camp);
            actprod = false;
            regprod = false;

            if (ListaBuscarProducto.size() <= 0) {
                actprod2 = true;
            }

            if (ListaBuscarProductoFinal.size() > 0) {
                actprod = true;
                regprod = false;

            }

            if (ListaBuscarProductoFinal.size() <= 0) {
                actprod = false;
                regprod = true;
                limpiar();
                camp.setNO_PARTE(camp.getCONSULTA_PARTE());

            }

            float precioCal = 0;
            float precioCal2 = 0;
            float precio = 0;
            float ganancia = 0;

            ivacalcula = iva();
            dolarcalcula = dolar();

            int cantidad = 0;
            if (camp.getCANTIDAD_VENTA() != null) {
                cantidad = Integer.parseInt(camp.getCANTIDAD_VENTA());
            } else {
                cantidad = 1;
            }

            //Constantes.enviaMensajeConsola("la cantidad de productos a vender es:");

            Iterator cgeBP = ListaBuscarProductoFinal.iterator();
            productosBean pbBP;

            while (cgeBP.hasNext()) {
                pbBP = (productosBean) cgeBP.next();

                camp.setNO_PARTE(pbBP.getNO_PARTE());
                camp.setPRODUCTO(pbBP.getPRODUCTO());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setDESCRIPCION(pbBP.getDESCRIPCION());
                camp.setPRECIO(pbBP.getPRECIO());
                precio = Float.parseFloat(pbBP.getPRECIO());

                if (pbBP.getGANANCIA().equals("0")) {
                    ganancia = Float.parseFloat("0.32");

                } else {
                    ganancia = Float.parseFloat(pbBP.getGANANCIA());
                }
                
                precioUniPartida = calculaPrecio(pbBP.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), cantidad, ivacalcula, dolarcalcula);

                
                
               
                
                 pbBP.setPRECIO_PESO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));
               camp.setTOTAL_PRODUCTO_VENTA(String.valueOf(precioUniPartida.getPRECIO_PARTIDA_TOTAL()));
                

                

                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setMARCA(pbBP.getMARCA());
                camp.setUNIDADMEDIDA(pbBP.getUNIDADMEDIDA());
                camp.setMONEDA(pbBP.getMONEDA());
                camp.setALTERNATIVO(pbBP.getALTERNATIVO());
                camp.setPRECIO_PESO(pbBP.getPRECIO_PESO());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setPRECIO_CAL(pbBP.getPRECIO_CAL());

                camp.setRESPONSABLE(pbBP.getRESPONSABLE());
                camp.setPROVEEDOR(pbBP.getPROVEEDOR());
                camp.setCATEGORIA(pbBP.getCATEGORIA());
                camp.setCATEGORIA_GENERAL(pbBP.getCATEGORIA_GENERAL());

            }

            ListaCarroCotizacion = consult.carroCotizacion(camp);

            ListaProductoHist = consult.productosBuscarHist(camp);

            if (ListaProductoHist.size() > 0) {

                //Constantes.enviaMensajeConsola("ENTRE A CONSULTAR LA HISTORIA DEL PRODUCTO");
                Iterator cge = ListaProductoHist.iterator();

                productosBean pb;

                while (cge.hasNext()) {
                    pb = (productosBean) cge.next();
                    precio = Float.parseFloat(pb.getPRECIO());
                    ganancia = Float.parseFloat(pb.getGANANCIA());

                    precioUniPartida = calculaPrecio(pb.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

           

                pb.setPRECIO_PESO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

                }

            }

            ListaAlternativos = consult.productosAlternativos(camp);

            ListaProductoAlt = consult.productosBuscarAlt(camp);

            if (ListaProductoAlt.size() > 0) {
                Iterator cgeAlt = ListaProductoAlt.iterator();

                productosBean pbAlt;

                precioCal = 0;
                precio = 0;
                ganancia = 0;

                while (cgeAlt.hasNext()) {
                    pbAlt = (productosBean) cgeAlt.next();
                    precio = Float.parseFloat(pbAlt.getPRECIO());
                    
                     precioUniPartida = calculaPrecio(pbAlt.getMONEDA(), ganancia, precio, camp.getINCREMENTO(), 1, ivacalcula, dolarcalcula);

             

                pbAlt.setPRECIO(String.valueOf(precioUniPartida.getPRECIO_UNITARIO_TOTAL()));

                   

                }
            }

            String fecha;
            fecha = fecha();
            camp.setFECHA_COTIZA(fecha);
            camp.setRESPONSABLE(usuariocons.getNAMEUSUARIO());

            /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";

    }

    public String borrarAlternativo() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();

            consult.borrarAlternativo(camp);
            buscarAlternativos();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    public String guardarAlternativo() {
        try {
            ConsultaBusiness consult = new ConsultaBusiness();

            consult.guardarAlternativo(camp);
            buscarAlternativos();

        } catch (Exception e) {
              System.out.println(e);
        }

        return "SUCCESS";
    }

    private void cierraConexiones() {
        try {
            objConexion.close();
            //objPreConexion.close();
            conecta.close();
            //Constantes.enviaMensajeConsola("******************************Conexion cerrada************************************ ");

        } catch (Exception e) {
            e.printStackTrace();
            //Constantes.enviaMensajeConsola("Ocurrio un error al cerrar conexiones: " + e);

        }
    }

    public String tipocambio() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            String dolar = "";

            dolar = consult.dolarCambio();
            camp.setDOLAR(dolar);

            //Constantes.enviaMensajeConsola("Entre a tipo  de cambio:" );
        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public String actualizatipocambio() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            consult.actualizaTipoCambio(camp);

            addFieldError("EXITO", "¡Se guardo con éxito el nuevo tipo de cambio!");
            tipocambio();

            //Constantes.enviaMensajeConsola("Actuallize el tipo de cambio");

        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public String consultaCreditos() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();
            bancrednota = false;
            bancrednotaAbonos = false;

            if (camp.getBUSCARCLIENTE() == null) {
                //Constantes.enviaMensajeConsola("entre opcion 1");

                listaCreditos = consult.creditosGeneral(camp);
            } else {
                //Constantes.enviaMensajeConsola("entre opcion 2");
                listaCreditos = consult.creditosGeneraLike(camp);

            }

            float deuda = 0;
            float aportaciones = 0;
            float saldo = 0;

            for (int i = 0; i < listaCreditos.size(); i++) {
                deuda = deuda + listaCreditos.get(i).getTOTAL_DEUDA();
                aportaciones = aportaciones + listaCreditos.get(i).getTOTAL_APORTACIONES_DEUDA();
                saldo = saldo + listaCreditos.get(i).getSALDO_DEUDA();

            }

            camp.setTOTAL_DEUDA_GENERAL(deuda);
            camp.setTOTAL_APORTACIONES_DEUDA_GENERAL(aportaciones);
            camp.setSALDO_DEUDA_GENERAL(saldo);

            //Constantes.enviaMensajeConsola("TOTAL_DEUDA_GENERAL" + camp.getTOTAL_DEUDA_GENERAL());
            //Constantes.enviaMensajeConsola("Créditos abiertos:" + listaCreditos.size());

        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public String consultaNotasCredito() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            consultaCreditos();
            bancrednotaAbonos = false;
            bancrednota = true;

            if (camp.getNO_VENTA().equals("") || camp.getNO_VENTA() == null) {
                //Constantes.enviaMensajeConsola("entre opcion 1");

                listaCreditosNota = consult.creditosNota(camp);
            } else {
                //Constantes.enviaMensajeConsola("entre opcion 2");
                listaCreditosNota = consult.creditosNotaNoVenta(camp);

            }

            //Constantes.enviaMensajeConsola("listaCreditosNota" + listaCreditosNota.size());

        } catch (Exception e) {
             System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public String consultaNotasCreditoAbonos() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            consultaNotasCredito();
            bancrednota = true;
            bancrednotaAbonos = true;

            listaCreditosNotaAbonos = consult.creditosNotaAbonos(camp);
            //Constantes.enviaMensajeConsola("listaCreditosNotaAbonos" + listaCreditosNotaAbonos.size());

        } catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public String guardaAbonos() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }

            ConsultaBusiness consult = new ConsultaBusiness();

            float abono = 0;
            int no_venta = 0;

            no_venta = Integer.parseInt(camp.getNO_VENTA());
            abono = Float.parseFloat(camp.getAPORTACION());

            consult.guardaAbonos(camp, no_venta, abono);

        } catch (Exception e) {
              System.out.println(e);
            return "ERROR";
        }

        return "SUCCESS";
    }

    public int validaRfc(String rfc) {
        //Constantes.enviaMensajeConsola("action.Consultas_Action.validaRfc()");
        Pattern pat = Pattern.compile("^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\\d]{3})?$");
        Matcher mat = pat.matcher(rfc);
        if (mat.find()) {
            return 1;

        } else {
            return 0;
        }

    }

    public int validaCorreo(String correo) {

        Pattern pat = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}");
        Matcher mat = pat.matcher(correo);
        if (mat.find()) {
            return 1;

        } else {
            return 0;
        }

    }

    public int validaTelefono(String tel) {
        Pattern pat = Pattern.compile("[0-9]{10}");
        Matcher mat = pat.matcher(tel);
        if (mat.find()) {
            return 1;

        } else {
            return 0;
        }

    }

}
