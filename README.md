# Rick and Morty

Una aplicación nativa para Android que permite explorar personajes de la serie Rick and Morty, gestionar favoritos y marcar episodios vistos. Construida con Jetpack Compose y MVVM + Clean Architecture.

---

## Arquitectura

La app sigue la arquitectura **MVVM + Clean Architecture**:

- `domain`: Casos de uso y modelos de negocio.
- `data`: Fuentes de datos (API, Room) y repositorios.
- `presentation`: Pantallas, ViewModels y manejo de UI.
- `app`: Módulo principal que ensambla todo.

---

## 🛠️ Tecnologías y Librerías

- **Jetpack Compose** - UI moderna declarativa.
- **Retrofit + Gson** - Consumo de APIs REST.
- **Room** - Persistencia local.
- **Hilt** - Inyección de dependencias.
- **Coroutines + Flow** - Manejo de asincronía y estados.

---

## Características principales

### Pantalla principal - Listado de personajes
- Paginación (scroll infinito).
- Filtros por estado y especie.
- Barra de búsqueda por nombre.
- Pull to refresh.

### Detalle de personaje
- Imagen, nombre, género, especie, estado, ubicación.
- Lista de episodios (marcar como vistos).
- Agregar o quitar de favoritos.

### Favoritos
- Lista de personajes guardados como favoritos.

---

## ✅ Requisitos

- Android Studio Giraffe o superior.
- SDK mínimo: 24 (Android 7.0)
- Conexión a internet para consumir la API.

---

## 🚀 Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git https://github.com/RicardoVO94/PruebaTecnicaRickAndMorty.git
   cd pruebatecnicarickandmorty
