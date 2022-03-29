# Coachorros-App


https://user-images.githubusercontent.com/66447409/160702473-3b241370-11a6-42ff-aec1-fe70af328129.mp4


Meu primeiro projeto disponível na Play Store!

O app Coachorros une os melhores cachorros com as melhores frases motivacionais para energizar seu dia.

Nesse projeto pessoal, o objetivo foi aplicar alguns conceitos que estou estudando.

A ideia era consumir duas APIs, uma de foto de uma de frases, e uni-las em uma imagem que pode ser compartilhada.

Para a API de fotos utilizei a Dog CEO Api: https://dog.ceo/dog-api/.
Nas frases motivacionais foi onde tive maior dificuldade, não existe uma API dessa em português. Para resolver esse problema, tive que aprender a fazer um Webscraper em Java que utilizei para recuperar mais de 7000 frases (link para o repositório do webscraper: https://github.com/gustavozreis/JavaWebScraping).

Outra novidade que tive que aprender foi como criar o Bundle necessário para subir o App e como configurá-lo na Play Store.

Deu tudo certo e ele pode ser baixado em: https://play.google.com/store/apps/details?id=com.gustavozreis.dogvacionalapp

Link do repositório do projeto no GitHub: https://github.com/gustavozreis/Coachorros-App


Para a construção desse projeto utilizei:
- Consumo de APIs
- View Binding
- View Model
- Live Data / Observers
- Corroutines
- Retrofit com Moshi
- Biblioteca Glide
- Manipulação de Arquivos no Dispositivo
- Splash Activity
- Bundle Build
- Webscraping
- Intent implícito
