package com.ua.passlocker.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ua.passlocker.auth.models.UserDetailsImp;
import com.ua.passlocker.auth.service.UserSecurityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String SECRET;

    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("Authorization");

        if (!StringUtils.isEmpty(token)) {
            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC512(this.SECRET.getBytes())).build();
                DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
                Claim sub = jwt.getClaim("emailId");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(sub.asString(), null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException ex) {
                log.error("Exception authentication", ex);
            } catch (Exception ex) {
                log.error("Exception in security check", ex);
            }
        }

        chain.doFilter(httpServletRequest, httpServletResponse);
    }


}
