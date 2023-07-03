package edu.scut.tinymail.service.impl;

import edu.scut.tinymail.domain.entity.Userauth;
import edu.scut.tinymail.service.UserauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {

    private final UserauthService systemUserService;

    /**
     * 当前用户
     *
     * @return 当前用户
     */
    public Userauth currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return systemUserService.getByUsername(username);
    }

    /**
     * 根据用户名查询用户的认证授权信息
     *
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException 异常
     * @since 2022/12/6 15:03
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Userauth systemUser = systemUserService.getByUsername(username);
        if (systemUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        systemUser.setAuthorities(new ArrayList<>());
        return User.withUsername(systemUser.getUsername())
                .password(systemUser.getPassword())
                .authorities("user")
                .accountExpired(false)
                .disabled(false)
                .build()
                ;
    }

    /**
     * 修改密码
     *
     * @param user        用户
     * @param newPassword 新密码
     * @return UserDetails
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Userauth systemUser = systemUserService.getByUsername(user.getUsername());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        systemUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        systemUserService.updateById(systemUser);
        return User.withUsername(systemUser.getUsername())
                .password(systemUser.getPassword())
                .authorities("user")
                .accountExpired(false)
                .disabled(false)
                .build()
                ;
    }
}
