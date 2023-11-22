package com.projeto.lucasluan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.projeto.lucasluan.model.Contato;
import com.projeto.lucasluan.repository.ContatoRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    // Operação CREATE (Exibir formulário para inserir novo contato)
    @GetMapping("/novoContato")
    public String exibirFormularioInserirContato(Model model) {
        model.addAttribute("contato", new Contato());
        return "inserir_contato";
    }

    // Operação CREATE
    @PostMapping("/salvarContato")
    public String criarContato(@ModelAttribute Contato contato) {
        contatoRepository.save(contato);
        return "redirect:/contatos/listarContatos";
    }

    // Operação READ (Listar Todos)
    @GetMapping("/listarContatos")
    public String listarContatos(Model model) {
        List<Contato> contatos = contatoRepository.findAll();
        model.addAttribute("contatos", contatos);
        return "listar_contatos";
    }

    // Operação READ (Buscar por ID)
    @GetMapping("/detalhesContato/{id}")
    public String detalhesContato(@PathVariable Long id, Model model) {
        Optional<Contato> optionalContato = contatoRepository.findById(id);

        if (optionalContato.isPresent()) {
            model.addAttribute("contato", optionalContato.get());
            return "detalhes_contato";
        } else {
            return "redirect:/contatos/listarContatos";
        }
    }

    // Operação UPDATE
    @GetMapping("/editarContato/{id}")
    public String formularioEditarContato(@PathVariable Long id, Model model) {
        Optional<Contato> optionalContato = contatoRepository.findById(id);
        if (optionalContato.isPresent()) {
            model.addAttribute("contato", optionalContato.get());
            return "editar_contato";
        } else {
            // Lógica para tratar o contato não encontrado
            return "redirect:/contatos/listarContatos";
        }
    }

    @PostMapping("/atualizarContato/{id}")
    public String atualizarContato(@PathVariable Long id, @ModelAttribute Contato contato) {
        Optional<Contato> contatoExistente = contatoRepository.findById(id);

        if (contatoExistente.isPresent()) {
            Contato contatoAtualizado = contatoExistente.get();
            contatoAtualizado.setNome(contato.getNome());
            contatoAtualizado.setTelefone(contato.getTelefone());
            contatoAtualizado.setEmail(contato.getEmail());
            contatoAtualizado.setAdicionais(contato.getAdicionais());

            contatoRepository.save(contatoAtualizado);
        }
        return "redirect:/contatos/listarContatos";
    }

    // Operação DELETE
    @GetMapping("/excluirContato/{id}")
    public String excluirContato(@PathVariable Long id) {
        contatoRepository.deleteById(id);
        return "redirect:/contatos/listarContatos";
    }
}