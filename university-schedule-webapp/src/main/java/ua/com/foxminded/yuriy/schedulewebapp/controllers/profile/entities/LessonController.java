package ua.com.foxminded.yuriy.schedulewebapp.controllers.profile.entities;

import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.schedulewebapp.entity.Lesson;
import ua.com.foxminded.yuriy.schedulewebapp.entity.dto.LessonDto;
import ua.com.foxminded.yuriy.schedulewebapp.exception.ValidationException;
import ua.com.foxminded.yuriy.schedulewebapp.service.AuditoriumService;
import ua.com.foxminded.yuriy.schedulewebapp.service.HouseService;
import ua.com.foxminded.yuriy.schedulewebapp.service.LessonService;
import ua.com.foxminded.yuriy.schedulewebapp.service.ProfessorService;
import ua.com.foxminded.yuriy.schedulewebapp.service.SubjectService;
import ua.com.foxminded.yuriy.schedulewebapp.service.YearService;

@RestController
@AllArgsConstructor
@RequestMapping("/profile/dashboard/lessons")
public class LessonController {

	private LessonService lessonService;
	private SubjectService subjectService;
	private AuditoriumService auditoriumService;
	private HouseService houseService;
	private ProfessorService professorService;
	private YearService yearService;

	@GetMapping
	public ModelAndView pagination(Authentication authentication,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "selectedDate", required = false) String selectedDate) {

		ModelAndView mav = new ModelAndView();
		Page<LessonDto> pageLessons = lessonService.getLessonsByFilters(selectedDate, page);
		
		mav.addObject("selectedDate", selectedDate);
		mav.addObject("pageLessons", pageLessons);
		mav.addObject("numbers", IntStream.range(1, pageLessons.getTotalPages()).toArray());
		mav.setViewName("profile/entities/lessons");
		return mav;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
		try {
			lessonService.delete(id);
			return new ResponseEntity<>("Lesson deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete lesson : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditView(@PathVariable Long id) {

		ModelAndView mav = new ModelAndView();
		return lessonService.getById(id).map(lesson -> {
			mav.addObject("subjects", subjectService.getAll());
			mav.addObject("professors", professorService.getAll());
			mav.addObject("auditoriums", auditoriumService.getAll());
			mav.addObject("lesson", lesson);
			mav.addObject("years", yearService.getAll());
			mav.addObject("houses", houseService.getAll());
			mav.setViewName("profile/entities/edit/lessonEdit");
			return mav;
		}).orElseGet(() -> {
			mav.setViewName("redirect:/profile/dashboard/lessons");
			return mav;
		});
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Object> update(@RequestBody Lesson lesson, @PathVariable Long id) {
		try {
			Lesson updatedLesson = lessonService.save(lessonService.lessonBuilder(lesson, id));
			return ResponseEntity.ok(updatedLesson);

		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/create")
	public ModelAndView showCreateView() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("subjects", subjectService.getAll());
		mav.addObject("professors", professorService.getAll());
		mav.addObject("auditoriums", auditoriumService.getAll());
		mav.addObject("years", yearService.getAll());
		mav.addObject("houses", houseService.getAll());
		mav.setViewName("profile/entities/create/lessonCreate");
		return mav;
	}

	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody Lesson lesson) {

		try {
			Lesson createdLesson = lessonService.save(lessonService.lessonBuilder(lesson, lesson.getId()));
			return ResponseEntity.ok(createdLesson);

		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
