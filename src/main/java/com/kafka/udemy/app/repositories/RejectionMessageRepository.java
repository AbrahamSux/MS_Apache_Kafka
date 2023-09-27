package com.kafka.udemy.app.repositories;

import com.kafka.udemy.app.models.elk.MensajeRechazo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectionMessageRepository extends ElasticsearchRepository<MensajeRechazo, String> {

}
