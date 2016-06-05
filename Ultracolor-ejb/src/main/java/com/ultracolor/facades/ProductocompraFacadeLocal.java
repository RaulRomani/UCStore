/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Productocompra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Raul
 */
@Local
public interface ProductocompraFacadeLocal {

  void create(Productocompra productocompra);

  void edit(Productocompra productocompra);

  void remove(Productocompra productocompra);

  Productocompra find(Object id);

  List<Productocompra> findAll();

  List<Productocompra> findRange(int[] range);

  int count();
  
}
