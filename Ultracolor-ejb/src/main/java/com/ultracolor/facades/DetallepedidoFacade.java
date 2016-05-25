/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Detallepedido;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Raul
 */
@Stateless
public class DetallepedidoFacade extends AbstractFacade<Detallepedido> implements DetallepedidoFacadeLocal {
  @PersistenceContext(unitName = "com.ultracolor_Ultracolor-ejb_ejb_1.0PU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public DetallepedidoFacade() {
    super(Detallepedido.class);
  }
  
}
