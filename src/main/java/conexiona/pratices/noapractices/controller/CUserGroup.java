//Se definen las clases encargadas de ejecutar las acciones correspondientes a las peticiones http recibidas desde el exterior y se devuelve la respuesta correspondiente en formato JSON

/*ANOTACIONES UTILIZADAS EN LA CLASE*/
//@RestController: se encarga de mapear los datos de las peticiones al método definido y convirtiendolo en respuestas JSON o XML (@Controller + @ResponseBody)
//RequestMapping: se utiliza para asignar las solicitudes web a los métodos del controlador
//@Autowired: permite la inyección de propiedades
//@Qualifier: permite establecer un nombre
//@PutMapping: acepta solicitudes http de tipo put que contienen un cuerpo de petición de tipo JSON o XML permitiendo actualizar datos de usuario
//@RequestBody: indica que un parámetro del método debe estar vinculado al valor del cuerpo de la petición http
//@Valid: comprueba si la información que se le está pasando a lmétodo es correcta o no

/*MÉTODOS DE PETICIÓN HTTP*/
//PUT: (update) reemplaza todas las representaciones actuales del recurso de destino con la carga útil de la petición
//POST: (insert) se utiliza para enviar una entidad a un recurso en específico, causando a menudo un cambio en el estado o efectos secundarios en el servidor.
//GET: (select) solicita una representación de un recurso específico. Las peticiones que usan el método GET sólo deben recuperar datos.
//DELETE: (delete) borra un recurso en específico.
//-----------------------------------------------------------------------------------------------------------
//PATCH: es utilizado para aplicar modificaciones parciales a un recurso.
//HEAD: pide una respuesta idéntica a la de una petición GET, pero sin el cuerpo de la respuesta.
//CONNECT: establece un túnel hacia el servidor identificado por el recurso.
//OPTIONS: es utilizado para describir las opciones de comunicación para el recurso de destino.
//TRACE: realiza una prueba de bucle de retorno de mensaje a lo largo de la ruta al recurso de destino.

/*CÓDIGOS DE ERROR HTTP (HttpStatus)*/
//100s: Códigos informativos que indican que la solicitud iniciada por el navegador continúa.
//CONTINUE: 100 Continue -> 100: «Continuar». Esto significa que el servidor en cuestión ha recibido las cabeceras de solicitud de tu navegador, y ahora está listo para que el cuerpo de la solicitud sea enviado también. Esto hace que el proceso de solicitud sea más eficiente ya que evita que el navegador envíe una solicitud de cuerpo aunque los encabezados hayan sido rechazados.
//SWITCHING_PROTOCOLS: 101 Switching Protocols -> 101: «Cambiando protocolos». Tu navegador ha pedido al servidor que cambie los protocolos, y el servidor ha cumplido.
//PROCESSING: 102 Processing
//CHECKPOINT: 103 Checkpoint -> 103: «Primeros avisos». Esto devuelve algunos encabezados de respuesta antes de que el resto de la respuesta del servidor esté lista.

//200s: Los códigos con éxito regresaron cuando la solicitud del navegador fue recibida, entendida y procesada por el servidor.
//OK: 200 OK -> 200: «Está bien». Este es el código que se entrega cuando una página web o recurso actúa exactamente como se espera.
//CREATED: 201 Created -> 201: «Creado». El servidor ha cumplido con la petición del navegador y, como resultado, ha creado un nuevo recurso.
//ACCEPTED: 202 Accepted -> 202: «Aceptado». El servidor ha aceptado la solicitud de tu navegador pero aún la está procesando. La solicitud puede, en última instancia, dar lugar o no a una respuesta completa.
//NON_AUTHORITATIVE_INFORMATION: 203 Non-Authoritative Information -> 203: «Información no autorizada». Este código de estado puede aparecer cuando se utiliza un apoderado. Significa que el servidor proxy recibió un código de estado de 200 del servidor de origen, pero ha modificado la respuesta antes de pasarla a su navegador.
//NO_CONTENT: 204 No Content -> 204: «Sin contenido». Este código significa que el servidor ha procesado con éxito la solicitud, pero no va a devolver ningún contenido.
//RESET_CONTENT: 205 Reset Content -> 205: «Restablecer el contenido». Como un código 204, esto significa que el servidor ha procesado la solicitud pero no va a devolver ningún contenido. Sin embargo, también requiere que tu navegador restablezca la vista del documento.
//PARTIAL_CONTENT: 206 Partial Content -> 206: «Contenido parcial». Puedes ver este código de estado si tu cliente HTTP (también conocido como tu navegador) usa «cabeceras de rango». Esto permite a tu navegador reanudar las descargas en pausa, así como dividir una descarga en múltiples flujos. Se envía un código 206 cuando un encabezado de rango hace que el servidor envíe sólo una parte del recurso solicitado.
//MULTI_STATUS: 207 Multi-Status
//ALREADY_REPORTED: 208 Already Reported
//IM_USED: 226 IM Used

//300s: Códigos de redireccionamiento devueltos cuando un nuevo recurso ha sido sustituido por el recurso solicitado.
//MULTIPLE_CHOICES: 300 Multiple Choices -> 300: «Opciones Múltiples». A veces, puede haber múltiples recursos posibles con los que el servidor puede responder para cumplir con la solicitud de su navegador. Un código de estado 300 significa que tu navegador ahora tiene que elegir entre ellos. Esto puede ocurrir cuando hay múltiples extensiones de tipo de archivo disponibles, o si el servidor está experimentando desambiguación del sentido de las palabras.
//MOVED_PERMANENTLY: 301 Moved Permanently -> 301: «El recurso solicitado ha sido trasladado permanentemente». Este código se entrega cuando una página web o un recurso ha sido reemplazado permanentemente por un recurso diferente. Se utiliza para la redirección permanente del URL.
//FOUND: 302 Found -> 302: «El recurso solicitado se ha movido, pero fue encontrado». Este código se utiliza para indicar que el recurso solicitado se encontró, pero no en el lugar donde se esperaba. Se utiliza para la redirección temporal de la URL.
//SEE_OTHER: 303 See Other -> 303: «Ver otros». Para entender un código de estado 303 es necesario conocer la diferencia entre los cuatro métodos de solicitud HTTP principales. Esencialmente, un código 303 le dice a tu navegador que encontró el recurso que el navegador solicitó vía POST, PUT o DELETE. Sin embargo, para recuperarlo usando GET, necesita hacer la solicitud apropiada a un URL diferente al que usó anteriormente.
//NOT_MODIFIED: 304 Not Modified -> 304: «El recurso solicitado no ha sido modificado desde la última vez que accedió a él». Este código le dice al navegador que los recursos almacenados en la caché del navegador no han cambiado. Se usa para acelerar la entrega de páginas web reutilizando los recursos descargados previamente.
//TEMPORARY_REDIRECT: 307 Temporary Redirect -> 307: «Redireccionamiento temporal». Este código de estado ha reemplazado a 302 «Encontrado» como la acción apropiada cuando un recurso ha sido movido temporalmente a una URL diferente. A diferencia del código de estado 302, no permite que el método HTTP cambie.
//PERMANENT_REDIRECT: 308 Permanent Redirect -> 308: «Redireccionamiento permanente». El código de estado 308 es el sucesor del código 301 «Movido permanentemente». No permite que el método HTTP cambie e indica que el recurso solicitado está ahora localizado permanentemente en una nueva URL.

//400s: Códigos de error del cliente que indican que hubo un problema con la solicitud.
//BAD_REQUEST: 400 Bad Request -> 400: «Mala petición». El servidor no puede devolver una respuesta debido a un error del cliente. Vea nuestra guía para resolver este error.
//UNAUTHORIZED: 401 Unauthorized -> 401: «No autorizado» o «Se requiere autorización». Esto es devuelto por el servidor cuando el recurso de destino carece de credenciales de autenticación válidas. Podrías ver esto si has configurado la autenticación básica de HTTP usando htpasswd.
//PAYMENT_REQUIRED: 402 Payment Required -> 402: «Pago requerido». Originalmente, este código fue creado para ser usado como parte de un sistema de dinero digital. Sin embargo, ese plan nunca se llevó a cabo. En cambio, es utilizado por diversas plataformas para indicar que una solicitud no se puede cumplir, por lo general debido a la falta de los fondos necesarios. Los casos más comunes incluyen:
//Has alcanzado el límite de solicitudes diarias al API de los desarrolladores de Google.
//No ha pagado tus honorarios de Shopify y su tienda ha sido desactivada temporalmente.
//Tu pago a través de Stripe ha fallado, o Stripe está tratando de evitar un pago fraudulento.
//FORBIDDEN: 403 Forbidden -> 403: «El acceso a ese recurso está prohibido». Este código se devuelve cuando un usuario intenta acceder a algo a que no tiene permiso para ver. Por ejemplo, intentar acceder a un contenido protegido por contraseña sin registrarse podría producir un error 403.
//NOT_FOUND: 404 Not Found -> 404: «No se encontró el recurso solicitado». Este es el mensaje de error más común de todos ellos. Este código significa que el recurso solicitado no existe, y el servidor no sabe si alguna vez existió.
//METHOD_NOT_ALLOWED: 405 Method Not Allowed -> 405: «Método no permitido». Esto se genera cuando el servidor de alojamiento (servidor de origen) soporta el método recibido, pero el recurso de destino no lo hace.
//NOT_ACCEPTABLE: 406 Not Acceptable -> 406: «Respuesta no aceptable». El recurso solicitado es capaz de generar sólo contenido que no es aceptable según los encabezamientos de aceptación enviados en la solicitud.
//PROXY_AUTHENTICATION_REQUIRED: 407 Proxy Authentication Required -> 407: «Se requiere autenticación de proxy». Se está utilizando un servidor proxy que requiere que el navegador se autentifique antes de continuar.
//REQUEST_TIMEOUT: 408 Request Timeout -> 408: «El servidor se agotó esperando el resto de la petición del navegador». Este código se genera cuando un servidor se apaga mientras espera la solicitud completa del navegador. En otras palabras, el servidor no recibió la solicitud completa que fue enviada por el navegador. Una posible causa podría ser la saturación de la red, lo que provoca la pérdida de paquetes de datos entre el navegador y el servidor.
//CONFLICT: 409 Conflict -> 409: «Conflicto». Un código de estado 409 significa que el servidor no pudo procesar la solicitud de su navegador porque hay un conflicto con el recurso correspondiente. Esto ocurre a veces debido a múltiples ediciones simultáneas.
//GONE: 410 Gone -> 410: «El recurso solicitado se ha ido y no volverá». Esto es similar a un código 404 «No encontrado», excepto que un 410 indica que la condición es esperada y permanente.
//LENGTH_REQUIRED: 411 Length Required -> 411: «Longitud requerida». Esto significa que el recurso solicitado requiere que el cliente especifique una cierta longitud y que no lo hizo.
//PRECONDITION_FAILED: 412 Precondition failed -> 412: «La condición previa falló». Tu navegador incluyó ciertas condiciones en sus encabezados de solicitud, y el servidor no cumplió con esas especificaciones.
//PAYLOAD_TOO_LARGE: 413 Payload Too Large -> 413: «Carga útil demasiado grande» o «Entidad solicitante demasiado grande». Su solicitud es más grande de lo que el servidor está dispuesto o es capaz de procesar.
//URI_TOO_LONG: 414 URI Too Long -> 414: «URI demasiado largo». Esto suele ser el resultado de una solicitud GET que ha sido codificada como una cadena de consulta demasiado grande para que el servidor la procese.
//UNSUPPORTED_MEDIA_TYPE: 415 Unsupported Media Type -> 415: «Tipo de medios de comunicación sin apoyo». La solicitud incluye un tipo de medio que el servidor o recurso no soporta.
//REQUESTED_RANGE_NOT_SATISFIABLE: 416 Requested Range Not Satisfiable -> 416: «Rango no satisfactorio». Su solicitud fue por una porción de un recurso que el servidor no puede devolver.
//EXPECTATION_FAILED: 417 Expectation Failed -> 417: «La expectativa fracasó». El servidor no puede cumplir los requisitos especificados en el campo de cabecera de la solicitud.
//I_AM_A_TEAPOT: 418 I'm a teapot -> 418: «Soy una tetera». Este código es devuelto por las teteras que reciben solicitudes para preparar café. También es un chiste del «día de las bromas de abril» de 1988.
//UNPROCESSABLE_ENTITY: 422 Unprocessable Entity -> 422: «Entidad no procesable». La solicitud del cliente contiene errores semánticos, y el servidor no puede procesarla.
//LOCKED: 423 Locked
//FAILED_DEPENDENCY: 424 Failed Dependency
//TOO_EARLY: 425 Too Early -> 425: «Demasiado pronto». Este código se envía cuando el servidor no está dispuesto a procesar una solicitud porque puede ser reproducida.
//UPGRADE_REQUIRED: 426 Upgrade Required -> 426: «Se requiere actualización». Debido al contenido del campo de cabecera de la solicitud, el cliente debería cambiar a un protocolo diferente.
//PRECONDITION_REQUIRED: 428 Precondition Required -> 428: «Se requiere condición previa». El servidor requiere que se especifiquen las condiciones antes de procesar la solicitud.
//TOO_MANY_REQUESTS: 429 Too Many Requests -> 429: «Demasiadas peticiones». Esto es generado por el servidor cuando el usuario ha enviado demasiadas solicitudes en un determinado período de tiempo (con límite de velocidad). Esto puede ocurrir a veces debido a los bots o scripts que intentan acceder a tu sitio. En este caso, tal vez quieras intentar cambiar tu URL de acceso a WordPress. También puedes revisar nuestra guía para arreglar el error 429 «Demasiadas peticiones».
//REQUEST_HEADER_FIELDS_TOO_LARGE: 431 Request Header Fields Too Large -> 431: «Solicitud de campos de cabecera demasiado grandes». El servidor no puede procesar la solicitud porque los campos de la cabecera son demasiado grandes. Esto puede indicar un problema con un solo campo de encabezamiento, o con todos ellos colectivamente.
//UNAVAILABLE_FOR_LEGAL_REASONS: 451 Unavailable For Legal Reasons -> 451: «No disponible por razones legales». El operador del servidor ha recibido una demanda para prohibir el acceso al recurso que has solicitado (o a un conjunto de recursos, incluido el que has solicitado). Dato curioso: Este código es una referencia a la novela Fahrenheit 451 de Ray Bradbury.

//500s: Códigos de error del servidor que indican que la solicitud fue aceptada, pero que un error en el servidor impidió que se cumpliera.
//INTERNAL_SERVER_ERROR: 500 Internal Server Error -> 500: «Hubo un error en el servidor y la solicitud no pudo ser completada». Este es un código genérico que simplemente significa «error interno del servidor». Algo salió mal en el servidor y el recurso solicitado no fue entregado. Este código es típicamente generado por plugins de terceros, PHP defectuoso, o incluso la ruptura de la conexión a la base de datos. Revisa nuestros tutoriales sobre cómo corregir el error al establecer una conexión de base de datos y otras formas de resolver un error de 500 servidores internos.
//NOT_IMPLEMENTED: 501 Not Implemented -> 501: «No implementado». Este error indica que el servidor no es compatible con la funcionalidad necesaria para cumplir con la solicitud. Esto es casi siempre un problema en el propio servidor web, y por lo general debe ser resuelto por el host. Revisa nuestras recomendaciones sobre cómo resolver un error 501 no implementado.
//BAD_GATEWAY: 502 Bad Gateway -> 502: «Mala entrada». Este código de error significa típicamente que un servidor ha recibido una respuesta inválida de otro, como cuando se utiliza un servidor proxy. Otras veces una consulta o petición tardará demasiado, y así es cancelada o asesinada por el servidor y la conexión a la base de datos se rompe. Para más detalles, consulta nuestro tutorial en profundidad sobre cómo arreglar el error del 502 Bad Gateway.
//SERVICE_UNAVAILABLE: 503 Service Unavailable -> 503: «El servidor no está disponible para manejar esta solicitud en este momento.» La solicitud no puede ser completada en este momento. Este código puede ser devuelto por un servidor sobrecargado que no puede manejar solicitudes adicionales. Tenemos una guía completa sobre cómo arreglar el error de no disponibilidad del servicio 503.
//GATEWAY_TIMEOUT: 504 Gateway Timeout -> 504: «El servidor, actuando como una puerta de enlace, se ha agotado esperando a que otro servidor responda». Este es el código devuelto cuando hay dos servidores involucrados en el procesamiento de una solicitud, y el primer servidor se apaga esperando que el segundo servidor responda. Puedes leer más sobre cómo corregir los errores del 504 en nuestra guía dedicada.
//HTTP_VERSION_NOT_SUPPORTED: 505 HTTP Version Not Supported -> 505: «Versión HTTP no soportada». El servidor no soporta la versión HTTP que el cliente usó para hacer la solicitud.
//VARIANT_ALSO_NEGOTIATES: 506 Variant Also Negotiates
//INSUFFICIENT_STORAGE: 507 Insufficient Storage
//LOOP_DETECTED: 508 Loop Detected
//BANDWIDTH_LIMIT_EXCEEDED: 509 Bandwidth Limit Exceeded
//NOT_EXTENDED: 510 Not Extended
//NETWORK_AUTHENTICATION_REQUIRED: 511 Network Authentication Required -> 511: «Se requiere autenticación de la red». Este código de estado se envía cuando la red que está tratando de usar requiere alguna forma de autenticación antes de enviar su solicitud al servidor. Por ejemplo, es posible que tenga que aceptar los términos y condiciones de un punto de acceso Wi-Fi público.


package conexiona.pratices.noapractices.controller;

import conexiona.pratices.noapractices.model.MUserGroup;
import conexiona.pratices.noapractices.model.MUserGroupUser;
import conexiona.pratices.noapractices.service.SUserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class CUserGroup {

    @Autowired
    @Qualifier("SUserGroup")
    SUserGroup sUserGroup;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/userGroupAdd")
    public ResponseEntity<Void> addUserGroup(@RequestBody @Valid MUserGroup mUserGroup, UriComponentsBuilder builder){
        boolean flag = sUserGroup.insertUserGroup(mUserGroup);
        if(!flag){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/userGroupSelect/{userGroupId}").buildAndExpand(mUserGroup.getUserGroupId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/userGroupUpdate")
    public ResponseEntity<MUserGroup> updateUserGroup(@RequestBody @Valid MUserGroup mUserGroup) {
        boolean flag = sUserGroup.updateUserGroup(mUserGroup);
        if(!flag){
            return new ResponseEntity<>(mUserGroup,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(mUserGroup, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/userGroupDelete/{userGroupId}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable("userGroupId") String userGroupId) {
        boolean flag = sUserGroup.deleteUserGroup(userGroupId);
        if(!flag){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("userGroupSelect/{userGroupId}")
    public ResponseEntity<MUserGroup> getUserGroupById(@PathVariable("userGroupId") String userGroupId) {
        MUserGroup mUserGroup = sUserGroup.selectUserGroupByUserGroupId(userGroupId);
        return new ResponseEntity<>(mUserGroup, HttpStatus.OK);
    }

    @GetMapping("/userGroupSelect")
    public ResponseEntity<List<MUserGroup>> getAllUserGroup(){
        List<MUserGroup> userGroupList = sUserGroup.selectAllUserGroup();
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }

    @GetMapping("/userGroupPageableSelect")
    public ResponseEntity<List<MUserGroup>> getPageableUserGroup(Pageable pageable){
        List<MUserGroup> userGroupList = sUserGroup.selectPageableUserGroup(pageable);
        return new ResponseEntity<>(userGroupList, HttpStatus.OK);
    }

    @GetMapping("/userGroupUserGroupUserSelect/{userGroupId}")
    public ResponseEntity<List<MUserGroupUser>> getUserGroupUserByUserGroupId(@PathVariable("userGroupId") String userGroupId){
        List<MUserGroupUser> userGroupUserList = sUserGroup.selectUserGroupUserbyUserGroupId(userGroupId);
        return new ResponseEntity<>(userGroupUserList, HttpStatus.OK);
    }
}
