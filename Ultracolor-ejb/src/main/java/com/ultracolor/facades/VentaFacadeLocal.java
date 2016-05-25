/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Cliente;
import com.ultracolor.entities.Credito;
import com.ultracolor.entities.Usuario;
import com.ultracolor.entities.Venta;
import com.ultracolor.entities.util.Carrito;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Raul
 */
@Local
public interface VentaFacadeLocal {

  void create(Venta venta);

  void edit(Venta venta);

  void remove(Venta venta);

  Venta find(Object id);

  List<Venta> findAll();

  List<Venta> findRange(int[] range);

  int count();
  
  public Integer grabarVentaContado(Carrito carrito, Cliente cliente, Usuario usuario);
  public Integer grabarVentaCreditos(Carrito carrito, Cliente cliente, Usuario usuario, Credito cuota);
  public List<Venta> findByFormaPagoCliente(Cliente cliente, String formaPago);
  
}
