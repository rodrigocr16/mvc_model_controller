# PADRÕES DE PROJETOS / LABORATÓRIO DE BANCO DE DADOS IV
## FATEC SÃO JOSÉ DOS CAMPOS - 2ºSEM/2020
### Professores
• [Emanuel Mineda Carneiro](https://github.com/mineda);<br>
• [Giuliano Araujo Bertoti](https://github.com/giulianobertoti).
### Integrantes
• [Lucas José Povinske](https://github.com/lucas-povinske);<br>
• [Otavio Augusto Raposo Barreto](https://github.com/otavio-raposo);<br>
• Rodrigo César Reis.

## 1. Apresentação
Projeto com back-end em Java e front-end em Vue.js<br>
Este projeto foi feito para as disciplinas de Padrões de Projetos e Laboratório de Banco de Dados IV, aplicando o padrão MVC;<br>

A camada VIEW foi feita em Vue.js utilizando Axios para integração com a camada CONTROLLER que retorna através de protocolo HTTP as informações do banco em MySQL na camada MODEL, estruturada com Jpa / Hibernate.<br>

Este repositório refere-se às camadas MODEL e CONTROLLER do projeto;<br>
O repositório referente à camada VIEW se encontra [neste endereço](https://github.com/rodrigocr16/mvc_view).
___
## 2. MODEL
### 2.i) Banco MySQL
O arquivo [up.sql](https://github.com/rodrigocr16/padroes_projetos/blob/master/up.sql) disponível neste repositório contém todos os comandos DDL do banco criado em MySQL: suas tabelas, colunas e constraints.<br>
Na versão atual do projeto, apenas uma entidade foi desenvolvida a fundo - a entidade Usuário, representada na tabela a seguir:
```
CREATE TABLE USU_USUARIO(
    USU_ID BIGINT UNSIGNED AUTO_INCREMENT,
    USU_NOME_USUARIO VARCHAR(50) NOT NULL,
    USU_SENHA VARCHAR(50) NOT NULL,
    USU_NOME_EXIBICAO VARCHAR(64),
    USU_ADMINISTRADOR VARCHAR(20),
    ADM_CLASSIFICACAO INT,

    CONSTRAINT PK_USU_ID PRIMARY KEY (USU_ID),
    CONSTRAINT UK_USU_NOME_USUARIO UNIQUE (USU_NOME_USUARIO)
);
```
As colunas desta tabela representam todos os atributos da entidade:<br>
  • ``USU_ID``: Uma ID auto incrementada que serve como chave primária;<br>
  • ``USU_NOME_USUARIO``: Um nome de usuário único para identificação do mesmo na aplicação;<br>
  • ``USU_SENHA``: Valor obrigatório utilizado para autenticação do usuário;<br>
  • ``USU_NOME_EXIBICAO``: Campo opcional que representa como este usuário é apresentado aos demais;<br>
  • ``USU_ADMINISTRADOR``: Valor do tipo [DiscriminatorValue](https://docs.oracle.com/javaee/7/api/javax/persistence/DiscriminatorValue.html) da classe [Admin.java](https://github.com/rodrigocr16/padroes_projetos/blob/master/src/main/java/br/gov/sp/fatec/padroesprojetos/entity/Admin.java) que define se este é do tipo Usuário ou Administrador - por questões de privilégios;<br>
  • ``ADM_CLASSIFICACAO``: Valor inteiro de 01 a 05 inicialmente pensando para definir o tipo de acesso que aquele administrador possui.<br>

### 2.ii) Entidade Java
Ao analisar a classe ``Usuario.java`` é impossível não notar a relação dela com a tabela mencionada acima, já que seus atributos levam o nome e as identificações de suas tabelas correspondentes:
```
01.  @Entity
02.  @Table(name = "USU_USUARIO")
03.  @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
04.  @DiscriminatorColumn(name = "USU_ADMINISTRADOR", discriminatorType = DiscriminatorType.STRING)
05.  @AttributeOverride(name = "id", column = @Column(name = "USU_ID"))
06.  public class Usuario extends GeraId { 
07.  
08.      @JsonIgnore
09.      @OneToMany(fetch = FetchType.LAZY, mappedBy = "proprietario")
10.      private Set<Personagem> personagens;
11.      
12.      @Column(name = "USU_NOME_USUARIO")
13.      private String nomeUsuario;
14.  
15.      @Column(name = "USU_SENHA")
16.      private String senha;
17.      
18.      @Column(name = "USU_NOME_EXIBICAO")
19.      private String nomeExibicao;
20.      
21.      
22.      public String getNomeUsuario() {
23.          return nomeUsuario;
24.      }
25.      public void setNomeUsuario(String nomeUsuario) {
26.          this.nomeUsuario = nomeUsuario;
27.      }
28.      
29.      public String getSenha() {
30.          return senha;
31.      }
32.      public void setSenha(String senha) {
33.          this.senha = senha;
34.      }
35.      
36.      public String getNomeExibicao() {
37.          return nomeExibicao;
38.      }
39.      public void setNomeExibicao(String nomeExibicao) {
40.          this.nomeExibicao = nomeExibicao;
41.      }
42.  }
```
Na linha #03 vemos que para o banco esta classe possue uma hierarquia do tipo SingleTable, onde independente se um objeto é criado em uma ou outra das suas classes filhas, todos são passados para a mesma tabela;<br>
Na linha #04 temos a propriedade que identifica a coluna na tabela que receberia o DiscriminatorValue como Usuário ou Administrador.<br>
É possível notar também que Usuário herda a classe GeraId (que como o nome sugere, gera uma Id) e que possue uma relação de ONE-TO-MANY com a classe Personagem.

### 2.iii) Classe DaoJpa
O modelo MVC abordado trabalha com DAO e JPA para efetuar transações com o banco, servindo como nossa camada de persistência;<br>
Os métodos descritos abaixo são utilizados pelo CONTROLLER para ler e gravar informações no nosso banco.<br>

#### Buscar um Usuário:
Existem duas opções quando é preciso buscar usuários: uma para retornar um único registro informando o valor nomeUsuario, que é único, e uma para retornar todos os registros existentes no banco;<br>
O método ``buscarUsuario(nomeUsuario)`` recebe uma String que é utilizada como parâmetro em uma query no banco. Desta forma, apenas um resultado é retornado pela query, que finalmente é retornado pelo método:
```
@Override
public Usuario buscarUsuario(String nomeUsuario) {
    String jpql = "select u from Usuario u where u.nomeUsuario = :nomeUsuario";
    TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
    query.setParameter("nomeUsuario", nomeUsuario);
    return query.getSingleResult();
}
```
A outra opção é através da List ``todosUsuario()``, que simplesmente faz uma query sem cláusula WHERE, retornando todos os registros da tabela:
```
@Override
public List<Usuario> todosUsuario() {
    String jpql = "select u from Usuario u";
    TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
    return query.getResultList();
}
```

#### Cadastrar um novo Usuario:
O cadastro de um novo usuário é feito em etapas, através de três métodos que conferem maior versatilidade à aplicação, permitindo realizar apenas parte do processo, se preciso for.<br>

A primeira etapa é feita com o método ``salvarUsuario(usuario)``, que verifica se o usuário possui uma Id, salvando um novo registro em caso positivo, e atualizado o que for encontrado com aquela Id em caso negativo:
```
@Override
public Usuario salvarUsuario(Usuario usuario){
    if(usuario.getId() == null){
        em.persist(usuario);
    } else {
        em.merge(usuario);
    }   return usuario;
}
```
A outra etapa do processo de um novo cadastro é feita pelo método ``commitUsuario(usuario)``, que executa o método acima criando ou atualizando o usuário existente, e então faz a persistência desta alteração no banco. Caso haja algum erro, é feito o rollback das alterações:
```
@Override
public Usuario commitUsuario(Usuario usuario){
    try{
        em.getTransaction().begin();
        salvarUsuario(usuario);
        em.getTransaction().commit();
        return usuario;
    }
    catch(PersistenceException pe){
        pe.printStackTrace();
        em.getTransaction().rollback();
        throw new RuntimeException("Ocorreu um erro ao salvar o usuário: ", pe);
    }
}
```
O que define as informações que serão salvas no banco no caso de um novo cadastro é o método ``cadastrarUsuario(nomeUsuario, senha, nomeExibicao)``, que cria um novo objeto do tipo Usuario, define seus atributos obrigatórios através de setters, e finaliza com a chamada do método ``commitUsuario(usuario)`` abordado acima:
```
@Override
public Usuario cadastrarUsuario(String nomeUsuario, String senha, String nomeExibicao) {
    Usuario usuario = new Usuario();
    usuario.setNomeUsuario(nomeUsuario);
    usuario.setSenha(senha);
    usuario.setNomeExibicao(nomeExibicao);
    return commitUsuario(usuario);
}
```
#### Alterar um Usuário:
A forma como a criação de usuários é feita permite que utilizemos apenas uma parte do processo de criação, se preciso for. Com isso, o método mencionado ``salvarUsuario(usuario)`` mencionado anteriormente é o que utilizamos caso seja preciso atualizar um registro no banco, já que ele verifica se o usuário já existe no banco e atualiza o registro em caso positivo.

#### Remover um Usuario:
Através de uma busca no banco realizada pelo método ``buscarUsuario(nomeUsuario)`` o método de remoção ``removerUsuario(nomeUsuario)`` retorna uma RuntimeException caso a busca retorne nada, ou remove o usuário encontrado, finalizando com um commit:
```
@Override
public void removerUsuario(String nomeUsuario) {
    Usuario usuario = buscarUsuario(nomeUsuario);
    if(usuario == null){
        throw new RuntimeException("O usuário solicitado não foi encontrado.");
    }
    em.getTransaction().begin();
    em.remove(usuario);
    em.getTransaction().commit();
}
```
___
## 3. CONTROLLER
Na camada CONTROLLER nós trabalhamos com o UsuarioController.java que faz a comunicação entre a VIEW e o MODEL para esta entidade, e com alguns exemplos de filtros:<br>

### 3.i) Usuario
O UsuarioController.java trabalha no padrão HTTP, recebendo uma requisição e devolvendo uma resposta. Resumidamente, serve de intermediário entre o nosso back-end em Java e o nosso front-end em Vue, trocando informações e devolvendo um status dependendo se a comunicação foi ou não efetuada com sucesso;<br>
A seguir mostraremos os procedimentos Get, Post, Put e Delete, que respectivamente permitem leitura, criação, atualização e remoção de registros.

#### GET
Buscando pelo parâmetro ``nomeUsuario`` na request, criamos um novo UsuarioDao que fará a busca no banco;<br>
Se o parâmetro buscado não for encontrado, sabemos que se trata de uma busca que deseja listar todos os usuários cadastrados, então cria-se uma List para receber essas informações e chamamos o método ``todosUsuario()``;<br>
Caso um nomeUsuario seja informado, o método ``buscarUsuario(nomeUsuario)`` utilizará este parâmetro para retornar o registro desejado;<br>
Independentemente se a busca desejava retornar um ou vários usuários, estes serão dados no formato .Json que será utilizado pela VIEW:
```
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String nomeUsuario = req.getParameter("nomeUsuario");
    if (nomeUsuario.equals("")) {
        try{
            UsuarioDao usuarioDao = new UsuarioDaoJpa();
            List<Usuario> usuario = usuarioDao.todosUsuario();
            ObjectMapper mapper = new ObjectMapper();
            String usuarioJson = mapper.writeValueAsString(usuario);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.print(usuarioJson);
            out.flush();
        } catch (NullPointerException npe) {
            resp.sendError(400, "Valor buscado inválido");
            return;
        } catch (NoResultException nre) {
            resp.sendError(404, "Usuário não cadastrado");
            return;
        }
    } else{
        try {
            UsuarioDao usuarioDao = new UsuarioDaoJpa();
            Usuario usuario = usuarioDao.buscarUsuario(nomeUsuario);
            ObjectMapper mapper = new ObjectMapper();
            String usuarioJson = mapper.writeValueAsString(usuario);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(200);
            PrintWriter out = resp.getWriter();
            out.print(usuarioJson);
            out.flush();
        } catch (NullPointerException npe) {
            resp.sendError(400, "Valor buscado inválido");
            return;
        } catch (NoResultException nre) {
            resp.sendError(404, "Usuário não cadastrado");
            return;
        }
    }               
}
```

#### POST
Através do ObjectMapper, mapeamos o .Json passado pela request em um objeto do tipo Usuario, que por sua vez é inserido no banco pelo método ``commitUsuario(usuario)`` chamado para um DAO recém criado que recebe este objeto. Note que qualquer transação realizada no banco é feita através da DAO:
```
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Usuario usuario = mapper.readValue(req.getReader(), Usuario.class);

    UsuarioDao usuarioDao = new UsuarioDaoJpa();
    usuarioDao.commitUsuario(usuario);

    String usuarioJson = mapper.writeValueAsString(usuario);
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    resp.setStatus(201);
    String location = req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/usuario?nomeUsuario="
            + usuario.getNomeUsuario();
    resp.setHeader("Location", location);
    PrintWriter out = resp.getWriter();
    out.print(usuarioJson);
    out.flush();
}
```
Após a transação, é exibido o .Json do usuario recém criado.

#### PUT
Como o PUT deseja atualizar um registro existente, ele primeiro deve buscar este registro através do nome do usuário;<br>
Já que a Id dos usuários é única, definimos a Id do objeto do tipo Usuario criado aqui como o valor encontrado pela busca. Finalmente salvamos as demais informações sobrescrevendo o que existia anteriormente pelas informações passadas através do .Json:
```
@Override
protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    Usuario usuario = mapper.readValue(req.getReader(), Usuario.class);
    UsuarioDao usuarioDao = new UsuarioDaoJpa();

    String nomeUsuario = req.getParameter("nomeUsuario");
    try{
        usuario.setId(usuarioDao.buscarUsuario(nomeUsuario).getId());
        usuarioDao.commitUsuario(usuario);
    } catch(Exception e){
        resp.sendError(404, "Usuario nao encontrado");
        return;
    }

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(200);
    PrintWriter out = resp.getWriter();
    out.print("");
    out.flush();
}
```

#### DELETE
Finalizando, aqui também criamos uma DAO que fará a transação no banco, recebendo o parâmetro ``nomeUsuario`` na request e passando este valor para o método ``removerUsuario(nomeUsuario)`` que discutimos na seção MODEL:
```
@Override
protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String nomeUsuario = req.getParameter("nomeUsuario");

    UsuarioDao usuarioDao = new UsuarioDaoJpa();
    try{
        usuarioDao.removerUsuario(nomeUsuario);
    } catch(Exception e){
        resp.sendError(404, "Usuario nao encontrado");
        return;
    }

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(200);
    PrintWriter out = resp.getWriter();
    out.print("");
    out.flush();
}
```
