package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper.DevDnmaUnidadMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository.DevDnmaUnidadRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaUnidad;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDnmaUnidadWriter")
public class DevDnmaUnidadAdapter implements TargetWriter<DnmaUnidad> {

    private static final Logger log = LoggerFactory.getLogger(DevDnmaUnidadAdapter.class);

    private final DevDnmaUnidadRepository repository;
    private final DevDnmaUnidadMapper mapper;

    public DevDnmaUnidadAdapter(DevDnmaUnidadRepository repository, DevDnmaUnidadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(DnmaUnidad record) {
        return repository.countByUnidadId(record.unidadId()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(DnmaUnidad record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dnma unidad: {}", record.unidadId()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dnma unidad " + record.unidadId() + ": " + ex.getMessage(), ex))
                .then();
    }
}
