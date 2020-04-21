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
  
  public Pessoa findById(@PathVariaable int id) {
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
