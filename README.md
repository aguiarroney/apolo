# Apolo

Aplicação android feita para auxiliar um agente comercial a gerir a sua carteira de clientes e a prospectar novos clientes.

## Instruções

Para que o projeto funcione corretamente é necessário inserir o arquivo google_maps_api.xml no caminho `app/src/debug/res/values/google_maps_api.xml`

Este arquivo deve ter o seguinte formato:

```
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">YOUR_KEY</string>
</resources>
```

O valor `YOUR_KEY` deve ser substituído pela [chave de API do Google Maps](https://developers.google.com/maps/documentation/android/start#get-key).


