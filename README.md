# 🔐 Controlador de Acesso

Sistema desenvolvido em **Java** para gerenciamento de usuários e controle de acesso a ambientes, utilizando **MySQL** como banco de dados e arquitetura em camadas.

---

## 📋 Funcionalidades

- ✅ Cadastro de usuários
- ✅ Login com senha criptografada (BCrypt)
- ✅ Controle de permissões por nível de acesso
- ✅ Registro de entrada
- ✅ Registro de saída
- ✅ Histórico de acessos
- ✅ Dashboard com estatísticas
- ✅ Busca de usuários
- ✅ Edição de usuários
- ✅ Exclusão de usuários
- ✅ Recuperação de senha por pergunta secreta
- ✅ Auditoria completa das ações do sistema

---

## 👥 Níveis de acesso

### Administrador

- Cadastro de usuários
- Edição de usuários
- Exclusão de usuários
- Dashboard
- Auditoria
- Histórico de acessos
- Registro de entrada e saída
- Busca de usuários

### Funcionário

- Registro de entrada
- Registro de saída
- Busca de usuários

### Visitante

- Registro de entrada
- Registro de saída

---

## 📊 Dashboard

O sistema apresenta informações como:

- Total de usuários
- Quantidade de administradores
- Quantidade de funcionários
- Quantidade de visitantes
- Total de entradas
- Total de saídas
- Entradas do dia
- Saídas do dia
- Usuário com maior número de acessos
- Último acesso registrado

---

## 🔒 Segurança

- Criptografia de senhas utilizando **BCrypt**
- Recuperação de senha através de pergunta secreta
- Controle de permissões por perfil
- Registro de auditoria de todas as ações importantes

---

## 📝 Auditoria

O sistema registra automaticamente:

- Login
- Cadastro de usuários
- Edição de usuários
- Exclusão de usuários
- Registro de entrada
- Registro de saída
- Recuperação de senha

Também é possível consultar todos os registros diretamente pelo sistema.

---

## 🛠️ Tecnologias utilizadas

- Java 17
- JDBC
- MySQL
- BCrypt
- Git
- GitHub

---

## 📂 Estrutura do projeto

```
src
│
├── database
├── model
├── repository
├── service
├── ui
└── Main.java
```

---

## 🚀 Como executar

1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

2. Configure o banco de dados MySQL.

3. Execute o script SQL para criação das tabelas.

4. Configure a conexão com o banco de dados.

5. Execute o arquivo:

```
Main.java
```

---

## 📸 Demonstração

Adicione aqui imagens do sistema.

Exemplo:

```
/images/menu.png
/images/dashboard.png
/images/auditoria.png
```

---

## 👨‍💻 Autor

**Pedro Henrique Moscardini**

GitHub:
https://github.com/Mosca-Pedro