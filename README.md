# pessoaApplication

This application was generated using JHipster 7.8.1, you can find documentation and help at [https://www.jhipster.tech](https://www.jhipster.tech).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.
This application is configured for Service Discovery and Configuration with the JHipster-Registry. On launch, it will refuse to start if it is not able to connect to the JHipster-Registry at [http://localhost:8761](http://localhost:8761). For more information, read our documentation on [Service Discovery and Configuration with the JHipster-Registry][].

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husk, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if ommited) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files

- `npmw` - wrapper to use locally installed npm.
  JHipster installs Node and npm locally using the build tool by default. This wrapper makes sure npm is installed locally and uses it avoiding some differences different versions can cause. By using `./npmw` instead of the traditional `npm` you can configure a Node-less environment to develop or test your application.
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use npm scripts and [Angular CLI][] with [Webpack][] as our build system.

If you are using redis as a cache, you will have to launch a cache server.
To start your cache server, run:

```
docker-compose -f src/main/docker/redis.yml up -d
```

The cache can also be turned off by adding to the application yaml:

```
spring:
    cache:
        type: none
```

See [here](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching-provider-none) for details.

**WARNING**: If you using second level hibernate cache and disabling the spring cache, you have to disable the second level hibernate cache as well since they are using
the same CacheManager.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./gradlew -x webapp
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### PWA Support

JHipster ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is disabled by default. To enable it, uncomment the following code in `src/main/webapp/app/app.module.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```
npm install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```
npm install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Edit [src/main/webapp/app/app.module.ts](src/main/webapp/app/app.module.ts) file:

```
import 'leaflet/dist/leaflet.js';
```

Edit [src/main/webapp/content/scss/vendor.scss](src/main/webapp/content/scss/vendor.scss) file:

```
@import '~leaflet/dist/leaflet.css';
```

Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

### Developing Microfrontend

Microservices doesn't contain every required backend feature to allow microfrontends to run alone.
You must start a pre-built gateway version or from source.

Start gateway from source:

```
cd gateway
npm run docker:db:up # start database if necessary
npm run docker:others:up # start service discovery and authentication service if necessary
npm run app:start # alias for ./(mvnw|gradlew)
```

Microfrontend's `build-watch` script is configured to watch and compile microfrontend's sources and synchronizes with gateway's frontend.
Start it using:

```
cd microfrontend
npm run docker:db:up # start database if necessary
npm run build-watch
```

It's possible to run microfrontend's frontend standalone using:

```
cd microfrontend
npm run docker:db:up # start database if necessary
npm watch # alias for `npm start` and `npm run backend:start` in parallel
```

### Using Angular CLI

You can also use [Angular CLI][] to generate some custom client code.

For example, the following command:

```
ng generate component my-component
```

will generate few files:

```
create src/main/webapp/app/my-component/my-component.component.html
create src/main/webapp/app/my-component/my-component.component.ts
update src/main/webapp/app/app.module.ts
```

### JHipster Control Center

JHipster Control Center can help you manage and control your application(s). You can start a local control center server (accessible on http://localhost:7419) with:

```
docker-compose -f src/main/docker/jhipster-control-center.yml up
```

### Doing API-First development using openapi-generator

[OpenAPI-Generator]() is configured for this application. You can generate API code from the `src/main/resources/swagger/api.yml` definition file by running:

```bash
./gradlew openApiGenerate
```

Then implements the generated delegate classes with `@Service` classes.

To edit the `api.yml` definition file, you can use a tool such as [Swagger-Editor](). Start a local instance of the swagger-editor using docker by running: `docker-compose -f src/main/docker/swagger-editor.yml up -d`. The editor will then be reachable at [http://localhost:7742](http://localhost:7742).

Refer to [Doing API-First development][] for more details.

## Building for production

### Packaging as jar

To build the final jar and optimize the pessoaApplication application for production, run:

```
./gradlew -Pprod clean bootJar
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar build/libs/*.jar
```

Then navigate to [http://localhost:8081](http://localhost:8081) in your browser.

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./gradlew -Pprod -Pwar clean bootWar
```

## Testing

To launch your application's tests, run:

```
./gradlew test integrationTest jacocoTestReport
```

### Client tests

Unit tests are run by [Jest][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
npm test
```

### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in [src/test/gatling](src/test/gatling).

To use those tests, you must install Gatling from [https://gatling.io/](https://gatling.io/).

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

Then, run a Sonar analysis:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./gradlew bootJar -Pprod jibDockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.8.1 archive]: https://www.jhipster.tech
[doing microservices with jhipster]: https://www.jhipster.tech/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/development/
[service discovery and configuration with the jhipster-registry]: https://www.jhipster.tech/microservices-architecture/#jhipster-registry
[using docker and docker-compose]: https://www.jhipster.tech/docker-compose
[using jhipster in production]: https://www.jhipster.tech/production/
[running tests page]: https://www.jhipster.tech/running-tests/
[code quality page]: https://www.jhipster.tech/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/setting-up-ci/
[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/
[webpack]: https://webpack.github.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
[angular cli]: https://cli.angular.io/
[gatling]: https://gatling.io/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: https://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/doing-api-first-development/





Uma breve introdução ao Terraform

Conteúdo

Introdução	3
Fluxo de uso do Terraform	3
Dependências implícitas e explícitas	4
Um Overview de um script Terraform	5
O script Main	5
INIT - Inicializar o diretório de trabalho	7
FMT & VALIDATE - Formatar e validar a configuração	7
APPLY - Criar a infraestrutura	8
SHOW - Inspect state	8
Trabalhando com back-ends	9
Atualizar o estado	11
DESTROY – destrua todos os recursos criados	11
Recursos	12
Sintaxe do recurso	12
Meta-arguments	13
for_each	13
Variáveis	14
Precedência da definição de variável	14
Definindo uma variável usando o arquivo de variáveis	14
Definindo uma variável usando a linha de comando	14
Variáveis de entrada	15
Declarando uma variável de entrada	15
Usando valores de variáveis de entrada	16
Validando um conteúdo de variável de entrada	17
Locals	18
Declarando um valor local	18
Usando o valor local	19
Alguns exemplos locais	20
Valores de saída	21
Declarando um valor de saída	21
Usando as variáveis de saída	21
Valores de saída confidenciais	22
Verificando o valor de uma variável de saída sensível	22
Introdução aos módulos	23
O módulo raiz	23
Módulos Filho	23
Módulos Publicados	23
Desenvolvendo módulos	23
Variáveis de entrada nos módulos filho	23
Quando escrever um módulo	23
Blocos de módulos	24
Chamando um módulo filho	24
Source	24
Version	25
Criando módulos	26
Estrutura do módulo	26
Estrutura do módulo padrão	26


 
Introdução
O objetivo deste documento é descrever alguns principais conceitos do Terraform a fim de servir como suporte a equipe de desenvolvimento do Banco na utilização dos módulos disponibilizados pelo Banco para serem utilizados no desenvolvimento.
A documentação destes módulos por vezes pode ser difícil de ser elaborada e uma análise so módulo a ser utilizado pode ajudar muito no entendimento do correto uso dos seus diversos parâmetros.
Vamos iniciar falando um pouco da ferramenta em sí.
O Terraform é uma infraestrutura de código aberto que fornece um fluxo de trabalho CLI consistente para gerenciar centenas de serviços em nuvem. O Terraform codifica APIs de nuvem em arquivos de configuração declarativa.
O Terraform é a infraestrutura como ferramenta de código da HashiCorp. É uma ferramenta para construir, alterar e gerenciar a cloud de maneira segura e repetível. Operadores e equipes de infraestrutura podem usar o Terraform para gerenciar ambientes com uma linguagem de configuração chamada HashiCorp Configuration Language (HCL) para implantações automatizadas e legíveis.
Em um alto nível, o Terraform permite que as operadoras usem a HCL para criar arquivos contendo definições de seus recursos desejados em praticamente qualquer provedor (AWS, GCP, GitHub, Docker, etc.) e automatiza a criação desses recursos no momento da aplicação.
Fluxo de uso do Terraform
Um fluxo de trabalho simples para implantação seguirá de perto as etapas abaixo. Vamos analisar cada uma dessas etapas e conceitos mais profundamente ao longo desse tutorial, então não entre em pânico se você não entender os conceitos imediatamente.
1.	Scope - Confirme quais recursos precisam ser criados para um determinado projeto.
2.	Author - Criar o arquivo de configuração na HCL com base nos parâmetros com escopo
3.	Initialize - Execute terraform init no diretório do projeto com os arquivos de configuração. Isso baixará os plug-ins de provedor corretos para o projeto.
4.	Validate – Esta é uma etapa importante onde diversas validações do código são executadas e os principais erros identificados e resolvidos.
5.	Plan & Apply - Execute o plano terraform para verificar o processo de criação e, em seguida, o terraform apply para criar recursos reais, bem como o arquivo de estado que compara alterações futuras em seus arquivos de configuração com o que realmente existe em seu ambiente de implantação.
O Terraform possui diversas vantagens para operadores e organizações de qualquer tamanho:
Platform Agnostic: Em um datacenter moderno, você pode ter várias nuvens e plataformas diferentes para suportar seus vários aplicativos. Com o Terraform, você pode gerenciar um ambiente heterogêneo com o mesmo fluxo de trabalho criando um arquivo de configuração para atender às necessidades do seu projeto ou organização.
Gerenciamento de estado: o Terraform cria um arquivo de estado quando um projeto é inicializado pela primeira vez. O Terraform usa esse estado local para criar planos e fazer alterações em sua infraestrutura. Antes de qualquer operação, o Terraform faz uma atualização para atualizar o estado com a infraestrutura real. Isso significa que o estado Terraform é a fonte da verdade pela qual as mudanças de configuração são medidas. 
Se uma alteração for feita ou um recurso for anexado a uma configuração, o Terraform comparará essas alterações com o arquivo de estado para determinar quais alterações resultam em um novo recurso ou modificações de recurso.
Confiança do operador: O fluxo de trabalho incorporado ao Terraform visa incutir confiança nos usuários, promovendo operações facilmente repetíveis e uma fase de planejamento para permitir que os usuários garantam que as ações tomadas pelo Terraform não causem interrupções em seu ambiente. 
Terraform é escrito em linguagem GO e a linguagem de sintaxe dos arquivos de configuração é HCL , que significa linguagem de configuração HashiCorp, que é muito mais fácil de utilizar do que yaml ou json.
Dependências implícitas e explícitas
Ao estudar os atributos de recurso usados em expressões de interpolação, o Terraform pode inferir automaticamente quando um recurso depende de outro.
 
No exemplo acima, a referência a google_compute_address.vm_static_ip.address cria uma dependência implícita do google_compute_address nomeado vm_static_ip.
O Terraform usa essas informações de dependência para determinar a ordem correta na qual criar e atualizar diferentes recursos. No exemplo acima, o Terraform sabe que o vm_static_ip deve ser criado antes que o vm_instance seja atualizado para usá-lo.
Dependências implícitas via expressões de interpolação são a principal maneira de informar a Terraform sobre essas relações, e devem ser usadas sempre que possível.
Às vezes, há dependências entre recursos que não são visíveis para o Terraform. O argumento depends_on pode ser adicionado a qualquer recurso e aceita uma lista de recursos para os quais criar dependências explícitas.
Você pode se perguntar para onde em sua configuração esses recursos devem ir. A ordem em que os recursos são definidos em um arquivo de configuração terraform não tem efeito sobre como o Terraform aplica suas alterações. Organize seus arquivos de configuração de uma maneira que faça mais sentido para você e sua equipe.

 
Um Overview de um script Terraform
O script Main
O primeiro passo para trabalhar com o Terraform é a criação de um arquivo .tf dentro do diretório de trabalho desejado. No banco você verá um arquivo main dentro de cada módulo do Terraform que for utilizar. Isso é porque os módulos seguem a mesma padronização do Terraform como um todo.
main.tf
terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
      version = "3.5.0"
    }
  }
}


Terraform Block
O bloco terraform {} contém as configurações do Terraform, incluindo os provedores necessários que o Terraform usará para provisionar sua infraestrutura. 
Para cada provedor, o atributo source define um nome de host opcional, um namespace e o tipo de provedor. 
O Terraform instala provedores do Registro Terraform por padrão. 
Nesta configuração de exemplo, a fonte do provedor do Google é definida como hashicorp/google, que é a abreviação de registry.terraform.io/hashicorp/google.
Você também pode definir uma restrição de versão para cada provedor no bloco required_providers. O atributo version é opcional, mas recomendamos usá-lo para impor a versão do provedor. Sem ele, a Terraform sempre usará a versão mais recente do provedor, o que pode introduzir uma quebra no seu código devido a atualizações da ferramenta.
Para saber mais consulte este link: provider source documentation.
Providers
O bloco do provedor configura o provedor especificado, neste caso, o google. Poderia ser também: AWS, Azure entre outros. 
Um provedor é um plugin que o Terraform usa para criar e gerenciar seus recursos. 
Você pode definir vários blocos de provedor em uma configuração Terraform para gerenciar recursos de diferentes provedores.
Desta forma, um único Script pode provisionar sua infraestrutura em diversas clouds e até mesmo on-premisses a fim de atender os requisitos específicos das aplicações do banco.
 
Resource
Após a criação do terraform e do bloco do provedor, os próximos passos é a criação dos recursos a serem criados/gerenciados pelo Terraform. Por exemplo: 

resource "aws_ecs_task_definition" "default_with_splunk" {
  count                    = local.use_splunk ? 1 : 0
  family                   = local.task_family_name
  requires_compatibilities = var.service_launch_config.launch_type == "EC2" ? ["EC2"] : ["FARGATE"]
  network_mode             = "awsvpc"
  execution_role_arn       = local.execution_role_arn
  task_role_arn            = local.task_role_arn
  cpu                      = local.total_required_cpu
  memory                   = local.total_required_mem

  container_definitions = jsonencode(local.containers_with_splunk)

  tags = module.default_tags.tags
  lifecycle {
    ignore_changes = [
      tags["REPO_ID"]
    ]
  }
}


Use blocos de recursos para definir componentes de sua infraestrutura. O bloco de recurso é o principal componente do seu script Terraform. 
Um recurso pode ser um componente físico, como um servidor, ou pode ser um recurso lógico, como um aplicativo Heroku.
Os blocos de recursos têm duas cadeias de caracteres antes da definição de bloco (lembre-se que o bloco está definido entre {}: 
1.	resource type
2.	resource name. 
Neste exemplo, o tipo de recurso é google_compute_network e o nome é vpc_network. 
O prefixo do tipo de recurso mapeia para o nome do provedor. 
Na configuração de exemplo, o Terraform gerencia o recurso de google_compute_network com o provedor do Google. De modo semelhante ocorre com o AWS e Azure.
 

Unique ID: Juntos, o tipo de recurso  e  o nome do recurso  formam uma ID exclusiva para o recurso. 
Por exemplo, a ID da sua rede é google_compute_network.vpc_network.
Resource blocks contain arguments que você utiliza para configurar o recursos.
Estes argumentos podem parecer um pouco confusos a primeira vista e a melhor maneira de entender os mesmos é buscar, ao mesmo tempo, a documentação do recurso no Terraform e na AWS (caso o recurso seja criado nesta cloud). 
INIT - Inicializar o diretório de trabalho
Quando você cria uma nova configuração ou faz check-out de uma existente no git, você precisa inicializar o diretório com o comando terraform init.
terraform init
Terraform baixa o provedor da cloud (AWS no caso do Banco) e o instala em um subdiretório oculto do seu diretório de trabalho atual, chamado .terraform. 
O comando terraform init imprime a versão do provedor Terraform instalada. 
O Terraform também cria um arquivo de bloqueio chamado . terraform.lock.hcl, que especifica as versões exatas do provedor usadas para garantir que cada execução do Terraform seja consistente. 
Isso também permite que você controle quando deseja atualizar os provedores usados em sua configuração.
FMT & VALIDATE - Formatar e validar a configuração
Recomendamos o uso de formatação consistente em todos os seus arquivos de configuração. O comando terraform fmt atualiza automaticamente as configurações no diretório atual para facilitar e consistência.
Formate sua configuração. O Terraform irá listar os nomes dos arquivos modificados, se houver. 
terraform fmt
Caso o arquivo de configuração já esteja formatado corretamente, o Terraform não retornará nenhum nome de arquivo.
Você deve certificar-se de que sua configuração é sintaticamente válida e internamente consistente usando o comando terraform validate.
Valide sua configuração. 
terraform validate
Se a configuração fornecida for válida, o Terraform retornará uma mensagem de sucesso.
 
APPLY - Criar a infraestrutura
Para realmente criar a infraestrutura definida no main.tf, você deve aplicar a configuração com o comando terraform apply. 
terraform apply
O Terraform irá listar  uma saída semelhante à mostrada abaixo, mostrando todas as ações que serão emitidas pelo terraform e solicitando sua confirmação para prosseguir.
 
Essa saída mostra o plano de execução, descrevendo quais ações o Terraform executará para criar a infraestrutura que corresponde à configuração. 
O formato de saída é semelhante ao formato diff gerado por ferramentas como o Git. 
A saída tem um sinal de mais (+) ao lado do recurso "google_compute_network" "vpc_network", o que significa que o Terraform criará esse recurso. Abaixo disso, ele mostra os atributos que serão definidos. Quando o valor exibido é (conhecido após a aplicação), isso significa que o valor não será conhecido até que o recurso seja criado.
SHOW - Inspect state
O Terraform deve armazenar o estado sobre sua infraestrutura e configuração gerenciadas. Esse estado é usado pelo Terraform para mapear recursos do mundo real para sua configuração, acompanhar metadados e melhorar o desempenho de grandes infraestruturas.
Esse estado é armazenado por padrão em um arquivo local chamado terraform.tfstate, mas também pode ser armazenado remotamente, o que funciona melhor em um ambiente de grandes equipes.
O Terraform usa esse estado local para criar planos e fazer alterações em sua infraestrutura. Antes de qualquer operação, o Terraform faz uma atualização para atualizar o estado com a infraestrutura real.
O objetivo principal do estado Terraform é armazenar associações entre objetos em um sistema remoto e instâncias de recursos declaradas em sua configuração. Quando o Terraform cria um objeto remoto em resposta a uma alteração de configuração, ele registrará a identidade desse objeto remoto em relação a uma instância de recurso específica e, em seguida, potencialmente atualizará ou excluirá esse objeto em resposta a futuras alterações de configuração.
O Terraform armazena as IDs e propriedades dos recursos que gerencia nesse arquivo, para que ele possa atualizar ou destruir esses recursos daqui para frente.
O arquivo de estado Terraform é a única maneira pela qual o Terraform pode rastrear quais recursos ele gerencia e, muitas vezes, contém informações confidenciais, portanto, você deve armazenar seu arquivo de estado com segurança e distribuí-lo apenas para membros confiáveis da equipe que precisam gerenciar sua infraestrutura.
Sincronização
Na configuração padrão, o Terraform armazena o estado em um arquivo no diretório de trabalho atual onde o Terraform foi executado. Isso funciona quando você está começando, mas quando o Terraform é usado em uma equipe, é importante que todos trabalhem com o mesmo estado para que as operações sejam aplicadas aos mesmos objetos remotos.
Remote state é a solução recomendada para este problema. Com um back-end de estado completo, o Terraform pode usar o bloqueio remoto como uma medida para evitar que vários usuários diferentes executem acidentalmente um script Terraform ao mesmo tempo na mesma infraestrutura; isso garante que cada execução do Terraform comece com o estado atualizado mais recente.
O banco possui sua própria solução para gerenciamento centralizado dos arquivos de estado.
terraform show
Trabalhando com back-ends
O backend no Terraform determina como o estado é carregado e como uma operação é executada. Essa abstração permite o armazenamento de estado de arquivo não local, a execução remota, etc.
Por padrão, o Terraform usa o back-end "local", que é o comportamento normal do Terraform ao qual você está acostumado quando está criando um script novo em sua máquina para efetuar um teste por exemplo.
Aqui estão alguns dos benefícios dos back-ends:
1.	Trabalhando em equipe: os back-ends podem armazenar seu estado remotamente e proteger esse estado com bloqueios para evitar a corrupção do mesmo. Alguns back-ends, como o Terraform Cloud, até armazenam automaticamente um histórico de todas as revisões de estado.
2.	Mantendo informações confidenciais fora do disco local: o estado é recuperado de back-ends sob demanda e armazenado apenas na memória.
3.	Operações remotas: Para infraestruturas maiores ou certas mudanças, a aplicação do terraform pode levar muito tempo. Alguns back-ends oferecem suporte a operações remotas, que permitem que a operação seja executada remotamente. Em seguida, você pode desligar o computador e a operação ainda será concluída. Combinado com o armazenamento de estado remoto e o bloqueio (descritos acima), isso também ajuda em ambientes de equipes compartilhadas.
Adicionar um back-end local
Adicionar um back-end local ao seu arquivo main.tf fará referência a um arquivo terraform.tfstate no diretório terraform/state. Para especificar um caminho de arquivo diferente, altere a variável de caminho.
 

Adicionar um back-end do Cloud Storage
Um back-end do Cloud Storage armazena o estado como um objeto em um prefixo configurável em um determinado bucket no Cloud Storage. Esse back-end também oferece suporte ao bloqueio de estado. Isso bloqueará seu estado para todas as operações que possam gravar o estado. Isso impede que outras pessoas adquiram o bloqueio e potencialmente corrompam seu estado.
O bloqueio de estado ocorre automaticamente em todas as operações que podem gravar o estado. Você não verá nenhuma mensagem de que isso está acontecendo. Se o bloqueio de estado falhar, o Terraform não continuará. Você pode desabilitar o bloqueio de estado para a maioria dos comandos com o sinalizador -lock, mas isso não é recomendado.
As etapas para migrar de um back-end local para um back-end de armazenamento em nuvem são muito simples. Basta editar sua main.tf e  substituir o back-end local atual por um back-end na cloud desejada (exemploe GCS bucket), conforme ilustrado abaixo:
 
A próxima etapa é migrar o estado usando o seguinte comando:
terraform init -migrate-state
 
 
Atualizar o estado
O comando terraform refresh é usado para reconciliar o estado que o Terraform conhece (por meio de seu arquivo de estado) com a infraestrutura do mundo real.
Vamos supor que criamos um bucket sem nenhum Labels com um script Terraform.
Em seguida, usamos o console do GCP para adicionar os Rótulos ao bucket, conforme ilustrado abaixo: 
 
Para atualizar o estado de terraform, precisaremos executar o seguinte comando:
terraform refresh
 
DESTROY – destrua todos os recursos criados
O Terraform destroy às vezes é usado para gerenciar infraestrutura efêmera para fins de desenvolvimento, caso em que você pode usar o terraform destroy para limpar convenientemente todos esses objetos temporários quando terminar seu trabalho, sem ter que entrar na SWS e apagar recurso a recurso. Além de evitar de esquecer de apagar algum recurso importante por não conhecer a localização exata do mesmo.
terraform destroy

 
Recursos
Os recursos são o elemento mais importante na linguagem Terraform. 
Cada bloco de recursos descreve um ou mais objetos de infraestrutura, como redes virtuais, instâncias de computação ou componentes de nível superior, como registros DNS.
Sintaxe do recurso
As declarações de recursos podem incluir vários recursos avançados, mas apenas um pequeno subconjunto é necessário para o uso inicial.
Um bloco de recursos declara um recurso de um determinado tipo ("google_sql_database_instance") com um determinado nome local ("mestre"). 
O nome é usado para se referir a esse recurso de outro lugar no mesmo módulo Terraform, mas não tem significado fora do escopo desse módulo.
O tipo de recurso e o nome juntos servem como um identificador para um determinado recurso e, portanto, devem ser exclusivos dentro de um módulo.

Subblock
A figura abaixo ilustra a sintaxe a ser usada para incluir uma definição de subbloco dentro de um bloco e mostra como ela se relaciona com a documentação do Terraform.

 

 
Meta-arguments
for_each
Sintaxe básica
As figuras a seguir ilustram um uso básico de for_each.
No primeiro arquivo, a variável de mapa "vpcs_to_create" é criada.
No main.tf declaramos a variável "vpcs_to_create" e a usamos no for_each dentro do módulo referenciando cada elemento na variável do mapa usando each.value.
terraform.tfvars
vpcs_to_create = {
  "vpc_01" = {
    project       = "prj-dbj"
    name          = "my-vpc-01"
  },
  "vpc_02" = {
    project       = "prj-dbj"
    name          = "my-vpc-02"
  }
}
main.tf
variable "vpcs_to_create" {
  description = "List of the VPCs to be created."
  type = map(object({
    project          = string
    name             = string  
  }))
}

module "vpc-new" {
  source   = "./modules/vpc-new"

  for_each = var.vpcs_to_create

  project  = each.value.project
  name     = each.value.name
}

 
Variáveis
Precedência da definição de variável
O Terraform possui uma ordem bem específica na utilização de variáveis, com fontes posteriores tendo precedência sobre as anteriores. Importante entender este conceito pois você poderá se deparar com uma variável sendo definida em um módulo do banco e depois, por exemplo, sendo sobrecarregada com a definição de uma variável de ambiente.
1.	Variáveis de ambiente
	O terraform.tfvars file, se estiver presente.
	O terraform.tfvars.json file, se estiver presente.
	Qualquer *.auto.tfvars or *.auto.tfvars.json files, processados em ordem lexical de seus nomes de arquivos.
	Quaisquer opções -var e -var-file na linha de comando, na ordem em que são fornecidas. (Isso inclui variáveis definidas por um espaço de trabalho do Terraform Cloud.)
Definindo uma variável usando o arquivo de variáveis
Crie um arquivo chamado terraform.tfvars na mesma pasta que o main.tf.
Examples:
projeto     = "prjdbjexample"
regiao      = "us-central1"
zona        = "us-central1-a"
banco-dados = "dbjmysql4"

availability_zone_names = [
  "us-east-1a",
  "us-west-1c",
]
Definindo uma variável usando a linha de comando
Para especificar variáveis individuais na linha de comando, use a opção -var ao executar os comandos terraform plan e terraform apply:
Exemplos:
terraform apply -var="image_id=ami-abc123"
terraform apply -var='image_id_list=["ami-abc123","ami-def456"]' -var="instance_type=t2.micro"
terraform apply -var='image_id_map={"us-east-1":"ami-abc123","us-east-2":"ami-def456"}'
terraform apply -var "do_token=${DO_PAT}" -var "domain_name=${DO_DOMAIN_NAME}"

 
Variáveis de entrada
As variáveis de entrada servem como parâmetros para um módulo Terraform, permitindo que aspectos do módulo sejam personalizados sem alterar o próprio código-fonte do módulo e permitindo que os módulos sejam compartilhados entre diferentes configurações.
Ao declarar variáveis no módulo raiz de sua configuração, você pode definir seus valores usando opções de CLI e variáveis de ambiente. 
Quando você os declara em módulos filho, o módulo main deve passar valores no bloco de módulo.
Se você estiver familiarizado com linguagens de programação tradicionais, pode ser útil comparar módulos Terraform com definições de função:
	As variáveis de entrada são como argumentos de função.
	Os valores de saída são como valores de retorno de função.
	Os valores locais são como as variáveis locais temporárias de uma função.
Declarando uma variável de entrada
Cada variável de entrada aceita por um módulo deve ser declarada usando um bloco de variáveis, conforme ilustrado pelo exemplo abaixo:
variable "image_id" {
  type = string
}

variable "availability_zone_names" {
  type    = list(string)
  default = ["us-west-1a"]
}

variable "docker_ports" {
  type = list(object({
    internal = number
    external = number
    protocol = string
  }))
  default = [
    {
      internal = 8300
      external = 8300
      protocol = "tcp"
    }
  ]
}

O rótulo após a palavra-chave da variável é um nome para a variável, que deve ser exclusivo entre todas as variáveis no mesmo módulo. 
Esse nome é usado para atribuir um valor à variável de fora e para fazer referência ao valor da variável de dentro do módulo.
O nome de uma variável pode ser qualquer identificador válido. 
NÃO USE os seguintes nomes reservados: origem, versão, provedores, contagem, for_each, ciclo de vida, depends_on, locais.
Usando valores de variáveis de entrada
Dentro do módulo que declarou uma variável, seu valor pode ser acessado de dentro de expressões como var.<name>, onde <name> corresponde ao rótulo dado no bloco de declaração:
Exemplo de uma variável sendo usada:
variable "cred-file-path" {
  type        = string
  description = "Nome completo para localizacao do arquivo de credenciais da GSA usada."
}

provider "google" {
  credentials = file(var.cred-file-path)

  project = "prjdbjexemplo"
  region  = "us-central1"
  zone    = "us-central1-a"
  alias   = "gb"
}

 
Validando um conteúdo de variável de entrada
Ao declarar variáveis Terraform, você define o tipo de variável (string, number, bool, etc.), uma descrição da variável, um valor padrão para tornar a variável opcional e se a variável contém ou não um valor confidencial, como uma senha.
As variáveis Terraform também possuem um argumento de validação. O argumento de validação define regras para determinar se o valor da variável atende a determinados requisitos. Esses requisitos podem ser um comprimento específico para uma cadeia de caracteres ou garantir que o valor comece com um prefixo específico.
As regras de validação usam as funções Terraform internas  para validar o valor. Por exemplo, use a função length() para validar se uma cadeia de caracteres contém um número específico de caracteres. Se você quiser testar suas regras de validação, use o console terraform para abrir o console  de expressão Terraform em seu ambiente de shell favorito.
As regras de validação têm duas partes:
1.	Condição: A condição usa funções Terraform para executar a validação no valor da variável. Você pode colocar várias instruções juntas usando dois E comerciais (&&) para uma instrução e ou dois símbolos de pipe (| |) para uma instrução ou. Dentro da condição, você só pode fazer referência à variável atual, não a outras variáveis na configuração.
2.	Mensagem de erro: Terraform exibe a mensagem de erro quando a validação falha. A mensagem de erro de validação deve ser pelo menos uma frase completa começando com uma letra maiúscula e terminando com um ponto ou ponto de interrogação.
Example 1 - Aqui está a sintaxe básica para declarar uma variável e incluir o argumento de validação com as duas partes. 
Neste exemplo, a condição da validação verifica se resource_name tem mais de 5 caracteres. 
Ao fazer referência à variável no argumento condition, preceda o nome da variável com var., assim como faria em uma definição de recurso. O argumento error_message é uma frase completa descrevendo por que a validação falhou.

variable "resource_name" {
  type        = string
  description = "Input name of the resource."
  validation {
    condition     = length(var.resource_name) > 5
    error_message = "The resource_name must be greater than 5 characters in length."
  }
}
Example 2 – Testa se uma variável pertence a uma matriz de strings. 
variable "finalidade" {
  type        = string
  description = "Novo ou antigo modelo"

  validation {
    condition     = contains(["new", "old"], var.finalidade) == true
    error_message = "A tag de finalidade aceita dois valores: new ou old."
  }
}
 
Locals
Este é outro tipo de recurso que é muito utilizado pelo banco e é importante conhecer pois muitas vezes você irá se deparar com a necessidade de entender os arquivos contendo as definições de locals para poder utilizar seu módulo do Terraform
Um valor local atribui um nome a uma expressão, para que você possa usar o nome várias vezes em um módulo em vez de repetir a expressão.
Se você estiver familiarizado com linguagens de programação tradicionais, pode ser útil comparar módulos Terraform com definições de função:
•	Input variables são como argumentos de função.
•	Output values são como valores de retorno de função.
•	Os valores locais são como as variáveis locais temporárias de uma função.
Os valores locais podem ser úteis para evitar a repetição dos mesmos valores ou expressões várias vezes em uma configuração, mas, se usados em excesso, também podem dificultar a leitura de uma configuração por futuros mantenedores, ocultando os valores reais usados.
Use valores locais somente com moderação, em situações em que um único valor ou resultado é usado em muitos lugares e esse valor provavelmente será alterado no futuro. 
A capacidade de alterar facilmente o valor em um local central é a principal vantagem dos valores locais.
Declarando um valor local
Um conjunto de valores locais relacionados pode ser declarado em conjunto em um único bloco local:
locals {
  service_name    = "forum"
  owner                 = "Community Team"
}
As expressões em valores locais não se limitam a constantes literais; eles também podem fazer referência 
outros valores no módulo para transformá-los ou combiná-los, incluindo variáveis, atributos de recurso ou outros valores locais:

locals {
  # Ids for multiple sets of EC2 instances, merged together
  instance_ids = concat(aws_instance.blue.*.id, aws_instance.green.*.id)
}

locals {
  # Common tags to be assigned to all resources
  common_tags = {
    Service = local.service_name
    Owner   = local.owner
  }
}

 
Usando o valor local
Depois que um valor local é declarado, você pode fazer referência a ele em expressões como local.<NAME>.
Nota: Os valores locais são criados por  um bloco locals (plural), mas você  os referencia como atributos em um objeto chamado local (singular). Certifique-se de deixar de fora o "s" ao fazer referência a um valor local!

resource "aws_instance" "example" {
  # ...

  tags = local.common_tags
}

 
Alguns exemplos locais
1.	Testa se o conteúdo de uma variável é diferente de nulo.
bkp-default-retention-days = var.bkp-retention-days != null ? var.bkp-retention-days : 7
1.	Usando uma combinação de variáveis para definir uma nova definição local
use_splunk = local.splunk_config_is_set || local.use_splunk_with_ssm_params ? true : false
use_splunk_with_ssm_params = !local.splunk_config_is_set && var.use_splunk_with_ssm_params
2.	Testa se a definição de uma lista é vazia 
subnet_ids_defined = var.subnet_ids != tolist([""]) ? true : false
tolist: converts it’s argument to a list value.
1.	Pesquisar um valor específico dentro de um mapa 
custom_path = lookup(var.metrics_path, "path", "/metrics")
lookup: A função pode ser usada para pesquisar um valor específico dentro de um mapa, dada a sua chave e, se a chave não existir, o valor padrão fornecido será retornado.
1.	Definindo uma lista local de pares de chaves/valores
 
1.	Retornando uma lista de acordo com um parâmetro de cadeia de caracteres
LaunchType = var.service_launch_config.launch_type == "EC2" ? ["EC2"] : ["FARGATE"]

 
Valores de saída
Os recursos definidos em um módulo são encapsulados, de modo que o módulo de chamada não pode acessar seus atributos diretamente. 
No entanto, o módulo filho pode declarar valores de saída para exportar seletivamente determinados valores a serem acessados pelo módulo de chamada.
Declarando um valor de saída
Cada valor de saída exportado por um  módulo filho deve ser declarado usando um bloco de saída , conforme ilustrado abaixo:
resource "google_container_cluster" "my_private_cluster" {
  provider = google-beta
  ... <Module Blocks> ... 
}
#####     MODULE OUTPUT VARIABLES DEFINITION     #####
output "client_certificate" {
  description = "Base64 encoded public certificate used by clients to authenticate to the cluster endpoint."
  value       = google_container_cluster.my_private_cluster.master_auth.0.client_certificate
}
output "client_key" {
  description = "Base64 encoded private key used by clients to authenticate to the cluster endpoint."
  value       = google_container_cluster.my_private_cluster.master_auth.0.client_key
}
output "ca_certificate" {
  description = "Base64 encoded public certificate that is the root certificate of the cluster."
  value       = google_container_cluster.my_private_cluster.master_auth.0.cluster_ca_certificate
}
Usando as variáveis de saída
Em um módulo pai, as saídas de módulos filho estão disponíveis em expressões como:
module.MODULE NAME.OUTPUT NAME
 
Valores de saída confidenciais
Existem certas saídas de terraforma que podem conter dados confidenciais.
Por exemplo: Os valores de helm renderizados podem conter dados confidenciais que precisamos passar ao helm para poder instalar os pods em nosso cluster kubernetes. 
Iniciando a terraforma 0,15,  podemos dizer à terraforma quais variáveis de entrada e saída são sensíveis para que ela possa escondê-las de sua saída.
A figura a seguir ilustra uma aplicação de terraforma que gera 4 variáveis sensíveis.

 
Verificando o valor de uma variável de saída sensível
Para verificar (depurar) os valores dessas variáveis, precisaremos executar o seguinte comando após a aplicação bem-sucedida:
Terraform output VARIABLE_NAME
Exemplo:
client_certificate de saída de terraform


 
Introdução aos módulos
Os módulos são contêineres para vários recursos que são usados juntos. 
Um módulo consiste em uma coleção de arquivos .tf  e/ou  .tf.json mantidos juntos em um diretório. 
Os módulos são a principal maneira de empacotar e reutilizar configurações de recursos com o Terraform.
O módulo raiz
Cada configuração Terraform tem pelo menos um módulo, conhecido como seu módulo raiz, que consiste nos recursos definidos nos arquivos .tf no diretório de trabalho principal.
Módulos Filho
Um módulo Terraform (geralmente o módulo raiz de uma configuração) pode chamar outros módulos para incluir seus recursos na configuração. Um módulo que foi chamado por outro módulo é muitas vezes referido como um módulo filho.
Os módulos filho podem ser chamados várias vezes dentro da mesma configuração e várias configurações podem usar o mesmo módulo filho.
Módulos Publicados
Além dos módulos do sistema de arquivos local, o Terraform pode carregar módulos de um registro público ou privado. Isso torna possível publicar módulos para outros usarem e usar módulos que outros publicaram.
O Registro Terraform  hospeda uma ampla coleção de módulos Terraform disponíveis publicamente para configurar muitos tipos de infraestrutura comum. Esses módulos são gratuitos para uso, e o Terraform pode baixá-los automaticamente se você especificar a fonte e a versão apropriadas em um bloco de chamada de módulo.
Além disso, os membros da sua organização podem produzir módulos criados especificamente para suas próprias necessidades de infraestrutura. O Terraform Cloud  e o Terraform Enterprise incluem um registro de módulo privado para compartilhar módulos internamente em sua organização.
Desenvolvendo módulos
Para obter informações sobre como desenvolver módulos reutilizáveis, consulte Desenvolvimento de módulos.
Variáveis de entrada nos módulos filho
Os valores para  as variáveis de entrada dos módulos filho  devem ser atribuídos na configuração de seu módulo pai, conforme descrito em Módulos.
Quando escrever um módulo
Não recomendamos escrever módulos que sejam apenas wrappers em torno de outros tipos de recursos únicos. Se você tiver problemas para encontrar um nome para o módulo que não seja o mesmo que o tipo de recurso principal dentro dele, isso pode ser um sinal de que o módulo não está criando nenhuma nova abstração e, portanto, o módulo está adicionando complexidade desnecessária. Basta usar o tipo de recurso diretamente no módulo de chamada.
 
Blocos de módulos
Um módulo pode chamar outros módulos, o que permite incluir os recursos do módulo filho na configuração de forma concisa. Os módulos também podem ser chamados várias vezes, dentro da mesma configuração ou em configurações separadas, permitindo que as configurações de recursos sejam empacotadas e reutilizadas.
Esta página descreve como chamar um módulo de outro. Para obter mais informações sobre como criar módulos filho reutilizáveis, consulte Desenvolvimento de módulos.
Chamando um módulo filho
Chamar um módulo significa incluir o conteúdo desse módulo na configuração com valores específicos para suas variáveis de entrada. Os módulos são chamados de dentro de outros módulos usando blocos de módulos:

module “servers” {
  source = “./app-cluster”

  servers = 5
}
Servers: É um nome local, que o módulo de chamada pode usar para se referir a essa instância do módulo.
{…..}: Dentro do corpo do bloco (entre { e }) estão os argumentos para o módulo. As chamadas de módulo usam os seguintes tipos de argumentos:
•	The source argumento é obrigatório para todos os módulos e contém o caminho para o módulo chield.
•	The version é recomendado para módulos de um registro.
•	Most  ther arguments correspondem às variáveis de entrada definidas pelo módulo. (O argumento servidores no exemplo acima é um desses.)
O Terraform define alguns outros meta-argumentos que podem ser usados com todos os módulos, incluindo for_each e depends_on.
 Source
Todos os módulos requerem um argumento  de origem, que é um meta-argumento definido pelo Terraform. Seu valor é o caminho para um diretório local que contém os arquivos de configuração do módulo ou uma fonte de módulo remota que o Terraform deve baixar e usar.
Para obter mais informações sobre valores possíveis para esse argumento, consulte Fontes de módulo.
Depois de adicionar, remover ou modificar blocos de módulos, você deve executar novamente o terraform init para permitir que o Terraform tenha a oportunidade de ajustar os módulos instalados. 
Por padrão, esse comando não atualizará um módulo já instalado; use a opção -upgrade para, em vez disso, atualizar para a versão mais recente disponível.
 
Version
 Ao usar módulos instalados a partir de um registro de módulo, recomendamos restringir explicitamente os números de versão aceitáveis para evitar alterações inesperadas ou indesejadas.
 
module "consul" {
  source  = "hashicorp/consul/aws"
  version = "0.0.5"

  servers = 3
}
O argumento version aceita uma cadeia de caracteres de restrição de versão. O Terraform usará a versão mais recente instalada do módulo que atenda à restrição; se nenhuma versão aceitável estiver instalada, ele baixará a versão mais recente que atende à restrição.
As restrições de versão são suportadas apenas para módulos instalados a partir de um registro de módulo, como o Registro Terraform público  ou  o registro de módulo privado do Terraform Cloud usado pelo banco , por exemplo.
Outras fontes de módulo podem fornecer seus próprios mecanismos de controle de versão dentro da própria cadeia de caracteres de origem ou podem não oferecer suporte a versões. 
Em particular, os módulos originados de caminhos de arquivo locais não suportam a versão; uma vez que eles são carregados do mesmo repositório de origem, eles sempre compartilham a mesma versão que o chamador.

Meta-argumentos
Juntamente com o código-fonte e  a versão, o Terraform define mais alguns meta-argumentos opcionais que têm um significado especial em todos os módulos, descritos com mais detalhes nas páginas a seguir:
•	count - Cria várias instâncias de um módulo a partir de um único bloco de módulo. Consulte a página de contagem para obter detalhes.
•	for_each - Cria várias instâncias de um módulo a partir de um único bloco de módulo. Consulte a página for_each para obter detalhes.
•	providers - Passa as configurações do provedor para um módulo filho. Consulte a página de provedores para obter detalhes. Se não for especificado, o módulo filho herdará todas as configurações de provedor padrão (sem alias) do módulo de chamada.
•	depends_on - Cria dependências explícitas entre o módulo inteiro e os destinos listados. Consulte a página depends_on para obter detalhes.
 
Criando módulos
Um módulo é um contêiner para vários recursos que são usados juntos. Os módulos podem ser usados para criar abstrações leves, para que você possa descrever sua infraestrutura em termos de sua arquitetura, em vez de diretamente em termos de objetos físicos.
Os arquivos .tf em seu diretório de trabalho quando você executa o plano terraform  ou terraform se aplicam juntos formam o  módulo raiz. Esse módulo pode chamar outros módulos e conectá-los juntos passando valores de saída de um para valores de entrada de outro.
Estrutura do módulo
Os módulos reutilizáveis são definidos usando todos os mesmos conceitos que usamos em módulos raiz. Veja o seguinte documento para maiores detalhes: configuration language . 
Mais comumente, os módulos usam:
•	Input variables para aceitar valores do módulo de chamada.
•	Output values para retornar resultados para o módulo de chamada, que ele pode usar para preencher argumentos em outro lugar.
•	Resources para definir um ou mais objetos de infraestrutura que o módulo gerenciará.
Para definir um módulo, crie um novo diretório para ele e coloque um ou mais arquivos .tf dentro exatamente como você faria para um módulo raiz. O Terraform pode carregar módulos a partir de caminhos relativos locais ou de repositórios remotos; se um módulo for reutilizado por muitas configurações, você poderá colocá-lo em seu próprio repositório de controle de versão.
Os módulos também podem chamar outros módulos usando um bloco de módulos, mas recomendamos manter a árvore de módulos relativamente plana e usar a composição do módulo como uma alternativa a uma árvore de módulos profundamente aninhada, porque isso torna os módulos individuais mais fáceis de reutilizar em diferentes combinações.
Estrutura do módulo padrão
A estrutura de módulo padrão é um layout de arquivo e diretório que recomendamos para módulos reutilizáveis distribuídos em repositórios separados. 
As ferramentas Terraform são construídas para entender a estrutura do módulo padrão e usar essa estrutura para gerar documentação.
A estrutura do módulo padrão espera o layout documentado abaixo. A lista pode parecer longa, mas tudo é opcional, exceto o módulo raiz. A maioria dos módulos não precisa fazer nenhum trabalho extra para seguir a estrutura padrão.
•	Root module. Este é o único elemento necessário para a estrutura do módulo padrão. Os arquivos Terraform devem existir no diretório raiz do repositório. 
•	README. O módulo raiz e quaisquer módulos aninhados devem ter arquivos README. Esse arquivo deve ser chamado de README ou README.md. Este último será tratado como markdown. Deve haver uma descrição do módulo e para que ele deve ser usado. Se você quiser incluir um exemplo de como este módulo pode ser usado em combinação com outros recursos, coloque-o em um exemplos de diretório como este.
O README não precisa documentar entradas ou saídas do módulo porque as ferramentas gerarão isso automaticamente.
•	LICENSE. A licença sob a qual este módulo está disponível. Se você estiver publicando um módulo publicamente, muitas organizações não adotarão um módulo, a menos que uma licença clara esteja presente. Recomendamos sempre ter um arquivo de licença, mesmo que não seja uma licença de código aberto.
•	main.tf, variables.tf, outputs.tf. Esses são os nomes de arquivo recomendados para um módulo mínimo, mesmo que estejam vazios.
main.tf deve ser o principal ponto de entrada. 
Para um módulo simples, pode ser aqui que todos os recursos são criados. 
Para um módulo complexo, a criação de recursos pode ser dividida em vários arquivos, mas todas as chamadas de módulo aninhadas devem estar no arquivo principal.
variables.tf e outputs.tf devem conter as declarações de variáveis e saídas, respectivamente.
1.	Variáveis e saídas devem ter descrições.  Todas as variáveis e saídas devem ter uma ou duas descrições de sentença que expliquem sua finalidade. 
Isso é usado para documentação. Consulte a documentação para configuração de variáveis e configuração de saída para obter mais detalhes.
•	Nested modules. Os módulos aninhados devem existir no subdiretório modules/. Qualquer módulo aninhado com  um README.md é considerado utilizável por um usuário externo. Se um README não existir, ele será considerado apenas para uso interno. Trata-se de uma mera assessoria; A Terraform não negará ativamente o uso de módulos internos. Os módulos aninhados devem ser usados para dividir o comportamento complexo em vários módulos pequenos que os usuários avançados podem escolher cuidadosamente.
Se o módulo raiz incluir chamadas para módulos aninhados, eles devem usar caminhos relativos como ./modules/consul-cluster para que o Terraform os considere parte do mesmo repositório ou pacote, em vez de baixá-los novamente separadamente.
Um módulo mínimo recomendado seguindo a estrutura padrão é mostrado abaixo. Embora o módulo raiz seja o único elemento necessário, recomendamos a estrutura abaixo como o mínimo:
 
Um exemplo completo de um módulo seguindo a estrutura padrão é mostrado abaixo. Este exemplo inclui todos os elementos opcionais e, portanto, é o mais complexo que um módulo pode se tornar:
 
