package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.repositories.ProfessorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {

    @Autowired
    private ProfessorsRepository professorsRepository;


    public void addProfessor(Professor professor) {
        professorsRepository.save(professor);
    }

    public void deleteProfessor(Long id) {
        professorsRepository.deleteById(id);
    }

    public Professor getProfessor(Long id) {
        return professorsRepository.findById(id).get();
    }

    public Object getProfessors() {
        List<Professor> professors = new ArrayList<>();
        professorsRepository.findAll().forEach(professors::add);
        return professors;
    }
}
