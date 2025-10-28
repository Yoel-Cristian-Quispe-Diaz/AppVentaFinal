[📄 Ver documento PDF](captura de pantallas.pdf)


# 📱 MyVentaApp - Sistema de Gestión de Ventas

Una aplicación Android moderna y completa para la gestión integral de ventas, desarrollada con Kotlin y siguiendo las mejores prácticas de desarrollo móvil.

## 🌟 Características Principales

### ✨ **Gestión Completa CRUD**
- **👥 Usuarios**: Crear, editar, eliminar y visualizar usuarios del sistema
- **📦 Productos**: Administración completa del inventario de productos
- **🏷️ Categorías**: Organización y clasificación de productos
- **💰 Ventas**: Registro y seguimiento de todas las transacciones
- **📋 Detalles de Venta**: Gestión detallada de items por venta

### 🎨 **Interfaz de Usuario Moderna**
- **Material Design 3**: Diseño moderno y consistente
- **Navegación Intuitiva**: Bottom Navigation con iconos personalizados
- **Tarjetas Interactivas**: Cards con elevación y colores temáticos
- **Dropdowns Inteligentes**: Selección de relaciones mediante listas desplegables
- **Validación en Tiempo Real**: Feedback inmediato al usuario

### 🔧 **Funcionalidades Avanzadas**
- **Edición Rápida**: Toque cualquier elemento para editarlo
- **Confirmación de Eliminación**: Diálogos de seguridad para prevenir pérdidas
- **Relaciones Inteligentes**: Dropdowns automáticos para categorías y usuarios
- **Actualización Automática**: Las listas se refrescan automáticamente tras cambios
- **Manejo de Errores**: Mensajes informativos para el usuario

## 🏗️ Arquitectura de la Aplicación

### 📁 **Estructura del Proyecto**
```
app/src/main/java/com/example/appventafinal/
├── controller/          # ViewModels (MVVM)
│   ├── UsuarioViewModel.kt
│   ├── ProductoViewModel.kt
│   ├── CategoriaViewModel.kt
│   ├── VentaViewModel.kt
│   └── DetalleVentaViewModel.kt
├── model/              # Modelos de datos
│   ├── Usuario.kt
│   ├── Producto.kt
│   ├── Categoria.kt
│   ├── Venta.kt
│   └── DetalleVenta.kt
├── network/            # Configuración de red
│   ├── ApiService.kt
│   └── RetrofitClient.kt
└── view/               # UI y Adapters
    ├── MainActivity.kt
    ├── UsuariosFragment.kt
    ├── ProductosFragment.kt
    ├── CategoriasFragment.kt
    ├── VentasFragment.kt
    ├── DetalleVentaFragment.kt
    └── [Adapters]
```

### 🎯 **Patrón de Arquitectura: MVVM**
- **Model**: Modelos de datos con anotaciones Gson para serialización
- **View**: Fragments con ViewBinding para UI reactiva
- **ViewModel**: Lógica de negocio y gestión de estado con LiveData

## 📊 Modelos de Datos

### 👤 **Usuario**
```kotlin
data class Usuario(
    val idUsuario: Int,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val telefono: String?,
    val direccion: String?,
    val rol: String
)
```

### 📦 **Producto**
```kotlin
data class Producto(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    val idCategoria: Int?,
    val imagen: String?
)
```

### 🏷️ **Categoría**
```kotlin
data class Categoria(
    val idCategoria: Int,
    val nombre: String,
    val descripcion: String?
)
```

### 💰 **Venta**
```kotlin
data class Venta(
    val idVenta: Int,
    val idUsuario: Int?,
    val fecha: String,
    val total: Double,
    val metodoPago: String
)
```

### 📋 **Detalle de Venta**
```kotlin
data class DetalleVenta(
    val idDetalle: Int,
    val idVenta: Int,
    val idProducto: Int,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double
)
```

## 🌐 API Endpoints

La aplicación se conecta a una API REST con los siguientes endpoints:

### 👥 **Usuarios**
- `GET /usuarios/` - Obtener todos los usuarios
- `POST /usuarios/` - Crear nuevo usuario
- `GET /usuarios/{id}` - Obtener usuario específico
- `PUT /usuarios/{id}` - Actualizar usuario
- `DELETE /usuarios/{id}` - Eliminar usuario

### 📦 **Productos**
- `GET /productos/` - Obtener todos los productos
- `POST /productos/` - Crear nuevo producto
- `GET /productos/{id}` - Obtener producto específico
- `PUT /productos/{id}` - Actualizar producto
- `DELETE /productos/{id}` - Eliminar producto

### 🏷️ **Categorías**
- `GET /categorias/` - Obtener todas las categorías
- `POST /categorias/` - Crear nueva categoría
- `GET /categorias/{id}` - Obtener categoría específica
- `PUT /categorias/{id}` - Actualizar categoría
- `DELETE /categorias/{id}` - Eliminar categoría

### 💰 **Ventas**
- `GET /ventas/` - Obtener todas las ventas
- `POST /ventas/` - Crear nueva venta
- `GET /ventas/{id}` - Obtener venta específica
- `PUT /ventas/{id}` - Actualizar venta
- `DELETE /ventas/{id}` - Eliminar venta

### 📋 **Detalles de Venta**
- `GET /detalle_venta/` - Obtener todos los detalles
- `POST /detalle_venta/` - Crear nuevo detalle
- `GET /detalle_venta/{id}` - Obtener detalle específico
- `PUT /detalle_venta/{id}` - Actualizar detalle
- `DELETE /detalle_venta/{id}` - Eliminar detalle

## 🚀 Funcionalidades Implementadas

### ✅ **CRUD Completo**
- ✅ Crear nuevos registros con validación
- ✅ Leer y mostrar listas con diseño atractivo
- ✅ Actualizar registros existentes (toque para editar)
- ✅ Eliminar con confirmación de seguridad

### ✅ **Mejoras de UX/UI**
- ✅ Dropdowns para relaciones (Categoría en Productos, Usuario en Ventas)
- ✅ Iconos temáticos por sección
- ✅ Colores diferenciados por módulo
- ✅ Animaciones y transiciones suaves
- ✅ Feedback visual inmediato

### ✅ **Gestión de Estados**
- ✅ Loading states durante operaciones de red
- ✅ Error handling con mensajes informativos
- ✅ Actualización automática de listas
- ✅ Validación de formularios

## 🛠️ Tecnologías Utilizadas

### 📱 **Android**
- **Kotlin** - Lenguaje principal
- **Android SDK 36** - Plataforma objetivo
- **Material Design 3** - Sistema de diseño
- **ViewBinding** - Binding de vistas

### 🏗️ **Arquitectura**
- **MVVM** - Patrón de arquitectura
- **LiveData** - Observables reactivos
- **ViewModel** - Gestión de estado
- **Repository Pattern** - Abstracción de datos

### 🌐 **Networking**
- **Retrofit 2** - Cliente HTTP
- **Gson** - Serialización JSON
- **OkHttp** - Cliente HTTP subyacente

### 🎨 **UI/UX**
- **RecyclerView** - Listas eficientes
- **Material Components** - Componentes UI
- **CardView** - Tarjetas de contenido
- **Navigation Component** - Navegación

## 📋 Requisitos del Sistema

### 📱 **Dispositivo**
- **Android 9.0** (API 28) o superior
- **RAM**: 2GB mínimo recomendado
- **Almacenamiento**: 50MB libres
- **Conexión a Internet**: Requerida para API

### 🛠️ **Desarrollo**
- **Android Studio** Hedgehog o superior
- **Kotlin** 1.9+
- **Gradle** 8.0+
- **JDK** 17

## 🚀 Instalación y Configuración

### 1️⃣ **Clonar el Repositorio**
```bash
git clone [URL_DEL_REPOSITORIO]
cd AppVentaFinal
```

### 2️⃣ **Configurar API**
Actualiza la URL base de la API en `RetrofitClient.kt`:
```kotlin
private const val BASE_URL = "https://tu-api.com/api/"
```

### 3️⃣ **Compilar y Ejecutar**
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## 📱 Guía de Uso

### 🏠 **Pantalla Principal**
- **Bottom Navigation**: Navega entre secciones
- **FAB**: Botón flotante para crear nuevos elementos

### 👥 **Gestión de Usuarios**
1. **Ver Lista**: Todos los usuarios registrados
2. **Crear**: Toque el FAB (+) y complete el formulario
3. **Editar**: Toque cualquier usuario en la lista
4. **Eliminar**: En el diálogo de edición, toque "Eliminar"

### 📦 **Gestión de Productos**
1. **Ver Lista**: Productos con precio y stock
2. **Crear**: Use el dropdown para seleccionar categoría
3. **Editar**: Toque el producto para modificar
4. **Eliminar**: Confirmación requerida

### 💰 **Gestión de Ventas**
1. **Ver Lista**: Ventas con total y método de pago
2. **Crear**: Seleccione usuario del dropdown
3. **Métodos de Pago**: Efectivo, Tarjeta, Transferencia
4. **Editar/Eliminar**: Toque la venta deseada

### 🏷️ **Gestión de Categorías**
1. **Crear Categorías**: Para organizar productos
2. **Editar**: Modificar nombre y descripción
3. **Eliminar**: Con validación de dependencias

## 🎨 Paleta de Colores

### 🎯 **Colores Temáticos**
- **Usuarios**: `#2196F3` (Azul)
- **Productos**: `#4CAF50` (Verde)
- **Categorías**: `#FF9800` (Naranja)
- **Ventas**: `#9C27B0` (Púrpura)
- **Detalles**: `#607D8B` (Gris Azulado)

### 🎨 **Colores del Sistema**
- **Primary**: `#1976D2`
- **Secondary**: `#03DAC6`
- **Surface**: `#FFFFFF`
- **Error**: `#B00020`
- **Success**: `#4CAF50`

## 🔧 Configuración Avanzada

### 🌐 **Configuración de Red**
```kotlin
// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "https://api.ejemplo.com/"
    
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
```

### 🎨 **Personalización de Temas**
```xml
<!-- colors.xml -->
<color name="primary_blue">#1976D2</color>
<color name="usuarios_color">#2196F3</color>
<color name="productos_color">#4CAF50</color>
<color name="categorias_color">#FF9800</color>
<color name="ventas_color">#9C27B0</color>
```

## 🐛 Solución de Problemas

### ❌ **Errores Comunes**

#### 🌐 **Error de Conexión**
```
Error: Unable to resolve host
```
**Solución**: Verificar conexión a internet y URL de la API

#### 📱 **Error de Compilación**
```
ViewBinding not found
```
**Solución**: Verificar que `viewBinding = true` esté en `build.gradle.kts`

#### 🔄 **Error de Sincronización**
```
Failed to refresh data
```
**Solución**: Verificar que la API esté funcionando correctamente

## 📈 Futuras Mejoras

### 🚀 **Próximas Funcionalidades**
- [ ] **Autenticación**: Login y registro de usuarios
- [ ] **Reportes**: Gráficos de ventas y estadísticas
- [ ] **Búsqueda**: Filtros avanzados en todas las listas
- [ ] **Sincronización Offline**: Caché local con Room
- [ ] **Notificaciones**: Alertas de stock bajo
- [ ] **Exportación**: PDF y Excel de reportes
- [ ] **Imágenes**: Subida y gestión de fotos de productos
- [ ] **Códigos de Barras**: Scanner para productos

### 🎨 **Mejoras de UI/UX**
- [ ] **Tema Oscuro**: Soporte completo para dark mode
- [ ] **Animaciones**: Transiciones más fluidas
- [ ] **Accesibilidad**: Soporte para lectores de pantalla
- [ ] **Responsive**: Mejor soporte para tablets

## 👥 Contribución

### 🤝 **Cómo Contribuir**
1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### 📝 **Estándares de Código**
- Seguir las convenciones de Kotlin
- Documentar funciones públicas
- Escribir tests para nuevas funcionalidades
- Mantener la arquitectura MVVM

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 📞 Contacto

**Desarrollador**: [Tu Nombre]
**Email**: [tu.email@ejemplo.com]
**GitHub**: [tu-usuario-github]

---

## 🙏 Agradecimientos

- **Material Design Team** - Por los componentes UI
- **Retrofit Team** - Por la excelente librería de networking
- **Android Team** - Por las herramientas de desarrollo
- **Kotlin Team** - Por el increíble lenguaje

---

### 📱 **¡Descarga y prueba MyVentaApp hoy mismo!**

*Una solución completa para la gestión de ventas en tu dispositivo Android.*
