# MecControl - Sistema de Gerenciamento de Oficina Mecânica 🚗

Este é o projeto backend desenvolvido em Java Puro (Core) para o Projeto Integrador do curso do Senac. O sistema foi projetado para gerenciar o fluxo de trabalho de uma oficina mecânica de veículos leves, incluindo o cadastro de clientes, ordens de serviço e um sistema integrado de auditoria.

## 🚀 Arquitetura e Tecnologias
* **Linguagem:** Java 17
* **Gerenciador de Dependências:** Maven
* **Banco de Dados:** MySQL hospedado na nuvem (Aiven Cloud)
* **Conexão:** JDBC (Java Database Connectivity) puro
* **Interface:** Terminal Interativo (via console com `Scanner`)
* **Padrão de Organização:** Camadas (Conexao, DAO, Model, View)

## 🗄️ Banco de Dados e Auditoria
O banco de dados já está populado na nuvem e conta com uma tabela de `logs_auditoria`. Essa tabela rastreia alterações críticas do sistema (como atualizações de ordens de serviço) gravando o usuário, a tabela afetada, o tipo de operação e a descrição da mudança.

## 🛠️ Como rodar o projeto localmente
1. Certifique-se de ter o **JDK 17** instalado.
2. Clone este repositório na sua máquina.
3. Abra o projeto no seu editor (IntelliJ IDEA recomendado).
4. Sincronize o **Maven** para baixar a dependência do driver do MySQL (`mysql-connector-j`).
5. No arquivo responsável pela conexão, certifique-se de inserir a credencial secreta da nuvem Aiven.
6. Execute a classe `Main.java` para abrir o menu interativo no terminal.