package com.sifpe.crudsifpe.crud.service;

import com.sifpe.crudsifpe.crud.entity.Role;
import com.sifpe.crudsifpe.crud.repository.RoleRepository;
import com.sifpe.crudsifpe.global.exepctions.ResourseNotFundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role getName(String name) throws ResourseNotFundException {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourseNotFundException("no encontrado"));
    }
}
