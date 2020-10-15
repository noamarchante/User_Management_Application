package conexiona.pratices.noapractices.controller;

import conexiona.pratices.noapractices.configuration.JwtTokenUtil;
import conexiona.pratices.noapractices.model.MJwtRequest;
import conexiona.pratices.noapractices.model.MJwtResponse;
import conexiona.pratices.noapractices.model.MUser;
import conexiona.pratices.noapractices.service.SUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;

@RestController
@RequestMapping
@CrossOrigin
public class CJwtAuthentication {

    @Autowired
    private CUser cUser;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SUser sUser;


    @RequestMapping("/login")
    public ModelAndView getUserForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    //PETICIÓN QUE ATIENDE A LA AUTENTICACIÓN DE UN USUARIO YA REGISTRADO
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid MJwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassword());
        final UserDetails userDetails = sUser.loadUserByUsername(authenticationRequest.getUserName());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new MJwtResponse(token));
    }

    private void authenticate( String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    //PETICIÓN QUE ATIENDE AL REGISTRO DE UN USUARIO
    @PostMapping("/register")
    public ResponseEntity<?> register ( @RequestBody @Valid MUser mUser, UriComponentsBuilder builder) throws Exception {
       return cUser.addUser(mUser, builder);
    }
}
