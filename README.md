# MecControl - Sistema de Gerenciamento de Oficina Mecânica 🚗

O **MecControl** é uma aplicação de backend e controle lógico desenvolvida em Java e integrada ao banco de dados relacional MySQL. O software foi projetado sob medida para automatizar e blindar a gestão operacional de oficinas mecânicas de veículos leves, gerenciando desde o cadastro de clientes e veículos até o fluxo seguro de auditoria de Ordens de Serviço (O.S.).

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

* **Linguagem Principal:** Java (JDK 17 ou superior)
* **Banco de Dados:** MySQL Server
* **Conectividade:** JDBC (Java Database Connectivity) com driver *MySQL Connector/J*
* **Arquitetura:** Arquitetura em Camadas (View, Model, DAO)
* **Versionamento:** Git & Interface Gráfica Fork

---

## 🧱 Arquitetura em Camadas (Multilayer)

O código-fonte do projeto está estruturado de forma modular no pacote principal `br.juliana`, garantindo a separação estrita de responsabilidades:

1.  **`br.juliana.view` (Visão):** Camada de interface por terminal interativo (`Main.java`). Controla os menus, captura os inputs via teclado e exibe as respostas.
2.  **br.juliana.model (Modelo):** Contém as entidades que espelham as tabelas relacionais em forma de objetos Java (ex: `OrdemServico.java`), trafegando os dados na memória.
3.  **`br.juliana.dao` (Persistência):** Classes Data Access Object responsáveis exclusivas pela execução de consultas, inserções, atualizações e transações SQL na base de dados.

---

## 🗄️ Como Carregar a Base de Dados (Dump do Banco)

O banco de dados se chama `defaultdb`. Para subir a estrutura de tabelas idêntica no seu MySQL local, execute o script DDL abaixo no seu MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS defaultdb;
USE defaultdb;

-- Exemplo da tabela pai de Ordens de Serviço estruturada
CREATE TABLE IF NOT EXISTS ordens_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_os INT NOT NULL,
    situacao VARCHAR(30) NOT NULL,
    data_abertura TIMESTAMP NOT NULL,
    data_encerramento TIMESTAMP NULL,
    data_aguardando_pecas_desde TIMESTAMP NULL,
    chk_estepe TINYINT(1) DEFAULT 0,
    chk_macaco_chave_roda TINYINT(1) DEFAULT 0,
    chk_triangulo TINYINT(1) DEFAULT 0,
    chk_radio TINYINT(1) DEFAULT 0,
    chk_nivel_combustivel VARCHAR(20),
    chk_observacoes_avarias TEXT,
    cliente_id BIGINT NOT NULL,
    veiculo_id BIGINT NOT NULL,
    aberto_por_colaborador_id BIGINT NOT NULL,
    valor_total_servicos DECIMAL(10,2) DEFAULT 0.00,
    valor_total_pecas DECIMAL(10,2) DEFAULT 0.00,
    valor_total_geral DECIMAL(10,2) DEFAULT 0.00
);

## 🚀 Como Executar o Projeto Localmente

### 1. Pré-requisitos
* Ter o Java JDK 17 ou superior instalado e configurado nas variáveis de ambiente (`JAVA_HOME`).
* Ter o **MySQL Server** ativo localmente na porta padrão `3306`.
* Configurar as credenciais da sua máquina (usuário e senha do banco) na classe de configuração em `br.juliana.config.FabricaConexao`.

### 2. Configuração do Banco de Dados
1. Abra o seu gerenciador MySQL (ex: MySQL Workbench ou DBeaver).
2. Execute o script de criação do banco contido na seção "Como Carregar a Base de Dados".
3. Certifique-se de que as tabelas foram criadas com sucesso no schema `defaultdb`.

### 3. Compilação e Execução pelo Terminal
Abra o terminal na raiz do projeto (onde está o arquivo `pom.xml`) e execute os comandos abaixo:

```bash
# 1. Limpar e baixar as dependências do Maven
mvn clean install

# 2. Compilar todo o código fonte do projeto
mvn clean compile

# 3. Executar a aplicação principal pelo terminal
mvn exec:java -Dexec.mainClass="br.juliana.view.Main"

### 4. Execução dos Testes Automatizados (Adendo Futuro)
Para validar a integridade das regras de negócio do sistema e rodar a suíte de testes que estamos desenvolvendo:
```bash
mvn test