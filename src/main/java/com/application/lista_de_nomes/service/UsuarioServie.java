package com.application.lista_de_nomes.service;

import com.application.lista_de_nomes.dto.UsuarioDto;
import com.application.lista_de_nomes.model.Usuario;
import com.application.lista_de_nomes.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioServie {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServie.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioServie(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDto> buscarNomes(){
        LOGGER.info("Buscando usuarios ...");

        return usuarioRepository.findAll().stream().map(usuario ->
                new UsuarioDto(usuario.getNome())
        ).toList();
    }


    public String adicionarUsuario(UsuarioDto usuarioDto) {
        LOGGER.info("Adicionando novo usuario {}", usuarioDto);

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.nome());

        usuarioRepository.save(usuario);

        return "Usuario" + usuarioDto +  "adicionado com sucesso";
    }

    @Transactional
    public String alterarUsuarios(String nomeAtual, UsuarioDto usuarioDto) {
        LOGGER.info("Alterando usuario {} para {}", nomeAtual, usuarioDto);

        List<Usuario> usuarios = usuarioRepository.findByNome(nomeAtual);

        if (usuarios.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario nao encontrado");
        }

        usuarios.forEach(usuario -> usuario.setNome(usuarioDto.nome()));
        usuarioRepository.saveAll(usuarios);

        return usuarios.size() + " usuario(s) alterado(s) com sucesso";
    }

    @Transactional
    public String removerUsuarios(String nome){
        LOGGER.info("Excluindo usuario(s) com o nome {}", nome);

        List<Usuario> usuarios = usuarioRepository.findByNome(nome);

        if(usuarios.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario nao encontrado");
        }

        usuarioRepository.deleteAll(usuarios);

        return usuarios.size() + " usuario(s) removido com sucesso";
    }
}
