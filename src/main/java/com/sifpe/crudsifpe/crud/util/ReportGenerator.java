package com.sifpe.crudsifpe.crud.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ReportGenerator {

    private String parameterData;
    private String classPath;

    public byte[] exportToPdf(List<?> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(this.getReport(list));

    }

    public byte[] exportToXls(List<?> list) throws JRException, FileNotFoundException {

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);

        JRXlsExporter xlsExporter = new JRXlsExporter();
        xlsExporter.setExporterInput(new SimpleExporterInput(this.getReport(list)));
        xlsExporter.setExporterOutput(output);
        xlsExporter.exportReport();

        output.close();

        return byteArray.toByteArray();
    }

    private JasperPrint getReport(List<?> list) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<>();
//        params.put("activityData", new JRBeanCollectionDataSource(list));
        params.put(this.parameterData, new JRBeanCollectionDataSource(list));

        return JasperFillManager
                .fillReport(JasperCompileManager
                                .compileReport(ResourceUtils
//                                .getFile("classpath:activityReport.jrxml")
                                        .getFile(this.classPath)
                                        .getAbsolutePath()), params, new JREmptyDataSource()
                );
    }
}
