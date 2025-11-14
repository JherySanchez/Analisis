# Analisis
# 🍰 Sistema de Gestión de Inventario - Pastelería "DelyJhos"
 El objetivo es diseñar e implementar un sistema de escritorio para optimizar el control de insumos, reducir pérdidas y mejorar la planificación de compras y producción en una pastelería.

---

## 👥 Integrantes
* **Sanchez Rios Jheremy Ayrton** 
* **Castillo Velasquez Dayanna Araceli** 
* **Valverde Tuesta Adriano Luis**

**Docente:**
* Chayan Coloma Alejandro 

---

## 🎯 Funcionalidades Principales
El sistema permite realizar las siguientes operaciones:

* **Seguridad:** Autenticación de usuarios (Login) con roles.
* **Gestión de Insumos:** CRUD completo para insumos (harina, azúcar, etc.).
* **Gestión de Proveedores:** CRUD completo de proveedores.
* **Gestión de Productos:** CRUD de productos terminados (tortas, postres) y asignación de sus recetas.
* **Gestión de Pedidos:** Creación de pedidos a proveedores (cabecera y detalle).
* **Control de Inventario:** Registro de movimientos de inventario (consumo, merma, ventas, producción).
* **Reportes:** Generación de reportes básicos (ej. Stock Actual).

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 
* **IDE:** Apache NetBeans
* **Base de Datos:** MySQL
* **Driver:** MySQL Connector/J
* **UI:** Java Swing (JFrames)
* **Arquitectura:** Modelo-Vista-Controlador (MVC)

---

## 🚀 Cómo Ejecutar el Proyecto

Sigue estos pasos para levantar el proyecto en tu entorno local.

### 1. Prerrequisitos
* Tener instalado [Apache NetBeans IDE](https://netbeans.apache.org/).
* Tener instalado un gestor de base de datos MySQL (como [XAMPP](https://www.apachefriends.org/es/index.html) o [MySQL Workbench](https://www.mysql.com/products/workbench/)).

### 2. Configuración de la Base de Datos
1.  Inicia tu servicio de MySQL.
2.  Abre tu gestor de base de datos (MySQL Workbench, phpMyAdmin, etc.).
3.  Crea una nueva base de datos llamada **`bd_delyjhos`**.
4.  Importa el script SQL que se encuentra en `/database/bd_delyjhos.sql` (¡Recuerda agregar tu archivo `.sql` a esta carpeta!).
5.  ¡Listo! Ya tienes la estructura de tablas y (opcionalmente) datos de prueba.

### 3. Configuración del Proyecto en NetBeans
1.  Clona o descarga este repositorio.
2.  Abre NetBeans y selecciona `File` > `Open Project...` y abre la carpeta del proyecto.
3.  **Configura la conexión:**
    * Navega al archivo `modelo/ConexionDB.java`.
    * Modifica las constantes `USER` y `PASSWORD` para que coincidan con tu configuración local de MySQL.
    ```java
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; // <-- cambiar segun usuario
    ```
4.  **Driver de MySQL:** El driver `mysql-connector-j-9.5.0.jar` ya está incluido en la carpeta `lib` y referenciado con una ruta relativa, por lo que debería funcionar automáticamente.

### 4. Ejecutar la Aplicación
1.  Haz clic derecho en el proyecto en NetBeans.
2.  Selecciona **`Run`** (Ejecutar).
3.  NetBeans compilará el proyecto e iniciará la aplicación ejecutando la clase `main.Main.java`, la cual abrirá la ventana de Login.

**Credenciales de prueba:**
* **Usuario:** `admin`
* **Contraseña:** `123`

---

## 📁 Estructura de Paquetes (Arquitectura MVC)
El proyecto está organizado siguiendo una arquitectura MVC estricta para separar responsabilidades.