package smart.gestion.des.equipments.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.gestion.des.equipments.admin.dtos.StatisticsDTO;
import smart.gestion.des.equipments.admin.services.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public StatisticsDTO getStatistics() {
        return statisticsService.getStatistics();
    }
}
