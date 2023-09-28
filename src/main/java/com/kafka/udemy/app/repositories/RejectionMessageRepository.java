package com.kafka.udemy.app.repositories;

import com.kafka.udemy.app.models.elk.MensajeRechazo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RejectionMessageRepository extends ElasticsearchRepository<MensajeRechazo, String> {

	Optional<MensajeRechazo> findByCliente(String cliente);

	Optional<MensajeRechazo> findByClienteAndAndCorreoElectronico(String cliente, String correoElectronico);

	List<MensajeRechazo> findByPlataformaOrigen(String plataformaOrigen);

	List<MensajeRechazo> findByPlataformaOrigenAndTipoDeNotificacion(String plataformaOrigen, String tipoDeNotificacion);

}
