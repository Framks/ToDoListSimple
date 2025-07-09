package com.gabriel.tudosimples.usecases.task;

import com.gabriel.tudosimples.models.Task;

import java.util.List;

public interface ITaskUseCase {

    Task create(Task task);
    Task create(Task task, String username);
    void update(Task task);

    Task findById(Long id);

    List<Task> findByUserId(Long id);

    List<Task> findTaskNotConcluidByUserId(Long id);

    Task delete(Long id);
}
