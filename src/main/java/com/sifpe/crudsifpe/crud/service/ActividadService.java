package com.sifpe.crudsifpe.crud.service;

import com.sifpe.crudsifpe.crud.dto.ActividadDto;
import com.sifpe.crudsifpe.crud.entity.Actividad;
import com.sifpe.crudsifpe.crud.repository.ActividadRepository;
import com.sifpe.crudsifpe.crud.util.ActivityReportGenerator;
import com.sifpe.crudsifpe.global.exepctions.AttributeException;
import com.sifpe.crudsifpe.global.exepctions.ResourseNotFundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

@Service
public class ActividadService {
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    ActivityReportGenerator activityReportGenerator;

    public List<Actividad> getAll() {
        return actividadRepository.findAll();
    }

    public Actividad getOne(int id) throws ResourseNotFundException {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));

    }

    public Actividad save(ActividadDto dto) throws AttributeException {
        if (actividadRepository.existsByNombre(dto.getNombre()))
            throw new AttributeException("El nombre ya esta en uso");
        int id = autoIncrement();
        Actividad actividad = new Actividad(id, dto.getNombre(), dto.getDescripcion(), dto.getObservaciones(), dto.getFecha());
        return actividadRepository.save(actividad);
    }

    public Actividad update(int id, ActividadDto dto) throws ResourseNotFundException, AttributeException {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
        if (actividadRepository.existsByNombre(dto.getNombre()) && actividadRepository.findByNombre(dto.getNombre()).get().getId() != id)
            throw new AttributeException("El nombre ya esta en uso");
        actividad.setNombre(dto.getNombre());
        actividad.setDescripcion(dto.getDescripcion());
        actividad.setObservaciones(dto.getObservaciones());
        actividad.setFecha(dto.getFecha());
        return actividadRepository.save(actividad);
    }

    public Actividad delete(int id) throws ResourseNotFundException {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
        actividadRepository.delete(actividad);
        return actividad;
    }

    public byte[] exportPdf() throws JRException, FileNotFoundException {
        return activityReportGenerator.exportToPdf(actividadRepository.findAll());
    }

    public byte[] exportXls() throws JRException, FileNotFoundException {
        return activityReportGenerator.exportToXls(actividadRepository.findAll());
    }

    //PRIVATE METODOS
    private int autoIncrement() {
        List<Actividad> actividades = actividadRepository.findAll();
        return actividades.isEmpty() ? 1 :
                actividades.stream().max(Comparator.comparing(Actividad::getId)).get().getId() + 1;
    }
}
