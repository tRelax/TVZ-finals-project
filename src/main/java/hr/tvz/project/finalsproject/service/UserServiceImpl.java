package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(ConvertorsDTO::mapUserToDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name).stream().map(ConvertorsDTO::mapUserToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(ConvertorsDTO::mapUserToDTO);
    }

    @Override
    public UserDTO save(User user, boolean save) {
        if(save){
            List<UserDTO> listOfAllUsers = findAll();
            UserDTO tempUser = listOfAllUsers.stream().max(Comparator.comparing(UserDTO::getId)).get();
            if (user.getId() <= tempUser.getId())
                user.setId(tempUser.getId() + 1);
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
