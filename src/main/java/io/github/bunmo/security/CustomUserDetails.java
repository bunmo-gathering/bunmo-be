package io.github.bunmo.security;

import io.github.bunmo.member.infrastructure.domain.Member;
import io.github.bunmo.member.infrastructure.domain.enums.RoleType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private String email;
    private RoleType role;

    private CustomUserDetails(String email, RoleType role) {
        this.email = email;
        this.role = role;
    }

    public static CustomUserDetails from(Member member) {
        return new CustomUserDetails(member.email(), member.role());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }
}
