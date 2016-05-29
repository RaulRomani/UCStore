/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Cliente;
import com.ultracolor.entities.Credito;
import com.ultracolor.entities.Producto;
import com.ultracolor.entities.Productoventa;
import com.ultracolor.entities.Usuario;
import com.ultracolor.entities.Venta;
import com.ultracolor.entities.util.Carrito;
import com.ultracolor.entities.util.CarritoItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul
 */
@Stateless
public class VentaFacade extends AbstractFacade<Venta> implements VentaFacadeLocal {

  @PersistenceContext(unitName = "com.ultracolor_Ultracolor-ejb_ejb_1.0PU")
  private EntityManager em;

  @EJB
  private com.ultracolor.facades.ProductoFacadeLocal ejbFacadeProducto;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public VentaFacade() {
    super(Venta.class);
  }

  @Override
  public Integer grabarVentaContado(Carrito carrito, Cliente cliente, Usuario usuario) {

    Venta venta = new Venta();
    venta.setComprobante(carrito.getComprobante());
    venta.setFormapago("CONTADO");
    venta.setFechaHora(new Date());
    venta.setDescuento(BigDecimal.ZERO);
    venta.setSubtotal(carrito.getTotal());
    venta.setTotal(carrito.getTotal());
    if(cliente.getIdCliente() != null) // si es cliente registrado
      venta.setIdCliente(cliente);
    venta.setIdUsuario(usuario);
    venta.setEstado("CANCELADO");

    try {
//    utx.begin();

      em.persist(venta);
      em.flush();
      List<Productoventa> productoVentaList = new ArrayList<>();
      Productoventa productoVenta;

      for (CarritoItem i : carrito.getItems()) {
        productoVenta = new Productoventa();
        productoVenta.setCantidad(i.getCantidad());
        productoVenta.setImporte(i.getImporte());
        productoVenta.setIdProducto(new Producto(i.getIdProducto()));
        productoVenta.setIdVenta(venta); //para persistencia 1 a muchos

        //productoVenta.setProductoventaPK(new ProductoventaPK(venta.getIdVenta(), i.getIdProducto()));
        productoVentaList.add(productoVenta);

        // Stock
        Producto producto = ejbFacadeProducto.find(i.getIdProducto());
        
        if(producto.getTipo().equals("PRODUCTO")){
          if (i.getCantidad() > producto.getStock()) {
            throw new Exception("Stock agotado en producto: id:" + i.getIdProducto() + " - " +i.getNombreProducto() +".");
          }
          producto.setStock(producto.getStock() -i.getCantidad());
          ejbFacadeProducto.edit(producto);
        }
        
        
      }
      venta.setProductoventaList(productoVentaList);
      em.persist(venta);

      //save jpa many to one
//    ejbFacadeVenta.create(venta);
//    utx.commit();
    } catch (Exception e) {
      Logger.getLogger(VentaFacade.class.getName()).log(Level.SEVERE, null, "EROOR GUARDAR VENTA AL CONTADO: " + e.toString());
//      try {
//        utx.rollback();
//      } catch (IllegalStateException ex) {
//        Logger.getLogger(VentaFacade.class.getName()).log(Level.SEVERE, null, ex);
//      } catch (SecurityException ex) {
//        Logger.getLogger(VentaFacade.class.getName()).log(Level.SEVERE, null, ex);
//      } catch (SystemException ex) {
//        Logger.getLogger(VentaFacade.class.getName()).log(Level.SEVERE, null, ex);
//      }
    }
    return venta.getIdVenta();
  }

  @Override
  public Integer grabarVentaCreditos(Carrito carrito, Cliente cliente, Usuario usuario, Credito cuota) {

    Venta venta = new Venta();
    venta.setComprobante("");  //No se asigna comprobante hasta que se cancele las cuotas
    venta.setFormapago("CUOTAS");
    venta.setFechaHora(new Date());
    venta.setDescuento(BigDecimal.ZERO);
    venta.setSubtotal(carrito.getTotal());
    venta.setTotal(carrito.getTotal());
    if(cliente.getIdCliente() != null) // si es cliente registrado
      venta.setIdCliente(cliente);
    venta.setIdUsuario(usuario);
    venta.setEstado("CREDITO");

    try {

      em.persist(venta);
      em.flush();
      List<Productoventa> productoVentaList = new ArrayList<>();
      Productoventa productoVenta;

      for (CarritoItem i : carrito.getItems()) {
        productoVenta = new Productoventa();
        productoVenta.setCantidad(i.getCantidad());
        productoVenta.setImporte(i.getImporte());
        productoVenta.setIdProducto(new Producto(i.getIdProducto()));
        productoVenta.setIdVenta(venta); //para persistencia 1 a muchos
        //productoVenta.setProductoventaPK(new ProductoventaPK(venta.getIdVenta(), i.getIdProducto()));

        productoVentaList.add(productoVenta);
        
        // Stock
        Producto producto = ejbFacadeProducto.find(i.getIdProducto());
        
        if(producto.getTipo().equals("PRODUCTO")){
          if (i.getCantidad() > producto.getStock()) {
            throw new Exception("Stock agotado en producto: id:" + i.getIdProducto() + " - " +i.getNombreProducto() +".");
          }
          producto.setStock(producto.getStock() -i.getCantidad());
          ejbFacadeProducto.edit(producto);
        }

      }

      venta.setProductoventaList(productoVentaList);

      //Agregar cuota
      List<Credito> cuotaList = new ArrayList<>();
      cuota.setFechaHora(venta.getFechaHora());
      cuota.setCuotaspagado(0);
      cuota.setIdVenta(venta);
      cuotaList.add(cuota);
      venta.setCreditoList(cuotaList);
      System.out.println("Se agregó la cuota");

      em.persist(venta);
      System.out.println("Se guardó la venta en cuotas");

    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return venta.getIdVenta();

  }

  @Override
  public List<Venta> findByFormaPagoCliente(Cliente cliente, String formaPago) {

    TypedQuery<Venta> q = getEntityManager().createNamedQuery("Venta.findByformaPagoCliente", Venta.class);
    q.setParameter("formapago", formaPago);
    q.setParameter("idCliente", cliente);
    List<Venta> list;
    try {
      list = q.getResultList();
    } catch (NoResultException e) {
      list = null;
    }
    return list;
  }

}
