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
@Table(name = "productoventa")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Productoventa.findAll", query = "SELECT p FROM Productoventa p"),
  @NamedQuery(name = "Productoventa.findByIdProductoVenta", query = "SELECT p FROM Productoventa p WHERE p.idProductoVenta = :idProductoVenta"),
  @NamedQuery(name = "Productoventa.findByCantidad", query = "SELECT p FROM Productoventa p WHERE p.cantidad = :cantidad"),
  @NamedQuery(name = "Productoventa.findByImporte", query = "SELECT p FROM Productoventa p WHERE p.importe = :importe")})
public class Productoventa implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idProductoVenta")
  private Integer idProductoVenta;
  @Basic(optional = false)
  @NotNull
  @Column(name = "cantidad")
  private int cantidad;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "importe")
  private BigDecimal importe;
  @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
  @ManyToOne(optional = false)
  private Producto idProducto;
  @JoinColumn(name = "idVenta", referencedColumnName = "idVenta")
  @ManyToOne(optional = false)
  private Venta idVenta;

  public Productoventa() {
  }

  public Productoventa(Integer idProductoVenta) {
    this.idProductoVenta = idProductoVenta;
  }

  public Productoventa(Integer idProductoVenta, int cantidad, BigDecimal importe) {
    this.idProductoVenta = idProductoVenta;
    this.cantidad = cantidad;
    this.importe = importe;
  }

  public Integer getIdProductoVenta() {
    return idProductoVenta;
  }

  public void setIdProductoVenta(Integer idProductoVenta) {
    this.idProductoVenta = idProductoVenta;
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

  public Producto getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Producto idProducto) {
    this.idProducto = idProducto;
  }

  public Venta getIdVenta() {
    return idVenta;
  }

  public void setIdVenta(Venta idVenta) {
    this.idVenta = idVenta;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idProductoVenta != null ? idProductoVenta.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Productoventa)) {
      return false;
    }
    Productoventa other = (Productoventa) object;
    if ((this.idProductoVenta == null && other.idProductoVenta != null) || (this.idProductoVenta != null && !this.idProductoVenta.equals(other.idProductoVenta))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Productoventa[ idProductoVenta=" + idProductoVenta + " ]";
  }
  
}
