package com.levimartines.cursomc.service;

import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.security.CustomUserDetails;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new UsernameNotFoundException(email);
        }
        var authorities = cliente.getPerfisId().stream()
            .map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
        return new CustomUserDetails(cliente.getId(), cliente.getEmail(), cliente.getSenha(),
            authorities);
    }
}
