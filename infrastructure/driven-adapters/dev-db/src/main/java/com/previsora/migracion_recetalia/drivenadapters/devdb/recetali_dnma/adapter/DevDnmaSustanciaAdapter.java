package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper.DevDnmaSustanciaMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository.DevDnmaSustanciaRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaSustancia;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDnmaSustanciaWriter")
public class DevDnmaSustanciaAdapter implements TargetWriter<DnmaSustancia> {

    private static final Logger log = LoggerFactory.getLogger(DevDnmaSustanciaAdapter.class);

    private final DevDnmaSustanciaRepository repository;
    private final DevDnmaSustanciaMapper mapper;

    public DevDnmaSustanciaAdapter(DevDnmaSustanciaRepository repository, DevDnmaSustanciaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(DnmaSustancia record) {
        return repository.countBySustanciaId(record.sustanciaId()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(DnmaSustancia record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dnma sustancia: {}", record.sustanciaId()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dnma sustancia " + record.sustanciaId() + ": " + ex.getMessage(), ex))
                .then();
    }
}
