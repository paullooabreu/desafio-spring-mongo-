# desafio-spring-mongo
Desafio Via Varejo

![JDK](https://camo.githubusercontent.com/834209b2982522a628496ba03e4e197320fb8dd45c359e923a826bc9bc8babe0/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6a646b2d382d6c69676874677261792e737667)
![JDK](https://camo.githubusercontent.com/e6e29c92ba28b45b5571f94a37f22ca54f5b87ee2728f375cb0315953b56c299/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f746f6f6c2d677261646c652d626c75652e737667)

Projeto criado como desafio via varejo

Possui APIs que consomen banco de dados NSQL mongoDB

# Collections das Apis: 
Swagger v3.0: url: [Swagger](http://localhost:8080/swagger-ui/index.html)

Postman: [Postman](https://github.com/paullooabreu/desafio-spring-mongo-/blob/main/via-varejo.postman_collection.json)

# Configurações do gradle:

    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'

    testImplementation('junit:junit:4.13')
    testImplementation 'org.mockito:mockito-core:3.8.0'

    //swagger
    implementation "io.springfox:springfox-boot-starter:3.0.0"
    compile "io.springfox:springfox-swagger-ui:3.0.0"

    //mapstruct
    implementation 'org.mapstruct:mapstruct:1.4.2.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'

    //Validação
    implementation "javax.validation:validation-api:2.0.1.Final"
    implementation "org.hibernate:hibernate-validator:6.1.0.Final"

Desafio Via Varejo V1.0.0
