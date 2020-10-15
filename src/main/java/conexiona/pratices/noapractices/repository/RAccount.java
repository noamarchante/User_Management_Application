//Los repositorios se encargan de hacer las peticiones a la BD
//@Repository: identifica a la clase como un repositorio
//Patrón DAO: propone separar por completo la lógica de negocio de la lógica para acceder a los datos, de esta forma, el DAO proporcionará los métodos necesarios para insertar, actualizar, borrar y consultar la información; por otra parte, la capa de negocio solo se preocupa por lógica de negocio y utiliza el DAO para interactuar con la fuente de datos.

package conexiona.pratices.noapractices.repository;

import conexiona.pratices.noapractices.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("RAccount")
public interface RAccount  extends CrudRepository<Account, String>, PagingAndSortingRepository<Account, String> {

    @Override
    Page<Account> findAll (Pageable pageable);

    Account findByAccountId (String accountId);
}
