package com.gabriel.tudosimples.usecases.impl;

import com.gabriel.tudosimples.models.Task;
import com.gabriel.tudosimples.models.User;
import com.gabriel.tudosimples.usecases.exceptions.DataBindingViolationException;
import com.gabriel.tudosimples.usecases.exceptions.ObjectNotFoundException;
import com.gabriel.tudosimples.usecases.repository.TaskRepository;
import com.gabriel.tudosimples.usecases.task.ITaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskCaseUseImpl implements ITaskUseCase {

    private final TaskRepository taskRepository;

    private final UserUseCaseImpl userUseCaseImpl;

    @Override
    @Transactional
    public Task create(Task task) {
        User user = this.userUseCaseImpl.findByusername(task.getUser().getUsername());
        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    public Task create(Task task, String username) {
        User user = this.userUseCaseImpl.findByusername(username);
        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    public void update(Task task) {
        Task newObj = findById(task.getId());
        newObj.setDescription(task.getDescription());
        this.taskRepository.save(newObj);
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() ->new ObjectNotFoundException("tarefa não encontrada Id:"+ id +", Tipo: "+Task.class.getName()));
    }

    @Override
    public List<Task> findByUserId(Long id) {
        return this.taskRepository.findByUserId(id);
    }

    @Override
    public List<Task> findTaskNotConcluidByUserId(Long id) {
        return null;
    }

    @Override
    @Transactional
    public Task delete(Long id) {
        Task task = findById(id);
        try{
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw (new DataBindingViolationException("Não é possivel excluir pois há entidade realcionada a Task"));
        }
        return task;
    }
}
