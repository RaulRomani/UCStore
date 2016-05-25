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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
  @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
  @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
  @NamedQuery(name = "Producto.findByPrecioVenta", query = "SELECT p FROM Producto p WHERE p.precioVenta = :precioVenta"),
  @NamedQuery(name = "Producto.findByUnidadCompra", query = "SELECT p FROM Producto p WHERE p.unidadCompra = :unidadCompra"),
  @NamedQuery(name = "Producto.findByUnidadVenta", query = "SELECT p FROM Producto p WHERE p.unidadVenta = :unidadVenta"),
  @NamedQuery(name = "Producto.findByMarca", query = "SELECT p FROM Producto p WHERE p.marca = :marca"),
  @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
  @NamedQuery(name = "Producto.findByFechaVencimiento", query = "SELECT p FROM Producto p WHERE p.fechaVencimiento = :fechaVencimiento"),
  @NamedQuery(name = "Producto.findByStock", query = "SELECT p FROM Producto p WHERE p.stock = :stock"),
  @NamedQuery(name = "Producto.findByTipo", query = "SELECT p FROM Producto p WHERE p.tipo = :tipo")})
public class Producto implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "idProducto")
  private Integer idProducto;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "nombre")
  private String nombre;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Column(name = "precioVenta")
  private BigDecimal precioVenta;
  @Size(max = 30)
  @Column(name = "unidadCompra")
  private String unidadCompra;
  @Size(max = 30)
  @Column(name = "unidadVenta")
  private String unidadVenta;
  @Size(max = 30)
  @Column(name = "marca")
  private String marca;
  @Size(max = 200)
  @Column(name = "descripcion")
  private String descripcion;
  @Column(name = "fechaVencimiento")
  @Temporal(TemporalType.DATE)
  private Date fechaVencimiento;
  @Basic(optional = false)
  @NotNull
  @Column(name = "stock")
  private int stock;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 8)
  @Column(name = "tipo")
  private String tipo;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
  private List<Productoventa> productoventaList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
  private List<Detallepedido> detallepedidoList;
  @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
  @ManyToOne(optional = false)
  private Categoria idCategoria;

  public Producto() {
  }

  public Producto(Integer idProducto) {
    this.idProducto = idProducto;
  }

  public Producto(Integer idProducto, String nombre, BigDecimal precioVenta, int stock, String tipo) {
    this.idProducto = idProducto;
    this.nombre = nombre;
    this.precioVenta = precioVenta;
    this.stock = stock;
    this.tipo = tipo;
  }

  public Integer getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Integer idProducto) {
    this.idProducto = idProducto;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public BigDecimal getPrecioVenta() {
    return precioVenta;
  }

  public void setPrecioVenta(BigDecimal precioVenta) {
    this.precioVenta = precioVenta;
  }

  public String getUnidadCompra() {
    return unidadCompra;
  }

  public void setUnidadCompra(String unidadCompra) {
    this.unidadCompra = unidadCompra;
  }

  public String getUnidadVenta() {
    return unidadVenta;
  }

  public void setUnidadVenta(String unidadVenta) {
    this.unidadVenta = unidadVenta;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(Date fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  @XmlTransient
  public List<Productoventa> getProductoventaList() {
    return productoventaList;
  }

  public void setProductoventaList(List<Productoventa> productoventaList) {
    this.productoventaList = productoventaList;
  }

  @XmlTransient
  public List<Detallepedido> getDetallepedidoList() {
    return detallepedidoList;
  }

  public void setDetallepedidoList(List<Detallepedido> detallepedidoList) {
    this.detallepedidoList = detallepedidoList;
  }

  public Categoria getIdCategoria() {
    return idCategoria;
  }

  public void setIdCategoria(Categoria idCategoria) {
    this.idCategoria = idCategoria;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idProducto != null ? idProducto.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Producto)) {
      return false;
    }
    Producto other = (Producto) object;
    if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ultracolor.entities.Producto[ idProducto=" + idProducto + " ]";
  }
  
}
