package com.sifpe.crudsifpe.crud.controller;

import com.sifpe.crudsifpe.crud.dto.PsicologicoDto;
import com.sifpe.crudsifpe.crud.entity.Psicologico;
import com.sifpe.crudsifpe.crud.service.PsicologicoService;
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
public class PsicologicoController {
    @Autowired
    PsicologicoService psicologicoService;

    @PreAuthorize("hasRole('PSICOLOGA')")
    @GetMapping("/psicologicos")
    public ResponseEntity<List<Psicologico>> getAll() {

        return ResponseEntity.ok(psicologicoService.getAll());
    }

    @PreAuthorize("hasRole('PSICOLOGA')")
    @GetMapping("/psicologico/{id}")
    public ResponseEntity<Psicologico> getOne(@PathVariable("id") int id) throws ResourseNotFundException {
        return ResponseEntity.ok(psicologicoService.getOne(id));
    }

    @PreAuthorize("hasRole('PSICOLOGA')")
    @GetMapping("/psicologicos/export/pdf")
    public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reportes_estudiantes_sifpe", "reportes_estudiantes_sifpe.pdf");

        byte[] activityReport = psicologicoService.exportPdf();
        if (activityReport == null && activityReport.length > 0) {

            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(activityReport);
    }

    @PreAuthorize("hasRole('PSICOLOGA')")
    @GetMapping("/psicologicos/export/xls")
    public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");

        ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("reportes_actividades_sifpe" + ".xls").build();
        headers.setContentDisposition(contentDisposition);

        byte[] activityXls = psicologicoService.exportXls();
        if (activityXls == null && activityXls.length > 0) return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(activityXls);
    }

    @PreAuthorize("hasRole('PSICOLOGA')")
    @PostMapping("/psicologico")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody PsicologicoDto dto) {
        System.out.println(dto);
        Psicologico psicologico = psicologicoService.save(dto);
        String message = "El seguimiento " + psicologico.getTiposeguimiento() + " han sido guardados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PreAuthorize("hasRole('PSICOLOGA')")
    @PutMapping("/psicologico/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody PsicologicoDto dto)
            throws ResourseNotFundException, AttributeException {
        Psicologico psicologico = psicologicoService.update(id, dto);
        String message = "El seguimiento " + psicologico.getTiposeguimiento() + " han sido actualizados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PreAuthorize("hasRole('PSICOLOGA')")
    @DeleteMapping("/psicologico/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourseNotFundException {
        Psicologico psicologico = psicologicoService.delete(id);
        String message = "El seguimiento " + psicologico.getTiposeguimiento() + " han sido eliminados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

}
