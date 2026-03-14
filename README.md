 # NewsApp 📰
​
 Una aplicación Android que muestra las últimas noticias
 
 ## 🛠️ Tecnologías utilizadas
​
 - **Kotlin**: Lenguaje principal de desarrollo.
 - **Jetpack Compose**: Para la creación de la interfaz de usuario declarativa.
 - **Hilt (Dagger)**: Para la inyección de dependencias.
 - **Retrofit**: Cliente HTTP para el consumo de la API REST.
 - **Corrutinas de Kotlin**: Para operaciones asíncronas de forma eficiente.
 - **Coil**: Biblioteca de carga de imágenes optimizada para Compose.
 - **Navigation Compose**: Para la navegación entre pantallas.
​
 ## 🏗️ Arquitectura
 El proyecto sigue los principios de **Clean Architecture** y el patrón **MVVM (Model-View-ViewModel)**:
​
 - **Data**: Contiene la implementación de la API (Retrofit), los repositorios y las fuentes de datos.
 - **Domain**: Contiene las entidades de negocio (Models) y la lógica de negocio.
 - **Presentation**: Contiene los ViewModels y las pantallas (UI) desarrolladas con Compose, organizadas por temas.
​
## 📱 App Screenshots

Estas son las 3 pantallas principales de la App

| Home Screen | Article Selected | Article Opened |
|-------------|------------------|----------------|
| <img src="https://github.com/user-attachments/assets/3d49ac82-f9d5-4765-8c6a-97847175086f" width="250"/> | <img src="https://github.com/user-attachments/assets/04288baf-8a82-47f2-9164-e8203222c573" width="250"/> | <img src="https://github.com/user-attachments/assets/303a8e29-096b-4fd9-b5a0-891a46baf55c" width="250"/> |
