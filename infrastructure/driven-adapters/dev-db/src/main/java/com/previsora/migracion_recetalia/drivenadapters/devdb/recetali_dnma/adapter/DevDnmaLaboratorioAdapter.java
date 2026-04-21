package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper.DevDnmaLaboratorioMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository.DevDnmaLaboratorioRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaLaboratorio;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDnmaLaboratorioWriter")
public class DevDnmaLaboratorioAdapter implements TargetWriter<DnmaLaboratorio> {

    private static final Logger log = LoggerFactory.getLogger(DevDnmaLaboratorioAdapter.class);

    private final DevDnmaLaboratorioRepository repository;
    private final DevDnmaLaboratorioMapper mapper;

    public DevDnmaLaboratorioAdapter(DevDnmaLaboratorioRepository repository, DevDnmaLaboratorioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(DnmaLaboratorio record) {
        return repository.countByLabId(record.labId()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(DnmaLaboratorio record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dnma laboratorio: {}", record.labId()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dnma laboratorio " + record.labId() + ": " + ex.getMessage(), ex))
                .then();
    }
}
