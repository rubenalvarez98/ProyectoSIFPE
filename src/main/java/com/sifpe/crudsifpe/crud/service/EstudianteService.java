package com.sifpe.crudsifpe.crud.service;

import com.sifpe.crudsifpe.crud.dto.EstudianteDto;

import com.sifpe.crudsifpe.crud.entity.Estudiante;
import com.sifpe.crudsifpe.crud.repository.EstudianteRepository;
import com.sifpe.crudsifpe.crud.util.EstudentsReportGenerator;
import com.sifpe.crudsifpe.global.exepctions.AttributeException;
import com.sifpe.crudsifpe.global.exepctions.ResourseNotFundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    EstudentsReportGenerator estudentsReportGenerator;
    public List<Estudiante> getAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante getOne(int id) throws ResourseNotFundException {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));

    }
    public Estudiante getOneForDocument(String documento) throws ResourseNotFundException {

        return estudianteRepository.findByDocumento(documento)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
    }
    public Estudiante getOneForDocuemnt(String documento)throws ResourseNotFundException{
        return estudianteRepository.findByDocumento(documento)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
    }

    public Estudiante save(EstudianteDto dto) throws AttributeException {
        if (estudianteRepository.existsByNombre(dto.getNombre()))
            throw new AttributeException("El nombre ya esta en uso");
        int id = autoIncrement();
        Estudiante estudiante = new Estudiante(id, dto.getNombre(), dto.getEdad(), dto.getSexo(),
                dto.getTipodocumento(), dto.getDocumento(), dto.getFechanacimiento());
        return estudianteRepository.save(estudiante);
    }

    public Estudiante update(int id, EstudianteDto dto) throws ResourseNotFundException, AttributeException {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
        if (estudianteRepository.existsByNombre(dto.getNombre())
                && estudianteRepository.findByNombre(dto.getNombre()).get().getId() != id)
            throw new AttributeException("El nombre ya esta en uso");
        estudiante.setNombre(dto.getNombre());
        estudiante.setEdad(dto.getEdad());
        estudiante.setSexo(dto.getSexo());
        estudiante.setTipodocumento(dto.getTipodocumento());
        estudiante.setDocumento(dto.getDocumento());
        estudiante.setFechanacimiento(dto.getFechanacimiento());
        return estudianteRepository.save(estudiante);
    }

    public Estudiante delete(int id) throws ResourseNotFundException {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
        estudianteRepository.delete(estudiante);
        return estudiante;
    }

    public byte[] exportPdf() throws JRException, FileNotFoundException {
        return estudentsReportGenerator.exportToPdf(estudianteRepository.findAll());
    }

    public byte[] exportXls() throws JRException, FileNotFoundException {
        return estudentsReportGenerator.exportToXls(estudianteRepository.findAll());
    }

    // PRIVATE METODOS
    private int autoIncrement() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        return estudiantes.isEmpty() ? 1
                : estudiantes.stream().max(Comparator.comparing(Estudiante::getId)).get().getId() + 1;
    }

}
