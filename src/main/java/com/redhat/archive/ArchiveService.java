package com.redhat.archive;

import com.redhat.todo.domain.TodoJson;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArchiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveService.class);

//    @ConsumeEvent("todo") @Blocking
    @Incoming("todo") @Blocking
    public void recordTodoCreation(TodoJson todoJson) {

        LOGGER.info("Todo archived: {}", todoJson);
    }
}
