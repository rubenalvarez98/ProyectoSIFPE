package com.sifpe.crudsifpe.crud.controller;

import com.sifpe.crudsifpe.crud.dto.EstudianteDto;
import com.sifpe.crudsifpe.crud.entity.Estudiante;
import com.sifpe.crudsifpe.crud.service.EstudianteService;
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
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService;

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('DOCENTE')or hasRole('ASISTENTE') or hasRole('PSICOLOGA')")
    @GetMapping("/estudiantes")
    public ResponseEntity<List<Estudiante>> getAll() {
        return ResponseEntity.ok(estudianteService.getAll());
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('DOCENTE') or hasRole('ASISTENTE')")
    @GetMapping("/estudiante/{id}")
    public ResponseEntity<Estudiante> getOne(@PathVariable("id") int id) throws ResourseNotFundException {
        return ResponseEntity.ok(estudianteService.getOne(id));
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('DOCENTE') or hasRole('ASISTENTE') or hasRole('PSICOLOGA')")
    @GetMapping("/estudiante/buscar/{documento}")
    public ResponseEntity<Estudiante> getOneDocument(@PathVariable("documento") String documento)
            throws ResourseNotFundException {
        return ResponseEntity.ok(estudianteService.getOneForDocument(documento));
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('ASISTENTE')or hasRole('PSICOLOGA')")
    @GetMapping("/estudiantes/export/pdf")
    public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reportes_estudiantes_sifpe", "reportes_estudiantes_sifpe.pdf");

        byte[] activityReport = estudianteService.exportPdf();
        if (activityReport == null && activityReport.length > 0) {

            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(activityReport);
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('ASISTENTE')or hasRole('PSICOLOGA')")
    @GetMapping("/estudiantes/export/xls")
    public ResponseEntity<byte[]> exportXls() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");

        ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("reportes_actividades_sifpe" + ".xls").build();
        headers.setContentDisposition(contentDisposition);

        byte[] activityXls = estudianteService.exportXls();
        if (activityXls == null && activityXls.length > 0) return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(activityXls);
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('ASISTENTE')")
    @PostMapping("/estudiante")
    public ResponseEntity<MessageDto> save(@Valid @RequestBody EstudianteDto dto) throws AttributeException {
        Estudiante estudiante = estudianteService.save(dto);
        String message = "estudiante " + estudiante.getNombre() + " han sido guardados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('ASISTENTE')")
    @PutMapping("/estudiante/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody EstudianteDto dto)
            throws ResourseNotFundException, AttributeException {
        Estudiante estudiante = estudianteService.update(id, dto);
        String message = "estudiante " + estudiante.getNombre() + " han sido actualizados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PreAuthorize("hasRole('DIRECTORA') or hasRole('ASISTENTE')")
    @DeleteMapping("/estudiante/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourseNotFundException {
        Estudiante estudiante = estudianteService.delete(id);
        String message = "estudiante " + estudiante.getNombre() + " han sido eliminados";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

}
