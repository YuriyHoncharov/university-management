package ua.com.foxminded.yuriy.schedulewebapp.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.yuriy.schedulewebapp.entity.Role;
import ua.com.foxminded.yuriy.schedulewebapp.repository.RoleRepository;
import ua.com.foxminded.yuriy.schedulewebapp.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> getRoleById(Long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void deleteRole(Long id) {
		roleRepository.deleteById(id);
	}
}