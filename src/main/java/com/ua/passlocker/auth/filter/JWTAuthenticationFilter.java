package com.ua.passlocker.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.passlocker.auth.models.UserDetailsImp;
import com.ua.passlocker.auth.models.dto.TokenResp;
import com.ua.passlocker.auth.models.dto.UserReq;
import com.ua.passlocker.auth.security.JWTSecurityProvider;
import com.ua.passlocker.auth.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.ua.passlocker.auth.utils.Utils.formatedSecret;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JWTSecurityProvider jwtSecurityProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String ENCRYPT_ATTRIBUTE_NAME = "encrypted_pass";

    @Autowired
    @Qualifier("authenticationManagerBean")
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserReq userReq = objectMapper.readValue(request.getReader(), UserReq.class);
            UsernamePasswordAuthenticationToken userPassToken = new UsernamePasswordAuthenticationToken(userReq.getEmailId(), userReq.getPassword());

            request.setAttribute(ENCRYPT_ATTRIBUTE_NAME, SecurityUtils.encodeWithBase64(formatedSecret(userReq.getEmailId(), userReq.getPassword())));
            return authenticationManager.authenticate(userPassToken);
        } catch (IOException e) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImp userReq = (UserDetailsImp) authResult.getPrincipal();
        String hashKey = SecurityUtils.generateMasterToken((String) request.getAttribute(ENCRYPT_ATTRIBUTE_NAME), userReq.getClientSecret());
        String tokenResp = objectMapper.writeValueAsString(new TokenResp(jwtSecurityProvider.doGenerateToken(userReq.getUsername(), userReq.getEmailId()), hashKey));
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(tokenResp);
        writer.flush();
        writer.close();
    }
}
