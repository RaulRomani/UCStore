package com.ultracolor.controllers;

import com.ultracolor.entities.Detallepedido;
import com.ultracolor.controllers.util.JsfUtil;
import com.ultracolor.controllers.util.JsfUtil.PersistAction;
import com.ultracolor.facades.DetallepedidoFacadeLocal;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("detallepedidoController")
@SessionScoped
public class DetallepedidoController implements Serializable {

  @EJB
  private com.ultracolor.facades.DetallepedidoFacadeLocal ejbFacade;
  private List<Detallepedido> items = null;
  private Detallepedido selected;

  public DetallepedidoController() {
  }

  public Detallepedido getSelected() {
    return selected;
  }

  public void setSelected(Detallepedido selected) {
    this.selected = selected;
  }

  protected void setEmbeddableKeys() {
  }

  protected void initializeEmbeddableKey() {
  }

  private DetallepedidoFacadeLocal getFacade() {
    return ejbFacade;
  }

  public Detallepedido prepareCreate() {
    selected = new Detallepedido();
    initializeEmbeddableKey();
    return selected;
  }

  public void create() {
    persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DetallepedidoCreated"));
    if (!JsfUtil.isValidationFailed()) {
      items = null;    // Invalidate list of items to trigger re-query.
    }
  }

  public void update() {
    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DetallepedidoUpdated"));
  }

  public void destroy() {
    persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DetallepedidoDeleted"));
    if (!JsfUtil.isValidationFailed()) {
      selected = null; // Remove selection
      items = null;    // Invalidate list of items to trigger re-query.
    }
  }

  public List<Detallepedido> getItems() {
    if (items == null) {
      items = getFacade().findAll();
    }
    return items;
  }

  private void persist(PersistAction persistAction, String successMessage) {
    if (selected != null) {
      setEmbeddableKeys();
      try {
        if (persistAction != PersistAction.DELETE) {
          getFacade().edit(selected);
        } else {
          getFacade().remove(selected);
        }
        JsfUtil.addSuccessMessage(successMessage);
      } catch (EJBException ex) {
        String msg = "";
        Throwable cause = ex.getCause();
        if (cause != null) {
          msg = cause.getLocalizedMessage();
        }
        if (msg.length() > 0) {
          JsfUtil.addErrorMessage(msg);
        } else {
          JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
      } catch (Exception ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
      }
    }
  }

  public Detallepedido getDetallepedido(java.lang.Integer id) {
    return getFacade().find(id);
  }

  public List<Detallepedido> getItemsAvailableSelectMany() {
    return getFacade().findAll();
  }

  public List<Detallepedido> getItemsAvailableSelectOne() {
    return getFacade().findAll();
  }

  @FacesConverter(forClass = Detallepedido.class)
  public static class DetallepedidoControllerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      if (value == null || value.length() == 0) {
        return null;
      }
      DetallepedidoController controller = (DetallepedidoController) facesContext.getApplication().getELResolver().
              getValue(facesContext.getELContext(), null, "detallepedidoController");
      return controller.getDetallepedido(getKey(value));
    }

    java.lang.Integer getKey(String value) {
      java.lang.Integer key;
      key = Integer.valueOf(value);
      return key;
    }

    String getStringKey(java.lang.Integer value) {
      StringBuilder sb = new StringBuilder();
      sb.append(value);
      return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
      if (object == null) {
        return null;
      }
      if (object instanceof Detallepedido) {
        Detallepedido o = (Detallepedido) object;
        return getStringKey(o.getIdDetallepedido());
      } else {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Detallepedido.class.getName()});
        return null;
      }
    }

  }

}
