# Botón Mensaje

Aplicación móvil para Android que permite redactar un mensaje y enviarlo directamente a un número de WhatsApp mediante un enlace generado automáticamente.

## Descripción

La aplicación presenta un formulario en el que el usuario introduce un número telefónico de Panamá y escribe el mensaje que desea enviar.

Después de validar los datos, la aplicación genera un enlace de WhatsApp con el número y el mensaje codificado. Este enlace se abre mediante un `Intent`, permitiendo continuar el envío desde WhatsApp o desde el navegador del dispositivo.

## Funcionalidades

- Ingreso de números telefónicos de Panamá.
- Incorporación automática del código de país `+507`.
- Validación de números telefónicos de ocho dígitos.
- Restricción del campo telefónico a caracteres numéricos.
- Campo para redactar mensajes.
- Límite de 200 caracteres por mensaje.
- Contadores de dígitos y caracteres.
- Validación de campos vacíos o incompletos.
- Codificación del mensaje para utilizarlo dentro de una URL.
- Apertura de WhatsApp mediante un enlace externo.
- Presentación de mensajes de validación mediante `Toast`.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- Android SDK
- Android Intents
- API de enlaces de WhatsApp
- Gradle con Kotlin DSL

## Funcionamiento

La aplicación solicita dos datos:

1. Un número telefónico de ocho dígitos.
2. El mensaje que se desea enviar.

El código de país de Panamá, `+507`, se agrega automáticamente. Cuando el usuario presiona **Enviar a WhatsApp**, la aplicación verifica que el número tenga ocho dígitos y que el mensaje no esté vacío.

Si los datos son válidos, el mensaje se codifica y se genera un enlace con la siguiente estructura:

```text
https://api.whatsapp.com/send?phone=NUMERO&text=MENSAJE
