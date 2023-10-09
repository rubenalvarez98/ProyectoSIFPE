package com.sifpe.crudsifpe.crud.util;


import com.sifpe.crudsifpe.crud.entity.Actividad;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ActivityReportGenerator {
    //    @Autowired
//    ReportGenerator reportGenerator;
    private ReportGenerator reportGenerator;

    public ActivityReportGenerator() {
        this.reportGenerator = new ReportGenerator("activityData", "classpath:activityReport.jrxml");
    }

    public byte[] exportToPdf(List<Actividad> list) throws JRException, FileNotFoundException {
        return this.reportGenerator.exportToPdf(list);
    }

    public byte[] exportToXls(List<Actividad> list) throws JRException, FileNotFoundException {


        return this.reportGenerator.exportToXls(list);
    }

//    private JasperPrint getReport(List<Actividad> list) throws FileNotFoundException, JRException {
//        Map<String, Object> params = new HashMap<>();
//        params.put("activityData", new JRBeanCollectionDataSource(list));
//
//        return JasperFillManager
//                .fillReport(JasperCompileManager
//                        .compileReport(ResourceUtils
//                                .getFile("classpath:activityReport.jrxml")
//                                .getAbsolutePath()), params, new JREmptyDataSource()
//                );
//    }
}
