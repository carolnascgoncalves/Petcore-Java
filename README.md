# 🐾 PetCore – Challenge 2026

## 🗣️ Desenvolvedoras do Projeto

### Emanuelly Ventura Do Nascimento
**RM562339 - 2TDSPJ**

### Carolina Nascimento Gonçalves
**RM564786 - 2TDSPJ**

### Julia Sayuri Kina
**RM564555 - 2TDSPJ**

---

# 📌 Descrição do Projeto

O **PetCore** é um sistema web de gerenciamento veterinário desenvolvido com **Java e Spring Boot**. O objetivo da aplicação é centralizar informações dos pets e seus registros clínicos, facilitando o acompanhamento do histórico médico dos animais.

A aplicação possui interface web desenvolvida com **Thymeleaf**, persistência de dados com **Spring Data JPA/Hibernate**, integração com o banco de dados Oracle por meio de **JDBC** e controle de autenticação e autorização utilizando **Spring Security**.

O sistema possui dois perfis de usuário, **Tutor** e **Médico Veterinário**, com permissões diferentes de acordo com suas responsabilidades dentro da aplicação.

A aplicação também conta com uma interface personalizada, gerenciamento de imagens e está hospedada na plataforma **Render**, permitindo seu acesso pela internet.

---

# 🎯 Objetivo do Projeto

O principal objetivo do PetCore é facilitar o gerenciamento veterinário por meio de um sistema organizado, seguro e centralizado.

A aplicação permite que tutores acompanhem seus pets e respectivos registros médicos, enquanto médicos veterinários podem gerenciar informações clínicas, como exames, receitas, prontuários, relatórios, medicamentos e clínicas.

---

# ⚙️ Funcionalidades

## 👤 Tutor

O perfil **TUTOR** possui acesso às funcionalidades relacionadas à própria conta e aos seus pets.

Principais funcionalidades:

- Cadastro de uma nova conta;
- Login com e-mail e senha;
- Visualização e atualização da própria conta;
- Envio e atualização da foto de perfil;
- Cadastro de pets;
- Visualização dos próprios pets;
- Alteração dos dados e da imagem de pets ativos;
- Inativação de pets;
- Visualização do histórico médico dos próprios pets;
- Visualização de exames;
- Visualização de receitas;
- Exclusão da própria conta quando não possuir pets ativos.

Ao cadastrar um Pet, ele é automaticamente associado ao Tutor autenticado e recebe um Histórico médico.

Quando um Pet é inativado, seus dados passam a ser preservados como registro histórico e não podem mais ser alterados.

## 🩺 Médico Veterinário

O perfil **MEDICO** possui acesso às funcionalidades clínicas do sistema.

Principais funcionalidades:

- Cadastro de uma nova conta;
- Login com e-mail e senha;
- Visualização e atualização da própria conta;
- Envio e atualização da foto de perfil;
- Visualização dos pets;
- Visualização dos históricos médicos;
- Cadastro e gerenciamento de exames;
- Cadastro e gerenciamento de receitas;
- Cadastro e gerenciamento de prontuários;
- Cadastro e gerenciamento de relatórios;
- Cadastro e gerenciamento de medicamentos;
- Cadastro e gerenciamento de clínicas;
- Gerenciamento de endereços.

Nos registros clínicos criados pelo Médico, o profissional autenticado é associado automaticamente ao registro.

---

# 🐶 Pets e Histórico Médico

Cada Pet possui um **Histórico médico criado automaticamente no momento de seu cadastro**.

Na página do Pet são apresentados os registros atuais ou futuros, como:

- Exames agendados;
- Receitas ativas.

Já o Histórico concentra informações passadas:

- Exames realizados;
- Receitas vencidas.

Dessa forma, a aplicação separa informações atuais das informações que fazem parte do histórico clínico do animal.

---

# 🔒 Regras de Negócio

O PetCore possui regras de negócio para preservar a consistência dos dados e dos registros clínicos.

### Pets

- Todo Pet é cadastrado inicialmente como **ATIVO**;
- Um Histórico médico é criado automaticamente junto com o Pet;
- Um Tutor só pode gerenciar seus próprios pets;
- Um Pet pode ser alterado enquanto estiver ativo;
- A alteração de status ocorre de **ATIVO para INATIVO**;
- Depois de inativado, o Pet não pode ser alterado ou reativado;
- Não podem ser criados novos registros clínicos para Pets inativos;
- Os registros clínicos existentes são preservados.

### Exclusão da conta do Tutor

A conta de um Tutor não pode ser excluída enquanto existir algum Pet ativo associado a ela.

Quando todos os Pets associados estiverem inativos, a conta pode ser excluída. A associação entre Tutor e Pet é removida, mas os Pets inativos e seus registros clínicos permanecem armazenados, preservando o histórico médico.

### Exames e Receitas

- Exames são associados a um Pet e ao Médico responsável;
- Receitas são associadas a um Pet, ao Médico responsável e aos medicamentos selecionados;
- Registros associados a Pets inativos não podem ser alterados;
- A aplicação diferencia exames agendados de exames já realizados;
- A aplicação diferencia receitas ativas de receitas vencidas.

### Dados exibidos ao usuário

Os identificadores utilizados internamente pelo sistema não são utilizados como principal informação para o usuário.

Nas telas e seleções são apresentados dados legíveis, como nome do Pet, Tutor e Médico, facilitando a identificação dos registros.

---

# 🔐 Spring Security

A aplicação utiliza **Spring Security** para autenticação e controle de acesso.

Existem dois perfis:

### `TUTOR`

Possui acesso às funcionalidades relacionadas à própria conta, aos próprios pets e à consulta dos respectivos registros clínicos.

### `MEDICO`

Possui acesso às funcionalidades clínicas, incluindo exames, receitas, prontuários, relatórios, medicamentos e clínicas.

As rotas são protegidas de acordo com o perfil autenticado. Dessa forma, esconder uma opção na interface não é a única proteção: o acesso também é validado pelo backend.

As senhas são armazenadas utilizando **BCrypt**.

---

# 🗄️ Banco de Dados e Integração JDBC

O projeto utiliza o **Oracle Database da FIAP** como banco de dados principal.

A persistência das informações é realizada utilizando:

- **Spring Data JPA e Hibernate:** gerenciamento das entidades, relacionamentos e operações de persistência;
- **Spring JDBC (JdbcTemplate):** integração direta com o banco Oracle e execução de rotinas SQL.

### Integração com Procedure Oracle

O projeto também implementa integração com uma procedure armazenada no banco Oracle, por meio da classe `ProcedurePetService`.

A procedure utilizada é:

`PKG_PETCORE.PROC_PETS_TUTOR_JSON`

Essa integração permite recuperar os identificadores dos pets associados a um Tutor por meio de dados retornados em formato JSON utilizando `DBMS_OUTPUT`.

O JDBC executa a procedure, recupera as informações retornadas e identifica os pets vinculados ao Tutor autenticado.

Dessa forma, o projeto combina o uso de **JPA/Hibernate e JDBC**, demonstrando diferentes formas de integração entre Java e Oracle Database.

A estrutura do banco e a procedure precisam estar disponíveis no ambiente Oracle utilizado pela aplicação.

---

# 📷 Gerenciamento de Imagens

O PetCore permite o envio e gerenciamento de imagens para personalizar os perfis dos usuários e dos animais.

Funcionalidades implementadas:

- Envio de foto de perfil para Tutores e Médicos;
- Envio de imagens para os Pets;
- Atualização das imagens cadastradas;
- Validação dos formatos de arquivo;
- Limite de tamanho de até 5 MB por imagem;
- Armazenamento dos arquivos em diretórios específicos.

São aceitos arquivos nos formatos JPG, PNG e WEBP.

---

# ✅ Validações

Os formulários possuem validações para impedir o cadastro de informações inválidas.

Entre as validações implementadas estão:

- Campos obrigatórios;
- Tamanho mínimo de textos;
- Validação de e-mail;
- Validação de telefone;
- Validação de CEP;
- Validação de CNPJ;
- Validação de URL;
- Validação de datas;
- Data de nascimento válida para Tutor e Médico;
- Verificação de e-mail e telefone já cadastrados;
- Validações específicas para os registros clínicos;
- Validação de imagens enviadas.

Quando uma informação é inválida, a aplicação retorna a mensagem correspondente no próprio formulário.

---

# 🎨 Interface

A interface do PetCore foi desenvolvida utilizando **Thymeleaf, HTML, CSS e Bootstrap**.

O projeto conta com uma identidade visual própria, com páginas estilizadas e componentes organizados para facilitar a navegação.

Entre os recursos implementados estão:

- Layout padronizado entre as páginas;
- Menu de navegação adaptado ao perfil autenticado;
- Páginas de listagem, cadastro, edição e detalhes;
- Exibição de fotos de usuários e pets;
- Mensagens de sucesso, erro e validação;
- Organização das informações clínicas;
- Interfaces específicas para os perfis Tutor e Médico.

---

# 🔄 Principais Fluxos

## Cadastro de Pet

1. O Tutor realiza login;
2. Cadastra um novo Pet;
3. O Pet é associado automaticamente ao Tutor autenticado;
4. O Pet é criado com status `ATIVO`;
5. Um Histórico médico é criado automaticamente para o animal.

## Inativação de Pet

1. O Tutor seleciona um Pet ativo;
2. Altera seu status para `INATIVO`;
3. O Pet deixa de aceitar alterações;
4. Novos registros clínicos deixam de ser permitidos;
5. Os registros anteriores permanecem disponíveis para consulta.

## Registro Clínico

1. O Médico realiza login;
2. Seleciona o Pet desejado por meio de informações legíveis, como nome do Pet e Tutor;
3. Cadastra o registro clínico;
4. O Médico autenticado é associado automaticamente ao registro;
5. O registro passa a integrar as informações clínicas do Pet.

---

# 💻 Tecnologias Utilizadas

## Backend

- Java 17;
- Spring Boot 4.1.0;
- Spring MVC;
- Spring Data JPA;
- Hibernate;
- Spring JDBC (JdbcTemplate);
- Spring Security;
- Jakarta Validation;
- Maven.

## Frontend

- Thymeleaf;
- HTML5;
- CSS3;
- Bootstrap 5;
- Bootstrap Icons;
- JavaScript.

## Banco de Dados

- Oracle Database;
- Oracle JDBC Driver;
- SQL;
- PL/SQL.

## Ferramentas e Hospedagem

- Eclipse IDE;
- Oracle SQL Developer;
- Git;
- GitHub;
- Docker;
- Render.

---

# ☁️ Deploy

O PetCore está hospedado na plataforma **Render**, utilizando Docker para a construção e execução da aplicação.

A aplicação possui configuração de porta dinâmica para se adaptar ao ambiente de hospedagem e utiliza variável de ambiente para proteger as credenciais de acesso ao banco Oracle.

O sistema pode ser acessado diretamente pelo navegador, sem necessidade de instalação local.

**Acesse o PetCore:**

https://petcore-java.onrender.com/login?logout=true

---

# 🔑 Usuários para Teste

O banco de dados possui registros iniciais para permitir a utilização dos diferentes perfis da aplicação.

A senha utilizada pelos usuários de teste é:

```text
Petcore123
```

Para testar as permissões, utilize um usuário com perfil **TUTOR** e outro com perfil **MEDICO** cadastrados no banco de dados.

Também é possível criar novas contas de Tutor e Médico pela página de login.

---

# 📂 Repositório

O código-fonte do projeto está disponível no GitHub:

https://github.com/carolnascgoncalves/Petcore-Java

# ▶️ Vídeo

POR LINK AQUI
