# Monumentos

App Android nativa para explorar monumentos y puntos de interés, desarrollada en Kotlin con Jetpack Compose durante mis prácticas en ALTEN.

## Funcionalidades

- **Listado de monumentos** con filtro por país y opción de marcar favoritos.
- **Ficha de cada monumento** con carrusel de fotos, descripción, enlace a Wikipedia, opción de compartir por correo y de abrir la ubicación en Google Maps.
- **Mapa** con un marcador por monumento. Al pulsar un marcador, el mapa se centra en él; al volver a pulsarlo se abre su ficha.
- **Añadir monumentos propios** manteniendo pulsado un punto del mapa: se abre un formulario con nombre, descripción, país, ciudad e imagen, con la ubicación ya rellena.
- **Favoritos** y **Mis monumentos**, que se guardan en el dispositivo y se mantienen al cerrar la app.

## Tecnologías

| Área | Herramientas |
| --- | --- |
| Lenguaje | Kotlin |
| Interfaz | Jetpack Compose, Material 3 |
| Arquitectura | MVVM (ViewModel + estado de pantalla), repositorio |
| Navegación | Navigation 3 |
| Inyección de dependencias | Hilt |
| Mapas | Google Maps Compose |
| Imágenes | Coil |
| Datos | JSON con Gson, guardado local con SharedPreferences |
| Asincronía | Kotlin Coroutines |

## Arquitectura

El proyecto separa datos, inyección de dependencias e interfaz. Cada pantalla tiene su propio ViewModel y su clase de estado.

    app/src/main/java/com/example/monumentos_practica6/
    ├── data/
    │   ├── domain/        Modelo de negocio (MonumentBo)
    │   ├── network/       DTO y mapper de la respuesta JSON
    │   ├── local/         Guardado de favoritos y monumentos propios
    │   └── repository/    MonumentRepository
    ├── di/                Módulo de Hilt
    └── ui/
        ├── launcher/          Pantalla de inicio
        ├── monument/          Listado, filtro y favoritos
        ├── monument_detail/   Ficha con carrusel
        ├── monument_map/      Mapa con marcadores
        ├── monument_add/      Formulario para añadir un monumento
        ├── monument_favourite/
        ├── my_monument/
        ├── navigation/        Rutas y navegación
        └── theme/

## Cómo ejecutarla

**Requisitos:** Android Studio reciente, JDK 21 y un dispositivo o emulador con Android 8.0 (API 26) o superior.

1. Clona el repositorio y ábrelo en Android Studio.
2. Consigue una clave de la API de Google Maps en [Google Cloud Console](https://console.cloud.google.com/) (APIs y servicios → Credenciales) con **Maps SDK for Android** activado.
3. Añade la línea `MAPS_API_KEY=tu_clave_aqui` al archivo `local.properties` de la raíz del proyecto (ese archivo no se sube al repositorio).
4. Ejecuta la app con el botón **Run**.

## Autor

**Alejandro Gadea Vicente**
[Portfolio](https://alejandro-gadea.vercel.app) · [LinkedIn](https://www.linkedin.com/in/alejandro-gadea-vicente-6146b3389) · [GitHub](https://github.com/alexga22604)
