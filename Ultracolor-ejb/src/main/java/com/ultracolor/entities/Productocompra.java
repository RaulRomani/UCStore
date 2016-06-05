/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Raul
 */
@Entity
@Table(name = "productocompra")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Productocompra.findAll", query = "SELECT p FROM Productocompra p"),
  @NamedQuery(name = "Productocompra.findByIdProductoCompra", query = "SELECT p FROM Productocompra p WHERE p.idProductoCompra = :idProductoCompra"),
  @NamedQuery(name = "Productocompra.findByCantidad", query = "SELECT p FROM Productocompra p WHERE p.cantidad = :cantidad"),
  @NamedQuery(name = "Productocompra.findByImporte", query = "SELECT p FROM Productocompra p WHERE p.importe = :importe"),
  @NamedQuery(name = "Productocompra.findByRecibido", query = "SELECT p FROM Productocompra p WHERE p.recibido = :recibido")})
public class Productocompra implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idProductoCompra")
  private Integer idProductoCompra;
  @Basic(optional = false)
  @NotNull
  @Column(name = "cantidad")
  private int cantidad;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "importe")
  private BigDecimal importe;
  @Basic(optional = false)
  @NotNull
  @Column(name = "recibido")
  private boolean recibido;
  @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
  @ManyToOne(optional = false)
  private Producto idProducto;
  @JoinColumn(name = "idCompra", referencedColumnName = "idCompra")
  @ManyToOne(optional = false)
  private Compra idCompra;

  public Productocompra() {
  }

  public Productocompra(Integer idProductoCompra) {
    this.idProductoCompra = idProductoCompra;
  }

  public Productocompra(Integer idProductoCompra, int cantidad, BigDecimal importe, boolean recibido) {
    this.idProductoCompra = idProductoCompra;
    this.cantidad = cantidad;
    this.importe = importe;
    this.recibido = recibido;
  }

  public Integer getIdProductoCompra() {
    return idProductoCompra;
  }

  public void setIdProductoCompra(Integer idProductoCompra) {
    this.idProductoCompra = idProductoCompra;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public BigDecimal getImporte() {
    return importe;
  }

  public void setImporte(BigDecimal importe) {
    this.importe = importe;
  }

  public boolean getRecibido() {
    return recibido;
  }

  public void setRecibido(boolean recibido) {
    this.recibido = recibido;
  }

  public Producto getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Producto idProducto) {
    this.idProducto = idProducto;
  }

  public Compra getIdCompra() {
    return idCompra;
  }

  public void setIdCompra(Compra idCompra) {
    this.idCompra = idCompra;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idProductoCompra != null ? idProductoCompra.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Productocompra)) {
      return false;
    }
    Productocompra other = (Productocompra) object;
    if ((this.idProductoCompra == null && other.idProductoCompra != null) || (this.idProductoCompra != null && !this.idProductoCompra.equals(other.idProductoCompra))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Productocompra[ idProductoCompra=" + idProductoCompra + " ]";
  }
  
}
