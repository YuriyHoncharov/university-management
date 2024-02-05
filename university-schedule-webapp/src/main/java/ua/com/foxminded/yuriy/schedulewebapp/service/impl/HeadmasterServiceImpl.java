package ua.com.foxminded.yuriy.schedulewebapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.foxminded.yuriy.schedulewebapp.entity.Headmaster;
import ua.com.foxminded.yuriy.schedulewebapp.repository.HeadmasterRepository;
import ua.com.foxminded.yuriy.schedulewebapp.service.HeadmasterService;

@Service
public class HeadmasterServiceImpl implements HeadmasterService {

	private final HeadmasterRepository headmasterRepository;

	@Autowired
	public HeadmasterServiceImpl(HeadmasterRepository headmasterRepository) {
		this.headmasterRepository = headmasterRepository;
	}

	@Override
	public List<Headmaster> getAll() {
		return headmasterRepository.findAll();
	}

	@Override
	public Optional<Headmaster> getById(Long id) {
		return headmasterRepository.findById(id);
	}

	@Override
	public Headmaster save(Headmaster headmaster) {
		return headmasterRepository.save(headmaster);
	}

	@Override
	public void delete(Long id) {
		headmasterRepository.deleteById(id);
	}
}
