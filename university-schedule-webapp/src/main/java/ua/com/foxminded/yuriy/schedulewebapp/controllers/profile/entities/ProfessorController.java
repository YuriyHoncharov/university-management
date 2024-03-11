package ua.com.foxminded.yuriy.schedulewebapp.controllers.profile.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.schedulewebapp.entity.House;
import ua.com.foxminded.yuriy.schedulewebapp.entity.Professor;
import ua.com.foxminded.yuriy.schedulewebapp.entity.Subject;
import ua.com.foxminded.yuriy.schedulewebapp.entity.dto.ProfessorDto;
import ua.com.foxminded.yuriy.schedulewebapp.exception.ValidationException;
import ua.com.foxminded.yuriy.schedulewebapp.service.ProfessorService;
import ua.com.foxminded.yuriy.schedulewebapp.service.SubjectService;

@Controller
@RequestMapping("/profile/dashboard/professors")
@AllArgsConstructor
public class ProfessorController {

	private ProfessorService professorService;
	private SubjectService subjectService;

	@GetMapping
	public ModelAndView getProfessorPage() {
		List<ProfessorDto> professors = professorService.getAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("professors", professors);
		mav.setViewName("profile/entities/professors");
		return mav;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProfessor(@PathVariable Long id) {
		try {
			professorService.delete(id);
			return new ResponseEntity<>("Professor deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete professor : " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditView(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView();
		List<Subject> availableSubjects = subjectService.findAllUnassignedSubjects();
		return professorService.getById(id).map(professor -> {
			mav.addObject("actualSubject", professor.getSubjects().get(0));
			mav.addObject("availableSubjects", availableSubjects);
			mav.addObject("professor", professor);
			mav.setViewName("profile/entities/edit/professorEdit");
			return mav;
		}).orElseGet(() -> {
			mav.setViewName("redirect:profile/entities/professors");
			return mav;
		});
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Object> update(@RequestBody Professor professor, @PathVariable Long id) {
		try {
			Professor existingProfessor = professorService.getById(id).get();
			if (existingProfessor != null) {
				List<Subject> subject = new ArrayList<>();
				Subject assignedSubject = subjectService.getById(professor.getSubjects().get(0).getId()).get();
				if (assignedSubject != null) {
					subject.add(assignedSubject);
				}
				existingProfessor.setName(professor.getName());
				existingProfessor.setLastName(professor.getLastName());
				existingProfessor.setSubjects(subject);
			}
			Professor updatedProfessor = professorService.save(existingProfessor);
			return ResponseEntity.ok(updatedProfessor);

		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}