# Projeto de Livraria (OAuth2 Resource Server)

Projeto backend de uma API REST que representa uma livraria online. Este projeto é utilizado no módulo de segurança com Spring Security e OAuth2, servindo como Resource Server para atividade.

## Tecnologias utilizadas:

Algumas das principais tecnologias utilizadas neste projeto:

1. Java 11;
2. Spring Boot 2.6.7;
3. Spring Data JPA com Hibernate;
4. Bean Validation;
5. Banco em memoria H2;
6. jUnit e Spring Testing;

## Ambiente de desenvolvimento

Para configurar e rodar a aplicação em ambiente local basta seguir os passos:

1. Clonar o repositório ou fazer seu download:

```shell
git clone git@github.com:zup-academy/oauth2-resourceserver-livraria.git
cd oauth2-resourceserver-livraria
```

2. Importar o projeto na IDE IntelliJ;

3. Iniciar a aplicação via IDE ou linha de comando:

```shell
./mvnw spring-boot:run
``` 

## Consumindo a API REST da aplicação

Aqui demonstramos através de exemplos como podemos consumir a API REST exposta pela aplicação.

Dado que a aplicação esteja rodando, basta executar os alguns dos comandos abaixo para exercitar os endpoints públicos da aplicação.

### Criando novo autor

```shell
curl --request POST \
  --url http://localhost:8080/oauth2-resourceserver-livraria/api/autores \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=56AB29FE8A394FBA578AD8C24723E80E \
  --data '{
	"nome": "Alberto Souza",
	"descricao": "CTO People na Zup",
	"email": "alberto.souza@zup.com.br"
}'
```

### Criando novo livro

```shell
curl --request POST \
  --url http://localhost:8080/oauth2-resourceserver-livraria/api/livros \
  --header 'Content-Type: application/json' \
  --cookie JSESSIONID=56AB29FE8A394FBA578AD8C24723E80E \
  --data '{
	"nome": "Spring Security",
	"descricao": "Livro sobre Spring Security",
	"isbn": "978-7-1653-2896-5",
	"publicadoEm": "2022-01-01",
	"autorId": "1"
}'
```

## Duvidas e suporte

Basta entrar em contato com a equipe da Zup Edu responsável pelo Bootcamp no horário comercial.