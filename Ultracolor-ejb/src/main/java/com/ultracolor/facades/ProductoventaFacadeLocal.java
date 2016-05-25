/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Productoventa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Raul
 */
@Local
public interface ProductoventaFacadeLocal {

  void create(Productoventa productoventa);

  void edit(Productoventa productoventa);

  void remove(Productoventa productoventa);

  Productoventa find(Object id);

  List<Productoventa> findAll();

  List<Productoventa> findRange(int[] range);

  int count();
  
}
