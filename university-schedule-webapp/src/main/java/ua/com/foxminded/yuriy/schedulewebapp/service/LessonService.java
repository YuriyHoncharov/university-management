package ua.com.foxminded.yuriy.schedulewebapp.service;

import java.util.List;
import java.util.Optional;
import ua.com.foxminded.yuriy.schedulewebapp.entity.Lesson;

public interface LessonService {
	
	List<Lesson> getAll();

	Optional<Lesson> getById(Long id);

	Lesson save(Lesson lesson);

	void delete(Long id);
	
	List<Lesson> getByWizardId(Long wizardId);
	
	List<Lesson>getByWizardIdAndDayOfWeek(Long wizardId, int selectedDay);
}
