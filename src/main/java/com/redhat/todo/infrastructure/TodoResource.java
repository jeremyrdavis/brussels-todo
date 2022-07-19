package com.redhat.todo.infrastructure;

import com.redhat.todo.domain.Todo;
import com.redhat.todo.domain.TodoJson;
import com.redhat.todo.domain.TodoRepository;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api")
public class TodoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoResource.class);

    @Inject
    TodoService todoService;

    @Inject
    TodoRepository todoRepository;

    @POST@Transactional
    public Response addTodo(TodoJson somethingTodo) {

        TodoJson todoJson = todoService.addTodo(somethingTodo);
        return Response.created(URI.create("/" + todoJson.id())).entity(todoJson).build();
    }

    @GET
    public Response allTodos() {

        List<Todo> allTodos = todoRepository.listAll();
        return Response.ok().entity(allTodos).build();
    }

    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updateTodo(@PathParam("id") final Long id, final TodoJson todoJson) {

        Todo todo = Todo.findById(id);
        todo.setTitle(todoJson.title());
        todo.setOrder(todoJson.order());
        todo.setCompleted(todoJson.completed());
        todoRepository.persist(todo);
        return Response.ok().entity(todo).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTodo(@PathParam("id") final Long id) {

        todoRepository.deleteById(id);
        return Response.ok().build();
    }
}
