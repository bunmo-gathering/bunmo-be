package io.github.bunmo.security.annotation;

import io.github.bunmo.security.CustomUserDetails;
import io.github.bunmo.security.exception.AuthErrorCode;
import io.github.bunmo.security.exception.AuthException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean hasUserType = CustomUserDetails.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginUserAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null){
            throw new AuthException(AuthErrorCode.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return principal;
        }

        if (!(principal instanceof String)) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED);
        }

        return principal;
    }
}
