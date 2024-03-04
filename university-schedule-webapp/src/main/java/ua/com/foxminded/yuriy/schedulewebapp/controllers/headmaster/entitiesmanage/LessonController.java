package ua.com.foxminded.yuriy.schedulewebapp.controllers.headmaster.entitiesmanage;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import lombok.AllArgsConstructor;
import ua.com.foxminded.yuriy.schedulewebapp.entity.dto.LessonDto;
import ua.com.foxminded.yuriy.schedulewebapp.service.LessonService;

@RestController
@AllArgsConstructor
@RequestMapping("headmaster/dashboard/lessons")
public class LessonController {

	private LessonService lessonService;


	@GetMapping
	public ModelAndView pagination(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
		Page<LessonDto> pageLessons = lessonService.getAllByPage(PageRequest.of(page, 7));
		ModelAndView mav = new ModelAndView();
		mav.addObject("pageLessons", pageLessons);
		mav.addObject("numbers", IntStream.range(1, pageLessons.getTotalPages()).toArray());
		mav.setViewName("headmaster/entities/lessons");
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

	@GetMapping("/search")
	public ModelAndView getLessonsByDate(
	    @RequestParam(name = "selectedDate", required = false) String selectedDate,
	    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {

	    if (selectedDate != null) {
	        // Search by date
	        Page<LessonDto> lessonPageByDate = lessonService.getAllByDate(selectedDate, PageRequest.of(page, 7));
	        ModelAndView mav = new ModelAndView();
	        mav.addObject("pageLessons", lessonPageByDate);
	        mav.addObject("numbers", IntStream.range(1, lessonPageByDate.getTotalPages()).toArray());
	        mav.setViewName("headmaster/entities/lessons");
	        return mav;
	    } else {	        
	        return pagination(page);
	    }
	}
}
