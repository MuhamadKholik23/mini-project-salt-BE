package com.project.konsumenservices.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.konsumenservices.models.Konsumen;
import com.project.konsumenservices.models.KonsumenRequest;

@Service
public class KonsumenService {
  @Autowired
  DataSource ds;

  public void createKonsumen (Konsumen konsumen) throws Exception {
    Connection conn = null;
    PreparedStatement pStatement = null;

    String SQL = " INSERT INTO konsumen " +
                 " (nama, alamat, kota, provinsi, tgl_registrasi, status) " +
                 " VALUES (?, ?, ?, ?, NOW(), ?)";

    try {
      conn = this.ds.getConnection();
      pStatement = conn.prepareStatement(SQL);

      pStatement.setString(1, konsumen.getNama());
      pStatement.setString(2, konsumen.getAlamat());
      pStatement.setString(3, konsumen.getKota());
      pStatement.setString(4, konsumen.getProvinsi());
      pStatement.setString(5, konsumen.getStatus());

      pStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Gagal menambahkan Konsumen !", e);
    }
  }

  public List<Konsumen> getAllKonsumenByFilter (KonsumenRequest f) throws Exception {
    Connection conn = null;
    PreparedStatement pStatement = null;
    ResultSet rs = null;
    List<Konsumen> listkonsumen = null;

    String SQL = " SELECT id_konsumen, nama, alamat, kota, provinsi, tgl_registrasi, status " +
                 " FROM konsumen" +
                 " WHERE 1 = 1 ";

    try {
      conn = this.ds.getConnection();

      if(f.getNama() != null && f.getNama() != ""){
        SQL += " AND LOWER(nama) LIKE LOWER('%" + f.getNama() + "%')";
      }
      if(f.getKota() != null && f.getKota() != ""){
        SQL += " AND LOWER(kota) LIKE LOWER('%" + f.getKota() + "%')";
      }

      pStatement = conn.prepareStatement(SQL);

      rs = pStatement.executeQuery();

      listkonsumen = new ArrayList<Konsumen>();
      while (rs.next()) {
        Konsumen konsumen = new Konsumen();
        konsumen.setIdKonsumen(rs.getInt("id_konsumen"));
        konsumen.setNama(rs.getString("nama"));
        konsumen.setAlamat(rs.getString("alamat"));
        konsumen.setKota(rs.getString("kota"));
        konsumen.setProvinsi(rs.getString("provinsi"));
        konsumen.setTglRegistrasi(rs.getDate("tgl_registrasi"));
        konsumen.setStatus(rs.getString("status"));
        listkonsumen.add(konsumen);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Gagal mengambil konsumen", e);
    }
    return listkonsumen;
  }

  public Konsumen getKonsumenByid (Integer idKonsumen) throws Exception {
    Connection conn = null;
    PreparedStatement pStatement = null;
    ResultSet rs = null;
    Konsumen konsumen = null;

    String SQL = " SELECT id_konsumen, nama, alamat, kota, provinsi, tgl_registrasi, status " +
                 " FROM konsumen " +
                 " WHERE id_konsumen = ? ";

    try {
      conn = this.ds.getConnection();
      pStatement = conn.prepareStatement(SQL);
      pStatement.setInt(1, idKonsumen);

      rs = pStatement.executeQuery();
      
      konsumen = new Konsumen();

      if (rs.next()) {
        konsumen.setIdKonsumen(rs.getInt("id_konsumen"));
        konsumen.setNama(rs.getString("nama"));
        konsumen.setAlamat(rs.getString("alamat"));
        konsumen.setKota(rs.getString("kota"));
        konsumen.setProvinsi(rs.getString("provinsi"));
        konsumen.setTglRegistrasi(rs.getDate("tgl_registrasi"));
        konsumen.setStatus(rs.getString("status"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Gagal mengambil konsumen By Id", e);
    }
    return konsumen;
  }

  public void updateKonsumenByid (Konsumen konsumen) throws Exception {
    Connection conn = null;
    PreparedStatement pStatement = null;

    String SQL = " UPDATE konsumen " + 
                 " SET nama = ?, alamat = ?, kota = ?, provinsi = ?  " +
                 " WHERE id_konsumen = ?";

    try {
      conn = this.ds.getConnection();
      pStatement = conn.prepareStatement(SQL);

      pStatement.setString(1, konsumen.getNama());
      pStatement.setString(2, konsumen.getAlamat());
      pStatement.setString(3, konsumen.getKota());
      pStatement.setString(4, konsumen.getProvinsi());
      pStatement.setInt(5, konsumen.getIdKonsumen());

      pStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Gagal update konsumen", e);
    }
  }

  public void deleteKonsumenByid (Integer idKonsumen) throws Exception {
    Connection conn = null;
    PreparedStatement pStatement = null;

    String SQL = " DELETE FROM konsumen WHERE id_konsumen = ? ";

    try {
      conn = this.ds.getConnection();
      pStatement = conn.prepareStatement(SQL);

      pStatement.setInt(1, idKonsumen);

      pStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Gagal delete konsumen", e);
    }
  }
}
