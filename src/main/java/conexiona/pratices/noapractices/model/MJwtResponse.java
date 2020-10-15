package conexiona.pratices.noapractices.model;

import java.io.Serializable;

//RESPUESTA A UNA PETICIÓN: RECOGE EL TOKEN
public class MJwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    public MJwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

}
