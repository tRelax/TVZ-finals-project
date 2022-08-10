package hr.tvz.project.finalsproject.service;

import hr.tvz.project.finalsproject.entity.User;

public interface JwtService {

    boolean authenticate(String token);

    String createJwt(User user);
}
