## 🗄️ Guia de Conexão ao Banco de Dados (MySQL Workbench)

Para acessar o banco de dados do nosso projeto integrador na nuvem, siga detalhadamente o passo a passo abaixo:

### 📥 1. Pré-requisito Obrigatório
* [cite_start]Antes de começar, faça o download do arquivo de certificado **`ca.pem`** fornecido pelo professor para a sua máquina[cite: 11, 13].
* [cite_start]⚠️ **Importante:** Não altere o nome do arquivo baixado[cite: 14].

### ➕ 2. Criando a Nova Conexão
1. [cite_start]Abra o **MySQL Workbench**[cite: 2].
2. [cite_start]Na tela inicial, clique no botão de adição **`+`** localizado ao lado de *MySQL Connections*[cite: 3].

### 📝 3. Preenchendo os Parâmetros (Aba Parameters)
Na janela que se abrir, preencha os campos da aba *Parameters* com os seguintes dados de nuvem:

* **Connection Name:** `MecControl - Nuvem` (ou o nome de sua preferência)
* [cite_start]**Hostname / Host:** `mysql-1dc12192-julianafmendes2784-projeto-integrador.a.aivencloud.com` [cite: 6]
* [cite_start]**Port:** `15777` [cite: 7]
* [cite_start]**Username:** `avnadmin` [cite: 8]
* [cite_start]**Default Schema:** `defaultdb` [cite: 10]

> 🔒 **Senha (Password):** A senha de acesso padrão `AVNS_...` foi omitida aqui por segurança por se tratar de um repositório público. [cite_start]Solicite-a diretamente no grupo privado da nossa equipe para realizar o primeiro acesso. Solicite também o arquivo ca.pem para ser importado no campo "SSL CA File".

### 🔒 4. Configuração de Segurança (Aba SSL)
Mude para a aba **SSL** e realize os seguintes ajustes obrigatórios para permitir a conexão com a nuvem Aiven:

1. [cite_start]No campo **Use SSL**, altere a opção para **`Require`**[cite: 11].
2. [cite_start]No campo **SSL CA File**, clique no botão com três pontinhos `...` e selecione o arquivo **`ca.pem`** que você baixou no passo 1[cite: 11, 13].

### 🚀 5. Finalizando
* Clique no botão **`Test Connection`** no rodapé para testar se deu tudo certo.
* [cite_start]Clique em **`OK`** para salvar a conexão[cite: 12].
* [cite_start]Ao entrar na conexão, você terá acesso imediato a todas as tabelas e registros estruturados do sistema[cite: 12]!