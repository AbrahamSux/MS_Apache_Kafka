package com.kafka.udemy.app.repositories;

import com.kafka.udemy.app.models.elk.MensajeRechazo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RejectionMessageRepository extends ElasticsearchRepository<MensajeRechazo, String> {

	Optional<MensajeRechazo> findByPlataformaOrigen(String plataformaOrigen);

	Optional<MensajeRechazo> findByPlataformaOrigenAndTipoDeNotificacion(String plataformaOrigen, String tipoDeNotificacion);

}
