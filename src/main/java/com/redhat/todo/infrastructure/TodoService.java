package com.redhat.todo.infrastructure;

import com.redhat.todo.domain.Todo;
import com.redhat.todo.domain.TodoJson;
import com.redhat.todo.domain.TodoRepository;
import io.vertx.core.eventbus.EventBus;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TodoService {

    @Inject
    TodoRepository todoRepository;

    @Inject
    EventBus eventBus;

    @Channel("todo")
    Emitter<TodoJson> todoJsonEmitter;

    public TodoJson addTodo(TodoJson todoJson) {

        Todo todo = Todo.createFromJson(todoJson);
        todoRepository.persist(todo);
//        eventBus.publish("todo", todoJson);
        TodoJson result = new TodoJson(todo.id, todo.getTitle(), todo.getOrder(), todo.isCompleted());
        todoJsonEmitter.send(result);
        return new TodoJson(todo.id, todo.getTitle(), todo.getOrder(), todo.isCompleted());
    }
}
