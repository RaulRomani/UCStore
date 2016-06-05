/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Cliente;
import com.ultracolor.entities.Proveedor;
import com.ultracolor.entities.Compra;
import com.ultracolor.entities.Producto;
import com.ultracolor.entities.Productocompra;
import com.ultracolor.entities.Usuario;
import com.ultracolor.entities.Compra;
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
public class CompraFacade extends AbstractFacade<Compra> implements CompraFacadeLocal {

  @PersistenceContext(unitName = "com.ultracolor_Ultracolor-ejb_ejb_1.0PU")
  private EntityManager em;

  @EJB
  private com.ultracolor.facades.ProductoFacadeLocal ejbFacadeProducto;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public CompraFacade() {
    super(Compra.class);
  }

  @Override
  public Integer grabarPedidoCompra(Carrito carrito, Proveedor proveedor, Usuario usuario) {

    Compra compra = new Compra();

    compra.setTotal(carrito.getTotal());
    compra.setFechaHora(new Date());
    compra.setComprobante("FACTURA");
    compra.setEstado("ESPERA");

    compra.setIdProveedor(proveedor);
    compra.setIdUsuario(usuario);

    try {

      em.persist(compra);
      em.flush();
      List<Productocompra> productoCompraList = new ArrayList<>();
      Productocompra productoCompra;

      for (CarritoItem i : carrito.getItems()) {
        productoCompra = new Productocompra();
        productoCompra.setCantidad(i.getCantidad());
        productoCompra.setImporte(i.getImporte());
        productoCompra.setIdProducto(new Producto(i.getIdProducto()));
        productoCompra.setRecibido(true);
        productoCompra.setIdCompra(compra); //para persistencia 1 a muchos

        //productoCompra.setProductocompraPK(new ProductocompraPK(compra.getIdCompra(), i.getIdProducto()));
        productoCompraList.add(productoCompra);

      }
      compra.setProductocompraList(productoCompraList);
      em.persist(compra);

    } catch (Exception e) {
      Logger.getLogger(CompraFacade.class.getName()).log(Level.SEVERE, null, "EROOR GUARDAR PEDIDO DE COMPRA: " + e.toString());
    }
    
    return compra.getIdCompra();
  }

  @Override
  public void grabarCompra(Compra compra) {

    try {
      compra.setEstado("PAGADO");

      // Actualizar Stock
      Producto producto;
      for (Productocompra productoCompra : compra.getProductocompraList()) {
        if(productoCompra.getRecibido()){
          producto = productoCompra.getIdProducto();
          producto.setStock(producto.getStock() + productoCompra.getCantidad());
          ejbFacadeProducto.edit(producto);
        }
      }

      em.merge(compra);

    } catch (Exception e) {
      Logger.getLogger(CompraFacade.class.getName()).log(Level.SEVERE, null, "EROOR GUARDAR COMPRA: " + e.toString());

    } 

  }

  @Override
  public List<Compra> findByEstado(String estado) {

    TypedQuery<Compra> q = getEntityManager().createNamedQuery("Compra.findByEstado", Compra.class);
    q.setParameter("estado", estado);
    List<Compra> list;
    try {
      list = q.getResultList();
    } catch (NoResultException e) {
      list = null;
    }
    return list;
  }

}
