# BetShare - API de Apuestas y Rifas

**BetShare** es una API desarrollada en **Java** con **Spring Boot** que permite a los usuarios registrarse, autenticarse mediante JWT y participar en rifas y apuestas. El sistema gestiona usuarios, roles, pagos, compras de apuestas y rifas, proporcionando una API REST escalable, segura y robusta.


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
      - Subir apuestas gratuitas o con precios definidos.
      - Ver apuestas disponibles.
      - Comprar apuestas y consultar su historial de compras.
- **Rifas**:
  - Los usuarios pueden:
      - Crear rifas y definir condiciones.
      - Consultar rifas activas.
      - Comprar participaciones.
      - Ver sus participaciones en rifas.
- **Pagos**:
  - Registro y seguimiento de los pagos realizados por cada usuario, tanto para apuestas como rifas.


## 📡 Endpoints principales
- **Usuarios**:
    - Registro de usuarios: `POST /api/users`
    - Login: `POST /api/auth/login`

- **Apuestas**:
  - Listar apuestas disponibles: `GET /api/bets`
  - Crear nueva apuesta: `POST /api/bets`
  - Comprar una apuesta: `POST /api/bet-purchases`
  - Los creadores de apuestas pueden ver quién ha comprado sus apuestas: `GET /api/bet-purchases/bet/{id}`

- **Rifas**:
  - Listar rifas activas: `GET /api/raffles`
  - Crear nueva rifa: `POST /api/raffles`
  - Comprar participación en rifa: `POST /api/raffle-participations`
  - Los creadores de rifas pueden ver quién compró participaciones en sus rifas: `GET /api/raffle-participations/raffle/{id}`

- **Pagos**:
  - Consultar pagos de un usuario: `GET /api/payments/user/{userId}`
  - Registrar nuevo pago: `POST /api/payments`

### 📬 Colección Postman

Puedes explorar y hacer un fork para probar todos los endpoints de la API usando la colección pública en Postman:

🔗 [Colección BetShare API en Postman](https://www.postman.com/aviation-geoscientist-53836028/betshare/collection/yh56k4u/betshare?action=share&source=copy-link&creator=43512481)
  
### 🔐 Seguridad
Todos los endpoints (excepto registro e inicio de sesión) requieren autenticación mediante JWT. Los roles de usuario determinan el acceso a ciertas operaciones. 

## Próximas funcionalidades
- 📈 Estadísticas detalladas de las apuestas de los usuarios:
  - Historial de rendimiento (cuántas ganadas/perdidas).
  - Rentabilidad y retorno estimado por usuario.
- 🌐 Integración con frontend desarrollado en **Angular**.
- 🧪 Pruebas automatizadas (JUnit + MockMvc).
- 🛡️ Expiración y renovación de tokens JWT (con refresh tokens).

## 🚧Estado del proyecto
**En desarrollo activo.** Se encuentra en fase funcional básica y continúa en expansión con nuevas características orientadas a una mejor experiencia de usuario, administración y análisis de rendimiento.

  

