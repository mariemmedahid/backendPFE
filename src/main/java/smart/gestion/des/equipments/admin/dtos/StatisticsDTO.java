package smart.gestion.des.equipments.admin.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsDTO {
    private long totalEquipments;
    private long totalEmployees;
    private long availableEquipments;
    private long unavailableEquipments;
}
