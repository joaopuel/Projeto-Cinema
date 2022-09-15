package com.entra21.grupo1.view.service;

import com.entra21.grupo1.model.dto.PessoaDTO;
import com.entra21.grupo1.model.dto.PessoaPayloadDTO;
import com.entra21.grupo1.model.dto.SessaoDTO;
import com.entra21.grupo1.model.entity.PessoaEntity;
import com.entra21.grupo1.view.repository.PessoaRepository;
import com.entra21.grupo1.view.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService implements UserDetailsService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaDTO> getAll(){
        return pessoaRepository.findAll().stream().map( pessoa -> {
            PessoaDTO dto = new PessoaDTO();
            dto.setNome(pessoa.getNome());
            dto.setSobrenome(pessoa.getSobrenome());
            dto.setTelefone(pessoa.getTelefone());
            dto.setCpf(pessoa.getCpf());
            dto.setSaldoCarteira(pessoa.getSaldoCarteira());
            dto.setLogin(pessoa.getLogin());
            dto.setSenha(pessoa.getSenha());
            return dto;
        }).collect(Collectors.toList());
    }

    public void save(PessoaPayloadDTO input) {
        PessoaEntity newEntity = new PessoaEntity();
        newEntity.setNome(input.getNome());
        newEntity.setSobrenome(input.getSobrenome());
        newEntity.setTelefone(input.getTelefone());
        newEntity.setCpf(input.getCpf());
        newEntity.setSaldoCarteira(input.getSaldoCarteira());
        newEntity.setLogin(input.getLogin());
        newEntity.setSenha(input.getSenha());
        pessoaRepository.save(newEntity);
    }

    public PessoaDTO update(PessoaDTO newPessoa) {
        PessoaEntity e = pessoaRepository.findById(newPessoa.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));

        if(newPessoa.getNome() != null) e.setNome(newPessoa.getNome());
        if(newPessoa.getSobrenome() != null) e.setSobrenome(newPessoa.getSobrenome());
        if(newPessoa.getTelefone() != null) e.setTelefone(newPessoa.getTelefone());
        if(newPessoa.getCpf() != null) e.setCpf(newPessoa.getCpf());
        if(newPessoa.getNome() != null) e.setNome(newPessoa.getNome());
        if(newPessoa.getSaldoCarteira() != null) e.setSaldoCarteira(newPessoa.getSaldoCarteira());
        if(newPessoa.getSenha() != null) e.setSenha(newPessoa.getSenha());

        pessoaRepository.save(e);
        PessoaDTO dto = new PessoaDTO();
        dto.setNome(e.getNome());
        dto.setSobrenome(e.getSobrenome());
        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PessoaEntity user = pessoaRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}