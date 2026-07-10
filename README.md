# Sistema de Gestión de Reservas - Sixt (TP)

Sistema desarrollado en Java por consola para la gestión de vehículos, clientes y reservas, aplicando principios de Programación Orientada a Objetos (Herencia, Polimorfismo, Encapsulamiento) y persistencia de datos mediante `java.nio`.

## 📊 Arquitectura y Modelado (UML)

A continuación se detalla el Diagrama de Clases del dominio, incluyendo el patrón DTO para la transferencia de datos.

![Diagrama de Clases](docs/uml.png)
*(El código fuente PlantUML se encuentra en `docs/uml.txt`)*

---

## 🗺️ Hoja de Ruta del Equipo (Sprints)

- [x] **Sprint 1: Las Bases (Modelos)**
  - Creación de clases en el paquete `modelos`.
  - Implementación de Herencia en Usuarios y Polimorfismo en Vehículos.
  - Creación de constructores, getters, setters y método `toCSV()`.
- [x] **Sprint 2: Atributos de clases que vamos a guardar/mostrar (DTO)**
  - Creación de clases dto en el paquete `dto`.
- [ ] **Sprint 2: El Almacenamiento (DAO)**
  - Lectura y escritura de archivos `.txt` utilizando `java.nio`.
- [ ] **Sprint 3: El Cerebro (Servicios)**
  - Implementación de la lógica de negocio (`AuthService` y `SixtService`).
  - Lógica de cálculo de precios y gestión de DTOs.
- [ ] **Sprint 4: La Pantalla (Main y Consola)**
  - Desarrollo de menús interactivos por roles (Admin, Vendedor, Cliente).
- [ ] **Sprint 5: Entregables Finales**
  - Generación de Javadoc y compilación del `.jar` final.