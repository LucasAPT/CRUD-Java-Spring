package com.projeto.lucasluan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.lucasluan.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}

