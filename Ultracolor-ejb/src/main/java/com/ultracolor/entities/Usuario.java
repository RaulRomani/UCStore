/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultracolor.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Raul
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
  @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
  @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
  @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave"),
  @NamedQuery(name = "Usuario.findByRol", query = "SELECT u FROM Usuario u WHERE u.rol = :rol"),
  @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado"),
  @NamedQuery(name = "Usuario.findByNota", query = "SELECT u FROM Usuario u WHERE u.nota = :nota"),
  @NamedQuery(name = "Usuario.validar", query = "SELECT u FROM Usuario u WHERE u.clave = :clave and u.usuario = :usuario")})
public class Usuario implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idUsuario")
  private Integer idUsuario;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "usuario")
  private String usuario;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "clave")
  private String clave;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "rol")
  private String rol;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "estado")
  private String estado;
  @Size(max = 200)
  @Column(name = "nota")
  private String nota;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
  private List<Compra> compraList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
  private List<Pago> pagoList;
  @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
  @ManyToOne(optional = false)
  private Personal idPersonal;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
  private List<Venta> ventaList;

  public Usuario() {
  }

  public Usuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Usuario(Integer idUsuario, String usuario, String clave, String rol, String estado) {
    this.idUsuario = idUsuario;
    this.usuario = usuario;
    this.clave = clave;
    this.rol = rol;
    this.estado = estado;
  }

  public Integer getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNota() {
    return nota;
  }

  public void setNota(String nota) {
    this.nota = nota;
  }

  @XmlTransient
  public List<Compra> getCompraList() {
    return compraList;
  }

  public void setCompraList(List<Compra> compraList) {
    this.compraList = compraList;
  }

  @XmlTransient
  public List<Pago> getPagoList() {
    return pagoList;
  }

  public void setPagoList(List<Pago> pagoList) {
    this.pagoList = pagoList;
  }

  public Personal getIdPersonal() {
    return idPersonal;
  }

  public void setIdPersonal(Personal idPersonal) {
    this.idPersonal = idPersonal;
  }

  @XmlTransient
  public List<Venta> getVentaList() {
    return ventaList;
  }

  public void setVentaList(List<Venta> ventaList) {
    this.ventaList = ventaList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idUsuario != null ? idUsuario.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Usuario)) {
      return false;
    }
    Usuario other = (Usuario) object;
    if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Usuario[ idUsuario=" + idUsuario + " ]";
  }
  
}
