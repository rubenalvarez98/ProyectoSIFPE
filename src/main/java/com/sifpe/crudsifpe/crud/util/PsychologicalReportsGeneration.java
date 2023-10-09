package com.sifpe.crudsifpe.crud.util;

import com.sifpe.crudsifpe.crud.entity.Psicologico;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
public class PsychologicalReportsGeneration {

    private ReportGenerator reportGenerator;

    public PsychologicalReportsGeneration() {
        this.reportGenerator = new ReportGenerator("psychogicalData", "classpath:psychologicalReport.jrxml");
    }

    private List<PsychologicalReport> mapReport(List<Psicologico> list) {
        List<PsychologicalReport> psychologicalReports = new ArrayList<>();

        list.forEach(data -> {
            PsychologicalReport report = new PsychologicalReport();

            report.setId(data.getId());
            report.setNombre(data.getEstudiante().getNombre());
            report.setDocumento(data.getEstudiante().getDocumento());
            report.setTiposeguimiento(data.getTiposeguimiento());
            report.setDescripcion(data.getDescripcion());
            report.setEstadoseguimiento(data.getEstadoseguimiento());
            report.setFechaseguimiento(data.getFechaseguimiento());

            psychologicalReports.add(report);

        });

        return psychologicalReports;
    }

    public byte[] exportToPdf(List<Psicologico> list) throws JRException, FileNotFoundException {
        return this.reportGenerator.exportToPdf(this.mapReport(list));
    }

    public byte[] exportToXls(List<Psicologico> list) throws JRException, FileNotFoundException {
        return this.reportGenerator.exportToXls(this.mapReport(list));
    }
}
