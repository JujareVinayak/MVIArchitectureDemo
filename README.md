This project demonstrates the Model-View-Intent Architecture in Android with Kotlin and Compose.
-----------
MutableStateFlow is used for uiState as StateFlow's classes retains data on screen orientation changes.
MutableSharedFlow is used for event to as SharedFlow's classes notify the listeners continously about changes. Here Viewmodel acts as listener.
Channel is used for effect (error toasts) as they are used to handle events that must be processed exactly once. In this case we show error toast exactly once.
