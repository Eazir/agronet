# AgroNet-B

Plataforma de comercio electrónico agropecuario que conecta productores y consumidores, eliminando intermediarios. Construida con **Java 17**, **Spring Boot 3.5**, **MySQL 8** y **Thymeleaf**.
---
*USUARIOS EN LA DB*
CLIENTE:
email: a@a.com
pswr: 12345678
PODUCTOR
email: d@d.com
pswr: 12345678
---

## Tabla de Contenidos

- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Flujo de Trabajo](#flujo-de-trabajo)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Ejecución](#instalación-y-ejecución)
  - [Con Docker (recomendado)](#con-docker-recomendado)
  - [Sin Docker (desarrollo local)](#sin-docker-desarrollo-local)
    - [Windows](#windows)
    - [Linux](#linux)
- [Endpoints Principales](#endpoints-principales)
- [Variables de Entorno](#variables-de-entorno)

---

## Arquitectura

El proyecto sigue el patrón **MVC (Model-View-Controller)** con una arquitectura en capas:

```
 ┌──────────────────────────────────────────────────────┐
 │                   CLIENTE (Navegador)                 │
 │        Thymeleaf HTML + Tailwind CSS + FA Icons       │
 └──────────────┬───────────────────────┬───────────────┘
                │ HTTP (GET/POST)       │
                ▼                       ▼
 ┌─────────────────────────┐ ┌─────────────────────────┐
 │   Controladores (MVC)   │ │   Filtros JWT (Seguridad)│
 │  @Controller            │ │  JwtAuthenticationFilter │
 │  NavegacionController   │ │  SecurityConfig           │
 │  AuthController         │ └──────────┬──────────────┘
 │  ProductoController     │            │
 │  ProyectosController    │            ▼
 └──────────┬──────────────┘ ┌─────────────────────────┐
            │                │  Sesión HTTP (Session)   │
            ▼                │  + Cookie JWT (auth_token)│
 ┌─────────────────────────┐ └─────────────────────────┘
 │   Servicios (Service)    │
 │  @Service                │
 │  AuthService             │
 │  UsersService            │
 │  ProductoService         │
 │  ProyectoService         │
 └──────────┬──────────────┘
            │
            ▼
 ┌─────────────────────────┐
 │   Repositorios (JPA)     │
 │  @Repository             │
 │  extends JpaRepository   │
 └──────────┬──────────────┘
            │
            ▼
 ┌─────────────────────────┐
 │   Base de Datos MySQL    │
 │  12 tablas normalizadas  │
 └─────────────────────────┘
```

### Capas

| Capa | Responsabilidad |
|------|----------------|
| **Controller** | Recibe peticiones HTTP, valida sesión, orquesta llamadas a servicios, retorna vistas Thymeleaf o redirects |
| **Service** | Lógica de negocio, orquestación entre repositorios, validaciones |
| **Repository** | Acceso a datos vía Spring Data JPA (`JpaRepository`) |
| **Model (Entity)** | Clases anotadas con `@Entity` que mapean a tablas MySQL |
| **View** | Plantillas HTML Thymeleaf con Tailwind CSS y Font Awesome |

### Seguridad

- **JWT (JSON Web Token)**: Almacenado en cookie HttpOnly (`auth_token`), expira en 1 hora
- **Sesión HTTP**: Session timeout de 3600s, almacena `userId`, `userName`, `userEmail`, `tipo`
- **Rutas protegidas**: Todas excepto `/AgroNet`, `/auth`, `/login**`, `/registro**`
- **Redirección 401**: Usuario no autenticado es redirigido a `/auth`

---

## Tecnologías

| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| Java | 17 | Lenguaje base |
| Spring Boot | 3.5.6 | Framework principal |
| Spring MVC | - | Controladores REST/MVC |
| Spring Data JPA | - | ORM y repositorios |
| Spring Security | - | Autenticación y autorización |
| Thymeleaf | 5.x | Motor de plantillas HTML |
| Tailwind CSS | 3.x | Estilos utilitarios CDN |
| Font Awesome | 6.x | Iconos vectoriales CDN |
| MySQL | 8.0 | Base de datos relacional |
| Hibernate | 6.x | Implementación JPA |
| JJWT | 0.12.5 | Generación/validación de JWT |
| Lombok | - | Reducción de boilerplate |
| Maven | 3.x | Gestión de dependencias y build |
| Docker | - | Contenedorización |

---

## Flujo de Trabajo

### Registro y Autenticación

1. Usuario ingresa a `http://localhost:8080/system/api/v1/AgroNet`
2. Navega a `Iniciar Sesión` o `Registrarse`
3. Se registra como **Productor** (tipo=1) o **Consumidor** (tipo=2)
4. Inicia sesión → se genera JWT (1h) almacenado en cookie `auth_token`
5. Se crea sesión HTTP con datos del usuario
6. Redirige al panel de control según el tipo de usuario

### Roles de Usuario

#### Productor (tipo=1)
- **Panel**: `/panel-control` → `panel-controlP.html`
- **Productos**: CRUD completo (`/mis-productos`, `/crear-producto`, `/editar-producto/{id}`)
- **Proyectos**: CRUD completo (`/mis-proyectos`, `/crear-proyecto`, `/editar-proyecto/{id}`)
- **Perfil**: Editar datos y cambiar contraseña (`/mi-perfil`)

#### Consumidor (tipo=2)
- **Panel**: `/panel-control` → `panel-controlC.html`
- **Productos**: Vista de todos los productos activos (`/productos-disponibles`)
- **Proyectos**: Vista de todos los proyectos publicados (`/proyectos-disponibles`)
- **Perfil**: Editar datos y cambiar contraseña (`/mi-perfil`)

### Ciclo de Productos

```
Productor crea producto → Se guarda con estado=true → 
Consumidor lo visualiza en /productos-disponibles
```

### Ciclo de Proyectos

```
Productor crea proyecto (con meta de financiamiento) →
Consumidor visualiza en /proyectos-disponibles →
Puede donar para apoyar el proyecto
```

---

## Estructura del Proyecto

```
AgroNet-B/
├── docker/
│   └── db/
│       └── init.sql              # Script de inicialización de MySQL
├── src/
│   └── main/
│       ├── java/com/tyrservices/agronetb/
│       │   ├── AgroNetBApplication.java    # Punto de entrada
│       │   ├── Configs/
│       │   │   ├── SecurityConfig.java     # Config Spring Security + JWT
│       │   │   ├── JwtUtil.java           # Utilidades JWT
│       │   │   ├── JwtAuthenticationFilter.java  # Filtro JWT
│       │   │   ├── JwtUserDetails.java    # UserDetails para JWT
│       │   │   └── SessionAdvice.java     # Advice de sesión
│       │   ├── Controllers/
│       │   │   ├── AuthController.java    # Login, registro, logout
│       │   │   ├── NavegacionController.java  # Home, perfil, paneles
│       │   │   ├── ProductoController.java    # CRUD productos
│       │   │   ├── ProyectosController.java   # CRUD proyectos
│       │   │   ├── CarritoController.java     # Carrito de compras
│       │   │   └── PedidoController.java      # Pedidos
│       │   ├── Models/
│       │   │   └── entidades/
│       │   │       ├── TipoDocumento.java
│       │   │       ├── UsuarioConsumidor.java
│       │   │       ├── UsuarioProductor.java
│       │   │       ├── CategoriaProducto.java
│       │   │       ├── Producto.java
│       │   │       ├── StockProducto.java
│       │   │       ├── Carrito.java
│       │   │       ├── ProductoCarrito.java
│       │   │       ├── Pedido.java
│       │   │       ├── ProductoPedido.java
│       │   │       ├── Proyecto.java
│       │   │       └── Donaciones.java
│       │   ├── Repositorys/          # 12 interfaces JpaRepository
│       │   └── Services/
│       │       ├── AuthService/
│       │       ├── UsersService/
│       │       ├── ProductosService/
│       │       └── ProyectosService/
│       └── resources/
│           ├── application.properties
│           ├── static/
│           └── templates/
│               ├── index.html
│               ├── auth.html
│               ├── enCosntruccion.html
│               ├── Consumidor/
│               │   ├── panel-controlC.html
│               │   ├── mi-perfilC.html
│               │   ├── productosC.html
│               │   └── proyectosC.html
│               └── Productor/
│                   ├── panel-controlP.html
│                   ├── mi-perfilP.html
│                   ├── productosP.html
│                   ├── proyectosP.html
│                   ├── crear-proyecto.html
│                   └── editar-proyecto.html
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── pom.xml
└── README.md
```

---

## Requisitos Previos

### Para Docker (recomendado)
- **Docker** 24+ y **Docker Compose** v2+
- Git (para clonar)

### Para desarrollo local sin Docker
- **JDK 17** (Eclipse Temurin recomendado)
- **Maven 3.8+** (o usar `mvnw` incluido)
- **MySQL 8.0** instalado y corriendo con base de datos `AgroNet`
  - Usuario: `Tyr` / Contraseña: `Tyr_051025`
  - Puerto: `3306`

---

## Instalación y Ejecución

### Con Docker (recomendado)

```bash
# 1. Clonar el repositorio
git clone https://github.com/TU_USUARIO/AgroNet-B.git
cd AgroNet-B

# 2. Construir y levantar los contenedores
docker compose up --build

# 3. Esperar ~30 segundos a que MySQL arranque y la app se conecte
# 4. Abrir en el navegador:
#    http://localhost:8080/system/api/v1/AgroNet
```

> La primera vez, Docker Compose descargará las imágenes de MySQL 8.0 y Maven/Temurin,
> construirá el JAR de la aplicación, e inicializará la base de datos automáticamente
> con el esquema y datos de semilla (tipos de documento, categorías de productos).

**Comandos útiles:**

```bash
# Ver logs de la aplicación
docker compose logs -f app

# Ver logs de MySQL
docker compose logs -f db

# Detener contenedores (sin eliminar datos)
docker compose stop

# Detener y eliminar contenedores (los datos persisten en el volumen)
docker compose down

# Detener, eliminar contenedores y borrar la base de datos
docker compose down -v
```

---

### Sin Docker (desarrollo local)

#### Windows

**1. Requisitos**
- Instalar [JDK 17 (Eclipse Temurin)](https://adoptium.net/)
- Instalar [MySQL 8.0](https://dev.mysql.com/downloads/installer/)
- Durante la instalación de MySQL:
  - Puerto: `3306`
  - Contraseña root: la que elijas (o usa `Tyr_051025`)
  - Marcar "Add to PATH"

**2. Configurar MySQL**

```powershell
# Abrir MySQL Command Line Client o PowerShell con MySQL
mysql -u root -p

# En el cliente MySQL:
CREATE USER IF NOT EXISTS 'Tyr'@'localhost' IDENTIFIED BY 'Tyr_051025';
CREATE DATABASE IF NOT EXISTS AgroNet CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON AgroNet.* TO 'Tyr'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# Cargar datos iniciales (opcional, JPA crea tablas automáticamente)
mysql -u Tyr -p AgroNet < docker\db\init.sql
```

**3. Compilar y ejecutar**

```powershell
# Compilar
mvn clean package -DskipTests

# Ejecutar
java -jar target\AgroNet-B-0.0.1-SNAPSHOT.jar

# Alternativa: ejecutar directamente con Maven
mvn spring-boot:run
```

**4. Abrir en el navegador**
```
http://localhost:8080/system/api/v1/AgroNet
```

---

#### Linux

**1. Requisitos**

```bash
# JDK 17
sudo apt update
sudo apt install openjdk-17-jdk maven -y

# MySQL 8.0
sudo apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql
```

**2. Configurar MySQL**

```bash
sudo mysql -u root

# En el cliente MySQL:
# (si el plugin de auth es auth_socket, puede que necesites usar sudo)
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'tu_contraseña';
CREATE USER IF NOT EXISTS 'Tyr'@'localhost' IDENTIFIED BY 'Tyr_051025';
CREATE DATABASE IF NOT EXISTS AgroNet CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON AgroNet.* TO 'Tyr'@'localhost';
FLUSH PRIVILEGES;
EXIT;

# Cargar datos iniciales (opcional, JPA crea tablas automáticamente)
mysql -u Tyr -p AgroNet < docker/db/init.sql
```

**3. Compilar y ejecutar**

```bash
# Usando Maven Wrapper (no requiere Maven instalado)
./mvnw clean package -DskipTests
java -jar target/AgroNet-B-0.0.1-SNAPSHOT.jar

# O directamente
mvn spring-boot:run
```

**4. Abrir en el navegador**
```
http://localhost:8080/system/api/v1/AgroNet
```

---

## Endpoints Principales

| Método | Ruta | Descripción | Autenticación |
|--------|------|-------------|---------------|
| GET | `/AgroNet` | Página de inicio | No |
| GET | `/auth` | Página de login/registro | No |
| POST | `/login1` | Login productor | No |
| POST | `/login2` | Login consumidor | No |
| POST | `/registro1` | Registro productor | No |
| POST | `/registro2` | Registro consumidor | No |
| GET | `/logou` | Cerrar sesión | Sí |
| GET | `/panel-control` | Panel según tipo de usuario | Sí |
| GET | `/mi-perfil` | Editar perfil | Sí |
| POST | `/actualizar-perfil` | Guardar datos (productor) | Sí |
| POST | `/actualizar-perfil-consumidor` | Guardar datos (consumidor) | Sí |
| POST | `/actualizar-contrasena` | Cambiar contraseña | Sí |
| GET | `/mis-productos` | Lista productos (productor) | Sí |
| GET | `/productos-disponibles` | Productos activos (consumidor) | Sí |
| GET | `/mis-proyectos` | Lista proyectos (productor) | Sí |
| GET | `/proyectos-disponibles` | Todos los proyectos (consumidor) | Sí |
| GET | `/crear-proyecto` | Formulario crear proyecto | Sí |
| POST | `/guardar-proyecto` | Guardar proyecto | Sí |
| GET | `/editar-proyecto/{id}` | Editar proyecto | Sí |
| POST | `/actualizar-proyecto/{id}` | Actualizar proyecto | Sí |
| GET | `/eliminar-proyecto/{id}` | Eliminar proyecto | Sí |

---

## Variables de Entorno

| Variable | Default | Descripción |
|----------|---------|-------------|
| `DB_HOST` | `localhost` | Host de MySQL |
| `DB_PORT` | `3306` | Puerto de MySQL |
| `DB_NAME` | `AgroNet` | Nombre de la base de datos |
| `DB_USER` | `Tyr` | Usuario de MySQL |
| `DB_PASSWORD` | `Tyr_051025` | Contraseña de MySQL |

Estas variables se usan en `application.properties` con sintaxis `${VAR:default}`.
En Docker se pasan a través de `docker-compose.yml` en `environment`.
