package smart.gestion.des.equipments.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import smart.gestion.des.equipments.admin.dtos.StatisticsDTO;
import smart.gestion.des.equipments.dao.repositories.EquipmentRepository;
import smart.gestion.des.equipments.dao.repositories.UtilisateurRepository;

@Service
public class StatisticsService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UtilisateurRepository employeeRepository;

    public StatisticsDTO getStatistics() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setTotalEquipments(equipmentRepository.count());
        statisticsDTO.setTotalEmployees(employeeRepository.count());
        statisticsDTO.setAvailableEquipments(equipmentRepository.countByStatus("Online"));
        statisticsDTO.setUnavailableEquipments(equipmentRepository.countByStatus("Offline"));
        return statisticsDTO;
    }
}
