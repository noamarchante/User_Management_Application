/*Clase que define la configuración básica deseguridad */
//@Configuration: identifica esta clase como parte de la configuración
//@EnableWebSecurity: permite activar el módulo de seguridad
//@Override: indica que es un método sobreescrito

/*AUTHENTIFICATION AND AUTHORIZATION*/
/* AUTHENTIFICATION: who you are in the context of an application (HTTP, FORMS, CERTIFICATE, TOKENS)
   AUTHORIZATION: what you are allowed to do in the context of an application (PRIVILEGES/AUTHORITIES, ROLES)
*/

/*HTTP BASIC AUTHENTICATION*/
/*El proceso del navegador de pedir un usuario y una contraseña cuando realiza una petición para autentificar a un usuario
Es un sistema simple que transmite las credenciales sin encriptar. Se codifican en el sistema BASE64 en el tránsito.
Por lo tanto, es necesario complementarlo con SSL (HTTPS) para proporcionar confidencialidad
HTTP + SSL proporciona la seguridad mínima para aplicaciones que trabajan con información no sensibles
No proporciona log out
    CLIENT                              SERVER
       |                                   |
       | --------- GET/HOME ----------->   |
       | <--- 401 UNAUTHORIZED ---------   |
       | -- GET/HOME AUTHORIZATION ---->   |
       | <--------- 200 OK -------------   |
 */

/*HTTPS*/
/*Proporciona seguridad en la información del usuario entre los endpoints.
HTTP por si solo no es seguro porque solo codifica
HTTP + SSL es seguro porque encripta
Los certificados SSL pueden ser:
    - SELF SIGNED: utiles durante el desarrollo pero no proporcionan confianza al usuario final
    - SIGNED BY TRUSTED AUTHORITY (Comodo, Symantic, DigiCert,...): Son firmas de entidades de confianza, resulta util durante la producción

    CLIENT                                                              SERVER
       | --------------------- HTTPS REQUEST -------------------------->   |
       | <----------- SERVER SENDS CERTIFICATE WITH PUBLIC KEY --------    |
       | ----- SSL VERIFICATION IF OK BROWSER SENDS BACK SESSION KEY -->   |
*/

/*FORM BASED AUTHENTICATION*/
/*
It is the process of authenticating a user by presenting a custom HTML page that will collect credentials and by directing the authentication
responsibility to the web application that collects the form data.

LOGIN
------

CLIENT                                                          SERVER (SESSION ID)
   |   ---------------------- GET/HOME ----------------------->         |
   |   <----- IF NOT AUTHENTICATED REDIRECT TO LOGIN PAGE -----         |
   |   -------- POST FORM DATA (USERNAME + PASSWORD) --------->         |
   |   <-- IF OK CREATE SESSION ID AND RETURN AUTH COOKIE -----         |
   |   ------------- GET/REPORTS ALL (COOKIE) ---------------->         |
   |   <--------------------- 200 OK --------------------------         |


LOGOUT
------

CLIENT                                                          SERVER (NO SESSION ID)
   |   ------------ GET/LOGOUT OR EXPIRES (INACTIVITY) ------->         |
   |   <----- SESSION IS INVALIDATED REDIRECT FOLLOGIN --------         |


This application is responsible for dealing with form data and performing the actual authorization pathern.
It is the most widespread form of authentication, well suited for self- contained apps.
The user credentials are conveyed in the clear to the web application, so use SSL to keep them safe transit.
 */

/*JWT*/
/*
jwt authentication -> compact and safe way to transmit data between two parties. The information can be trusted because it is digitally signed.
jwt structure -> header(alg,typ) + payload(data) + signature (secret)

LOGIN
-----

CLIENT(OUTSIDE YOUR APP DOMAIN)                                                         AUTH SERVER
                                ---- USER SIGN IN (CRENTIALS, FB, GOOGLE....) ------>
                                <--- AUTHENTICATED TOKEN JWT CREATED AND RETURNED --
                                                                                        APPLICATION (REST)
                                --- GET/REPORTS/ALL HEADER AUTHORIZATION BEARER ---->
                                <---------------- 200 OK ----------------------------
                                ------ POST/PRODUCT HEADER AUTHORIZATION BEARER ---->
                                <--------------- 200 OK -----------------------------



*/

package conexiona.pratices.noapractices.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    @Qualifier("SUser")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    //CONFIGURE GLOBAL: CONFIGURA EL AUTHENTICATION MANAGER BUSCANDO LA INFORMACIÓN DE LAS CREDENCIALES Y LA CONTRASEÑA CODIFICADA DEL USUARIO
    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //BEAN IMPLEMENTADO PARA LA AUTENTICACIÓN
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //CONFIGURE: CONFIGURA LAS URL QUE TIENEN ACCESO SIN AUTHENTICACIÓN Y LOS PERMISOS Y ROLES ASIGNADOS A UNA URL
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //PARTIENDO DE UN USUARIO ADMINISTRADOR
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate", "/register", "/login", "/v1/userAdd", "/v1/userUpdate", "/v1/userDelete", "/v1/user").permitAll()
                //.antMatchers("/v1/accountAdd", "/v1/accountUpdate", "/v1/accountDelete", "/v1/userGroupAdd", "/v1/userGroupUpdate", "/v1/userGroupDelete", "/v1/userAdd", "/v1/userUpdate", "/v1/userDelete", "/v1/userGroupUserAdd", "/v1/userGroupUserUpdate", "/v1/userGroupUserDelete").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .rememberMe().tokenValiditySeconds(5*60*60).key("${jwt.secret}").alwaysRemember(true)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        httpSecurity
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //CODIFICACIÓN DE LA CONTRASEÑA
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}