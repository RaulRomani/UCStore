/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.facades;

import com.ultracolor.entities.Pago;
import com.ultracolor.entities.Personal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Raul
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> implements PagoFacadeLocal {
  @PersistenceContext(unitName = "com.ultracolor_Ultracolor-ejb_ejb_1.0PU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public PagoFacade() {
    super(Pago.class);
  }
  
  @Override
  public List<Pago> findByPersonal(Personal personal, String mes) {
    TypedQuery<Pago> q = getEntityManager().createNamedQuery("Pago.findByPersonal", Pago.class);
    q.setParameter("idPersonal", personal);
    Date fecha = new Date();
    
    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); 
    String startDate = yearFormat.format(fecha) + "-" +  mes +"-01 00:00:01";
    
    Integer endMes =Integer.parseInt(mes) + 1;
    String endDate;
    if(endMes != 10 && endMes != 11 && endMes != 12)
      endDate = yearFormat.format(fecha) + "-0" +  endMes +"-01 00:00:01";
    else
      endDate = yearFormat.format(fecha) + "-" +  endMes +"-01 00:00:01";
    
    try {
      
      q.setParameter("startDate", dtFormat.parse(startDate) , TemporalType.TIMESTAMP );
      q.setParameter("endDate", dtFormat.parse(endDate) , TemporalType.TIMESTAMP );
      
      
    } catch (ParseException ex) {
      System.out.println(ex.getMessage());
    }
    
    
//    Date fecha = new Date();
//    Query q = em.createNativeQuery("SELECT * FROM pago WHERE fechaHora LIKE '%" + fecha.getYear() + "-" + mes + "%'");
    
    List<Pago> list;
    try {
      list = q.getResultList();
    } catch (NoResultException e) {
      list = null;
      System.out.println("NO HAY PAGOS REGISTRADOS");
    }
    if(list.isEmpty())
      list = null;
    System.out.println("FIND PAGO BY PERSONAL OK");
    return list;
  }
  
}
