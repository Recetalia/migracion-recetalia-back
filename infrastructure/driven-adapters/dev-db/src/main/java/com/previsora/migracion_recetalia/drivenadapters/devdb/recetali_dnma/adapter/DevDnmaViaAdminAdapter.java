package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.mapper.DevDnmaViaAdminMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_dnma.repository.DevDnmaViaAdminRepository;
import com.previsora.migracion_recetalia.model.domain.dnma.DnmaViaAdmin;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devDnmaViaAdminWriter")
public class DevDnmaViaAdminAdapter implements TargetWriter<DnmaViaAdmin> {

    private static final Logger log = LoggerFactory.getLogger(DevDnmaViaAdminAdapter.class);

    private final DevDnmaViaAdminRepository repository;
    private final DevDnmaViaAdminMapper mapper;

    public DevDnmaViaAdminAdapter(DevDnmaViaAdminRepository repository, DevDnmaViaAdminMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(DnmaViaAdmin record) {
        return repository.countByViaAdminId(record.viaAdminId()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(DnmaViaAdmin record) {
        return repository.save(mapper.toEntity(record))
                .doOnSuccess(saved -> log.debug("Inserted dnma via_admin: {}", record.viaAdminId()))
                .onErrorMap(ex -> new TargetWriteException("Failed to insert dnma via_admin " + record.viaAdminId() + ": " + ex.getMessage(), ex))
                .then();
    }
}
