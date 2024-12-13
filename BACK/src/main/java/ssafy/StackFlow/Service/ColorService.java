package ssafy.StackFlow.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.StackFlow.Domain.Color;
import ssafy.StackFlow.Repository.ColorRepository;


import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

}
