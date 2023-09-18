# integracaoTabelaFipe
Link para download do Docker: https://www.docker.com/. <br/>

Comandos para executar a aplicação: <br/>

Configuração do banco de dados: <br/>

`docker pull mongo:latest` <br/>
`docker run -d -p 27017:27017 --name integracaoFipeMongodb mongo:latest` <br/>

após iniciar o container do banco de dados, é necessário executar a aplicação <br/>
`docker pull edumokfa/integracao_tabela_fipe:1.0` <br/>
`docker run -p 8080:8080 --name integracao-tabela-fipe-mongodb --link integracaoFipeMongodb:mongo -d edumokfa/integracao_tabela_fipe:1.0` <br/>

Caso tudo funcione perfeitamente, a api está sendo executada. <br/>

É possível acessar a documentação em: http://localhost:8080/swagger-ui/index.html <br/>
<br/>
Este projeto possui: <br/>
Java 17 <br/>
Spring Boot 3.1.3 <br/>
Lombok <br/>
Swagger <br/>
MongoDB <br/>
Docker <br/>

