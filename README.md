# ChallengeMercadolibre

Solución al Challenge: Cupón de compra

## Tabla de Contenidos

- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Uso](#uso)
- [Fuentes](#USO)

## Requisitos

- Java 17
- Spring Boot 3.2.2+
- 4.0.0-alpha-13

## Configuración

- Clonar el repositorio: https://github.com/ogrico/MercadolibreChallengue.git
- Compilar y empaquetar op1: En una terminal en la ubicación del proyecto clonado ejecutar mvn package
- Compilar y empaquetar op2: Utilizando un IDE de desarrollo utilizar los Lifecycle de maven con el apartado gradico IDE einiciar el package


## Ejecución

- Una vez ejecutado el package, ubicar la carpeta :open_file_folder: .\target el jar genrado Challenge-1.0.0.jar
- En una terminal ejecutar el comando java -jar Challenge-1.0.0.jar

## Uso

- El servicio expone 2 endpoints coupon y stats
- /api/challengeMercadolibre/coupon: El cual espera como entrada un json con:
   * El siteId (lacción de mercadolire "Argentina MLA", "Colombia MCO", etc).
   * El items_ids (Colección con los ids de los productos a comprar).
   * amount (Valor del cupon a utilizar) 
    ```json
  {
   "siteId":"MLA",
   "item_ids":[
      "MLA1578016358",
      "MLA3",
      "MLA1378609707",
      "MLA1578016358",
      "MLA1",
      "MLA1378609707"
   ],
   "amount":4406189
  }
- /api/challengeMercadolibre/coupon/stats: El cual no requeire datos de entrada y ese nos retorna las estaditicas del uso sobre el metodo coupon

## Fuentes

Para la elavoración del servicio de /coupon se realizo uso de la api de mercadolibre para desarrolladores http://developers.mercadolibre.com
- Se utilizo https://api.mercadolibre.com/sites/ para conocer el id del site sobre el cual buscar los productos
- Para listar las categorias de los productos se utilizo https://api.mercadolibre.com/sites/{idSite}
- Para identificar los prodcutos por categoria se utilizo https://api.mercadolibre.com/sites/{idSite}/search?category={idCategoria}
- Para consultar el detalle de los prodcutos se utilizo https://api.mercadolibre.com/items/{idTem}
