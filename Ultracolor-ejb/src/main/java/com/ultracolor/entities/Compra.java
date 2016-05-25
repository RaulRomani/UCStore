/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Raul
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
  @NamedQuery(name = "Compra.findByIdCompra", query = "SELECT c FROM Compra c WHERE c.idCompra = :idCompra"),
  @NamedQuery(name = "Compra.findByTotal", query = "SELECT c FROM Compra c WHERE c.total = :total"),
  @NamedQuery(name = "Compra.findByFechaHora", query = "SELECT c FROM Compra c WHERE c.fechaHora = :fechaHora"),
  @NamedQuery(name = "Compra.findByComprobante", query = "SELECT c FROM Compra c WHERE c.comprobante = :comprobante"),
  @NamedQuery(name = "Compra.findByEstado", query = "SELECT c FROM Compra c WHERE c.estado = :estado")})
public class Compra implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idCompra")
  private Integer idCompra;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "total")
  private BigDecimal total;
  @Column(name = "fechaHora")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaHora;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "comprobante")
  private String comprobante;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "estado")
  private String estado;
  @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
  @ManyToOne(optional = false)
  private Usuario idUsuario;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCompra")
  private List<Pedidocompra> pedidocompraList;

  public Compra() {
  }

  public Compra(Integer idCompra) {
    this.idCompra = idCompra;
  }

  public Compra(Integer idCompra, BigDecimal total, String comprobante, String estado) {
    this.idCompra = idCompra;
    this.total = total;
    this.comprobante = comprobante;
    this.estado = estado;
  }

  public Integer getIdCompra() {
    return idCompra;
  }

  public void setIdCompra(Integer idCompra) {
    this.idCompra = idCompra;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public Date getFechaHora() {
    return fechaHora;
  }

  public void setFechaHora(Date fechaHora) {
    this.fechaHora = fechaHora;
  }

  public String getComprobante() {
    return comprobante;
  }

  public void setComprobante(String comprobante) {
    this.comprobante = comprobante;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Usuario getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Usuario idUsuario) {
    this.idUsuario = idUsuario;
  }

  @XmlTransient
  public List<Pedidocompra> getPedidocompraList() {
    return pedidocompraList;
  }

  public void setPedidocompraList(List<Pedidocompra> pedidocompraList) {
    this.pedidocompraList = pedidocompraList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCompra != null ? idCompra.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Compra)) {
      return false;
    }
    Compra other = (Compra) object;
    if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Compra[ idCompra=" + idCompra + " ]";
  }
  
}
