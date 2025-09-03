# BetShare - Plataforma de Picks Deportivos

**BetShare** es una plataforma desarrollada en **Java** con **Spring Boot.** Diseñada para gestionar operaciones relacionadas con pronósticos deportivos. El sistema ofrece funcionalidades completas de registro y autenticación mediante JWT, gestión de usuarios, control de acceso por roles, y lógica de negocio para la publicación, compra y administración de picks deportivos.

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Security para la autenticación y autorización.
- JPA/Hibernate para la interacción con la base de datos.
- Supabase con PostgreSQL


## 🧩 Funcionalidades principales

- **Autenticación y autorización JWT**:
   - Registro e inicio de sesión de usuarios.
   - Uso de **tokens JWT** para proteger endpoints y restringir el acceso basado en roles (`USER`, `ADMIN`, etc.).
- **Gestión de usuarios**:
   - Crear cuenta, iniciar sesión, obtener detalles y actualizar información personal.
- **Apuestas**:
   - Los usuarios pueden:
      - Subir picks deportivos gratuitos o de pago con imágenes.
      - Ver picks disponibles con filtros como competición y deporte.
      - Comprar picks de otros usuarios y consultar su historial de compras.


- **Pagos**:
    - Registro y seguimiento de pagos realizados por los usuarios para compra de picks.
    - Simulacion de pagos y retiros de fondos.
    
## 📡 Endpoints principales
- **Usuarios**:
    - Registro de usuarios: `POST /api/v1/users`
    - Login: `POST /api/v1/auth/login`

- **Apuestas**:
    - Obtener todas las apuestas: `GET /api/v1/bets`
    - Obtener apuesta por ID: `GET /api/v1/bets/{id}`
    - Obtener apuestas disponibles: `GET /api/v1/bets/available` 
    - Obtener apuestas por competición: `GET /api/v1/bets/competition/{id}`
    - Obtener apuestas por categoría: `GET /api/v1/bets/category/{id}`
    - Crear nueva apuesta/pick: `POST /api/v1/bets` 
    - Actualizar apuesta: `PATCH /api/v1/bets/{id}`
    - Eliminar apuesta: `DELETE /api/v1/bets/{id}`

-  **Compras de Apuestas**
    - Obtener todas las compras: `GET /api/v1/bet-purchases`
    - Obtener compra por ID: `GET /api/v1/bet-purchases/{id}`
    - Obtener compras de una apuesta: `GET /api/v1/bet-purchases/bet/{id}`
    - Obtener compras de un usuario: `GET /api/v1/bet-purchases/user/{id}`
    - Obtener compras de picks de un creador: `GET /api/v1/bet-purchases/creator/{id}`
    - Verificar si usuario compró apuesta específica: `GET /api/v1/bet-purchases/userandbet?userId={id}&betId={id}`
    - Comprar apuesta: `POST /api/v1/bet-purchases`
    - Eliminar compra: `DELETE /api/v1/bet-purchases/{id}`

- **Pagos**:
    - Obtener todos los pagos: `GET /api/v1/payments`
    - Obtener pagos de un usuario: `GET /api/v1/payments/user/{userId}`
    - Obtener pago por ID: `GET /api/v1/payments/{id}`
    - Registrar pago: `POST /api/v1/payments`
    - Eliminar pago: `DELETE /api/v1/payments/{id}`


### 📬 Colección Postman

Puedes explorar y hacer un fork para probar todos los endpoints de la API usando la colección pública en Postman:

🔗 [Colección BetShare API en Postman](https://www.postman.com/aviation-geoscientist-53836028/workspace/betshare/collection/43512481-74cf8c49-c939-492c-b053-1b59736963b6?action=share&source=copy-link&creator=43512481)
  
### 🔐 Seguridad
Todos los endpoints (excepto registro e inicio de sesión) requieren autenticación mediante JWT. Los roles de usuario determinan el acceso a ciertas operaciones. 

### 📋 Características técnicas

- **Paginación**: Todos los endpoints de listado soportan paginación con parámetros `page` y `size/elements`.
- **Filtros**: Filtrado por competición y categoría en picks.
- **Subida de imágenes**: Los picks soportan imágenes mediante Multipart Form Data.
- **Validaciones**: Validaciones robustas con Bean Validation en todos los DTOs.
- **Manejo de excepciones**: Sistema centralizado de manejo de errores con mensajes descriptivos.

## Próximas funcionalidades
- **Estadísticas detalladas**:
    - Dashboard con métricas de rendimiento de picks.
    - Historial de ganancias y pérdidas por usuario.
    - Rankings de mejores tipsters.
- **Notificaciones**: Sistema de notificaciones para nuevos picks y resultados.
- **Seguimiento de tipsters**: Funcionalidad para seguir a tipsters favoritos.
- **🌐 Integración con frontend desarrollado en Angular**.
- **🧪 Pruebas automatizadas** (JUnit + MockMvc).
- **🛡️ Refresh tokens** para renovación automática de sesiones.


## 🚧Estado del proyecto
**En desarrollo activo.** La API se encuentra funcional con todas las operaciones CRUD implementadas. Continúa en expansión con nuevas características orientadas a mejorar la experiencia del usuario y proporcionar análisis detallados de rendimiento de picks deportivos.

  

