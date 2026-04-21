package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper.DevDnmaTfMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository.DevDnmaTfRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaTf;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDnmaTfWriter")
public class DevDnmaTfAdapter implements TargetWriter<DnmaTf> {

    private static final Logger log = LoggerFactory.getLogger(DevDnmaTfAdapter.class);

    private final DevDnmaTfRepository repository;
    private final DevDnmaTfMapper mapper;

    public DevDnmaTfAdapter(DevDnmaTfRepository repository, DevDnmaTfMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(DnmaTf record) {
        return repository.countByTfId(record.tfId()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(DnmaTf record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dnma tf: {}", record.tfId()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dnma tf " + record.tfId() + ": " + ex.getMessage(), ex))
                .then();
    }
}
