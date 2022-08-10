package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.DTO.UserDTO;
import hr.tvz.project.finalsproject.convertorsDTO.ConvertorsDTO;
import hr.tvz.project.finalsproject.entity.Team;
import hr.tvz.project.finalsproject.entity.Ticket;
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
    public UserDTO save(User user) {
        List<UserDTO> listOfAllUsers = findAll();
        if(!listOfAllUsers.isEmpty()){
            UserDTO tempUser = listOfAllUsers.stream().max(Comparator.comparing(UserDTO::getId)).get();
            if (user.getId() <= tempUser.getId())
                user.setId(tempUser.getId() + 1);
        }
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
    public UserDTO updateUserTeams(User user, Team team) {
        Optional<User> tempUser = findByIdRaw(user.getId());
        if(tempUser.isPresent()){
            user.setPassword(tempUser.get().getPassword());
            user.setTicketList(tempUser.get().getTicketList());
            user.setTicketListTester(tempUser.get().getTicketListTester());
            List<Team> tList = tempUser.get().getTeamList();
            if (tList.stream().noneMatch(t->t.getId().equals(team.getId()))){
                tempUser.get().getTeamList().add(team);
                user.setTeamList(tempUser.get().getTeamList());
            }
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUserTeamsRemove(User user, Team team) {
        Optional<User> tempUser = findByIdRaw(user.getId());
        if(tempUser.isPresent()){
            user.setPassword(tempUser.get().getPassword());
            user.setTicketList(tempUser.get().getTicketList());
            user.setTicketListTester(tempUser.get().getTicketListTester());
            List<Team> tList = tempUser.get().getTeamList();
            if (tList.stream().anyMatch(t->t.getId().equals(team.getId()))){
                tempUser.get().getTeamList().remove(team);
                user.setTeamList(tempUser.get().getTeamList());
            }
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateAssigneeTicketListAdd(User userAdd, Ticket ticket) {
        Optional<User> tempUserAdd = findByIdRaw(userAdd.getId());
        if(tempUserAdd.isPresent()){
            List<Ticket> tList = tempUserAdd.get().getTicketList();
            if (tList.stream().noneMatch(t->t.getId().equals(ticket.getId()))){
                tempUserAdd.get().getTicketList().add(ticket);
                userAdd.setTicketList(tempUserAdd.get().getTicketList());
            }
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(userAdd));
    }

    @Override
    public UserDTO updateAssigneeTicketListRemove(User userRemove, Ticket ticket) {
        Optional<User> tempUserRemove = findByIdRaw(userRemove.getId());
        if(tempUserRemove.isPresent()){
            List<Ticket> tList = tempUserRemove.get().getTicketList();
            if (tList.stream().anyMatch(t->t.getId().equals(ticket.getId()))){
                tempUserRemove.get().getTicketList().remove(ticket);
                userRemove.setTicketList(tempUserRemove.get().getTicketList());
            }
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(userRemove));
    }

    @Override
    public UserDTO updateTesterTicketListAdd(User userAdd, Ticket ticket) {
        Optional<User> tempUserAdd = findByIdRaw(userAdd.getId());
        if(tempUserAdd.isPresent()){
            List<Ticket> tList = tempUserAdd.get().getTicketListTester();
            if (tList.stream().noneMatch(t->t.getId().equals(ticket.getId()))){
                tempUserAdd.get().getTicketListTester().add(ticket);
                userAdd.setTicketListTester(tempUserAdd.get().getTicketListTester());
            }
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(userAdd));
    }

    @Override
    public UserDTO updateTesterTicketListRemove(User userRemove, Ticket ticket) {
        Optional<User> tempUserAdd = findByIdRaw(userRemove.getId());
        if(tempUserAdd.isPresent()){
            List<Ticket> tList = tempUserAdd.get().getTicketListTester();
            if (tList.stream().anyMatch(t->t.getId().equals(ticket.getId()))){
                tempUserAdd.get().getTicketListTester().remove(ticket);
                userRemove.setTicketListTester(tempUserAdd.get().getTicketListTester());
            }
        }
        return ConvertorsDTO.mapUserToDTO(userRepository.save(userRemove));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
