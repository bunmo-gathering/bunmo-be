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
    private Long id;
    private RoleType role;

    private CustomUserDetails(Long id, RoleType role) {
        this.id = id;
        this.role = role;
    }

    public static CustomUserDetails from(Member member) {
        return new CustomUserDetails(member.getId(), member.role());
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
        return id.toString();
    }
}
