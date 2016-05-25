/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Raul
 */
@Entity
@Table(name = "pedidocompra")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Pedidocompra.findAll", query = "SELECT p FROM Pedidocompra p"),
  @NamedQuery(name = "Pedidocompra.findByIdPedidocompra", query = "SELECT p FROM Pedidocompra p WHERE p.idPedidocompra = :idPedidocompra"),
  @NamedQuery(name = "Pedidocompra.findByFecha", query = "SELECT p FROM Pedidocompra p WHERE p.fecha = :fecha")})
public class Pedidocompra implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idPedidocompra")
  private Integer idPedidocompra;
  @Basic(optional = false)
  @NotNull
  @Column(name = "fecha")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fecha;
  @JoinColumn(name = "idProveedor", referencedColumnName = "idProveedor")
  @ManyToOne(optional = false)
  private Proveedor idProveedor;
  @JoinColumn(name = "idCompra", referencedColumnName = "idCompra")
  @ManyToOne(optional = false)
  private Compra idCompra;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPedidocompra")
  private List<Detallepedido> detallepedidoList;

  public Pedidocompra() {
  }

  public Pedidocompra(Integer idPedidocompra) {
    this.idPedidocompra = idPedidocompra;
  }

  public Pedidocompra(Integer idPedidocompra, Date fecha) {
    this.idPedidocompra = idPedidocompra;
    this.fecha = fecha;
  }

  public Integer getIdPedidocompra() {
    return idPedidocompra;
  }

  public void setIdPedidocompra(Integer idPedidocompra) {
    this.idPedidocompra = idPedidocompra;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Proveedor getIdProveedor() {
    return idProveedor;
  }

  public void setIdProveedor(Proveedor idProveedor) {
    this.idProveedor = idProveedor;
  }

  public Compra getIdCompra() {
    return idCompra;
  }

  public void setIdCompra(Compra idCompra) {
    this.idCompra = idCompra;
  }

  @XmlTransient
  public List<Detallepedido> getDetallepedidoList() {
    return detallepedidoList;
  }

  public void setDetallepedidoList(List<Detallepedido> detallepedidoList) {
    this.detallepedidoList = detallepedidoList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPedidocompra != null ? idPedidocompra.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Pedidocompra)) {
      return false;
    }
    Pedidocompra other = (Pedidocompra) object;
    if ((this.idPedidocompra == null && other.idPedidocompra != null) || (this.idPedidocompra != null && !this.idPedidocompra.equals(other.idPedidocompra))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Pedidocompra[ idPedidocompra=" + idPedidocompra + " ]";
  }
  
}
