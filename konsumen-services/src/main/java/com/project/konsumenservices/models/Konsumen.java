package com.project.konsumenservices.models;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "konsumen")
public class Konsumen {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idKonsumen;
  private String nama;
  private String alamat;
  private String kota;
  private String provinsi;
  private Date tglRegistrasi;
  private String status;
}
