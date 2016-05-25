/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Pedidocompra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Raul
 */
@Local
public interface PedidocompraFacadeLocal {

  void create(Pedidocompra pedidocompra);

  void edit(Pedidocompra pedidocompra);

  void remove(Pedidocompra pedidocompra);

  Pedidocompra find(Object id);

  List<Pedidocompra> findAll();

  List<Pedidocompra> findRange(int[] range);

  int count();
  
}
