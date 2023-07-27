package com.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     *
     *
     */

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Authorization
        String requestHeader = request.getHeader("Authorization");
        log.info("Header:{}", requestHeader);

        String userName = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            //
            token = requestHeader.substring(7);
            try {
                userName = this.jwtHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {
                log.info("Illegal argument while fetching username..");
                e.printStackTrace();

            } catch (ExpiredJwtException e) {
                log.info("given Jwt Token is Expired.");
                e.printStackTrace();

            } catch (MalformedJwtException e) {
                log.info("Some Changed has done in token Invalid token ..");
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            log.info("Invalid Header value..!!");
        }
        if (userName!=null  && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            boolean validateToken = jwtHelper.validateToken(token, userDetails);
            if (validateToken){

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                log.info("Validation Fails..!!");
            }
        }
    }
}
