package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper.DevDnmaFfaMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository.DevDnmaFfaRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaFfa;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDnmaFfaWriter")
public class DevDnmaFfaAdapter implements TargetWriter<DnmaFfa> {

    private static final Logger log = LoggerFactory.getLogger(DevDnmaFfaAdapter.class);

    private final DevDnmaFfaRepository repository;
    private final DevDnmaFfaMapper mapper;

    public DevDnmaFfaAdapter(DevDnmaFfaRepository repository, DevDnmaFfaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(DnmaFfa record) {
        return repository.countByFfaId(record.ffaId()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(DnmaFfa record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dnma ffa: {}", record.ffaId()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dnma ffa " + record.ffaId() + ": " + ex.getMessage(), ex))
                .then();
    }
}
