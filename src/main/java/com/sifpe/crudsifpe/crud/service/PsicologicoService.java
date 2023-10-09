package com.sifpe.crudsifpe.crud.service;


import com.sifpe.crudsifpe.crud.dto.PsicologicoDto;
import com.sifpe.crudsifpe.crud.entity.Psicologico;
import com.sifpe.crudsifpe.crud.repository.PsicologicoRepository;
import com.sifpe.crudsifpe.crud.util.PsychologicalReportsGeneration;
import com.sifpe.crudsifpe.global.exepctions.AttributeException;
import com.sifpe.crudsifpe.global.exepctions.ResourseNotFundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

@Service
public class PsicologicoService {
    @Autowired
    PsicologicoRepository psicologicoRepository;
    @Autowired
    PsychologicalReportsGeneration psychologicalReportsGeneration;

    public List<Psicologico> getAll() {
        return psicologicoRepository.findAll();
    }

    public Psicologico getOne(int id) throws ResourseNotFundException {
        return psicologicoRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not fund"));

    }

    public Psicologico save(PsicologicoDto dto) {

        int id = autoIncrement();
        Psicologico psicologico = new Psicologico(id,
                dto.getEstudiante(),
                dto.getTiposeguimiento(),
                dto.getDescripcion(),
                dto.getEstadoseguimiento(),
                dto.getFechaseguimiento());
        return psicologicoRepository.save(psicologico);
    }

    public Psicologico update(int id, PsicologicoDto dto) throws ResourseNotFundException, AttributeException {
        Psicologico psicologico = psicologicoRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
        // if (psicologicoRepository.existsByNombre(dto.getNombre())
        //  && psicologicoRepository.findByNombre(dto.getNombre()).get().getId() != id)
        // throw new AttributeException("El nombre ya esta en uso");
        psicologico.setEstudiante(dto.getEstudiante());
        psicologico.setTiposeguimiento(dto.getTiposeguimiento());
        psicologico.setDescripcion(dto.getDescripcion());
        psicologico.setEstadoseguimiento(dto.getEstadoseguimiento());
        psicologico.setFechaseguimiento(dto.getFechaseguimiento());
        return psicologicoRepository.save(psicologico);
    }

    public Psicologico delete(int id) throws ResourseNotFundException {
        Psicologico psicologico = psicologicoRepository.findById(id)
                .orElseThrow(() -> new ResourseNotFundException("not found"));
        psicologicoRepository.delete(psicologico);
        return psicologico;
    }

    public byte[] exportPdf() throws JRException, FileNotFoundException {

        return this.psychologicalReportsGeneration.exportToPdf( this.psicologicoRepository.findAll());
    }

    public byte[] exportXls() throws JRException, FileNotFoundException {
        return this.psychologicalReportsGeneration.exportToXls(this.psicologicoRepository.findAll());
    }

    private int autoIncrement() {
        List<Psicologico> psicologico = psicologicoRepository.findAll();
        return psicologico.isEmpty() ? 1
                : psicologico.stream().max(Comparator.comparing(Psicologico::getId)).get().getId() + 1;
    }


}
