-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 15/02/2025 às 14:59
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `projetojava`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `administrador`
--

CREATE TABLE `administrador` (
  `codigo_pessoa` int(11) NOT NULL,
  `cargo` varchar(100) NOT NULL,
  `data_contratacao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `evento`
--

CREATE TABLE `evento` (
  `codigo_evento` int(11) NOT NULL,
  `nome_evento` varchar(250) NOT NULL,
  `desc_evento` varchar(250) NOT NULL,
  `data_evento` datetime NOT NULL,
  `duracao_evento` int(11) NOT NULL,
  `local_evento` varchar(250) NOT NULL,
  `capacidade_maxima` int(11) NOT NULL,
  `status_evento` varchar(50) NOT NULL,
  `categoria_evento` varchar(30) NOT NULL,
  `preco_evento` float NOT NULL,
  `codigo_organizador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `funcao_pessoa`
--

CREATE TABLE `funcao_pessoa` (
  `id_funcao` int(11) NOT NULL,
  `funcao` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `funcao_pessoa`
--

INSERT INTO `funcao_pessoa` (`id_funcao`, `funcao`) VALUES
(1, 'PARTICIPANTE'),
(2, 'ADMINISTRADOR');

-- --------------------------------------------------------

--
-- Estrutura para tabela `inscricao_evento`
--

CREATE TABLE `inscricao_evento` (
  `codigo_inscricao` int(11) NOT NULL,
  `codigo_participante` int(11) NOT NULL,
  `codigo_evento` int(11) NOT NULL,
  `data_inscricao` date NOT NULL,
  `status_inscricao` varchar(50) NOT NULL,
  `presenca_confirmada` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `participante`
--

CREATE TABLE `participante` (
  `codigo_pessoa` int(11) NOT NULL,
  `data_nascimento` date NOT NULL,
  `cpf` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `pessoa`
--

CREATE TABLE `pessoa` (
  `codigo_pessoa` int(11) NOT NULL,
  `nome_completo` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `senha` varchar(250) NOT NULL,
  `id_funcao` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`codigo_pessoa`);

--
-- Índices de tabela `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`codigo_evento`),
  ADD KEY `fk_cod_organizador` (`codigo_organizador`);

--
-- Índices de tabela `funcao_pessoa`
--
ALTER TABLE `funcao_pessoa`
  ADD PRIMARY KEY (`id_funcao`);

--
-- Índices de tabela `inscricao_evento`
--
ALTER TABLE `inscricao_evento`
  ADD PRIMARY KEY (`codigo_inscricao`),
  ADD KEY `codigo_participante` (`codigo_participante`),
  ADD KEY `codigo_evento` (`codigo_evento`);

--
-- Índices de tabela `participante`
--
ALTER TABLE `participante`
  ADD PRIMARY KEY (`codigo_pessoa`);

--
-- Índices de tabela `pessoa`
--
ALTER TABLE `pessoa`
  ADD PRIMARY KEY (`codigo_pessoa`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `fk_role_pessoa` (`id_funcao`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `evento`
--
ALTER TABLE `evento`
  MODIFY `codigo_evento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `inscricao_evento`
--
ALTER TABLE `inscricao_evento`
  MODIFY `codigo_inscricao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `pessoa`
--
ALTER TABLE `pessoa`
  MODIFY `codigo_pessoa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `fk_cod_admin` FOREIGN KEY (`codigo_pessoa`) REFERENCES `pessoa` (`codigo_pessoa`);

--
-- Restrições para tabelas `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `fk_cod_organizador` FOREIGN KEY (`codigo_organizador`) REFERENCES `administrador` (`codigo_pessoa`);

--
-- Restrições para tabelas `inscricao_evento`
--
ALTER TABLE `inscricao_evento`
  ADD CONSTRAINT `fk_cod_evento` FOREIGN KEY (`codigo_evento`) REFERENCES `evento` (`codigo_evento`),
  ADD CONSTRAINT `fk_cod_participante_evento` FOREIGN KEY (`codigo_participante`) REFERENCES `participante` (`codigo_pessoa`);

--
-- Restrições para tabelas `participante`
--
ALTER TABLE `participante`
  ADD CONSTRAINT `fk_cod_participante` FOREIGN KEY (`codigo_pessoa`) REFERENCES `pessoa` (`codigo_pessoa`);

--
-- Restrições para tabelas `pessoa`
--
ALTER TABLE `pessoa`
  ADD CONSTRAINT `fk_role_pessoa` FOREIGN KEY (`id_funcao`) REFERENCES `funcao_pessoa` (`id_funcao`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
