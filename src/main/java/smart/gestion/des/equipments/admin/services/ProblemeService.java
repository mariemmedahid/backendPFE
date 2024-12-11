package smart.gestion.des.equipments.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smart.gestion.des.equipments.admin.dtos.ProblemeDTO;
import smart.gestion.des.equipments.dao.entities.Probleme;
import smart.gestion.des.equipments.dao.repositories.ProblemeRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProblemeService {

    @Autowired
    private ProblemeRepository problemeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProblemeDTO createProbleme(ProblemeDTO problemeDTO) {
        Probleme probleme = modelMapper.map(problemeDTO, Probleme.class);
        probleme = problemeRepository.save(probleme);
        return modelMapper.map(probleme, ProblemeDTO.class);
    }

    public List<ProblemeDTO> getAllProblemes() {
        return problemeRepository.findAll()
                .stream()
                .map(probleme -> modelMapper.map(probleme, ProblemeDTO.class))
                .collect(Collectors.toList());
    }

    public ProblemeDTO getProblemeById(Long id) {
        return problemeRepository.findById(id)
                .map(probleme -> modelMapper.map(probleme, ProblemeDTO.class))
                .orElse(null);
    }

    public ProblemeDTO updateProbleme(Long id, ProblemeDTO problemeDTO) {
        Optional<Probleme> optionalProbleme = problemeRepository.findById(id);
        if (optionalProbleme.isPresent()) {
            Probleme probleme = optionalProbleme.get();
            probleme.setDescription(problemeDTO.getDescription());
            probleme = problemeRepository.save(probleme);
            return modelMapper.map(probleme, ProblemeDTO.class);
        }
        return null;
    }

    public void deleteProbleme(Long id) {
        problemeRepository.deleteById(id);
    }
}
