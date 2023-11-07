package com.project.konsumenservices.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.konsumenservices.models.Konsumen;
import com.project.konsumenservices.models.KonsumenRequest;
import com.project.konsumenservices.repository.KonsumenService;

@RestController
@RequestMapping("/konsumen")
public class KonsumenController {
  @Autowired
    private KonsumenService konsumenService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Konsumen>> getAllKonsumen(@RequestParam(name = "nama", required = false) String nama,
    @RequestParam(name = "kota", required = false) String kota) {
        KonsumenRequest f = new KonsumenRequest();
        try {
            f.setNama(nama);
            f.setKota(kota);
            
            List<Konsumen> konsumenList = konsumenService.getAllKonsumenByFilter(f);
            return new ResponseEntity<>(konsumenList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Konsumen> getKonsumenById(@PathVariable int id) {
        try {
            Konsumen konsumen = konsumenService.getKonsumenByid(id);
            if (konsumen != null) {
                return new ResponseEntity<>(konsumen, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Konsumen> updateKonsumen(@PathVariable int id, @RequestBody Konsumen konsumen) {
        try {
          konsumen.setIdKonsumen(id);
            konsumenService.updateKonsumenByid(konsumen);
            Konsumen konsumenNew = konsumenService.getKonsumenByid(id);
            if (konsumenNew != null) {
                return new ResponseEntity<>(konsumenNew, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteKonsumen(@PathVariable int id) {
        try {
          konsumenService.deleteKonsumenByid(id);
              
          return new ResponseEntity<>("Konsumen dengan ID " + id + " berhasil dihapus", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Gagal menghapus konsumen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Konsumen> createKonsumen(@RequestBody KonsumenRequest konsumenReq) {
      Konsumen konsumen = null;
        try {
          konsumen = new Konsumen();
          konsumen.setNama(konsumenReq.getNama());
          konsumen.setAlamat(konsumenReq.getAlamat());
          konsumen.setKota(konsumenReq.getKota());
          konsumen.setProvinsi(konsumenReq.getProvinsi());
          konsumen.setStatus("A");
          
          konsumenService.createKonsumen(konsumen);
          return new ResponseEntity<>(konsumen, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
