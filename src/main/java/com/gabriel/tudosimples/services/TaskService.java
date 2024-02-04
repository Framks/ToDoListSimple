package com.gabriel.tudosimples.services;

import com.gabriel.tudosimples.models.Task;
import com.gabriel.tudosimples.models.User;
import com.gabriel.tudosimples.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    public TaskRepository taskRepository;

    @Autowired
    public UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() ->new RuntimeException("tarefa não encontrada Id:"+ id +", Tipo: "+Task.class.getName()));
    }

    @Transactional
    public Task create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);
        try{
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw (new RuntimeException("Não é possivel excluir pois há entidade realcionada a Task"));
        }
    }

    public List<Task> findAllByUserId(Long id){
        return this.taskRepository.findByUser_id(id);
    }

}
