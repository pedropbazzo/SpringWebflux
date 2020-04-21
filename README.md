# SpringWebflux

Repository for application developed with Spring Webflux

O Spring Framework 5 suporta programação reativa via Reactive Stream, a especificação que padroniza o uso de streams reativas dentro da JVM. 
Internamente, o Spring Framework implementa a Reactive Stream através do Reactor. 

**Documentação**: https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html

## Tipos de retorno com Webflux

Pensando numa api Rest que não utiliza webflux, na maioria dos casos temos 2 tipos de retorno para os dados.
Supondo uma classe **Pessoa**, e dois métodos, findAll, findById, teriamos a seguinte implementação:

``` Java
  public List<Pessoa> findAll() {
    // Realiza a listagem de pessoas
  }
  
  public Pessoa findById(@PathVariable int id) {
    // Realiza a busca de uma pessoa pelo id
  }
```
Com o webflux, temos uma abordagem um pouco diferente, utilizandos as classes **Flux** e **Mono**.
As classes Flux e o Mono são expostas nas APIs reativas do Spring Framework:
* Flux é um stream reativo formado 0 ou N elementos.
* Mono é um stream reativo formado por 0 ou 1 elemento.

Seguindo o exemplo acima, teriamos a seguinte implementação:
``` Java
  public Flux<Pessoa> findAll() {
    // Realiza a listagem de pessoas
  }
  
  public Mono<Pessoa> findById(@PathVariable int id) {
    // Realiza a busca de uma pessoa pelo id
  }
```

## Serviço reativo funcional

> Com o WebFlux.fn, o modelo de programação usa funções para rotear e
> manipular solicitações.
> 
> -   `HandlerFunction`- Solicitações HTTP são tratadas por um `HandlerFunction`que pega a `ServerRequest`e retorna a
> `Mono<ServerResponse>`. `HandlerFunctions`são como o corpo de um
> `@RequestMapping`método no modelo de programação baseado em anotação.
> -   `RouterFunction`- Solicitações HTTP de entrada são roteadas de a `RouterFunction`para a `Mono<HandlerFunction>`. Eles podem ser
> considerados semelhantes à `@RequestMapping`anotação.
> 
> 
> [# APIs REST reativas com Spring WebFlux.fn - Medium](https://medium.com/onehub-engineering/reactive-rest-apis-with-spring-webflux-fn-c9cb350822ce)

Novamente seguindo os exemplos acima, temos duas abordagens, **tradicional** utilizando a anotação @RestController, e **Reativo funcional** utilizando *handler* e *router*.

Seguindo a abordagem tradicional, teríamos a seguinte implementação:

``` Java
  @RestController("pessoa")
  @RequestMapping("/pessoas")
  public class PessoaController {
	  
      @Autowired
      private PessoaService service;
        
      public Flux<Pessoa> findAll() {
	return service.findAll();
      }
	  
      public Mono<Pessoa> findById(@PathVariable int id) {
        return service.findById(id);
      }

  }
```
Já seguindo a abordagem reativa funcinal teríamos a seguinte implementação.

Primeiramente criamos nosso **Handler**
O handler determina quais são os métodos, e como serão respondias as regras de negócio.

``` Java
@Component  
public class PessoaHandler {  
    
    @Autowired
    private PessoaService service;  
 
    public Mono<ServerResponse> findAll(ServerRequest request) {  
        return ok()  
                .contentType(MediaType.APPLICATION_JSON)  
                .body(service.findAll(), Pessoa.class);  
    }  
  
    public Mono<ServerResponse> findById(ServerRequest request) {  
        String id = request.pathVariable("id");  
  
		return ok()  
                .contentType(MediaType.APPLICATION_JSON)  
                .body(service.findById(id), Pessoa.class);  
	}  
  
}
```
Em nossa classe handler, primeiramente anotamos com *@Component*, para definir como um *bean* gerenciado pelo Spring. Em seguida criamos uma injeção de depencia com nosso *service*. E então para cada rota que tivermos, criamos um método que retorno um *Mono* de *ServerResponse*, e nesse método recebemos como parâmetro um *ServerRequest*, com todos as informações da requisição.
- [X]  Como retorno do método, primeiramente definimos qual o status code.

> O status code pode ser importado estaticamente com:   
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

- [X]  Após, definimos o tipo de retorno do conteudo.
- [X]  E por último, definimos qual será o corpo de resultado da requisição, chamando nosso *service*, também definindo qual a classe de retorno.

Após nosso handler estar criado, podemos partir para a criação no **Router**

O router é uma classe que tem como responsabilidade o roteamentos das requisições *http* para a classe handler. Basicamente definido para cada requisição, qual método será chamada, e qual o status code da requisição.

``` Java
@Configuration  
public class PessoaRouter {  
  
  @Bean  
  public RouterFunction<ServerResponse> route(PessoaHandler handler) {  
        return RouterFunctions  
                .route(GET("/pessoas").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)  
                .andRoute(GET("/pessoas/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById);
  }  
  
}
```

 - [X] Primeiramente anotamos nossa classe Router com *@Configuration*, para informar ao Spring que será uma classe de configuração.
 - [X] Após isso definimos um método que é anotado com *@Bean*, recebe nosso handler, e retorna um *RouteFunction* de *ServerResponse*. Esse método terá como retorno as rotas.
 - [X] Retornaremos então nossas rotas, primeiramente definido o método http que a rota response, junto com qual o endpoint da rota.
 - [X] Definimos tambem qual o tipo de Media que é produzido (retornado) por nossa rota.
 - [X] E por fim, chamados nosso *handler* indicando qual método do *handler* deverá ser chamado para a rota.
