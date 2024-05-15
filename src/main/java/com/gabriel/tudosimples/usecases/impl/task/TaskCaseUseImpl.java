package com.gabriel.tudosimples.usecases.impl.task;

import com.gabriel.tudosimples.models.Task;
import com.gabriel.tudosimples.models.Usuario;
import com.gabriel.tudosimples.usecases.exceptions.DataBindingViolationException;
import com.gabriel.tudosimples.usecases.exceptions.ObjectNotFoundException;
import com.gabriel.tudosimples.usecases.impl.user.UserUseCaseImpl;
import com.gabriel.tudosimples.usecases.repository.task.TaskRepository;
import com.gabriel.tudosimples.usecases.task.ITaskUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskCaseUseImpl implements ITaskUseCase {

    private final TaskRepository taskRepository;

    private final UserUseCaseImpl userUseCaseImpl;

    public TaskCaseUseImpl(UserUseCaseImpl userUseCase, TaskRepository taskRepository){
        this.userUseCaseImpl = userUseCase;
        this.taskRepository = taskRepository;
    }


    @Override
    @Transactional
    public Task create(Task task) {
        Usuario usuario = this.userUseCaseImpl.findByusername(task.getUsuario().getUsername());
        task.setId(null);
        task.setUsuario(usuario);
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
        return this.taskRepository.findByUsuarioId(id);
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
