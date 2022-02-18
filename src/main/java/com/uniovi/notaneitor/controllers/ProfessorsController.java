package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfessorsController {

    @Autowired
    private ProfessorsService professorsService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        model.addAttribute("professorList", professorsService.getProfessors());
        return professorsService.getProfessors().toString();
    }

    //add, edit, detail, delete
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor){
        professorsService.addProfessor(professor);
        return "Professor added";
    }

    @RequestMapping(value = "/professor/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable Long id){
        professor.setId(id);
        professorsService.addProfessor(professor);
        return "Professor edited";
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return professorsService.getProfessor(id) != null
                ? professorsService.getProfessor(id).toString()
                : "No professor with such id";
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id){
        professorsService.deleteProfessor(id);
        return "Professor deleted";
    }

}
