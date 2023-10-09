package com.sifpe.crudsifpe.crud.util;

import com.sifpe.crudsifpe.crud.entity.Estudiante;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class EstudentsReportGenerator {

    private ReportGenerator reportGenerator;

    public EstudentsReportGenerator() {
        this.reportGenerator = new ReportGenerator("estudentData", "classpath:estudentReport.jrxml");
    }

    public byte[] exportToPdf(List<Estudiante> list) throws JRException, FileNotFoundException {
        return reportGenerator.exportToPdf(list);
    }

    public byte[] exportToXls(List<Estudiante> list) throws JRException, FileNotFoundException {
        return this.reportGenerator.exportToXls(list);
    }
}
