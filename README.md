# PADRÕES DE PROJETOS / LABORATÓRIO DE BANCO DE DADOS IV
## FATEC SÃO JOSÉ DOS CAMPOS - 2ºSEM/2020
### Professores
• [Emanuel Mineda Carneiro](https://github.com/mineda)<br>
• [Giuliano Araujo Bertoti](https://github.com/giulianobertoti)
### Integrantes
• [Lucas José Povinske](https://github.com/lucas-povinske)<br>
• Rodrigo César Reis

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

### 2.ii) Classe Java
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
Na linha #04 temos a propriedade que identifica a coluna na tabela que receberia o DiscriminatorValue como Usuário ou Administrador.
