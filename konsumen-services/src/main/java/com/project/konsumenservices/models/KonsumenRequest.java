package com.project.konsumenservices.models;

import lombok.Data;

@Data
public class KonsumenRequest {
  private String nama;
  private String alamat;
  private String kota;
  private String provinsi;
}
