package com.application.lista_de_nomes.controller;

import com.application.lista_de_nomes.dto.UsuarioDto;
import com.application.lista_de_nomes.service.UsuarioServie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    public final UsuarioServie usuarioServie;

    public UsuarioController(UsuarioServie usuarioServie) {
        this.usuarioServie = usuarioServie;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> bucarNomes(){
        return ResponseEntity.ok().body(usuarioServie.buscarNomes());
    }

    @PostMapping
    public ResponseEntity<String> adicionarUsuario(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.created(URI.create("/api/v1/usuarios")).body(usuarioServie.adicionarUsuario(usuarioDto));
    }

    @PutMapping("/{nome}")
    public ResponseEntity<String> alterarUsuario(@PathVariable String nome, @RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioServie.alterarUsuarios(nome, usuarioDto));
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<String> excluirUsuario(@PathVariable String nome){
        return ResponseEntity.ok(usuarioServie.removerUsuarios(nome));
    }

}
