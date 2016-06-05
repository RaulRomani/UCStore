package com.ultracolor.controllers.process;

import com.ultracolor.controllers.util.AccesoDB;
import com.ultracolor.controllers.util.CantidadLetras;
import com.ultracolor.controllers.util.JsfUtil;
import com.ultracolor.controllers.util.Log4jConfig;
import com.ultracolor.entities.Credito;
import com.ultracolor.entities.Proveedor;
import com.ultracolor.entities.Producto;
import com.ultracolor.entities.Productocompra;
import com.ultracolor.entities.Compra;
import com.ultracolor.entities.util.Carrito;
import com.ultracolor.entities.util.CarritoItem;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Raul
 */
@Named(value = "compras")
@SessionScoped
public class Compras implements Serializable {

  @EJB
  private com.ultracolor.facades.ProveedorFacadeLocal ejbFacadeProveedor;
  @EJB
  private com.ultracolor.facades.ProductoFacadeLocal ejbFacadeProducto;
  @EJB
  private com.ultracolor.facades.CompraFacadeLocal ejbFacadeCompra;

  @Inject
  private LoginController personal;

  private List<Proveedor> proveedorList;
  private Proveedor proveedorSelected;

  private List<Producto> productoList;
  private Producto productoSelected = null;

  private List<Compra> compraList;
  private Compra compraSelected;
  
  private Productocompra productoCompra;
  private Carrito carrito;

  private boolean comprobanteCredito;
  private String tipoProveedor;

  @edu.umd.cs.findbugs.annotations.SuppressWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
  private transient final org.slf4j.Logger logger = LoggerFactory.getLogger(Compras.class);

  @PostConstruct
  void init() {
    carrito = new Carrito();
    productoCompra = new Productocompra();
    //Default values
    carrito.setComprobante("BOLETA");
    tipoProveedor = "REGISTRADO";
    proveedorSelected = new Proveedor();
  }

  public void agregarProducto() {
    if (productoSelected != null) {
      BigDecimal precio = productoSelected.getPrecioCompra();
      Integer cantidad = 1;
      BigDecimal importe = precio.multiply(new BigDecimal(cantidad));

      CarritoItem item = new CarritoItem();
      item.setIdProducto(productoSelected.getIdProducto());
      item.setNombreProducto(productoSelected.getNombre());
      item.setPrecioProducto(precio);
      item.setCantidad(cantidad);
      item.setImporte(importe);
//      if (productoCompra.getDiente() != null) {
//        item.setDiente(productoCompra.getDiente());
//      }
      carrito.add(item);
      logger.info("Producto agregado cantidad: " + carrito.getItems().size());
    } else {
      JsfUtil.addErrorMessage("Primero elige un Producto.");
    }
  }

  public void eliminarProducto(CarritoItem carritoItem) {
    int index = carrito.getItems().indexOf(carritoItem);
    carrito.getItems().remove(index);
    JsfUtil.addErrorMessage("El producto se eliminó correctamente.");
    logger.info("Eliminar producto OK");
  }

  public void handleChangeCantidad(CarritoItem carritoItem) {
    int index = carrito.getItems().indexOf(carritoItem);
    Integer cantidad = carrito.getItems().get(index).getCantidad();
    BigDecimal precio = carrito.getItems().get(index).getPrecioProducto();
    carrito.getItems().get(index).setImporte(precio.multiply(new BigDecimal(cantidad)));
  }

  public void grabarPedidoCompra() throws JRException, IOException, NamingException, SQLException, Exception {

    Integer idCompra;
    if (!carrito.getItems().isEmpty()) {
      idCompra = ejbFacadeCompra.grabarPedidoCompra(carrito, proveedorSelected, personal.getUsuario());
      reportePedidoCompra(idCompra);
      
      carrito= new Carrito();  // Limpiar carrito
      JsfUtil.addSuccessMessage("El pedido de compra se realizo correctamente.");
      logger.info("SE AGREGO UNA COMPRA Y SU DETALLE");
    } else {
      JsfUtil.addErrorMessage("Primero agregue productos");
    }
  }
  
  public void grabarCompra(){
//    Integer cantidad = compraSelected.getProductocompraList().get(0).getCantidad();
//    Boolean recibido = compraSelected.getProductocompraList().get(0).getRecibido();
    
    ejbFacadeCompra.grabarCompra(compraSelected);
    
    JsfUtil.addSuccessMessage("La compra se guardo correctamente.");
    logger.info("GRABAR COMPRA: OK");
//    logger.info("CANTIDAD: " + cantidad +"  recibido: "+ recibido);
  }

  public void imprimirProforma() throws JRException, IOException {

    if (!carrito.getItems().isEmpty()) {
      Map<String, Object> parametro = new HashMap<>();
      parametro.put("carrito", carrito.getItems());
      parametro.put("proveedor_razonSocial", proveedorSelected.getRazonSocial());
      parametro.put("proveedor_RUC", proveedorSelected.getRuc());
      parametro.put("proveedor_direccion", proveedorSelected.getDireccion());

      File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/compras/proforma.jasper"));
      logger.info("OK PROFORMA REPORT ");

      //Connection con = AccesoDB.getConnection();
      //java.sql.Connection co = em.unwrap(java.sql.Connection.class);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametro, new JREmptyDataSource());

      HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
      response.addHeader("Content-disposition", "attachment; filename=Proforma-" + proveedorSelected.getRuc()+ ".pdf");  //Works in chrome
      try (ServletOutputStream stream = response.getOutputStream()) {
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "D://proveedors.pdf");
        JasperPrintManager.printReport(jasperPrint, false);

        stream.flush();
      }

      FacesContext.getCurrentInstance().responseComplete();

    } else {
      JsfUtil.addErrorMessage("Primero agregue productos");
    }

  }

  public void reportePedidoCompra(Integer idCompra) throws JRException, IOException, NamingException, SQLException, Exception {

    Map<String, Object> parametro = new HashMap<>();
    parametro.put("idCompra", idCompra);
    parametro.put("proveedor_razonSocial", proveedorSelected.getRazonSocial());
    parametro.put("proveedor_RUC", proveedorSelected.getRuc());
    parametro.put("proveedor_direccion", proveedorSelected.getDireccion());
    

    File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/compras/compras.jasper"));
    logger.info("OK REPORTE PEDIDO DE COMPRA");

    Connection con = AccesoDB.getConnection();
    //java.sql.Connection co = em.unwrap(java.sql.Connection.class);
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametro, con);

    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    response.addHeader("Content-disposition", "attachment; filename=Proforma-" + proveedorSelected.getRuc()+ ".pdf");  //Works in chrome
    ServletOutputStream stream = response.getOutputStream();

    JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
    //JasperExportManager.exportReportToPdfFile(jasperPrint, "D://proveedors.pdf");
    JasperPrintManager.printReport(jasperPrint, false);

    stream.flush();
    stream.close();

    FacesContext.getCurrentInstance().responseComplete();
  }

  

  public List<Proveedor> getProveedorList() {
//    if (proveedorList == null) {
    proveedorList = ejbFacadeProveedor.findAll();
//    }
    return proveedorList;
  }
  
  
  
  public List<Compra> getCompraList() {
//    if (proveedorList == null) {
    compraList = ejbFacadeCompra.findByEstado("ESPERA");
//    }
    return compraList;
  }
  
  public void prepareCompra() {
    compraSelected = new Compra();
  }

  public void prepareProveedor() {
    proveedorSelected = new Proveedor();
  }
  
  public void loadProveedor(){
    proveedorSelected = compraSelected.getIdProveedor();
  }

  public void registrarProveedor() {
    if (!proveedorSelected.getRuc().isEmpty() && !proveedorSelected.getRazonSocial().isEmpty()) {
      ejbFacadeProveedor.create(proveedorSelected);
      //proveedorSelected = ejbFacadeProveedor.find(this);
      JsfUtil.addSuccessMessage("Proveedor registrado exitosamente!");
    }

  }

  public Proveedor getProveedorSelected() {
    return proveedorSelected;
  }

  public void setProveedorSelected(Proveedor proveedorSelected) {
    this.proveedorSelected = proveedorSelected;
  }

  public Carrito getCarrito() {
    return carrito;
  }

  public List<Producto> getProductoList() {
//    if (productoList == null) {
    productoList = ejbFacadeProducto.findAll();
//    }
    return productoList;
  }

  public Producto getProductoSelected() {
    return productoSelected;
  }

  public void setProductoSelected(Producto productoSelected) {
    this.productoSelected = productoSelected;
  }

  public LoginController getPersonal() {
    return personal;
  }

  public void setPersonal(LoginController personal) {
    this.personal = personal;
  }

  public Productocompra getProductoCompra() {
    return productoCompra;
  }

  public void setProductoCompra(Productocompra productoCompra) {
    this.productoCompra = productoCompra;
  }

  public boolean isComprobanteCredito() {
    return comprobanteCredito;
  }

  public void setComprobanteCredito(boolean comprobanteCredito) {
    this.comprobanteCredito = comprobanteCredito;
  }

  public String getTipoProveedor() {
    return tipoProveedor;
  }

  public void setTipoProveedor(String tipoProveedor) {
    this.tipoProveedor = tipoProveedor;
  }

  public Compra getCompraSelected() {
    return compraSelected;
  }

  public void setCompraSelected(Compra compraSelected) {
    this.compraSelected = compraSelected;
  }
  
  

}
