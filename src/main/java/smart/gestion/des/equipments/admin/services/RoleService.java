
package smart.gestion.des.equipments.admin.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.RoleDTO;
import smart.gestion.des.equipments.dao.entities.Role;
import smart.gestion.des.equipments.dao.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        role = roleRepository.save(role);
        return modelMapper.map(role, RoleDTO.class);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .orElse(null);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setNom(roleDTO.getNom());
                    roleRepository.save(existingRole);
                    return modelMapper.map(existingRole, RoleDTO.class);
                })
                .orElse(null);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
