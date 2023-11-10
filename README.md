# NutriAiEnterprise

# Integrantes
Nome: Daniel Tsyuoshi Yamamoto  RM: 95344


Nome: Lucas Sabonaro RM: 95518

### Problema a ser resolvido: 
 
A nutrição é uma área crucial para a saúde, pois uma alimentação adequada pode prevenir diversas doenças e melhorar a qualidade de vida. No entanto, o avanço tecnológico também trouxe problemas para a nutrição, como o fácil acesso a alimentos ultraprocessados e a adoção de uma dieta rápida e pouco saudável. Além disso, as informações nutricionais conflitantes na internet podem confundir as pessoas e levá-las a seguir modismos alimentares sem a orientação adequada. 
Apesar desses desafios, a tecnologia pode ser uma aliada na área da nutrição, por meio de aplicativos e dispositivos que monitoram a ingestão de alimentos. Com o uso da inteligência artificial, como o ChatGPT, podemos criar soluções que ajudem nutricionistas e pacientes a encontrar a receita mais qualificada para a situação, filtrando informações relevantes e fornecendo recomendações personalizadas. 

### Solução 
 
Nossa solução é baseada na inteligência artificial ChatGPT, que foi desenvolvida especificamente para atender às necessidades de saúde e bem-estar. Nosso objetivo é ajudar profissionais e clínicas a encontrar a melhor opção de dieta e exercício para seus pacientes. Utilizando a IA de maneira adequada, nossa solução busca auxiliar os especialistas a criar planos de dieta personalizados para cada indivíduo, levando em consideração suas necessidades específicas. Por exemplo, se um paciente quer ganhar massa muscular, nossa solução pode ajudar a criar um cardápio personalizado e adequado para atingir esse objetivo, sem que a pessoa necessariamente precise falar com um profissional. Um dos benefícios é que o paciente não precisa sair de casa para uma consulta. 

### Desenho da Arquitetura 
![image](https://github.com/DanielTsuyoshi/NutriAiDevops/assets/110398603/045befd2-3a2f-4d42-b105-9c1769e809a6)

Serviço de Aplicativos (ou API): Para hospedar a aplicação web ou API que permitirá aos usuários interagir com o sistema e criar planos de dieta personalizados. 
 
Banco de Dados em Nuvem: Utilizaremos um banco de dados em nuvem compatível, como PostgreSQL, para armazenar informações dos pacientes, planos de dieta e históricos de interações. 
 
Inteligência Artificial (ChatGPT): A integração com o ChatGPT será feita por meio de API para geração de recomendações personalizadas. 


### Banco de dados

O NutriAI utiliza o poderoso Banco de Dados Oracle para armazenar, gerenciar e recuperar informações de maneira eficiente e segura. A escolha pelo Oracle foi motivada por sua confiabilidade, atendendo às demandas específicas da área nutricional. Os dados compreendem informações de usuários, planos de assinatura, transações de pagamento, avaliações nutricionais, consultas e diversos dados nutricionais.
Para configurar a conexão com o Banco de Dados Oracle, basta ajustar as informações de acesso no arquivo application.properties, seguindo as orientações detalhadas nas instruções de execução.

### Hospedagem na Nuvem (Azure)

O NutriAI encontra-se na plataforma Microsoft Azure, garantindo não apenas alta disponibilidade, mas também escalabilidade. A Azure oferece serviços confiáveis e seguros para aplicativos em nuvem. Para configurar a hospedagem na Azure, ajuste as configurações pertinentes, como o endereço do servidor, no arquivo application.properties.

### Estratégia de Projeto

O projeto NutriAI foi concebido após um minucioso planejamento para atender às demandas específicas na área nutricional. Os principais aspectos do planejamento incluem:

#### Entendimento das Necessidades dos Usuários:

Pesquisas detalhadas para compreender as necessidades e desafios dos usuários na área de nutrição.
Entrevistas direcionadas aos usuários-alvo e coleta de feedback valioso.

#### Definição de Objetivos e Escopo:

Estabelecimento dos objetivos fundamentais do projeto, destacando a facilidade no acompanhamento nutricional e suporte eficaz aos usuários.
Definição clara do escopo do projeto, identificando as principais funcionalidades a serem implementadas.

#### Arquitetura de Software:

Adoção de uma arquitetura de microsserviços para garantir escalabilidade, segurança e eficiência.
Planejamento cuidadoso da estrutura de software, focado na modularidade e flexibilidade.

#### Desenvolvimento Iterativo:

Adoção de uma abordagem iterativa para o lançamento constante de versões funcionais.
Coleta contínua de feedback dos usuários para ajustes ágeis.

#### Escalabilidade na Nuvem:

Hospedagem estratégica na Azure, garantindo a facilidade de escalabilidade para atender a um número crescente de usuários.
Foco na alta disponibilidade do sistema.
