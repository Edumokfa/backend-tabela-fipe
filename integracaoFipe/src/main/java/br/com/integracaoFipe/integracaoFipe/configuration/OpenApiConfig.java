package br.com.integracaoFipe.integracaoFipe.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
                            contact = @Contact(
                                    name = "Eduardo Mokfa", email = "edumokfa@hotmail.com"
                            ),
                            title = "API de integração com a tabela FIPE",
                            description = "Esta API é responsável por coletar informações da tabela FIPE e armazená-las em cache, para que haja uma filtragem maior e mais rápida das informações dos Veículos"))
public class OpenApiConfig {

}
