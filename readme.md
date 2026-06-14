# MecControl - Sistema de Gerenciamento de Oficina Mecânica 🚗

Este é o projeto backend desenvolvido em Java Puro (Core) para o Projeto Integrador do curso do Senac. 
O sistema foi projetado para gerenciar o fluxo de trabalho de uma oficina mecânica de veículos leves, 
incluindo o cadastro de clientes, ordens de serviço e um sistema integrado de auditoria.

## 🚀 Arquitetura e Tecnologias
* **Linguagem:** Java 17
* **Gerenciador de Dependências:** Maven
* **Interface:** Terminal Interativo (via console com `Scanner`)
* **Padrão de Organização:** Camadas (Conexao, DAO, Model, View)

## 🗄️ Banco de Dados e Conexão
O projeto utiliza o **MySQL** como sistema gerenciador de banco de dados. Para garantir a consistência dos dados e facilitar o desenvolvimento, o banco de dados agora é gerenciado **localmente** por cada integrante da equipe.

Abra o projeto que você baixou do GitHub e localize a pasta db.
1. Quando receber o arquivo, seguir estes passos no Workbench
2. Abrir o Workbench e conectar na instância local  (localhost).
3. No menu lateral esquerdo (aba Management), clicar em Data Import/Restore.
4. Selecionar a opção Import from Self-Contained File e buscar o arquivo oficina_backup.sql que está dentro da pasta db do projeto.
5. No campo Default Target Schema,  pode deixar em branco ou clicar em New... e dar o nome do projeto.
6. Clicar no botão Start Import no canto inferior direito.
7. Depois disso, basta  atualizar (Refresh) a aba Schemas no Workbench, e todas as tabelas, relacionamentos e dados gerados por você estarão prontos para usar localmente.

