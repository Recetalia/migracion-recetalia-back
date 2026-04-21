package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevLaboratoryMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevLaboratoryRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaLaboratory;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devLaboratoryWriter")
public class DevLaboratoryAdapter implements TargetWriter<RecetaLaboratory> {

    private static final Logger log = LoggerFactory.getLogger(DevLaboratoryAdapter.class);

    private final DevLaboratoryRepository repository;
    private final DevLaboratoryMapper mapper;

    public DevLaboratoryAdapter(DevLaboratoryRepository repository,
                                 DevLaboratoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaLaboratory record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaLaboratory record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted laboratory: {}", record.id()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert laboratory " + record.id() + ": " + ex.getMessage(), ex))
                .then();
    }
}
