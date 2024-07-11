Feature: TI-EA2023-12608-R3 Compliance getExtenalLink

  @UAT @DemoSerenity @Ejecutar
  Scenario Outline: <Peticion>

    Given quiero ejecutar el API <API> con la peticion <Peticion>
    When consumo el api <API> con la data data de prueba
      | <UrlBase> | <EndPoint> | <METODO> | <HEADERS> | <BODY> | <PathFile> | <NameKeys> |
    Then verifico el status code <StatusCode>
    And las respuestas esperadas <RespuestasEsperadas> en las rutas <PATHS> del response
    #And valido los <CamposVariables> con url del response <urlEsperada>

    Examples:
      | API     | Peticion | UrlBase                      | EndPoint | METODO | HEADERS | BODY                 | StatusCode | PATHS             | RespuestasEsperadas | CamposVariables | urlEsperada | PathFile | NameKeys |
    ##@externaldata@src/test/resources/Datadriven/ModuloR4/ModuloR4UAT.xlsx@API3
      | Prueba3 | Prueba3  | URL_BASE_REDIRECCIONAR_LINK2 | /        | GET    |         | v:638560029906626892 | 200        | MinPasswordLength | 6                   |                 |             |          |          |
      #|Prueba|Prueba|URL_BASE_REDIRECCIONAR_LINK|/|GET-QUERY||name:Irvin|200|count|854|||||
#|Prueba|Prueba2|URL_BASE_REDIRECCIONAR_LINK|/|GET-QUERY||name:vacio|200|count|0|||||
