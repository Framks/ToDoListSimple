package com.gabriel.tudosimples.usecases.impl;

import com.gabriel.tudosimples.models.User;
import com.gabriel.tudosimples.usecases.exceptions.DataBindingViolationException;
import com.gabriel.tudosimples.usecases.exceptions.ObjectNotFoundException;
import com.gabriel.tudosimples.usecases.repository.UserRepository;
import com.gabriel.tudosimples.usecases.user.IUserUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements IUserUsecase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User create(User user) {
        user.setId(null);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        return user;
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: "+id+", Tip: "+ User.class.getName()
        ));
    }

    @Override
    public User findByusername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not Found"));
    }

    @Override
    @Transactional
    public void update(User user) {
        User newObj = findById(user.getId());
        newObj.setPassword(user.getPassword());
        this.userRepository.save(newObj);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        findById(id);
        try{
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new DataBindingViolationException("Não é possível excluir pois há entidades realcionadas a esse usuário!") {
            };
        }
    }
}
