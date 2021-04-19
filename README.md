# Desafío Calidad Gabriel Peralta

Se presenta a continuación la documentación necesaria del desafío de calidad.

Se utilizaron los archivos 'dbHotel.csv' y 'dbFlights.csv' para ser utilizados como bases de datos, el resto de los archivos que también poseen una extensión .csv estan creados con el fin de ser utilizados para diferentes tests que se llevaron a cabo.

##Controllers

Se crearon dos controllers para el desarrollo del desafío.

* **HotelController**: Contiene dos endpoints a utilizar: api/v1/hotels y api/v1/booking. El primero corresponde a los ejercicios US0001 y US0002, mientras que segundo corresponde al US0003.

Ejemplo de consulta de Endpoints:
* http://localhost:8080/api/v1/hotels y/o http://localhost:8080/api/v1/hotels?destination=Medellín&dateFrom=10/02/2021&dateTo=25/03/2021
* http://localhost:8080/api/v1/booking
* JSON para segundo endpoint:
  {
  "userName" : "seba_gonzalez@unmail.com",
  "booking" : {
  "dateFrom" : "10/11/2021",
  "dateTo" : "20/11/2021",
  "destination" : "Puerto Iguazú",
  "hotelCode" : "CH-0002",
  "peopleAmount" : 2,
  "roomType" : "double",
  "people" : [
  {
  "dni" : "12345678",
  "name" : "Pepito",
  "lastName" : "Gomez",
  "birthDate" : "10/11/1982",
  "mail" : "pepitogomez@gmail.com"
  },
  {
  "dni" : "13345678",
  "name" : "Fulanito",
  "lastName" : "Gomez",
  "birthDate" : "10/11/1983",
  "mail" : "fulanitogomez@gmail.com"
  }
  ],
  "paymentMethod" : {
  "type" : "CREDIT",
  "number" : "1234-1234-1234-1234",
  "dues" : 6
  }
  }
  }
  
* Recordar que si se copia el json a postman, se puede formatear al mismo haciendo Ctrl + B


* **FlightController**: Contiene dos endpoints a utilizar: api/v2/flights y api/v2/flight-reservation. El primero corresponde a los ejercicios US0004 y US0005, mientras que segundo corresponde al US0006.

Ejemplo de consulta de Endpoints:

* http://localhost:8080/api/v1/hotels y/o http://localhost:8080/api/v2/flights?dateFrom=23/01/2021&dateTo=20/02/2021&origin=Cartagena
* http://localhost:8080/api/v2/flight-reservation
* JSON para segundo endpoint: {
  "userName" : "seba_gonzalez@unmail.com",
  "flightReservation" : {
  "dateFrom" : "10/10/2021",
  "dateTo" : "20/11/2021",
  "origin" : "Buenos Aires",
  "destination" : "Puerto Iguazú",
  "flightNumber" : "BAPI-1235",
  "seats" : 2,
  "seatType" : "ECONOMY",
  "people" : [
  {
  "dni" : "12345678",
  "name" : "Pepito",
  "lastName" : "Gomez",
  "birthDate" : "10/11/1982",
  "mail" : "pepitogomez@gmail.com"
  },
  {
  "dni" : "13345678",
  "name" : "Fulanito",
  "lastName" : "Gomez",
  "birthDate" : "10/11/1983",
  "mail" : "fulanitogomez@gmail.com"
  }
  ],
  "paymentMethod" : {
  "type" : "credit",
  "number" : "1234-1234-1234-1234",
  "dues" : 6
  }
  }
  }
    
* Recordar que si se copia el json a postman, se puede formatear al mismo haciendo Ctrl + B

##Services

Se crearon dos services (interfaces + impl) para el desarrollo del desafío

* **HotelService**: Se utilizó Constructor en vez de Autowired para poder facilitar el desarrollo de los tests.
* Posee los siguientes métodos:
    * getHotels(Map<String, String> requestParams): método para los puntos US0001 y US0002. Dependiendo de lo que reciba en 'requestParams' es como habilita los filtros o devuelve todo. Este método SIEMPRE filtra por aquellos hoteles cuyo campo 'Booked' es igual a false.
    * createPayLoad(HotelPayloadDTO payloadDTO): método para el punto US0003. Recibe un json para poder realizar efectivamente la reserva de un hotel, contiene todas sus validaciones correspondientes. Si la creación de la reserva es exitosa, actualiza el campo 'Booked' de true a false, de manera de que no pueda ser reservado por otra persona.
    * applyFilters(List<HotelDTO> hoteles): método para aplicar en la búsqueda de los hoteles los filtros indicados por el usuario.
    * validateDestination(String destination): método para validar que el destino elegido por el usuario en un filtro exista. Si no existe, retornará un valor 'false' que implicará una excepcion de búsqueda.
    
* **FlightService**: Se utilizó Constructor en vez de Autowired para poder facilitar el desarrollo de los tests.
* Posee los siguientes métodos:
    * getFlights(Map<String, String> requestParams): método para los puntos US0004 y US0005. Dependiendo de lo que reciba en 'requestParams' es como habilita los filtros o devuelve todo.
    * createPayload(FlightPayLoadDTO payloadDTO): método para el punto US0006. Recibe un json para poder realizar efectivamente la reserva de un hotel, contiene todas sus validaciones correspondientes.
    * applyFilters(List<FlightDTO> flights): método para aplicar en la búsqueda de los vuelos los filtros indicados por el usuario.
    * validateDestination(String destination): método para validar que el destino elegido por el usuario en un filtro exista. Si no existe, retornará un valor 'false' que implicará una excepcion de búsqueda.
    * validateOrigin(String origin): método para validar que el origen elegido por el usuario en un filtro exista. Si no existe, retornará un valor 'false' que implicará una excepcion de búsqueda.
    
##Repositories

Se crearon dos repositories (interfaces + impl) para el desarrollo del desafío
* **HotelRepository**:
* Posee los siguientes métodos:
    * getAllHotels(): método para devolver todos los hoteles que existen, leídos del archivo dbHotel.csv
    * updateBoookedHotels(List<HotelDTO> hotels): actualiza el archivo dbHotel.csv luego de que se realiza una reserva de un hotel. Actualiza el campo booked.

* **FlightRepository**:
* Posee el método getAllFlights(): método para devolver todos los vuelos que existen, leídos del archivo dbFlights.csv

##Utils

* **DateValidator**: util para validación general de fechas. Se utiliza tanto para vuelos como para hoteles.
* Posee los siguientes métodos:
    * validateDate(String dateString): método que recibe un string, al cual luego intenta parsear a un LocalDate. Si no se puede parsear, retorna una excepcion.
    * CompareDates(LocalDate dateFrom, LocalDate dateTo): método para comparar la fecha desde vs la fecha hasta. Valida que la fecha desde no sea mayor que la fecha hasta.


* **EmailValidator**: util para validación general de emails. Se utiliza tanto para vuelos como para hoteles.
* Posee el método validateEmail(String emailStr) que utiliza un regex para validar que el string ingresado es correctamente un mail. Si no es asi, larga una excepcion.


* **MockCreator**: utili utilizado para la validación de tests. Posee diferentes métodos que son utilizados para crear json o conjuntos de datos mockeados que sirven para diferentes escenarios en los tests desarrollados.

##Tests

* **HotelControllerTest**:
    * getAll_OK(): test para verificar que una lista de hoteles mockeadas corresponde con lo que retorna el repository. **ESTE TEST PUEDE FALLAR LUEGO DE QUE SE REALICE LA RESERVA DE UN HOTEL, DEBIDO A QUE EL CAMPO BOOKED CAMBIA DE 'NO' A 'SI' Y POR LO TANTO SERÁ MODIFICADO.**
    * createPayLoad(): test para validar la creación correcta de una reserva de hotel.

* **FlightControllerTest**:
    * getAll_OK(): test para verificar que una lista de vuelos mockeadas corresponde con lo que retorna el repository. 
    * createPayLoad(): test para validar la creación correcta de una reserva de vuelo.
    
* **HotelRepositoryImplTest**:
    * getAllHotelsEquals(): test para verificar que una lista de datos mockeados es igual a lo que esta devolviendo el metodo getAllHotels() del repository.

* **HotelServiceImplTest**:
    * getHotels(): test para validar que una lista de datos mockeados es igual a lo que esta devolviendo el metodo getHotels() del service.
    * getHotelsByDestination(): test para validar que una lista de datos mockeados (del archivo dbHotelTestDestinationOk.csv) es igual a lo que esta devolviendo el metodo getHotels() del service cuando se filtra por destination = Buenos Aires.
    * getHotelsByWrongDestination(): test similar al anterior. Verifica que al enviar un destino desconcido, el service devuelva correctamente la excepcion correspondiente.
    * getHotelWrongDate(): Idem que el anterior pero mediante una fecha mal ingresada.
    * createPayLoad(): test para validar la creación correcta de una reserva de un hotel.
    * verifyRoomException(): Test para validar que al momento de crear una reserva, se ingrese correctamente la cantidad de persona respecto a la capacidad de la habitación.

* **FlightServiceImplTest**:
    * getFlights(): test para validar que una lista de datos mockeados es igual a lo que esta devolviendo el metodo getFlights() del service.
    * getNoFlights(): test para validar que al ingresar una fecha correcta pero que no posee datos se corresponda con lo que esta devolviendo el metodo getFlights() del service.
    * getFlightsByOriginDestination(): test para validar que al ingresar determinados filtros, lo que devuelve el metodo getFlights() del service se corresponda con lo insertado en el archivo dbFlightsDestinationTestOk.csv.
    * getFlightsByWrongDestination(): test para validar que al ingresar un destino desconocido, el metodo getFlights() del service largue la excepcion correspondiente.
    * getHotelsByWrongOrigin(): idem que test anterior, pero con origen.
    * getFlighsWrongDate(): Idem que el anterior pero mediante una fecha mal ingresada.
      * applyFiltersOK(): test igual que test 'getFlightsByOriginDestination' pero con mayor cantidad de filtros.
    * createPayLoad(): test para validar la creación correcta de una reserva de un vuelo.

* **DateValidatorTest**:
    * validateCorrectDate(): test para validar que un string se corresponde con su equivalente de LocalDate.
    * validateIncorrectDate(): test para validar que un string es incorrecto para ser parseado a LocalDate.
    * compareDatesOK(): test para validar que la fecha desde es menor que la fecha hasta.
    * compareDatesWrong(): test para validar que la fecha desde no puede ser mayor que la fecha hasta.

* **EmailValidatorTest**:
    * validateCorrectEmail(): test para validar que un string es correctamente un mail.
    * validateIncorrectEmail(): test para validar que un string no es un mail correcto.