# Apolo

Aplicação android feita para auxiliar um agente comercial a gerir a sua carteira de clientes e a prospectar novos clientes.

## Instruções

Apolo implementa funcionalidades de geolocalização utilizando a API do Google Maps.

Para que o projeto funcione corretamente é necessário instalar o Google Play Services através do Android Studio e criar o arquivo google_maps_api.xml no caminho `app/src/debug/res/values/google_maps_api.xml`

Este arquivo deve ter o seguinte formato:

```
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_KEY</string>
</resources>
```

O valor `YOUR_KEY` deve ser substituído pela [chave de API do Google Maps](https://developers.google.com/maps/documentation/android/start#get-key).

## Implementação

Este app trabalha com requisições a uma API fake que contém dados fictícios sobre clientes e leads.
Esta API foi criada no [mockApi](https://www.mockapi.io/), um serviço que permite realizar ações HTTP. Apesar da praticidade, o mockAPI não permite envio de dados personalizados, dessa forma, as ações do Apolo que precisariam de uma ação POST precisaram ser feitas de forma representativa via código.
Ademais, ações de GET e DELETE foram implementadas com sucesso através do consumo da API.


