# ☀️ Projeto Sunny

<div align="center">

![ODS 2](https://img.shields.io/badge/ODS%202-Fome%20Zero-brightgreen?style=for-the-badge&logo=leaflet)
![Java](https://img.shields.io/badge/Java-Swing-orange?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

**Sistema desktop de gestão de doações com login, metas, dashboard e integração com PIX**

*Projeto desenvolvido com foco em organização de campanhas, registro de doações e acompanhamento de metas.*

</div>

---

## 📖 Sobre o Projeto

O **Projeto Sunny** é um sistema desktop desenvolvido em **Java** com interface gráfica em **Java Swing** e banco de dados **MySQL**, criado para auxiliar no controle de doações, campanhas e acompanhamento de metas.

O sistema conta com **login administrativo**, **dashboard com visualização de usuários**, **registro de doações** e **doações via PIX integradas à API do Mercado Pago**. Além disso, possui uma tela específica de **metas**, permitindo acompanhar os objetivos cadastrados para cada campanha.

O projeto foi pensado como uma solução funcional e organizada para apoiar o trabalho administrativo de instituições e projetos sociais.

---

## 🎯 Funcionalidades

- Sistema de **login com perfil de administrador**.
- **Dashboard** para exibição e controle de usuários.
- Tela de **metas** para acompanhamento dos objetivos da campanha.
- **Cadastro e registro de doações**.
- Integração de **doação PIX via API do Mercado Pago**.
- Controle de tipos, status e categorias de doações.
- Estrutura de banco relacional com suporte a diferentes perfis de usuário.
- CRUD completo para gerenciamento dos dados do sistema.

---

## 🗂️ Telas do Sistema

| # | Tela | Descrição |
|---|------|-----------|
| 1 | 🔐 Login | Autenticação de usuários com validação por perfil |
| 2 | 🏠 Dashboard | Visualização geral dos usuários e informações do sistema |
| 3 | 🎯 Metas | Cadastro e acompanhamento das metas da campanha |
| 4 | 🤝 Registro de Doações | Cadastro das doações recebidas no sistema |
| 5 | 💳 Doação PIX | Geração e registro de doações via Mercado Pago |
| 6 | 👤 Usuários | Gerenciamento de usuários e permissões |
| 7 | 🤝 Registro de Doações ADMIN | Crud doações |
| 8 | 🎯 Metas | Alteração de Metas, criação e edição. | 

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java
- **Interface Gráfica:** Java Swing
- **Banco de Dados:** MySQL
- **Cliente de Banco:** DBeaver
- **IDE de Código:** IntelliJ IDEA
- **IDE de Design:** NetBeans
- **Integração de PIX:** API do Mercado Pago
- **Conexão com banco:** Classe `DatabaseConnection`

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, tenha instalado:

- **JDK 17+**
- **MySQL**
- **DBeaver**
- **IntelliJ IDEA**
- Apenas execute o Main/main.java O projeto já inclui o driver JDBC, portanto **não é necessário adicionar o mysql-connector manualmente**.

---

## 🚀 Como Executar

### 1. Acesse a pasta do projeto

Entre na pasta `sunny` antes de iniciar o projeto:

```bash
cd sunny
```

### 2. Importe o banco de dados

Abra o **DBeaver** ou outro gerenciador MySQL e execute o script `.sql` fornecido para criar as tabelas e inserir os dados iniciais.

### 3. Configure a conexão

A conexão com o banco de dados é feita pela classe **`DatabaseConnection`**, já incluída no projeto.

### 4. Execute a aplicação

Abra o projeto na **IntelliJ IDEA** e execute a classe principal localizada em `src/main/java/main/Main.java`.

---

## 🗃️ Estrutura do Banco de Dados

O banco do **Projeto Sunny** foi modelado para suportar usuários, campanhas, metas e doações, com as seguintes tabelas principais:

- `perfil_usuario`
- `usuario`
- `tipo_doacao`
- `status_doacao`
- `genero_destino`
- `tamanho_roupa`
- `tamanho_calcado`
- `campanha`
- `doacao`
- `doacao_dinheiro`
- `doacao_item`

### Script SQL

```sql
CREATE DATABASE projeto_sunny;
USE projeto_sunny;

CREATE TABLE perfil_usuario (
  id_perfil INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(30) NOT NULL UNIQUE,
  descricao VARCHAR(120)
);

CREATE TABLE usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  usuario VARCHAR(50) NOT NULL UNIQUE,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  telefone VARCHAR(20),
  id_perfil INT NOT NULL,
  ativo TINYINT(1) NOT NULL DEFAULT 1,
  criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (id_perfil) REFERENCES perfil_usuario(id_perfil)
);

CREATE TABLE tipo_doacao (
  id_tipo_doacao INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(30) NOT NULL UNIQUE,
  descricao VARCHAR(120)
);

CREATE TABLE status_doacao (
  id_status_doacao INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(20) NOT NULL UNIQUE,
  descricao VARCHAR(120)
);

CREATE TABLE genero_destino (
  id_genero_destino INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE tamanho_roupa (
  id_tamanho_roupa INT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(5) NOT NULL UNIQUE
);

CREATE TABLE tamanho_calcado (
  id_tamanho_calcado INT AUTO_INCREMENT PRIMARY KEY,
  codigo VARCHAR(5) NOT NULL UNIQUE
);

CREATE TABLE campanha (
  id_campanha INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(120) NOT NULL,
  descricao TEXT,
  mes_referencia DATE NOT NULL,
  meta_dinheiro DECIMAL(10,2) NOT NULL DEFAULT 0,
  meta_alimentos INT NOT NULL DEFAULT 0,
  meta_roupas INT NOT NULL DEFAULT 0,
  meta_brinquedos INT NOT NULL DEFAULT 0,
  meta_livros INT NOT NULL DEFAULT 0,
  meta_calcados INT NOT NULL DEFAULT 0,
  meta_eletrodomesticos INT NOT NULL DEFAULT 0,
  meta_saude INT NOT NULL DEFAULT 0,
  ativa TINYINT(1) NOT NULL DEFAULT 1,
  id_admin INT NOT NULL,
  criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_admin) REFERENCES usuario(id_usuario)
);

CREATE TABLE doacao (
  id_doacao BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_doador INT NOT NULL,
  nome_doador VARCHAR(100) NOT NULL,
  email_doador VARCHAR(120) NOT NULL,
  id_tipo_doacao INT NOT NULL,
  id_status_doacao INT NOT NULL,
  id_campanha INT,
  observacao VARCHAR(255),
  data_doacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_doador) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_tipo_doacao) REFERENCES tipo_doacao(id_tipo_doacao),
  FOREIGN KEY (id_status_doacao) REFERENCES status_doacao(id_status_doacao),
  FOREIGN KEY (id_campanha) REFERENCES campanha(id_campanha)
);

CREATE TABLE doacao_dinheiro (
  id_doacao BIGINT PRIMARY KEY,
  valor DECIMAL(10,2) NOT NULL,
  status_pagamento VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
  mercado_pago_preference_id VARCHAR(120),
  pix_copia_cola TEXT,
  pix_qr_code LONGTEXT,
  FOREIGN KEY (id_doacao) REFERENCES doacao(id_doacao) ON DELETE CASCADE
);

CREATE TABLE doacao_item (
  id_item BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_doacao BIGINT NOT NULL,
  quantidade INT NOT NULL DEFAULT 1,
  validade DATE,
  id_genero_destino INT,
  id_tamanho_roupa INT,
  id_tamanho_calcado INT,
  descricao_item VARCHAR(120),
  FOREIGN KEY (id_doacao) REFERENCES doacao(id_doacao) ON DELETE CASCADE,
  FOREIGN KEY (id_genero_destino) REFERENCES genero_destino(id_genero_destino),
  FOREIGN KEY (id_tamanho_roupa) REFERENCES tamanho_roupa(id_tamanho_roupa),
  FOREIGN KEY (id_tamanho_calcado) REFERENCES tamanho_calcado(id_tamanho_calcado)
);

INSERT INTO perfil_usuario (nome, descricao) VALUES
('ADMINISTRADOR', 'Usuário com acesso total ao sistema'),
('COMUM', 'Pessoa física doadora'),
('EMPRESA', 'Pessoa jurídica doadora'),
('BENEFICIARIO', 'Pessoa atendida pela ONG');

INSERT INTO tipo_doacao (nome, descricao) VALUES
('DINHEIRO', 'Doação financeira'),
('ALIMENTO', 'Doação de alimentos'),
('ROUPA', 'Doação de roupas'),
('BRINQUEDO', 'Doação de brinquedos'),
('LIVRO', 'Doação de livros'),
('CALCADO', 'Doação de calçados'),
('ELETRODOMESTICO', 'Doação de eletrodomésticos'),
('SAUDE', 'Itens relacionados à saúde'),
('OUTROS', 'Outros tipos de doação');

INSERT INTO status_doacao (nome, descricao) VALUES
('PENDENTE', 'Aguardando validação'),
('CONCLUIDA', 'Doação processada com sucesso'),
('REJEITADA', 'Doação recusada');

INSERT INTO genero_destino (nome) VALUES ('MASCULINO'), ('FEMININO'), ('UNISSEX');

INSERT INTO tamanho_roupa (codigo) VALUES ('P'), ('M'), ('G'), ('GG'), ('XG'), ('XXG'), ('XXXG'), ('XXXXG'), ('XXXXXG');

INSERT INTO tamanho_calcado (codigo) VALUES ('28'), ('29'), ('30'), ('31'), ('32'), ('33'), ('34'), ('35'), ('36'), ('37'), ('38'), ('39'), ('40'), ('41'), ('42'), ('43'), ('44'), ('45'), ('46'), ('47'), ('48');

INSERT INTO usuario (usuario, nome, email, senha, telefone, id_perfil) VALUES
('admin', 'Administrador do Projeto Sunny', 'admin@projetosunny.com', 'admin123', '(11)90000-0000', 1);

INSERT INTO campanha (titulo, descricao, mes_referencia, meta_dinheiro, meta_alimentos, meta_roupas, meta_brinquedos, meta_livros, meta_calcados, meta_eletrodomesticos, meta_saude, ativa, id_admin)
VALUES ('Campanha Mensal Sunny', 'Metas mensais iniciais do Projeto Sunny', DATE_FORMAT(CURDATE(), '%Y-%m-01'), 5000.00, 300, 100, 50, 30, 40, 10, 20, 1, 1);
```

---

## 📁 Estrutura do Projeto

A estrutura segue o padrão mostrado na imagem:

```text
sunny/
├── .github/
├── .idea/
└── src/
    └── main/
        └── java/
            ├── config/
            ├── controller/
            ├── dao/
            ├── icons/
            ├── main/
            ├── model/
            ├── service/
            ├── util/
            └── view/
```

---

## 👥 Equipe

| Nome | GitHub |
|------|--------|
| Matheus | [@Imnotsu](https://github.com/Imnotsu) |
| Leonardo Arboleya | [@leonardoarboleya](https://github.com/leonardoarboleya) |
| Fernando | - |

---

## 🌍 ODS 2 — Fome Zero

<div align="center">

Este projeto é desenvolvido em apoio ao **Objetivo de Desenvolvimento Sustentável 2** da ONU:

> "Acabar com a fome, alcançar a segurança alimentar e melhoria da nutrição e promover a agricultura sustentável."

🔗 [Saiba mais sobre a ODS 2](https://brasil.un.org/pt-br/sdgs/2)

</div>

---

<div align="center">
Feito com dedicação para apoiar campanhas de doação e organização social.
</div>
