package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Authority;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
import hr.tvz.project.finalsproject.entity.User;
import hr.tvz.project.finalsproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
    public List<UserDTO> findByTeamId(Long id) {
        return userRepository.findByTeamListId(id).stream().map(ConvertorsDTO::mapUserToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(ConvertorsDTO::mapUserToDTO);
    }

    @Override
    public Optional<User> findByIdRaw(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserDTO> findAssigneeByTicketId(Long id) {
        return userRepository.findByTicketListId(id).map(ConvertorsDTO::mapUserToDTO);
    }

    @Override
    public Optional<UserDTO> findTesterByTicketId(Long id) {
        return userRepository.findByTicketListTesterId(id).map(ConvertorsDTO::mapUserToDTO);
    }

    @Override
    public UserDTO save(User user, String password) {
        List<UserDTO> listOfAllUsers = findAll();
        if(!listOfAllUsers.isEmpty()){
            UserDTO tempUser = listOfAllUsers.stream().max(Comparator.comparing(UserDTO::getId)).get();
            if (user.getId() <= tempUser.getId())
                user.setId(tempUser.getId() + 1);
        }
        Authority authority = new Authority(1L, "ROLE_USER");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        user.setAuthorities(authoritySet);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(password);
        user.setPassword(passwordEncoded);
        user.setTicketList(Collections.<Ticket>emptyList());
        user.setTicketListTester(Collections.<Ticket>emptyList());
        user.setTeamList(Collections.<Team>emptyList());
        return ConvertorsDTO.mapUserToDTO(userRepository.save(user));
    }

    @Override
    public User update(User user) {
        Optional<User> tempUser = findByIdRaw(user.getId());
        if(tempUser.isPresent()){
            user.setPassword(tempUser.get().getPassword());
            user.setTeamList(tempUser.get().getTeamList());
            user.setTicketList(tempUser.get().getTicketList());
            user.setTicketListTester(tempUser.get().getTicketListTester());
            user.setAuthorities(tempUser.get().getAuthorities());
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
