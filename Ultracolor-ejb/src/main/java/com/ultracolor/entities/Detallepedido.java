/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Raul
 */
@Entity
@Table(name = "detallepedido")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Detallepedido.findAll", query = "SELECT d FROM Detallepedido d"),
  @NamedQuery(name = "Detallepedido.findByIdDetallepedido", query = "SELECT d FROM Detallepedido d WHERE d.idDetallepedido = :idDetallepedido"),
  @NamedQuery(name = "Detallepedido.findByCantidad", query = "SELECT d FROM Detallepedido d WHERE d.cantidad = :cantidad"),
  @NamedQuery(name = "Detallepedido.findByPrecio", query = "SELECT d FROM Detallepedido d WHERE d.precio = :precio"),
  @NamedQuery(name = "Detallepedido.findByImporte", query = "SELECT d FROM Detallepedido d WHERE d.importe = :importe"),
  @NamedQuery(name = "Detallepedido.findByRecibido", query = "SELECT d FROM Detallepedido d WHERE d.recibido = :recibido"),
  @NamedQuery(name = "Detallepedido.findByFecha", query = "SELECT d FROM Detallepedido d WHERE d.fecha = :fecha")})
public class Detallepedido implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idDetallepedido")
  private Integer idDetallepedido;
  @Basic(optional = false)
  @NotNull
  @Column(name = "cantidad")
  private int cantidad;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "precio")
  private BigDecimal precio;
  @Basic(optional = false)
  @NotNull
  @Column(name = "importe")
  private BigDecimal importe;
  @Basic(optional = false)
  @NotNull
  @Column(name = "recibido")
  private boolean recibido;
  @Basic(optional = false)
  @NotNull
  @Column(name = "fecha")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;
  @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
  @ManyToOne(optional = false)
  private Producto idProducto;
  @JoinColumn(name = "idPedidocompra", referencedColumnName = "idPedidocompra")
  @ManyToOne(optional = false)
  private Pedidocompra idPedidocompra;

  public Detallepedido() {
  }

  public Detallepedido(Integer idDetallepedido) {
    this.idDetallepedido = idDetallepedido;
  }

  public Detallepedido(Integer idDetallepedido, int cantidad, BigDecimal precio, BigDecimal importe, boolean recibido, Date fecha) {
    this.idDetallepedido = idDetallepedido;
    this.cantidad = cantidad;
    this.precio = precio;
    this.importe = importe;
    this.recibido = recibido;
    this.fecha = fecha;
  }

  public Integer getIdDetallepedido() {
    return idDetallepedido;
  }

  public void setIdDetallepedido(Integer idDetallepedido) {
    this.idDetallepedido = idDetallepedido;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
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

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Producto getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Producto idProducto) {
    this.idProducto = idProducto;
  }

  public Pedidocompra getIdPedidocompra() {
    return idPedidocompra;
  }

  public void setIdPedidocompra(Pedidocompra idPedidocompra) {
    this.idPedidocompra = idPedidocompra;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idDetallepedido != null ? idDetallepedido.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Detallepedido)) {
      return false;
    }
    Detallepedido other = (Detallepedido) object;
    if ((this.idDetallepedido == null && other.idDetallepedido != null) || (this.idDetallepedido != null && !this.idDetallepedido.equals(other.idDetallepedido))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Detallepedido[ idDetallepedido=" + idDetallepedido + " ]";
  }
  
}
