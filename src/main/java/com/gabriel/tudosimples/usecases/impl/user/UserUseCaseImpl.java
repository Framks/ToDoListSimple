package com.gabriel.tudosimples.usecases.impl.user;

import com.gabriel.tudosimples.models.Usuario;
import com.gabriel.tudosimples.usecases.exceptions.DataBindingViolationException;
import com.gabriel.tudosimples.usecases.exceptions.ObjectNotFoundException;
import com.gabriel.tudosimples.usecases.repository.user.UserRepository;
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
    public Usuario create(Usuario usuario) {
        usuario.setId(null);
        usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        usuario = this.userRepository.save(usuario);
        return usuario;
    }

    @Override
    public List<Usuario> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        Optional<Usuario> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: "+id+", Tip: "+ Usuario.class.getName()
        ));
    }

    @Override
    public Usuario findByusername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("Usuario not Found"));
    }

    @Override
    @Transactional
    public void update(Usuario usuario) {
        Usuario newObj = findById(usuario.getId());
        newObj.setPassword(usuario.getPassword());
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
