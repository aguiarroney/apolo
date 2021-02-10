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

## Funcionalidades

### Tela principal

O app é composto por uma tela principal que contem um mapa onde é possivel que o usuário veja seu polo de atuação e os clientes (pins azuis) e leads (pins verdes) próximos. 
Além disso, nesta tela é possivel visualizar detalhes sobre os pins, criar novos pins de Leads ou clientes, deletar pins e converter pins de Leads em clientes.
Por fim, é possivel exibir os pins do mapa por clusters de de TPV. Sendo os clusters agrupadores para exibir pins com TPV menor que 10k, TPV entre 10k e 20k e TPV maior do que 20k.

![](/files/mapa.jpeg) ![](/files/mapa_add.jpeg) ![](/files/mapa_details.jpeg)


### Tela de Clientes

Na tela de clientes é possivel visualizar a lista com informaões sobre cada cliente.
Aqui é possível ver o nome do estabelecimento, o endereço, a satisfação do cliente com o atendimento, em quanto tempo ocorreu uma visita, a data da próxima visita e TPV potencial do cliente.
Além disso, é possível fazer uma busca na lista através do nome do estabelecimento ou do endereço.

![](/files/clientes.jpeg)

### Tela de Leads

Na tela de leads é possível visualizar a lista com informações sobre cada lead.
Aqui é possível ver o nome do lead, endereço, data da próxima visita, status da negociação (quente, fria), número de visitas.
Além disso, é possivel enviar uma proposta para o lead, caso a mesma não tenha sido enviada. Se houver uma proposta enviada, o lead terá um indicador do envio.

![](/files/leads.jpeg)
