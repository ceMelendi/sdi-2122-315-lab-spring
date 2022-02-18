package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {

    private List<Professor> professorList = new ArrayList<>();


    public void addProfessor(Professor professor) {
        professorList.add(professor);
    }

    public void deleteProfessor(Long id){
        professorList.removeIf(p -> p.getId().equals(id));
    }

    public Professor getProfessor(Long id) {
        return professorList.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void editProfessor(Long id, Professor professor) {
        for (Professor p: professorList) {
            if(p.getId().equals(id)){
                p.setDni(professor.getDni()!=null?professor.getDni():p.getDni());
                p.setName(professor.getName()!=null?professor.getName():p.getName());
                p.setSurname(professor.getSurname()!=null?professor.getSurname():p.getSurname());
                p.setCategoria(professor.getCategoria()!=null?professor.getCategoria():p.getCategoria());
            }
        }
    }
}
