# Manifiesto de Arquitectura N-Capas - TiendasGo

Este documento define la estructura estricta y las reglas de diseño para el microservicio `tg-auth-core`. Copilot debe seguir esta jerarquía y reglas en cada sugerencia de código.

## 1. Estructura de Paquetes (Árbol de Directorios)

```text
src/main/java/com/tiendasgo/catalog/
├── config/                # Seguridad (Validación JWT), CORS, Swagger.
├── controllers/           # Endpoints de Inventario y Clasificadores.
├── domain/                
│   ├── entity/            # Entidades JPA mapeadas al esquema 'catalog'.
│   └── repository/        # Repositorios para Marcas, Categorías, etc.
├── services/              
│   ├── IProductService.java
│   ├── ICategoryService.java
│   └── impl/              
├── dto/                   
│   ├── request/           # Ej: ProductCreateRequest
│   └── response/          # Ej: StockMovementResponse
├── exceptions/            # Manejo de errores (Ej: ProductNotFoundException).
└── utils/                 # Constantes de negocio y Mappers (MapStruct/ModelMapper).

