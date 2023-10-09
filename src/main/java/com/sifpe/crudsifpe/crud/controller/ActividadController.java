package com.sifpe.crudsifpe.crud.controller;

import com.sifpe.crudsifpe.crud.dto.ActividadDto;
import com.sifpe.crudsifpe.crud.entity.Actividad;
import com.sifpe.crudsifpe.crud.service.ActividadService;
import com.sifpe.crudsifpe.global.dto.MessageDto;
import com.sifpe.crudsifpe.global.exepctions.AttributeException;
import com.sifpe.crudsifpe.global.exepctions.ResourseNotFundException;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ActividadController {
    @Autowired
    ActividadService actividadService;

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('DOCENTE')")
    @GetMapping("/actividades")
    public ResponseEntity<List<Actividad>> getAll() {
        return ResponseEntity.ok(actividadService.getAll());
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('DOCENTE')")
    @GetMapping("/actividad/{id}")
    public ResponseEntity<Actividad> getOne(@PathVariable("id") int id) throws ResourseNotFundException {
        return ResponseEntity.ok(actividadService.getOne(id));
    }

    @PreAuthorize("hasRole('DIRECTORA')")
    @GetMapping("/actividades/export/pdf")
    public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reportes_actividades_sifpe", "reportes_actividades_sifpe.pdf");

        byte[] activityReport = actividadService.exportPdf();
        if (activityReport == null && activityReport.length > 0) {

            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(activityReport);
    }

    @PreAuthorize("hasRole('DIRECTORA')")
    @GetMapping("/actividades/export/xls")
    public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");

        ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("reportes_actividades_sifpe" + ".xls").build();
        headers.setContentDisposition(contentDisposition);

        byte[] activityXls = actividadService.exportXls();
        if (activityXls == null && activityXls.length > 0) return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(activityXls);
    }

    @PreAuthorize("hasRole('DIRECTORA')")
    @PostMapping("/actividad")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody ActividadDto dto) throws AttributeException {
        System.out.println("hola");
        Actividad actividad = actividadService.save(dto);
        String message = "actividad " + actividad.getNombre() + " han sido guardados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PreAuthorize("hasRole('DIRECTORA')")
    @PutMapping("/actividad/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody ActividadDto dto)
            throws ResourseNotFundException, AttributeException {
        Actividad actividad = actividadService.update(id, dto);
        String message = "actividad " + actividad.getNombre() + " han sido actualizados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PreAuthorize("hasRole('DIRECTORA')")
    @DeleteMapping("/actividad/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourseNotFundException {
        Actividad actividad = actividadService.delete(id);
        String message = "actividad " + actividad.getNombre() + " han sido eliminados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }
}
